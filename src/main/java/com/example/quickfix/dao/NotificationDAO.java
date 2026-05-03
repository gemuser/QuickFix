package com.quickfix.dao;

import com.quickfix.model.Notification;
import com.quickfix.util.DBConnection;
import java.sql.*;
import java.util.*;

public class NotificationDAO {
    public void create(Notification n) throws SQLException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement("INSERT INTO notifications(user_id," + titleColumn(c) + "," + messageColumn(c) + ") VALUES(?,?,?)")) {
            ps.setInt(1, n.getUserId()); ps.setString(2, n.getTitle()); ps.setString(3, n.getMessage()); ps.executeUpdate();
        }
    }
    public List<Notification> findByUser(int userId) throws SQLException {
        List<Notification> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement("SELECT * FROM notifications WHERE user_id=? ORDER BY created_at DESC LIMIT 10")) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Notification n = new Notification();
                    n.setNotificationId(rs.getInt("notification_id")); n.setUserId(rs.getInt("user_id")); n.setTitle(rs.getString(titleColumn(c)));
                    n.setMessage(rs.getString(messageColumn(c))); n.setRead(rs.getBoolean("is_read")); n.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    list.add(n);
                }
            }
        }
        return list;
    }

    private String titleColumn(Connection c) throws SQLException {
        return hasColumn(c, "notifications", "title") ? "title" : "notification_title";
    }

    private String messageColumn(Connection c) throws SQLException {
        return hasColumn(c, "notifications", "message") ? "message" : "notification_message";
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
