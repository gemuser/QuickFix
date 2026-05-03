package com.quickfix.service;

import com.quickfix.dao.*;
import com.quickfix.model.*;
import java.util.List;

public class AdminService {
    private final UserDAO userDAO = new UserDAO();
    private final ProviderProfileDAO profileDAO = new ProviderProfileDAO();
    private final ServiceCategoryDAO categoryDAO = new ServiceCategoryDAO();
    private final ComplaintDAO complaintDAO = new ComplaintDAO();

    public List<User> users() throws Exception { return userDAO.findAll(); }
    public List<ProviderProfile> providers() throws Exception { return profileDAO.findAll(); }
    public void userStatus(int userId, String status) throws Exception { userDAO.updateStatus(userId, status); }
    public void providerVerification(int providerId, String status) throws Exception {
        profileDAO.updateVerification(providerId, status);
        userDAO.updateStatus(providerId, "APPROVED".equals(status) ? "ACTIVE" : "REJECTED".equals(status) ? "BLOCKED" : "PENDING");
    }
    public List<ServiceCategory> categories() throws Exception { return categoryDAO.findAll(); }
    public void saveCategory(ServiceCategory category) throws Exception { categoryDAO.save(category); }
    public List<Complaint> complaints() throws Exception { return complaintDAO.findAll(); }
    public void resolveComplaint(int id, String status, String response) throws Exception { complaintDAO.resolve(id, status, response); }
}
