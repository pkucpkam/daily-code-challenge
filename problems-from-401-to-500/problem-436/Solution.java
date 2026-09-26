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

    public String tree2str(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        dfs(root, sb);
        return sb.toString();
    }

    private void dfs(TreeNode node, StringBuilder sb) {
        if (node == null) {
            return;
        }

        // 1. Thêm giá trị của node hiện tại
        sb.append(node.val);

        // 2. Nếu là node lá, không cần thêm ngoặc
        if (node.left == null && node.right == null) {
            return;
        }

        // 3. Xử lý cây con bên trái:
        // Luôn bọc trong ngoặc nếu node có ít nhất một con (trái hoặc phải)
        sb.append('(');
        dfs(node.left, sb);
        sb.append(')');

        // 4. Xử lý cây con bên phải:
        // Chỉ thêm ngoặc khi con phải thực sự tồn tại
        if (node.right != null) {
            sb.append('(');
            dfs(node.right, sb);
            sb.append(')');
        }
    }
}