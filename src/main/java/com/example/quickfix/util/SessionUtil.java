package com.quickfix.util;

import com.quickfix.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    private SessionUtil() { }
    public static void login(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        session.setAttribute("role", user.getRoleName());
    }
    public static User currentUser(HttpServletRequest request) {
        Object user = request.getSession(false) == null ? null : request.getSession(false).getAttribute("user");
        return user instanceof User ? (User) user : null;
    }
    public static boolean requireRole(HttpServletRequest request, String role) {
        User user = currentUser(request);
        return user != null && role.equals(user.getRoleName());
    }
    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
    }
}
