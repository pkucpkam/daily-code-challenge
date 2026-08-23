# 553. Optimal Division

## Intuition

We are given an array of positive integers `nums = [X_1, X_2, X_3, ..., X_n]` and need to add parentheses to maximize the evaluated float division expression: $X_1 / X_2 / X_3 / \dots / X_n$.

At first glance, this looks like a Dynamic Programming / Backtracking problem where we might try all combinations of parentheses. However, a key mathematical property of division makes this problem solvable in **$\mathcal{O}(N)$ time** using a greedy approach.

---

## Key Insights & Mathematical Proof

### 1. Division Representation as a Fraction

Any valid parenthesized division of $X_1, X_2, \dots, X_n$ can always be reduced to a simple fraction:
$$\text{Expression} = \frac{\text{Numerator}}{\text{Denominator}}$$

### 2. Roles of $X_1$ and $X_2$

- **$X_1$ is ALWAYS in the Numerator:**  
  Since $X_1$ is the first number, no operation precedes it to divide it.
- **$X_2$ is ALWAYS in the Denominator:**  
  The expression starts with $X_1 / X_2 \dots$, meaning $X_1$ is directly divided by $X_2$ (or an expression starting with $X_2$). Therefore, $X_2$ can never be moved to the numerator.

### 3. Maximizing the Expression

To maximize $\frac{\text{Numerator}}{\text{Denominator}}$, we want:
1. **Numerator to be as LARGE as possible**
2. **Denominator to be as SMALL as possible**

Since all numbers in `nums` are positive integers ($\ge 2$):
- $X_2$ is fixed in the denominator, so the minimum possible denominator is $X_2$.
- All other elements $X_3, X_4, \dots, X_n$ can be moved into the numerator!

By placing parentheses around all elements from index $1$ to $n-1$:
$$X_1 / (X_2 / X_3 / X_4 / \dots / X_n)$$

Evaluating the inner expression first gives:
$$X_2 / X_3 / X_4 / \dots / X_n = \frac{X_2}{X_3 \times X_4 \times \dots \times X_n}$$

Substituting back into the main expression:
$$X_1 / \left( \frac{X_2}{X_3 \times X_4 \times \dots \times X_n} \right) = \frac{X_1 \times X_3 \times X_4 \times \dots \times X_n}{X_2}$$

This maximizes the numerator ($X_1 \times X_3 \times \dots \times X_n$) while keeping the denominator minimized ($X_2$).

---

## Algorithmic Rule Summary

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

## Walkthrough Example

### Example 1: `nums = [1000, 100, 10, 2]` ($N = 4$)

- `nums[0] = 1000`, `nums[1] = 100`, `nums[2] = 10`, `nums[3] = 2`
- Start string builder: `"1000/(100"`
- Loop $i = 2$: append `"/10"` $\rightarrow$ `"1000/(100/10"`
- Loop $i = 3$: append `"/2"` $\rightarrow$ `"1000/(100/10/2"`
- Append closing `")"` $\rightarrow$ `"1000/(100/10/2)"`

**Mathematical Evaluation:**
- `100/10/2` $= (100 / 10) / 2 = 10 / 2 = 5$
- `1000 / 5 = 200`
- Value $= 200$ (Maximum possible).

### Example 2: `nums = [2, 3, 4]` ($N = 3$)

- Start string builder: `"2/(3"`
- Loop $i = 2$: append `"/4"` $\rightarrow$ `"2/(3/4"`
- Append closing `")"` $\rightarrow$ `"2/(3/4)"`

**Mathematical Evaluation:**
- `3/4 = 0.75`
- `2 / 0.75 = 2.667`
- Value $= 2.667$ (Maximum possible).

---

## Alternative Approaches

### Dynamic Programming (Interval / Matrix Chain Multiplication DP)

While unnecessary due to the mathematical shortcut, an alternative approach is to use Range DP:
- Define `memo[i][j]` to store both the `max_val` and `min_val` of expression `nums[i...j]`.
- For `max_val(i, j)`: split at $k \in [i, j-1]$, and compute $\max( \text{max\_val}(i, k) / \text{min\_val}(k+1, j) )$.
- For `min_val(i, j)`: split at $k \in [i, j-1]$, and compute $\min( \text{min\_val}(i, k) / \text{max\_val}(k+1, j) )$.

- **Time Complexity:** $\mathcal{O}(N^3)$
- **Space Complexity:** $\mathcal{O}(N^2)$

*Conclusion:* DP is far less efficient than the $\mathcal{O}(N)$ Mathematical Greedy solution.

---

## Complexity Analysis

| Approach | Time Complexity | Space Complexity | Notes |
|:---|:---|:---|:---|
| **Mathematical Greedy (Optimal)** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | Optimal performance, single pass to build string |
| **Interval Dynamic Programming** | $\mathcal{O}(N^3)$ | $\mathcal{O}(N^2)$ | Redundant due to fixed denominator proof |

- **Time Complexity:** $\mathcal{O}(N)$ where $N$ is the number of elements in `nums`. We traverse `nums` once to construct the string.
- **Space Complexity:** $\mathcal{O}(N)$ auxiliary memory used by `StringBuilder` to hold the output string.
