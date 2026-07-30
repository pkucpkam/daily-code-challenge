# 516. Longest Palindromic Subsequence

## Intuition

The problem asks for the length of the longest palindromic subsequence in a given string `s`. 

A palindrome reads the same forwards and backwards. To determine if a substring `s[i..j]` can form a palindrome, we inspect its boundaries:
- If `s[i] == s[j]`, the two characters form the outer boundary of a palindrome. The overall length is $2$ plus the longest palindromic subsequence of the inner substring `s[i+1..j-1]`.
- If `s[i] != s[j]`, the characters cannot both be the endpoints of the same palindromic subsequence. Thus, the answer is the maximum of:
  1. The longest palindromic subsequence of `s[i+1..j]` (excluding `s[i]`).
  2. The longest palindromic subsequence of `s[i..j-1]` (excluding `s[j]`).

This subproblem structure displays **overlapping subproblems** and **optimal substructure**, making Dynamic Programming (DP) the ideal approach.

---

## Key Insights & Algorithm

### Approach 1: 2D Dynamic Programming — $\mathcal{O}(N^2)$ Time, $\mathcal{O}(N^2)$ Space

Let `dp[i][j]` represent the length of the longest palindromic subsequence in `s[i..j]`.

1. **Base Cases**:
   - Single character: `dp[i][i] = 1` for all `0 <= i < N`.
2. **State Transitions**:
   - For `j > i`:
     $$\text{dp}[i][j] = \begin{cases} \text{dp}[i + 1][j - 1] + 2 & \text{if } s[i] == s[j] \\ \max(\text{dp}[i + 1][j], \text{dp}[i][j - 1]) & \text{if } s[i] \neq s[j] \end{cases}$$
3. **Loop Order**:
   - Outer loop `i` iterates backwards from `N - 1` to `0`.
   - Inner loop `j` iterates forward from `i + 1` to `N - 1`.
4. **Final Answer**: `dp[0][N - 1]`.

---

### Approach 2: Space-Optimized 1D Dynamic Programming — $\mathcal{O}(N^2)$ Time, $\mathcal{O}(N)$ Space (Optimal)

Notice that computing `dp[i][j]` only requires values from:
- The current row `i`: `dp[i][j-1]`
- The bottom row `i+1`: `dp[i+1][j]` and `dp[i+1][j-1]`

We can compress the 2D matrix into a single 1D array `dp` of size $N$, where `dp[j]` stores `dp[i..j]`.

- As we iterate `j` from `i + 1` to `N - 1`, we use a variable `prev` to hold the value of `dp[i + 1][j - 1]` before `dp[j]` gets overwritten.
- `dp[j]` (before update) represents `dp[i + 1][j]`.
- `dp[j - 1]` (already updated) represents `dp[i][j - 1]`.

---

## Detailed Step-by-Step (Optimal 1D DP)

1. Check if `s` is empty; if so, return `0`.
2. Initialize array `dp` of size $N$.
3. Loop `i` from $N - 1$ down to `0`:
   - Set `dp[i] = 1` (base case: a single character `s[i]` has palindrome length 1).
   - Initialize `prev = 0` (represents `dp[i + 1][j - 1]`).
   - Loop `j` from `i + 1` to $N - 1$:
     - Save current `dp[j]` in temporary variable `temp` (this is `dp[i + 1][j]`).
     - If `s.charAt(i) == s.charAt(j)`:
       - Set `dp[j] = prev + 2`.
     - Else:
       - Set `dp[j] = Math.max(dp[j], dp[j - 1])`.
     - Set `prev = temp`.
4. Return `dp[N - 1]`.

---

## Complexity Analysis

### 2D Dynamic Programming
- **Time Complexity:** $\mathcal{O}(N^2)$ — Filling an $N \times N$ DP table.
- **Space Complexity:** $\mathcal{O}(N^2)$ — Storing the 2D DP matrix.

### Space-Optimized 1D Dynamic Programming (Optimal)
- **Time Complexity:** $\mathcal{O}(N^2)$ — Dual nested loops covering $\frac{N(N-1)}{2}$ subproblems.
- **Space Complexity:** $\mathcal{O}(N)$ — Only requires a single array of size $N$.
