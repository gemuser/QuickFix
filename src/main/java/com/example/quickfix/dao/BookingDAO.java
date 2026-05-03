package com.quickfix.dao;

import com.quickfix.model.Booking;
import com.quickfix.util.DBConnection;
import java.sql.*;
import java.util.*;

public class BookingDAO {
    public int create(Booking b) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            boolean hasProviderColumn = hasColumn(c, "bookings", "provider_id");
            String serviceColumn = resolveBookingServiceColumn(c);
            String notesColumn = resolveNotesColumn(c);
            String sql = hasProviderColumn
                ? "INSERT INTO bookings(customer_id,provider_id," + serviceColumn + ",address_id,status_id,booking_date,booking_time," + notesColumn + ") VALUES(?,?,?,?,?,?,?,?)"
                : "INSERT INTO bookings(customer_id," + serviceColumn + ",address_id,status_id,booking_date,booking_time," + notesColumn + ") VALUES(?,?,?,?,?,?,?)";
            try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setInt(i++, b.getCustomerId());
            if (hasProviderColumn) ps.setInt(i++, b.getProviderId());
            ps.setInt(i++, b.getServiceId());
            if (b.getAddressId() > 0) ps.setInt(i++, b.getAddressId()); else ps.setNull(i++, Types.INTEGER);
            ps.setInt(i++, b.getStatusId());
            ps.setDate(i++, java.sql.Date.valueOf(b.getBookingDate())); ps.setTime(i++, Time.valueOf(b.getBookingTime()));
            ps.setString(i, b.getNotes()); ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { return rs.next() ? rs.getInt(1) : 0; }
            }
        }
    }
    public void updateStatus(int bookingId, int statusId) throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement("UPDATE bookings SET status_id=? WHERE booking_id=?")) {
            ps.setInt(1, statusId); ps.setInt(2, bookingId); ps.executeUpdate();
        }
    }
    public Booking findById(int id) throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(baseSql(c) + " WHERE b.booking_id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }
    public List<Booking> findByCustomer(int customerId) throws SQLException {
        try (Connection c = DBConnection.getConnection()) { return list(c, baseSql(c) + " WHERE b.customer_id=? ORDER BY b.created_at DESC", customerId); }
    }
    public List<Booking> findByProvider(int providerId) throws SQLException {
        try (Connection c = DBConnection.getConnection()) { return list(c, baseSql(c) + " WHERE " + providerWhereColumn(c) + "=? ORDER BY b.created_at DESC", providerId); }
    }
    public List<Booking> findAll() throws SQLException {
        List<Booking> rows = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(baseSql(c) + " ORDER BY b.created_at DESC"); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) rows.add(map(rs));
        }
        return rows;
    }
    public int countAll() throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM bookings"); ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
    private List<Booking> list(Connection c, String sql, int id) throws SQLException {
        List<Booking> rows = new ArrayList<>();
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) rows.add(map(rs)); }
        }
        return rows;
    }
    private String baseSql(Connection c) throws SQLException {
        String providerServiceIdColumn = hasColumn(c, "provider_services", "service_id") ? "service_id" : "provider_service_id";
        String bookingServiceColumn = resolveBookingServiceColumn(c);
        String notesColumn = resolveNotesColumn(c);
        boolean servicesUseProfileId = providerServicesUseProfileId(c);
        String providerJoin = hasColumn(c, "bookings", "provider_id")
            ? "JOIN users pu ON b.provider_id=pu.user_id"
            : servicesUseProfileId
                ? "JOIN provider_profiles pp ON ps.provider_id=pp.provider_id JOIN users pu ON pp.user_id=pu.user_id"
                : "JOIN users pu ON ps.provider_id=pu.user_id";
        String providerSelect = hasColumn(c, "bookings", "provider_id") ? "b.provider_id" : servicesUseProfileId ? "pp.user_id" : "ps.provider_id";
        return "SELECT b.*, b." + bookingServiceColumn + " AS service_id, " + providerSelect + " AS provider_id, b." + notesColumn + " AS notes, bs.status_name, cu.full_name customer_name, pu.full_name provider_name, ps.service_title " +
            "FROM bookings b JOIN booking_statuses bs ON b.status_id=bs.status_id JOIN users cu ON b.customer_id=cu.user_id " +
            "JOIN provider_services ps ON b." + bookingServiceColumn + "=ps." + providerServiceIdColumn + " " + providerJoin;
    }
    private Booking map(ResultSet rs) throws SQLException {
        Booking b = new Booking();
        b.setBookingId(rs.getInt("booking_id")); b.setCustomerId(rs.getInt("customer_id")); b.setProviderId(rs.getInt("provider_id"));
        b.setServiceId(rs.getInt("service_id")); b.setStatusId(rs.getInt("status_id")); b.setAddressId(rs.getInt("address_id"));
        b.setBookingDate(rs.getDate("booking_date").toLocalDate()); b.setBookingTime(rs.getTime("booking_time").toLocalTime());
        Timestamp ts = rs.getTimestamp("created_at"); if (ts != null) b.setCreatedAt(ts.toLocalDateTime());
        b.setNotes(rs.getString("notes")); b.setStatusName(rs.getString("status_name")); b.setCustomerName(rs.getString("customer_name"));
        b.setProviderName(rs.getString("provider_name")); b.setServiceTitle(rs.getString("service_title"));
        return b;
    }

    private String resolveBookingServiceColumn(Connection c) throws SQLException {
        return hasColumn(c, "bookings", "service_id") ? "service_id" : "provider_service_id";
    }

    private String resolveNotesColumn(Connection c) throws SQLException {
        return hasColumn(c, "bookings", "notes") ? "notes" : "problem_description";
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

    private boolean providerServicesUseProfileId(Connection c) throws SQLException {
        return hasColumn(c, "provider_profiles", "provider_id") && !hasColumn(c, "provider_profiles", "profile_id");
    }

    private String providerWhereColumn(Connection c) throws SQLException {
        if (hasColumn(c, "bookings", "provider_id")) return "b.provider_id";
        return providerServicesUseProfileId(c) ? "pp.user_id" : "ps.provider_id";
    }
}
