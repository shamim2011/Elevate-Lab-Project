package model;

public class Application {
    private int applicationId;
    private int studentId;
    private int courseId;
    private String status;

    public Application(int studentId, int courseId, String status) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.status = status;
    }

    // Getters and Setters
    public int getApplicationId() { return applicationId; }
    public void setApplicationId(int applicationId) { this.applicationId = applicationId; }
    public int getStudentId() { return studentId; }
    public int getCourseId() { return courseId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
