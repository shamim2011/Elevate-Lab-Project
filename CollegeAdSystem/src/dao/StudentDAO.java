package dao;

import model.Student;
import util.DatabaseConnection;

import java.sql.*;

public class StudentDAO {
    public void addStudent(Student student) throws SQLException {
        String query = "INSERT INTO Students (name, email, marks) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setDouble(3, student.getMarks());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                student.setStudentId(rs.getInt(1));
            }
        }
    }
}
