package com.quickfix.controller;

import com.quickfix.model.*;
import com.quickfix.service.*;
import com.quickfix.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/*")
public class AdminController extends HttpServlet {
    private static final String VIEW_PREFIX = "/WEB-INF/views";
    private final AdminService adminService = new AdminService();
    private final BookingService bookingService = new BookingService();
    private final ReportService reportService = new ReportService();
    private final NotificationService notificationService = new NotificationService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SessionUtil.requireRole(req, "ADMIN")) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        try {
            String path = req.getPathInfo() == null ? "/dashboard" : req.getPathInfo();
            req.setAttribute("users", adminService.users());
            req.setAttribute("providers", adminService.providers());
            req.setAttribute("categories", adminService.categories());
            req.setAttribute("bookings", bookingService.allBookings());
            req.setAttribute("complaints", adminService.complaints());
            req.setAttribute("report", reportService.dashboardReport());
            User user = SessionUtil.currentUser(req);
            if (user != null) {
                req.setAttribute("notifications", notificationService.recent(user.getUserId()));
                req.setAttribute("unreadNotificationCount", notificationService.unreadCount(user.getUserId()));
            }
            req.getRequestDispatcher(VIEW_PREFIX + "/admin" + path + ".jsp").forward(req, resp);
        } catch (Exception e) { throw new ServletException(e); }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SessionUtil.requireRole(req, "ADMIN")) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        try {
            String action = req.getParameter("action");
            User user = SessionUtil.currentUser(req);
            if ("markNotificationsRead".equals(action) && user != null) notificationService.markAllRead(user.getUserId());
            if ("userStatus".equals(action)) adminService.userStatus(Integer.parseInt(req.getParameter("userId")), req.getParameter("status"));
            if ("providerVerification".equals(action)) adminService.providerVerification(Integer.parseInt(req.getParameter("providerId")), req.getParameter("status"));
            if ("category".equals(action)) {
                ServiceCategory c = new ServiceCategory();
                c.setCategoryId(parseInt(req.getParameter("categoryId"))); c.setCategoryName(req.getParameter("categoryName"));
                c.setDescription(req.getParameter("description")); c.setActive("on".equals(req.getParameter("active")));
                adminService.saveCategory(c);
            }
            if ("complaint".equals(action)) adminService.resolveComplaint(Integer.parseInt(req.getParameter("complaintId")), req.getParameter("status"), req.getParameter("adminResponse"));
            resp.sendRedirect(req.getHeader("Referer") == null ? req.getContextPath() + "/admin/dashboard" : req.getHeader("Referer"));
        } catch (Exception e) { throw new ServletException(e); }
    }
    private int parseInt(String value) { try { return value == null || value.isBlank() ? 0 : Integer.parseInt(value); } catch (Exception e) { return 0; } }
}
