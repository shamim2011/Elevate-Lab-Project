package dao;

import model.Course;
import util.DatabaseConnection;

import java.sql.*;

public class CourseDAO {
    public void addCourse(Course course) throws SQLException {
        String query = "INSERT INTO Courses (course_name, cutoff_marks, seats_available) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, course.getCourseName());
            stmt.setDouble(2, course.getCutoffMarks());
            stmt.setInt(3, course.getSeatsAvailable());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                course.setCourseId(rs.getInt(1));
            }
        }
    }

    public Course getCourse(int courseId) throws SQLException {
        String query = "SELECT * FROM Courses WHERE course_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Course(rs.getString("course_name"), rs.getDouble("cutoff_marks"), rs.getInt("seats_available"));
            }
            return null;
        }
    }

    public void updateSeats(int courseId, int seats) throws SQLException {
        String query = "UPDATE Courses SET seats_available = ? WHERE course_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, seats);
            stmt.setInt(2, courseId);
            stmt.executeUpdate();
        }
    }
}