# Day 3: Custom Exceptions — Design Patterns & Best Practices

## 🎯 Learning Goals
- Create your own exception classes
- Design exception hierarchies for real applications
- Use exception chaining to preserve root cause information
- Master try-with-resources for automatic resource cleanup
- Follow industry best practices for exception design

---

## 🏗️ Why Custom Exceptions?

Built-in exceptions are generic. Custom exceptions:
1. **Communicate domain-specific problems** — `InsufficientFundsException` is clearer than `RuntimeException`
2. **Enable precise catching** — callers can catch YOUR exception specifically
3. **Carry extra data** — add fields like error codes, context objects
4. **Document your API** — the `throws` clause tells callers what can go wrong

---

## 📦 Creating a Basic Custom Exception

### Step 1: Extend the right class
```java
// Checked exception — caller MUST handle
public class InsufficientFundsException extends Exception {

    public InsufficientFundsException(String message) {
        super(message);
    }
}

// Unchecked exception — caller CAN handle
public class InvalidAccountException extends RuntimeException {

    public InvalidAccountException(String message) {
        super(message);
    }
}
```

### Step 2: Use it
```java
public class BankAccount {
    private String owner;
    private double balance;

    public BankAccount(String owner, double balance) {
        if (owner == null || owner.isEmpty()) {
            throw new InvalidAccountException("Owner name cannot be null or empty");
        }
        this.owner = owner;
        this.balance = balance;
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException(
                "Cannot withdraw $" + amount + ". Available balance: $" + balance
            );
        }
        balance -= amount;
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount("Alice", 500);

        try {
            account.withdraw(700);  // 💥 InsufficientFundsException
        } catch (InsufficientFundsException e) {
            System.out.println("Transaction failed: " + e.getMessage());
            // Transaction failed: Cannot withdraw $700.0. Available balance: $500.0
        }
    }
}
```

---

## 🔧 Custom Exception with Extra Fields

Real-world exceptions carry more than just a message:

```java
public class InsufficientFundsException extends Exception {
    private final double requestedAmount;
    private final double availableBalance;
    private final String accountId;

    public InsufficientFundsException(String accountId, double requested, double available) {
        super(String.format(
            "Account %s: Cannot withdraw $%.2f. Available: $%.2f. Shortfall: $%.2f",
            accountId, requested, available, requested - available
        ));
        this.accountId = accountId;
        this.requestedAmount = requested;
        this.availableBalance = available;
    }

    // Getters for programmatic access
    public double getRequestedAmount() { return requestedAmount; }
    public double getAvailableBalance() { return availableBalance; }
    public String getAccountId() { return accountId; }
    public double getShortfall() { return requestedAmount - availableBalance; }
}
```

```java
// Usage — the catch block can access rich data
try {
    account.withdraw(700);
} catch (InsufficientFundsException e) {
    System.out.println(e.getMessage());
    System.out.println("You need $" + e.getShortfall() + " more.");
    // Suggest transfer from another account, etc.
}
```

> **Interview Tip**: Adding domain-specific fields to exceptions shows you understand production-quality error handling. This is a **huge** differentiator in interviews.

---

## 🏢 Exception Hierarchy Design

For larger applications, design a hierarchy:

```java
// Base exception for your application
public class AppException extends Exception {
    private final String errorCode;

    public AppException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public AppException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() { return errorCode; }
}

// Domain-specific exceptions extend the base
public class UserNotFoundException extends AppException {
    public UserNotFoundException(String userId) {
        super("USER_NOT_FOUND", "User not found: " + userId);
    }
}

public class DuplicateEmailException extends AppException {
    public DuplicateEmailException(String email) {
        super("DUPLICATE_EMAIL", "Email already registered: " + email);
    }
}

public class InvalidInputException extends AppException {
    public InvalidInputException(String field, String reason) {
        super("INVALID_INPUT", "Invalid " + field + ": " + reason);
    }
}
```

```java
// Usage — catch specific or general
try {
    userService.register(email, password);
} catch (DuplicateEmailException e) {
    // Specific handling
    showMessage("This email is already registered. Try logging in.");
} catch (InvalidInputException e) {
    // Slightly broader
    showMessage("Please check your input: " + e.getMessage());
} catch (AppException e) {
    // Catch-all for any app error
    logError(e.getErrorCode(), e.getMessage());
    showMessage("Something went wrong. Error code: " + e.getErrorCode());
}
```

### Hierarchy Visualization
```
AppException (base — has errorCode)
├── UserNotFoundException
├── DuplicateEmailException
├── InvalidInputException
├── PaymentException
│   ├── InsufficientFundsException
│   └── PaymentGatewayException
└── DataAccessException
    ├── ConnectionFailedException
    └── QueryTimeoutException
```

---

## 🔗 Exception Chaining (Wrapping)

Preserve the **root cause** when converting between exception types:

```java
public class DatabaseService {

    public User findUser(String id) throws AppException {
        try {
            // Low-level database operation
            return database.query("SELECT * FROM users WHERE id = ?", id);
        } catch (SQLException e) {
            // Wrap the low-level exception in a domain exception
            throw new AppException("DB_ERROR", "Failed to find user: " + id, e);
            //                                                              ↑
            //                                            The 'cause' parameter preserves the original
        }
    }
}

// When debugging, you can see the full chain:
try {
    service.findUser("123");
} catch (AppException e) {
    System.out.println("App error: " + e.getMessage());
    System.out.println("Root cause: " + e.getCause().getMessage());  // SQL error details
    e.printStackTrace();  // Shows BOTH exceptions
}
```

**Stack trace output**:
```
AppException: Failed to find user: 123
    at DatabaseService.findUser(DatabaseService.java:8)
    at Main.main(Main.java:5)
Caused by: java.sql.SQLException: Connection refused
    at Database.query(Database.java:15)
    at DatabaseService.findUser(DatabaseService.java:6)
    ... 1 more
```

> **Key**: The `Caused by:` section shows the original exception. This is **essential** for production debugging.

---

## ♻️ try-with-resources (Java 7+)

Automatically closes resources like files, databases, network connections.

### The Problem (Old Way)
```java
// ❌ Verbose and error-prone
BufferedReader reader = null;
try {
    reader = new BufferedReader(new FileReader("data.txt"));
    String line = reader.readLine();
    System.out.println(line);
} catch (IOException e) {
    System.out.println("Read error: " + e.getMessage());
} finally {
    if (reader != null) {
        try {
            reader.close();  // close() can also throw IOException!
        } catch (IOException e) {
            System.out.println("Close error: " + e.getMessage());
        }
    }
}
```

### The Solution (try-with-resources)
```java
// ✅ Clean and safe — reader is auto-closed
try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
    String line = reader.readLine();
    System.out.println(line);
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
}
// reader.close() is automatically called here — even if exception occurs!
```

### How It Works
Any class that implements `AutoCloseable` (or `Closeable`) can be used in try-with-resources:

```java
public interface AutoCloseable {
    void close() throws Exception;
}
```

### Multiple Resources
```java
try (
    FileReader fr = new FileReader("input.txt");
    BufferedReader br = new BufferedReader(fr);
    FileWriter fw = new FileWriter("output.txt");
    BufferedWriter bw = new BufferedWriter(fw)
) {
    String line;
    while ((line = br.readLine()) != null) {
        bw.write(line.toUpperCase());
        bw.newLine();
    }
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
}
// ALL four resources are automatically closed in REVERSE order
```

> **Interview Tip**: Resources are closed in **reverse order** of declaration. This matters for nested resources.

### Making Your Own AutoCloseable Class
```java
public class DatabaseConnection implements AutoCloseable {
    private String name;

    public DatabaseConnection(String name) {
        this.name = name;
        System.out.println("Opening connection: " + name);
    }

    public void query(String sql) {
        System.out.println("Executing: " + sql);
    }

    @Override
    public void close() {
        System.out.println("Closing connection: " + name);
    }
}

// Usage:
try (DatabaseConnection db = new DatabaseConnection("ProductionDB")) {
    db.query("SELECT * FROM users");
}
// Output:
// Opening connection: ProductionDB
// Executing: SELECT * FROM users
// Closing connection: ProductionDB
```

---

## 🔇 Suppressed Exceptions

When both the try block AND the close() method throw exceptions:

```java
public class SuppressedDemo implements AutoCloseable {
    @Override
    public void close() throws Exception {
        throw new Exception("Close failed!");
    }

    public static void main(String[] args) {
        try (SuppressedDemo resource = new SuppressedDemo()) {
            throw new Exception("Try block failed!");
        } catch (Exception e) {
            System.out.println("Primary: " + e.getMessage());        // Try block failed!
            for (Throwable suppressed : e.getSuppressed()) {
                System.out.println("Suppressed: " + suppressed.getMessage());  // Close failed!
            }
        }
    }
}
```

> The try block exception is the **primary** one. The close() exception is **suppressed** (attached) to it. Both are preserved!

---

## ✅ Best Practices Checklist

### DO ✅
```java
// 1. Use specific exceptions
throw new FileNotFoundException("Config file not found: " + path);

// 2. Include context in messages
throw new IllegalArgumentException("Expected positive value, got: " + value);

// 3. Use exception chaining
throw new AppException("Processing failed", originalException);

// 4. Use try-with-resources
try (var reader = new BufferedReader(new FileReader(path))) { ... }

// 5. Log exceptions properly
catch (Exception e) {
    logger.error("Failed to process order {}: {}", orderId, e.getMessage(), e);
    throw e;  // Re-throw if you can't handle it
}

// 6. Document thrown exceptions in Javadoc
/**
 * @throws IllegalArgumentException if amount is negative
 * @throws InsufficientFundsException if balance is too low
 */
public void withdraw(double amount) throws InsufficientFundsException { }
```

### DON'T ❌
```java
// 1. Don't swallow exceptions
catch (Exception e) { }                                    // ❌ Silent failure

// 2. Don't catch Throwable or Error
catch (Throwable t) { }                                    // ❌ Catches JVM errors
catch (OutOfMemoryError e) { }                             // ❌ Can't recover from this

// 3. Don't use exceptions for flow control
try { map.get(key).doSomething(); }                        // ❌
catch (NullPointerException e) { /* key not present */ }   // ❌
// ✅ Use: if (map.containsKey(key)) { ... }

// 4. Don't throw generic exceptions
throw new Exception("something");                          // ❌ Too vague
throw new RuntimeException("error");                       // ❌ Too vague

// 5. Don't put return in finally
finally { return value; }                                  // ❌ Overrides other returns

// 6. Don't log AND throw (logs duplicate info)
catch (IOException e) {
    logger.error("Error", e);    // ❌ Logged here
    throw e;                     // ❌ And will be logged by caller too
}
```

---

## 🧪 Try It Yourself

### Complete Custom Exception System
```java
// Step 1: Define your exceptions
class VotingException extends Exception {
    public VotingException(String message) { super(message); }
}

class InvalidAgeException extends VotingException {
    private final int age;

    public InvalidAgeException(int age) {
        super("Voter must be 18+. Got age: " + age);
        this.age = age;
    }

    public int getAge() { return age; }
}

class AlreadyVotedException extends VotingException {
    public AlreadyVotedException(String voterId) {
        super("Voter " + voterId + " has already voted");
    }
}

// Step 2: Use them in your logic
class VotingSystem {
    private java.util.Set<String> voters = new java.util.HashSet<>();

    public void castVote(String voterId, int age) throws VotingException {
        if (age < 18) {
            throw new InvalidAgeException(age);
        }
        if (voters.contains(voterId)) {
            throw new AlreadyVotedException(voterId);
        }
        voters.add(voterId);
        System.out.println("Vote cast successfully for: " + voterId);
    }
}

// Step 3: Handle them
public class VotingDemo {
    public static void main(String[] args) {
        VotingSystem system = new VotingSystem();

        String[] ids = {"V001", "V002", "V001", "V003"};
        int[] ages = {25, 16, 25, 30};

        for (int i = 0; i < ids.length; i++) {
            try {
                system.castVote(ids[i], ages[i]);
            } catch (InvalidAgeException e) {
                System.out.println("Age violation: " + e.getMessage());
                System.out.println("   Provided age: " + e.getAge());
            } catch (AlreadyVotedException e) {
                System.out.println("Duplicate vote: " + e.getMessage());
            } catch (VotingException e) {
                System.out.println("Voting error: " + e.getMessage());
            }
        }
    }
}
```

---

## 🔑 Key Takeaways
- Extend `Exception` for **checked**, `RuntimeException` for **unchecked** custom exceptions
- Always provide constructors that accept `String message` and `Throwable cause`
- Add domain-specific fields (error codes, relevant data) to carry context
- Design exception **hierarchies** for complex applications
- Use **exception chaining** (`new MyException("msg", cause)`) to preserve root cause
- Use **try-with-resources** for all `AutoCloseable` objects (files, connections, streams)
- Suppressed exceptions capture close() failures without losing the primary exception
- Follow the best practices: specific types, meaningful messages, proper chaining, no swallowing
