package com.quickfix.controller;

import com.quickfix.dao.ComplaintDAO;
import com.quickfix.model.*;
import com.quickfix.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/complaint")
public class ComplaintController extends HttpServlet {
    private final ComplaintDAO complaintDAO = new ComplaintDAO();
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionUtil.currentUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        try {
            String description = req.getParameter("description");
            if (description == null || description.trim().isEmpty()) throw new IllegalArgumentException("Complaint description cannot be empty.");
            Complaint c = new Complaint();
            c.setRaisedBy(user.getUserId()); c.setBookingId(parseInt(req.getParameter("bookingId"))); c.setAgainstUserId(parseInt(req.getParameter("againstUserId")));
            c.setSubject(req.getParameter("subject") == null || req.getParameter("subject").isBlank() ? "Complaint" : req.getParameter("subject").trim()); c.setDescription(description.trim()); complaintDAO.create(c);
            resp.sendRedirect(req.getHeader("Referer") == null ? req.getContextPath() + "/" : req.getHeader("Referer"));
        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("complaintError", e.getMessage());
            resp.sendRedirect(req.getHeader("Referer") == null ? req.getContextPath() + "/" : req.getHeader("Referer"));
        } catch (Exception e) { throw new ServletException(e); }
    }
    private int parseInt(String value) { try { return value == null || value.isBlank() ? 0 : Integer.parseInt(value); } catch (Exception e) { return 0; } }
}
