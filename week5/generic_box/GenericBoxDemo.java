import java.util.*;

/**
 * Generic Box Demo — Week 5 Hands-On Project #2
 * 
 * Demonstrates generics through a comprehensive Box<T> implementation
 * with various constraints, utility methods, and patterns.
 * 
 * Concepts covered:
 * - Generic classes with type parameters
 * - Bounded type parameters
 * - Generic methods
 * - Wildcard usage
 * - Generic interfaces
 * - The Comparable pattern
 */

// ============================================================
// 1. Basic Generic Box
// ============================================================
class Box<T> {
    private T item;

    public Box(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public boolean isEmpty() {
        return item == null;
    }

    // Generic method — transforms the item using a function
    public <R> Box<R> map(java.util.function.Function<T, R> mapper) {
        if (item == null) {
            return new Box<>(null);
        }
        return new Box<>(mapper.apply(item));
    }

    @Override
    public String toString() {
        return "Box[" + (item != null ? item : "empty") + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Box<?> box = (Box<?>) o;
        return Objects.equals(item, box.item);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(item);
    }
}

// ============================================================
// 2. Bounded Box — Only accepts Comparable types
// ============================================================
class ComparableBox<T extends Comparable<T>> extends Box<T> {

    public ComparableBox(T item) {
        super(item);
    }

    public boolean isGreaterThan(T other) {
        return getItem().compareTo(other) > 0;
    }

    public boolean isLessThan(T other) {
        return getItem().compareTo(other) < 0;
    }

    public T max(T other) {
        return getItem().compareTo(other) >= 0 ? getItem() : other;
    }

    public T min(T other) {
        return getItem().compareTo(other) <= 0 ? getItem() : other;
    }
}

// ============================================================
// 3. NumberBox — Only accepts Number types
// ============================================================
class NumberBox<T extends Number> extends Box<T> {

    public NumberBox(T item) {
        super(item);
    }

    public double doubleValue() {
        return getItem().doubleValue();
    }

    public int intValue() {
        return getItem().intValue();
    }

    public boolean isPositive() {
        return doubleValue() > 0;
    }

    public boolean isNegative() {
        return doubleValue() < 0;
    }

    public boolean isZero() {
        return doubleValue() == 0;
    }

    // Wildcard method: accepts any NumberBox
    public double add(NumberBox<?> other) {
        return this.doubleValue() + other.doubleValue();
    }
}

// ============================================================
// 4. Pair<A, B> — Two-type generic
// ============================================================
class Pair<A, B> {
    private final A first;
    private final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public Pair<B, A> swap() {
        return new Pair<>(second, first);
    }

    public static <X, Y> Pair<X, Y> of(X first, Y second) {
        return new Pair<>(first, second);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}

// ============================================================
// 5. Generic Utility Methods
// ============================================================
class BoxUtils {

    // Swap contents of two boxes
    public static <T> void swap(Box<T> a, Box<T> b) {
        T temp = a.getItem();
        a.setItem(b.getItem());
        b.setItem(temp);
    }

    // Find the box containing the maximum value
    public static <T extends Comparable<T>> Box<T> maxBox(Box<T> a, Box<T> b) {
        if (a.getItem().compareTo(b.getItem()) >= 0) {
            return a;
        }
        return b;
    }

    // Print any box using an unbounded wildcard
    public static void printBox(Box<?> box) {
        System.out.println("Box contains: " + box.getItem());
    }

    // Sum all number boxes (upper bounded wildcard)
    public static double sumBoxes(List<? extends NumberBox<?>> boxes) {
        double sum = 0;
        for (NumberBox<?> box : boxes) {
            sum += box.doubleValue();
        }
        return sum;
    }

    // Create a list of boxes from an array
    public static <T> List<Box<T>> boxAll(T[] items) {
        List<Box<T>> boxes = new ArrayList<>();
        for (T item : items) {
            boxes.add(new Box<>(item));
        }
        return boxes;
    }
}

// ============================================================
// Main Demo
// ============================================================
public class GenericBoxDemo {
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║         GENERIC BOX DEMO             ║");
        System.out.println("╚══════════════════════════════════════╝\n");

        // --- 1. Basic Box ---
        System.out.println("=== 1. Basic Box<T> ===");
        Box<String> stringBox = new Box<>("Hello, Generics!");
        Box<Integer> intBox = new Box<>(42);
        Box<List<String>> listBox = new Box<>(List.of("a", "b", "c"));

        System.out.println(stringBox); // Box[Hello, Generics!]
        System.out.println(intBox); // Box[42]
        System.out.println(listBox); // Box[[a, b, c]]

        // Map: transform the content
        Box<Integer> lengthBox = stringBox.map(String::length);
        System.out.println("String length: " + lengthBox); // Box[16]

        Box<String> doubledBox = intBox.map(n -> "Value: " + (n * 2));
        System.out.println(doubledBox); // Box[Value: 84]

        // --- 2. ComparableBox ---
        System.out.println("\n=== 2. ComparableBox<T> ===");
        ComparableBox<Integer> numBox = new ComparableBox<>(50);
        System.out.println("50 > 30? " + numBox.isGreaterThan(30)); // true
        System.out.println("50 < 70? " + numBox.isLessThan(70)); // true
        System.out.println("Max(50, 75): " + numBox.max(75)); // 75
        System.out.println("Min(50, 75): " + numBox.min(75)); // 50

        ComparableBox<String> wordBox = new ComparableBox<>("banana");
        System.out.println("banana > apple? " + wordBox.isGreaterThan("apple")); // true

        // --- 3. NumberBox ---
        System.out.println("\n=== 3. NumberBox<T> ===");
        NumberBox<Integer> intNumBox = new NumberBox<>(42);
        NumberBox<Double> doubleNumBox = new NumberBox<>(3.14);

        System.out.println("42 as double: " + intNumBox.doubleValue()); // 42.0
        System.out.println("3.14 as int: " + doubleNumBox.intValue()); // 3
        System.out.println("42 is positive? " + intNumBox.isPositive()); // true
        System.out.println("42 + 3.14 = " + intNumBox.add(doubleNumBox)); // 45.14

        // --- 4. Pair ---
        System.out.println("\n=== 4. Pair<A, B> ===");
        Pair<String, Integer> nameAge = Pair.of("Alice", 25);
        System.out.println(nameAge); // (Alice, 25)
        System.out.println("Name: " + nameAge.getFirst()); // Alice
        System.out.println("Age: " + nameAge.getSecond()); // 25
        System.out.println("Swapped: " + nameAge.swap()); // (25, Alice)

        // --- 5. Utility Methods ---
        System.out.println("\n=== 5. BoxUtils ===");
        Box<String> a = new Box<>("First");
        Box<String> b = new Box<>("Second");
        System.out.println("Before swap: " + a + ", " + b);
        BoxUtils.swap(a, b);
        System.out.println("After swap:  " + a + ", " + b);

        Box<Integer> x = new Box<>(10);
        Box<Integer> y = new Box<>(20);
        System.out.println("Max box: " + BoxUtils.maxBox(
                new ComparableBox<>(10), new ComparableBox<>(20)));

        // Box all
        String[] words = { "Java", "Generics", "Rock" };
        List<Box<String>> boxes = BoxUtils.boxAll(words);
        boxes.forEach(System.out::println);

        // Wildcard: print any box
        BoxUtils.printBox(stringBox);
        BoxUtils.printBox(intBox);

        System.out.println("\n✅ All demos completed successfully!");
    }
}
