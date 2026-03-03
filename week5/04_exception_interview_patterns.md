# Day 4: Exception Interview Patterns — Tricky Questions & Gotchas

## 🎯 Learning Goals
- Master the top 20+ exception interview questions
- Handle tricky code output prediction questions
- Understand edge cases that trip up even experienced developers
- Learn patterns used in coding interviews involving exceptions

---

## 🔥 Top Interview Questions with Answers

### Q1: What is the difference between `Error` and `Exception`?

| Aspect | `Error` | `Exception` |
|--------|---------|-------------|
| **Recovery** | Cannot recover | Can recover |
| **Cause** | JVM / system-level problems | Application-level problems |
| **Should you catch?** | ❌ Never | ✅ Yes |
| **Examples** | `OutOfMemoryError`, `StackOverflowError` | `IOException`, `NullPointerException` |
| **Checked?** | Unchecked | Can be checked or unchecked |

### Q2: `final` vs `finally` vs `finalize()`

| Keyword | Type | Purpose |
|---------|------|---------|
| `final` | Modifier | Variable can't change, method can't be overridden, class can't be extended |
| `finally` | Block | Always executes after try-catch (cleanup) |
| `finalize()` | Method | Called by GC before object is destroyed (**deprecated since Java 9**) |

```java
final int x = 10;              // Can't change x
// x = 20;                     // ❌ Compiler error

try {
    // ...
} finally {
    // Always runs
}

@Override
protected void finalize() {    // ⚠️ Deprecated — don't use in new code
    // Cleanup before GC
}
```

### Q3: Can `finally` block NOT execute?

**Yes, in these rare cases:**
1. `System.exit(0)` is called in try/catch
2. JVM crashes
3. Thread is killed with `Thread.stop()` (deprecated)
4. Infinite loop in try block

```java
try {
    System.out.println("Try");
    System.exit(0);  // JVM shuts down
} finally {
    System.out.println("Finally");  // ❌ NEVER PRINTS
}
```

---

## 🧩 Code Output Prediction Questions

### Question 1: What does this print?
```java
public class Q1 {
    public static int getValue() {
        try {
            return 1;
        } finally {
            return 2;
        }
    }

    public static void main(String[] args) {
        System.out.println(getValue());
    }
}
```

<details>
<summary>Click to see the answer</summary>

**Output: `2`**

`finally` executes BEFORE the method returns. The `return 2` in `finally` overrides `return 1` in `try`. This is a **bad practice** — avoid returning from `finally`.

</details>

---

### Question 2: What does this print?
```java
public class Q2 {
    public static void main(String[] args) {
        try {
            try {
                throw new RuntimeException("inner");
            } catch (RuntimeException e) {
                System.out.println("Inner catch: " + e.getMessage());
                throw new RuntimeException("rethrown");
            } finally {
                System.out.println("Inner finally");
            }
        } catch (RuntimeException e) {
            System.out.println("Outer catch: " + e.getMessage());
        } finally {
            System.out.println("Outer finally");
        }
    }
}
```

<details>
<summary>Click to see the answer</summary>

**Output:**
```
Inner catch: inner
Inner finally
Outer catch: rethrown
Outer finally
```

The inner catch handles "inner", then re-throws "rethrown". Inner finally runs before propagation. Outer catch handles "rethrown". Outer finally runs last.

</details>

---

### Question 3: What does this print?
```java
public class Q3 {
    public static void main(String[] args) {
        System.out.println(test());
    }

    static String test() {
        try {
            System.out.println("try");
            return "from try";
        } finally {
            System.out.println("finally");
        }
    }
}
```

<details>
<summary>Click to see the answer</summary>

**Output:**
```
try
finally
from try
```

The `return` value is computed, then `finally` runs, then the method returns. Since `finally` doesn't have its own `return`, the original return value is preserved.

</details>

---

### Question 4: What does this print?
```java
public class Q4 {
    public static void main(String[] args) {
        try {
            System.out.println("A");
            int x = 10 / 0;
            System.out.println("B");
        } catch (ArithmeticException e) {
            System.out.println("C");
        } catch (Exception e) {
            System.out.println("D");
        } finally {
            System.out.println("E");
        }
        System.out.println("F");
    }
}
```

<details>
<summary>Click to see the answer</summary>

**Output:**
```
A
C
E
F
```

- "A" prints, then exception at `10/0`, "B" skipped
- First matching catch (`ArithmeticException`) prints "C"
- `finally` always prints "E"
- Program continues, prints "F"

</details>

---

### Question 5: Does this compile?
```java
public void read() throws IOException {
    try {
        throw new FileNotFoundException("file.txt");
    } catch (IOException e) {
        throw e;
    } catch (FileNotFoundException e) {  // Does this compile?
        System.out.println("File not found");
    }
}
```

<details>
<summary>Click to see the answer</summary>

**❌ Does NOT compile!**

`FileNotFoundException` is a subclass of `IOException`. The `IOException` catch already handles `FileNotFoundException`. The second catch is **unreachable**, so the compiler reports an error.

**Fix**: Put `FileNotFoundException` BEFORE `IOException`:
```java
} catch (FileNotFoundException e) {
    // ...
} catch (IOException e) {
    // ...
}
```

</details>

---

### Question 6: What does this print?
```java
public class Q6 {
    static void method() {
        try {
            throw new RuntimeException("original");
        } finally {
            throw new RuntimeException("from finally");
        }
    }

    public static void main(String[] args) {
        try {
            method();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
```

<details>
<summary>Click to see the answer</summary>

**Output: `from finally`**

The "original" exception is **lost** because `finally` throws its own exception. The finally exception replaces the try exception. This is why you should **never throw from finally** — it silently discards the original exception.

With try-with-resources, the original is preserved and the close exception becomes a **suppressed** exception.

</details>

---

## 🎓 Conceptual Interview Questions

### Q7: When would you use a checked vs unchecked exception?

**Checked**: When the caller can **reasonably recover**
- File not found → prompt for different file
- Network timeout → retry
- Invalid SQL → show error to user

**Unchecked**: When it's a **programming bug** that shouldn't happen in correct code
- Null pointer → fix the code
- Array out of bounds → fix the loop
- Illegal argument → validate input before calling

### Q8: What is the "Catch or Declare" rule?

For checked exceptions, you must either:
1. **Catch** it in a try-catch block, OR
2. **Declare** it with `throws` in your method signature

```java
// Option 1: Catch
void readFile() {
    try {
        Files.readString(Path.of("file.txt"));
    } catch (IOException e) {
        // handle
    }
}

// Option 2: Declare
void readFile() throws IOException {
    Files.readString(Path.of("file.txt"));
}
```

### Q9: Can you throw multiple exceptions from a method?

**Yes**, but only one at a time. Declare multiple with commas:
```java
void process() throws IOException, SQLException, ClassNotFoundException {
    // any of these could be thrown
}
```

### Q10: What happens if an exception is thrown in a catch block?

It propagates to the next outer try-catch or terminates the method:

```java
try {
    throw new RuntimeException("first");
} catch (RuntimeException e) {
    System.out.println("Caught first");
    throw new RuntimeException("second");  // Propagates out!
} finally {
    System.out.println("Finally runs");   // Still runs!
}
// "second" exception continues propagation after finally
```

---

## 💼 Coding Interview Patterns

### Pattern 1: Retry with Exception Handling
```java
public static String fetchWithRetry(String url, int maxRetries) {
    int attempt = 0;
    while (attempt < maxRetries) {
        try {
            return httpGet(url);  // May throw IOException
        } catch (IOException e) {
            attempt++;
            System.out.println("Attempt " + attempt + " failed: " + e.getMessage());
            if (attempt >= maxRetries) {
                throw new RuntimeException("All " + maxRetries + " attempts failed", e);
            }
            try {
                Thread.sleep(1000 * attempt);  // Exponential backoff
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupted during retry", ie);
            }
        }
    }
    throw new RuntimeException("Unreachable");
}
```

### Pattern 2: Validation with Multiple Exception Types
```java
public class UserRegistration {

    public User register(String email, String password, int age)
            throws InvalidEmailException, WeakPasswordException, InvalidAgeException {

        if (!email.contains("@")) {
            throw new InvalidEmailException(email);
        }
        if (password.length() < 8) {
            throw new WeakPasswordException("Password must be 8+ characters");
        }
        if (age < 13 || age > 120) {
            throw new InvalidAgeException(age);
        }

        return new User(email, password, age);
    }
}
```

### Pattern 3: Resource Cleanup Pattern
```java
public List<String> processFiles(List<String> filenames) {
    List<String> results = new ArrayList<>();
    List<String> errors = new ArrayList<>();

    for (String filename : filenames) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String content = reader.lines().collect(Collectors.joining("\n"));
            results.add(process(content));
        } catch (FileNotFoundException e) {
            errors.add(filename + ": not found");
        } catch (IOException e) {
            errors.add(filename + ": read error - " + e.getMessage());
        }
    }

    if (!errors.isEmpty()) {
        System.out.println("Errors encountered:");
        errors.forEach(System.out::println);
    }

    return results;
}
```

### Pattern 4: Safe Parsing (Very Common in Interviews)
```java
public static Optional<Integer> safeParse(String str) {
    try {
        return Optional.of(Integer.parseInt(str));
    } catch (NumberFormatException e) {
        return Optional.empty();
    }
}

// Usage:
safeParse("42").ifPresent(n -> System.out.println("Got: " + n));   // Got: 42
safeParse("abc").ifPresent(n -> System.out.println("Got: " + n));  // Nothing printed
int value = safeParse("abc").orElse(-1);  // -1
```

---

## ⚡ Quick-Fire Interview Answers

| Question | One-Line Answer |
|----------|----------------|
| Can we have try without catch? | Yes, if there's a `finally` block |
| Can we have multiple finally blocks? | No, only one per try |
| Can we throw checked exception from main? | Yes: `public static void main(String[] args) throws Exception` |
| Does finally run on `return`? | Yes, before the method actually returns |
| What's `e.printStackTrace()`? | Prints the call stack to stderr for debugging |
| Can you re-throw an exception? | Yes: `catch (Exception e) { throw e; }` |
| What is exception masking? | When `finally` throws, it hides the original exception |
| What's multi-catch? | `catch (A \| B \| C e)` — handles multiple types identically |
| Can you catch `Throwable`? | Technically yes, but never do it in production |
| Are custom exceptions checked or unchecked? | Depends on what you extend: `Exception` = checked, `RuntimeException` = unchecked |

---

## 🔑 Key Takeaways
- Know the hierarchy cold: `Throwable` → `Error` / `Exception` → `RuntimeException`
- `final` (modifier), `finally` (block), `finalize()` (deprecated method) — all different
- `finally` always runs except after `System.exit()` or JVM crash
- Never `return` from or `throw` in `finally` — it masks the original exception
- Order catch blocks from most specific to most general
- Know the retry, validation, and safe parsing patterns for coding interviews
- Use `Optional` for safe parsing instead of returning magic values like `-1`
