package model;

public class Course {
    private int courseId;
    private String courseName;
    private double cutoffMarks;
    private int seatsAvailable;

    public Course(String courseName, double cutoffMarks, int seatsAvailable) {
        this.courseName = courseName;
        this.cutoffMarks = cutoffMarks;
        this.seatsAvailable = seatsAvailable;
    }

    // Getters and Setters
    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public String getCourseName() { return courseName; }
    public double getCutoffMarks() { return cutoffMarks; }
    public int getSeatsAvailable() { return seatsAvailable; }
    public void setSeatsAvailable(int seatsAvailable) { this.seatsAvailable = seatsAvailable; }
}
