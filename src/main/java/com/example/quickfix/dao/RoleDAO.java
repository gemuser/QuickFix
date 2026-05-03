package com.quickfix.dao;

import com.quickfix.model.Role;
import com.quickfix.util.DBConnection;
import java.sql.*;
import java.util.*;

public class RoleDAO {
    public int findIdByName(String roleName) throws SQLException {
        String sql = "SELECT role_id FROM roles WHERE UPPER(role_name) = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, roleName == null ? "" : roleName.trim().toUpperCase(Locale.ROOT));
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? rs.getInt(1) : 0; }
        }
    }
    public List<Role> findAll() throws SQLException {
        List<Role> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement("SELECT * FROM roles ORDER BY role_name"); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Role r = new Role();
                r.setRoleId(rs.getInt("role_id"));
                r.setRoleName(rs.getString("role_name"));
                list.add(r);
            }
        }
        return list;
    }
}
