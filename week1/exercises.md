# Python Basics Exercises

Practice what you've learned with these exercises!

---

## Exercise 1: Personal Info
**Difficulty**: ⭐

Create variables for:
- Your name
- Your age
- Your favorite programming language

Print them in a nice formatted message using f-strings.

```python
# Your code here
```

<details>
<summary>💡 Solution</summary>

```python
name = "Alice"
age = 25
fav_language = "Python"

print(f"Hi! I'm {name}, {age} years old.")
print(f"My favorite language is {fav_language}!")
```
</details>

---

## Exercise 2: Temperature Converter
**Difficulty**: ⭐

Convert Celsius to Fahrenheit.
Formula: `F = C * 9/5 + 32`

```python
celsius = 25
# Convert to fahrenheit
```

<details>
<summary>💡 Solution</summary>

```python
celsius = 25
fahrenheit = celsius * 9/5 + 32
print(f"{celsius}°C = {fahrenheit}°F")
```
</details>

---

## Exercise 3: Even or Odd
**Difficulty**: ⭐

Write a program that asks for a number and prints if it's even or odd.

```python
# Your code here
```

<details>
<summary>💡 Solution</summary>

```python
num = int(input("Enter a number: "))

if num % 2 == 0:
    print(f"{num} is even")
else:
    print(f"{num} is odd")
```
</details>

---

## Exercise 4: FizzBuzz
**Difficulty**: ⭐⭐

Print numbers 1-20 with these rules:
- If divisible by 3: print "Fizz"
- If divisible by 5: print "Buzz"
- If divisible by both: print "FizzBuzz"
- Otherwise: print the number

```python
# Your code here
```

<details>
<summary>💡 Solution</summary>

```python
for i in range(1, 21):
    if i % 3 == 0 and i % 5 == 0:
        print("FizzBuzz")
    elif i % 3 == 0:
        print("Fizz")
    elif i % 5 == 0:
        print("Buzz")
    else:
        print(i)
```
</details>

---

## Exercise 5: Sum of Numbers
**Difficulty**: ⭐⭐

Calculate the sum of numbers from 1 to N (user input).

```python
# Your code here
```

<details>
<summary>💡 Solution</summary>

```python
n = int(input("Enter N: "))
total = 0

for i in range(1, n + 1):
    total += i

print(f"Sum of 1 to {n} = {total}")

# Or using the formula:
# total = n * (n + 1) // 2
```
</details>

---

## Exercise 6: Factorial Function
**Difficulty**: ⭐⭐

Write a function that calculates factorial.
`5! = 5 × 4 × 3 × 2 × 1 = 120`

```python
def factorial(n: int) -> int:
    # Your code here
    pass
```

<details>
<summary>💡 Solution</summary>

```python
def factorial(n: int) -> int:
    if n == 0 or n == 1:
        return 1
    
    result = 1
    for i in range(2, n + 1):
        result *= i
    return result

# Test
print(factorial(5))  # 120
print(factorial(0))  # 1
```
</details>

---

## Exercise 7: Prime Checker
**Difficulty**: ⭐⭐⭐

Write a function to check if a number is prime.

```python
def is_prime(n: int) -> bool:
    # Your code here
    pass
```

<details>
<summary>💡 Solution</summary>

```python
def is_prime(n: int) -> bool:
    if n < 2:
        return False
    if n == 2:
        return True
    if n % 2 == 0:
        return False
    
    for i in range(3, int(n ** 0.5) + 1, 2):
        if n % i == 0:
            return False
    return True

# Test
for num in range(1, 20):
    if is_prime(num):
        print(f"{num} is prime")
```
</details>

---

## Exercise 8: Multiplication Table
**Difficulty**: ⭐⭐

Print a multiplication table from 1-10 using nested loops.

```python
# Your code here
```

<details>
<summary>💡 Solution</summary>

```python
print("   ", end="")
for i in range(1, 11):
    print(f"{i:4}", end="")
print()
print("-" * 44)

for i in range(1, 11):
    print(f"{i:2} |", end="")
    for j in range(1, 11):
        print(f"{i*j:4}", end="")
    print()
```
</details>

---

## Exercise 9: Password Validator
**Difficulty**: ⭐⭐⭐

Write a function that validates a password:
- At least 8 characters
- Contains at least one digit
- Contains at least one uppercase letter

```python
def is_valid_password(password: str) -> bool:
    # Your code here
    pass
```

<details>
<summary>💡 Solution</summary>

```python
def is_valid_password(password: str) -> bool:
    if len(password) < 8:
        return False
    
    has_digit = False
    has_upper = False
    
    for char in password:
        if char.isdigit():
            has_digit = True
        if char.isupper():
            has_upper = True
    
    return has_digit and has_upper

# Test
passwords = ["short", "alllowercase123", "NoDigitsHere", "ValidPass1"]
for p in passwords:
    print(f"{p}: {'✓' if is_valid_password(p) else '✗'}")
```
</details>

---

## Exercise 10: Mini Menu System
**Difficulty**: ⭐⭐⭐

Create a menu-driven program that:
1. Greets the user
2. Shows a menu
3. Performs actions based on choice
4. Loops until user exits

```python
# Your code here
```

<details>
<summary>💡 Solution</summary>

```python
def show_menu():
    print("\n=== Main Menu ===")
    print("1. Say Hello")
    print("2. Tell a Joke")
    print("3. Show Time")
    print("4. Exit")
    return input("Choose option: ")

def tell_joke():
    import random
    jokes = [
        "Why do programmers prefer dark mode? Because light attracts bugs!",
        "A SQL query walks into a bar, walks up to two tables and asks... 'Can I join you?'",
        "Why do Java developers wear glasses? Because they can't C#!"
    ]
    print(random.choice(jokes))

def show_time():
    from datetime import datetime
    print(f"Current time: {datetime.now().strftime('%H:%M:%S')}")

def main():
    name = input("What's your name? ")
    print(f"Welcome, {name}!")
    
    while True:
        choice = show_menu()
        
        match choice:
            case "1":
                print(f"Hello, {name}! 👋")
            case "2":
                tell_joke()
            case "3":
                show_time()
            case "4":
                print("Goodbye!")
                break
            case _:
                print("Invalid option!")

if __name__ == "__main__":
    main()
```
</details>

---

**Great job completing Week 1! 🎉**
