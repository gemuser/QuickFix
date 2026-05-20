package com.quickfix.dao;

import com.quickfix.model.ProviderService;
import com.quickfix.util.DBConnection;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class ProviderServiceDAO {
    public void save(ProviderService s) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            String serviceIdColumn = resolveServiceIdColumn(c);
            String descriptionColumn = resolveDescriptionColumn(c);
            int storedProviderId = toStoredProviderId(c, s.getProviderId());
            String sql = s.getServiceId() > 0
                ? "UPDATE provider_services SET category_id=?, service_title=?, " + descriptionColumn + "=?, price=? WHERE " + serviceIdColumn + "=? AND provider_id=?"
                : "INSERT INTO provider_services(provider_id,category_id,service_title," + descriptionColumn + ",price) VALUES(?,?,?,?,?)";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
            int i = 1;
            if (s.getServiceId() == 0) ps.setInt(i++, storedProviderId);
            ps.setInt(i++, s.getCategoryId()); ps.setString(i++, s.getServiceTitle()); ps.setString(i++, s.getDescription()); ps.setBigDecimal(i++, s.getPrice());
            if (s.getServiceId() > 0) { ps.setInt(i++, s.getServiceId()); ps.setInt(i, storedProviderId); }
            ps.executeUpdate();
            }
        }
    }
    public void delete(int serviceId, int providerId) throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement("DELETE FROM provider_services WHERE " + resolveServiceIdColumn(c) + "=? AND provider_id=?")) {
            ps.setInt(1, serviceId); ps.setInt(2, toStoredProviderId(c, providerId)); ps.executeUpdate();
        }
    }
    public boolean updatePrice(int serviceId, int providerId, BigDecimal price) throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement("UPDATE provider_services SET price=? WHERE " + resolveServiceIdColumn(c) + "=? AND provider_id=?")) {
            ps.setBigDecimal(1, price); ps.setInt(2, serviceId); ps.setInt(3, toStoredProviderId(c, providerId));
            return ps.executeUpdate() > 0;
        }
    }
    public ProviderService findById(int serviceId) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            String sql = baseSql(c) + " WHERE ps." + resolveServiceIdColumn(c) + "=?";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, serviceId);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
            }
        }
    }
    public List<ProviderService> findByProvider(int providerId) throws SQLException {
        try (Connection c = DBConnection.getConnection()) {
            String sql = baseSql(c) + " WHERE " + providerWhereColumn(c) + "=? ORDER BY ps.service_title";
            return query(c, sql, providerId);
        }
    }
    public List<ProviderService> search(Integer categoryId, Double maxPrice, Double minRating) throws SQLException {
        return search(categoryId, maxPrice, minRating, null);
    }
    public List<ProviderService> search(Integer categoryId, Double maxPrice, Double minRating, String keyword) throws SQLException {
        List<ProviderService> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            String statusColumn = hasColumn(c, "users", "status") ? "status" : "account_status";
            String approvalColumn = hasColumn(c, "provider_profiles", "verification_status") ? "verification_status" : "approval_status";
            String ratingProviderExpression = ratingsFeedbackUsesProfileId(c) ? "pp." + resolveProfileIdColumn(c) : "pp.user_id";
            String descriptionColumn = resolveDescriptionColumn(c);
            String sql = baseSql(c) + " WHERE u." + statusColumn + "='ACTIVE' AND pp." + approvalColumn + "='APPROVED' " +
                "AND (? IS NULL OR ps.category_id=?) AND (? IS NULL OR ps.price<=?) " +
                "AND (? IS NULL OR COALESCE((SELECT AVG(rating) FROM ratings_feedback rf WHERE rf.provider_id=" + ratingProviderExpression + "),0)>=?) " +
                "AND (? IS NULL OR LOWER(CONCAT_WS(' ', ps.service_title, ps." + descriptionColumn + ", sc.category_name, u.full_name)) LIKE ?) ORDER BY ps.price";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
            if (categoryId == null) { ps.setNull(1, Types.INTEGER); ps.setNull(2, Types.INTEGER); } else { ps.setInt(1, categoryId); ps.setInt(2, categoryId); }
            if (maxPrice == null) { ps.setNull(3, Types.DOUBLE); ps.setNull(4, Types.DOUBLE); } else { ps.setDouble(3, maxPrice); ps.setDouble(4, maxPrice); }
            if (minRating == null) { ps.setNull(5, Types.DOUBLE); ps.setNull(6, Types.DOUBLE); } else { ps.setDouble(5, minRating); ps.setDouble(6, minRating); }
            String normalizedKeyword = keyword == null || keyword.isBlank() ? null : "%" + keyword.trim().toLowerCase(Locale.ROOT) + "%";
            if (normalizedKeyword == null) { ps.setNull(7, Types.VARCHAR); ps.setNull(8, Types.VARCHAR); } else { ps.setString(7, normalizedKeyword); ps.setString(8, normalizedKeyword); }
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
            }
        }
        return list;
    }
    public List<String> popularServices() throws SQLException {
        List<String> rows = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            String serviceIdColumn = resolveServiceIdColumn(c);
            String bookingServiceColumn = hasColumn(c, "bookings", "service_id") ? "service_id" : "provider_service_id";
            String sql = "SELECT sc.category_name, COUNT(b.booking_id) total FROM service_categories sc LEFT JOIN provider_services ps ON sc.category_id=ps.category_id LEFT JOIN bookings b ON ps." + serviceIdColumn + "=b." + bookingServiceColumn + " GROUP BY sc.category_id ORDER BY total DESC";
            try (PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) rows.add(rs.getString("category_name") + " - " + rs.getInt("total") + " bookings");
            }
        }
        return rows;
    }
    private List<ProviderService> query(Connection c, String sql, int id) throws SQLException {
        List<ProviderService> list = new ArrayList<>();
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        }
        return list;
    }
    private String baseSql(Connection c) throws SQLException {
        String serviceIdColumn = resolveServiceIdColumn(c);
        String descriptionColumn = resolveDescriptionColumn(c);
        String activeSelect = hasColumn(c, "provider_services", "active") ? "ps.active" : "TRUE AS active";
        String ratingProviderExpression = ratingsFeedbackUsesProfileId(c) ? "pp." + resolveProfileIdColumn(c) : "pp.user_id";
        if (providerServicesUseProfileId(c)) {
            return "SELECT ps." + serviceIdColumn + " AS service_id, pp.user_id AS provider_id, ps.category_id, ps.service_title, ps." + descriptionColumn + " AS description, ps.price, " + activeSelect + ", sc.category_name, u.full_name provider_name, COALESCE((SELECT AVG(rating) FROM ratings_feedback rf WHERE rf.provider_id=" + ratingProviderExpression + "),0) AS average_rating FROM provider_services ps JOIN service_categories sc ON ps.category_id=sc.category_id JOIN provider_profiles pp ON ps.provider_id=pp.provider_id JOIN users u ON pp.user_id=u.user_id";
        }
        return "SELECT ps." + serviceIdColumn + " AS service_id, ps.provider_id, ps.category_id, ps.service_title, ps." + descriptionColumn + " AS description, ps.price, " + activeSelect + ", sc.category_name, u.full_name provider_name, COALESCE((SELECT AVG(rating) FROM ratings_feedback rf WHERE rf.provider_id=" + ratingProviderExpression + "),0) AS average_rating FROM provider_services ps JOIN service_categories sc ON ps.category_id=sc.category_id JOIN users u ON ps.provider_id=u.user_id LEFT JOIN provider_profiles pp ON ps.provider_id=pp.user_id";
    }
    private ProviderService map(ResultSet rs) throws SQLException {
        ProviderService s = new ProviderService();
        s.setServiceId(rs.getInt("service_id")); s.setProviderId(rs.getInt("provider_id")); s.setCategoryId(rs.getInt("category_id"));
        s.setServiceTitle(rs.getString("service_title")); s.setDescription(rs.getString("description")); s.setPrice(rs.getBigDecimal("price"));
        s.setActive(rs.getBoolean("active")); s.setCategoryName(rs.getString("category_name")); s.setProviderName(rs.getString("provider_name")); s.setAverageRating(rs.getDouble("average_rating"));
        return s;
    }

    private String resolveServiceIdColumn(Connection c) throws SQLException {
        return hasColumn(c, "provider_services", "service_id") ? "service_id" : "provider_service_id";
    }

    private String resolveDescriptionColumn(Connection c) throws SQLException {
        return hasColumn(c, "provider_services", "description") ? "description" : "service_description";
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
        return providerServicesUseProfileId(c);
    }

    private String resolveProfileIdColumn(Connection c) throws SQLException {
        if (hasColumn(c, "provider_profiles", "profile_id")) return "profile_id";
        if (hasColumn(c, "provider_profiles", "provider_id")) return "provider_id";
        throw new SQLException("Neither profile_id nor provider_id exists in provider_profiles table.");
    }

    private int toStoredProviderId(Connection c, int userId) throws SQLException {
        if (!providerServicesUseProfileId(c)) return userId;
        try (PreparedStatement ps = c.prepareStatement("SELECT provider_id FROM provider_profiles WHERE user_id=?")) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return userId;
    }

    private String providerWhereColumn(Connection c) throws SQLException {
        return providerServicesUseProfileId(c) ? "pp.user_id" : "ps.provider_id";
    }
}
