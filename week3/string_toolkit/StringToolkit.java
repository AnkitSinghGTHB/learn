import java.util.Scanner;

/**
 * String Toolkit - Week 3 Hands-On Project #3
 * 
 * This program demonstrates string manipulation:
 * - Substring extraction
 * - Split and join
 * - Replace operations
 * - String analysis
 * - StringBuilder usage
 */
public class StringToolkit {

    // ═══════════════════════════════════════════════════════
    // Basic String Operations
    // ═══════════════════════════════════════════════════════

    /**
     * Reverses a string.
     */
    public static String reverse(String s) {
        StringBuilder sb = new StringBuilder(s);
        return sb.reverse().toString();
    }

    /**
     * Counts occurrences of a substring.
     */
    public static int countOccurrences(String text, String search) {
        int count = 0;
        int index = 0;

        while ((index = text.indexOf(search, index)) != -1) {
            count++;
            index += search.length();
        }

        return count;
    }

    /**
     * Checks if a string is a palindrome.
     */
    public static boolean isPalindrome(String s) {
        String cleaned = s.toLowerCase().replaceAll("[^a-z0-9]", "");
        return cleaned.equals(reverse(cleaned));
    }

    /**
     * Converts to title case (Each Word Capitalized).
     */
    public static String toTitleCase(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }

        StringBuilder result = new StringBuilder();
        String[] words = s.toLowerCase().split("\\s+");

        for (int i = 0; i < words.length; i++) {
            if (words[i].length() > 0) {
                result.append(Character.toUpperCase(words[i].charAt(0)));
                result.append(words[i].substring(1));
            }
            if (i < words.length - 1) {
                result.append(" ");
            }
        }

        return result.toString();
    }

    /**
     * Counts words in a string.
     */
    public static int countWords(String s) {
        if (s == null || s.trim().isEmpty()) {
            return 0;
        }
        return s.trim().split("\\s+").length;
    }

    /**
     * Counts characters (excluding spaces).
     */
    public static int countCharsNoSpaces(String s) {
        return s.replace(" ", "").length();
    }

    // ═══════════════════════════════════════════════════════
    // Advanced Operations
    // ═══════════════════════════════════════════════════════

    /**
     * Extracts domain from email.
     */
    public static String extractEmailDomain(String email) {
        int atIndex = email.indexOf("@");
        if (atIndex == -1) {
            return null;
        }
        return email.substring(atIndex + 1);
    }

    /**
     * Extracts username from email.
     */
    public static String extractEmailUsername(String email) {
        int atIndex = email.indexOf("@");
        if (atIndex == -1) {
            return null;
        }
        return email.substring(0, atIndex);
    }

    /**
     * Masks sensitive data (shows first and last char only).
     */
    public static String maskString(String s, char maskChar) {
        if (s == null || s.length() <= 2) {
            return s;
        }

        StringBuilder masked = new StringBuilder();
        masked.append(s.charAt(0));

        for (int i = 1; i < s.length() - 1; i++) {
            masked.append(maskChar);
        }

        masked.append(s.charAt(s.length() - 1));
        return masked.toString();
    }

    /**
     * Truncates string with ellipsis.
     */
    public static String truncate(String s, int maxLength) {
        if (s == null || s.length() <= maxLength) {
            return s;
        }
        return s.substring(0, maxLength - 3) + "...";
    }

    /**
     * Converts string to slug (URL-friendly format).
     */
    public static String toSlug(String s) {
        return s.toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s-]", "") // Remove special chars
                .replaceAll("\\s+", "-") // Replace spaces with hyphens
                .replaceAll("-+", "-"); // Remove duplicate hyphens
    }

    /**
     * Wraps text at specified width.
     */
    public static String wordWrap(String text, int width) {
        StringBuilder result = new StringBuilder();
        String[] words = text.split("\\s+");
        int lineLength = 0;

        for (String word : words) {
            if (lineLength + word.length() > width) {
                result.append("\n");
                lineLength = 0;
            } else if (lineLength > 0) {
                result.append(" ");
                lineLength++;
            }
            result.append(word);
            lineLength += word.length();
        }

        return result.toString();
    }

    // ═══════════════════════════════════════════════════════
    // Interactive Demo
    // ═══════════════════════════════════════════════════════

    public static void runDemo() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║         String Toolkit v1.0            ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();

        // Demo string
        String sample = "Hello World";
        System.out.println("Demo String: \"" + sample + "\"");
        System.out.println();

        // Basic operations
        System.out.println("── Basic Operations ───────────────────");
        System.out.println("Length:        " + sample.length());
        System.out.println("Uppercase:     " + sample.toUpperCase());
        System.out.println("Lowercase:     " + sample.toLowerCase());
        System.out.println("Reversed:      " + reverse(sample));
        System.out.println("Title Case:    " + toTitleCase(sample.toLowerCase()));
        System.out.println();

        // Substrings
        System.out.println("── Substring Operations ───────────────");
        System.out.println("First 5 chars: " + sample.substring(0, 5));
        System.out.println("Last 5 chars:  " + sample.substring(sample.length() - 5));
        System.out.println("Char at 4:     " + sample.charAt(4));
        System.out.println();

        // Search and replace
        System.out.println("── Search & Replace ───────────────────");
        System.out.println("Contains 'World': " + sample.contains("World"));
        System.out.println("Index of 'o':     " + sample.indexOf('o'));
        System.out.println("Last index of 'o':" + sample.lastIndexOf('o'));
        System.out.println("Replace 'World':  " + sample.replace("World", "Java"));
        System.out.println();

        // Analysis
        String sentence = "Java is a popular programming language!";
        System.out.println("── Text Analysis ──────────────────────");
        System.out.println("Text: \"" + sentence + "\"");
        System.out.println("Word count:            " + countWords(sentence));
        System.out.println("Chars (with spaces):   " + sentence.length());
        System.out.println("Chars (no spaces):     " + countCharsNoSpaces(sentence));
        System.out.println("Occurrences of 'a':    " + countOccurrences(sentence, "a"));
        System.out.println();

        // Palindrome check
        System.out.println("── Palindrome Check ───────────────────");
        String[] testStrings = { "radar", "hello", "A man a plan a canal Panama" };
        for (String test : testStrings) {
            System.out.println("\"" + test + "\" → " + (isPalindrome(test) ? "✓ Palindrome" : "✗ Not palindrome"));
        }
        System.out.println();

        // Email parsing
        System.out.println("── Email Parsing ──────────────────────");
        String email = "user@example.com";
        System.out.println("Email:    " + email);
        System.out.println("Username: " + extractEmailUsername(email));
        System.out.println("Domain:   " + extractEmailDomain(email));
        System.out.println();

        // Utility functions
        System.out.println("── Utility Functions ──────────────────");
        System.out.println("Masked password: " + maskString("mypassword", '*'));
        System.out.println("Truncated:       " + truncate("This is a very long string", 15));
        System.out.println("To slug:         " + toSlug("Hello World! This is a Test."));
        System.out.println();

        // Word wrap
        System.out.println("── Word Wrap (width=30) ───────────────");
        String longText = "Java is a high-level, class-based, object-oriented programming language.";
        System.out.println(wordWrap(longText, 30));
        System.out.println();

        // Split demo
        System.out.println("── Split Demo ─────────────────────────");
        String csv = "apple,banana,cherry,date";
        System.out.println("CSV: " + csv);
        String[] fruits = csv.split(",");
        for (int i = 0; i < fruits.length; i++) {
            System.out.println("  [" + i + "] " + fruits[i]);
        }
    }

    public static void main(String[] args) {
        runDemo();

        System.out.println("════════════════════════════════════════");
        System.out.println("Try it yourself! Modify main() to test");
        System.out.println("different strings and methods.");
        System.out.println("════════════════════════════════════════");
    }
}
