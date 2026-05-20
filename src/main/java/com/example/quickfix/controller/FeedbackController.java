package com.quickfix.controller;

import com.quickfix.dao.RatingFeedbackDAO;
import com.quickfix.dao.BookingDAO;
import com.quickfix.model.*;
import com.quickfix.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet("/feedback")
public class FeedbackController extends HttpServlet {
    private final RatingFeedbackDAO feedbackDAO = new RatingFeedbackDAO();
    private final BookingDAO bookingDAO = new BookingDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.currentUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        try {
            String ratingValue = req.getParameter("rating");
            if (ratingValue == null || ratingValue.isBlank()) throw new IllegalArgumentException("Please select a star rating.");
            int bookingId = Integer.parseInt(req.getParameter("bookingId"));
            Booking booking = bookingDAO.findById(bookingId);
            if (booking == null || booking.getCustomerId() != user.getUserId() || !"COMPLETED".equals(booking.getStatusName())) {
                throw new IllegalArgumentException("Only completed bookings can be reviewed.");
            }
            if (feedbackDAO.existsForBooking(bookingId)) throw new IllegalArgumentException("You have already reviewed this service.");
            RatingFeedback f = new RatingFeedback();
            f.setBookingId(bookingId); f.setCustomerId(user.getUserId());
            f.setProviderId(booking.getProviderId()); f.setRating(Integer.parseInt(ratingValue)); f.setComments(req.getParameter("comments"));
            feedbackDAO.create(f);
            resp.sendRedirect(req.getContextPath() + "/customer/booking-history");
        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("feedbackError", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/customer/feedback");
        } catch (SQLIntegrityConstraintViolationException e) {
            req.getSession().setAttribute("feedbackError", "You have already reviewed this service.");
            resp.sendRedirect(req.getContextPath() + "/customer/feedback");
        } catch (Exception e) { throw new ServletException(e); }
    }
}
