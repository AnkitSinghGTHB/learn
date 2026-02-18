# Day 2: Inheritance & Polymorphism

## 🎯 Learning Goals
- Understand IS-A relationships and the `extends` keyword
- Use `super` to call parent constructors and methods
- Master method overriding vs method overloading
- Understand runtime polymorphism (dynamic dispatch)
- Know about the `Object` class and `final` keyword

---

## 🧬 What Is Inheritance?

Inheritance lets a class **reuse** fields and methods from another class. The child class **IS-A** type of the parent class.

```
        Animal          ← Parent / Superclass / Base class
       /      \
     Dog      Cat       ← Child / Subclass / Derived class
      |
  GoldenRetriever       ← Grandchild (multi-level inheritance)
```

### Why Use Inheritance?
- **Code reuse** — Don't repeat common fields/methods
- **Logical hierarchy** — Models real-world relationships
- **Polymorphism** — Treat child objects as parent types

---

## ✨ Basic Inheritance with `extends`

```java
// Parent class
public class Animal {
    String name;
    int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    void eat() {
        System.out.println(name + " is eating.");
    }

    void sleep() {
        System.out.println(name + " is sleeping.");
    }

    @Override
    public String toString() {
        return name + " (age " + age + ")";
    }
}

// Child class — inherits ALL non-private members from Animal
public class Dog extends Animal {
    String breed;

    public Dog(String name, int age, String breed) {
        super(name, age);  // MUST call parent constructor first
        this.breed = breed;
    }

    // New method specific to Dog
    void fetch() {
        System.out.println(name + " fetches the ball!");
    }
}

// Usage:
Dog d = new Dog("Buddy", 3, "Labrador");
d.eat();    // ✅ Inherited from Animal: "Buddy is eating."
d.sleep();  // ✅ Inherited from Animal: "Buddy is sleeping."
d.fetch();  // ✅ Dog's own method: "Buddy fetches the ball!"
```

---

## 🔗 The `super` Keyword

`super` refers to the **parent class**. It has two forms:

### 1. Calling the Parent Constructor
```java
public class Cat extends Animal {
    boolean isIndoor;

    public Cat(String name, int age, boolean isIndoor) {
        super(name, age);        // Call Animal's constructor FIRST
        this.isIndoor = isIndoor;
    }
}
```

> **Rule**: `super(...)` must be the **first statement** in the child constructor. If you don't write it, Java automatically calls the parent's **no-arg** constructor — if none exists, you get a compilation error!

### 2. Calling a Parent Method
```java
public class Cat extends Animal {
    boolean isIndoor;

    public Cat(String name, int age, boolean isIndoor) {
        super(name, age);
        this.isIndoor = isIndoor;
    }

    @Override
    void eat() {
        super.eat();  // Call Animal's eat() first
        System.out.println(name + " purrs happily.");
    }
}

// Usage:
Cat c = new Cat("Whiskers", 2, true);
c.eat();
// Output:
// Whiskers is eating.     ← from super.eat()
// Whiskers purrs happily. ← from Cat's override
```

---

## 🔄 Method Overriding vs Method Overloading

These are two **completely different** concepts that interviewers love to ask about.

### Method Overriding (Runtime Polymorphism)
**Same method signature** in parent and child — child replaces parent's version.

```java
public class Animal {
    void makeSound() {
        System.out.println("Some generic animal sound");
    }
}

public class Dog extends Animal {
    @Override
    void makeSound() {
        System.out.println("Woof! Woof!");
    }
}

public class Cat extends Animal {
    @Override
    void makeSound() {
        System.out.println("Meow!");
    }
}

// The @Override annotation is optional but STRONGLY recommended.
// It tells the compiler to verify you're actually overriding a parent method.
```

### Method Overloading (Compile-time Polymorphism)
**Same method name**, **different parameter list** — in the same class or across inheritance.

```java
public class Calculator {
    // Overloaded: same name, different parameters
    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }
}
```

### Side-by-Side Comparison (Interview Favourite ⭐)

| Feature | Overriding | Overloading |
|---------|-----------|-------------|
| Where? | Parent → Child | Same class (or inherited) |
| Method name | Same | Same |
| Parameters | Same | **Different** |
| Return type | Same (or covariant) | Can differ |
| Decided at | **Runtime** | **Compile time** |
| Annotation | `@Override` | None |
| Also called | Runtime polymorphism | Compile-time polymorphism |

---

## 🎭 Polymorphism (The Big One)

**Polymorphism** = "many forms". A parent reference can hold a child object, and the **child's method** runs.

```java
// Parent reference, child objects
Animal a1 = new Dog("Buddy", 3, "Lab");
Animal a2 = new Cat("Whiskers", 2, true);

a1.makeSound();  // Woof! Woof!    ← Dog's version runs
a2.makeSound();  // Meow!          ← Cat's version runs
```

### Why Is This Powerful?

```java
// Process ANY animal — no need to know the specific type!
public static void animalRoutine(Animal animal) {
    animal.eat();
    animal.makeSound();
    animal.sleep();
}

// Works for ANY subclass of Animal:
animalRoutine(new Dog("Rex", 5, "Husky"));
animalRoutine(new Cat("Luna", 1, false));
```

### Arrays / Collections of Mixed Types
```java
Animal[] zoo = {
    new Dog("Max", 4, "Poodle"),
    new Cat("Mimi", 3, true),
    new Dog("Rex", 2, "Bulldog"),
    new Cat("Tom", 5, false)
};

for (Animal animal : zoo) {
    animal.makeSound();  // Each animal makes its OWN sound
}
// Output:
// Woof! Woof!
// Meow!
// Woof! Woof!
// Meow!
```

> **Interview Insight**: This is **dynamic dispatch** — the JVM looks at the **actual object type** at runtime to decide which method to call, not the reference type.

---

## 🧱 The `Object` Class — The Root of All Classes

Every class in Java implicitly extends `Object`. So every object has these methods:

```java
Object
├── toString()     → String representation
├── equals()       → Logical equality
├── hashCode()     → Hash code for collections
├── getClass()     → Runtime class info
├── clone()        → Copy (shallow)
├── finalize()     → Before garbage collection (deprecated)
├── wait()         → Thread synchronisation
├── notify()       → Thread synchronisation
└── notifyAll()    → Thread synchronisation
```

### Overriding `equals()` (Interview Essential ⭐)

```java
public class Student {
    String name;
    int id;

    public Student(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        // 1. Same reference?
        if (this == obj) return true;
        // 2. Null or different class?
        if (obj == null || getClass() != obj.getClass()) return false;
        // 3. Cast and compare fields
        Student other = (Student) obj;
        return this.id == other.id && this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        // Must override hashCode when overriding equals
        return name.hashCode() * 31 + id;
    }
}

// Usage:
Student s1 = new Student("Alice", 101);
Student s2 = new Student("Alice", 101);

System.out.println(s1 == s2);       // false (different objects in memory)
System.out.println(s1.equals(s2));  // true  (same logical data)
```

> **Golden Rule**: If you override `equals()`, you MUST also override `hashCode()`. This is critical for `HashMap` and `HashSet` — we'll cover why in Day 6.

---

## 🔒 The `final` Keyword

`final` means "cannot be changed / extended / overridden".

```java
// 1. Final variable — constant, cannot reassign
final double PI = 3.14159;
// PI = 3.14;  // ERROR!

// 2. Final method — cannot be overridden by subclasses
public class Animal {
    final void breathe() {
        System.out.println("Breathing...");
    }
}

public class Dog extends Animal {
    // @Override
    // void breathe() { ... }  // ERROR! Can't override final method
}

// 3. Final class — cannot be extended at all
final class MathUtils {
    static int square(int n) { return n * n; }
}

// class SuperMath extends MathUtils { }  // ERROR! Can't extend final class
```

> **Examples in Java**: `String`, `Integer`, `Math` are all `final` classes.

---

## 🏗️ Multi-Level & Hierarchical Inheritance

### Multi-Level (Chain)
```java
class Animal { }
class Dog extends Animal { }
class GoldenRetriever extends Dog { }
// GoldenRetriever IS-A Dog, which IS-A Animal
```

### Hierarchical (Tree)
```java
class Shape { }
class Circle extends Shape { }
class Rectangle extends Shape { }
class Triangle extends Shape { }
// Circle, Rectangle, Triangle all IS-A Shape
```

### ❌ No Multiple Inheritance of Classes
```java
// Java does NOT support this:
// class Hybrid extends Dog, Cat { }  // ERROR!
// Use Interfaces instead (Day 3)
```

---

## 🆚 `instanceof` Operator

Check whether an object is an instance of a specific class:

```java
Animal a = new Dog("Rex", 3, "Husky");

System.out.println(a instanceof Dog);    // true
System.out.println(a instanceof Animal); // true
System.out.println(a instanceof Cat);    // false

// Safe casting
if (a instanceof Dog) {
    Dog d = (Dog) a;    // Downcast
    d.fetch();          // Now we can call Dog-specific methods
}

// Java 16+ pattern matching
if (a instanceof Dog d) {
    d.fetch();  // d is already cast!
}
```

---

## 🧪 Try It Yourself — Full Example

```java
public class Vehicle {
    String brand;
    int year;
    double speed;

    public Vehicle(String brand, int year) {
        this.brand = brand;
        this.year = year;
        this.speed = 0;
    }

    void accelerate(double amount) {
        speed += amount;
        System.out.println(brand + " accelerates to " + speed + " km/h");
    }

    void brake() {
        speed = Math.max(0, speed - 20);
        System.out.println(brand + " brakes. Speed: " + speed + " km/h");
    }

    @Override
    public String toString() {
        return brand + " (" + year + ") @ " + speed + " km/h";
    }
}

public class ElectricCar extends Vehicle {
    double batteryLevel;

    public ElectricCar(String brand, int year, double batteryLevel) {
        super(brand, year);
        this.batteryLevel = batteryLevel;
    }

    @Override
    void accelerate(double amount) {
        if (batteryLevel <= 0) {
            System.out.println("Battery dead! Can't accelerate.");
            return;
        }
        super.accelerate(amount);
        batteryLevel -= amount * 0.1;
        System.out.println("Battery: " + batteryLevel + "%");
    }

    void charge() {
        batteryLevel = 100;
        System.out.println(brand + " is fully charged!");
    }

    @Override
    public String toString() {
        return super.toString() + " | Battery: " + batteryLevel + "%";
    }
}

// Main
public class Main {
    public static void main(String[] args) {
        Vehicle car = new Vehicle("Honda", 2020);
        ElectricCar tesla = new ElectricCar("Tesla", 2024, 80);

        car.accelerate(60);
        tesla.accelerate(100);

        // Polymorphism
        Vehicle[] garage = { car, tesla };
        for (Vehicle v : garage) {
            v.accelerate(30);  // Each calls its OWN version
        }
    }
}
```

---

## 💼 Interview Questions You Should Be Able to Answer

1. **What is inheritance? Why use it?**
   → Code reuse, IS-A modelling, enables polymorphism.

2. **What is the difference between method overriding and overloading?**
   → Overriding: same signature, parent-child, runtime. Overloading: same name, different params, compile-time.

3. **Can a constructor be inherited?**
   → No. But the child must call the parent's constructor via `super()`.

4. **What happens if you don't call `super()` explicitly?**
   → Java auto-inserts `super()` (no-arg). If the parent has no no-arg constructor, compile error.

5. **Can we override static methods?**
   → No. Static methods are bound at compile time (method hiding, not overriding).

6. **What is the purpose of `final` on a class/method/variable?**
   → Prevents extension / overriding / reassignment.

7. **Does Java support multiple inheritance?**
   → Not for classes. Yes for interfaces (Day 3).

---

## 🔑 Key Takeaways
- Use `extends` to inherit — child gets parent's non-private members
- Always call `super()` in child constructors
- **Overriding** = same method signature in subclass (runtime polymorphism)
- **Overloading** = same method name, different parameters (compile-time)
- A parent reference can hold a child object — the **child's overridden method** runs
- Override `equals()` AND `hashCode()` together
- `final` prevents extension, overriding, or reassignment
- `instanceof` checks an object's runtime type for safe casting
