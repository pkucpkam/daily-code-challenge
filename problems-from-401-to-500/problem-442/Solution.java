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

    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        // Nếu một trong hai node là null, trả về node còn lại (O(1))
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }

        // Cả 2 node đều tồn tại: cộng dồn giá trị của root2 vào root1
        root1.val += root2.val;

        // Đệ quy gộp nhánh con bên trái và bên phải
        root1.left = mergeTrees(root1.left, root2.left);
        root1.right = mergeTrees(root1.right, root2.right);

        // Trả về gốc của cây đã gộp
        return root1;
    }
}