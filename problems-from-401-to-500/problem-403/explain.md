# 553. Optimal Division

## Intuition

We are given an array of positive integers `nums = [X_1, X_2, X_3, ..., X_n]` ($nums[i] \ge 2$) and need to add parentheses to maximize the evaluated float division expression: $X_1 / X_2 / X_3 / \dots / X_n$.

At first glance, this problem appears to require Dynamic Programming or Backtracking to test all possible parenthesization strategies. However, due to a key mathematical property of sequential division, this problem can be solved greedily in **$\mathcal{O}(N)$ time**.

---

## Key Insights & Mathematical Proof

### 1. Division Representation as a Fraction

Any valid parenthesized division expression involving $X_1, X_2, \dots, X_n$ can be simplified into a single fraction:
$$\text{Expression} = \frac{\text{Numerator}}{\text{Denominator}}$$

### 2. Roles of $X_1$ and $X_2$

- **$X_1$ is ALWAYS in the Numerator:**  
  $X_1$ is the dividend of the first operation. No preceding operation can divide $X_1$, so it remains in the numerator for all possible parenthesizations.
- **$X_2$ is ALWAYS in the Denominator:**  
  The expression begins with $X_1 / X_2 \dots$. Regardless of parenthesization, $X_2$ will divide $X_1$ (or divide an expression containing $X_1$). Therefore, $X_2$ cannot be moved to the numerator.

### 3. Maximizing the Fraction Value

To maximize $\frac{\text{Numerator}}{\text{Denominator}}$, we must:
1. **Maximize the Numerator**
2. **Minimize the Denominator**

Given that all elements $X_i \ge 2$:
- $X_2$ is locked in the denominator, so the absolute smallest denominator we can achieve is $X_2$.
- Every remaining element $X_3, X_4, \dots, X_n$ can be shifted into the numerator!

By enclosing all elements from index $1$ to $n-1$ in parentheses:
$$X_1 / (X_2 / X_3 / X_4 / \dots / X_n)$$

Evaluating the inner denominator term yields:
$$X_2 / X_3 / X_4 / \dots / X_n = \frac{X_2}{X_3 \times X_4 \times \dots \times X_n}$$

Substituting this back into the overall expression:
$$X_1 / \left( \frac{X_2}{X_3 \times X_4 \times \dots \times X_n} \right) = \frac{X_1 \times X_3 \times X_4 \times \dots \times X_n}{X_2}$$

This places every element except $X_2$ in the numerator, achieving the theoretical maximum value.

---

## Algorithmic Strategy

- **If $N = 1$:** Return `"nums[0]"`.
- **If $N = 2$:** Return `"nums[0]/nums[1]"`.
- **If $N > 2$:** Return `"nums[0]/(nums[1]/nums[2]/.../nums[N-1])"`.

---

## Code Implementation (Java)

```java
class Solution {
    public String optimalDivision(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return String.valueOf(nums[0]);
        }
        if (n == 2) {
            return nums[0] + "/" + nums[1];
        }

        StringBuilder sb = new StringBuilder();
        sb.append(nums[0]).append("/(").append(nums[1]);
        for (int i = 2; i < n; i++) {
            sb.append("/").append(nums[i]);
        }
        sb.append(")");

        return sb.toString();
    }
}
```

---

## Step-by-Step Walkthrough

### Example 1: `nums = [1000, 100, 10, 2]` ($N = 4$)

1. $N > 2$, so initialize `StringBuilder` with `"1000/(100"`.
2. Append `"/10"` ($i = 2$) $\rightarrow$ `"1000/(100/10"`.
3. Append `"/2"` ($i = 3$) $\rightarrow$ `"1000/(100/10/2"`.
4. Append closing `")"` $\rightarrow$ `"1000/(100/10/2)"`.

**Mathematical Verification:**
- Denominator term: `100 / 10 / 2` $= (100 / 10) / 2 = 10 / 2 = 5$.
- Overall value: `1000 / 5 = 200`.
- Algebraic expansion: $\frac{1000 \times 10 \times 2}{100} = \frac{20000}{100} = 200$.

### Example 2: `nums = [2, 3, 4]` ($N = 3$)

1. Initialize `StringBuilder` with `"2/(3"`.
2. Append `"/4"` ($i = 2$) $\rightarrow$ `"2/(3/4"`.
3. Append closing `")"` $\rightarrow$ `"2/(3/4)"`.

**Mathematical Verification:**
- Denominator term: `3 / 4 = 0.75`.
- Overall value: `2 / 0.75 = 2.667`.

---

## Complexity Analysis

| Strategy | Time Complexity | Space Complexity | Recommendation |
|:---|:---|:---|:---|
| **Mathematical Greedy** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | **Optimal** — single pass string construction |
| **Interval Dynamic Programming** | $\mathcal{O}(N^3)$ | $\mathcal{O}(N^2)$ | Overkill / Unnecessary |

- **Time Complexity:** $\mathcal{O}(N)$, where $N$ is `nums.length`. A single loop populates the output string.
- **Space Complexity:** $\mathcal{O}(N)$ auxiliary space for the `StringBuilder` output buffer.
