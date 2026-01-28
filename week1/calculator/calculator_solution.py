"""
Simple Calculator - Complete Solution
Use this to check your work!
"""

def add(a: float, b: float) -> float:
    """Add two numbers."""
    return a + b

def subtract(a: float, b: float) -> float:
    """Subtract b from a."""
    return a - b

def multiply(a: float, b: float) -> float:
    """Multiply two numbers."""
    return a * b

def divide(a: float, b: float) -> float:
    """Divide a by b. Handle division by zero!"""
    if b == 0:
        print("Error: Cannot divide by zero!")
        return None
    return a / b

def main():
    print("=== Simple Calculator ===")
    
    # Get numbers from user
    num1 = float(input("Enter first number: "))
    num2 = float(input("Enter second number: "))
    
    # Get operation
    operation = input("Choose operation (+, -, *, /): ")
    
    # Perform calculation
    result = None
    
    match operation:
        case "+":
            result = add(num1, num2)
        case "-":
            result = subtract(num1, num2)
        case "*":
            result = multiply(num1, num2)
        case "/":
            result = divide(num1, num2)
        case _:
            print("Invalid operation!")
            return
    
    # Print result
    if result is not None:
        print(f"Result: {num1} {operation} {num2} = {result}")

if __name__ == "__main__":
    main()
