import java.util.ArrayDeque;
import java.util.Queue;

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
     * Lớp bổ trợ lưu trữ cặp đối tượng (Node, Index)
     */
    private static class NodeIndex {
        TreeNode node;
        long index;

        NodeIndex(TreeNode node, long index) {
            this.node = node;
            this.index = index;
        }
    }

    /**
     * Thuật toán Tối ưu: Duyệt theo tầng (BFS / Level-Order Traversal) kết hợp Chuẩn hóa chỉ số
     * 
     * Phân tích kỹ thuật:
     * 1. Đánh số vị trí theo cây nhị phân đầy đủ (Heap Indexing):
     *    - Nếu nút cha có chỉ số là i:
     *      + Con bên trái có chỉ số: 2 * i + 1
     *      + Con bên phải có chỉ số: 2 * i + 2
     *    - Độ rộng của một tầng được tính bằng: (chỉ số nút phải cùng - chỉ số nút trái cùng + 1).
     * 
     * 2. Giải pháp chống tràn số (Overflow Prevention):
     *    - Trong trường hợp cây bị lệch sâu (độ cao cây lên tới 3000), chỉ số có thể lên tới 2^3000,
     *      vượt xa giới hạn của cả kiểu int (32-bit) và long (64-bit).
     *    - Vì độ rộng của một tầng chỉ phụ thuộc vào KHOẢNG CÁCH TƯƠNG ĐỐI giữa các nút trong cùng tầng đó,
     *      ta có thể lấy chỉ số của nút đầu tiên ở mỗi tầng làm mốc (minIndex), rồi trừ minIndex khỏi
     *      chỉ số của mọi nút trong tầng đó.
     *    - Thao tác này đưa chỉ số nút đầu tiên về 0 ở mỗi tầng, triệt tiêu hoàn toàn nguy cơ tràn số.
     * 
     * Độ phức tạp:
     * - Thời gian: O(N) - Mỗi nút trong cây được đưa vào và lấy ra khỏi Queue đúng 1 lần.
     * - Không gian bộ nhớ: O(W) <= O(N) - Với W là độ rộng lớn nhất của cây (số lượng nút tối đa ở một tầng).
     */
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<NodeIndex> queue = new ArrayDeque<>();
        queue.offer(new NodeIndex(root, 0));
        int maxWidth = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            long minIndex = queue.peek().index; // Chỉ số mốc nhỏ nhất của tầng hiện tại
            long first = 0;
            long last = 0;

            for (int i = 0; i < size; i++) {
                NodeIndex curr = queue.poll();
                // Chuẩn hóa chỉ số để chống tràn số
                long normalizedIndex = curr.index - minIndex;

                if (i == 0) {
                    first = normalizedIndex;
                }
                if (i == size - 1) {
                    last = normalizedIndex;
                }

                // Gán chỉ số cho các nút con dựa trên chỉ số đã chuẩn hóa của nút cha
                if (curr.node.left != null) {
                    queue.offer(new NodeIndex(curr.node.left, 2 * normalizedIndex + 1));
                }
                if (curr.node.right != null) {
                    queue.offer(new NodeIndex(curr.node.right, 2 * normalizedIndex + 2));
                }
            }

            // Cập nhật độ rộng tối đa
            int currentWidth = (int) (last - first + 1);
            if (currentWidth > maxWidth) {
                maxWidth = currentWidth;
            }
        }

        return maxWidth;
    }
}