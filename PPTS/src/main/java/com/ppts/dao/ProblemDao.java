package com.ppts.dao;

import com.ppts.DatabaseConnection;
import com.ppts.models.Problem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProblemDao {

    public boolean addProblem(Problem problem) {
        String query = "INSERT INTO problems (user_id, problem_name, topic, platform, difficulty, language) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) return false;
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, problem.getUserId());
                pstmt.setString(2, problem.getProblemName());
                pstmt.setString(3, problem.getTopic());
                pstmt.setString(4, problem.getPlatform());
                pstmt.setString(5, problem.getDifficulty());
                pstmt.setString(6, problem.getLanguage());

                return pstmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error adding problem: " + e.getMessage());
            return false;
        }
    }

    public List<Problem> getProblemsByUserId(int userId) {
        List<Problem> problems = new ArrayList<>();
        String query = "SELECT * FROM problems WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) return problems;
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, userId);
                ResultSet rs = pstmt.executeQuery();
                
                while (rs.next()) {
                    problems.add(extractProblemFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching problems: " + e.getMessage());
        }
        return problems;
    }

    public List<Problem> searchProblems(int userId, String filterType, String filterValue) {
        List<Problem> problems = new ArrayList<>();
        String query = "SELECT * FROM problems WHERE user_id = ? AND " + filterType + " LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) return problems;
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, userId);
                pstmt.setString(2, "%" + filterValue + "%");
                ResultSet rs = pstmt.executeQuery();
                
                while (rs.next()) {
                    problems.add(extractProblemFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching problems: " + e.getMessage());
        }
        return problems;
    }

    public boolean deleteProblem(int problemId, int userId) {
        String query = "DELETE FROM problems WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) return false;
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, problemId);
                pstmt.setInt(2, userId);
                
                return pstmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error deleting problem: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateProblem(Problem problem) {
        String query = "UPDATE problems SET problem_name = ?, topic = ?, platform = ?, difficulty = ?, language = ? WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) return false;
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, problem.getProblemName());
                pstmt.setString(2, problem.getTopic());
                pstmt.setString(3, problem.getPlatform());
                pstmt.setString(4, problem.getDifficulty());
                pstmt.setString(5, problem.getLanguage());
                pstmt.setInt(6, problem.getId());
                pstmt.setInt(7, problem.getUserId());

                return pstmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error updating problem: " + e.getMessage());
            return false;
        }
    }

    private Problem extractProblemFromResultSet(ResultSet rs) throws SQLException {
        return new Problem(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getString("problem_name"),
            rs.getString("topic"),
            rs.getString("platform"),
            rs.getString("difficulty"),
            rs.getString("language"),
            rs.getDate("solved_date")
        );
    }
}
