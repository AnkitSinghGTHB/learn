# Day 4: Methods - Static vs Instance

## 🎯 Learning Goals
- Write reusable methods
- Understand static vs instance methods
- Master method overloading
- Learn parameter passing behavior

---

## 📚 Method Basics

### Method Structure
```java
accessModifier returnType methodName(parameters) {
    // method body
    return value;  // if returnType isn't void
}
```

### Simple Examples
```java
public class Calculator {
    
    // Method that returns a value
    public static int add(int a, int b) {
        return a + b;
    }
    
    // Method that returns nothing (void)
    public static void greet(String name) {
        System.out.println("Hello, " + name + "!");
    }
    
    // Method with no parameters
    public static double getPi() {
        return 3.14159;
    }
    
    public static void main(String[] args) {
        int sum = add(5, 3);        // 8
        greet("Alice");              // Hello, Alice!
        double pi = getPi();         // 3.14159
    }
}
```

---

## ⚡ Static vs Instance Methods

### Static Methods
- Belong to the **class**, not any specific object
- Called using: `ClassName.methodName()`
- Can only access other static members
- No `this` keyword available

```java
public class MathUtils {
    // Static method
    public static int square(int n) {
        return n * n;
    }
    
    public static int max(int a, int b) {
        return (a > b) ? a : b;
    }
}

// Usage - no object needed!
int result = MathUtils.square(5);  // 25
int bigger = MathUtils.max(10, 7); // 10
```

### Instance Methods
- Belong to **objects** (instances of a class)
- Called using: `objectName.methodName()`
- Can access both instance and static members
- Have access to `this` keyword

```java
public class BankAccount {
    // Instance variable
    private double balance;
    
    // Constructor
    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }
    
    // Instance method
    public void deposit(double amount) {
        this.balance += amount;
    }
    
    // Instance method
    public double getBalance() {
        return this.balance;
    }
}

// Usage - object required!
BankAccount myAccount = new BankAccount(100);
myAccount.deposit(50);
System.out.println(myAccount.getBalance());  // 150
```

### When to Use Each

| Use Static When... | Use Instance When... |
|-------------------|---------------------|
| No object state needed | Method needs object data |
| Utility/helper functions | Method modifies object state |
| Factory methods | Behavior varies per object |
| `main` method | Most OOP scenarios |

```java
public class Example {
    private String name;  // Instance variable
    
    // Instance method - uses 'name'
    public void sayHello() {
        System.out.println("Hello, I'm " + this.name);
    }
    
    // Static method - doesn't need object state
    public static void printWelcome() {
        System.out.println("Welcome to the program!");
    }
}
```

---

## 🔄 Method Overloading

Same method name, different parameters:

```java
public class Printer {
    
    // Overloaded methods - same name, different signatures
    public static void print(String message) {
        System.out.println(message);
    }
    
    public static void print(int number) {
        System.out.println(number);
    }
    
    public static void print(String message, int times) {
        for (int i = 0; i < times; i++) {
            System.out.println(message);
        }
    }
    
    public static void print(int... numbers) {  // Varargs
        for (int n : numbers) {
            System.out.print(n + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        print("Hello");           // Calls first version
        print(42);                // Calls second version
        print("Hi", 3);           // Calls third version
        print(1, 2, 3, 4, 5);     // Calls fourth version
    }
}
```

### Note: Return type alone doesn't differentiate methods
```java
// This is NOT valid overloading!
public int getValue() { return 1; }
public double getValue() { return 1.0; }  // ERROR!
```

---

## 📋 Parameter Passing

### Primitives are Passed by Value (Copy)
```java
public static void modify(int x) {
    x = 100;  // Changes local copy only
}

public static void main(String[] args) {
    int num = 5;
    modify(num);
    System.out.println(num);  // Still 5!
}
```

### Objects are Passed by Reference Value
```java
public static void modify(int[] arr) {
    arr[0] = 100;  // Modifies original array!
}

public static void reassign(int[] arr) {
    arr = new int[] {9, 9, 9};  // Creates new array, doesn't affect original
}

public static void main(String[] args) {
    int[] numbers = {1, 2, 3};
    
    modify(numbers);
    System.out.println(numbers[0]);  // 100 (changed!)
    
    reassign(numbers);
    System.out.println(numbers[0]);  // 100 (not changed to 9)
}
```

---

## 🔙 Return Statements

```java
// Single return value
public static int add(int a, int b) {
    return a + b;
}

// Early return (guard clause)
public static double divide(int a, int b) {
    if (b == 0) {
        return 0;  // Early exit
    }
    return (double) a / b;
}

// Return boolean
public static boolean isEven(int n) {
    return n % 2 == 0;
}

// Return array
public static int[] getFirstAndLast(int[] arr) {
    return new int[] { arr[0], arr[arr.length - 1] };
}
```

---

## 🧪 Try It Yourself

```java
public class MethodPractice {
    
    // 1. Static utility method
    public static int findMax(int[] numbers) {
        int max = numbers[0];
        for (int num : numbers) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }
    
    // 2. Overloaded methods
    public static double average(int a, int b) {
        return (a + b) / 2.0;
    }
    
    public static double average(int a, int b, int c) {
        return (a + b + c) / 3.0;
    }
    
    public static double average(int[] numbers) {
        int sum = 0;
        for (int n : numbers) {
            sum += n;
        }
        return (double) sum / numbers.length;
    }
    
    // 3. Method that modifies array
    public static void doubleValues(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] *= 2;
        }
    }
    
    public static void main(String[] args) {
        // Test findMax
        int[] scores = {75, 92, 88, 67, 95};
        System.out.println("Max score: " + findMax(scores));
        
        // Test overloaded average
        System.out.println("Average of 5 and 10: " + average(5, 10));
        System.out.println("Average of 5, 10, 15: " + average(5, 10, 15));
        System.out.println("Average of array: " + average(scores));
        
        // Test array modification
        int[] values = {1, 2, 3, 4, 5};
        doubleValues(values);
        for (int v : values) {
            System.out.print(v + " ");  // 2 4 6 8 10
        }
    }
}
```

---

## 🔑 Key Takeaways
- **Static methods** belong to class, called with `ClassName.method()`
- **Instance methods** belong to objects, need an object to call
- **Method overloading** = same name, different parameters
- **Primitives** are passed by value (copy)
- **Objects/arrays** are passed by reference value (can modify contents)
- `void` means no return; otherwise `return` is required
- `main` is static because JVM calls it without creating an object
