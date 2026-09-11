# 583. Delete Operation for Two Strings

## Problem Understanding

Given two strings `word1` and `word2`, we want to find the **minimum number of deletions** required to make both strings equal. In each step, we can delete exactly one character from either string.

For example:
- `word1 = "sea"`, `word2 = "eat"`:
  - Delete `'s'` from `"sea"` $\to$ `"ea"` (1 deletion)
  - Delete `'t'` from `"eat"` $\to$ `"ea"` (1 deletion)
  - Both strings are now `"ea"` with a total of $2$ deletions.

---

## Key Insights & Mathematical Formulation

### 1. Reduction to Longest Common Subsequence (LCS)

When we delete characters from `word1` and `word2` to make them identical, the resulting string must be a **common subsequence** of both `word1` and `word2`.

To **minimize** the total number of deleted characters, the remaining common string must be as **long as possible**. Therefore, the remaining string is the **Longest Common Subsequence (LCS)** of `word1` and `word2`.

Let:
- $m = \text{length}(word1)$
- $n = \text{length}(word2)$
- $L = \text{LCS}(word1, word2)$

The number of characters we need to delete from each string is:
- From `word1`: $m - L$ deletions
- From `word2`: $n - L$ deletions

Thus, the total minimum deletions is:
$$\text{Total Deletions} = (m - L) + (n - L) = m + n - 2 \cdot L$$

### 2. Recurrence Relation for LCS

Let $dp[i][j]$ be the length of the LCS between the prefixes $word1[0 \dots i-1]$ and $word2[0 \dots j-1]$:

$$dp[i][j] = \begin{cases} 
0 & \text{if } i = 0 \text{ or } j = 0 \\
dp[i-1][j-1] + 1 & \text{if } word1[i-1] == word2[j-1] \\
\max(dp[i-1][j], dp[i][j-1]) & \text{if } word1[i-1] \ne word2[j-1]
\end{cases}$$

### 3. Alternative: Direct Deletions Recurrence

We can also formulate the DP directly without computing LCS first:
Let $dist[i][j]$ be the minimum deletions to make prefixes $word1[0 \dots i-1]$ and $word2[0 \dots j-1]$ equal:

$$dist[i][j] = \begin{cases}
j & \text{if } i = 0 \\
i & \text{if } j = 0 \\
dist[i-1][j-1] & \text{if } word1[i-1] == word2[j-1] \\
1 + \min(dist[i-1][j], dist[i][j-1]) & \text{if } word1[i-1] \ne word2[j-1]
\end{cases}$$

Both formulations yield the identical result. The LCS reduction is typically preferred because:
1. It connects directly to the well-known LCS problem.
2. The initial DP state is all zeros (which is the default in Java arrays), eliminating explicit base-case population loops.

---

## Detailed Approaches & Evolution

### Approach 1: Plain Recursion (Brute Force)

We can recursively explore all possibilities:
- If current characters match, move both pointers forward without adding any deletions.
- If they differ, branch into two possibilities: delete from `word1` or delete from `word2`, taking $1 + \min(\dots)$.

- **Time Complexity:** $\mathcal{O}(2^{\max(m, n)})$ — exponential due to overlapping subproblems.
- **Space Complexity:** $\mathcal{O}(\max(m, n))$ for recursion call stack.

---

### Approach 2: 2D Dynamic Programming

We store intermediate results in a matrix `int[][] dp = new int[m + 1][n + 1]`.
We fill the table row by row.

- **Time Complexity:** $\mathcal{O}(m \cdot n)$
- **Space Complexity:** $\mathcal{O}(m \cdot n)$

---

### Approach 3: Two-Row Space-Optimized DP

Notice that to compute row $i$, we only need values from row $i - 1$.
We can alternate between two 1D arrays: `prev` and `curr`.

- **Time Complexity:** $\mathcal{O}(m \cdot n)$
- **Space Complexity:** $\mathcal{O}(\min(m, n))$

---

### Approach 4: Optimal 1D Rolling Array DP (Best Solution)

We can reduce memory further to a single 1D array of size $n + 1$:
1. If $m < n$, swap `word1` and `word2` so that the inner dimension is always $\min(m, n)$.
2. Convert both strings to `char[]` to eliminate repeated method overhead and boundary checks from `charAt()`.
3. In the inner loop, use a single scalar variable `prev` to carry the top-left diagonal value $dp[i-1][j-1]$:
   - Before overwriting $dp[j]$, store it in a temporary variable `temp`.
   - Update $dp[j]$:
     - If $s_1[i-1] == s_2[j-1]$: $dp[j] = prev + 1$.
     - Else: $dp[j] = \max(dp[j], dp[j - 1])$.
   - Assign $prev = temp$.

- **Time Complexity:** $\mathcal{O}(m \cdot n)$
- **Space Complexity:** $\mathcal{O}(\min(m, n))$

---

## Step-by-Step Execution Trace (Example 1)

Input: `word1 = "sea"`, `word2 = "eat"`, $m = 3, n = 3$.

Initial DP array: `dp = [0, 0, 0, 0]`

### Row 1: $i = 1$ (`s1[0] = 's'`)
- $j = 1$ (`'e'`): `'s' != 'e'` $\to dp[1] = \max(dp[1], dp[0]) = \max(0, 0) = 0$
- $j = 2$ (`'a'`): `'s' != 'a'` $\to dp[2] = \max(dp[2], dp[1]) = \max(0, 0) = 0$
- $j = 3$ (`'t'`): `'s' != 't'` $\to dp[3] = \max(dp[3], dp[2]) = \max(0, 0) = 0$
- State after row 1: `[0, 0, 0, 0]`

### Row 2: $i = 2$ (`s1[1] = 'e'`)
- $j = 1$ (`'e'`): `'e' == 'e'` $\to dp[1] = prev + 1 = 0 + 1 = 1$
- $j = 2$ (`'a'`): `'e' != 'a'` $\to dp[2] = \max(dp[2], dp[1]) = \max(0, 1) = 1$
- $j = 3$ (`'t'`): `'e' != 't'` $\to dp[3] = \max(dp[3], dp[2]) = \max(0, 1) = 1$
- State after row 2: `[0, 1, 1, 1]`

### Row 3: $i = 3$ (`s1[2] = 'a'`)
- $j = 1$ (`'e'`): `'a' != 'e'` $\to dp[1] = \max(dp[1], dp[0]) = \max(1, 0) = 1$
- $j = 2$ (`'a'`): `'a' == 'a'` $\to dp[2] = prev + 1 = 1 + 1 = 2$
- $j = 3$ (`'t'`): `'a' != 't'` $\to dp[3] = \max(dp[3], dp[2]) = \max(1, 2) = 2$
- State after row 3: `[0, 1, 2, 2]`

### Final Calculation:
- $\text{LCS} = dp[3] = 2$ (the longest common subsequence is `"ea"`)
- $\text{Result} = m + n - 2 \cdot \text{LCS} = 3 + 3 - 2 \cdot 2 = \mathbf{2}$

---

## Code Implementation (Java)

```java
class Solution {
    public int minDistance(String word1, String word2) {
        // Fast path for identical strings
        if (word1.equals(word2)) {
            return 0;
        }

        // Ensure word2 is the shorter string to minimize space to O(min(m, n))
        if (word1.length() < word2.length()) {
            String temp = word1;
            word1 = word2;
            word2 = temp;
        }

        int m = word1.length();
        int n = word2.length();
        char[] s1 = word1.toCharArray();
        char[] s2 = word2.toCharArray();

        // dp[j] stores the length of the Longest Common Subsequence (LCS)
        // of word1[0...i-1] and word2[0...j-1]
        int[] dp = new int[n + 1];

        for (int i = 1; i <= m; i++) {
            int prev = 0; // Represents dp[i-1][j-1]
            for (int j = 1; j <= n; j++) {
                int temp = dp[j]; // Holds dp[i-1][j] which becomes dp[i-1][j-1] in the next iteration
                if (s1[i - 1] == s2[j - 1]) {
                    dp[j] = prev + 1;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
                prev = temp;
            }
        }

        int lcs = dp[n];
        return m + n - 2 * lcs;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Explanation |
| :--- | :---: | :--- |
| **Time Complexity** | $\mathcal{O}(m \cdot n)$ | Two nested loops iterating over $m$ and $n$. Each iteration performs constant-time $\mathcal{O}(1)$ operations. |
| **Space Complexity** | $\mathcal{O}(\min(m, n))$ | By swapping strings so that $n \le m$, the DP array only requires $n + 1$ integers ($\le 501 \times 4 \text{ bytes} \approx 2\text{ KB}$). |

---

## Comparison of Approaches

| Approach | Time Complexity | Auxiliary Space | Pros / Cons |
| :--- | :---: | :---: | :--- |
| **Brute Force Recursion** | $\mathcal{O}(2^{\max(m, n)})$ | $\mathcal{O}(\max(m, n))$ | Times out on medium/large inputs. |
| **Memoized Recursion** | $\mathcal{O}(m \cdot n)$ | $\mathcal{O}(m \cdot n)$ | Call stack and table overhead. |
| **2D DP Table** | $\mathcal{O}(m \cdot n)$ | $\mathcal{O}(m \cdot n)$ | Straightforward, but allocates $500 \times 500$ matrix. |
| **1D Rolling Array DP** | $\mathcal{O}(m \cdot n)$ | $\mathcal{O}(\min(m, n))$ | **Optimal.** Minimal memory footprint, high CPU cache locality. |

---

## Edge Cases Handled

1. **Identical Strings (`word1 = "abc", word2 = "abc"`):**
   - Handled immediately by the `if (word1.equals(word2))` fast path in $\mathcal{O}(m)$ time, returning `0`.
2. **Completely Disjoint Strings (`word1 = "abc", word2 = "def"`):**
   - $\text{LCS} = 0$, result is $m + n - 0 = m + n$ (all characters must be deleted).
3. **One String Is Subsequence of Another (`word1 = "ab", word2 = "a"`):**
   - $\text{LCS} = 1$, result is $2 + 1 - 2(1) = 1$.
4. **Single-Character Strings:**
   - Handled correctly by boundary loops.
5. **Strings of Differing Lengths:**
   - Swapping guarantees the smaller string is chosen for the 1D DP array, bounding auxiliary space strictly to $\min(m, n) + 1$.
