# Java Code Style Guidelines
## 1. Naming Conventions

### 1.1 CamelCase

Java class, field, method, and variable names are written in CamelCase: Words are smashed together and the first letter of each word is capitalized. No word separator, like the underscore _, is used.

There is one exception to CamelCase: Constants are in all-caps with words separated by underscores. Constants should also have the keyword final, and they are usually static:

```java
public static final double SPEED_OF_LIGHT= 299792458; // in m/s
```

static means the variable belongs to the class no to the instance, we can access it without create an object
final means we can not reassign it, its an immutable constant (reference object)

### 1.2 Class and interface names

Class and interface names are generally noun or noun phrases and begin with a capital letter:

```java 
            ✅                     
class User {}
class Account {}
class BankAccount {}
interface PaymentService {}
interface AqueousHabitat { ... }
class FishBowl implements AqueousHabitat { ... }
```

they must not be verbs or adjectives

```java 
            ❌
class Run {}        // verb
class Fast {}       // adjective
class DoPayment {}  // verb
```

### 1.3 Method Names

Method names generally begin with a lowercase letter. A call on a procedure is a statement to do something, so a procedure name is generally a verb phrase that is a command to do something.

```java
public void setTitle(String t) { ... }
```

If a function call yields a value, so a function name is generally a noun phrase that describes the value.

```java
public double areaOfTriangle(int b, int c, int d) { ... }
```

A function that yields the value of a field (say title) is the name of the field preceded by "get".

```java
public String getTitle() { ... }
```

The name of a boolean function is often a verb phrase starting with "is", thus describing what the value means:

```java
public boolean isEquilateralTriangle(int b, int c, int d) { ... }
```

### 1.4 Variable names

Variable names start with a lowercase letter. They should give the reader some hint about what the variable is used for, helps document a program, making it easier to understand.

```java
hipotenusaDeTriángulo = 6 * (2 + hipotenusaDeTriángulo) + 7 / hipotenusaDeTriángulo - hipotenusaDeTriángulo;

x = 6 * (2 + x) + 7 / x - x; 
```

We tend to use shorter names for parameters and local variables and longer names for fields and static variables. 

- Parameter names

 The specification/signature of a method will name all parameters and give their meanings. The body of a method is usually fairly short at most 30-50 lines. Therefore, when reading the method body, the meanings of parameters are available without any scrolling. So parameter names may be short even one letter long.

```java
/** Returns whether the lengths: b, c, and d are the sides of an equilateral triangle"**/

public boolean isEquilateralTriangle(int b, int c, int d) {
return b == c && c == d;
}
```

- Local variable names 

A local variable is a variable that is declared in a method body. Its declaration should be placed as close to its first use as possible. The scope of a local variable is usually short, and its meaning is often obvious.

```java
public double calculateCircleArea(double radius) {
    double pi = 3.14159; // local variable
    double area = pi * radius * radius;
    return area;
}
```

- Fields and class variables

The declarations of field and class variables may be far from their uses perhaps hundreds of lines away. Therefore, the names of field and class variables should be longer and as mnemonic as possible, giving the reader a good idea what the meaning are.

```java
public class BankAccount {

    private double currentBalance;

    private static final double MINIMUM_BALANCE = 100.0;

    public void deposit(double amount) {
        currentBalance += amount;
    }

    public boolean withdraw(double amount) {
        if (currentBalance - amount >= MINIMUM_BALANCE) {
            currentBalance -= amount;
            return true;
        }
        return false;
    }
}
```

### 1.5 Package names

Package names are usually all lowercase and consist of nouns.

package com.shop.orderservice;  ✅                     
package com.Shop.OrderService;  ❌

## 2. Format conventions

Use a formatting convention consistently within a class. the position of open braces "{" should be the same throughout the program.
Put only one statement on a line. Don't pack everything together, making a program hard to read.
Make sure that all lines can be read without the need for horizontal scrolling. A maximum of 80 characters per line is recommended.

### 2.1. Indentation and braces { }

Indentation is used to make the structure of a program clear. The basic rule is:
Substatements of a statement or declaration should be indented.

For example, the body of a method, and the if-part and then-part of a conditional statement should be indented. 
We prefer indentation of 4 spaces. 

We prefer putting an opening curly brace "{" at the end of a line, and not on a line by itself, as shown below. The closing brace "}" appears on its own line, indented as shown below.

```java
     ✅                     ❌
if (x < y) {           if (x < y) 
    x = y;              {
    y = 0;                  x = y;
}                           y = 0; 
else {                 }
    x = 0;              else  
    y = y/2;            {  
}                           x = 0;
                            y = y/2;
                        }  
```

### 2.2 Always use braces for control flow structures

The following is just begging for a bug:

```java
if (flag) validate(); update(); ❌
```

Always use braces:

```java
if (flag) {                     ✅
    validate();
    update();
}
```
(This applies to other structures as well, like if-else statements for-loops, and while-loops)

Put a space between the keyword and the following parenthesis, to differentiate control structures from method calls:

```java
if (username == null) { ... } ✅
if(username == null) { ... }  ❌
 ```

### 3. Access Modifiers

Access modifiers allow us to hide the internal details of a class and expose only what is necessary, thus protecting the code from unwanted modifications.
They control the visibility of classes, interfaces, methods, and variables. The four types are:

- public: accessible from anywhere
- protected: accessible within the same class, package, and by subclasses of any other package.
- default: accessible only within the same package
- private: accessible only within the class itself.

### 4. Documentation

Omit unnecessary words. Use the active voice.

## Referencias:
- [Java Code Style Guidelines](https://www.cs.cornell.edu/courses/JavaAndDS/JavaStyle.html)
- [Java Best Practices](https://blog.jetbrains.com/idea/2024/02/java-best-practices/)
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)