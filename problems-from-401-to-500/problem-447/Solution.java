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
    /**
     * Best Solution: Duyệt Đệ quy DFS (Depth-First Search)
     * 
     * Time Complexity: O(N) - chỉ duyệt tới tầng depth - 1 rồi dừng ngay, không duyệt các tầng sâu hơn.
     * Space Complexity: O(H) - độ sâu ngăn xếp đệ quy với H <= depth <= N.
     */
    public TreeNode addOneRow(TreeNode root, int val, int depth) {
        // Trường hợp đặc biệt: depth == 1, tạo root mới với cây ban đầu làm cây con bên trái
        if (depth == 1) {
            return new TreeNode(val, root, null);
        }

        // Bắt đầu duyệt DFS từ node gốc ở tầng 1
        dfs(root, val, depth, 1);
        return root;
    }

    private void dfs(TreeNode node, int val, int depth, int currentDepth) {
        if (node == null) {
            return;
        }

        // Khi đạt đến độ sâu depth - 1, chèn 2 node mới vào giữa node hiện tại và các cây con ban đầu
        if (currentDepth == depth - 1) {
            node.left = new TreeNode(val, node.left, null);
            node.right = new TreeNode(val, null, node.right);
            return;
        }

        // Tiếp tục đệ quy xuống tầng tiếp theo cho cả hai nhánh con
        dfs(node.left, val, depth, currentDepth + 1);
        dfs(node.right, val, depth, currentDepth + 1);
    }
}