# Polynomial Code – Root-Based Polynomial Solver

This code reads a set of polynomial roots provided in a **JSON file**, decodes each root from its given **base & value**, and constructs the polynomial of degree **k – 1**, where `k` is specified in the input.

It supports **very large integers**, arbitrary number bases, and expands the full polynomial using **BigInteger** arithmetic in Java.

---

## Features

###  Reads JSON input

The input file contains `n` roots. Each root is given as:

```
"2": {
    "base": "2",
    "value": "111"
}
```

### Decodes each root

Converts `value` from the given `base` to a Java `BigInteger`.

### Constructs polynomial of degree `m = k - 1`

If `k = 7`, degree = 6.

### Computes

* **Constant term C**
* **Full polynomial coefficients a0 … am**
* **Full polynomial expression P(x)**

### Handles very large numbers

Example input value:

```
2122212201122002221120200210011020220200 (base 3)
```
---

## Input Format

Example JSON:

```
{
    "keys": {
        "n": 4,
        "k": 3
    },
    "1": {
        "base": "10",
        "value": "4"
    },
    "2": {
        "base": "2",
        "value": "111"
    }
}
```

* **n** = number of roots given
* **k** = minimum number of roots required
* Polynomial degree = `k - 1`
* Program uses the **first (k − 1) smallest roots**

---

## How to Compile

```
javac hivelcode.java
```

---

## ▶️ How to Run

### **1️⃣ Compile the program**

```
javac hivelcode.java
```

### **2️⃣ Run with a JSON testcase**

**PowerShell / VS Code Terminal:**

```
Get-Content testcase1.json | java hivelcode
```

Or for testcase 2:

```
Get-Content testcase2.json | java hivelcode
```

**Command Prompt (CMD):**

```
java hivelcode < testcase1.json
```

```
java hivelcode < testcase2.json
```

---


## 📤 Outputs

### 1️⃣ Constant term C

```
Constant term C:
28
```

### 2️⃣ Polynomial coefficients

```
a0 = 28
a1 = -11
a2 = 1
```

### 3️⃣ Full polynomial

```
P(x) = (1)*x^2 + (-11)*x^1 + (28)*x^0
```

---

## Logic Used

1. Convert all roots to decimal (BigInteger).
2. Sort the roots.
3. Use first `m = k − 1` roots.
4. Compute constant term:

```
C = (-r1)(-r2)…(-rm)
```

5. Expand polynomial iteratively:

```
P(x) = 1
for each root r:
    P(x) = P(x) * (x - r)
```


## File Structure

```
Hivel_PolynomialCode/
  ├── hivelcode.java
  ├── testcase1.json
  ├── testcase2.json
  ├── README.md
```

---

## Tools Used

* Java 
* JSON parsing
* VS Code 

---
