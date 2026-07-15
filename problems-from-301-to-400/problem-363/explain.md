# Matchsticks to Square

## Intuition

The problem asks us to determine if we can form a square using all the given matchsticks exactly once. A square has 4 equal sides. This means the sum of the lengths of all matchsticks must be perfectly divisible by 4. If it's not, we can immediately return `false`. If it is, the target length of each side is `sum / 4`.

This problem can be modeled as a backtracking/depth-first search (DFS) problem. We can imagine 4 empty buckets (representing the 4 sides of the square), each with a capacity equal to `target`. We iterate through the matchsticks and try to place each one into one of the 4 buckets without overflowing the bucket's capacity.

Since the number of matchsticks is small (at most 15), a brute-force DFS is feasible. However, to ensure it runs efficiently and avoids Time Limit Exceeded (TLE) errors, we must apply some powerful pruning techniques.

## Optimizations

1. **Sort Descending:** 
   If we sort the matchsticks in descending order, we try to place the longest matchsticks first. Long matchsticks have fewer valid placement options and are more likely to fail early. Failing early prevents the algorithm from wasting time exploring deep into dead-end branches. Furthermore, if the longest matchstick itself is strictly greater than the target side length, it's impossible to form the square, allowing us to return `false` instantly.

2. **Avoid Duplicate Placements:** 
   The 4 sides of the square are identical. When deciding which bucket to place the current matchstick into, if multiple buckets currently have the exact same sum, placing the matchstick into any of them will lead to symmetric and identical future states. Therefore, we should only attempt to place the matchstick in the first of these identical buckets and skip the rest. This drastically cuts down the search space.

## Algorithm

1. **Initial Checks:** 
   Calculate the sum of all matchsticks. If `sum % 4 != 0`, return `false`. Let `target = sum / 4`.
2. **Sort and Reverse:** 
   Sort the matchsticks array in ascending order, then reverse it to get descending order.
3. **Capacity Check:** 
   If `matchsticks[0] > target`, return `false`.
4. **Backtracking (DFS):**
   Initialize an array `sides = [0, 0, 0, 0]` to track the current length of each side. 
   Call a recursive function `dfs(index)` starting at `index = 0`.
   - **Base Case:** If `index == matchsticks.length`, it means we have successfully placed all matchsticks. Since we ensure no side exceeds the `target`, they all must equal `target`. Return `true`.
   - **Recursive Step:** For the current matchstick `matchsticks[index]`, try placing it into each of the 4 `sides`.
     - Check if `sides[i] + matchsticks[index] <= target`. If not, continue to the next side.
     - **Pruning:** Iterate $j$ from $0$ to $i-1$. If `sides[j] == sides[i]`, skip the current side `i` because it's symmetrically identical to a previous branch.
     - Add `matchsticks[index]` to `sides[i]`.
     - Recursively call `dfs(index + 1)`. If it returns `true`, we found a valid arrangement, so return `true`.
     - Backtrack by subtracting `matchsticks[index]` from `sides[i]`.
   - If placing the current matchstick into all 4 sides fails, return `false`.

## Complexity Analysis

### Time Complexity: $\mathcal{O}(4^N)$
- In the worst case, for each of the $N$ matchsticks, we have 4 choices (one for each side). However, the pruning techniques (especially sorting descending and skipping identical side sums) reduce the actual number of operations significantly, allowing it to easily pass the time limit for $N \le 15$.
- Sorting takes $\mathcal{O}(N \log N)$.
- The overall worst-case time complexity is bounded by $\mathcal{O}(4^N)$, but practically much faster.

### Space Complexity: $\mathcal{O}(N)$
- Sorting takes $\mathcal{O}(N)$ space in some implementations, or $\mathcal{O}(1)$ for primitives depending on the language.
- The recursion stack can go as deep as the number of matchsticks, which takes $\mathcal{O}(N)$ auxiliary space.
- The `sides` array requires $\mathcal{O}(1)$ space.
- The overall space complexity is $\mathcal{O}(N)$.
