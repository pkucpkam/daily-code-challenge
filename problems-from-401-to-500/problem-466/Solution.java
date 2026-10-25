import java.util.ArrayDeque;
import java.util.Deque;

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
     * Best Solution: Kỹ thuật Hai Con Trỏ trên BST kết hợp Dual BST Iterator (Hai Ngăn Xếp).
     *
     * Tư tưởng cốt lõi:
     * 1. Khai thác trọn vẹn đặc tính của Cây tìm kiếm nhị phân (BST):
     *    - Phép duyệt trung thứ tự (In-order Traversal: Trái -> Gốc -> Phải) cho dãy giá trị tăng dần.
     *    - Phép duyệt trung thứ tự đảo ngược (Reverse In-order: Phải -> Gốc -> Trái) cho dãy giá trị giảm dần.
     *
     * 2. Tối ưu hóa không gian bộ nhớ tuyệt đối O(H):
     *    - Thay vì lưu toàn bộ N phần tử vào danh sách O(N) hay dùng HashSet O(N), ta chỉ dùng 2 ngăn xếp (Stack).
     *    - Ngăn xếp `leftIter` duyệt theo chiều xuôi để lấy phần tử nhỏ nhất kế tiếp.
     *    - Ngăn xếp `rightIter` duyệt theo chiều ngược để lấy phần tử lớn nhất kế tiếp.
     *    - Bộ nhớ cho mỗi ngăn xếp tại mọi thời điểm không vượt quá chiều cao H của cây (O(log N) với BST cân bằng).
     *
     * 3. Cơ chế Hai Con Trỏ và Dừng Sớm (Early Termination):
     *    - Tại mỗi bước, xét tổng hai phần tử: sum = left.val + right.val.
     *    - Nếu sum == k: Tìm thấy cặp hợp lệ -> trả về true ngay lập tức.
     *    - Nếu sum < k: Tổng còn thiếu -> dịch chuyển con trỏ trái sang phần tử lớn hơn tiếp theo.
     *    - Nếu sum > k: Tổng quá lớn -> dịch chuyển con trỏ phải sang phần tử nhỏ hơn tiếp theo.
     *    - Quá trình dừng lại khi hai con trỏ gặp nhau (left == right hoặc left.val >= right.val).
     *
     * Time Complexity: O(N) trong trường hợp xấu nhất (mỗi nút được push/pop vào stack tối đa 1 lần).
     *                  Hỗ trợ dừng sớm (early exit) ngay khi tìm thấy cặp nghiệm.
     * Space Complexity: O(H) với H là chiều cao của cây (O(log N) nếu cây cân bằng, O(N) nếu cây suy biến thành đường thẳng).
     */
    public boolean findTarget(TreeNode root, int k) {
        if (root == null) {
            return false;
        }

        BSTIterator leftIter = new BSTIterator(root, true);   // Con trỏ duyệt từ nhỏ đến lớn
        BSTIterator rightIter = new BSTIterator(root, false); // Con trỏ duyệt từ lớn đến nhỏ

        TreeNode left = leftIter.next();
        TreeNode right = rightIter.next();

        // Lặp cho đến khi hai con trỏ gặp nhau
        while (left != null && right != null && left != right && left.val < right.val) {
            int sum = left.val + right.val;

            if (sum == k) {
                return true;
            } else if (sum < k) {
                left = leftIter.hasNext() ? leftIter.next() : null;
            } else {
                right = rightIter.hasNext() ? rightIter.next() : null;
            }
        }

        return false;
    }

    /**
     * Lớp bổ trợ BSTIterator: Duyệt cây BST từng phần tử một (Lazy / On-demand).
     * - forward = true:  Duyệt In-order (sinh giá trị tăng dần).
     * - forward = false: Duyệt Reverse In-order (sinh giá trị giảm dần).
     */
    private static class BSTIterator {
        private final Deque<TreeNode> stack = new ArrayDeque<>();
        private final boolean forward;

        public BSTIterator(TreeNode root, boolean forward) {
            this.forward = forward;
            pushAll(root);
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public TreeNode next() {
            TreeNode curr = stack.pop();
            // Nếu duyệt tăng dần: sau khi thăm nút, đi sang nhánh con phải và đẩy toàn bộ nhánh trái
            // Nếu duyệt giảm dần: sau khi thăm nút, đi sang nhánh con trái và đẩy toàn bộ nhánh phải
            if (forward) {
                pushAll(curr.right);
            } else {
                pushAll(curr.left);
            }
            return curr;
        }

        private void pushAll(TreeNode node) {
            while (node != null) {
                stack.push(node);
                node = forward ? node.left : node.right;
            }
        }
    }
}