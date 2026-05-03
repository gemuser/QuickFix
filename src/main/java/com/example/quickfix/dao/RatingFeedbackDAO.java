package com.quickfix.dao;

import com.quickfix.model.RatingFeedback;
import com.quickfix.util.DBConnection;
import java.sql.*;
import java.util.*;

public class RatingFeedbackDAO {
    public void create(RatingFeedback f) throws SQLException {
        String sql = "INSERT INTO ratings_feedback(booking_id,customer_id,provider_id,rating,feedback_text) VALUES(?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, f.getBookingId()); ps.setInt(2, f.getCustomerId()); ps.setInt(3, f.getProviderId()); ps.setInt(4, f.getRating()); ps.setString(5, f.getComments()); ps.executeUpdate();
        }
    }
    public List<RatingFeedback> findByProvider(int providerId) throws SQLException {
        List<RatingFeedback> list = new ArrayList<>();
        String sql = "SELECT rf.*, cu.full_name customer_name, pu.full_name provider_name FROM ratings_feedback rf JOIN users cu ON rf.customer_id=cu.user_id JOIN users pu ON rf.provider_id=pu.user_id WHERE rf.provider_id=? ORDER BY rf.created_at DESC";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, providerId);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        }
        return list;
    }
    private RatingFeedback map(ResultSet rs) throws SQLException {
        RatingFeedback f = new RatingFeedback();
        f.setFeedbackId(rs.getInt("feedback_id")); f.setBookingId(rs.getInt("booking_id")); f.setCustomerId(rs.getInt("customer_id"));
        f.setProviderId(rs.getInt("provider_id")); f.setRating(rs.getInt("rating")); f.setComments(rs.getString("feedback_text"));
        f.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime()); f.setCustomerName(rs.getString("customer_name")); f.setProviderName(rs.getString("provider_name"));
        return f;
    }
}
