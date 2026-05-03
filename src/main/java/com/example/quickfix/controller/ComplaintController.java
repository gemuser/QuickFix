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
            Complaint c = new Complaint();
            c.setRaisedBy(user.getUserId()); c.setBookingId(parseInt(req.getParameter("bookingId"))); c.setAgainstUserId(parseInt(req.getParameter("againstUserId")));
            c.setSubject(req.getParameter("subject")); c.setDescription(req.getParameter("description")); complaintDAO.create(c);
            resp.sendRedirect(req.getHeader("Referer") == null ? req.getContextPath() + "/" : req.getHeader("Referer"));
        } catch (Exception e) { throw new ServletException(e); }
    }
    private int parseInt(String value) { try { return value == null || value.isBlank() ? 0 : Integer.parseInt(value); } catch (Exception e) { return 0; } }
}
