/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
class Solution {
    private int tiltSum = 0;

    /**
     * Find the sum of tilts of all nodes in the binary tree.
     * 
     * Approach: Post-order DFS traversal
     * - For each node, calculate sum of left subtree and right subtree
     * - Calculate tilt as absolute difference
     * - Accumulate total tilt
     * - Return sum of current subtree
     * 
     * Time Complexity: O(N) - visit each node once
     * Space Complexity: O(H) - recursion stack depth, H = tree height
     */
    public int findTilt(TreeNode root) {
        postOrder(root);
        return tiltSum;
    }

    /**
     * Post-order traversal that calculates subtree sum and accumulates tilt.
     * 
     * @param node Current node being processed
     * @return Sum of all node values in the subtree rooted at node
     */
    private int postOrder(TreeNode node) {
        if (node == null) {
            return 0;
        }

        // Get sum of left and right subtrees
        int leftSum = postOrder(node.left);
        int rightSum = postOrder(node.right);

        // Calculate tilt for current node and add to total
        tiltSum += Math.abs(leftSum - rightSum);

        // Return sum of entire subtree (left + right + current)
        return leftSum + rightSum + node.val;
    }
}