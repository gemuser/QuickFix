package com.quickfix.dao;

import com.quickfix.model.ServiceCategory;
import com.quickfix.util.DBConnection;
import java.sql.*;
import java.util.*;

public class ServiceCategoryDAO {
    public List<ServiceCategory> findAll() throws SQLException {
        List<ServiceCategory> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement("SELECT * FROM service_categories ORDER BY category_name"); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }
    public void save(ServiceCategory cat) throws SQLException {
        String sql = cat.getCategoryId() > 0
            ? "UPDATE service_categories SET category_name=?, description=? WHERE category_id=?"
            : "INSERT INTO service_categories(category_name,description) VALUES(?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cat.getCategoryName()); ps.setString(2, cat.getDescription());
            if (cat.getCategoryId() > 0) ps.setInt(3, cat.getCategoryId());
            ps.executeUpdate();
        }
    }
    private ServiceCategory map(ResultSet rs) throws SQLException {
        ServiceCategory c = new ServiceCategory();
        c.setCategoryId(rs.getInt("category_id")); c.setCategoryName(rs.getString("category_name"));
        c.setDescription(rs.getString("description")); c.setActive(true);
        return c;
    }
}
