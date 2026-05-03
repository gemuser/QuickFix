package com.quickfix.dao;

import com.quickfix.model.BookingStatusHistory;
import com.quickfix.util.DBConnection;
import java.sql.*;
import java.util.*;

public class BookingStatusHistoryDAO {
    public void create(int bookingId, int statusId, int changedBy, String note) throws SQLException {
        String sql = "INSERT INTO booking_status_history(booking_id,status_id,changed_by) VALUES(?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, bookingId); ps.setInt(2, statusId); ps.setInt(3, changedBy); ps.executeUpdate();
        }
    }
    public List<BookingStatusHistory> findByBooking(int bookingId) throws SQLException {
        List<BookingStatusHistory> list = new ArrayList<>();
        String sql = "SELECT h.*, bs.status_name FROM booking_status_history h JOIN booking_statuses bs ON h.status_id=bs.status_id WHERE h.booking_id=? ORDER BY h.changed_at DESC";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BookingStatusHistory h = new BookingStatusHistory();
                    h.setHistoryId(rs.getInt("history_id")); h.setBookingId(rs.getInt("booking_id")); h.setStatusId(rs.getInt("status_id"));
                    h.setChangedBy(rs.getInt("changed_by")); h.setChangedAt(rs.getTimestamp("changed_at").toLocalDateTime());
                    h.setNote(""); h.setStatusName(rs.getString("status_name")); list.add(h);
                }
            }
        }
        return list;
    }
}
