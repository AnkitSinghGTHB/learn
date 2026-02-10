public class Calculator {

    // Method that returns a value
    public static int add(int a, int b) {
        return a + b;
    }

    // Method that returns nothing (void)
    public static void greet(String name) {
        System.out.println("Hello, " + name + "!");
    }

    // Method with no parameters
    public static double getPi() {
        return 3.14159;
    }

    public static void main(String[] args) {
        int sum = add(5, 3); // 8
        greet("Alice"); // Hello, Alice!
        double pi = getPi(); // 3.14159
        System.out.println(sum);
        System.out.println(pi);
    }
}