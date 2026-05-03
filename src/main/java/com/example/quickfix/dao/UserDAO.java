package com.quickfix.dao;

import com.quickfix.model.User;
import com.quickfix.util.DBConnection;
import java.sql.*;
import java.util.*;

public class UserDAO {
    private static volatile String cachedStatusColumn;

    public User findByEmail(String email) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            String statusColumn = resolveStatusColumn(c);
            String sql = "SELECT u.*, u." + statusColumn + " AS account_status, r.role_name FROM users u JOIN roles r ON u.role_id=r.role_id WHERE u.email=?";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
            }
        }
    }
    public User findById(int id) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            String statusColumn = resolveStatusColumn(c);
            String sql = "SELECT u.*, u." + statusColumn + " AS account_status, r.role_name FROM users u JOIN roles r ON u.role_id=r.role_id WHERE u.user_id=?";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
            }
        }
    }
    public User findByPhone(String phone) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            String statusColumn = resolveStatusColumn(c);
            String sql = "SELECT u.*, u." + statusColumn + " AS account_status, r.role_name FROM users u JOIN roles r ON u.role_id=r.role_id WHERE u.phone=?";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, phone);
                try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
            }
        }
    }
    public int create(User user) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            String statusColumn = resolveStatusColumn(c);
            String sql = "INSERT INTO users(role_id, full_name, email, phone, password_hash, " + statusColumn + ") VALUES(?,?,?,?,?,?)";
            try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, user.getRoleId());
                ps.setString(2, user.getFullName());
                ps.setString(3, user.getEmail());
                ps.setString(4, user.getPhone());
                ps.setString(5, user.getPasswordHash());
                ps.setString(6, user.getStatus());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) { return rs.next() ? rs.getInt(1) : 0; }
            }
        }
    }
    public void updateStatus(int userId, String status) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            String statusColumn = resolveStatusColumn(c);
            try (PreparedStatement ps = c.prepareStatement("UPDATE users SET " + statusColumn + "=? WHERE user_id=?")) {
                ps.setString(1, status); ps.setInt(2, userId); ps.executeUpdate();
            }
        }
    }
    public List<User> findAll() throws SQLException {
        List<User> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            String statusColumn = resolveStatusColumn(c);
            String sql = "SELECT u.*, u." + statusColumn + " AS account_status, r.role_name FROM users u JOIN roles r ON u.role_id=r.role_id ORDER BY u.created_at DESC";
            try (PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }
    public int countAll() throws SQLException { return count("SELECT COUNT(*) FROM users"); }
    public int countActiveProviders() throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            String statusColumn = resolveStatusColumn(c);
            String sql = "SELECT COUNT(*) FROM users u JOIN roles r ON u.role_id=r.role_id WHERE r.role_name='PROVIDER' AND u." + statusColumn + "='ACTIVE'";
            try (PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }
    private int count(String sql) throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
    private User map(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setRoleId(rs.getInt("role_id"));
        u.setFullName(rs.getString("full_name"));
        u.setEmail(rs.getString("email"));
        u.setPhone(rs.getString("phone"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setStatus(rs.getString("account_status"));
        u.setRoleName(rs.getString("role_name"));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) u.setCreatedAt(ts.toLocalDateTime());
        return u;
    }
    private String resolveStatusColumn(Connection c) throws SQLException {
        if (cachedStatusColumn != null) return cachedStatusColumn;
        if (hasColumn(c, "account_status")) {
            cachedStatusColumn = "account_status";
            return cachedStatusColumn;
        }
        if (hasColumn(c, "status")) {
            cachedStatusColumn = "status";
            return cachedStatusColumn;
        }
        throw new SQLException("Neither account_status nor status column exists in users table.");
    }
    private boolean hasColumn(Connection c, String columnName) throws SQLException {
        DatabaseMetaData meta = c.getMetaData();
        try (ResultSet rs = meta.getColumns(c.getCatalog(), null, "users", columnName)) {
            if (rs.next()) return true;
        }
        try (ResultSet rs = meta.getColumns(c.getCatalog(), null, "USERS", columnName.toUpperCase(Locale.ROOT))) {
            return rs.next();
        }
    }
}
