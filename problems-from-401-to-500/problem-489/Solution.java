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
    private int maxLen = 0;

    public int longestUnivaluePath(TreeNode root) {
        maxLen = 0;
        dfs(root);
        return maxLen;
    }

    private int dfs(TreeNode node) {
        if (node == null) {
            return 0;
        }

        // Đệ quy tìm độ dài nhánh đơn cùng giá trị lớn nhất từ cây con trái và phải
        int left = dfs(node.left);
        int right = dfs(node.right);

        int leftPath = 0;
        int rightPath = 0;

        // Nếu nút con bên trái có cùng giá trị với nút hiện tại
        if (node.left != null && node.left.val == node.val) {
            leftPath = left + 1;
        }

        // Nếu nút con bên phải có cùng giá trị với nút hiện tại
        if (node.right != null && node.right.val == node.val) {
            rightPath = right + 1;
        }

        // Cập nhật kỷ lục đường đi uốn cong tại đỉnh `node` (nối cả nhánh trái và phải)
        maxLen = Math.max(maxLen, leftPath + rightPath);

        // Trả về cho nút cha nhánh đơn dài nhất có thể kéo dài lên
        return Math.max(leftPath, rightPath);
    }
}
