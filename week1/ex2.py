#Write a program that prints "Fizz" for multiples of 3, "Buzz" for 5, "FizzBuzz" for both
#Create a simple menu using match/case
#Print a multiplication table using nested loops
#Count down from 10 to 1 using a while loop

for i in range(1, 101):
    if i % 15 == 0:
        print("FizzBuzz")
    elif i % 3 == 0:
        print("Fizz")
    elif i % 5 == 0:
        print("Buzz")
    else:
        print(i)

choice = input("Enter a command (start/stop/exit): ").lower()
match choice:
    case "start":
        print("System starting...")
    case "stop":
        print("System stopping...")
    case "exit":
        print("Exiting program.")
    case _:
        print("Unknown command.")

for i in range(1, 11):
    for j in range(1, 11):
        print(f"{i * j:4}", end="")
    print()

count = 10
while count >= 1:
    print(count)
    count -= 1
