# 508. Most Frequent Subtree Sum

## Intuition

The problem asks us to find the most frequent **subtree sum** in a binary tree. If multiple subtree sums share the maximum frequency, we should return all of them.

The subtree sum of any node is defined as:
$$\text{SubtreeSum}(\text{node}) = \text{node.val} + \text{SubtreeSum}(\text{node.left}) + \text{SubtreeSum}(\text{node.right})$$

Because a node's subtree sum depends directly on the subtree sums of its left and right children, a **bottom-up Post-Order Depth-First Search (DFS)** traversal is the natural choice.

---

## Key Insights & Algorithm

1. **Post-Order Traversal (DFS)**:
   - We recursively compute the left subtree sum and right subtree sum first.
   - Then, we calculate the total sum for the current node as `node.val + leftSum + rightSum`.

2. **Frequency Counting with HashMap**:
   - We maintain a hash map `sumCountMap` mapping each `subtreeSum -> frequency`.
   - Simultaneously, we track `maxCount`, which records the highest frequency encountered across all subtree sums so far.

3. **Result Extraction**:
   - After traversing all nodes in the tree, we iterate through the map entries to collect all subtree sums whose frequency matches `maxCount`.

---

## Detailed Step-by-Step

1. If `root == null`, return an empty array `[]`.
2. Execute post-order traversal (`getTreeSum(node)`):
   - Base case: `if (node == null) return 0;`
   - Recursively obtain `leftSum = getTreeSum(node.left)`.
   - Recursively obtain `rightSum = getTreeSum(node.right)`.
   - Compute `totalSum = node.val + leftSum + rightSum`.
   - Increment `totalSum`'s count in `sumCountMap`.
   - Update `maxCount = Math.max(maxCount, newCount)`.
   - Return `totalSum` to the parent caller.
3. Filter `sumCountMap` to collect all keys with `count == maxCount` into a list.
4. Convert the list to an array `int[]` and return it.

---

## Complexity Analysis

### Time Complexity:
- $\mathcal{O}(N)$: Where $N$ is the total number of nodes in the binary tree. We visit each node exactly once, performing $\mathcal{O}(1)$ average time hash map lookup and insert operations.

### Space Complexity:
- $\mathcal{O}(N)$:
  - **HashMap**: Stores up to $N$ distinct subtree sums in the worst case.
  - **Call Stack**: In the worst-case scenario of a skewed tree, recursion stack depth reaches $\mathcal{O}(N)$. For a balanced tree, it takes $\mathcal{O}(\log N)$ space.
