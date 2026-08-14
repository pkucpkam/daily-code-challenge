# 538. Convert BST to Greater Tree

## Intuition

A Binary Search Tree (BST) has an inherent property: for any given node, all keys in its right subtree are strictly greater than the node's key, and all keys in its left subtree are strictly smaller.

Standard **In-Order Traversal** (Left $\rightarrow$ Root $\rightarrow$ Right) visits tree nodes in ascending sorted order. 
If we reverse this order—performing a **Reverse In-Order Traversal** (Right $\rightarrow$ Root $\rightarrow$ Left)—we visit the nodes in **descending order** (from largest key to smallest key).

By traversing from largest to smallest while maintaining a running `sum` of node values:
1. Every node visited is guaranteed to be processed *after* all nodes with strictly greater values have already been processed.
2. The running `sum` at the moment we visit a node will be the exact sum of all keys greater than or equal to that node's original value.
3. Updating `node.val = sum` converts the tree into a Greater Tree in a single pass.

---

## Key Insights & Algorithm

### Reverse In-Order Traversal (DFS)

1. **Maintain Running Sum**: Initialize a global/class variable `sum = 0`.
2. **Right Subtree First**: Recursively traverse the right child `convertBST(root.right)` to accumulate values of all larger nodes.
3. **Update Node Value**: 
   - Add current node's original value to running `sum`: `sum += root.val`.
   - Update current node's value with running `sum`: `root.val = sum`.
4. **Left Subtree Last**: Recursively traverse the left child `convertBST(root.left)` so smaller nodes can incorporate the updated sum of larger nodes.
5. **Return Root**: Return `root` after traversal completes.

---

## Complexity Analysis

- **Time Complexity**: $\mathcal{O}(N)$
  - Each node in the BST is visited exactly once during the traversal, where $N$ is the total number of nodes in the tree.

- **Space Complexity**: $\mathcal{O}(H)$
  - The space complexity is determined by the call stack depth during recursion, where $H$ is the height of the tree.
  - In the worst case (skewed tree), $H = \mathcal{O}(N)$.
  - In the best case (balanced tree), $H = \mathcal{O}(\log N)$.

