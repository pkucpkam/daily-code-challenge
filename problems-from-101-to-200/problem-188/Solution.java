/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null)
            return res;
        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int sz = q.size();
            List<Integer> level = new ArrayList<>(sz);
            for (int i = 0; i < sz; i++) {
                TreeNode node = q.poll();
                level.add(node.val);
                if (node.left != null)
                    q.offer(node.left);
                if (node.right != null)
                    q.offer(node.right);
            }
            res.add(level);
        }
        return res;
    }
}

// Better solution 

// class Solution {
//     public List<List<Integer>> levelOrder(TreeNode root) {
//         List<List<Integer>> ls = new ArrayList<>();
//         help(root,0,ls);
//         return ls;
//     }

//     public void help(TreeNode root,int level,List<List<Integer>> ls){
//         if(root==null) return;
//         if(level==ls.size()){
//             ls.add(new ArrayList<>());
//         }

//         ls.get(level).add(root.val);

//         help(root.left,level+1,ls);
//         help(root.right,level+1,ls);
//     }
// }