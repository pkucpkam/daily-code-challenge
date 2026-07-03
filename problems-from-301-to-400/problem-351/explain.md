# Recursion Approach: O(H) Time and O(H) Space

## Intuition

Deleting a node in a Binary Search Tree (BST) can be broken down into two main steps: finding the node to delete, and then actually deleting it while maintaining the BST properties (left child < parent < right child).

Since the tree is a BST, we can efficiently find the target node by comparing its value to the current node and traversing left or right. Once we find the node, we have three cases to handle:
1. **The node is a leaf (no children):** We can simply remove it by returning `null` to its parent.
2. **The node has one child:** We can bypass the node by returning its non-null child to its parent.
3. **The node has two children:** This is the most complex case. We need to replace the node's value with its **inorder successor** (the smallest value in its right subtree). After replacing the value, we recursively delete the inorder successor from the right subtree.

## Algorithm

1. If the `root` is `null`, return `null` (base case).
2. Compare the `key` to `root.val`:
   - If `key < root.val`, the node to delete is in the left subtree. Assign `root.left = deleteNode(root.left, key)`.
   - If `key > root.val`, the node to delete is in the right subtree. Assign `root.right = deleteNode(root.right, key)`.
3. If `key == root.val`, we have found the node to delete! Handle the three cases:
   - **Case 1 & 2 (0 or 1 child):** 
     - If `root.left` is `null`, return `root.right`.
     - If `root.right` is `null`, return `root.left`.
   - **Case 3 (2 children):**
     - Find the inorder successor by traversing to the leftmost node in the right subtree (`findMin(root.right)`).
     - Replace the current node's value (`root.val`) with the inorder successor's value.
     - Recursively call `deleteNode` on the right subtree to delete the inorder successor. Assign the result to `root.right`.
4. Return the updated `root`.

## Complexity

- **Time Complexity:** $\mathcal{O}(H)$, where $H$ is the height of the tree. In the worst case, we traverse from the root down to a leaf node to either find the node or find the inorder successor. This takes $\mathcal{O}(\log N)$ time for a balanced BST and $\mathcal{O}(N)$ for a skewed tree. This perfectly matches the follow-up requirement to solve it with time complexity $\mathcal{O}(\text{height of tree})$.
- **Space Complexity:** $\mathcal{O}(H)$ for the recursion stack. The maximum depth of the recursion will be the height of the tree.
