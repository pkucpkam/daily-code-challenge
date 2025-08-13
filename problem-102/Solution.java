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
    public int[] findMode(TreeNode root) {
        List<Integer> modes = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root, prev = null;
        int maxCount = 0, count = 0;

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            if (prev != null && prev.val == curr.val) {
                count++;
            } else {
                count = 1;
            }

            if (count > maxCount) {
                modes.clear();
                modes.add(curr.val);
                maxCount = count;
            } else if (count == maxCount) {
                modes.add(curr.val);
            }
            prev = curr;
            curr = curr.right;
        }
        int[] res = new int[modes.size()];
        for (int i = 0; i < modes.size(); i++) {
            res[i] = modes.get(i);
        }
        return res;
    }
}

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
// class Solution {
//     TreeNode prev=null;
//     int count=1;
//     int max=0;
//     public int[] findMode(TreeNode root) {
//         ArrayList<Integer> arr = new ArrayList<>(); 
//         inorder(root,arr);
//         int[] ans = new int[arr.size()];
//         for(int i=0;i<arr.size();i++){
//             ans[i]=arr.get(i);
//         }
//         return ans;
//     }
//     public void inorder(TreeNode node,ArrayList<Integer> arr){
//         if(node==null){
//             return;
//         }
//         inorder(node.left,arr);
//         if(prev!=null){
//             if(prev.val==node.val){
//                 count++;
//             }
//             else{
//                 count=1;
//             }
//         }
//         if(count>max){
//             arr.clear();
//             arr.add(node.val);
//             max=count;
//         }
//         else if(count==max){
//             arr.add(node.val);
//         }
//         prev=node;
//         inorder(node.right,arr);
//     }
// }