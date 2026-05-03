package com.quickfix.service;

import com.quickfix.dao.*;
import java.util.*;

public class ReportService {
    private final UserDAO userDAO = new UserDAO();
    private final BookingDAO bookingDAO = new BookingDAO();
    private final ProviderServiceDAO providerServiceDAO = new ProviderServiceDAO();

    public Map<String, Object> dashboardReport() throws Exception {
        Map<String, Object> report = new LinkedHashMap<>();
        report.put("totalUsers", userDAO.countAll());
        report.put("totalBookings", bookingDAO.countAll());
        report.put("activeProviders", userDAO.countActiveProviders());
        report.put("popularServices", providerServiceDAO.popularServices());
        return report;
    }
}
