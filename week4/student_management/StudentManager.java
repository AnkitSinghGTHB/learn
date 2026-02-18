
/**
 * Student Management System — Week 4 Hands-On Project #2
 * 
 * Demonstrates:
 * - OOP: Classes, Inheritance, Encapsulation
 * - Interfaces: Comparable, custom Searchable
 * - Collections: ArrayList, HashMap, HashSet, TreeSet
 * - Sorting with Comparator
 * - equals()/hashCode() override
 * - Full CRUD operations
 */

import java.util.*;
import java.util.stream.Collectors;

// ──────────────────────────────────────────
// INTERFACES
// ──────────────────────────────────────────

interface Searchable<T> {
    List<T> searchByName(String name);

    T findById(int id);
}

interface Printable {
    void printDetails();
}

// ──────────────────────────────────────────
// STUDENT CLASS (Encapsulated + Comparable)
// ──────────────────────────────────────────

class Student implements Comparable<Student>, Printable {
    private static int nextId = 1000;

    private final int id;
    private String name;
    private int age;
    private double gpa;
    private Set<String> enrolledCourses;

    public Student(String name, int age, double gpa) {
        this.id = nextId++;
        setName(name);
        setAge(age);
        setGpa(gpa);
        this.enrolledCourses = new HashSet<>();
    }

    // --- Getters ---
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getGpa() {
        return gpa;
    }

    public Set<String> getEnrolledCourses() {
        return Collections.unmodifiableSet(enrolledCourses);
    }

    // --- Setters with Validation ---
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name.trim();
    }

    public void setAge(int age) {
        if (age < 16 || age > 100) {
            throw new IllegalArgumentException("Age must be between 16 and 100");
        }
        this.age = age;
    }

    public void setGpa(double gpa) {
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
        }
        this.gpa = gpa;
    }

    // --- Course Management ---
    public boolean enrollCourse(String course) {
        return enrolledCourses.add(course);
    }

    public boolean dropCourse(String course) {
        return enrolledCourses.remove(course);
    }

    // --- Comparable: Sort by GPA descending ---
    @Override
    public int compareTo(Student other) {
        int gpaCompare = Double.compare(other.gpa, this.gpa); // Descending
        if (gpaCompare != 0)
            return gpaCompare;
        return this.name.compareTo(other.name); // Then by name ascending
    }

    // --- equals / hashCode based on id ---
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Student student = (Student) obj;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // --- Printable interface ---
    @Override
    public void printDetails() {
        System.out.println("┌─────────────────────────────────┐");
        System.out.printf("│ ID: %-10d GPA: %-12.2f│%n", id, gpa);
        System.out.printf("│ Name: %-25s│%n", name);
        System.out.printf("│ Age: %-26d│%n", age);
        System.out.printf("│ Courses: %-22s│%n", enrolledCourses);
        System.out.println("└─────────────────────────────────┘");
    }

    @Override
    public String toString() {
        return String.format("Student{id=%d, name='%s', age=%d, gpa=%.2f, courses=%s}",
                id, name, age, gpa, enrolledCourses);
    }
}

// ──────────────────────────────────────────
// GRADUATE STUDENT (Inheritance)
// ──────────────────────────────────────────

class GraduateStudent extends Student {
    private String thesis;
    private String advisor;

    public GraduateStudent(String name, int age, double gpa, String thesis, String advisor) {
        super(name, age, gpa);
        this.thesis = thesis;
        this.advisor = advisor;
    }

    public String getThesis() {
        return thesis;
    }

    public String getAdvisor() {
        return advisor;
    }

    @Override
    public void printDetails() {
        super.printDetails();
        System.out.println("  → Thesis: " + thesis);
        System.out.println("  → Advisor: " + advisor);
    }

    @Override
    public String toString() {
        return super.toString() + " [GRAD: thesis='" + thesis + "', advisor='" + advisor + "']";
    }
}

// ──────────────────────────────────────────
// STUDENT MANAGER (Collections Hub)
// ──────────────────────────────────────────

class StudentManager implements Searchable<Student> {
    private final List<Student> students; // All students
    private final Map<Integer, Student> idIndex; // Fast lookup by ID
    private final Map<String, List<Student>> courseRosters; // Course → students

    public StudentManager() {
        this.students = new ArrayList<>();
        this.idIndex = new HashMap<>();
        this.courseRosters = new HashMap<>();
    }

    // ── CRUD Operations ──

    public void addStudent(Student student) {
        if (idIndex.containsKey(student.getId())) {
            System.out.println("⚠ Student already exists: " + student.getName());
            return;
        }
        students.add(student);
        idIndex.put(student.getId(), student);
        System.out.println("✅ Added: " + student.getName() + " (ID: " + student.getId() + ")");
    }

    public boolean removeStudent(int id) {
        Student student = idIndex.remove(id);
        if (student == null) {
            System.out.println("⚠ Student not found with ID: " + id);
            return false;
        }
        students.remove(student);
        // Remove from all course rosters
        for (String course : student.getEnrolledCourses()) {
            List<Student> roster = courseRosters.get(course);
            if (roster != null)
                roster.remove(student);
        }
        System.out.println("🗑 Removed: " + student.getName());
        return true;
    }

    // ── Enrollment ──

    public void enrollStudent(int studentId, String course) {
        Student student = findById(studentId);
        if (student == null) {
            System.out.println("⚠ Student not found");
            return;
        }
        if (student.enrollCourse(course)) {
            courseRosters.computeIfAbsent(course, k -> new ArrayList<>()).add(student);
            System.out.println("📚 " + student.getName() + " enrolled in " + course);
        } else {
            System.out.println("⚠ " + student.getName() + " is already enrolled in " + course);
        }
    }

    public void dropStudent(int studentId, String course) {
        Student student = findById(studentId);
        if (student == null)
            return;
        if (student.dropCourse(course)) {
            List<Student> roster = courseRosters.get(course);
            if (roster != null)
                roster.remove(student);
            System.out.println("❌ " + student.getName() + " dropped " + course);
        }
    }

    // ── Searchable Implementation ──

    @Override
    public Student findById(int id) {
        return idIndex.get(id); // O(1) with HashMap!
    }

    @Override
    public List<Student> searchByName(String name) {
        List<Student> results = new ArrayList<>();
        String lowerName = name.toLowerCase();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(lowerName)) {
                results.add(s);
            }
        }
        return results;
    }

    // ── Reporting ──

    public void printGPARanking() {
        TreeSet<Student> ranked = new TreeSet<>(students); // Uses compareTo()
        System.out.println("\n🏆 GPA Ranking:");
        int rank = 1;
        for (Student s : ranked) {
            System.out.printf("  #%d  %-15s GPA: %.2f%n", rank++, s.getName(), s.getGpa());
        }
    }

    public void printCourseRoster(String course) {
        List<Student> roster = courseRosters.getOrDefault(course, Collections.emptyList());
        System.out.println("\n📋 " + course + " Roster (" + roster.size() + " students):");
        roster.forEach(s -> System.out.println("  - " + s.getName() + " (GPA: " + s.getGpa() + ")"));
    }

    public void printStatistics() {
        if (students.isEmpty()) {
            System.out.println("No students enrolled.");
            return;
        }

        double avgGpa = students.stream()
                .mapToDouble(Student::getGpa)
                .average()
                .orElse(0.0);

        Student highest = Collections.max(students, Comparator.comparingDouble(Student::getGpa));
        Student lowest = Collections.min(students, Comparator.comparingDouble(Student::getGpa));

        System.out.println("\n📊 Statistics:");
        System.out.println("  Total Students: " + students.size());
        System.out.printf("  Average GPA: %.2f%n", avgGpa);
        System.out.println("  Highest GPA: " + highest.getName() + " (" + highest.getGpa() + ")");
        System.out.println("  Lowest GPA:  " + lowest.getName() + " (" + lowest.getGpa() + ")");
        System.out.println("  Courses offered: " + courseRosters.keySet());
    }

    public void printAll() {
        System.out.println("\n📝 All Students (" + students.size() + "):");
        students.forEach(Student::printDetails);
    }
}

// ──────────────────────────────────────────
// MAIN — Demo
// ──────────────────────────────────────────

public class StudentManager {
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();

        // Add students
        Student alice = new Student("Alice Johnson", 20, 3.8);
        Student bob = new Student("Bob Smith", 22, 3.5);
        Student charlie = new Student("Charlie Brown", 19, 3.9);
        Student diana = new Student("Diana Prince", 21, 3.2);
        GraduateStudent eve = new GraduateStudent("Eve Wilson", 25, 3.7,
                "Machine Learning in Healthcare", "Dr. Smith");

        manager.addStudent(alice);
        manager.addStudent(bob);
        manager.addStudent(charlie);
        manager.addStudent(diana);
        manager.addStudent(eve);

        // Enroll in courses
        System.out.println("\n── Enrollments ──");
        manager.enrollStudent(alice.getId(), "CS101");
        manager.enrollStudent(alice.getId(), "MATH201");
        manager.enrollStudent(bob.getId(), "CS101");
        manager.enrollStudent(bob.getId(), "ENG101");
        manager.enrollStudent(charlie.getId(), "CS101");
        manager.enrollStudent(charlie.getId(), "MATH201");
        manager.enrollStudent(diana.getId(), "ENG101");
        manager.enrollStudent(eve.getId(), "CS101");
        manager.enrollStudent(eve.getId(), "CS501");

        // Print all students
        manager.printAll();

        // GPA Ranking
        manager.printGPARanking();

        // Course rosters
        manager.printCourseRoster("CS101");
        manager.printCourseRoster("MATH201");

        // Search
        System.out.println("\n── Search Results for 'ali' ──");
        List<Student> results = manager.searchByName("ali");
        results.forEach(s -> System.out.println("  Found: " + s));

        // Statistics
        manager.printStatistics();

        // Drop a course
        System.out.println("\n── Drop Course ──");
        manager.dropStudent(bob.getId(), "CS101");
        manager.printCourseRoster("CS101");

        // Remove a student
        System.out.println("\n── Remove Student ──");
        manager.removeStudent(diana.getId());
        manager.printStatistics();
    }
}
