package service;

import dao.*;
import model.*;
import util.DatabaseConnection;

import java.io.*;
import java.sql.*;
import java.util.List;

public class AdmissionService {
    private StudentDAO studentDAO = new StudentDAO();
    private CourseDAO courseDAO = new CourseDAO();
    private ApplicationDAO applicationDAO = new ApplicationDAO();

    public void registerStudent(Student student) throws SQLException {
        studentDAO.addStudent(student);
    }

    public void addCourse(Course course) throws SQLException {
        courseDAO.addCourse(course);
    }

    public void applyForCourse(int studentId, int courseId) throws SQLException {
        Application application = new Application(studentId, courseId, "PENDING");
        applicationDAO.addApplication(application);
    }

    public void processApplications() throws SQLException {
        List<Application> applications = applicationDAO.getPendingApplications();
        for (Application app : applications) {
            String query = "SELECT s.marks, c.cutoff_marks, c.seats_available, c.course_id " +
                          "FROM Students s JOIN Courses c ON s.student_id = ? AND c.course_id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, app.getStudentId());
                stmt.setInt(2, app.getCourseId());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    double studentMarks = rs.getDouble("marks");
                    double cutoffMarks = rs.getDouble("cutoff_marks");
                    int seatsAvailable = rs.getInt("seats_available");
                    if (studentMarks >= cutoffMarks && seatsAvailable > 0) {
                        applicationDAO.updateApplicationStatus(app.getApplicationId(), "APPROVED");
                        courseDAO.updateSeats(app.getCourseId(), seatsAvailable - 1);
                    } else {
                        applicationDAO.updateApplicationStatus(app.getApplicationId(), "REJECTED");
                    }
                }
            }
        }
    }

    public void generateAdmissionList() throws SQLException, IOException {
        String query = "SELECT s.name, s.email, c.course_name, a.status " +
                      "FROM Applications a JOIN Students s ON a.student_id = s.student_id " +
                      "JOIN Courses c ON a.course_id = c.course_id WHERE a.status = 'APPROVED'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery();
             FileWriter writer = new FileWriter("output/admission_list.csv")) {
            writer.write("Name,Email,Course,Status\n");
            while (rs.next()) {
                writer.write(String.format("%s,%s,%s,%s\n",
                    rs.getString("name"), rs.getString("email"),
                    rs.getString("course_name"), rs.getString("status")));
            }
        }
    }
}
