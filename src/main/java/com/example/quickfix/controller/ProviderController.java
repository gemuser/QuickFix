package com.quickfix.controller;

import com.quickfix.model.*;
import com.quickfix.service.BookingService;
import com.quickfix.service.NotificationService;
import com.quickfix.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/provider/*")
public class ProviderController extends HttpServlet {
    private static final String VIEW_PREFIX = "/WEB-INF/views";
    private final com.quickfix.service.ProviderService providerService = new com.quickfix.service.ProviderService();
    private final BookingService bookingService = new BookingService();
    private final NotificationService notificationService = new NotificationService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.currentUser(req);
        if (!SessionUtil.requireRole(req, "PROVIDER")) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        try {
            String path = req.getPathInfo() == null ? "/dashboard" : req.getPathInfo();
            req.setAttribute("profile", providerService.profile(user.getUserId()));
            req.setAttribute("services", providerService.services(user.getUserId()));
            req.setAttribute("categories", providerService.categories());
            req.setAttribute("availability", providerService.availability(user.getUserId()));
            req.setAttribute("bookings", bookingService.providerBookings(user.getUserId()));
            req.setAttribute("notifications", notificationService.recent(user.getUserId()));
            req.setAttribute("unreadNotificationCount", notificationService.unreadCount(user.getUserId()));
            req.getRequestDispatcher(VIEW_PREFIX + "/provider" + path + ".jsp").forward(req, resp);
        } catch (Exception e) { throw new ServletException(e); }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.currentUser(req);
        if (!SessionUtil.requireRole(req, "PROVIDER")) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        try {
            String action = req.getParameter("action");
            if ("markNotificationsRead".equals(action)) {
                notificationService.markAllRead(user.getUserId());
            } else if ("profile".equals(action)) {
                ProviderProfile p = new ProviderProfile();
                p.setUserId(user.getUserId()); p.setBio(req.getParameter("bio")); p.setExperienceYears(Integer.parseInt(req.getParameter("experienceYears"))); p.setVerificationStatus("PENDING");
                providerService.saveProfile(p);
            } else if ("service".equals(action)) {
                com.quickfix.model.ProviderService s = new com.quickfix.model.ProviderService();
                s.setServiceId(parseInt(req.getParameter("serviceId"))); s.setProviderId(user.getUserId()); s.setCategoryId(Integer.parseInt(req.getParameter("categoryId")));
                s.setServiceTitle(req.getParameter("serviceTitle")); s.setDescription(req.getParameter("description")); s.setPrice(new BigDecimal(req.getParameter("price"))); s.setActive(true);
                providerService.saveService(s);
            } else if ("updateServiceRate".equals(action)) {
                providerService.updateServiceRate(Integer.parseInt(req.getParameter("serviceId")), user.getUserId(), new BigDecimal(req.getParameter("price")));
            } else if ("deleteService".equals(action)) {
                providerService.deleteService(Integer.parseInt(req.getParameter("serviceId")), user.getUserId());
            } else if ("availability".equals(action)) {
                ProviderAvailability a = new ProviderAvailability();
                a.setProviderId(user.getUserId()); a.setAvailableDate(LocalDate.parse(req.getParameter("availableDate")));
                a.setStartTime(LocalTime.parse(req.getParameter("startTime"))); a.setEndTime(LocalTime.parse(req.getParameter("endTime"))); a.setAvailable(true);
                providerService.addAvailability(a);
            } else if ("deleteAvailability".equals(action)) {
                providerService.deleteAvailability(Integer.parseInt(req.getParameter("availabilityId")), user.getUserId());
            } else if ("bookingStatus".equals(action)) {
                bookingService.changeStatus(Integer.parseInt(req.getParameter("bookingId")), req.getParameter("status"), user.getUserId());
            }
            resp.sendRedirect(req.getHeader("Referer") == null ? req.getContextPath() + "/provider/dashboard" : req.getHeader("Referer"));
        } catch (IllegalArgumentException e) {
            try {
                req.setAttribute("profile", providerService.profile(user.getUserId()));
                req.setAttribute("services", providerService.services(user.getUserId()));
                req.setAttribute("categories", providerService.categories());
                req.setAttribute("availability", providerService.availability(user.getUserId()));
                req.setAttribute("bookings", bookingService.providerBookings(user.getUserId()));
                req.setAttribute("notifications", notificationService.recent(user.getUserId()));
                req.setAttribute("unreadNotificationCount", notificationService.unreadCount(user.getUserId()));
            } catch (Exception ignored) { }
            req.setAttribute("error", e.getMessage());
            String referer = req.getHeader("Referer");
            String view = "/dashboard";
            if (referer != null && referer.contains("/manage-profile")) view = "/manage-profile";
            else if (referer != null && referer.contains("/manage-services")) view = "/manage-services";
            else if (referer != null && referer.contains("/availability")) view = "/availability";
            else if (referer != null && referer.contains("/booking-requests")) view = "/booking-requests";
            req.getRequestDispatcher(VIEW_PREFIX + "/provider" + view + ".jsp").forward(req, resp);
        } catch (Exception e) { throw new ServletException(e); }
    }
    private int parseInt(String value) { try { return value == null || value.isBlank() ? 0 : Integer.parseInt(value); } catch (Exception e) { return 0; } }
}
