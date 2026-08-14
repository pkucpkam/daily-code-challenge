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
    private int sum = 0;

    public TreeNode convertBST(TreeNode root) {
        if (root != null) {
            // 1. Visit right subtree first (greater values)
            convertBST(root.right);

            // 2. Process current node
            sum += root.val;
            root.val = sum;

            // 3. Visit left subtree (smaller values)
            convertBST(root.left);
        }
        return root;
    }
}