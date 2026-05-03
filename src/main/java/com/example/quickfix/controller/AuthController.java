package com.quickfix.controller;

import com.quickfix.model.User;
import com.quickfix.service.AuthService;
import com.quickfix.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

@WebServlet({"/login", "/register", "/logout"})
public class AuthController extends HttpServlet {
    private static final String VIEW_PREFIX = "/WEB-INF/views";
    private final AuthService authService = new AuthService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/logout".equals(path)) {
            SessionUtil.logout(req);
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            req.getRequestDispatcher(VIEW_PREFIX + path + ".jsp").forward(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/register".equals(path)) {
            handleRegister(req, resp);
            return;
        }
        handleLogin(req, resp);
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String error = authService.register(req.getParameter("role"), req.getParameter("fullName"), req.getParameter("email"), req.getParameter("phone"), req.getParameter("password"));
            if (error != null) {
                populateRegisterForm(req);
                req.setAttribute("error", error);
                req.getRequestDispatcher(VIEW_PREFIX + "/register.jsp").forward(req, resp);
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/login?registered=1");
        } catch (SQLException e) {
            populateRegisterForm(req);
            req.setAttribute("error", "Database error while creating account. Please try again.");
            req.getRequestDispatcher(VIEW_PREFIX + "/register.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Unexpected registration error", e);
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = authService.login(req.getParameter("email"), req.getParameter("password"));
            if (user == null) {
                req.setAttribute("error", "Invalid credentials or inactive account.");
                req.setAttribute("email", normalizeEmail(req.getParameter("email")));
                req.getRequestDispatcher(VIEW_PREFIX + "/login.jsp").forward(req, resp);
                return;
            }
            SessionUtil.login(req, user);
            resp.sendRedirect(req.getContextPath() + "/" + user.getRoleName().toLowerCase(Locale.ROOT) + "/dashboard");
        } catch (SQLException e) {
            req.setAttribute("error", "Database error while signing in. Please try again.");
            req.setAttribute("email", normalizeEmail(req.getParameter("email")));
            req.getRequestDispatcher(VIEW_PREFIX + "/login.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Unexpected login error", e);
        }
    }

    private void populateRegisterForm(HttpServletRequest req) {
        req.setAttribute("role", normalizeRole(req.getParameter("role")));
        req.setAttribute("fullName", req.getParameter("fullName") == null ? "" : req.getParameter("fullName").trim());
        req.setAttribute("email", normalizeEmail(req.getParameter("email")));
        req.setAttribute("phone", req.getParameter("phone") == null ? "" : req.getParameter("phone").trim());
    }

    private String normalizeRole(String role) {
        if (role == null) return "CUSTOMER";
        String value = role.trim().toUpperCase(Locale.ROOT);
        return "PROVIDER".equals(value) ? "PROVIDER" : "CUSTOMER";
    }

    private String normalizeEmail(String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }
}
