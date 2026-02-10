# Day 2: Java Syntax & Data Types

## 🎯 Learning Goals
- Understand Java's static typing
- Learn primitive types vs wrapper classes
- Master variable declaration and initialization

---

## 📚 Static Typing

In Java, you **must declare the type** of every variable:

```java
// Python (dynamic)
x = 5           // Python figures out it's an int
x = "hello"     // Now it's a string - no problem!

// Java (static)
int x = 5;      // x is forever an int
x = "hello";    // ERROR! Can't assign String to int
```

### Benefits of Static Typing
- Catch errors at **compile time** (before running)
- Better IDE autocomplete and tooling
- Clearer code documentation
- Faster execution

---

## 🔢 Primitive Data Types

Java has **8 primitive types** (stored directly in memory):

| Type | Size | Range | Example |
|------|------|-------|---------|
| `byte` | 1 byte | -128 to 127 | `byte b = 100;` |
| `short` | 2 bytes | -32,768 to 32,767 | `short s = 1000;` |
| `int` | 4 bytes | ±2.1 billion | `int i = 42;` |
| `long` | 8 bytes | ±9 quintillion | `long l = 999999999L;` |
| `float` | 4 bytes | ~6-7 decimal digits | `float f = 3.14f;` |
| `double` | 8 bytes | ~15 decimal digits | `double d = 3.14159;` |
| `boolean` | 1 bit | true/false | `boolean flag = true;` |
| `char` | 2 bytes | Unicode character | `char c = 'A';` |

### Common Usage
```java
public class DataTypes {
    public static void main(String[] args) {
        // Most common types
        int age = 25;
        double price = 19.99;
        boolean isActive = true;
        char grade = 'A';
        
        // Notice the suffixes for long and float
        long bigNumber = 9999999999L;  // L for long
        float smallDecimal = 3.14f;     // f for float
        
        System.out.println("Age: " + age);
        System.out.println("Price: $" + price);
    }
}
```

---

## 📦 Wrapper Classes (Primitives vs Objects)

Each primitive has a corresponding **wrapper class**:

| Primitive | Wrapper Class |
|-----------|---------------|
| `int` | `Integer` |
| `double` | `Double` |
| `boolean` | `Boolean` |
| `char` | `Character` |
| `long` | `Long` |
| `float` | `Float` |
| `byte` | `Byte` |
| `short` | `Short` |

### Why Wrapper Classes?
```java
// Collections can only hold Objects, not primitives
ArrayList<Integer> numbers = new ArrayList<>();  // ✓
ArrayList<int> numbers = new ArrayList<>();      // ✗ ERROR!

// Useful methods
int num = Integer.parseInt("42");     // String to int
String str = Integer.toString(42);     // int to String
int max = Integer.MAX_VALUE;           // 2147483647

// Can be null (primitives can't)
Integer maybeNull = null;  // OK
int cantBeNull = null;     // ERROR!
```

### Autoboxing & Unboxing
Java automatically converts between primitives and wrappers:

```java
// Autoboxing: primitive → wrapper
Integer boxed = 42;  // int automatically wrapped

// Unboxing: wrapper → primitive  
int unboxed = boxed;  // Integer automatically unwrapped

// Works in operations too
Integer a = 5;
Integer b = 10;
int sum = a + b;  // Unboxed, added, result is int
```

---

## 📝 Variable Declaration

### Declaration vs Initialization
```java
// Declaration only
int x;

// Initialization only
x = 10;

// Declaration + Initialization
int y = 10;

// Multiple declarations
int a, b, c;
int d = 1, e = 2, f = 3;

// Constants (use final)
final double PI = 3.14159;
final int MAX_SIZE = 100;
// PI = 3.14;  // ERROR! Can't reassign final
```

### Naming Conventions
```java
// Variables and methods: camelCase
int studentAge;
String firstName;
void calculateTotal() {}

// Classes: PascalCase
class StudentRecord {}
class BankAccount {}

// Constants: SCREAMING_SNAKE_CASE
final int MAX_VALUE = 100;
final String DEFAULT_NAME = "Unknown";
```

---

## 🔄 Type Casting

### Implicit (Widening) - Automatic
When converting to a larger type:
```java
int myInt = 100;
long myLong = myInt;    // int → long (automatic)
double myDouble = myInt; // int → double (automatic)
```

### Explicit (Narrowing) - Manual
When converting to a smaller type (may lose data):
```java
double myDouble = 9.78;
int myInt = (int) myDouble;  // 9 (decimal lost!)

long bigNum = 1000L;
int smallNum = (int) bigNum;  // OK if within int range
```

---

## 🧮 Operators

### Arithmetic
```java
int a = 10, b = 3;

int sum = a + b;        // 13
int diff = a - b;       // 7
int product = a * b;    // 30
int quotient = a / b;   // 3 (integer division!)
int remainder = a % b;  // 1 (modulo)

// For decimal division
double result = (double) a / b;  // 3.333...
```

### Compound Assignment
```java
int x = 10;
x += 5;   // x = x + 5 → 15
x -= 3;   // x = x - 3 → 12
x *= 2;   // x = x * 2 → 24
x /= 4;   // x = x / 4 → 6
x %= 4;   // x = x % 4 → 2

x++;      // x = x + 1 → 3 (increment)
x--;      // x = x - 1 → 2 (decrement)
```

### Comparison
```java
int a = 5, b = 10;

boolean result;
result = (a == b);   // false (equal to)
result = (a != b);   // true (not equal)
result = (a > b);    // false
result = (a < b);    // true
result = (a >= b);   // false
result = (a <= b);   // true
```

### Logical
```java
boolean x = true, y = false;

boolean and = x && y;  // false (AND)
boolean or = x || y;   // true (OR)
boolean not = !x;      // false (NOT)
```

---

## 🧪 Try It Yourself

```java
public class TypeExplorer {
    public static void main(String[] args) {
        // 1. Experiment with different types
        int wholeNumber = 42;
        double decimal = 3.14159;
        boolean flag = true;
        char letter = 'J';
        
        // 2. Wrapper class methods
        String numStr = "123";
        int parsed = Integer.parseInt(numStr);
        System.out.println("Parsed: " + parsed);
        
        // 3. Type casting
        double pi = 3.14159;
        int truncated = (int) pi;
        System.out.println("Truncated: " + truncated);
        
        // 4. Integer division gotcha
        int a = 5, b = 2;
        System.out.println("5 / 2 = " + (a / b));           // 2
        System.out.println("5.0 / 2 = " + (5.0 / 2));       // 2.5
        System.out.println("(double)5 / 2 = " + ((double)a / b)); // 2.5
    }
}
```

---

## 🔑 Key Takeaways
- Java is **statically typed** - declare types explicitly
- **8 primitive types** - int, double, boolean are most common
- **Wrapper classes** (Integer, Double) are objects with useful methods
- Use `final` for constants
- Integer division truncates!
- Explicit casting needed when narrowing types
