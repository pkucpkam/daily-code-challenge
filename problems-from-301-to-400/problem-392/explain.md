# 537. Complex Number Multiplication

## Intuition

A complex number is given in the form `a+bi`. When we multiply two complex numbers `(a+bi)` and `(c+di)`, we expand the expression algebraically:

$$(a+bi) \times (c+di) = ac + adi + bci + bdi^2$$

Since we know that $i^2 = -1$, the equation simplifies to:

$$(a+bi) \times (c+di) = (ac - bd) + (ad + bc)i$$

Thus, our goal is to extract the integer values $a, b, c,$ and $d$ from the given string inputs, perform this arithmetic, and format the output back into the required string representation.

---

## Key Insights & Algorithm

### String Parsing & Math Formula

1. **Extraction**:
   - The inputs are formatted exactly as `real+imaginaryi`.
   - We can efficiently split the strings using a regular expression that matches either the `+` character (escaped as `\\+`) or the `i` character.
   - For example, `num1.split("\\+|i")` on `"1+-1i"` returns an array `["1", "-1"]`.

2. **Computation**:
   - Parse the split strings into integers $a, b$ for the first number, and $c, d$ for the second number.
   - Calculate the new real part: `real = a * c - b * d`.
   - Calculate the new imaginary part: `img = a * d + b * c`.

3. **Formatting**:
   - Construct and return the result string as `real + "+" + img + "i"`.

---

## Complexity Analysis

- **Time Complexity**: $\mathcal{O}(1)$
  - Splitting the string, parsing the integers, and doing the basic arithmetic operations all take constant time, as the string lengths are bounded and extremely small (range is only `[-100, 100]`).

- **Space Complexity**: $\mathcal{O}(1)$
  - The arrays created by splitting the strings will contain exactly two elements each. The extra variables required take a minimal and strictly constant amount of memory.
