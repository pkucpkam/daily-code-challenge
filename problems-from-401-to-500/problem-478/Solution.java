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
     * Thuật toán Tối ưu: Đệ quy DFS Cắt tỉa nhánh (Recursive DFS with Branch Pruning)
     * 
     * Phân tích tính chất bài toán:
     * 1. Cây nhị phân đặc biệt thỏa mãn tính chất: root.val = min(root.left.val, root.right.val).
     *    -> Giá trị của nút cha luôn nhỏ hơn hoặc bằng giá trị của tất cả các con cháu.
     *    -> Giá trị tại nút gốc toàn cục (root.val) luôn là giá trị nhỏ nhất trên toàn bộ cây.
     * 
     * 2. Giá trị nhỏ thứ nhì cần tìm chính là giá trị nhỏ nhất trong số các nút có giá trị > root.val:
     *    secondMin = min { node.val | node.val > root.val }
     * 
     * 3. Chiến lược Cắt tỉa nhánh (Branch Pruning):
     *    - Nếu node.val > root.val: Nút này là một ứng cử viên có giá trị > root.val.
     *      Do đặc tính đơn điệu không giảm của cây, tất cả các nút con cháu của node đều có giá trị >= node.val.
     *      Vì vậy, không có bất kỳ con cháu nào của node có thể sinh ra một giá trị nhỏ hơn node.val.
     *      -> Ta DỪNG DUYỆT (cắt tỉa) toàn bộ cây con tại node và trả về node.val ngay lập tức!
     *    - Nếu node.val == root.val: Ta tiếp tục đệ quy xuống cả 2 con trái và phải.
     *    - Tổng hợp kết quả:
     *      + Nếu cả hai nhánh trái và phải đều tìm thấy giá trị > root.val (khác -1): lấy Math.min(left, right).
     *      + Nếu chỉ một nhánh tìm thấy: trả về giá trị của nhánh đó.
     *      + Nếu cả hai đều không tìm thấy (-1): trả về -1.
     * 
     * Độ phức tạp:
     * - Thời gian: O(N) trong trường hợp xấu nhất, nhưng nhờ cắt tỉa nên thực tế chỉ duyệt các nút bằng root.val.
     * - Không gian bộ nhớ: O(H) với H là chiều cao của cây (độ sâu call stack).
     */
    public int findSecondMinimumValue(TreeNode root) {
        if (root == null) {
            return -1;
        }
        return dfs(root, root.val);
    }

    private int dfs(TreeNode node, int minVal) {
        if (node == null) {
            return -1;
        }

        // Cắt tỉa nhánh: Nếu giá trị nút hiện tại lớn hơn minVal (root.val),
        // thì node.val là giá trị nhỏ nhất > minVal trong cả cây con này.
        // Không cần duyệt sâu xuống các con cháu nữa!
        if (node.val > minVal) {
            return node.val;
        }

        // Nếu node.val == minVal, tiếp tục tìm kiếm ở cả hai nhánh con
        int left = dfs(node.left, minVal);
        int right = dfs(node.right, minVal);

        // Nếu cả hai nhánh đều tìm được giá trị > minVal, chọn giá trị nhỏ hơn
        if (left != -1 && right != -1) {
            return Math.min(left, right);
        }

        // Nếu chỉ một trong hai nhánh tìm được (hoặc cả hai đều là -1)
        return left != -1 ? left : right;
    }
}
