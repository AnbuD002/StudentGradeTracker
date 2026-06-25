import java.util.*;

/**
 * CodeAlpha Internship - Task 1: Student Grade Tracker
 * Manages student grades, calculates statistics, and displays summary report.
 */
public class StudentGradeTracker {

    static class Student {
        String name;
        ArrayList<Double> grades;

        Student(String name) {
            this.name = name;
            this.grades = new ArrayList<>();
        }

        void addGrade(double grade) {
            grades.add(grade);
        }

        double getAverage() {
            if (grades.isEmpty()) return 0;
            double sum = 0;
            for (double g : grades) sum += g;
            return sum / grades.size();
        }

        double getHighest() {
            if (grades.isEmpty()) return 0;
            return Collections.max(grades);
        }

        double getLowest() {
            if (grades.isEmpty()) return 0;
            return Collections.min(grades);
        }

        String getLetterGrade() {
            double avg = getAverage();
            if (avg >= 90) return "A";
            else if (avg >= 80) return "B";
            else if (avg >= 70) return "C";
            else if (avg >= 60) return "D";
            else return "F";
        }

        String getStatus() {
            return getAverage() >= 60 ? "PASS" : "FAIL";
        }
    }

    private ArrayList<Student> students = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void run() {
        System.out.println("============================================");
        System.out.println("     STUDENT GRADE TRACKER - CodeAlpha     ");
        System.out.println("============================================");

        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Add Student");
            System.out.println("2. Add Grades for Student");
            System.out.println("3. View Student Report");
            System.out.println("4. View All Students Summary");
            System.out.println("5. Search Student");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = getIntInput();
            switch (choice) {
                case 1: addStudent(); break;
                case 2: addGrades(); break;
                case 3: viewStudentReport(); break;
                case 4: viewAllSummary(); break;
                case 5: searchStudent(); break;
                case 6:
                    System.out.println("\nThank you for using Student Grade Tracker!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Name cannot be empty."); return; }
        for (Student s : students) {
            if (s.name.equalsIgnoreCase(name)) {
                System.out.println("Student already exists!");
                return;
            }
        }
        students.add(new Student(name));
        System.out.println("Student '" + name + "' added successfully!");
    }

    private void addGrades() {
        if (students.isEmpty()) { System.out.println("No students found. Add a student first."); return; }
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        Student student = findStudent(name);
        if (student == null) { System.out.println("Student not found!"); return; }

        System.out.print("How many grades to add? ");
        int count = getIntInput();
        for (int i = 1; i <= count; i++) {
            System.out.print("Enter grade " + i + " (0-100): ");
            double grade = getDoubleInput();
            if (grade < 0 || grade > 100) { System.out.println("Invalid grade. Must be 0-100."); i--; continue; }
            student.addGrade(grade);
        }
        System.out.println("Grades added for " + student.name);
    }

    private void viewStudentReport() {
        if (students.isEmpty()) { System.out.println("No students found."); return; }
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        Student s = findStudent(name);
        if (s == null) { System.out.println("Student not found!"); return; }

        System.out.println("\n--- REPORT FOR: " + s.name.toUpperCase() + " ---");
        if (s.grades.isEmpty()) { System.out.println("No grades recorded yet."); return; }
        System.out.println("Grades     : " + s.grades);
        System.out.printf("Average    : %.2f%n", s.getAverage());
        System.out.printf("Highest    : %.2f%n", s.getHighest());
        System.out.printf("Lowest     : %.2f%n", s.getLowest());
        System.out.println("Letter     : " + s.getLetterGrade());
        System.out.println("Status     : " + s.getStatus());
    }

    private void viewAllSummary() {
        if (students.isEmpty()) { System.out.println("No students found."); return; }
        System.out.println("\n========== FULL CLASS SUMMARY ==========");
        System.out.printf("%-20s %-10s %-10s %-10s %-8s %-6s%n",
                "Name", "Average", "Highest", "Lowest", "Grade", "Status");
        System.out.println("-".repeat(70));

        double classTotal = 0;
        int passCount = 0;
        for (Student s : students) {
            if (s.grades.isEmpty()) {
                System.out.printf("%-20s %-10s%n", s.name, "No grades");
                continue;
            }
            System.out.printf("%-20s %-10.2f %-10.2f %-10.2f %-8s %-6s%n",
                    s.name, s.getAverage(), s.getHighest(), s.getLowest(),
                    s.getLetterGrade(), s.getStatus());
            classTotal += s.getAverage();
            if (s.getStatus().equals("PASS")) passCount++;
        }
        System.out.println("-".repeat(70));
        System.out.printf("Class Average: %.2f | Pass: %d | Fail: %d | Total: %d%n",
                students.isEmpty() ? 0 : classTotal / students.size(),
                passCount, students.size() - passCount, students.size());
    }

    private void searchStudent() {
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine().trim();
        Student s = findStudent(name);
        if (s == null) { System.out.println("Student not found!"); return; }
        System.out.println("Found: " + s.name + " | Grades: " + s.grades +
                " | Average: " + String.format("%.2f", s.getAverage()));
    }

    private Student findStudent(String name) {
        for (Student s : students) {
            if (s.name.equalsIgnoreCase(name)) return s;
        }
        return null;
    }

    private int getIntInput() {
        while (true) {
            try { return Integer.parseInt(scanner.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.print("Enter a valid number: "); }
        }
    }

    private double getDoubleInput() {
        while (true) {
            try { return Double.parseDouble(scanner.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.print("Enter a valid number: "); }
        }
    }

    public static void main(String[] args) {
        new StudentGradeTracker().run();
    }
}
