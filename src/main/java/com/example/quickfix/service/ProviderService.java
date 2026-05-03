package com.quickfix.service;

import com.quickfix.dao.*;
import com.quickfix.model.*;
import java.util.List;

public class ProviderService {
    private final ProviderProfileDAO profileDAO = new ProviderProfileDAO();
    private final ProviderServiceDAO serviceDAO = new ProviderServiceDAO();
    private final ProviderAvailabilityDAO availabilityDAO = new ProviderAvailabilityDAO();
    private final ServiceCategoryDAO categoryDAO = new ServiceCategoryDAO();

    public ProviderProfile profile(int providerId) throws Exception { return profileDAO.findByProvider(providerId); }
    public void saveProfile(ProviderProfile profile) throws Exception { profileDAO.save(profile); }
    public List<com.quickfix.model.ProviderService> services(int providerId) throws Exception { return serviceDAO.findByProvider(providerId); }
    public void saveService(com.quickfix.model.ProviderService service) throws Exception { serviceDAO.save(service); }
    public void deleteService(int serviceId, int providerId) throws Exception { serviceDAO.delete(serviceId, providerId); }
    public void addAvailability(ProviderAvailability availability) throws Exception { availabilityDAO.create(availability); }
    public List<ProviderAvailability> availability(int providerId) throws Exception { return availabilityDAO.findByProvider(providerId); }
    public List<ServiceCategory> categories() throws Exception { return categoryDAO.findAll(); }
}
