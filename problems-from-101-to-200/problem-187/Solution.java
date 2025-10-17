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
    public boolean isValidBST(TreeNode root) {
        return helper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean helper(TreeNode node, long lower, long upper) {
        if (node == null)
            return true;
        if (node.val <= lower || node.val >= upper)
            return false;
        return helper(node.left, lower, node.val) && helper(node.right, node.val, upper);
    }

    public void recoverTree(TreeNode root) {
        TreeNode x = null, y = null, pred = null, predecessor = null;
        TreeNode curr = root;

        while (curr != null) {
            if (curr.left != null) {
                predecessor = curr.left;
                while (predecessor.right != null && predecessor.right != curr) {
                    predecessor = predecessor.right;
                }
                if (predecessor.right == null) {
                    predecessor.right = curr;
                    curr = curr.left;
                } else {
                    if (pred != null && curr.val < pred.val) {
                        y = curr;
                        if (x == null)
                            x = pred;
                    }
                    pred = curr;
                    predecessor.right = null;
                    curr = curr.right;
                }
            } else {
                if (pred != null && curr.val < pred.val) {
                    y = curr;
                    if (x == null)
                        x = pred;
                }
                pred = curr;
                curr = curr.right;
            }
        }

        if (x != null && y != null) {
            int tmp = x.val;
            x.val = y.val;
            y.val = tmp;
        }
    }
}