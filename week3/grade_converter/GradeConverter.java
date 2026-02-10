import java.util.Scanner;

/**
 * Grade Converter - Week 3 Hands-On Project #1
 * 
 * This program converts numerical grades to letter grades
 * using switch statements (both traditional and modern syntax).
 */
public class GradeConverter {
    
    /**
     * Converts a numerical score to a letter grade using traditional switch.
     * A: 90-100, B: 80-89, C: 70-79, D: 60-69, F: 0-59
     */
    public static String getLetterGrade(int score) {
        // Validate input
        if (score < 0 || score > 100) {
            return "Invalid";
        }
        
        // Use score / 10 to get the tens digit for switching
        int bracket = score / 10;
        String grade;
        
        switch (bracket) {
            case 10:  // 100
            case 9:   // 90-99
                grade = "A";
                break;
            case 8:   // 80-89
                grade = "B";
                break;
            case 7:   // 70-79
                grade = "C";
                break;
            case 6:   // 60-69
                grade = "D";
                break;
            default:  // 0-59
                grade = "F";
                break;
        }
        
        return grade;
    }
    
    /**
     * Modern switch expression syntax (Java 14+).
     * More concise and doesn't require break statements.
     */
    public static String getLetterGradeModern(int score) {
        if (score < 0 || score > 100) {
            return "Invalid";
        }
        
        return switch (score / 10) {
            case 10, 9 -> "A";
            case 8 -> "B";
            case 7 -> "C";
            case 6 -> "D";
            default -> "F";
        };
    }
    
    /**
     * Provides a detailed grade description.
     */
    public static String getGradeDescription(String letterGrade) {
        return switch (letterGrade) {
            case "A" -> "Excellent! Outstanding performance.";
            case "B" -> "Good job! Above average.";
            case "C" -> "Satisfactory. Meets expectations.";
            case "D" -> "Needs improvement. Below average.";
            case "F" -> "Failed. Please see instructor.";
            default -> "Unknown grade.";
        };
    }
    
    /**
     * Interactive mode - prompts user for grades until they quit.
     */
    public static void interactiveMode() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       Grade Converter v1.0           ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  Enter a score (0-100) or -1 to quit ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println();
        
        while (true) {
            System.out.print("Enter score: ");
            int score = scanner.nextInt();
            
            if (score == -1) {
                System.out.println("\nThank you for using Grade Converter!");
                break;
            }
            
            String grade = getLetterGrade(score);
            String description = getGradeDescription(grade);
            
            System.out.println("  → Letter Grade: " + grade);
            System.out.println("  → " + description);
            System.out.println();
        }
        
        scanner.close();
    }
    
    /**
     * Demo mode - shows examples without user input.
     */
    public static void demoMode() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║    Grade Converter - Demo Mode       ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println();
        
        int[] testScores = {95, 87, 73, 65, 42, 100, 0};
        
        System.out.println("Score → Grade → Description");
        System.out.println("─────────────────────────────────────");
        
        for (int score : testScores) {
            String grade = getLetterGrade(score);
            String description = getGradeDescription(grade);
            System.out.printf("%5d → %s     → %s%n", score, grade, description);
        }
    }
    
    public static void main(String[] args) {
        // Run demo first
        demoMode();
        
        System.out.println();
        System.out.println("Would you like to try it yourself?");
        System.out.println();
        
        // Then interactive mode
        interactiveMode();
    }
}
