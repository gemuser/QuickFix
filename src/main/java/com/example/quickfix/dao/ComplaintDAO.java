package com.quickfix.dao;

import com.quickfix.model.Complaint;
import com.quickfix.util.DBConnection;
import java.sql.*;
import java.util.*;

public class ComplaintDAO {
    public void create(Complaint cpt) throws SQLException {
        String sql = "INSERT INTO complaints(booking_id,raised_by,complaint_text) VALUES(?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            if (cpt.getBookingId() > 0) ps.setInt(1, cpt.getBookingId()); else ps.setNull(1, Types.INTEGER);
            ps.setInt(2, cpt.getRaisedBy());
            ps.setString(3, cpt.getSubject() + " - " + cpt.getDescription()); ps.executeUpdate();
        }
    }
    public void resolve(int complaintId, String status, String response) throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement("UPDATE complaints SET complaint_status=? WHERE complaint_id=?")) {
            ps.setString(1, "IN_REVIEW".equals(status) ? "UNDER_REVIEW" : status); ps.setInt(2, complaintId); ps.executeUpdate();
        }
    }
    public List<Complaint> findAll() throws SQLException {
        List<Complaint> list = new ArrayList<>();
        String sql = "SELECT c.*, u.full_name raised_by_name FROM complaints c JOIN users u ON c.raised_by=u.user_id ORDER BY c.created_at DESC";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }
    private Complaint map(ResultSet rs) throws SQLException {
        Complaint c = new Complaint();
        c.setComplaintId(rs.getInt("complaint_id")); c.setBookingId(rs.getInt("booking_id")); c.setRaisedBy(rs.getInt("raised_by"));
        c.setAgainstUserId(0); c.setSubject("Complaint"); c.setDescription(rs.getString("complaint_text"));
        c.setStatus(rs.getString("complaint_status")); c.setAdminResponse(""); c.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        c.setRaisedByName(rs.getString("raised_by_name")); return c;
    }
}
