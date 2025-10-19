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
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;

        Deque<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        boolean leftToRight = true;

        while (!q.isEmpty()) {
            int sz = q.size();
            LinkedList<Integer> level = new LinkedList<>();
            for (int i = 0; i < sz; i++) {
                TreeNode node = q.poll();
                if (leftToRight) level.addLast(node.val);
                else level.addFirst(node.val);

                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            res.add(level);
            leftToRight = !leftToRight;
        }

        return res;
    }
}


// Better Solution
// class Solution {
//     public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
//         List<List<Integer>>result = new ArrayList<>();
//         if(root == null){
//             return result;
//         }
//         dfs(root , 0 , result);
//         return result;
//     }
     
//     public void dfs(TreeNode node , int level , List<List<Integer>> result){
//         if(node == null){
//             return;
//         }
//         if(level == result.size()){
//             result.add(new LinkedList<>());
//         }
//         //if level is even add to the list 
//         if(level % 2==0){
//             result.get(level).add(node.val); // add at beg if even level
//         }else{
//             result.get(level).add(0 , node.val);  // add at end if odd level
//         }

//         //resursive function to left and right for ramaining traverse 

//         dfs(node.left , level+1 , result);
//          dfs(node.right , level+1 , result);
//     }   
// }