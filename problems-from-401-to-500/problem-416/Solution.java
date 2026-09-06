/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    /**
     * Determines if subRoot is a subtree of root.
     * 
     * Approach: Depth-First Search (DFS) with Tree Comparison
     * - A tree root contains subRoot if:
     *   1. The current tree rooted at root is identical to subRoot (isSameTree).
     *   2. Or subRoot is a subtree of the left child (root.left).
     *   3. Or subRoot is a subtree of the right child (root.right).
     * 
     * Time Complexity: O(N * M) in the worst case, where N is the number of nodes in root
     *                  and M is the number of nodes in subRoot. In typical/average cases,
     *                  it terminates much earlier and approaches O(N).
     * Space Complexity: O(H) where H is the height of root (O(N) worst case for skewed trees,
     *                   O(log N) for balanced trees) due to the recursion call stack.
     */
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        // If the main tree is null, it cannot contain any non-empty subtree
        if (root == null) {
            return false;
        }

        // Check if trees rooted at current root and subRoot are identical
        if (isSameTree(root, subRoot)) {
            return true;
        }

        // Recursively check left and right subtrees
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    /**
     * Helper method to check if two binary trees are identical in structure and values.
     * 
     * @param p Root of the first binary tree
     * @param q Root of the second binary tree
     * @return true if both trees are structurally identical with equal node values
     */
    private boolean isSameTree(TreeNode p, TreeNode q) {
        // Both nodes are null -> structurally identical
        if (p == null && q == null) {
            return true;
        }

        // One is null or node values differ -> not identical
        if (p == null || q == null || p.val != q.val) {
            return false;
        }

        // Both current nodes match; recursively verify left and right subtrees
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}