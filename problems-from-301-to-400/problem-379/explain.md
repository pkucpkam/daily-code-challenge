# 518. Coin Change II

## Intuition

The problem asks us to find the total number of unique combinations of coins that sum up to a target `amount`. We are given an array `coins` with infinite supply for each coin denomination.

This is a classic variation of the **Unbounded Knapsack** problem. Since order does not matter (combinations, not permutations), we need to ensure that different orderings of the same set of coins (e.g., `[1, 2]` and `[2, 1]`) are counted as a single combination.

---

## Key Insights & Algorithm

### Approach 1: 2D Dynamic Programming — $\mathcal{O}(N \times A)$ Time, $\mathcal{O}(N \times A)$ Space

Let `dp[i][j]` represent the number of ways to make amount `j` using only the first `i` types of coins.

1. **Base Cases**:
   - `dp[i][0] = 1` for all `0 <= i <= N` (1 way to make amount 0: pick zero coins).
   - `dp[0][j] = 0` for `j > 0` (0 ways to make a positive amount using 0 coins).
2. **State Transition**:
   - If we don't pick coin `i`: we have `dp[i-1][j]` ways.
   - If we pick coin `i` (when `j >= coins[i-1]`): we have `dp[i][j - coins[i-1]]` ways.
   $$\text{dp}[i][j] = \text{dp}[i - 1][j] + (\text{if } j \ge \text{coins}[i-1] \text{ then } \text{dp}[i][j - \text{coins}[i-1]] \text{ else } 0)$$

---

### Approach 2: Space-Optimized 1D Dynamic Programming — $\mathcal{O}(N \times A)$ Time, $\mathcal{O}(A)$ Space (Optimal)

Notice that `dp[i][j]` only depends on:
1. `dp[i - 1][j]` (the value at the same amount from the previous coin state)
2. `dp[i][j - coin]` (the updated value at a smaller amount in the current coin state)

We can collapse the 2D matrix into a 1D array `dp` of size `amount + 1`:
- `dp[i]` stores the number of combinations to form amount `i`.
- **Crucial Order of Loops**: 
  - Outer loop iterates over each `coin` in `coins`.
  - Inner loop iterates `i` forward from `coin` to `amount`.

Processing coin by coin guarantees that coins are considered in a fixed order, counting **combinations** rather than **permutations**.

---

## Detailed Step-by-Step (Optimal 1D DP)

1. Create a 1D DP array `dp` of size `amount + 1`.
2. Set `dp[0] = 1` as the base case (1 combination to form amount 0).
3. For each `coin` in `coins`:
   - Loop `i` from `coin` up to `amount`:
     - Update `dp[i] += dp[i - coin]`.
4. Return `dp[amount]`.

---

## Complexity Analysis

### 2D Dynamic Programming
- **Time Complexity:** $\mathcal{O}(N \times A)$ — Where $N$ is the number of coins and $A$ is `amount`.
- **Space Complexity:** $\mathcal{O}(N \times A)$ — Table of size $(N + 1) \times (A + 1)$.

### Space-Optimized 1D Dynamic Programming (Optimal)
- **Time Complexity:** $\mathcal{O}(N \times A)$ — Two nested loops processing $N$ coins across $A$ amounts.
- **Space Complexity:** $\mathcal{O}(A)$ — Single array of size `amount + 1`.
