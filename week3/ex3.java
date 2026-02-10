import java.util.Scanner;

public class ex3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter length of array: ");
        int n = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter element " + (i + 1) + ": ");
            arr[i] = scanner.nextInt();
        }
        scanner.close();

        // max
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        System.out.println("Maximum element is: " + max);

        // min
        int min = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        System.out.println("Minimum element is: " + min);

        // sum
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += arr[i];
        }
        System.out.println("Sum of elements is: " + sum);

        // avg
        double avg = (double) sum / n;
        System.out.println("Average element is: " + avg);

        // above avg
        int abvavg = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] > avg) {
                abvavg++;
            }
        }
        System.out.println("Number of elements above average is: " + abvavg);
    }
}
