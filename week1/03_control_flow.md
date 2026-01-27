# Control Flow

## if / elif / else

Make decisions in your code.

```python
age = 18

if age < 13:
    print("Child")
elif age < 20:
    print("Teenager")
else:
    print("Adult")
```

### Important: Indentation!
Python uses **indentation** (4 spaces) to define code blocks - not braces `{}`.

```python
# ✅ Correct
if True:
    print("Indented correctly")

# ❌ Wrong - will cause IndentationError
if True:
print("Not indented")
```

### Comparison Operators
```python
x = 10

if x == 10:     # Equal
    print("x is 10")

if x != 5:      # Not equal
    print("x is not 5")

if x > 5:       # Greater than
    print("x is greater than 5")

if x >= 10:     # Greater or equal
    print("x is 10 or more")
```

### Logical Operators
```python
age = 25
has_license = True

# AND - both must be true
if age >= 18 and has_license:
    print("You can drive")

# OR - at least one must be true
if age < 18 or not has_license:
    print("You cannot drive")

# NOT - inverts boolean
if not has_license:
    print("Get a license first")
```

### Nested Conditions
```python
is_weekend = True
is_sunny = True

if is_weekend:
    if is_sunny:
        print("Go to the beach!")
    else:
        print("Watch movies at home")
else:
    print("Go to work")
```

## match (Python 3.10+)

Like switch-case in other languages.

```python
command = input("Enter command: ")

match command:
    case "start":
        print("Starting...")
    case "stop":
        print("Stopping...")
    case "restart":
        print("Restarting...")
    case _:  # Default case (underscore = wildcard)
        print("Unknown command")
```

### Match with Patterns
```python
point = (0, 5)

match point:
    case (0, 0):
        print("Origin")
    case (0, y):
        print(f"On Y-axis at y={y}")
    case (x, 0):
        print(f"On X-axis at x={x}")
    case (x, y):
        print(f"Point at ({x}, {y})")
```

## for Loops

Iterate over sequences.

```python
# Loop through a list
fruits = ["apple", "banana", "cherry"]
for fruit in fruits:
    print(fruit)

# Loop with range()
for i in range(5):       # 0, 1, 2, 3, 4
    print(i)

for i in range(2, 6):    # 2, 3, 4, 5
    print(i)

for i in range(0, 10, 2): # 0, 2, 4, 6, 8 (step by 2)
    print(i)

# Loop through string
for char in "Python":
    print(char)

# Loop with index using enumerate()
colors = ["red", "green", "blue"]
for index, color in enumerate(colors):
    print(f"{index}: {color}")
```

## while Loops

Repeat while a condition is true.

```python
count = 0
while count < 5:
    print(count)
    count += 1  # Don't forget to update!

# User input loop
while True:
    answer = input("Continue? (y/n): ")
    if answer.lower() == 'n':
        break
```

## Loop Control

### break - Exit the loop
```python
for i in range(10):
    if i == 5:
        break  # Stop at 5
    print(i)
# Output: 0, 1, 2, 3, 4
```

### continue - Skip to next iteration
```python
for i in range(5):
    if i == 2:
        continue  # Skip 2
    print(i)
# Output: 0, 1, 3, 4
```

### else with loops
```python
# else runs if loop completes without break
for i in range(5):
    print(i)
else:
    print("Loop completed!")

# else won't run if break is used
for i in range(5):
    if i == 3:
        break
    print(i)
else:
    print("This won't print")
```

## Practice Exercises

1. Write a program that prints "Fizz" for multiples of 3, "Buzz" for 5, "FizzBuzz" for both
2. Create a simple menu using match/case
3. Print a multiplication table using nested loops
4. Count down from 10 to 1 using a while loop

---
**Next**: [04_functions.md](04_functions.md)
