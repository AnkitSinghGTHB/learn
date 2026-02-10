import java.util.Scanner;
import java.util.Random;

public class ex2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int number = random.nextInt(100) + 1;
        int guess = 0;
        int attempts = 0;
        while (guess != number) {
            System.out.print("Guess a number between 1 and 100: ");
            guess = scanner.nextInt();
            attempts++;
            if (guess < number) {
                System.out.println("Too low!");
            } else if (guess > number) {
                System.out.println("Too high!");
            } else {
                System.out.println("Congratulations! You guessed the number in " + attempts + " attempts.");
            }
        }
        scanner.close();
    }
}