# Day 2: Checked vs Unchecked Exceptions — throw, throws & Error Types

## 🎯 Learning Goals
- Master the difference between checked and unchecked exceptions
- Use `throw` to explicitly throw exceptions
- Use `throws` to declare exceptions in method signatures
- Understand exception propagation up the call stack
- Learn when to catch vs when to propagate

---

## 🔴 Checked vs Unchecked — The Core Distinction

```
                        Exception
                    ┌───────┴────────┐
                    │                │
              CHECKED            UNCHECKED
          (Compile-time)        (Runtime)
                    │                │
         ┌─────────┼──────┐    RuntimeException
         │         │      │         │
    IOException  SQL   ClassNot  ┌──┴──────────┐
         │     Exception Found   │             │
    FileNot              Exception │         │
    Found                   NullPointer  IndexOutOf
    Exception               Exception    BoundsException
```

### Side-by-Side Comparison

| Aspect | Checked Exception | Unchecked Exception |
|--------|-------------------|---------------------|
| **Inherits from** | `Exception` (but NOT `RuntimeException`) | `RuntimeException` |
| **Compiler forces handling?** | ✅ Yes — must catch or declare | ❌ No |
| **When occurs** | External factors (file, network, DB) | Programming bugs |
| **Examples** | `IOException`, `SQLException`, `ClassNotFoundException` | `NullPointerException`, `ArithmeticException`, `ClassCastException` |
| **Should you catch?** | ✅ Always | ⚠️ Fix the bug instead, but catch if needed |
| **Keyword** | Must use `throws` or `try-catch` | Optional |

---

## 🎯 The `throw` Keyword — Throwing Exceptions Manually

`throw` creates and throws an exception object. You **throw** exceptions.

```java
public class ThrowDemo {

    public static void validateAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative: " + age);
        }
        if (age < 18) {
            throw new IllegalArgumentException("Must be 18 or older. Got: " + age);
        }
        System.out.println("Age " + age + " is valid ✓");
    }

    public static void main(String[] args) {
        validateAge(25);   // Age 25 is valid ✓
        validateAge(-5);   // 💥 IllegalArgumentException: Age cannot be negative: -5
    }
}
```

### `throw` Rules
1. `throw` takes an **object** that extends `Throwable`
2. It immediately stops the current method
3. Exception propagates up the call stack until caught

```java
// ✅ Correct
throw new RuntimeException("Something broke");
throw new IOException("File not found");

// ❌ Wrong
throw new String("not an exception");    // String is not Throwable
throw IOException;                       // Must use 'new' to create object
```

---

## 📢 The `throws` Keyword — Declaring Exceptions

`throws` goes in the **method signature** to warn callers that this method **might** throw a checked exception.

```java
import java.io.*;

public class ThrowsDemo {

    // This method DECLARES that it might throw IOException
    public static String readFirstLine(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine();
        reader.close();
        return line;
    }

    public static void main(String[] args) {
        // Option 1: Handle it with try-catch
        try {
            String line = readFirstLine("data.txt");
            System.out.println("First line: " + line);
        } catch (IOException e) {
            System.out.println("Could not read file: " + e.getMessage());
        }
    }
}
```

### `throw` vs `throws` — Don't Confuse Them!

| Feature | `throw` | `throws` |
|---------|---------|----------|
| **What it does** | Actually throws an exception | Declares that a method *might* throw |
| **Where it goes** | Inside a method body | In the method signature |
| **Followed by** | An exception object | Exception class name(s) |
| **Can list multiple?** | No (one at a time) | Yes (comma-separated) |

```java
//                          ↓ throws (declaration)
public void readFile(String path) throws IOException, FileNotFoundException {
    if (path == null) {
        throw new IllegalArgumentException("Path cannot be null");
        //  ↑ throw (action)
    }
    // ... file reading logic
}
```

### Multiple throws
```java
public void process(String filename)
        throws IOException, SQLException, ClassNotFoundException {
    // Method might throw any of these checked exceptions
}
```

---

## 🔄 Exception Propagation — The Call Stack

When an exception is thrown, Java searches **up the call stack** for a matching catch block:

```java
public class PropagationDemo {

    static void methodC() {
        System.out.println("C: before exception");
        int result = 10 / 0;  // 💥 ArithmeticException
        System.out.println("C: after exception");  // NEVER EXECUTES
    }

    static void methodB() {
        System.out.println("B: before calling C");
        methodC();  // Exception propagates from C to B
        System.out.println("B: after calling C");  // NEVER EXECUTES
    }

    static void methodA() {
        System.out.println("A: before calling B");
        try {
            methodB();  // Exception propagates from B, caught here
        } catch (ArithmeticException e) {
            System.out.println("A: caught exception — " + e.getMessage());
        }
        System.out.println("A: after try-catch");  // EXECUTES (exception was caught)
    }

    public static void main(String[] args) {
        methodA();
    }
}
```

**Output**:
```
A: before calling B
B: before calling C
C: before exception
A: caught exception — / by zero
A: after try-catch
```

### Visual Flow
```
main() ──► methodA() ──► methodB() ──► methodC()
                                           │
                                     💥 ArithmeticException
                                           │
                              ◄─── propagates back
                              │
                    try-catch in methodA() catches it ✓
```

> **Interview Tip**: If no method in the call stack catches the exception, the JVM terminates the program and prints the stack trace. This is called an **unhandled exception**.

---

## 📋 Complete List of Common Exceptions

### Unchecked (RuntimeException subclasses) — Your Bugs
```java
// NullPointerException — accessing a member of null
String s = null;
s.length();  // 💥

// ArrayIndexOutOfBoundsException — bad array index
int[] arr = {1, 2, 3};
arr[5] = 10;  // 💥

// StringIndexOutOfBoundsException — bad string index
"Hello".charAt(10);  // 💥

// ArithmeticException — division by zero (integers only)
int x = 10 / 0;  // 💥

// ClassCastException — invalid type cast
Object obj = "Hello";
Integer num = (Integer) obj;  // 💥

// NumberFormatException — parsing invalid number
Integer.parseInt("abc");  // 💥

// IllegalArgumentException — method received bad argument
Thread.sleep(-1);  // 💥

// IllegalStateException — object in wrong state
Iterator<String> it = list.iterator();
it.remove();  // 💥 (called before next())

// StackOverflowError — infinite recursion (technically an Error, not Exception)
public void recurse() { recurse(); }
```

### Checked — External Problems
```java
// IOException — general I/O failure
// FileNotFoundException — file doesn't exist (subclass of IOException)
// EOFException — unexpected end of file

// SQLException — database error
// ClassNotFoundException — class not found by ClassLoader
// InterruptedException — thread was interrupted
// CloneNotSupportedException — object doesn't support cloning

// MalformedURLException — bad URL format
// UnknownHostException — DNS resolution failed
// ConnectException — connection refused
```

---

## 🏭 When to Catch vs When to Propagate

### Catch When:
1. You can **recover** from the error (retry, use default, etc.)
2. You're at a **boundary** (API handler, main method, UI layer)
3. You need to **convert** the exception to a different type

### Propagate When:
1. The **caller** is better positioned to decide what to do
2. You're writing a **library** — let users decide how to handle errors
3. The exception is **unrecoverable** at this level

```java
// ✅ Good: Library method propagates — lets caller decide
public class FileUtils {
    public static String readFile(String path) throws IOException {
        return new String(java.nio.file.Files.readAllBytes(java.nio.file.Path.of(path)));
    }
}

// ✅ Good: Application boundary catches and handles
public class App {
    public static void main(String[] args) {
        try {
            String content = FileUtils.readFile("config.txt");
            System.out.println(content);
        } catch (IOException e) {
            System.err.println("Failed to load config: " + e.getMessage());
            System.err.println("Using default configuration...");
            // use defaults
        }
    }
}
```

---

## 🚫 Common Anti-Patterns (What NOT to Do)

### ❌ Anti-Pattern 1: Empty catch block (swallowing exceptions)
```java
try {
    riskyOperation();
} catch (Exception e) {
    // EMPTY — exception is silently ignored 😱
}
```

### ❌ Anti-Pattern 2: Catching Exception (too broad)
```java
try {
    // ...
} catch (Exception e) {
    // Catches EVERYTHING — hides bugs
    System.out.println("Something happened");
}
```

### ❌ Anti-Pattern 3: Using exceptions for flow control
```java
// ❌ DON'T do this
try {
    int i = 0;
    while (true) {
        array[i++] = 0;  // Uses ArrayIndexOutOfBoundsException to stop loop
    }
} catch (ArrayIndexOutOfBoundsException e) { }

// ✅ DO this instead
for (int i = 0; i < array.length; i++) {
    array[i] = 0;
}
```

### ❌ Anti-Pattern 4: Catching and re-throwing without adding value
```java
// ❌ Pointless
try {
    riskyOperation();
} catch (IOException e) {
    throw e;  // Why catch it at all?
}

// ✅ Only re-throw if you add context
try {
    riskyOperation();
} catch (IOException e) {
    throw new RuntimeException("Failed during data processing", e);  // Wrapping adds context
}
```

---

## 🧪 Try It Yourself

### Exercise: Robust Number Parser
```java
public class RobustParser {

    /**
     * Parses a string to an integer.
     * Throws IllegalArgumentException if input is null or empty.
     * Returns a default value if parsing fails.
     */
    public static int parseOrDefault(String input, int defaultValue) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            System.out.println("Warning: '" + input + "' is not a valid number. Using default: " + defaultValue);
            return defaultValue;
        }
    }

    public static void main(String[] args) {
        System.out.println(parseOrDefault("42", 0));       // 42
        System.out.println(parseOrDefault("hello", 0));    // 0 (with warning)
        System.out.println(parseOrDefault("  99  ", 0));   // 99

        try {
            parseOrDefault(null, 0);  // IllegalArgumentException
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}
```

---

## 🔑 Key Takeaways
- **Checked exceptions** must be caught or declared with `throws` — the compiler enforces this
- **Unchecked exceptions** (RuntimeException) aren't enforced — they indicate programming bugs  
- `throw` **creates and throws** an exception; `throws` **declares** one in the method signature
- Exceptions propagate **up the call stack** until caught or the program terminates
- **Catch** at boundaries where you can recover; **propagate** when the caller should decide
- Never swallow exceptions (empty catch blocks), use exceptions for flow control, or catch `Exception` broadly
- Always provide meaningful messages when throwing exceptions
