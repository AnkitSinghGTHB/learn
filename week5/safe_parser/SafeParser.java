import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Safe Parser — Week 5 Hands-On Project #1
 * 
 * Reads a file containing mixed data (numbers and text),
 * calculates the sum of valid numbers, and handles invalid
 * lines gracefully without crashing.
 * 
 * Demonstrates:
 * - try-with-resources
 * - NumberFormatException handling
 * - File I/O with java.nio.file
 * - Custom exception for reporting
 * - Collecting errors for summary reporting
 */
public class SafeParser {

    // Custom exception for parse errors
    static class ParseError {
        final int lineNumber;
        final String lineContent;
        final String errorMessage;

        ParseError(int lineNumber, String lineContent, String errorMessage) {
            this.lineNumber = lineNumber;
            this.lineContent = lineContent;
            this.errorMessage = errorMessage;
        }

        @Override
        public String toString() {
            return String.format("  Line %d: \"%s\" — %s", lineNumber, lineContent, errorMessage);
        }
    }

    // Result class to hold parsing results
    static class ParseResult {
        final List<Double> validNumbers;
        final List<ParseError> errors;

        ParseResult(List<Double> validNumbers, List<ParseError> errors) {
            this.validNumbers = validNumbers;
            this.errors = errors;
        }

        double getSum() {
            return validNumbers.stream().mapToDouble(Double::doubleValue).sum();
        }

        double getAverage() {
            if (validNumbers.isEmpty())
                return 0.0;
            return getSum() / validNumbers.size();
        }

        double getMin() {
            return validNumbers.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
        }

        double getMax() {
            return validNumbers.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
        }
    }

    /**
     * Parses a file and extracts valid numbers, collecting errors for invalid
     * lines.
     */
    public static ParseResult parseFile(String filename) throws IOException {
        Path filePath = Path.of(filename);

        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found: " + filename);
        }

        List<Double> validNumbers = new ArrayList<>();
        List<ParseError> errors = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String trimmed = line.trim();

                // Skip empty lines and comments
                if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                    continue;
                }

                try {
                    double value = Double.parseDouble(trimmed);
                    validNumbers.add(value);
                } catch (NumberFormatException e) {
                    errors.add(new ParseError(lineNumber, trimmed,
                            "Not a valid number"));
                }
            }
        }

        return new ParseResult(validNumbers, errors);
    }

    /**
     * Writes a summary report of the parsing results.
     */
    public static void writeReport(ParseResult result, String outputFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Path.of(outputFile)))) {
            writer.println("╔══════════════════════════════════════╗");
            writer.println("║        SAFE PARSER REPORT            ║");
            writer.println("╚══════════════════════════════════════╝");
            writer.println();

            writer.printf("Valid numbers found: %d%n", result.validNumbers.size());
            writer.printf("Errors encountered:  %d%n", result.errors.size());
            writer.println();

            if (!result.validNumbers.isEmpty()) {
                writer.println("--- Statistics ---");
                writer.printf("Sum:     %.2f%n", result.getSum());
                writer.printf("Average: %.2f%n", result.getAverage());
                writer.printf("Min:     %.2f%n", result.getMin());
                writer.printf("Max:     %.2f%n", result.getMax());
                writer.println();

                writer.println("--- All Valid Numbers ---");
                for (int i = 0; i < result.validNumbers.size(); i++) {
                    writer.printf("  [%d] %.2f%n", i + 1, result.validNumbers.get(i));
                }
                writer.println();
            }

            if (!result.errors.isEmpty()) {
                writer.println("--- Errors ---");
                for (ParseError error : result.errors) {
                    writer.println(error);
                }
            }
        }
    }

    public static void main(String[] args) {
        // Step 1: Create a sample data file
        String inputFile = "numbers.txt";
        String outputFile = "parse_report.txt";

        try {
            Files.writeString(Path.of(inputFile),
                    "# Sample number data file\n" +
                            "42\n" +
                            "3.14\n" +
                            "hello\n" +
                            "100\n" +
                            "not-a-number\n" +
                            "-7.5\n" +
                            "abc123\n" +
                            "99.9\n" +
                            "\n" +
                            "0\n" +
                            "twelve\n" +
                            "1000\n");
            System.out.println("Created sample file: " + inputFile);
        } catch (IOException e) {
            System.err.println("Failed to create sample file: " + e.getMessage());
            return;
        }

        // Step 2: Parse the file
        try {
            ParseResult result = parseFile(inputFile);

            // Step 3: Display results to console
            System.out.println("\n=== Parsing Complete ===");
            System.out.printf("Found %d valid numbers, %d errors%n",
                    result.validNumbers.size(), result.errors.size());
            System.out.printf("Sum: %.2f%n", result.getSum());
            System.out.printf("Average: %.2f%n", result.getAverage());

            if (!result.errors.isEmpty()) {
                System.out.println("\nErrors:");
                result.errors.forEach(System.out::println);
            }

            // Step 4: Write detailed report
            writeReport(result, outputFile);
            System.out.println("\nDetailed report written to: " + outputFile);

        } catch (FileNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
