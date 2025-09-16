package dao;

import model.Application;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {
    public void addApplication(Application application) throws SQLException {
        String query = "INSERT INTO Applications (student_id, course_id, status) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, application.getStudentId());
            stmt.setInt(2, application.getCourseId());
            stmt.setString(3, application.getStatus());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                application.setApplicationId(rs.getInt(1));
            }
        }
    }

    public List<Application> getPendingApplications() throws SQLException {
        List<Application> applications = new ArrayList<>();
        String query = "SELECT * FROM Applications WHERE status = 'PENDING'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                applications.add(new Application(rs.getInt("student_id"), rs.getInt("course_id"), rs.getString("status")));
            }
        }
        return applications;
    }

    public void updateApplicationStatus(int applicationId, String status) throws SQLException {
        String query = "UPDATE Applications SET status = ? WHERE application_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, applicationId);
            stmt.executeUpdate();
        }
    }
}