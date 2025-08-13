import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// public class TreeNode {
//     int val;
//     TreeNode left;
//     TreeNode right;
//     TreeNode() {}
//     TreeNode(int val) { this.val = val; }
//     TreeNode(int val, TreeNode left, TreeNode right) {
//         this.val = val;
//         this.left = left;
//         this.right = right;
//     }
// }

class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        Stack<TreeNode> output = new Stack<>();
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            output.push(node);
            
            if (node.left != null) stack.push(node.left);
            if (node.right != null) stack.push(node.right);
        }
        
        while (!output.isEmpty()) {
            result.add(output.pop().val);
        }
        
        return result;
    }
}