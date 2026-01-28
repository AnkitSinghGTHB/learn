# Write a function that calculates the factorial of a number
# Create a function that checks if a number is prime
# Build a temperature converter function (Celsius ↔ Fahrenheit)
# Write a function that reverses a string

def factorial(n):
    f=1
    if n==0:
        return 1
    else:
        for i in range(1,n+1):
            f=f*i
        return f

print(factorial(5)) #120

def prime(n):
    if n==1:
        return False
    
    for i in range(2,n//2+1): # n//2 is the largest possible factor of n, we go from 2 to n//2 to check if n is prime
        if n%i==0: # if n is divisible by any number in the range, it is not prime
            return False
    return True
print(prime(7)) # checking 2 to 7//2 = 3
print(prime(1304)) # checking 2 to 1304//2 = 652
print(prime(13)) # checking 2 to 13//2 = 6

def temp_c_to_f(c):
    return (c*9/5)+32
def temp_f_to_c(f):
    return (f-32)*5/9

print(temp_c_to_f(0)) #32
print(temp_f_to_c(144)) #62.2

def str_rev(s):
    #in python we can do in many ways, like using slicing, using loop, using join, using list
    #return s[::-1] #slicing
    t=""
    for i in s:
        t=i+t
    return t #loop
print(str_rev("ankit is a good boy"))
