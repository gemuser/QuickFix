package com.quickfix.dao;

import com.quickfix.model.Address;
import com.quickfix.util.DBConnection;
import java.sql.*;
import java.util.*;

public class AddressDAO {
    public int create(Address a) throws SQLException {
        String sql = "INSERT INTO addresses(user_id,address_line,city,area,postal_code) VALUES(?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, a.getUserId()); ps.setString(2, a.getLine1()); ps.setString(3, a.getCity());
            ps.setString(4, a.getState()); ps.setString(5, a.getPostalCode());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { return rs.next() ? rs.getInt(1) : 0; }
        }
    }
    public List<Address> findByUser(int userId) throws SQLException {
        List<Address> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement("SELECT * FROM addresses WHERE user_id=?")) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Address a = new Address();
                    a.setAddressId(rs.getInt("address_id")); a.setUserId(rs.getInt("user_id")); a.setLine1(rs.getString("address_line"));
                    a.setCity(rs.getString("city")); a.setState(rs.getString("area")); a.setPostalCode(rs.getString("postal_code")); a.setCountry("");
                    list.add(a);
                }
            }
        }
        return list;
    }
}
