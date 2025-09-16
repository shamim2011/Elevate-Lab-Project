package model;

public class Student {
    private int studentId;
    private String name;
    private String email;
    private double marks;

    public Student(String name, String email, double marks) {
        this.name = name;
        this.email = email;
        this.marks = marks;
    }

    // Getters and Setters
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public double getMarks() { return marks; }
}
