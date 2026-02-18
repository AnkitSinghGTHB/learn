# Day 4: Access Modifiers & Encapsulation

## 🎯 Learning Goals
- Master all four access modifiers
- Understand encapsulation and data hiding
- Use getters and setters with validation
- Apply the JavaBean naming convention
- Understand immutable objects

---

## 🔐 The Four Access Modifiers

Java has **four** levels of access control:

```
┌─────────────────────────────────────────────────────────┐
│                       public                            │
│  Accessible from EVERYWHERE                             │
│  ┌─────────────────────────────────────────────────┐    │
│  │                   protected                      │    │
│  │  Accessible from same package + subclasses       │    │
│  │  ┌──────────────────────────────────────────┐    │    │
│  │  │              default (no keyword)         │    │    │
│  │  │  Accessible from same package only        │    │    │
│  │  │  ┌───────────────────────────────────┐    │    │    │
│  │  │  │           private                  │    │    │    │
│  │  │  │  Accessible only within same class │    │    │    │
│  │  │  └───────────────────────────────────┘    │    │    │
│  │  └──────────────────────────────────────────┘    │    │
│  └─────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────┘
```

### Summary Table (Interview Question ⭐)

| Modifier | Same Class | Same Package | Subclass (other pkg) | Everywhere |
|----------|-----------|-------------|---------------------|------------|
| `private` | ✅ | ❌ | ❌ | ❌ |
| default | ✅ | ✅ | ❌ | ❌ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| `public` | ✅ | ✅ | ✅ | ✅ |

### Examples
```java
public class Person {
    public String name;          // Anyone can access
    protected String email;      // Same package + subclasses
    String phone;                // Same package only (default)
    private String password;     // Only within this class
}
```

> **Interview Tip**: "default" is NOT a keyword — it's the absence of a modifier. (Don't confuse with `default` in switch statements or interface default methods.)

---

## 🛡️ Encapsulation (Data Hiding)

**Encapsulation** means:
1. Make fields `private`
2. Provide `public` getters/setters to access them
3. Add **validation** inside setters

### Why Not Just Use Public Fields?

```java
// ❌ BAD — No protection
public class BankAccount {
    public double balance;  // Anyone can set balance to -1000!
}

BankAccount acc = new BankAccount();
acc.balance = -99999;  // No validation, no protection!
```

```java
// ✅ GOOD — Encapsulated
public class BankAccount {
    private double balance;  // Hidden from outside

    public BankAccount(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = initialBalance;
    }

    // Getter — controlled read access
    public double getBalance() {
        return balance;
    }

    // No direct setter for balance — use deposit/withdraw instead
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit must be positive");
        }
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;  // Failed
        }
        balance -= amount;
        return true;  // Success
    }
}
```

---

## 📐 Getter & Setter Patterns

### Standard Getter/Setter
```java
public class Student {
    private String name;
    private int age;
    private double gpa;

    // Constructor
    public Student(String name, int age, double gpa) {
        this.name = name;
        setAge(age);   // Use setter for validation!
        setGpa(gpa);
    }

    // --- Getters ---
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getGpa() {
        return gpa;
    }

    // --- Setters with validation ---
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name.trim();
    }

    public void setAge(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Invalid age: " + age);
        }
        this.age = age;
    }

    public void setGpa(double gpa) {
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be 0.0-4.0");
        }
        this.gpa = gpa;
    }

    // Boolean getter uses "is" prefix
    public boolean isHonorRoll() {
        return gpa >= 3.5;
    }

    @Override
    public String toString() {
        return name + " (Age: " + age + ", GPA: " + gpa + ")";
    }
}
```

### JavaBean Naming Conventions
| Type | Convention | Example |
|------|-----------|---------|
| Getter | `getFieldName()` | `getName()` |
| Setter | `setFieldName(value)` | `setName("Alice")` |
| Boolean getter | `isFieldName()` | `isActive()` |

> **Why Follow This?** Frameworks like Spring, Jackson, and Hibernate rely on these naming conventions to automatically find getters/setters.

---

## 🔒 Read-Only & Write-Only Fields

### Read-Only (Getter only, no setter)
```java
public class Employee {
    private final int id;    // assigned once
    private String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;           // Can read
    }
    // No setId() — can't change after creation

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;    // Can change name, but not id
    }
}
```

### Write-Only (Setter only, no getter) — Rare
```java
public class PasswordManager {
    private String passwordHash;

    public void setPassword(String password) {
        this.passwordHash = hashPassword(password);
    }
    // No getPassword() — we never expose the password!

    public boolean verifyPassword(String attempt) {
        return hashPassword(attempt).equals(passwordHash);
    }

    private String hashPassword(String password) {
        return Integer.toHexString(password.hashCode());  // Simplified
    }
}
```

---

## 🧊 Immutable Objects

An **immutable** object cannot be changed after creation. Think `String` in Java.

### How to Make a Class Immutable
1. Make the class `final` (can't extend)
2. Make all fields `private final`
3. Provide only getters, no setters
4. If fields are mutable objects (arrays, lists), return defensive copies

```java
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public final class ImmutableStudent {
    private final String name;
    private final int age;
    private final List<String> courses;

    public ImmutableStudent(String name, int age, List<String> courses) {
        this.name = name;
        this.age = age;
        // Defensive copy — don't store the original list reference
        this.courses = List.copyOf(courses);
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    // Return unmodifiable view — caller can't modify our list
    public List<String> getCourses() {
        return courses;  // Already immutable from List.copyOf
    }

    @Override
    public String toString() {
        return name + " (Age: " + age + ", Courses: " + courses + ")";
    }
}

// Usage:
List<String> myCourses = new ArrayList<>(Arrays.asList("Math", "CS"));
ImmutableStudent s = new ImmutableStudent("Alice", 20, myCourses);

// s.setName("Bob");           // ❌ No setter
// s.getCourses().add("Art");  // ❌ UnsupportedOperationException
myCourses.add("Art");          // Original list changes...
System.out.println(s.getCourses());  // [Math, CS] — our object is safe!
```

> **Interview Tip**: Interviewers love asking "How do you make a class immutable?" and "Why is `String` immutable?"

---

## 📦 Java Records (Java 16+) — Immutable Data in One Line

```java
// This single line generates:
// - private final fields
// - constructor
// - getters (name(), age(), gpa() — no "get" prefix)
// - equals(), hashCode(), toString()
public record StudentRecord(String name, int age, double gpa) {}

// Usage:
StudentRecord s = new StudentRecord("Alice", 20, 3.8);
System.out.println(s.name());    // "Alice"
System.out.println(s.age());     // 20
System.out.println(s);           // StudentRecord[name=Alice, age=20, gpa=3.8]
```

### Records with Validation
```java
public record StudentRecord(String name, int age, double gpa) {
    // "Compact constructor" for validation
    public StudentRecord {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name required");
        if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
        if (gpa < 0 || gpa > 4.0) throw new IllegalArgumentException("Invalid GPA");
    }
}
```

---

## 🏗️ Packages & Access Control

```
src/
├── com/company/model/
│   ├── Student.java       (package com.company.model)
│   └── Course.java        (package com.company.model)
├── com/company/service/
│   └── GradeService.java  (package com.company.service)
└── com/company/Main.java  (package com.company)
```

```java
package com.company.model;

public class Student {
    private int id;          // Only Student class
    String nickname;         // Student + Course (same package)
    protected double gpa;    // Same package + subclasses in other packages
    public String name;      // Everyone
}
```

```java
package com.company.service;

import com.company.model.Student;

public class GradeService {
    void processStudent() {
        Student s = new Student();
        // s.id            ❌ private — can't access
        // s.nickname      ❌ default — different package
        // s.gpa           ❌ protected — not a subclass
        s.name = "Alice";  // ✅ public — accessible everywhere
    }
}
```

---

## 💼 Common Interview Questions

1. **What is encapsulation?**
   → Bundling data (fields) and methods that operate on that data together, while hiding internal state behind access modifiers.

2. **Why make fields private?**
   → To control access, enforce validation, allow internal changes without breaking external code.

3. **Difference between access modifiers?**
   → See the table above. Key: `private` < default < `protected` < `public`.

4. **What is an immutable class? How do you create one?**
   → A class whose objects cannot change after creation. Use `final` class, `final` fields, no setters, defensive copies.

5. **Why is String immutable in Java?**
   → Security (used in class loading, networking), thread safety, string pool caching, and hashCode caching.

6. **What are Java Records?**
   → Compact syntax for immutable data-carrying classes (Java 16+).

---

## 🔑 Key Takeaways
- Use `private` by default for fields — expose via getters/setters
- Always validate in setters — that's the whole point of encapsulation
- Follow JavaBean conventions: `getX()`, `setX()`, `isX()` for booleans
- Immutable objects = `final` class + `final` fields + no setters + defensive copies
- Java Records auto-generate immutable classes with one line
- `protected` is for subclass access across packages — rarely used in day-to-day code
