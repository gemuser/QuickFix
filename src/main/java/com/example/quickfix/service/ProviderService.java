package com.quickfix.service;

import com.quickfix.dao.*;
import com.quickfix.model.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ProviderService {
    private final ProviderProfileDAO profileDAO = new ProviderProfileDAO();
    private final ProviderServiceDAO serviceDAO = new ProviderServiceDAO();
    private final ProviderAvailabilityDAO availabilityDAO = new ProviderAvailabilityDAO();
    private final ServiceCategoryDAO categoryDAO = new ServiceCategoryDAO();

    public ProviderProfile profile(int providerId) throws Exception { return profileDAO.findByProvider(providerId); }
    public void saveProfile(ProviderProfile profile) throws Exception {
        if (profile.getExperienceYears() < 0) throw new IllegalArgumentException("Experience cannot be negative");
        profileDAO.save(profile);
    }
    public List<com.quickfix.model.ProviderService> services(int providerId) throws Exception { return serviceDAO.findByProvider(providerId); }
    public void saveService(com.quickfix.model.ProviderService service) throws Exception {
        if (service.getPrice() == null || service.getPrice().compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Service rate must be greater than zero.");
        serviceDAO.save(service);
    }
    public void updateServiceRate(int serviceId, int providerId, BigDecimal price) throws Exception {
        if (serviceId <= 0) throw new IllegalArgumentException("Select a valid service.");
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Service rate must be greater than zero.");
        if (!serviceDAO.updatePrice(serviceId, providerId, price)) throw new IllegalArgumentException("Service not found for your account.");
    }
    public void deleteService(int serviceId, int providerId) throws Exception { serviceDAO.delete(serviceId, providerId); }
    public void addAvailability(ProviderAvailability availability) throws Exception {
        if (availability.getAvailableDate().isBefore(LocalDate.now())) throw new IllegalArgumentException("Cannot set availability for past dates.");
        if (!availability.getEndTime().isAfter(availability.getStartTime())) throw new IllegalArgumentException("End time must be after start time.");
        if (availabilityDAO.hasOverlap(availability)) throw new IllegalArgumentException("Time slots cannot overlap");
        availabilityDAO.create(availability);
    }
    public void deleteAvailability(int availabilityId, int providerId) throws Exception { availabilityDAO.delete(availabilityId, providerId); }
    public boolean isAvailable(int providerId, LocalDate date, java.time.LocalTime time) throws Exception { return availabilityDAO.isProviderAvailable(providerId, date, time); }
    public List<ProviderAvailability> availability(int providerId) throws Exception { return availabilityDAO.findByProvider(providerId); }
    public List<ServiceCategory> categories() throws Exception { return categoryDAO.findAll(); }
}
