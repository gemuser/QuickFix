package com.quickfix.util;

public class ValidationUtil {
    private ValidationUtil() { }
    public static boolean isBlank(String value) { return value == null || value.trim().isEmpty(); }
    public static boolean isEmail(String value) { return value != null && value.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"); }
    public static boolean isPositive(String value) {
        try { return Double.parseDouble(value) >= 0; } catch (Exception e) { return false; }
    }
}
