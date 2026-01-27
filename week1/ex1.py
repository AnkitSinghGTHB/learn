#Create variables for your name, age, and favorite number
#Print them using an f-string
#Ask the user for two numbers and print their sum
#Convert a temperature from Celsius to Fahrenheit: F = C * 9/5 + 32


name = "Ankit"
age = 20
favourite_number = 7

print(f"My name is {name}, I am {age} years old, and my favorite number is {favourite_number}")

num1 = int(input("Enter a number: "))
num2 = int(input("Enter a number: "))
print(f"The sum of {num1} and {num2} is {num1 + num2}")

celsius = int(input("Enter a temperature in Celsius: "))
fahrenheit = celsius * 9/5 + 32
print(f"The temperature in Fahrenheit is {fahrenheit}")