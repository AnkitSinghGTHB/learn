# Day 1: Java Environment - JVM, JRE, JDK

## 🎯 Learning Goals
- Understand the Java ecosystem components
- Install and configure the JDK
- Compile and run your first Java program

---

## 📚 The Java Ecosystem

### JDK (Java Development Kit)
The **complete development toolkit** for Java developers.

**Includes:**
- Compiler (`javac`) - converts `.java` to `.class` bytecode
- JRE (for running programs)
- Development tools (debugger, documentation generator, etc.)

**Who needs it?** Developers writing Java code.

### JRE (Java Runtime Environment)
The **runtime environment** for executing Java programs.

**Includes:**
- JVM (Java Virtual Machine)
- Core libraries (java.lang, java.util, etc.)
- Supporting files

**Who needs it?** Anyone running Java applications.

### JVM (Java Virtual Machine)
The **engine** that runs Java bytecode.

**Key Features:**
- Platform independence ("Write Once, Run Anywhere")
- Memory management (Garbage Collection)
- Security (sandboxing)

```
┌─────────────────────────────────────┐
│              JDK                    │
│  ┌───────────────────────────────┐  │
│  │  javac (Compiler)             │  │
│  │  jdb (Debugger)               │  │
│  │  javadoc (Documentation)      │  │
│  └───────────────────────────────┘  │
│  ┌───────────────────────────────┐  │
│  │            JRE                │  │
│  │  ┌─────────────────────────┐  │  │
│  │  │         JVM             │  │  │
│  │  │   (Runs bytecode)       │  │  │
│  │  └─────────────────────────┘  │  │
│  │  Core Libraries (rt.jar)      │  │
│  └───────────────────────────────┘  │
└─────────────────────────────────────┘
```

---

## 🔧 Installation

### Windows
1. Download JDK from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [Adoptium](https://adoptium.net/)
2. Run the installer
3. Add to PATH:
   ```cmd
   setx JAVA_HOME "C:\Program Files\Java\jdk-21"
   setx PATH "%PATH%;%JAVA_HOME%\bin"
   ```
4. Verify: `java --version` and `javac --version`

### Verify Installation
```bash
# Check Java version
java --version

# Check compiler version  
javac --version

# Check where Java is installed
where java
```

---

## 🚀 Your First Java Program

### Create `HelloWorld.java`
```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

### Compile & Run
```bash
# Compile (creates HelloWorld.class)
javac HelloWorld.java

# Run
java HelloWorld
```

### What Happened?
1. `javac` compiled `HelloWorld.java` → `HelloWorld.class` (bytecode)
2. `java` loaded the JVM and executed the bytecode
3. JVM found `main` method and executed it

---

## 📝 Anatomy of a Java Program

```java
public class HelloWorld {           // Class declaration
    public static void main(String[] args) {  // Entry point
        System.out.println("Hello, World!");  // Print statement
    }
}
```

| Part | Meaning |
|------|---------|
| `public` | Accessible from anywhere |
| `class` | Blueprint for objects |
| `HelloWorld` | Class name (must match filename!) |
| `static` | Belongs to class, not instance |
| `void` | Returns nothing |
| `main` | Special method name - entry point |
| `String[] args` | Command line arguments |
| `System.out.println()` | Print to console with newline |

---

## 🧪 Try It Yourself

1. Create a file `Greeting.java`:
```java
public class Greeting {
    public static void main(String[] args) {
        String name = "Your Name";
        System.out.println("Hello, " + name + "!");
        System.out.println("Welcome to Java programming!");
    }
}
```

2. Compile and run it
3. What happens if you name the file `greeting.java` (lowercase)? Try it!

---

## ❓ Common Errors

### File name doesn't match class name
```
error: class HelloWorld is public, should be declared in a file named HelloWorld.java
```
**Fix:** File name MUST match the public class name exactly (case-sensitive).

### Missing semicolon
```
error: ';' expected
```
**Fix:** Add `;` at the end of every statement.

### `java.lang.NoClassDefFoundError`
```
Error: Could not find or load main class HelloWorld
```
**Fix:** Make sure you're in the correct directory and compiled first.

---

## 🔑 Key Takeaways
- **JDK** = Development tools + JRE (for developers)
- **JRE** = Runtime environment + JVM (for running apps)
- **JVM** = The engine that runs bytecode (platform independent)
- File name must match the public class name
- `javac` compiles, `java` runs
- Every program needs a `main` method as entry point
