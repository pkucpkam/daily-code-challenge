/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */

class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        int pVal = p.val;
        int qVal = q.val;
        TreeNode current = root;

        while (current != null) {
            if (pVal < current.val && qVal < current.val) {
                current = current.left;
            } else if (pVal > current.val && qVal > current.val) {
                current = current.right;
            } else {
                return current;
            }
        }

        return null;
    }
}Microsoft.QuickAction.MobileHotspot