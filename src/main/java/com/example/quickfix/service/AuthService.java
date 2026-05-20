package com.quickfix.service;

import com.quickfix.dao.*;
import com.quickfix.model.User;
import com.quickfix.util.PasswordUtil;
import com.quickfix.util.ValidationUtil;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Locale;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();
    private final RoleDAO roleDAO = new RoleDAO();
    private final ProviderProfileDAO profileDAO = new ProviderProfileDAO();

    public User login(String email, String password) throws Exception {
        String normalizedEmail = email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
        User user = userDAO.findByEmail(normalizedEmail);
        if (user == null || !"ACTIVE".equals(user.getStatus()) || !PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            return null;
        }
        return user;
    }

    public String register(String role, String name, String email, String phone, String password) throws Exception {
        String normalizedRole = role == null ? "" : role.trim().toUpperCase(Locale.ROOT);
        String normalizedName = name == null ? "" : name.trim();
        String normalizedEmail = email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
        String normalizedPhone = phone == null ? null : phone.trim();
        if (normalizedPhone != null && normalizedPhone.isEmpty()) normalizedPhone = null;
        if (ValidationUtil.isBlank(normalizedName) || !ValidationUtil.isEmail(normalizedEmail)
                || !ValidationUtil.isPhone(normalizedPhone) || !ValidationUtil.isStrongPassword(password)) {
            return "Please enter a valid name, email, phone number, and password.";
        }
        if (userDAO.findByEmail(normalizedEmail) != null) return "User is already registered with this email.";
        if (!ValidationUtil.isBlank(normalizedPhone) && userDAO.findByPhone(normalizedPhone) != null) return "Phone number is already registered.";
        int roleId = roleDAO.findIdByName(normalizedRole);
        if (roleId == 0 || "ADMIN".equals(normalizedRole)) return "Invalid role selected.";
        User user = new User();
        user.setRoleId(roleId); user.setFullName(normalizedName); user.setEmail(normalizedEmail); user.setPhone(normalizedPhone);
        user.setPasswordHash(PasswordUtil.hashPassword(password));
        user.setStatus("PROVIDER".equals(normalizedRole) ? "PENDING" : "ACTIVE");
        int id;
        try {
            id = userDAO.create(user);
        } catch (SQLIntegrityConstraintViolationException e) {
            if (!ValidationUtil.isBlank(normalizedPhone) && userDAO.findByPhone(normalizedPhone) != null) {
                return "Phone number is already registered.";
            }
            if (userDAO.findByEmail(normalizedEmail) != null) {
                return "User is already registered with this email.";
            }
            throw e;
        }
        if ("PROVIDER".equals(normalizedRole)) {
            com.quickfix.model.ProviderProfile p = new com.quickfix.model.ProviderProfile();
            p.setUserId(id); p.setBio("New provider profile"); p.setExperienceYears(0); p.setVerificationStatus("PENDING");
            profileDAO.save(p);
        }
        return null;
    }
}
