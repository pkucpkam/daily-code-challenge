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
    private int diameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        depth(root);
        return diameter;
    }

    // Returns the depth (number of edges) of the deepest path from this node downward
    private int depth(TreeNode node) {
        if (node == null) return 0;

        int left  = depth(node.left);
        int right = depth(node.right);

        // The path through this node spans left + right edges
        diameter = Math.max(diameter, left + right);

        // Return the longer arm plus 1 edge to the parent
        return Math.max(left, right) + 1;
    }
}