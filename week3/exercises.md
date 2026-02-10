# Week 03: Exercises

## 📝 Exercise 1: Data Types & Operators
Create `Ex1.java` that demonstrates your understanding of Java data types.

### Requirements:
1. Declare variables of each primitive type (int, double, boolean, char)
2. Show autoboxing/unboxing with Integer
3. Demonstrate integer division vs double division
4. Use compound assignment operators (+=, -=, etc.)

### Expected Output:
```
=== Data Types Demo ===
Integer: 42
Double: 3.14159
Boolean: true
Character: J

=== Wrapper Classes ===
Integer wrapper: 100
Unboxed value: 100

=== Division Demo ===
5 / 2 = 2 (integer division)
5.0 / 2 = 2.5 (double division)

=== Compound Operators ===
Starting value: 10
After += 5: 15
After -= 3: 12
After *= 2: 24
After /= 4: 6
```

---

## 📝 Exercise 2: Control Flow
Create `Ex2.java` with a number guessing game.

### Requirements:
1. Generate a random number between 1-100
2. Prompt user for guesses (use Scanner)
3. Tell user if guess is too high, too low, or correct
4. Track number of attempts
5. Continue until correct guess

### Hints:
```java
import java.util.Scanner;
import java.util.Random;

Random random = new Random();
int secretNumber = random.nextInt(100) + 1;  // 1-100

Scanner scanner = new Scanner(System.in);
int guess = scanner.nextInt();
```

### Expected Output:
```
Welcome to the Number Guessing Game!
I'm thinking of a number between 1 and 100.

Enter your guess: 50
Too low! Try again.

Enter your guess: 75
Too high! Try again.

Enter your guess: 62
Congratulations! You got it in 3 attempts!
```

---

## 📝 Exercise 3: Arrays
Create `Ex3.java` that performs array operations.

### Requirements:
1. Create an array of 10 integers (you can hardcode values)
2. Find and print the maximum value
3. Find and print the minimum value
4. Calculate and print the sum
5. Calculate and print the average
6. Count how many numbers are above average

### Expected Output:
```
Array: [45, 67, 23, 89, 12, 56, 78, 34, 90, 41]
Maximum: 90
Minimum: 12
Sum: 535
Average: 53.5
Numbers above average: 5
```

---

## 📝 Exercise 4: Methods
Create `Ex4.java` with utility methods.

### Requirements:
Implement and use these methods:

```java
// Returns true if number is prime
public static boolean isPrime(int n)

// Returns factorial of n (n!)
public static long factorial(int n)

// Overloaded methods to find max
public static int max(int a, int b)
public static int max(int a, int b, int c)
public static int max(int[] arr)

// Returns reversed string
public static String reverse(String s)
```

### Expected Output:
```
isPrime(7): true
isPrime(10): false
factorial(5): 120
max(5, 10): 10
max(5, 10, 7): 10
max([3, 7, 2, 9, 4]): 9
reverse("Hello"): olleH
```

---

## 📝 Exercise 5: Strings
Create `Ex5.java` that processes a paragraph of text.

### Requirements:
1. Given a paragraph string:
   ```java
   String paragraph = "Java is a popular programming language. Java is used for building applications. Learning Java is fun!";
   ```
2. Count total characters (excluding spaces)
3. Count total words
4. Count sentences (assume sentences end with . ! ?)
5. Find how many times "Java" appears
6. Replace all occurrences of "Java" with "Python"
7. Print the first word of each sentence

### Expected Output:
```
Original: Java is a popular programming language. Java is used for building applications. Learning Java is fun!

=== Analysis ===
Characters (no spaces): 85
Words: 17
Sentences: 3
"Java" count: 3

=== Modifications ===
Replaced: Python is a popular programming language. Python is used for building applications. Learning Python is fun!

First words of each sentence: Java, Java, Learning
```

---

## 🏆 Challenge Exercise: Grade Statistics
Create `GradeStats.java` - A comprehensive program combining everything you've learned.

### Requirements:
1. Create an array of student grades (double[])
2. Implement these methods:
   - `double average(double[] grades)`
   - `double highest(double[] grades)`
   - `double lowest(double[] grades)`
   - `String letterGrade(double score)` - using switch
   - `int[] countByGrade(double[] grades)` - returns count of A, B, C, D, F
   - `void printHistogram(double[] grades)` - prints a simple text histogram

3. Main program should:
   - Print all statistics
   - Print grade distribution
   - Print letter grades for each student

### Expected Output:
```
Student Grades: [92.5, 85.0, 78.5, 95.0, 68.0, 88.5, 72.0, 81.0, 90.5, 65.0]

=== Statistics ===
Average: 81.6
Highest: 95.0
Lowest: 65.0

=== Grade Distribution ===
A (90-100): *** (3)
B (80-89):  **** (4)
C (70-79):  ** (2)
D (60-69):  * (1)
F (0-59):   (0)

=== Individual Grades ===
Student 1: 92.5 -> A
Student 2: 85.0 -> B
Student 3: 78.5 -> C
...
```

---

## ✅ Completion Checklist
- [ ] Exercise 1: Data Types & Operators
- [ ] Exercise 2: Control Flow (Number Guessing Game)
- [ ] Exercise 3: Arrays
- [ ] Exercise 4: Methods
- [ ] Exercise 5: Strings
- [ ] Challenge: Grade Statistics

## 💡 Tips
- Compile frequently: `javac FileName.java`
- Run with: `java FileName`
- Read error messages carefully - they tell you the line number!
- Use meaningful variable names
- Comment your code
