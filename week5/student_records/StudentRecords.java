import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

/**
 * Student Records — Week 5 Hands-On Project #3
 * 
 * A comprehensive project combining ALL Week 5 topics:
 * - Custom exceptions with rich data
 * - File I/O (reading CSV, writing reports)
 * - Generics (generic repository, generic CSV parser)
 * - try-with-resources
 * - Exception chaining
 * 
 * Features:
 * - Read student records from CSV
 * - Validate each record with specific error messages
 * - Calculate statistics (average, min, max, pass/fail counts)
 * - Write a formatted report to an output file
 * - Handle all errors gracefully
 */

// ============================================================
// Custom Exceptions
// ============================================================
class StudentRecordException extends Exception {
    private final int lineNumber;

    public StudentRecordException(String message, int lineNumber) {
        super(message);
        this.lineNumber = lineNumber;
    }

    public StudentRecordException(String message, int lineNumber, Throwable cause) {
        super(message, cause);
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}

class InvalidGradeException extends StudentRecordException {
    private final String invalidValue;

    public InvalidGradeException(String value, int lineNumber) {
        super("Invalid grade value: \"" + value + "\"", lineNumber);
        this.invalidValue = value;
    }

    public String getInvalidValue() {
        return invalidValue;
    }
}

class MissingFieldException extends StudentRecordException {
    private final String fieldName;

    public MissingFieldException(String fieldName, int lineNumber) {
        super("Missing required field: " + fieldName, lineNumber);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}

// ============================================================
// Generic Repository Interface
// ============================================================
interface Repository<T> {
    void add(T item);

    T findById(String id);

    List<T> findAll();

    int count();
}

// ============================================================
// Student Record (Data Class)
// ============================================================
class Student implements Comparable<Student> {
    private final String id;
    private final String name;
    private final String subject;
    private final double grade;

    public Student(String id, String name, String subject, double grade) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public double getGrade() {
        return grade;
    }

    public boolean isPassing() {
        return grade >= 40.0;
    }

    @Override
    public int compareTo(Student other) {
        return Double.compare(this.grade, other.grade);
    }

    @Override
    public String toString() {
        return String.format("%s | %-12s | %-10s | %6.1f | %s",
                id, name, subject, grade, isPassing() ? "PASS" : "FAIL");
    }
}

// ============================================================
// Generic In-Memory Repository
// ============================================================
class InMemoryRepository<T> implements Repository<T> {
    private final Map<String, T> storage = new LinkedHashMap<>();
    private final java.util.function.Function<T, String> idExtractor;

    public InMemoryRepository(java.util.function.Function<T, String> idExtractor) {
        this.idExtractor = idExtractor;
    }

    @Override
    public void add(T item) {
        String id = idExtractor.apply(item);
        storage.put(id, item);
    }

    @Override
    public T findById(String id) {
        return storage.get(id);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int count() {
        return storage.size();
    }
}

// ============================================================
// Generic CSV Parser
// ============================================================
class CsvParser<T> {
    private final java.util.function.BiFunction<String[], Integer, T> rowMapper;
    private final String delimiter;

    public CsvParser(java.util.function.BiFunction<String[], Integer, T> rowMapper) {
        this(rowMapper, ",");
    }

    public CsvParser(java.util.function.BiFunction<String[], Integer, T> rowMapper, String delimiter) {
        this.rowMapper = rowMapper;
        this.delimiter = delimiter;
    }

    public static class ParseResult<T> {
        public final List<T> items;
        public final List<StudentRecordException> errors;

        ParseResult(List<T> items, List<StudentRecordException> errors) {
            this.items = items;
            this.errors = errors;
        }
    }

    public ParseResult<T> parse(String filename) throws IOException {
        List<T> items = new ArrayList<>();
        List<StudentRecordException> errors = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filename))) {
            String line;
            int lineNumber = 0;

            // Skip header
            reader.readLine();
            lineNumber++;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String trimmed = line.trim();
                if (trimmed.isEmpty())
                    continue;

                try {
                    String[] fields = trimmed.split(delimiter, -1); // -1 keeps empty trailing fields
                    T item = rowMapper.apply(fields, lineNumber);
                    items.add(item);
                } catch (StudentRecordException e) {
                    errors.add(e);
                } catch (Exception e) {
                    errors.add(new StudentRecordException(
                            "Unexpected error: " + e.getMessage(), lineNumber, e));
                }
            }
        }

        return new ParseResult<>(items, errors);
    }
}

// ============================================================
// Statistics Calculator (uses bounded generics)
// ============================================================
class Statistics {

    public static <T extends Comparable<T>> T findMax(List<T> items) {
        if (items.isEmpty())
            throw new IllegalArgumentException("List is empty");
        T max = items.get(0);
        for (T item : items) {
            if (item.compareTo(max) > 0)
                max = item;
        }
        return max;
    }

    public static <T extends Comparable<T>> T findMin(List<T> items) {
        if (items.isEmpty())
            throw new IllegalArgumentException("List is empty");
        T min = items.get(0);
        for (T item : items) {
            if (item.compareTo(min) < 0)
                min = item;
        }
        return min;
    }
}

// ============================================================
// Main Application
// ============================================================
public class StudentRecords {

    // Student row mapper with validation
    static Student mapRow(String[] fields, int lineNumber) throws StudentRecordException {
        if (fields.length < 4) {
            throw new MissingFieldException(
                    "Expected 4 fields (id,name,subject,grade), got " + fields.length, lineNumber);
        }

        String id = fields[0].trim();
        String name = fields[1].trim();
        String subject = fields[2].trim();
        String gradeStr = fields[3].trim();

        if (id.isEmpty())
            throw new MissingFieldException("id", lineNumber);
        if (name.isEmpty())
            throw new MissingFieldException("name", lineNumber);
        if (subject.isEmpty())
            throw new MissingFieldException("subject", lineNumber);
        if (gradeStr.isEmpty())
            throw new MissingFieldException("grade", lineNumber);

        double grade;
        try {
            grade = Double.parseDouble(gradeStr);
        } catch (NumberFormatException e) {
            throw new InvalidGradeException(gradeStr, lineNumber);
        }

        if (grade < 0 || grade > 100) {
            throw new InvalidGradeException(
                    gradeStr + " (must be 0-100)", lineNumber);
        }

        return new Student(id, name, subject, grade);
    }

    static void createSampleData(String filename) throws IOException {
        Files.writeString(Path.of(filename),
                "id,name,subject,grade\n" +
                        "S001,Alice,Math,92.5\n" +
                        "S001,Alice,Science,88.0\n" +
                        "S001,Alice,History,95.0\n" +
                        "S002,Bob,Math,45.0\n" +
                        "S002,Bob,Science,NotANumber\n" +
                        "S002,Bob,History,52.0\n" +
                        "S003,Charlie,Math,78.5\n" +
                        "S003,Charlie,Science,82.0\n" +
                        "S003,Charlie\n" +
                        "S004,Diana,Math,35.0\n" +
                        "S004,Diana,Science,28.0\n" +
                        "S004,Diana,History,42.0\n" +
                        "S005,Eve,Math,100.0\n" +
                        "S005,Eve,Science,97.5\n" +
                        "S005,Eve,History,99.0\n");
    }

    static void writeReport(List<Student> students, List<StudentRecordException> errors,
            String outputFile) throws IOException {
        try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(Path.of(outputFile)))) {
            out.println("╔══════════════════════════════════════════════════════════════╗");
            out.println("║              STUDENT RECORDS REPORT                          ║");
            out.println("╚══════════════════════════════════════════════════════════════╝");
            out.println();

            // All records
            out.println("--- All Valid Records ---");
            out.printf("%-5s | %-12s | %-10s | %6s | %s%n", "ID", "Name", "Subject", "Grade", "Status");
            out.println("-".repeat(58));
            students.forEach(out::println);
            out.println();

            // Statistics
            if (!students.isEmpty()) {
                DoubleSummaryStatistics stats = students.stream()
                        .mapToDouble(Student::getGrade)
                        .summaryStatistics();

                long passing = students.stream().filter(Student::isPassing).count();
                long failing = students.size() - passing;

                out.println("--- Statistics ---");
                out.printf("Total records:   %d%n", students.size());
                out.printf("Average grade:   %.2f%n", stats.getAverage());
                out.printf("Highest grade:   %.1f%n", stats.getMax());
                out.printf("Lowest grade:    %.1f%n", stats.getMin());
                out.printf("Passing:         %d (%.1f%%)%n", passing, (passing * 100.0 / students.size()));
                out.printf("Failing:         %d (%.1f%%)%n", failing, (failing * 100.0 / students.size()));
                out.println();

                // Per-student averages
                Map<String, List<Student>> byStudent = students.stream()
                        .collect(Collectors.groupingBy(Student::getName));

                out.println("--- Per-Student Averages ---");
                out.printf("%-12s | %10s | %6s%n", "Student", "Avg Grade", "Status");
                out.println("-".repeat(35));
                byStudent.forEach((name, records) -> {
                    double avg = records.stream().mapToDouble(Student::getGrade).average().orElse(0);
                    out.printf("%-12s | %10.2f | %s%n", name, avg, avg >= 40 ? "PASS" : "FAIL");
                });
                out.println();

                // Top performer
                Student top = Statistics.findMax(students);
                out.println("🏆 Top Score: " + top.getName() + " in " + top.getSubject() +
                        " with " + top.getGrade());
                out.println();
            }

            // Errors
            if (!errors.isEmpty()) {
                out.println("--- Errors Encountered ---");
                for (StudentRecordException error : errors) {
                    out.printf("  Line %d: %s%n", error.getLineNumber(), error.getMessage());
                    if (error.getCause() != null) {
                        out.printf("    Caused by: %s%n", error.getCause().getMessage());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        String inputFile = "student_data.csv";
        String outputFile = "student_report.txt";

        System.out.println("=== Student Records System ===\n");

        // Step 1: Create sample data
        try {
            createSampleData(inputFile);
            System.out.println("✅ Created sample data: " + inputFile);
        } catch (IOException e) {
            System.err.println("❌ Failed to create sample data: " + e.getMessage());
            return;
        }

        // Step 2: Parse the CSV using our generic parser
        CsvParser<Student> parser = new CsvParser<>(StudentRecords::mapRow);
        CsvParser.ParseResult<Student> result;

        try {
            result = parser.parse(inputFile);
            System.out.printf("✅ Parsed %d records, %d errors%n",
                    result.items.size(), result.errors.size());
        } catch (IOException e) {
            System.err.println("❌ Failed to read file: " + e.getMessage());
            return;
        }

        // Step 3: Store in repository
        Repository<Student> repo = new InMemoryRepository<>(
                s -> s.getId() + "_" + s.getSubject() // Composite key
        );
        result.items.forEach(repo::add);
        System.out.printf("✅ Stored %d records in repository%n", repo.count());

        // Step 4: Display errors to console
        if (!result.errors.isEmpty()) {
            System.out.println("\n⚠️ Errors:");
            for (StudentRecordException e : result.errors) {
                System.out.printf("  Line %d: %s%n", e.getLineNumber(), e.getMessage());
            }
        }

        // Step 5: Write report
        try {
            writeReport(result.items, result.errors, outputFile);
            System.out.println("\n✅ Report written to: " + outputFile);
        } catch (IOException e) {
            System.err.println("❌ Failed to write report: " + e.getMessage());
        }

        System.out.println("\n🎉 Done! Open " + outputFile + " to see the full report.");
    }
}
