import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public List<List<String>> printTree(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        // Bước 1: Tính chiều cao của cây nhị phân
        int height = getHeight(root);
        int m = height + 1;
        int n = (1 << (height + 1)) - 1;

        // Bước 2: Khởi tạo ma trận chuỗi với các ô rỗng ""
        String[][] matrix = new String[m][n];
        for (String[] row : matrix) {
            Arrays.fill(row, "");
        }

        // Bước 3: Đặt nút gốc vào vị trí giữa của hàng đầu tiên và đệ quy điền các nút con
        fill(root, matrix, 0, (n - 1) / 2, height);

        // Bước 4: Chuyển đổi ma trận sang danh sách List<List<String>>
        List<List<String>> result = new ArrayList<>(m);
        for (String[] row : matrix) {
            result.add(Arrays.asList(row));
        }

        return result;
    }

    /**
     * Hàm tính chiều cao của cây (nút gốc có chiều cao là 0, cây rỗng là -1).
     */
    private int getHeight(TreeNode node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    /**
     * Hàm đệ quy DFS điền giá trị của các nút vào ma trận theo công thức tọa độ.
     *
     * @param node   Nút cây nhị phân hiện tại
     * @param matrix Ma trận kết quả
     * @param r      Chỉ số hàng hiện tại (0 <= r < m)
     * @param c      Chỉ số cột hiện tại (0 <= c < n)
     * @param height Chiều cao tổng thể của cây
     */
    private void fill(TreeNode node, String[][] matrix, int r, int c, int height) {
        if (node == null) {
            return;
        }

        // Ghi giá trị của nút hiện tại vào ma trận
        matrix[r][c] = Integer.toString(node.val);

        // Tính khoảng dịch chuyển cột cho hai nhánh con: offset = 2^(height - r - 1)
        int offset = 1 << (height - r - 1);

        if (node.left != null) {
            fill(node.left, matrix, r + 1, c - offset, height);
        }
        if (node.right != null) {
            fill(node.right, matrix, r + 1, c + offset, height);
        }
    }
}