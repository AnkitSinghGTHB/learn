"""
Simple Calculator - Starter Code
Complete the TODOs to finish this project!
"""

def add(a: float, b: float) -> float:
    """Add two numbers."""
    # TODO: Implement addition
    return a+b

def subtract(a: float, b: float) -> float:
    """Subtract b from a."""
    # TODO: Implement subtraction
    return a-b

def multiply(a: float, b: float) -> float:
    """Multiply two numbers."""
    # TODO: Implement multiplication
    return a*b

def divide(a: float, b: float) -> float:
    """Divide a by b. Handle division by zero!"""
    # TODO: Implement division
    # Remember to check if b is zero!
    if b==0:
        print("Zero Division Error")
        return None
    else:
        return a/b

def mod(a: float, b: float) -> float:
    if b==0:
        print("Zero Division Error")
        return None
    else:
        return a%b

def power(a: float, b: float) -> float:
    return a**b

def main():
    print("=== Simple Calculator ===")
    
    # TODO: Get first number from user (use input() and convert to float)
    num1 = float(input("Enter first number: "))
    
    # TODO: Get second number from user
    num2 = float(input("Enter second number: "))
    
    # TODO: Ask user for operation (+, -, *, /)
    operation = input("Enter operation (+, -, *, /, %, **): ")
    
    # TODO: Use if/elif/else (or match) to call the right function
    # and store the result
    result = None
    if operation=="+":
        result=add(num1,num2)
    elif operation=="-":
        result=subtract(num1,num2)
    elif operation=="*":
        result=multiply(num1,num2)
    elif operation=="/":
        result=divide(num1,num2)
    elif operation=="%":
        result=mod(num1,num2)
    elif operation=="**":
        result=power(num1,num2)
    else:
        print("Invalid operation")
    
    # TODO: Print the result in a nice format
    # Example: "10 + 5 = 15"
    if result is not None:
        print(num1,operation,num2,"=",result)

if __name__ == "__main__":
    main()