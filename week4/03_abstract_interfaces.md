# Day 3: Abstract Classes & Interfaces

## 🎯 Learning Goals
- Understand abstract classes and when to use them
- Master interfaces and default methods
- Know the difference between abstract classes and interfaces (interview must-know)
- Use interfaces for multiple inheritance
- Understand `Comparable` and `Comparator`

---

## 🎨 Abstract Classes

An **abstract class** is a class that **cannot be instantiated** directly. It's meant to be a base for subclasses. It can contain:
- ✅ Abstract methods (no body — subclasses MUST implement)
- ✅ Concrete methods (with body — subclasses inherit or override)
- ✅ Fields / constructors
- ✅ Static methods

```java
// Abstract class — can't do: new Shape()
public abstract class Shape {
    String color;

    // Constructor — used by subclasses via super()
    public Shape(String color) {
        this.color = color;
    }

    // Abstract method — NO BODY, just a signature
    // Every subclass MUST implement this
    abstract double area();
    abstract double perimeter();

    // Concrete method — inherited as-is (or overridden)
    void displayInfo() {
        System.out.println("Shape: " + getClass().getSimpleName());
        System.out.println("Color: " + color);
        System.out.println("Area: " + area());
        System.out.println("Perimeter: " + perimeter());
    }
}
```

### Concrete Subclasses
```java
public class Circle extends Shape {
    double radius;

    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }

    @Override
    double perimeter() {
        return 2 * Math.PI * radius;
    }
}

public class Rectangle extends Shape {
    double width, height;

    public Rectangle(String color, double width, double height) {
        super(color);
        this.width = width;
        this.height = height;
    }

    @Override
    double area() {
        return width * height;
    }

    @Override
    double perimeter() {
        return 2 * (width + height);
    }
}
```

### Using Abstract Classes with Polymorphism
```java
public class Main {
    public static void main(String[] args) {
        // Shape s = new Shape("Red");  // ❌ ERROR! Can't instantiate abstract class

        Shape circle = new Circle("Red", 5);
        Shape rect = new Rectangle("Blue", 4, 6);

        circle.displayInfo();
        // Shape: Circle
        // Color: Red
        // Area: 78.539...
        // Perimeter: 31.415...

        rect.displayInfo();
        // Shape: Rectangle
        // Color: Blue
        // Area: 24.0
        // Perimeter: 20.0
    }
}
```

---

## 📋 Interfaces

An **interface** is a contract — it says WHAT a class must do, but not HOW.

### Before Java 8 (Pure Contract)
```java
public interface Drawable {
    void draw();       // implicitly public and abstract
    int getLayer();    // implicitly public and abstract
}
```

### A Class `implements` an Interface
```java
public class Circle extends Shape implements Drawable {
    double radius;

    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    double area() { return Math.PI * radius * radius; }

    @Override
    double perimeter() { return 2 * Math.PI * radius; }

    @Override
    public void draw() {
        System.out.println("Drawing circle with radius " + radius);
    }

    @Override
    public int getLayer() {
        return 1;
    }
}
```

### Multiple Interfaces (Java's Answer to Multiple Inheritance)
```java
public interface Printable {
    void print();
}

public interface Exportable {
    void exportToPdf();
}

// A class can implement MULTIPLE interfaces
public class Report implements Printable, Exportable {
    String title;

    public Report(String title) {
        this.title = title;
    }

    @Override
    public void print() {
        System.out.println("Printing: " + title);
    }

    @Override
    public void exportToPdf() {
        System.out.println("Exporting " + title + " to PDF");
    }
}
```

---

## 🆕 Modern Interface Features (Java 8+)

### Default Methods (Java 8)
Interfaces can now have methods **with a body**:

```java
public interface Logger {
    // Abstract — must implement
    void log(String message);

    // Default — comes with implementation, can be overridden
    default void logWarning(String message) {
        log("WARNING: " + message);
    }

    default void logError(String message) {
        log("ERROR: " + message);
    }
}

// Only need to implement log() — get logWarning and logError for free
public class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}

// Usage:
Logger logger = new ConsoleLogger();
logger.log("App started");            // [LOG] App started
logger.logWarning("Low memory");       // [LOG] WARNING: Low memory
logger.logError("Crash detected");     // [LOG] ERROR: Crash detected
```

### Static Methods in Interfaces (Java 8)
```java
public interface MathOperations {
    double calculate(double a, double b);

    // Static helper method
    static double square(double x) {
        return x * x;
    }
}

// Called on the interface, not the object:
double result = MathOperations.square(5);  // 25.0
```

### Private Methods in Interfaces (Java 9)
```java
public interface DataProcessor {
    void process(String data);

    default void processAll(String[] items) {
        for (String item : items) {
            validate(item);  // Private helper
            process(item);
        }
    }

    // Private — used internally by default methods
    private void validate(String item) {
        if (item == null || item.isEmpty()) {
            throw new IllegalArgumentException("Invalid data");
        }
    }
}
```

---

## 🆚 Abstract Class vs Interface (Interview Must-Know ⭐⭐⭐)

| Feature | Abstract Class | Interface |
|---------|---------------|-----------|
| Keyword | `extends` | `implements` |
| Multiple? | ❌ One parent only | ✅ Multiple interfaces |
| Constructor? | ✅ Yes | ❌ No |
| Fields | ✅ Any type | ✅ Only `public static final` (constants) |
| Methods | Abstract + concrete | Abstract + default + static + private |
| Access modifiers | Any | `public` only (mostly) |
| Purpose | **IS-A** with shared state | **CAN-DO** capability contract |

### When to Use Which?

| Scenario | Use |
|----------|-----|
| Objects share common state (fields) | **Abstract class** |
| Objects share a common identity (IS-A) | **Abstract class** |
| Objects need a behaviour contract (CAN-DO) | **Interface** |
| A class needs multiple behaviours | **Interface** |
| You want to add a capability to unrelated classes | **Interface** |
| You have utility/template methods with shared code | **Abstract class** |

### Real-World Example
```java
// Abstract class — shared identity & state
abstract class Employee {
    String name;
    double baseSalary;

    Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }

    abstract double calculatePay();
}

// Interfaces — capabilities that any class can have
interface Taxable {
    double calculateTax();
}

interface Bonusable {
    double calculateBonus();
}

// Full-time employee — IS-A Employee, CAN-DO tax and bonus
class FullTimeEmployee extends Employee implements Taxable, Bonusable {
    public FullTimeEmployee(String name, double salary) {
        super(name, salary);
    }

    @Override
    public double calculatePay() { return baseSalary; }

    @Override
    public double calculateTax() { return baseSalary * 0.3; }

    @Override
    public double calculateBonus() { return baseSalary * 0.1; }
}

// Intern — IS-A Employee but NO tax or bonus
class Intern extends Employee {
    public Intern(String name) {
        super(name, 0);
    }

    @Override
    public double calculatePay() { return 0; }
}
```

---

## 📊 `Comparable` & `Comparator` (Interview Favourite ⭐)

### `Comparable<T>` — Natural Ordering
Implement this in your class to define its "default" sort order.

```java
public class Student implements Comparable<Student> {
    String name;
    double gpa;

    public Student(String name, double gpa) {
        this.name = name;
        this.gpa = gpa;
    }

    // Natural ordering: by GPA descending
    @Override
    public int compareTo(Student other) {
        return Double.compare(other.gpa, this.gpa);  // Descending
        // For ascending: Double.compare(this.gpa, other.gpa)
    }

    @Override
    public String toString() {
        return name + " (GPA: " + gpa + ")";
    }
}

// Usage:
List<Student> students = new ArrayList<>();
students.add(new Student("Alice", 3.8));
students.add(new Student("Bob", 3.5));
students.add(new Student("Charlie", 3.9));

Collections.sort(students);  // Uses compareTo()
System.out.println(students);
// [Charlie (GPA: 3.9), Alice (GPA: 3.8), Bob (GPA: 3.5)]
```

### `Comparator<T>` — Custom Ordering
Use when you need a DIFFERENT sort order than the natural one, or for classes you didn't write.

```java
import java.util.Comparator;

// Sort by name alphabetically
Comparator<Student> byName = new Comparator<Student>() {
    @Override
    public int compare(Student a, Student b) {
        return a.name.compareTo(b.name);
    }
};

// Lambda shorthand (Java 8+):
Comparator<Student> byName = (a, b) -> a.name.compareTo(b.name);

// Even shorter:
Comparator<Student> byName = Comparator.comparing(s -> s.name);

// Usage:
Collections.sort(students, byName);
// [Alice (GPA: 3.8), Bob (GPA: 3.5), Charlie (GPA: 3.9)]

// Or with method reference:
students.sort(Comparator.comparing(s -> s.name));
```

### Comparable vs Comparator
| Feature | Comparable | Comparator |
|---------|-----------|------------|
| Package | `java.lang` | `java.util` |
| Method | `compareTo(T)` | `compare(T, T)` |
| Modifies class? | ✅ Yes | ❌ No |
| Orderings | One (natural) | Unlimited custom |
| Usage | `Collections.sort(list)` | `Collections.sort(list, comp)` |

---

## 🧩 Functional Interfaces (Preview)

A **functional interface** has exactly ONE abstract method. It can be used with **lambda expressions**:

```java
@FunctionalInterface
public interface Greeting {
    void greet(String name);
}

// Traditional implementation
Greeting g1 = new Greeting() {
    @Override
    public void greet(String name) {
        System.out.println("Hello, " + name + "!");
    }
};

// Lambda (shorter)
Greeting g2 = name -> System.out.println("Hello, " + name + "!");

g1.greet("Alice");  // Hello, Alice!
g2.greet("Bob");    // Hello, Bob!
```

> **Common functional interfaces**: `Runnable`, `Callable`, `Comparator`, `Predicate`, `Function`, `Consumer`, `Supplier`

---

## 🧪 Try It Yourself

```java
// Interface
interface Resizable {
    void resize(double factor);
}

// Abstract class
abstract class Shape {
    String color;
    abstract double area();

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [color=" + color + ", area=" + String.format("%.2f", area()) + "]";
    }
}

// Concrete class implementing both
class Circle extends Shape implements Resizable {
    double radius;

    Circle(String color, double radius) {
        this.color = color;
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public void resize(double factor) {
        this.radius *= factor;
        System.out.println("Resized to radius: " + radius);
    }
}

// Main
public class Main {
    public static void main(String[] args) {
        Circle c = new Circle("Red", 5);
        System.out.println(c);          // Circle [color=Red, area=78.54]
        c.resize(2);                    // Resized to radius: 10.0
        System.out.println(c);          // Circle [color=Red, area=314.16]
    }
}
```

---

## 🔑 Key Takeaways
- **Abstract class**: can't be instantiated, can have constructors and any fields, use for IS-A with shared state
- **Interface**: pure contract (mostly), allows **multiple implementation**, use for CAN-DO capabilities
- Java 8+ interfaces can have `default`, `static`, and (Java 9+) `private` methods
- A class can extend ONE abstract class but implement MANY interfaces
- Use `Comparable` for natural ordering, `Comparator` for custom/alternate ordering
- `@FunctionalInterface` + lambdas = concise, powerful code
