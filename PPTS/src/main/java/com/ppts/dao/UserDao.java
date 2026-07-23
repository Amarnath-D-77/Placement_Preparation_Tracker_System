package com.ppts.dao;

import com.ppts.DatabaseConnection;
import com.ppts.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public boolean registerUser(User user) {
        String query = "INSERT INTO users (username, password, current_rating, target_rating) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.out.println("Could not connect to database. Is MySQL running?");
                return false;
            }
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, user.getUsername());
                pstmt.setString(2, user.getPassword()); // In a real app, hash this!
                pstmt.setInt(3, user.getCurrentRating());
                pstmt.setInt(4, user.getTargetRating());
                
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    public User loginUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.out.println("Could not connect to database. Is MySQL running?");
                return null;
            }
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("current_rating"),
                        rs.getInt("target_rating")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error logging in: " + e.getMessage());
        }
        return null;
    }

    public boolean updateRating(int userId, int newRating) {
        String query = "UPDATE users SET current_rating = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) return false;
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, newRating);
                pstmt.setInt(2, userId);
                
                return pstmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error updating rating: " + e.getMessage());
            return false;
        }
    }
}
