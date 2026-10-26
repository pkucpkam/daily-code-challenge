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
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();

        for (int num : nums) {
            TreeNode curr = new TreeNode(num);

            // Pop các nút nhỏ hơn num: nút bị pop cuối cùng sẽ là con trái trực tiếp của curr
            while (!stack.isEmpty() && stack.peek().val < num) {
                curr.left = stack.pop();
            }

            // Nút lớn hơn gần nhất ở bên trái nhận curr làm con phải trực tiếp
            if (!stack.isEmpty()) {
                stack.peek().right = curr;
            }

            // Đẩy nút hiện tại vào ngăn xếp để duy trì tính đơn điệu giảm dần
            stack.push(curr);
        }

        // Đáy ngăn xếp (phần tử lớn nhất trong toàn bộ mảng) chính là nút gốc của cây
        return stack.peekLast();
    }
}