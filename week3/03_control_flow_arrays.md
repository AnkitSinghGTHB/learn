# Day 3: Control Flow & Arrays

## 🎯 Learning Goals
- Master if/else, switch statements
- Use for, while, and enhanced for loops
- Work with fixed-size arrays

---

## 🔀 Conditional Statements

### if / else if / else
```java
int score = 85;

if (score >= 90) {
    System.out.println("Grade: A");
} else if (score >= 80) {
    System.out.println("Grade: B");
} else if (score >= 70) {
    System.out.println("Grade: C");
} else if (score >= 60) {
    System.out.println("Grade: D");
} else {
    System.out.println("Grade: F");
}
```

### Ternary Operator
```java
int age = 20;
String status = (age >= 18) ? "Adult" : "Minor";
System.out.println(status);  // Adult

// Equivalent to:
String status2;
if (age >= 18) {
    status2 = "Adult";
} else {
    status2 = "Minor";
}
```

### switch Statement
```java
int dayNumber = 3;
String dayName;

switch (dayNumber) {
    case 1:
        dayName = "Monday";
        break;
    case 2:
        dayName = "Tuesday";
        break;
    case 3:
        dayName = "Wednesday";
        break;
    case 4:
        dayName = "Thursday";
        break;
    case 5:
        dayName = "Friday";
        break;
    case 6:
    case 7:
        dayName = "Weekend";
        break;
    default:
        dayName = "Invalid";
}

System.out.println(dayName);  // Wednesday
```

### Modern Switch Expression (Java 14+)
```java
int dayNumber = 3;

String dayName = switch (dayNumber) {
    case 1 -> "Monday";
    case 2 -> "Tuesday";
    case 3 -> "Wednesday";
    case 4 -> "Thursday";
    case 5 -> "Friday";
    case 6, 7 -> "Weekend";
    default -> "Invalid";
};

System.out.println(dayName);  // Wednesday
```

---

## 🔁 Loops

### for Loop
```java
// Basic for loop
for (int i = 0; i < 5; i++) {
    System.out.println("Iteration: " + i);
}
// Output: 0, 1, 2, 3, 4

// Counting backwards
for (int i = 5; i > 0; i--) {
    System.out.println(i);
}
// Output: 5, 4, 3, 2, 1

// Step by 2
for (int i = 0; i <= 10; i += 2) {
    System.out.println(i);
}
// Output: 0, 2, 4, 6, 8, 10
```

### while Loop
```java
int count = 0;

while (count < 5) {
    System.out.println("Count: " + count);
    count++;
}
```

### do-while Loop
Executes at least once:
```java
int num = 10;

do {
    System.out.println("Number: " + num);
    num++;
} while (num < 5);  // Condition is false, but prints once!

// Output: Number: 10
```

### break and continue
```java
// break - exit loop entirely
for (int i = 0; i < 10; i++) {
    if (i == 5) {
        break;  // Exit when i is 5
    }
    System.out.println(i);
}
// Output: 0, 1, 2, 3, 4

// continue - skip current iteration
for (int i = 0; i < 5; i++) {
    if (i == 2) {
        continue;  // Skip when i is 2
    }
    System.out.println(i);
}
// Output: 0, 1, 3, 4
```

---

## 📦 Arrays

### Array Basics
Arrays in Java are **fixed-size** (unlike Python lists):

```java
// Declaration and initialization
int[] numbers = new int[5];  // Array of 5 integers (all 0)
String[] names = new String[3];  // Array of 3 strings (all null)

// Declaration with values
int[] scores = {95, 87, 73, 88, 91};
String[] fruits = {"Apple", "Banana", "Cherry"};

// Access elements (0-indexed)
System.out.println(scores[0]);  // 95 (first)
System.out.println(scores[4]);  // 91 (last)

// Modify elements
scores[0] = 100;
System.out.println(scores[0]);  // 100

// Array length
System.out.println(scores.length);  // 5
```

### Looping Through Arrays
```java
int[] numbers = {10, 20, 30, 40, 50};

// Method 1: Traditional for loop
for (int i = 0; i < numbers.length; i++) {
    System.out.println("Index " + i + ": " + numbers[i]);
}

// Method 2: Enhanced for loop (for-each)
for (int num : numbers) {
    System.out.println(num);
}
// Note: Can't get index with enhanced for loop

// Method 3: while loop
int i = 0;
while (i < numbers.length) {
    System.out.println(numbers[i]);
    i++;
}
```

### Common Array Operations
```java
int[] numbers = {45, 12, 89, 33, 67};

// Find maximum
int max = numbers[0];
for (int num : numbers) {
    if (num > max) {
        max = num;
    }
}
System.out.println("Max: " + max);  // 89

// Find minimum
int min = numbers[0];
for (int num : numbers) {
    if (num < min) {
        min = num;
    }
}
System.out.println("Min: " + min);  // 12

// Calculate sum
int sum = 0;
for (int num : numbers) {
    sum += num;
}
System.out.println("Sum: " + sum);  // 246

// Calculate average
double avg = (double) sum / numbers.length;
System.out.println("Average: " + avg);  // 49.2
```

### 2D Arrays
```java
// 2D array (matrix)
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};

// Access element
System.out.println(matrix[1][2]);  // 6 (row 1, col 2)

// Loop through 2D array
for (int row = 0; row < matrix.length; row++) {
    for (int col = 0; col < matrix[row].length; col++) {
        System.out.print(matrix[row][col] + " ");
    }
    System.out.println();
}
```

### Array Gotchas
```java
// Can't resize arrays!
int[] arr = {1, 2, 3};
// arr.add(4);  // ERROR! Arrays don't have add()

// ArrayIndexOutOfBoundsException
int[] nums = {1, 2, 3};
// System.out.println(nums[5]);  // RUNTIME ERROR!

// Arrays are reference types
int[] a = {1, 2, 3};
int[] b = a;  // b points to same array!
b[0] = 99;
System.out.println(a[0]);  // 99 (changed!)

// To copy an array
int[] c = a.clone();  // or Arrays.copyOf(a, a.length)
c[0] = 1;
System.out.println(a[0]);  // 99 (unchanged)
```

---

## 🧪 Try It Yourself

```java
public class ArrayPractice {
    public static void main(String[] args) {
        int[] temperatures = {72, 68, 75, 80, 69, 73, 77};
        
        // 1. Find the hottest day
        int hottest = temperatures[0];
        int hottestDay = 0;
        for (int i = 0; i < temperatures.length; i++) {
            if (temperatures[i] > hottest) {
                hottest = temperatures[i];
                hottestDay = i;
            }
        }
        System.out.println("Hottest: " + hottest + " on day " + (hottestDay + 1));
        
        // 2. Count days above 73
        int warmDays = 0;
        for (int temp : temperatures) {
            if (temp > 73) {
                warmDays++;
            }
        }
        System.out.println("Days above 73: " + warmDays);
        
        // 3. Calculate weekly average
        int total = 0;
        for (int temp : temperatures) {
            total += temp;
        }
        double average = (double) total / temperatures.length;
        System.out.println("Average: " + average);
    }
}
```

---

## 🔑 Key Takeaways
- Use `switch` for multiple exact-value comparisons
- Three loop types: `for`, `while`, `do-while`
- Enhanced for (`for-each`) is cleanest for iterating arrays
- Arrays are **fixed-size** - use `ArrayList` for dynamic sizing
- Arrays are **0-indexed**
- Use `.length` (not `.length()`) for array size
- Arrays are reference types - assignment creates a reference, not a copy
