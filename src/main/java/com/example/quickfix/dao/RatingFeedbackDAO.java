package com.quickfix.dao;

import com.quickfix.model.RatingFeedback;
import com.quickfix.util.DBConnection;
import java.sql.*;
import java.util.*;

public class RatingFeedbackDAO {
    public void create(RatingFeedback f) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            String commentsColumn = commentsColumn(c);
            String sql = "INSERT INTO ratings_feedback(booking_id,customer_id,provider_id,rating," + commentsColumn + ") VALUES(?,?,?,?,?)";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, f.getBookingId()); ps.setInt(2, f.getCustomerId()); ps.setInt(3, toStoredProviderId(c, f.getProviderId())); ps.setInt(4, f.getRating()); ps.setString(5, f.getComments()); ps.executeUpdate();
            }
        }
    }
    public boolean existsForBooking(int bookingId) throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM ratings_feedback WHERE booking_id=?")) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() && rs.getInt(1) > 0; }
        }
    }
    public List<RatingFeedback> findByProvider(int providerId) throws SQLException {
        List<RatingFeedback> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            String sql = ratingsFeedbackUsesProfileId(c)
                ? "SELECT rf.*, pp.user_id AS provider_user_id, cu.full_name customer_name, pu.full_name provider_name FROM ratings_feedback rf JOIN users cu ON rf.customer_id=cu.user_id JOIN provider_profiles pp ON rf.provider_id=pp." + resolveProfileIdColumn(c) + " JOIN users pu ON pp.user_id=pu.user_id WHERE pp.user_id=? ORDER BY rf.created_at DESC"
                : "SELECT rf.*, rf.provider_id AS provider_user_id, cu.full_name customer_name, pu.full_name provider_name FROM ratings_feedback rf JOIN users cu ON rf.customer_id=cu.user_id JOIN users pu ON rf.provider_id=pu.user_id WHERE rf.provider_id=? ORDER BY rf.created_at DESC";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, providerId);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
            }
        }
        return list;
    }

    public List<RatingFeedback> findByCustomer(int customerId) throws SQLException {
        List<RatingFeedback> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            String sql = ratingsFeedbackUsesProfileId(c)
                ? "SELECT rf.*, pp.user_id AS provider_user_id, cu.full_name customer_name, pu.full_name provider_name FROM ratings_feedback rf JOIN users cu ON rf.customer_id=cu.user_id JOIN provider_profiles pp ON rf.provider_id=pp." + resolveProfileIdColumn(c) + " JOIN users pu ON pp.user_id=pu.user_id WHERE rf.customer_id=? ORDER BY rf.created_at DESC"
                : "SELECT rf.*, rf.provider_id AS provider_user_id, cu.full_name customer_name, pu.full_name provider_name FROM ratings_feedback rf JOIN users cu ON rf.customer_id=cu.user_id JOIN users pu ON rf.provider_id=pu.user_id WHERE rf.customer_id=? ORDER BY rf.created_at DESC";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, customerId);
                try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
            }
        }
        return list;
    }

    public Map<Integer, Boolean> reviewedBookingsByCustomer(int customerId) throws SQLException {
        Map<Integer, Boolean> reviewedBookings = new HashMap<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement("SELECT booking_id FROM ratings_feedback WHERE customer_id=?")) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) reviewedBookings.put(rs.getInt("booking_id"), Boolean.TRUE);
            }
        }
        return reviewedBookings;
    }

    private RatingFeedback map(ResultSet rs) throws SQLException {
        RatingFeedback f = new RatingFeedback();
        f.setFeedbackId(rs.getInt("feedback_id")); f.setBookingId(rs.getInt("booking_id")); f.setCustomerId(rs.getInt("customer_id"));
        f.setProviderId(rs.getInt("provider_user_id")); f.setRating(rs.getInt("rating")); f.setComments(getOptionalString(rs, "feedback_text", "comments"));
        f.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime()); f.setCustomerName(rs.getString("customer_name")); f.setProviderName(rs.getString("provider_name"));
        return f;
    }
    private String commentsColumn(Connection c) throws SQLException { return hasColumn(c, "ratings_feedback", "feedback_text") ? "feedback_text" : "comments"; }
    private String getOptionalString(ResultSet rs, String first, String fallback) throws SQLException {
        try { return rs.getString(first); } catch (SQLException e) { return rs.getString(fallback); }
    }
    private boolean hasColumn(Connection c, String tableName, String columnName) throws SQLException {
        DatabaseMetaData meta = c.getMetaData();
        try (ResultSet rs = meta.getColumns(c.getCatalog(), null, tableName, columnName)) {
            if (rs.next()) return true;
        }
        try (ResultSet rs = meta.getColumns(c.getCatalog(), null, tableName.toUpperCase(Locale.ROOT), columnName.toUpperCase(Locale.ROOT))) {
            return rs.next();
        }
    }

    private int toStoredProviderId(Connection c, int userId) throws SQLException {
        if (!ratingsFeedbackUsesProfileId(c)) return userId;
        String profileIdColumn = resolveProfileIdColumn(c);
        try (PreparedStatement ps = c.prepareStatement("SELECT " + profileIdColumn + " FROM provider_profiles WHERE user_id=?")) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        throw new SQLException("Provider profile not found for user_id " + userId + ".");
    }

    private boolean ratingsFeedbackUsesProfileId(Connection c) throws SQLException {
        DatabaseMetaData meta = c.getMetaData();
        try (ResultSet rs = meta.getImportedKeys(c.getCatalog(), null, "ratings_feedback")) {
            while (rs.next()) {
                if ("provider_id".equalsIgnoreCase(rs.getString("FKCOLUMN_NAME"))) {
                    return "provider_profiles".equalsIgnoreCase(rs.getString("PKTABLE_NAME"));
                }
            }
        }
        try (ResultSet rs = meta.getImportedKeys(c.getCatalog(), null, "RATINGS_FEEDBACK")) {
            while (rs.next()) {
                if ("PROVIDER_ID".equalsIgnoreCase(rs.getString("FKCOLUMN_NAME"))) {
                    return "PROVIDER_PROFILES".equalsIgnoreCase(rs.getString("PKTABLE_NAME"));
                }
            }
        }
        return hasColumn(c, "provider_profiles", "provider_id") && !hasColumn(c, "provider_profiles", "profile_id");
    }

    private String resolveProfileIdColumn(Connection c) throws SQLException {
        if (hasColumn(c, "provider_profiles", "profile_id")) return "profile_id";
        if (hasColumn(c, "provider_profiles", "provider_id")) return "provider_id";
        throw new SQLException("Neither profile_id nor provider_id exists in provider_profiles table.");
    }
}
