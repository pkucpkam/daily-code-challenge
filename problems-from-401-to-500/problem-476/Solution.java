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
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null) {
            return null;
        }

        // Nếu giá trị nút nhỏ hơn ngưỡng dưới, loại bỏ nút và nhánh trái, chỉ xét tiếp nhánh phải
        if (root.val < low) {
            return trimBST(root.right, low, high);
        }

        // Nếu giá trị nút lớn hơn ngưỡng trên, loại bỏ nút và nhánh phải, chỉ xét tiếp nhánh trái
        if (root.val > high) {
            return trimBST(root.left, low, high);
        }

        // Giá trị nút nằm trong [low, high]: giữ nguyên nút và đệ quy cắt tỉa hai nhánh con
        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);

        return root;
    } 
}
