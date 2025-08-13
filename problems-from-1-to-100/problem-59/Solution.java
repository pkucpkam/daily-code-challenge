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
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        findPaths(root, "", paths);
        return paths;
    }

    private void findPaths(TreeNode node, String path, List<String> paths) {
        if (node == null) {
            return;
        }
        path += node.val;
        if (node.left == null && node.right == null) {
            paths.add(path);
        } else {
            path += "->";
            findPaths(node.left, path, paths);
            findPaths(node.right, path, paths);
        }
    }
}

// Better Solution 
// class Solution {
//     List<String> out = new ArrayList<>();
//     StringBuilder s = new StringBuilder();
//     public List<String> binaryTreePaths(TreeNode root) {
//        if(root==null)return out;
//        int len = s.length();
//        s.append(root.val);
//        if(root.left==null && root.right==null)out.add(s.toString());
//        else {
//         s.append("->");
//         out = binaryTreePaths(root.left);
//         out = binaryTreePaths(root.right);
//        }
//        s.setLength(len);
//        return out;
//     }
// }