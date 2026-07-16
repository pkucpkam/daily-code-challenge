# Ones and Zeroes

## Intuition

The problem asks us to find the size of the largest subset of binary strings that contains at most `m` 0's and `n` 1's.
This naturally maps to the classic **0/1 Knapsack Problem**. Instead of one weight limit (capacity), we have two independent weight limits: the maximum allowed number of `0`s and the maximum allowed number of `1`s. The "value" of each item (string) is 1, as we want to maximize the total number of items in our subset.

## Dynamic Programming Approach

We can solve this using 2D Dynamic Programming (DP). 
Let `dp[i][j]` represent the maximum number of strings we can include using at most `i` 0's and `j` 1's.

For each string in the array, we first count its number of `0`s and `1`s.
Then, we try to add this string to our existing subsets. If we decide to include this string, it will cost us `zeros` number of 0s and `ones` number of 1s. The new maximum subset size would be `1 + dp[i - zeros][j - ones]`. 

We compare this to our current best `dp[i][j]` (which represents not including the string) and take the maximum.

### Iterating Backwards
To optimize space complexity and only use a 2D array (rather than a 3D array `dp[k][i][j]`), we must iterate `i` and `j` backwards. Iterating forwards would mean a single string could be added multiple times in the same step, which violates the 0/1 Knapsack constraint (each item can be used at most once).

## Algorithm

1. Initialize a 2D array `dp` of size `(m + 1) \times (n + 1)` with all 0s.
2. Iterate through each binary string `str` in `strs`:
   - Count the number of `'0'`s (`zeros`) and `'1'`s (`ones`) in `str`.
   - Iterate `i` backwards from `m` down to `zeros`.
   - Iterate `j` backwards from `n` down to `ones`.
   - Update `dp[i][j] = max(dp[i][j], 1 + dp[i - zeros][j - ones])`.
3. Return `dp[m][n]`.

## Complexity Analysis

### Time Complexity: $\mathcal{O}(L + S \cdot m \cdot n)$
- $L$ is the total number of characters in all strings of `strs`. We spend $\mathcal{O}(L)$ time counting the zeros and ones.
- $S$ is the number of strings in `strs`. For each string, we update the DP table which takes at most $\mathcal{O}(m \cdot n)$ time.
- Thus, the total time complexity is bounded by $\mathcal{O}(L + S \cdot m \cdot n)$.

### Space Complexity: $\mathcal{O}(m \cdot n)$
- We use a 2D DP array of size `(m + 1) \times (n + 1)`.
- Therefore, the space complexity is $\mathcal{O}(m \cdot n)$. We optimized away the need for an $\mathcal{O}(S \cdot m \cdot n)$ 3D array by iterating backwards.
