# Prefix Sum & HashMap Approach: O(N) Time

## Intuition

The problem asks us to find the number of downward paths in a binary tree that sum to a given `targetSum`.
A naive approach would be to start a DFS from every single node, resulting in an **O(N²)** time complexity.

We can optimize this to **O(N)** by using the **Prefix Sum** technique combined with a Hash Map, similar to the approach used in the "Subarray Sum Equals K" problem. 
A prefix sum is the sum of node values from the root to the current node. If we are at a current node and the prefix sum is `currentSum`, and we want to find a path ending at the current node that sums to `targetSum`, we need to check if there is an ancestor node whose prefix sum is `currentSum - targetSum`.

We use a Hash Map to store the frequencies of all prefix sums we have seen so far on the path from the root to the current node.

## Algorithm

1. **Initialize Hash Map:** 
   - Create a `HashMap<Long, Integer>` to store `prefixSum -> count`. We use `Long` for the prefix sum to avoid integer overflow since node values can be up to `10^9`.
   - Add a base case: `map.put(0L, 1)` because a prefix sum of `0` inherently exists before we process any nodes (meaning a path starting from the root itself that equals the target).

2. **DFS Traversal:**
   - Traverse the tree using Depth-First Search (DFS), keeping track of the `currentSum`.
   - For each node, add its value to `currentSum`.
   - Check if `currentSum - targetSum` exists in our Hash Map. If it does, we add its frequency to our total `count` of valid paths.
   - Add the `currentSum` to the Hash Map (increment its frequency).
   - Recurse on the left and right children.

3. **Backtracking:**
   - When returning from the recursive calls (moving back up the tree), we must **decrement** the `currentSum` frequency from our Hash Map. This is because the Hash Map should only contain prefix sums of the ancestors of the current node. Prefix sums from one branch cannot be used to form a valid downward path in another branch.

## Complexity

- **Time Complexity:** `O(N)`, where `N` is the number of nodes in the tree. We visit each node exactly once during our DFS traversal, and Hash Map operations take `O(1)` average time.
- **Space Complexity:** `O(N)`. The Hash Map can store up to `N` prefix sums in the worst case. The recursion stack also takes `O(N)` space in the worst case (a completely skewed tree).
