package com.quickfix.controller;

import com.quickfix.model.User;
import com.quickfix.service.BookingService;
import com.quickfix.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/booking")
public class BookingController extends HttpServlet {
    private final BookingService bookingService = new BookingService();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.currentUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        try {
            bookingService.book(user.getUserId(), Integer.parseInt(req.getParameter("serviceId")),
                    LocalDate.parse(req.getParameter("bookingDate")), LocalTime.parse(req.getParameter("bookingTime")),
                    req.getParameter("notes"));
            resp.sendRedirect(req.getContextPath() + "/customer/booking-history");
        } catch (Exception e) { throw new ServletException(e); }
    }
}
