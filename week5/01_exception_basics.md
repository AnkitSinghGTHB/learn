# Day 1: Exception Basics — try/catch/finally & The Exception Hierarchy

## 🎯 Learning Goals
- Understand what exceptions are and why they exist
- Master the `try-catch-finally` block
- Learn the complete Exception class hierarchy
- Know the difference between `Error` and `Exception`
- Handle multiple exceptions in a single try block

---

## 🔥 Why Exceptions?

Without exceptions, you'd check every operation manually:

```java
// ❌ BAD: Without exceptions — messy and error-prone
int result = divide(a, b);
if (result == -1) {
    // Was it an error or is -1 a valid result? 🤷
}
```

```java
// ✅ GOOD: With exceptions — clean and unambiguous
try {
    int result = divide(a, b);
    System.out.println("Result: " + result);
} catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero!");
}
```

> **Definition**: An exception is an **event that disrupts the normal flow** of a program's execution. Java uses exceptions to separate error-handling code from regular code.

---

## 🌳 The Exception Hierarchy

This is one of the **most asked interview topics**. Memorize this tree:

```
                    java.lang.Object
                         │
                    java.lang.Throwable
                    ┌────┴────────────┐
                    │                 │
               java.lang.Error   java.lang.Exception
                    │                 │
          ┌─────────┤          ┌──────┴──────────────┐
          │         │          │                      │
   StackOverflow  OutOfMemory  │              RuntimeException
   Error          Error        │                      │
                               │            ┌─────────┼──────────┐
                         IOException        │         │          │
                         SQLException   NullPointer  ArrayIndex  ClassCast
                         FileNotFound   Exception    OutOfBounds Exception
                         Exception                   Exception

         UNCHECKED ◄─── Error                Exception ───► CHECKED
                        (Don't catch these!)  (Must handle)

         UNCHECKED ◄─── RuntimeException
                        (Can handle, but not forced to)
```

### The Three Categories

| Category | Examples | Must Handle? | Your Fault? |
|----------|----------|-------------|-------------|
| **Error** | `StackOverflowError`, `OutOfMemoryError` | ❌ No (JVM problem) | Usually not |
| **Checked Exception** | `IOException`, `SQLException`, `FileNotFoundException` | ✅ Yes (compiler forces you) | External factors |
| **Unchecked Exception** | `NullPointerException`, `ArrayIndexOutOfBoundsException` | ❌ Not forced (but you should) | Programming bugs |

> **Interview Tip**: "Checked exceptions represent recoverable conditions. Unchecked exceptions represent programming errors." — This one-liner impresses interviewers.

---

## 🛡️ The try-catch Block

### Basic Syntax
```java
try {
    // Code that MIGHT throw an exception
    int result = 10 / 0;  // ArithmeticException!
} catch (ArithmeticException e) {
    // Code to handle the exception
    System.out.println("Error: " + e.getMessage());
}
```

### Execution Flow
```
TRY block starts
    │
    ├── No exception? ──► Skip ALL catch blocks ──► Continue after try-catch
    │
    └── Exception thrown? ──► Jump to matching catch ──► Execute catch ──► Continue after try-catch
```

### Complete Example
```java
public class TryCatchDemo {
    public static void main(String[] args) {
        System.out.println("1. Before try block");

        try {
            System.out.println("2. Inside try — before error");
            int result = 10 / 0;  // 💥 Exception here!
            System.out.println("3. This line NEVER executes");
        } catch (ArithmeticException e) {
            System.out.println("4. Caught: " + e.getMessage());
        }

        System.out.println("5. After try-catch — program continues!");
    }
}
```

**Output**:
```
1. Before try block
2. Inside try — before error
4. Caught: / by zero
5. After try-catch — program continues!
```

> **Key Insight**: Line 3 is **skipped** because the exception happens at line with `10 / 0`. Execution jumps straight to the matching catch block.

---

## 🎯 Catching Multiple Exceptions

### Method 1: Multiple catch blocks (most common)
```java
public class MultiCatchDemo {
    public static void main(String[] args) {
        try {
            String text = null;
            System.out.println(text.length());  // NullPointerException

            int[] arr = {1, 2, 3};
            System.out.println(arr[10]);         // ArrayIndexOutOfBoundsException

            int result = 10 / 0;                 // ArithmeticException
        } catch (NullPointerException e) {
            System.out.println("Null pointer: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array index out of bounds: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("Math error: " + e.getMessage());
        }
    }
}
```

> **Rule**: Only the **first** matching catch block executes. Order matters — put **specific exceptions first**, general ones last.

### Method 2: Multi-catch with pipe `|` (Java 7+)
```java
try {
    // risky code
} catch (NullPointerException | ArithmeticException | ArrayIndexOutOfBoundsException e) {
    // Handle all three the same way
    System.out.println("Something went wrong: " + e.getMessage());
}
```

> **Interview Tip**: In multi-catch, the exceptions must NOT be in the same inheritance hierarchy. You can't write `IOException | FileNotFoundException` because `FileNotFoundException` extends `IOException`.

### Method 3: Catching a parent exception
```java
try {
    // risky code
} catch (Exception e) {
    // Catches ALL exceptions (checked and unchecked)
    System.out.println("Error: " + e.getMessage());
}
```

> ⚠️ **Warning**: Catching `Exception` is too broad for production code. It hides bugs. Use specific catch blocks.

### ❌ WRONG ORDER (Compiler Error!)
```java
try {
    // ...
} catch (Exception e) {           // ❌ This catches everything
    System.out.println("General");
} catch (IOException e) {          // ❌ UNREACHABLE — compiler error!
    System.out.println("IO");
}
```

### ✅ CORRECT ORDER
```java
try {
    // ...
} catch (FileNotFoundException e) {  // Most specific first
    System.out.println("File not found");
} catch (IOException e) {            // Then broader
    System.out.println("IO error");
} catch (Exception e) {              // Most general last
    System.out.println("General error");
}
```

---

## 🔒 The finally Block

The `finally` block **always executes** — whether an exception occurred or not. It's used for cleanup operations.

```java
public class FinallyDemo {
    public static void main(String[] args) {
        try {
            System.out.println("Try block");
            int result = 10 / 0;  // Exception!
        } catch (ArithmeticException e) {
            System.out.println("Catch block");
        } finally {
            System.out.println("Finally block — ALWAYS runs!");
        }
    }
}
```

**Output**:
```
Try block
Catch block
Finally block — ALWAYS runs!
```

### When does finally run?

| Scenario | try | catch | finally |
|----------|-----|-------|---------|
| No exception | ✅ Completes | ⏭️ Skipped | ✅ Runs |
| Exception caught | ❌ Partial | ✅ Runs | ✅ Runs |
| Exception NOT caught | ❌ Partial | ⏭️ No match | ✅ Runs (then propagates) |
| `return` in try | ✅ Returns | ⏭️ Skipped | ✅ Runs BEFORE return |
| `System.exit()` in try | ✅ | — | ❌ Does NOT run |

### ⚡ Tricky Interview Question: finally + return
```java
public static int trickyMethod() {
    try {
        return 1;
    } catch (Exception e) {
        return 2;
    } finally {
        return 3;  // ⚠️ This overrides the return from try!
    }
}

System.out.println(trickyMethod());  // Prints: 3
```

> **Interview Tip**: `finally` runs BEFORE the method returns. If `finally` has a `return`, it **overrides** any return from `try` or `catch`. This is considered bad practice — never put `return` in `finally`.

---

## 📊 Exception Object Methods

Every exception inherits useful methods from `Throwable`:

```java
try {
    int[] arr = new int[5];
    arr[10] = 42;
} catch (ArrayIndexOutOfBoundsException e) {
    // getMessage() — short description
    System.out.println(e.getMessage());        // "Index 10 out of bounds for length 5"

    // toString() — class name + message
    System.out.println(e.toString());
    // "java.lang.ArrayIndexOutOfBoundsException: Index 10 out of bounds for length 5"

    // printStackTrace() — full stack trace (most useful for debugging)
    e.printStackTrace();
    // java.lang.ArrayIndexOutOfBoundsException: Index 10 out of bounds for length 5
    //     at Main.main(Main.java:4)

    // getClass().getName() — fully qualified exception class name
    System.out.println(e.getClass().getName());
    // "java.lang.ArrayIndexOutOfBoundsException"
}
```

### Common Exception Methods Summary

| Method | Returns | Use Case |
|--------|---------|----------|
| `getMessage()` | `String` | Log the error message |
| `toString()` | `String` | Display class + message |
| `printStackTrace()` | `void` | Debugging (prints to stderr) |
| `getCause()` | `Throwable` | Get the root cause (exception chaining) |
| `getStackTrace()` | `StackTraceElement[]` | Programmatically inspect the stack |

---

## 🧩 Nested try-catch

You can nest try-catch blocks. The inner catch handles its own exceptions; unhandled ones propagate to the outer catch.

```java
public class NestedTryDemo {
    public static void main(String[] args) {
        try {
            System.out.println("Outer try");

            try {
                System.out.println("Inner try");
                int result = 10 / 0;  // ArithmeticException
            } catch (ArithmeticException e) {
                System.out.println("Inner catch: " + e.getMessage());
            }

            // This still executes because inner exception was caught
            String s = null;
            s.length();  // NullPointerException — propagates to outer catch

        } catch (NullPointerException e) {
            System.out.println("Outer catch: " + e.getMessage());
        }
    }
}
```

**Output**:
```
Outer try
Inner try
Inner catch: / by zero
Outer catch: null
```

---

## 🏭 Real-World Pattern: Input Validation

```java
import java.util.Scanner;

public class InputValidator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int age = -1;

        while (age < 0) {
            try {
                System.out.print("Enter your age: ");
                String input = scanner.nextLine();
                age = Integer.parseInt(input);  // May throw NumberFormatException

                if (age < 0 || age > 150) {
                    System.out.println("Age must be between 0 and 150.");
                    age = -1;  // Reset to continue loop
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }

        System.out.println("Your age is: " + age);
        scanner.close();
    }
}
```

This pattern is used in **every production application** — retry loops with exception handling.

---

## 🧪 Try It Yourself

### Exercise 1: Safe Division Calculator
```java
import java.util.Scanner;

public class SafeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter numerator: ");
            int num = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter denominator: ");
            int den = Integer.parseInt(scanner.nextLine());

            int result = num / den;
            System.out.println(num + " / " + den + " = " + result);

        } catch (NumberFormatException e) {
            System.out.println("Please enter valid integers!");
        } catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero!");
        } finally {
            scanner.close();
            System.out.println("Calculator closed.");
        }
    }
}
```

### Exercise 2: Array Access Guard
```java
public class ArrayGuard {
    public static int safeGet(int[] arr, int index) {
        try {
            return arr[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Index " + index + " is out of bounds (array length: " + arr.length + ")");
            return -1;  // Default value
        } catch (NullPointerException e) {
            System.out.println("Array is null!");
            return -1;
        }
    }

    public static void main(String[] args) {
        int[] numbers = {10, 20, 30, 40, 50};

        System.out.println(safeGet(numbers, 2));   // 30
        System.out.println(safeGet(numbers, 10));  // -1 (out of bounds)
        System.out.println(safeGet(null, 0));      // -1 (null array)
    }
}
```

---

## 🔑 Key Takeaways
- An **exception** disrupts normal program flow; Java uses `try-catch` to handle it
- The hierarchy: `Throwable` → `Error` (don't catch) and `Exception` (handle)
- `RuntimeException` and its subclasses are **unchecked** — the compiler won't force you to handle them
- Order catch blocks from **most specific to most general**
- `finally` **always runs** (except after `System.exit()`) — use it for cleanup
- Multi-catch (`|`) lets you handle multiple unrelated exceptions identically
- Never return from a `finally` block
- Use `e.getMessage()` for logs, `e.printStackTrace()` for debugging
