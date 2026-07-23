# 494. Target Sum

## Intuition

The problem asks us to assign `+` or `-` signs to elements in `nums` such that their total sum equals `target`.

Instead of generating all $2^n$ combinations of signs using brute-force recursion, we can transform this problem into a 0/1 Knapsack / Subset Sum problem using simple mathematical reduction.

Let $P$ be the subset of numbers assigned a positive sign `+`, and $N$ be the subset of numbers assigned a negative sign `-`.

1. $P - N = \text{target}$
2. $P + N = \text{total\_sum}$

Adding these two equations gives:
$$2P = \text{target} + \text{total\_sum} \implies P = \frac{\text{target} + \text{total\_sum}}{2}$$

Thus, the problem reduces to: **Count the number of subsets of `nums` that sum up to $\frac{\text{target} + \text{total\_sum}}{2}$**.

## Algorithm

1. **Calculate Total Sum**: Compute the total sum of all elements in `nums`.
2. **Validation**:
   - If $|\text{target}| > \text{total\_sum}$, or if $(\text{target} + \text{total\_sum})$ is odd (`% 2 != 0`), it is impossible to form the target sum, so return `0`.
3. **Dynamic Programming Setup**:
   - Define `subsetTarget = (target + total_sum) / 2`.
   - Create a 1D DP array `dp` of size `subsetTarget + 1`, where `dp[j]` represents the number of ways to form a subset with sum `j`.
   - Base case: `dp[0] = 1` (there is 1 way to form a sum of 0, which is the empty subset).
4. **DP Transitions**:
   - Iterate through each `num` in `nums`:
     - Iterate `j` backwards from `subsetTarget` down to `num`:
       - `dp[j] += dp[j - num]`
5. **Return Result**: Return `dp[subsetTarget]`.

## Complexity Analysis

### Time Complexity: $\mathcal{O}(n \cdot \text{subsetTarget})$
- Where $n$ is the length of `nums` and $\text{subsetTarget} = \frac{\text{target} + \text{total\_sum}}{2}$.
- Since $\text{total\_sum} \le 1000$ and $n \le 20$, the maximum number of operations is roughly $20 \times 1000 = 20,000$, which executes in under 1 millisecond.

### Space Complexity: $\mathcal{O}(\text{subsetTarget})$
- Uses a 1D DP array of size $\text{subsetTarget} + 1$, requiring $\mathcal{O}(\frac{\text{target} + \text{total\_sum}}{2})$ space (at most 1001 integers).
