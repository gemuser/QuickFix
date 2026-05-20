package com.quickfix.controller;

import com.quickfix.model.User;
import com.quickfix.service.BookingService;
import com.quickfix.service.CustomerService;
import com.quickfix.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/booking")
public class BookingController extends HttpServlet {
    private static final String VIEW_PREFIX = "/WEB-INF/views";
    private final BookingService bookingService = new BookingService();
    private final CustomerService customerService = new CustomerService();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.currentUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        try {
            bookingService.book(user.getUserId(), Integer.parseInt(req.getParameter("serviceId")),
                    LocalDate.parse(req.getParameter("bookingDate")), LocalTime.parse(req.getParameter("bookingTime")),
                    req.getParameter("address"), req.getParameter("notes"));
            resp.sendRedirect(req.getContextPath() + "/customer/booking-history");
        } catch (IllegalArgumentException e) {
            try {
                int serviceId = Integer.parseInt(req.getParameter("serviceId"));
                com.quickfix.model.ProviderService service = customerService.serviceDetails(serviceId);
                req.setAttribute("service", service);
                req.setAttribute("profile", customerService.providerProfile(service.getProviderId()));
            } catch (Exception ignored) { }
            if ("Address is required".equals(e.getMessage())) req.setAttribute("addressError", e.getMessage());
            else req.setAttribute("bookingError", e.getMessage());
            req.getRequestDispatcher(VIEW_PREFIX + "/customer/book-service.jsp").forward(req, resp);
        } catch (Exception e) { throw new ServletException(e); }
    }
}
