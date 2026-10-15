# 640. Solve the Equation

**Difficulty:** Medium 🟡  
**Topics:** Math, String, Simulation  

---

## Problem Statement

Solve a given equation and return the value of `'x'` in the form of a string `"x=#value"`. The equation contains only `'+'`, `'-'` operations, the variable `'x'`, and its coefficient. You should return `"No solution"` if there is no solution for the equation, or `"Infinite solutions"` if there are infinite solutions for the equation.

If there is exactly one solution for the equation, we ensure that the value of `'x'` is an integer.

---

## Examples

### Example 1

```text
Input: equation = "x+5-3+x=6+x-2"
Output: "x=2"
```

**Explanation:**
- Left side: `x + 5 - 3 + x` $\implies$ `2x + 2`
- Right side: `6 + x - 2` $\implies$ `x + 4`
- Equation: `2x + 2 = x + 4` $\implies$ `2x - x = 4 - 2` $\implies$ `x = 2`.

---

### Example 2

```text
Input: equation = "x=x"
Output: "Infinite solutions"
```

**Explanation:** Any value of `x` satisfies the equation because both sides are identical (`0x = 0`).

---

### Example 3

```text
Input: equation = "2x=x"
Output: "x=0"
```

**Explanation:** Subtracting `x` from both sides gives `x = 0`.

---

## Constraints

- `3 <= equation.length <= 1000`
- `equation` has exactly one `'='`.
- `equation` consists of integers with an absolute value in the range `[0, 100]` without any leading zeros, and the variable `'x'`.
- The input is generated that if there is a single solution, it will be an integer.