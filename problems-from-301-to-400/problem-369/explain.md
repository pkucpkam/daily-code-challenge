# 486. Predict the Winner

## Intuition

The game can be modeled using a minimax strategy or dynamic programming, as both players play optimally. A player's goal is to maximize their score minus the opponent's score. 

If it's a player's turn and the remaining array is `nums[i..j]`, they can choose either `nums[i]` or `nums[j]`. 
- If they choose `nums[i]`, they gain `nums[i]` points, and the remaining array becomes `nums[i+1..j]`. The opponent will then play optimally to maximize their own net score on `nums[i+1..j]`. So, the current player's net score from this move will be `nums[i] - dp[i+1][j]`.
- If they choose `nums[j]`, similarly, their net score will be `nums[j] - dp[i][j-1]`.

The player will choose the option that maximizes their net score: `dp[i][j] = max(nums[i] - dp[i+1][j], nums[j] - dp[i][j-1])`.
Player 1 wins if their net score for the entire array `nums[0..n-1]` is greater than or equal to `0`.

## Algorithm

1. **Base Case**: For subarrays of length 1 (`i == j`), the current player must take the only available number, so `dp[i][i] = nums[i]`.
2. **DP State**: We can use a 1D array `dp` of size `n` to optimize space. We will iterate over the length of the subarray `len` from 2 to `n`.
3. **Transitions**: For each length, we iterate through all possible starting indices `i`. The ending index `j` will be `i + len - 1`.
   - Update `dp[i]` as `max(nums[i] - dp[i + 1], nums[j] - dp[i])`. 
   - Here, `dp[i + 1]` holds the optimal difference for the subarray `nums[i + 1..j]` from the previous iteration, and `dp[i]` holds the optimal difference for `nums[i..j - 1]`.
4. **Result**: After completing the loops, `dp[0]` will store the maximum score difference for the entire array `nums[0..n-1]`. If `dp[0] >= 0`, Player 1 can win, so we return `true`; otherwise, we return `false`.

## Complexity Analysis

### Time Complexity: $\mathcal{O}(n^2)$
- There are two nested loops: the outer loop runs `n - 1` times (for lengths from 2 to `n`), and the inner loop runs $n - \text{len} + 1$ times. This gives an arithmetic progression summing to about $n^2 / 2$ iterations, resulting in $\mathcal{O}(n^2)$ time complexity.

### Space Complexity: $\mathcal{O}(n)$
- We use a 1D array `dp` of size `n` to store the maximum score differences for the current length being processed. Thus, the space complexity is optimized to $\mathcal{O}(n)$.
