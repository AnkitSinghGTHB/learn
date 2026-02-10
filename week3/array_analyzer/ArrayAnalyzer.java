import java.util.Arrays;

/**
 * Array Analyzer - Week 3 Hands-On Project #2
 * 
 * This program demonstrates array operations:
 * - Finding maximum and minimum values
 * - Calculating sum and average
 * - Searching for values
 * - Sorting
 */
public class ArrayAnalyzer {
    
    /**
     * Finds the maximum value in an array.
     */
    public static int findMax(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
    
    /**
     * Finds the minimum value in an array.
     */
    public static int findMin(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }
    
    /**
     * Finds the index of the maximum value.
     */
    public static int findMaxIndex(int[] arr) {
        int maxIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
    
    /**
     * Finds the index of the minimum value.
     */
    public static int findMinIndex(int[] arr) {
        int minIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[minIndex]) {
                minIndex = i;
            }
        }
        return minIndex;
    }
    
    /**
     * Calculates the sum of all elements.
     */
    public static int sum(int[] arr) {
        int total = 0;
        for (int num : arr) {
            total += num;
        }
        return total;
    }
    
    /**
     * Calculates the average of all elements.
     */
    public static double average(int[] arr) {
        return (double) sum(arr) / arr.length;
    }
    
    /**
     * Counts elements above a threshold.
     */
    public static int countAbove(int[] arr, int threshold) {
        int count = 0;
        for (int num : arr) {
            if (num > threshold) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Counts elements below a threshold.
     */
    public static int countBelow(int[] arr, int threshold) {
        int count = 0;
        for (int num : arr) {
            if (num < threshold) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Linear search - returns index of element or -1 if not found.
     */
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;  // Not found
    }
    
    /**
     * Creates a sorted copy of the array.
     */
    public static int[] getSorted(int[] arr) {
        int[] sorted = arr.clone();  // Create a copy
        Arrays.sort(sorted);
        return sorted;
    }
    
    /**
     * Reverses an array in place.
     */
    public static void reverse(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left < right) {
            // Swap elements
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            
            left++;
            right--;
        }
    }
    
    /**
     * Prints array in a formatted way.
     */
    public static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
    
    /**
     * Prints a simple bar chart visualization.
     */
    public static void printBarChart(int[] arr, int maxBars) {
        int max = findMax(arr);
        
        for (int i = 0; i < arr.length; i++) {
            int bars = (int) ((double) arr[i] / max * maxBars);
            System.out.printf("Index %2d (%3d): ", i, arr[i]);
            for (int j = 0; j < bars; j++) {
                System.out.print("█");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        // Test data
        int[] numbers = {45, 12, 89, 33, 67, 23, 94, 56, 78, 41};
        
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║         Array Analyzer v1.0            ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
        
        // Display array
        System.out.print("Original Array: ");
        printArray(numbers);
        System.out.println();
        
        // Basic statistics
        System.out.println("── Basic Statistics ───────────────────");
        System.out.println("Maximum:  " + findMax(numbers) + " (at index " + findMaxIndex(numbers) + ")");
        System.out.println("Minimum:  " + findMin(numbers) + " (at index " + findMinIndex(numbers) + ")");
        System.out.println("Sum:      " + sum(numbers));
        System.out.printf("Average:  %.2f%n", average(numbers));
        System.out.println();
        
        // Threshold analysis
        double avg = average(numbers);
        System.out.println("── Threshold Analysis ─────────────────");
        System.out.printf("Above average (%.2f): %d elements%n", avg, countAbove(numbers, (int) avg));
        System.out.printf("Below average (%.2f): %d elements%n", avg, countBelow(numbers, (int) avg));
        System.out.println();
        
        // Search demo
        System.out.println("── Search Demo ────────────────────────");
        int searchTarget = 67;
        int foundIndex = linearSearch(numbers, searchTarget);
        if (foundIndex != -1) {
            System.out.println("Found " + searchTarget + " at index " + foundIndex);
        } else {
            System.out.println(searchTarget + " not found");
        }
        
        searchTarget = 100;
        foundIndex = linearSearch(numbers, searchTarget);
        if (foundIndex != -1) {
            System.out.println("Found " + searchTarget + " at index " + foundIndex);
        } else {
            System.out.println(searchTarget + " not found");
        }
        System.out.println();
        
        // Sorted view
        System.out.println("── Sorted View ────────────────────────");
        System.out.print("Sorted (ascending):  ");
        printArray(getSorted(numbers));
        
        int[] descending = getSorted(numbers);
        reverse(descending);
        System.out.print("Sorted (descending): ");
        printArray(descending);
        System.out.println();
        
        // Visualization
        System.out.println("── Visualization ──────────────────────");
        printBarChart(numbers, 20);
    }
}
