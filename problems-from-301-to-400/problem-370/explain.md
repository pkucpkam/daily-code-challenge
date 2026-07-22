# 491. Non-decreasing Subsequences

## Intuition

We can find all possible non-decreasing subsequences by exploring all combinations of elements in the given array. Since the array can contain duplicates and we want unique subsequences, we can use a backtracking approach with a local hash set at each recursive level to track which numbers have already been used at that specific position in the subsequence.

## Algorithm

1. **Backtracking Function**: We define a recursive function `backtrack(start, current_subsequence)`.
2. **Base Case**: Every time `current_subsequence` has at least 2 elements, we add a copy of it to our result list. We do not stop recursion here because a valid subsequence can be extended further.
3. **Duplicate Prevention**: At each recursive step (i.e., for a specific position in the current subsequence being built), we initialize a `HashSet` called `used`.
4. **Iterating Options**: We iterate from `start` to the end of the array `nums`:
   - If `nums[i]` has already been used at this position (exists in `used`), we skip it to prevent generating duplicate subsequences.
   - We check if `nums[i]` can be appended to `current_subsequence`. It can be appended if `current_subsequence` is empty, or if `nums[i]` is greater than or equal to the last element of `current_subsequence`.
   - If valid, we add `nums[i]` to `used` and to `current_subsequence`.
   - We make a recursive call `backtrack(i + 1, current_subsequence)` to build further upon the newly added element.
   - We backtrack by removing the last element from `current_subsequence`.
5. **Result**: After the initial call `backtrack(0, [])` finishes, we return the accumulated result list.

## Complexity Analysis

### Time Complexity: $\mathcal{O}(2^n \cdot n)$
- In the worst case, every element can be either included or excluded, leading to $2^n$ possible subsequences. Building and copying each valid subsequence into the result takes $\mathcal{O}(n)$ time. Thus, the time complexity is bounded by $\mathcal{O}(2^n \cdot n)$.

### Space Complexity: $\mathcal{O}(2^n \cdot n)$
- The space complexity is primarily dominated by the space required to store all the valid subsequences in the result list. In the worst case, there could be up to $2^n$ subsequences, each of length up to $n$.
- Additionally, the recursion stack takes up to $\mathcal{O}(n)$ space, and the `HashSet` at each level takes $\mathcal{O}(n)$ space across the stack, summing up to $\mathcal{O}(n)$. Overall, space is $\mathcal{O}(2^n \cdot n)$.
