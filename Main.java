
import java.util.*;
import java.io.*;

public class Main{

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Student> students = new ArrayList<>();
    static HashMap<Integer, Student> studentMap = new HashMap<>();
    static HashSet<Integer> studentIds = new HashSet<>();

    static final String FILE_NAME = "students.txt";

    // Student class
    static class Student {
        int id;
        String name;
        double mark;

        Student(int id, String name, double mark) {
            this.id = id;
            this.name = name;
            this.mark = mark;
        }

        public String toString() {
            return "ID: " + id +
                    " | Name: " + name +
                    " | Mark: " + mark;
        }
    }

    // Add student
    static void addStudent() {
        try {
            System.out.print("Enter student ID: ");
            int id = Integer.parseInt(sc.nextLine());

            if (studentIds.contains(id)) {
                System.out.println("Student ID already exists!");
                return;
            }

            System.out.print("Enter student name: ");
            String name = sc.nextLine().trim();

            if (name.isEmpty() || name.contains(",")) {
                throw new IllegalArgumentException(
                        "Name cannot be empty or contain commas."
                );
            }

            System.out.print("Enter mark (0-100): ");
            double mark = Double.parseDouble(sc.nextLine());

            if (mark < 0 || mark > 100) {
                throw new IllegalArgumentException(
                        "Mark must be between 0 and 100."
                );
            }

            Student s = new Student(id, name, mark);

            students.add(s);
            studentMap.put(id, s);
            studentIds.add(id);

            System.out.println("Student added successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Display students
    static void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("\n--- Student Records ---");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    // Search student
    static void searchStudent() {
        try {
            System.out.print("Enter student ID to search: ");
            int id = Integer.parseInt(sc.nextLine());

            Student s = studentMap.get(id);

            if (s != null) {
                System.out.println(s);
            } else {
                System.out.println("Student not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid student ID.");
        }
    }

    // Update student
    static void updateStudent() {
        try {
            System.out.print("Enter student ID to update: ");
            int id = Integer.parseInt(sc.nextLine());

            Student s = studentMap.get(id);

            if (s == null) {
                System.out.println("Student not found.");
                return;
            }

            System.out.print("Enter new name: ");
            String name = sc.nextLine().trim();

            if (name.isEmpty() || name.contains(",")) {
                throw new IllegalArgumentException("Invalid name.");
            }

            System.out.print("Enter new mark (0-100): ");
            double mark = Double.parseDouble(sc.nextLine());

            if (mark < 0 || mark > 100) {
                throw new IllegalArgumentException(
                        "Mark must be between 0 and 100."
                );
            }

            s.name = name;
            s.mark = mark;

            System.out.println("Student updated successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Delete student
    static void deleteStudent() {
        try {
            System.out.print("Enter student ID to delete: ");
            int id = Integer.parseInt(sc.nextLine());

            Student s = studentMap.remove(id);

            if (s != null) {
                students.remove(s);
                studentIds.remove(id);
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Student not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid student ID.");
        }
    }

    // Save records to file
    static void saveToFile() {
        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(FILE_NAME))) {

            for (Student s : students) {
                writer.println(
                        s.id + "," + s.name + "," + s.mark
                );
            }

            System.out.println("Records saved to " + FILE_NAME);

        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    // Load records from file
    static void loadFromFile() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No saved records found yet.");
            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    String[] data = line.split(",", -1);

                    if (data.length != 3) {
                        continue;
                    }

                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    double mark = Double.parseDouble(data[2]);

                    if (id <= 0 || name.trim().isEmpty()
                            || mark < 0 || mark > 100
                            || studentIds.contains(id)) {
                        continue;
                    }

                    Student s = new Student(id, name, mark);

                    students.add(s);
                    studentMap.put(id, s);
                    studentIds.add(id);

                } catch (NumberFormatException e) {
                    System.out.println("Skipping an invalid file record.");
                }
            }

            System.out.println("Student records loaded.");

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Method overloading example
    static void greet() {
        System.out.println("Welcome to Student Management System!");
    }

    static void greet(String name) {
        System.out.println("Welcome, " + name + "!");
    }

    // Main method
    public static void main(String[] args) {
        loadFromFile();
        greet();

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Save Records");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        displayStudents();
                        break;
                    case 3:
                        searchStudent();
                        break;
                    case 4:
                        updateStudent();
                        break;
                    case 5:
                        deleteStudent();
                        break;
                    case 6:
                        saveToFile();
                        break;
                    case 7:
                        saveToFile();
                        System.out.println("Thank you!");
                        sc.close();
                        return;
                    default:
                        System.out.println("Choose between 1 and 7.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Enter a valid menu number.");
            }
        }
    }
}
