package com.quickfix.controller;

import com.quickfix.service.CustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/index", "", "/services", "/coverage", "/help-desk"})
public class HomeController extends HttpServlet {
    private final CustomerService customerService = new CustomerService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/coverage".equals(path) || "/help-desk".equals(path)) {
            req.getRequestDispatcher("/WEB-INF/views" + path + ".jsp").forward(req, resp);
            return;
        }
        try {
            req.setAttribute("categories", customerService.categories());
            req.setAttribute("recommendedServices", customerService.search(
                    parseInt(req.getParameter("categoryId")),
                    parseDouble(req.getParameter("maxPrice")),
                    parseDouble(req.getParameter("minRating")),
                    req.getParameter("keyword")
            ));
        } catch (Exception e) {
            req.setAttribute("homeDataError", "Live service data is currently unavailable.");
        }
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
    }

    private Integer parseInt(String value) {
        try {
            return value == null || value.isBlank() ? null : Integer.parseInt(value);
        } catch (Exception e) {
            return null;
        }
    }

    private Double parseDouble(String value) {
        try {
            return value == null || value.isBlank() ? null : Double.parseDouble(value);
        } catch (Exception e) {
            return null;
        }
    }
}
