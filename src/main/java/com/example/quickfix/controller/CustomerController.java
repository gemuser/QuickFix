package com.quickfix.controller;

import com.quickfix.model.User;
import com.quickfix.service.*;
import com.quickfix.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/customer/*")
public class CustomerController extends HttpServlet {
    private static final String VIEW_PREFIX = "/WEB-INF/views";
    private final CustomerService customerService = new CustomerService();
    private final BookingService bookingService = new BookingService();
    private final NotificationService notificationService = new NotificationService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.currentUser(req);
        if (!SessionUtil.requireRole(req, "CUSTOMER")) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        try {
            String path = req.getPathInfo() == null ? "/dashboard" : req.getPathInfo();
            req.setAttribute("notifications", notificationService.recent(user.getUserId()));
            req.setAttribute("categories", customerService.categories());
            if ("/search-services".equals(path)) {
                Integer cat = parseInt(req.getParameter("categoryId"));
                Double price = parseDouble(req.getParameter("maxPrice"));
                Double rating = parseDouble(req.getParameter("minRating"));
                req.setAttribute("services", customerService.search(cat, price, rating, req.getParameter("keyword")));
            } else if ("/provider-details".equals(path) || "/book-service".equals(path)) {
                int serviceId = Integer.parseInt(req.getParameter("serviceId"));
                com.quickfix.model.ProviderService service = customerService.serviceDetails(serviceId);
                req.setAttribute("service", service);
                req.setAttribute("profile", customerService.providerProfile(service.getProviderId()));
            } else if ("/booking-history".equals(path) || "/feedback".equals(path)) {
                req.setAttribute("bookings", bookingService.customerBookings(user.getUserId()));
            } else if ("/cancel".equals(path)) {
                bookingService.changeStatus(Integer.parseInt(req.getParameter("bookingId")), "CANCELLED", user.getUserId());
                resp.sendRedirect(req.getContextPath() + "/customer/booking-history"); return;
            }
            forward(req, resp, VIEW_PREFIX + "/customer" + path + ".jsp");
        } catch (Exception e) { throw new ServletException(e); }
    }

    private void forward(HttpServletRequest req, HttpServletResponse resp, String jsp) throws ServletException, IOException {
        req.getRequestDispatcher(jsp).forward(req, resp);
    }
    private Integer parseInt(String v) { try { return v == null || v.isBlank() ? null : Integer.parseInt(v); } catch (Exception e) { return null; } }
    private Double parseDouble(String v) { try { return v == null || v.isBlank() ? null : Double.parseDouble(v); } catch (Exception e) { return null; } }
}
