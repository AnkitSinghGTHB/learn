# Day 5: Strings & String Manipulation

## 🎯 Learning Goals
- Understand String immutability
- Master common String methods
- Know when to use StringBuilder
- Understand `==` vs `.equals()` for Strings

---

## 📚 String Basics

### Creating Strings
```java
// String literal (preferred - uses String Pool)
String s1 = "Hello";

// Using new keyword (creates new object)
String s2 = new String("Hello");

// Empty string
String empty = "";

// From char array
char[] chars = {'H', 'i'};
String s3 = new String(chars);  // "Hi"
```

### String Immutability
Strings in Java are **immutable** - they cannot be changed after creation:

```java
String s = "Hello";
s.toUpperCase();           // Returns "HELLO" but doesn't modify s
System.out.println(s);      // Still "Hello"

// To get the changed value, reassign:
s = s.toUpperCase();
System.out.println(s);      // Now "HELLO"
```

**Why immutable?**
- Thread safety
- Security (URLs, passwords can't be modified)
- String Pool optimization
- Hash code caching

---

## ⚠️ String Comparison: == vs .equals()

This is a **common gotcha** for beginners!

### `==` Compares References (Memory Addresses)
### `.equals()` Compares Content

```java
String a = "Hello";
String b = "Hello";
String c = new String("Hello");

// Using == (compares references)
System.out.println(a == b);     // true (both point to same pool object)
System.out.println(a == c);     // false! (c is a new object)

// Using .equals() (compares content) ✓ ALWAYS USE THIS
System.out.println(a.equals(b)); // true
System.out.println(a.equals(c)); // true

// Case-insensitive comparison
System.out.println("Hello".equalsIgnoreCase("hello")); // true
```

### The String Pool
```
┌─────────────────────────────────┐
│         String Pool             │
│   ┌───────────────────────┐     │
│   │      "Hello"          │◄────┼── a, b
│   └───────────────────────┘     │
└─────────────────────────────────┘

┌─────────────────────────────────┐
│           Heap                  │
│   ┌───────────────────────┐     │
│   │      "Hello"          │◄────┼── c (new String)
│   └───────────────────────┘     │
└─────────────────────────────────┘
```

---

## 🛠️ Essential String Methods

### Length and Characters
```java
String s = "Hello World";

s.length();                  // 11
s.charAt(0);                 // 'H'
s.charAt(6);                 // 'W'
s.isEmpty();                 // false
s.isBlank();                 // false (Java 11+, checks whitespace too)
```

### Searching
```java
String s = "Hello World";

s.indexOf('o');              // 4 (first occurrence)
s.lastIndexOf('o');          // 7 (last occurrence)
s.indexOf("World");          // 6
s.contains("World");         // true
s.startsWith("Hello");       // true
s.endsWith("World");         // true
```

### Extracting Substrings
```java
String s = "Hello World";

s.substring(0, 5);           // "Hello" (start to end-1)
s.substring(6);              // "World" (start to end)
```

### Case Conversion
```java
String s = "Hello World";

s.toUpperCase();             // "HELLO WORLD"
s.toLowerCase();             // "hello world"
```

### Trimming Whitespace
```java
String s = "  Hello World  ";

s.trim();                    // "Hello World" (removes leading/trailing)
s.strip();                   // "Hello World" (Java 11+, Unicode-aware)
s.stripLeading();            // "Hello World  "
s.stripTrailing();           // "  Hello World"
```

### Replacing
```java
String s = "Hello World";

s.replace('l', 'x');         // "Hexxo Worxd" (all occurrences)
s.replace("World", "Java");  // "Hello Java"
s.replaceFirst("l", "L");    // "HeLlo World" (first only)
s.replaceAll("l+", "L");     // "HeL WorLd" (regex)
```

### Splitting
```java
String csv = "apple,banana,cherry";
String[] fruits = csv.split(",");
// fruits = ["apple", "banana", "cherry"]

String sentence = "Hello   World";
String[] words = sentence.split("\\s+");  // Split on whitespace
// words = ["Hello", "World"]

// Limit splits
String[] parts = "a:b:c:d".split(":", 2);
// parts = ["a", "b:c:d"]
```

### Joining
```java
String[] words = {"Hello", "World"};
String joined = String.join(" ", words);  // "Hello World"

String joined2 = String.join("-", "a", "b", "c");  // "a-b-c"
```

---

## 🔧 StringBuilder (Mutable Strings)

For many string modifications, use StringBuilder (more efficient):

```java
// Inefficient - creates many intermediate String objects
String result = "";
for (int i = 0; i < 5; i++) {
    result += i;  // Creates new String each time!
}

// Efficient - uses mutable StringBuilder
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 5; i++) {
    sb.append(i);
}
String result = sb.toString();  // "01234"
```

### StringBuilder Methods
```java
StringBuilder sb = new StringBuilder("Hello");

sb.append(" World");         // "Hello World"
sb.insert(5, ",");           // "Hello, World"
sb.delete(5, 6);             // "Hello World"
sb.replace(6, 11, "Java");   // "Hello Java"
sb.reverse();                // "avaJ olleH"
sb.reverse();                // "Hello Java" (back to normal)

String result = sb.toString();  // Convert back to String
```

---

## 📊 String Formatting

### printf / String.format
```java
String name = "Alice";
int age = 25;
double score = 95.5;

// printf (print formatted)
System.out.printf("Name: %s, Age: %d, Score: %.2f%n", name, age, score);

// String.format (returns formatted String)
String formatted = String.format("Name: %s, Age: %d", name, age);
```

### Common Format Specifiers
| Specifier | Meaning | Example |
|-----------|---------|---------|
| `%s` | String | "Hello" |
| `%d` | Integer | 42 |
| `%f` | Float/Double | 3.140000 |
| `%.2f` | Float (2 decimals) | 3.14 |
| `%n` | Newline | (platform-specific) |
| `%5d` | Integer (min 5 width) | "   42" |
| `%-5d` | Integer (left-aligned) | "42   " |

---

## 🧪 Try It Yourself

```java
public class StringPractice {
    public static void main(String[] args) {
        // 1. String comparison
        String a = "Java";
        String b = new String("Java");
        System.out.println("== : " + (a == b));           // false
        System.out.println("equals: " + a.equals(b));     // true
        
        // 2. Extract information
        String email = "user@example.com";
        String username = email.substring(0, email.indexOf("@"));
        String domain = email.substring(email.indexOf("@") + 1);
        System.out.println("Username: " + username);  // user
        System.out.println("Domain: " + domain);      // example.com
        
        // 3. String manipulation
        String sentence = "  The Quick Brown Fox  ";
        sentence = sentence.trim().toLowerCase().replace(" ", "_");
        System.out.println(sentence);  // the_quick_brown_fox
        
        // 4. Split and process
        String csv = "John,25,Engineer";
        String[] parts = csv.split(",");
        System.out.println("Name: " + parts[0]);
        System.out.println("Age: " + parts[1]);
        System.out.println("Job: " + parts[2]);
        
        // 5. StringBuilder efficiency
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            sb.append(i);
            if (i < 5) sb.append("-");
        }
        System.out.println(sb.toString());  // 1-2-3-4-5
        
        // 6. Formatting
        double price = 19.99;
        int quantity = 3;
        String receipt = String.format("Qty: %d x $%.2f = $%.2f", 
                                       quantity, price, quantity * price);
        System.out.println(receipt);  // Qty: 3 x $19.99 = $59.97
    }
}
```

---

## 🔑 Key Takeaways
- Strings are **immutable** - methods return new strings
- **ALWAYS use `.equals()`** for string comparison, not `==`
- `==` compares references, `.equals()` compares content
- Use **StringBuilder** for many modifications (efficient)
- String Pool stores string literals for memory efficiency
- Important methods: `substring`, `split`, `replace`, `trim`, `indexOf`
- Use `String.format` or `printf` for formatted output
