# Day 1: Classes, Objects & Constructors

## 🎯 Learning Goals
- Understand the blueprint-to-object relationship
- Create classes with fields (instance variables) and methods
- Master constructors and constructor overloading
- Use the `this` keyword correctly
- Understand `toString()` and object printing

---

## 🧱 What Is a Class?

A **class** is a blueprint. An **object** is an instance built from that blueprint.

```
CLASS (Blueprint)          OBJECT (Instance)
┌──────────────┐          ┌──────────────┐
│  Car         │  ──────► │  myCar       │
│  - brand     │          │  - "Toyota"  │
│  - speed     │          │  - 120       │
│  + drive()   │          │  + drive()   │
└──────────────┘          └──────────────┘
```

Think of a class like a cookie cutter and objects like the cookies — same shape, different decorations.

---

## 📦 Creating Your First Class

```java
public class Dog {
    // Fields (instance variables) — what the object HAS
    String name;
    String breed;
    int age;

    // Method — what the object DOES
    void bark() {
        System.out.println(name + " says: Woof!");
    }

    void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Breed: " + breed);
        System.out.println("Age: " + age);
    }
}
```

### Creating Objects
```java
public class Main {
    public static void main(String[] args) {
        // Create objects using "new" keyword
        Dog dog1 = new Dog();
        dog1.name = "Buddy";
        dog1.breed = "Golden Retriever";
        dog1.age = 3;

        Dog dog2 = new Dog();
        dog2.name = "Max";
        dog2.breed = "German Shepherd";
        dog2.age = 5;

        dog1.bark();          // Buddy says: Woof!
        dog2.bark();          // Max says: Woof!
        dog1.displayInfo();   // Name: Buddy, Breed: Golden Retriever, Age: 3
    }
}
```

> **Key Point**: Each object has its OWN copy of the instance variables. Changing `dog1.name` does NOT affect `dog2.name`.

---

## 🔨 Constructors

A constructor is a special method that runs when you create an object with `new`. It initialises the object's state.

### Rules for Constructors
1. **Same name** as the class
2. **No return type** (not even `void`)
3. Called automatically when you write `new ClassName()`

### Default Constructor
If you don't write any constructor, Java provides a hidden **no-argument constructor** that sets fields to their defaults (`0`, `null`, `false`).

```java
public class Student {
    String name;
    int age;
    double gpa;

    // Default constructor (explicit)
    public Student() {
        name = "Unknown";
        age = 0;
        gpa = 0.0;
    }
}

// Usage:
Student s = new Student();
System.out.println(s.name);  // "Unknown"
```

### Parameterized Constructor
```java
public class Student {
    String name;
    int age;
    double gpa;

    // Parameterized constructor
    public Student(String name, int age, double gpa) {
        this.name = name;   // "this" refers to the current object
        this.age = age;
        this.gpa = gpa;
    }
}

// Usage:
Student s = new Student("Alice", 20, 3.8);
System.out.println(s.name);  // "Alice"
```

---

## 🔑 The `this` Keyword

`this` refers to **the current object** — the one whose method is being called.

### Use Case 1: Disambiguate fields from parameters
```java
public class BankAccount {
    double balance;
    String owner;

    public BankAccount(String owner, double balance) {
        this.owner = owner;       // this.owner = field, owner = parameter
        this.balance = balance;   // this.balance = field, balance = parameter
    }
}
```

### Use Case 2: Call another constructor
```java
public class BankAccount {
    String owner;
    double balance;

    // Full constructor
    public BankAccount(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    // Convenience constructor — delegates to the full one
    public BankAccount(String owner) {
        this(owner, 0.0);  // Calls the 2-parameter constructor
    }

    // Default constructor
    public BankAccount() {
        this("Unknown", 0.0);  // Calls the 2-parameter constructor
    }
}
```

> **Rule**: `this(...)` must be the **first statement** in the constructor.

### Use Case 3: Return current object (method chaining)
```java
public class Builder {
    String name;
    int age;

    public Builder setName(String name) {
        this.name = name;
        return this;  // return current object
    }

    public Builder setAge(int age) {
        this.age = age;
        return this;
    }
}

// Fluent/chained usage:
Builder b = new Builder().setName("Alice").setAge(25);
```

---

## 🔄 Constructor Overloading

You can have **multiple constructors** with different parameter lists:

```java
public class Rectangle {
    double width;
    double height;

    // No-arg: default 1x1 rectangle
    public Rectangle() {
        this(1.0, 1.0);
    }

    // Square: equal width and height
    public Rectangle(double side) {
        this(side, side);
    }

    // Full constructor
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    double area() {
        return width * height;
    }

    double perimeter() {
        return 2 * (width + height);
    }
}

// Usage:
Rectangle r1 = new Rectangle();          // 1x1
Rectangle r2 = new Rectangle(5);         // 5x5 square
Rectangle r3 = new Rectangle(4, 6);      // 4x6
System.out.println(r1.area());  // 1.0
System.out.println(r2.area());  // 25.0
System.out.println(r3.area());  // 24.0
```

---

## 📝 The `toString()` Method

By default, printing an object shows something like `Student@15db9742`. Override `toString()` to get readable output:

```java
public class Student {
    String name;
    int age;
    double gpa;

    public Student(String name, int age, double gpa) {
        this.name = name;
        this.age = age;
        this.gpa = gpa;
    }

    // Override toString() for readable printing
    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + ", gpa=" + gpa + "}";
    }
}

// Usage:
Student s = new Student("Alice", 20, 3.8);
System.out.println(s);  // Student{name='Alice', age=20, gpa=3.8}
// println automatically calls toString()!
```

> **Interview Tip**: Always override `toString()` in your classes. Interviewers expect it, and it makes debugging much easier.

---

## 🏗️ `static` vs Instance Members

```java
public class Counter {
    // Instance variable — each object has its own
    String label;

    // Static variable — shared across ALL objects
    static int totalCount = 0;

    public Counter(String label) {
        this.label = label;
        totalCount++;  // Increments for every new object
    }

    // Instance method — needs an object to call
    void display() {
        System.out.println(label + " (total: " + totalCount + ")");
    }

    // Static method — called on the CLASS, not an object
    static int getTotal() {
        return totalCount;
    }
}

// Usage:
Counter c1 = new Counter("First");
Counter c2 = new Counter("Second");
Counter c3 = new Counter("Third");

c1.display();                    // First (total: 3)
System.out.println(Counter.getTotal());  // 3  (called on class)
```

### Quick Comparison
| Feature | Instance | Static |
|---------|----------|--------|
| Belongs to | Each object | The class itself |
| Access | `object.method()` | `ClassName.method()` |
| Can access instance fields? | ✅ Yes | ❌ No |
| Memory | One per object | One total |
| Example | `dog1.bark()` | `Math.sqrt(25)` |

---

## 🧩 Objects as Method Parameters & Return Types

```java
public class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Method that takes an object as a parameter
    double distanceTo(Point other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Static method that returns a new object
    static Point midpoint(Point a, Point b) {
        return new Point((a.x + b.x) / 2, (a.y + b.y) / 2);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

// Usage:
Point p1 = new Point(0, 0);
Point p2 = new Point(3, 4);
System.out.println(p1.distanceTo(p2));    // 5.0
System.out.println(Point.midpoint(p1, p2)); // (1, 2)
```

---

## 🧪 Try It Yourself

```java
public class BankAccount {
    String owner;
    double balance;

    public BankAccount(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    public BankAccount(String owner) {
        this(owner, 0.0);
    }

    void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited $" + amount + ". Balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew $" + amount + ". Balance: $" + balance);
        } else {
            System.out.println("Invalid withdrawal! Balance: $" + balance);
        }
    }

    void transferTo(BankAccount other, double amount) {
        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            other.balance += amount;
            System.out.println("Transferred $" + amount + " to " + other.owner);
        }
    }

    @Override
    public String toString() {
        return owner + "'s Account: $" + balance;
    }

    public static void main(String[] args) {
        BankAccount alice = new BankAccount("Alice", 1000);
        BankAccount bob = new BankAccount("Bob", 500);

        alice.deposit(200);
        alice.withdraw(150);
        alice.transferTo(bob, 300);

        System.out.println(alice);  // Alice's Account: $750.0
        System.out.println(bob);    // Bob's Account: $800.0
    }
}
```

---

## 🔑 Key Takeaways
- A **class** is a blueprint; an **object** is a concrete instance
- **Constructors** initialise objects — they have no return type
- Use `this` to refer to the current object's fields
- `this(...)` chains constructors — must be first statement
- **Constructor overloading** = multiple constructors with different parameters
- Override `toString()` for human-readable printing
- **Static** members belong to the class; **instance** members belong to individual objects
- Objects can be passed to and returned from methods just like primitives
