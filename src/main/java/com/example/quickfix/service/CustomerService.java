package com.quickfix.service;

import com.quickfix.dao.*;
import com.quickfix.model.*;
import java.util.List;
import java.util.Map;

public class CustomerService {
    private final ServiceCategoryDAO categoryDAO = new ServiceCategoryDAO();
    private final ProviderServiceDAO serviceDAO = new ProviderServiceDAO();
    private final ProviderProfileDAO profileDAO = new ProviderProfileDAO();
    private final RatingFeedbackDAO feedbackDAO = new RatingFeedbackDAO();

    public List<ServiceCategory> categories() throws Exception { return categoryDAO.findAll(); }
    public List<com.quickfix.model.ProviderService> search(Integer categoryId, Double maxPrice, Double minRating) throws Exception {
        return search(categoryId, maxPrice, minRating, null);
    }
    public List<com.quickfix.model.ProviderService> search(Integer categoryId, Double maxPrice, Double minRating, String keyword) throws Exception {
        return serviceDAO.search(categoryId, maxPrice, minRating, keyword);
    }
    public com.quickfix.model.ProviderService serviceDetails(int serviceId) throws Exception { return serviceDAO.findById(serviceId); }
    public ProviderProfile providerProfile(int providerId) throws Exception { return profileDAO.findByProvider(providerId); }
    public List<RatingFeedback> feedback(int providerId) throws Exception { return feedbackDAO.findByProvider(providerId); }
    public List<RatingFeedback> customerFeedback(int customerId) throws Exception { return feedbackDAO.findByCustomer(customerId); }
    public Map<Integer, Boolean> reviewedBookings(int customerId) throws Exception { return feedbackDAO.reviewedBookingsByCustomer(customerId); }
}
