
/**
 * Word Frequency Counter — Week 4 Hands-On Project #3
 * 
 * Demonstrates:
 * - HashMap for frequency counting
 * - TreeMap for sorted output
 * - LinkedHashMap for insertion-order
 * - Comparator for custom sorting
 * - Collections utility methods
 * - Common interview patterns
 */

import java.util.*;
import java.util.stream.Collectors;

public class WordFrequency {

    // ──────────────────────────────────────────
    // Core: Count word frequencies
    // ──────────────────────────────────────────
    public static Map<String, Integer> countWords(String text) {
        Map<String, Integer> frequency = new HashMap<>();
        // Clean: lowercase, remove punctuation, split on whitespace
        String[] words = text.toLowerCase()
                .replaceAll("[^a-zA-Z\\s]", "")
                .split("\\s+");

        for (String word : words) {
            if (!word.isEmpty()) {
                frequency.merge(word, 1, Integer::sum);
            }
        }
        return frequency;
    }

    // ──────────────────────────────────────────
    // Analysis methods
    // ──────────────────────────────────────────

    /** Get words sorted alphabetically */
    public static Map<String, Integer> sortedAlphabetically(Map<String, Integer> freq) {
        return new TreeMap<>(freq);
    }

    /** Get top N most frequent words */
    public static List<Map.Entry<String, Integer>> topN(Map<String, Integer> freq, int n) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(freq.entrySet());
        // Sort by value descending, then by key alphabetically
        entries.sort((a, b) -> {
            int cmp = Integer.compare(b.getValue(), a.getValue());
            return cmp != 0 ? cmp : a.getKey().compareTo(b.getKey());
        });
        return entries.subList(0, Math.min(n, entries.size()));
    }

    /** Find unique words (appear exactly once) */
    public static Set<String> uniqueWords(Map<String, Integer> freq) {
        Set<String> unique = new TreeSet<>();
        for (Map.Entry<String, Integer> entry : freq.entrySet()) {
            if (entry.getValue() == 1) {
                unique.add(entry.getKey());
            }
        }
        return unique;
    }

    /** Find words longer than N characters */
    public static List<String> longWords(Map<String, Integer> freq, int minLength) {
        List<String> result = new ArrayList<>();
        for (String word : freq.keySet()) {
            if (word.length() >= minLength) {
                result.add(word);
            }
        }
        Collections.sort(result);
        return result;
    }

    /** Generate a simple histogram */
    public static void printHistogram(Map<String, Integer> freq) {
        // Find max count for scaling
        int maxCount = Collections.max(freq.values());
        int maxWordLen = freq.keySet().stream()
                .mapToInt(String::length)
                .max()
                .orElse(10);

        // Sort by frequency descending
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(freq.entrySet());
        sorted.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        System.out.println("\n📊 Word Frequency Histogram:");
        System.out.println("─".repeat(maxWordLen + 40));

        for (Map.Entry<String, Integer> entry : sorted) {
            String word = entry.getKey();
            int count = entry.getValue();
            int barLength = (int) ((double) count / maxCount * 30);
            String bar = "█".repeat(barLength);

            System.out.printf("  %-" + maxWordLen + "s │ %s %d%n", word, bar, count);
        }
        System.out.println("─".repeat(maxWordLen + 40));
    }

    // ──────────────────────────────────────────
    // Character frequency (interview pattern)
    // ──────────────────────────────────────────
    public static Map<Character, Integer> charFrequency(String text) {
        Map<Character, Integer> freq = new LinkedHashMap<>();
        for (char c : text.toLowerCase().toCharArray()) {
            if (Character.isLetter(c)) {
                freq.merge(c, 1, Integer::sum);
            }
        }
        return freq;
    }

    // ──────────────────────────────────────────
    // Anagram check (interview pattern)
    // ──────────────────────────────────────────
    public static boolean areAnagrams(String a, String b) {
        Map<Character, Integer> freqA = charFrequency(a);
        Map<Character, Integer> freqB = charFrequency(b);
        return freqA.equals(freqB);
    }

    // ──────────────────────────────────────────
    // MAIN — Demo
    // ──────────────────────────────────────────
    public static void main(String[] args) {

        String sampleText = "The quick brown fox jumps over the lazy dog. " +
                "The dog barked at the fox, and the fox ran away. " +
                "The quick brown fox was quicker than the lazy dog. " +
                "A fox and a dog can be friends, the fox thought.";

        System.out.println("════════════════════════════════════════════");
        System.out.println("   WORD FREQUENCY ANALYZER");
        System.out.println("════════════════════════════════════════════");
        System.out.println("\nInput text:");
        System.out.println("  \"" + sampleText + "\"\n");

        // 1. Count frequencies
        Map<String, Integer> freq = countWords(sampleText);
        System.out.println("📝 Total unique words: " + freq.size());
        int totalWords = freq.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("📝 Total word count: " + totalWords);

        // 2. Alphabetical listing
        System.out.println("\n📖 Alphabetical Order:");
        Map<String, Integer> alphabetical = sortedAlphabetically(freq);
        alphabetical.forEach((word, count) -> System.out.printf("  %-15s : %d%n", word, count));

        // 3. Top 5 most frequent
        System.out.println("\n🏆 Top 5 Most Frequent Words:");
        List<Map.Entry<String, Integer>> top5 = topN(freq, 5);
        int rank = 1;
        for (Map.Entry<String, Integer> entry : top5) {
            System.out.printf("  #%d  %-15s : %d occurrences%n",
                    rank++, entry.getKey(), entry.getValue());
        }

        // 4. Unique words (appear once)
        Set<String> unique = uniqueWords(freq);
        System.out.println("\n🔹 Words appearing only once (" + unique.size() + "):");
        System.out.println("  " + unique);

        // 5. Long words
        List<String> longW = longWords(freq, 5);
        System.out.println("\n📏 Words with 5+ characters:");
        System.out.println("  " + longW);

        // 6. Histogram
        printHistogram(freq);

        // 7. Character frequency
        System.out.println("\n🔤 Character Frequency:");
        Map<Character, Integer> charFreq = charFrequency(sampleText);
        charFreq.forEach((ch, count) -> System.out.printf("  '%c' : %d%n", ch, count));

        // 8. Anagram check
        System.out.println("\n🔄 Anagram Check:");
        System.out.println("  'listen' & 'silent' : " + areAnagrams("listen", "silent"));
        System.out.println("  'hello' & 'world'   : " + areAnagrams("hello", "world"));
        System.out.println("  'the eyes' & 'they see' : " + areAnagrams("the eyes", "they see"));
    }
}
