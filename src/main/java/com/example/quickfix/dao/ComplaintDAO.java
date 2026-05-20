package com.quickfix.dao;

import com.quickfix.model.Complaint;
import com.quickfix.util.DBConnection;
import java.sql.*;
import java.util.*;

public class ComplaintDAO {
    public void create(Complaint cpt) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            boolean modern = hasColumn(c, "complaints", "description");
            String sql = modern
                ? "INSERT INTO complaints(booking_id,raised_by,against_user_id,subject,description) VALUES(?,?,?,?,?)"
                : "INSERT INTO complaints(booking_id,raised_by,complaint_text) VALUES(?,?,?)";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                if (cpt.getBookingId() > 0) ps.setInt(1, cpt.getBookingId()); else ps.setNull(1, Types.INTEGER);
                ps.setInt(2, cpt.getRaisedBy());
                if (modern) {
                    if (cpt.getAgainstUserId() > 0) ps.setInt(3, cpt.getAgainstUserId()); else ps.setNull(3, Types.INTEGER);
                    ps.setString(4, cpt.getSubject());
                    ps.setString(5, cpt.getDescription());
                } else {
                    ps.setString(3, cpt.getSubject() + " - " + cpt.getDescription());
                }
                ps.executeUpdate();
            }
        }
    }
    public void resolve(int complaintId, String status, String response) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            String statusColumn = hasColumn(c, "complaints", "complaint_status") ? "complaint_status" : "status";
            String responseColumn = hasColumn(c, "complaints", "admin_response") ? ", admin_response=?" : "";
            try (PreparedStatement ps = c.prepareStatement("UPDATE complaints SET " + statusColumn + "=?" + responseColumn + " WHERE complaint_id=?")) {
                ps.setString(1, "complaint_status".equals(statusColumn) && "IN_REVIEW".equals(status) ? "UNDER_REVIEW" : status);
                int index = 2;
                if (!responseColumn.isEmpty()) ps.setString(index++, response);
                ps.setInt(index, complaintId);
                ps.executeUpdate();
            }
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
    public List<Complaint> findByRaisedBy(int userId) throws SQLException {
        List<Complaint> list = new ArrayList<>();
        String sql = "SELECT c.*, u.full_name raised_by_name FROM complaints c JOIN users u ON c.raised_by=u.user_id WHERE c.raised_by=? ORDER BY c.created_at DESC";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }
    private Complaint map(ResultSet rs) throws SQLException {
        Complaint c = new Complaint();
        c.setComplaintId(rs.getInt("complaint_id")); c.setBookingId(rs.getInt("booking_id")); c.setRaisedBy(rs.getInt("raised_by"));
        c.setAgainstUserId(getOptionalInt(rs, "against_user_id")); c.setSubject(getOptionalString(rs, "subject", "Complaint")); c.setDescription(getOptionalString(rs, "description", "complaint_text"));
        c.setStatus(getOptionalString(rs, "status", "complaint_status")); c.setAdminResponse(getOptionalString(rs, "admin_response", ""));
        c.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        c.setRaisedByName(rs.getString("raised_by_name")); return c;
    }
    private String getOptionalString(ResultSet rs, String first, String fallback) throws SQLException {
        try { return rs.getString(first); } catch (SQLException e) {
            if (fallback == null || fallback.isEmpty()) return "";
            try { return rs.getString(fallback); } catch (SQLException ignored) { return fallback; }
        }
    }
    private int getOptionalInt(ResultSet rs, String column) {
        try { return rs.getInt(column); } catch (SQLException e) { return 0; }
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
}
