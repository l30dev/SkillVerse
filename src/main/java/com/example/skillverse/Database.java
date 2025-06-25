package com.example.skillverse;

import java.sql.*;
import java.security.MessageDigest;

public class Database {

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/SkillVerse";
        String user = "postgres";
        String password = "1216";
        return DriverManager.getConnection(url, user, password);
    }


    public static boolean insertUser(String username, String password) {
        String hashed = hash(password);
        String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, hashed);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

   
    public static boolean validateUser(String username, String password) {
        String hashed = hash(password);
        String sql = "SELECT * FROM users WHERE username = ? AND password_hash = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, hashed);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static int getId(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

   
    private static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash)
                sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
