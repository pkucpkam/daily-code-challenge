import java.util.*;

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
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null)
            return res;
        List<Integer> path = new ArrayList<>();
        dfs(root, targetSum, path, res);
        return res;
    }

    private void dfs(TreeNode node, int remain, List<Integer> path, List<List<Integer>> res) {
        if (node == null)
            return;
        path.add(node.val);
        remain -= node.val;
        if (node.left == null && node.right == null) {
            if (remain == 0)
                res.add(new ArrayList<>(path));
        } else {
            dfs(node.left, remain, path, res);
            dfs(node.right, remain, path, res);
        }
        path.remove(path.size() - 1);
    }
}