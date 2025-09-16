package app;

import java.sql.SQLException;
import java.util.*;

import model.Course;
import model.Student;
import service.AdmissionService;

public class Main {
    public static void main(String[] args) {
        AdmissionService service = new AdmissionService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nCollege Admission Management System");
            System.out.println("1. Register Student");
            System.out.println("2. Add Course");
            System.out.println("3. Apply for Course");
            System.out.println("4. Process Applications");
            System.out.println("5. Generate Admission List");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter student name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter student email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter student marks: ");
                        double marks = scanner.nextDouble();
                        service.registerStudent(new Student(name, email, marks));
                        System.out.println("Student registered successfully!");
                        break;
                    case 2:
                        System.out.print("Enter course name: ");
                        String courseName = scanner.nextLine();
                        System.out.print("Enter cutoff marks: ");
                        double cutoff = scanner.nextDouble();
                        System.out.print("Enter available seats: ");
                        int seats = scanner.nextInt();
                        service.addCourse(new Course(courseName, cutoff, seats));
                        System.out.println("Course added successfully!");
                        break;
                    case 3:
                        System.out.print("Enter student ID: ");
                        int studentId = scanner.nextInt();
                        System.out.print("Enter course ID: ");
                        int courseId = scanner.nextInt();
                        service.applyForCourse(studentId, courseId);
                        System.out.println("Application submitted successfully!");
                        break;
                    case 4:
                        service.processApplications();
                        System.out.println("Applications processed!");
                        break;
                    case 5:
                        service.generateAdmissionList();
                        System.out.println("Admission list generated in output/admission_list.csv");
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (SQLException | java.io.IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
