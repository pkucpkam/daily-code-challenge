import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<Integer, Integer> sumCountMap = new HashMap<>();
    private int maxCount = 0;

    public int[] findFrequentTreeSum(TreeNode root) {
        if (root == null) {
            return new int[0];
        }

        getTreeSum(root);

        List<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : sumCountMap.entrySet()) {
            if (entry.getValue() == maxCount) {
                list.add(entry.getKey());
            }
        }

        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }

        return res;
    }

    private int getTreeSum(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int leftSum = getTreeSum(node.left);
        int rightSum = getTreeSum(node.right);
        int totalSum = node.val + leftSum + rightSum;

        int count = sumCountMap.getOrDefault(totalSum, 0) + 1;
        sumCountMap.put(totalSum, count);
        maxCount = Math.max(maxCount, count);

        return totalSum;
    }
}