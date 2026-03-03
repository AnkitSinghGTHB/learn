# Day 5: File I/O — java.nio.file, Path, Files, Readers & Writers

## 🎯 Learning Goals
- Understand the difference between old (`java.io`) and new (`java.nio.file`) file APIs
- Master `Path` and `Files` for all file operations
- Read and write files using modern Java approaches
- Use `BufferedReader` / `BufferedWriter` for efficient I/O
- Handle file I/O exceptions properly
- Know the `Scanner` class for parsing file content

---

## 🆚 Old vs New File API

| Feature | `java.io.File` (old) | `java.nio.file.Path` + `Files` (new) |
|---------|----------------------|---------------------------------------|
| **Available since** | Java 1.0 | Java 7+ |
| **Error handling** | Returns `false` on failure | Throws descriptive IOException |
| **Symbolic links** | No support | Full support |
| **File attributes** | Limited | Complete (permissions, timestamps) |
| **Watch service** | No | Yes (file change monitoring) |
| **Recommendation** | Legacy code only | ✅ **Use this for new code** |

> **Interview Tip**: Always mention `java.nio.file` in interviews. Using the old `java.io.File` API signals outdated knowledge.

---

## 📍 The `Path` Class

`Path` represents a file or directory location. It does NOT check if the file actually exists.

```java
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathDemo {
    public static void main(String[] args) {
        // Creating Path objects
        Path p1 = Path.of("data.txt");                           // Relative
        Path p2 = Path.of("C:", "Users", "ankit", "data.txt");   // Absolute
        Path p3 = Path.of("/home/user/documents/report.pdf");    // Unix-style
        Path p4 = Paths.get("data.txt");                         // Alternative (older)

        // Useful Path methods
        System.out.println(p2.getFileName());      // data.txt
        System.out.println(p2.getParent());        // C:\Users\ankit
        System.out.println(p2.getRoot());          // C:\
        System.out.println(p2.getNameCount());     // 3 (Users, ankit, data.txt)
        System.out.println(p2.getName(0));         // Users
        System.out.println(p2.isAbsolute());       // true
        System.out.println(p1.toAbsolutePath());   // Full path from current directory

        // Combining paths
        Path base = Path.of("C:", "Users", "ankit");
        Path full = base.resolve("documents/report.txt");
        System.out.println(full);  // C:\Users\ankit\documents\report.txt

        // Relative path between two paths
        Path from = Path.of("C:/Users/ankit");
        Path to = Path.of("C:/Users/ankit/documents/report.txt");
        System.out.println(from.relativize(to));  // documents\report.txt

        // Normalize (remove redundant . and ..)
        Path messy = Path.of("C:/Users/./ankit/../ankit/data.txt");
        System.out.println(messy.normalize());  // C:\Users\ankit\data.txt
    }
}
```

---

## 📁 The `Files` Class — Your Swiss Army Knife

### Checking File Existence and Properties
```java
import java.nio.file.*;
import java.io.IOException;

Path path = Path.of("data.txt");

// Existence checks
System.out.println(Files.exists(path));       // true/false
System.out.println(Files.notExists(path));    // true/false

// Type checks
System.out.println(Files.isRegularFile(path));  // Is it a file?
System.out.println(Files.isDirectory(path));    // Is it a directory?
System.out.println(Files.isReadable(path));     // Can we read it?
System.out.println(Files.isWritable(path));     // Can we write to it?

// File size
System.out.println(Files.size(path));  // Size in bytes
```

### Creating Files and Directories
```java
// Create a single file
Path newFile = Files.createFile(Path.of("newfile.txt"));

// Create a single directory
Path newDir = Files.createDirectory(Path.of("mydir"));

// Create nested directories (like mkdir -p)
Path nested = Files.createDirectories(Path.of("a/b/c/d"));

// Create a temporary file
Path temp = Files.createTempFile("prefix_", ".tmp");
System.out.println(temp);  // e.g., C:\Users\...\prefix_12345.tmp
```

### Copying, Moving, and Deleting
```java
import java.nio.file.StandardCopyOption;

// Copy a file
Files.copy(
    Path.of("source.txt"),
    Path.of("destination.txt"),
    StandardCopyOption.REPLACE_EXISTING  // Overwrite if exists
);

// Move/rename a file
Files.move(
    Path.of("oldname.txt"),
    Path.of("newname.txt"),
    StandardCopyOption.REPLACE_EXISTING
);

// Delete a file
Files.delete(Path.of("unwanted.txt"));           // Throws if file doesn't exist
Files.deleteIfExists(Path.of("maybe.txt"));      // Returns false if not found
```

---

## 📖 Reading Files

### Method 1: Read All at Once (Small Files)
```java
import java.nio.file.*;
import java.io.IOException;
import java.util.List;

// Read entire file as a single String
String content = Files.readString(Path.of("data.txt"));
System.out.println(content);

// Read all lines as a List<String>
List<String> lines = Files.readAllLines(Path.of("data.txt"));
for (String line : lines) {
    System.out.println(line);
}

// Read all bytes
byte[] bytes = Files.readAllBytes(Path.of("image.png"));
System.out.println("File size: " + bytes.length + " bytes");
```

> ⚠️ **Warning**: `readAllLines()` loads the ENTIRE file into memory. Don't use for large files (100MB+)!

### Method 2: Stream Lines (Large Files — Efficient)
```java
import java.nio.file.*;
import java.util.stream.Stream;

// Lazy line-by-line reading with Stream
try (Stream<String> stream = Files.lines(Path.of("bigfile.txt"))) {
    stream
        .filter(line -> !line.isEmpty())
        .map(String::trim)
        .forEach(System.out::println);
}
```

### Method 3: BufferedReader (Classic — Good for Parsing)
```java
import java.io.*;
import java.nio.file.*;

try (BufferedReader reader = Files.newBufferedReader(Path.of("data.txt"))) {
    String line;
    int lineNum = 0;
    while ((line = reader.readLine()) != null) {
        lineNum++;
        System.out.println(lineNum + ": " + line);
    }
}
```

### Method 4: Scanner (Best for Parsing Mixed Content)
```java
import java.util.Scanner;
import java.io.File;

try (Scanner scanner = new Scanner(new File("scores.txt"))) {
    while (scanner.hasNext()) {
        String name = scanner.next();
        int score = scanner.nextInt();
        System.out.println(name + ": " + score);
    }
} catch (FileNotFoundException e) {
    System.out.println("File not found!");
}
```

### Which Method to Use?

| Method | Best For | Memory | Closing |
|--------|----------|--------|---------|
| `Files.readString()` | Small files, simple content | Entire file in memory | Auto |
| `Files.readAllLines()` | Small files, line-by-line access | All lines in memory | Auto |
| `Files.lines()` | Large files, streaming | One line at a time | Use try-with-resources |
| `BufferedReader` | Parsing, custom logic | Buffered chunks | Use try-with-resources |
| `Scanner` | Mixed data types | Token by token | Use try-with-resources |

---

## ✏️ Writing Files

### Method 1: Write All at Once
```java
import java.nio.file.*;
import java.util.List;

// Write a string
Files.writeString(Path.of("output.txt"), "Hello, World!\n");

// Write with options
Files.writeString(
    Path.of("log.txt"),
    "New log entry\n",
    StandardOpenOption.CREATE,      // Create if not exists
    StandardOpenOption.APPEND       // Append to existing content
);

// Write a list of lines
List<String> lines = List.of("Line 1", "Line 2", "Line 3");
Files.write(Path.of("output.txt"), lines);
```

### Method 2: BufferedWriter (Efficient for Large Writes)
```java
import java.io.*;
import java.nio.file.*;

try (BufferedWriter writer = Files.newBufferedWriter(Path.of("report.txt"))) {
    writer.write("=== Sales Report ===");
    writer.newLine();
    writer.write("Total Revenue: $50,000");
    writer.newLine();
    writer.write("Date: 2025-03-03");
}
```

### Method 3: PrintWriter (Most Convenient — printf-style)
```java
import java.io.*;

try (PrintWriter writer = new PrintWriter(new FileWriter("formatted.txt"))) {
    writer.println("Student Grades");
    writer.println("==============");
    writer.printf("%-15s %5s %10s%n", "Name", "Grade", "Status");
    writer.printf("%-15s %5s %10s%n", "Alice", "A+", "Pass");
    writer.printf("%-15s %5s %10s%n", "Bob", "C-", "Pass");
    writer.printf("%-15s %5s %10s%n", "Charlie", "F", "Fail");
}
```

**Output in formatted.txt**:
```
Student Grades
==============
Name              Grade     Status
Alice               A+       Pass
Bob                 C-       Pass
Charlie              F       Fail
```

---

## 📂 Directory Operations

### List Directory Contents
```java
import java.nio.file.*;
import java.util.stream.Stream;

// List immediate children
try (Stream<Path> entries = Files.list(Path.of("."))) {
    entries.forEach(System.out::println);
}

// Walk entire directory tree (recursive)
try (Stream<Path> tree = Files.walk(Path.of("."))) {
    tree.filter(Files::isRegularFile)
        .forEach(System.out::println);
}

// Walk with max depth
try (Stream<Path> tree = Files.walk(Path.of("."), 2)) {
    tree.forEach(p -> System.out.println(p));
}

// Find files matching a pattern
try (Stream<Path> found = Files.find(Path.of("."), 5,
        (path, attrs) -> path.toString().endsWith(".java") && attrs.isRegularFile())) {
    found.forEach(System.out::println);
}
```

---

## 🏭 Real-World Patterns

### Pattern 1: CSV Reader
```java
import java.nio.file.*;
import java.io.*;
import java.util.*;

public class CsvReader {
    public static List<Map<String, String>> readCsv(String filename) throws IOException {
        List<Map<String, String>> records = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filename))) {
            String headerLine = reader.readLine();
            if (headerLine == null) return records;

            String[] headers = headerLine.split(",");

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> record = new HashMap<>();

                for (int i = 0; i < headers.length && i < values.length; i++) {
                    record.put(headers[i].trim(), values[i].trim());
                }
                records.add(record);
            }
        }
        return records;
    }

    public static void main(String[] args) {
        try {
            List<Map<String, String>> data = readCsv("students.csv");
            data.forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Failed to read CSV: " + e.getMessage());
        }
    }
}
```

### Pattern 2: Configuration File Loader
```java
import java.nio.file.*;
import java.io.*;
import java.util.Properties;

public class ConfigLoader {
    private Properties properties;

    public ConfigLoader(String filename) throws IOException {
        properties = new Properties();
        Path configPath = Path.of(filename);

        if (!Files.exists(configPath)) {
            throw new FileNotFoundException("Config file not found: " + filename);
        }

        try (BufferedReader reader = Files.newBufferedReader(configPath)) {
            properties.load(reader);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) return defaultValue;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
```

### Pattern 3: Log File Writer
```java
import java.nio.file.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleLogger implements AutoCloseable {
    private BufferedWriter writer;
    private static final DateTimeFormatter FMT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SimpleLogger(String filename) throws IOException {
        writer = Files.newBufferedWriter(
            Path.of(filename),
            StandardOpenOption.CREATE,
            StandardOpenOption.APPEND
        );
    }

    public void log(String level, String message) throws IOException {
        String timestamp = LocalDateTime.now().format(FMT);
        writer.write(String.format("[%s] [%s] %s", timestamp, level, message));
        writer.newLine();
        writer.flush();
    }

    public void info(String message) throws IOException { log("INFO", message); }
    public void warn(String message) throws IOException { log("WARN", message); }
    public void error(String message) throws IOException { log("ERROR", message); }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}

// Usage:
try (SimpleLogger logger = new SimpleLogger("app.log")) {
    logger.info("Application started");
    logger.warn("Low memory warning");
    logger.error("Failed to connect to database");
}
```

---

## 🧪 Try It Yourself

### Exercise: Word Counter
```java
import java.nio.file.*;
import java.io.*;
import java.util.*;

public class WordCounter {
    public static void main(String[] args) {
        Path inputFile = Path.of("sample.txt");

        // First, create a sample file
        try {
            Files.writeString(inputFile,
                "Hello world hello World\n" +
                "Java is great and java is fun\n" +
                "Hello from the Java world\n"
            );
        } catch (IOException e) {
            System.err.println("Cannot create sample: " + e.getMessage());
            return;
        }

        // Now read and count words
        Map<String, Integer> wordCounts = new TreeMap<>();
        int totalWords = 0;
        int totalLines = 0;

        try (BufferedReader reader = Files.newBufferedReader(inputFile)) {
            String line;
            while ((line = reader.readLine()) != null) {
                totalLines++;
                String[] words = line.toLowerCase().split("\\s+");
                for (String word : words) {
                    if (!word.isEmpty()) {
                        wordCounts.merge(word, 1, Integer::sum);
                        totalWords++;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Read error: " + e.getMessage());
            return;
        }

        // Write results
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Path.of("word_report.txt")))) {
            writer.println("=== Word Frequency Report ===");
            writer.printf("Total lines: %d%n", totalLines);
            writer.printf("Total words: %d%n", totalWords);
            writer.printf("Unique words: %d%n%n", wordCounts.size());
            writer.printf("%-15s %5s%n", "Word", "Count");
            writer.println("-".repeat(21));
            wordCounts.forEach((word, count) ->
                writer.printf("%-15s %5d%n", word, count)
            );
        } catch (IOException e) {
            System.err.println("Write error: " + e.getMessage());
        }

        System.out.println("Report written to word_report.txt");
    }
}
```

---

## 🔑 Key Takeaways
- Use `java.nio.file.Path` and `Files` for all modern file operations
- `Path.of()` creates path references; `Files.*` performs actual operations
- For **small files**: `Files.readString()`, `Files.readAllLines()`, `Files.writeString()`
- For **large files**: `Files.lines()` (streaming) or `BufferedReader`
- Always use **try-with-resources** with file streams, readers, and writers
- `Files.walk()` and `Files.find()` for recursive directory traversal
- `StandardOpenOption.APPEND` to add to files instead of overwriting
- All file operations throw `IOException` — handle it or declare with `throws`
