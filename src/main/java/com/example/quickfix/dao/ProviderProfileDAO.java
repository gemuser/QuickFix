package com.quickfix.dao;

import com.quickfix.model.ProviderProfile;
import com.quickfix.util.DBConnection;
import java.sql.*;
import java.util.*;

public class ProviderProfileDAO {
    private static volatile String cachedProfileIdColumn;
    private static volatile String cachedVerificationStatusColumn;

    public ProviderProfile findByProvider(int userId) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            String profileIdColumn = resolveProfileIdColumn(c);
            String verificationStatusColumn = resolveVerificationStatusColumn(c);
            String sql = "SELECT p." + profileIdColumn + " AS profile_id, p.user_id, p.bio, p.experience_years, p." + verificationStatusColumn + " AS verification_status, " +
                    "COALESCE((SELECT AVG(rating) FROM ratings_feedback rf WHERE rf.provider_id=p.user_id),0) AS average_rating, " +
                    "u.full_name provider_name, u.email, u.phone FROM provider_profiles p JOIN users u ON p.user_id=u.user_id WHERE p.user_id=?";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
            }
        }
    }
    public List<ProviderProfile> findAll() throws SQLException {
        List<ProviderProfile> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            String profileIdColumn = resolveProfileIdColumn(c);
            String verificationStatusColumn = resolveVerificationStatusColumn(c);
            String sql = "SELECT p." + profileIdColumn + " AS profile_id, p.user_id, p.bio, p.experience_years, p." + verificationStatusColumn + " AS verification_status, " +
                    "COALESCE((SELECT AVG(rating) FROM ratings_feedback rf WHERE rf.provider_id=p.user_id),0) AS average_rating, " +
                    "u.full_name provider_name, u.email, u.phone FROM provider_profiles p JOIN users u ON p.user_id=u.user_id ORDER BY p." + verificationStatusColumn + ", u.full_name";
            try (PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }
    public void save(ProviderProfile p) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            String verificationStatusColumn = resolveVerificationStatusColumn(c);
            String sql = "INSERT INTO provider_profiles(user_id,bio,experience_years," + verificationStatusColumn + ") VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE bio=VALUES(bio), experience_years=VALUES(experience_years)";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, p.getUserId()); ps.setString(2, p.getBio()); ps.setInt(3, p.getExperienceYears());
                ps.setString(4, p.getVerificationStatus() == null ? "PENDING" : p.getVerificationStatus()); ps.executeUpdate();
            }
        }
    }
    public void updateVerification(int userId, String status) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            String verificationStatusColumn = resolveVerificationStatusColumn(c);
            try (PreparedStatement ps = c.prepareStatement("UPDATE provider_profiles SET " + verificationStatusColumn + "=? WHERE user_id=?")) {
                ps.setString(1, status); ps.setInt(2, userId); ps.executeUpdate();
            }
        }
    }
    private ProviderProfile map(ResultSet rs) throws SQLException {
        ProviderProfile p = new ProviderProfile();
        p.setProfileId(rs.getInt("profile_id")); p.setUserId(rs.getInt("user_id")); p.setBio(rs.getString("bio"));
        p.setExperienceYears(rs.getInt("experience_years")); p.setVerificationStatus(rs.getString("verification_status"));
        p.setAverageRating(rs.getDouble("average_rating")); p.setProviderName(rs.getString("provider_name")); p.setEmail(rs.getString("email")); p.setPhone(rs.getString("phone"));
        return p;
    }

    private String resolveProfileIdColumn(Connection c) throws SQLException {
        if (cachedProfileIdColumn != null) return cachedProfileIdColumn;
        if (hasColumn(c, "profile_id")) {
            cachedProfileIdColumn = "profile_id";
            return cachedProfileIdColumn;
        }
        if (hasColumn(c, "provider_id")) {
            cachedProfileIdColumn = "provider_id";
            return cachedProfileIdColumn;
        }
        throw new SQLException("Neither profile_id nor provider_id exists in provider_profiles table.");
    }

    private String resolveVerificationStatusColumn(Connection c) throws SQLException {
        if (cachedVerificationStatusColumn != null) return cachedVerificationStatusColumn;
        if (hasColumn(c, "verification_status")) {
            cachedVerificationStatusColumn = "verification_status";
            return cachedVerificationStatusColumn;
        }
        if (hasColumn(c, "approval_status")) {
            cachedVerificationStatusColumn = "approval_status";
            return cachedVerificationStatusColumn;
        }
        throw new SQLException("Neither verification_status nor approval_status exists in provider_profiles table.");
    }

    private boolean hasColumn(Connection c, String columnName) throws SQLException {
        DatabaseMetaData meta = c.getMetaData();
        try (ResultSet rs = meta.getColumns(c.getCatalog(), null, "provider_profiles", columnName)) {
            if (rs.next()) return true;
        }
        try (ResultSet rs = meta.getColumns(c.getCatalog(), null, "PROVIDER_PROFILES", columnName.toUpperCase(Locale.ROOT))) {
            return rs.next();
        }
    }
}
