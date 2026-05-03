package com.quickfix.controller;

import com.quickfix.dao.RatingFeedbackDAO;
import com.quickfix.model.*;
import com.quickfix.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/feedback")
public class FeedbackController extends HttpServlet {
    private final RatingFeedbackDAO feedbackDAO = new RatingFeedbackDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.currentUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        try {
            RatingFeedback f = new RatingFeedback();
            f.setBookingId(Integer.parseInt(req.getParameter("bookingId"))); f.setCustomerId(user.getUserId());
            f.setProviderId(Integer.parseInt(req.getParameter("providerId"))); f.setRating(Integer.parseInt(req.getParameter("rating"))); f.setComments(req.getParameter("comments"));
            feedbackDAO.create(f);
            resp.sendRedirect(req.getContextPath() + "/customer/booking-history");
        } catch (Exception e) { throw new ServletException(e); }
    }
}
