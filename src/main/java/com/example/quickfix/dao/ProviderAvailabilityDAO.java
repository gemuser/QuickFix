package com.quickfix.dao;

import com.quickfix.model.ProviderAvailability;
import com.quickfix.util.DBConnection;
import java.sql.*;
import java.util.*;

public class ProviderAvailabilityDAO {
    public void create(ProviderAvailability a) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            boolean dateBased = hasColumn(c, "provider_availability", "available_date");
            String sql = dateBased
                ? "INSERT INTO provider_availability(provider_id,available_date,start_time,end_time,is_available) VALUES(?,?,?,?,?)"
                : "INSERT INTO provider_availability(provider_id,day_of_week,start_time,end_time) VALUES(?,?,?,?)";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, toStoredProviderId(c, a.getProviderId()));
                if (dateBased) {
                    ps.setDate(2, java.sql.Date.valueOf(a.getAvailableDate()));
                } else {
                    ps.setString(2, a.getAvailableDate().getDayOfWeek().toString().substring(0,1) + a.getAvailableDate().getDayOfWeek().toString().substring(1).toLowerCase());
                }
                ps.setTime(3, Time.valueOf(a.getStartTime())); ps.setTime(4, Time.valueOf(a.getEndTime()));
                if (dateBased) ps.setBoolean(5, a.isAvailable());
                ps.executeUpdate();
            }
        }
    }
    public List<ProviderAvailability> findByProvider(int providerId) throws SQLException {
        List<ProviderAvailability> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement("SELECT * FROM provider_availability WHERE provider_id=? ORDER BY availability_id DESC")) {
            ps.setInt(1, toStoredProviderId(c, providerId));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProviderAvailability a = new ProviderAvailability();
                    a.setAvailabilityId(rs.getInt("availability_id")); a.setProviderId(rs.getInt("provider_id"));
                    if (hasColumn(c, "provider_availability", "available_date") && rs.getDate("available_date") != null) {
                        a.setAvailableDate(rs.getDate("available_date").toLocalDate());
                    } else {
                        a.setAvailableDate(null);
                    }
                    a.setStartTime(rs.getTime("start_time").toLocalTime());
                    a.setEndTime(rs.getTime("end_time").toLocalTime());
                    a.setAvailable(!hasColumn(c, "provider_availability", "is_available") || rs.getBoolean("is_available"));
                    list.add(a);
                }
            }
        }
        return list;
    }

    private int toStoredProviderId(Connection c, int userId) throws SQLException {
        if (!(hasColumn(c, "provider_profiles", "provider_id") && !hasColumn(c, "provider_profiles", "profile_id"))) return userId;
        try (PreparedStatement ps = c.prepareStatement("SELECT provider_id FROM provider_profiles WHERE user_id=?")) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return userId;
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
