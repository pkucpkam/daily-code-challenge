
// public class TreeNode {
//     int val;
//     TreeNode left;
//     TreeNode right;

//     TreeNode() {
//     }

//     TreeNode(int val) {
//         this.val = val;
//     }

//     TreeNode(int val, TreeNode left, TreeNode right) {
//         this.val = val;
//         this.left = left;
//         this.right = right;
//     }
// }

class Solution {
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        if (root.left == null) {
            return minDepth(root.right) + 1;
        }
        if (root.right == null) {
            return minDepth(root.left) + 1;
        }
        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }
}

// Better Solution
// class Solution {
// public int minimum = Integer.MAX_VALUE;

// public int minDepth(TreeNode root) {
// if (root == null)
// return 0;

// dfs(root, 0);
// return minimum;
// }

// public void dfs(TreeNode node, int depth) {
// depth++;
// if (minimum == depth) {
// return;
// }

// if (isLeaf(node)) {
// minimum = Math.min(minimum, depth);
// return;
// }

// if (node.right != null && (node.left == null || !isLeaf(node.left))) {
// dfs(node.right, depth);
// }

// if (node.left != null) {
// dfs(node.left, depth);
// }
// }
// public boolean isLeaf(TreeNode root) {
// return root.right == null && root.left == null;
// }
// }