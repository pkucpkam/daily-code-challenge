# Pre-order Traversal Approach: O(N) Time and O(N) Space

## Intuition

The problem asks us to design an algorithm to serialize and deserialize a Binary Search Tree (BST). Serialization converts the tree into a string, and deserialization reconstructs the original tree from that string. 

To make the encoded string as compact as possible, we don't need to store `null` pointers like we would for a general binary tree. Because a BST strictly orders its values (left child < parent < right child), we can uniquely reconstruct the tree from just its **pre-order traversal** (Root $\rightarrow$ Left $\rightarrow$ Right).

During deserialization, we can use the properties of a BST (where every node falls within a valid `[lower, upper]` bound) to know exactly when to stop adding nodes to a subtree without needing explicit `null` markers in the string.

## Algorithm

### Serialization
1. Initialize a `StringBuilder` to build the result string.
2. Perform a pre-order traversal of the BST:
   - If the current node is `null`, simply return.
   - Append the current node's value followed by a delimiter (like `,`) to the string.
   - Recursively traverse the left subtree.
   - Recursively traverse the right subtree.
3. Return the built string.

### Deserialization
1. If the input string is empty, return `null`.
2. Split the string by the delimiter (`,`) and add all values into a `Queue`.
3. Use a recursive helper function that takes the queue and a valid range `[lower, upper]` (initially `[-∞, ∞]`):
   - If the queue is empty, return `null`.
   - Peek at the first value in the queue. If it is outside the current `[lower, upper]` boundary, it doesn't belong in the current subtree, so return `null`.
   - Otherwise, it belongs in this subtree! Poll the value from the queue and create a new `TreeNode`.
   - Recursively build the `left` child by updating the `upper` bound to the current node's value.
   - Recursively build the `right` child by updating the `lower` bound to the current node's value.
   - Return the created node.

## Complexity

- **Time Complexity:** $\mathcal{O}(N)$, where $N$ is the number of nodes in the BST. During serialization, we visit each node exactly once. During deserialization, we also process each value exactly once because the boundary checks run in $\mathcal{O}(1)$ time.
- **Space Complexity:** $\mathcal{O}(N)$. In the worst case (a completely unbalanced tree, such as a linked list), the recursion stack will be $N$ frames deep. Additionally, string splitting and maintaining the queue during deserialization require $\mathcal{O}(N)$ space.
