package com.quickfix.dao;

import com.quickfix.model.BookingStatus;
import com.quickfix.util.DBConnection;
import java.sql.*;
import java.util.*;

public class BookingStatusDAO {
    public int idByName(String name) throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement("SELECT status_id FROM booking_statuses WHERE status_name=?")) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? rs.getInt(1) : 0; }
        }
    }
    public List<BookingStatus> findAll() throws SQLException {
        List<BookingStatus> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement("SELECT * FROM booking_statuses"); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BookingStatus s = new BookingStatus(); s.setStatusId(rs.getInt("status_id")); s.setStatusName(rs.getString("status_name")); list.add(s);
            }
        }
        return list;
    }
}
