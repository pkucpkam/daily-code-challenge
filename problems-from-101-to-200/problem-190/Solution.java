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
    private int preIndex;
    private java.util.Map<Integer, Integer> idxMap;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        preIndex = 0;
        idxMap = new java.util.HashMap<>();
        for (int i = 0; i < inorder.length; i++) idxMap.put(inorder[i], i);
        return build(preorder, 0, inorder.length - 1);
    }

    private TreeNode build(int[] preorder, int inLeft, int inRight) {
        if (inLeft > inRight) return null;
        int rootVal = preorder[preIndex++];
        TreeNode root = new TreeNode(rootVal);
        int index = idxMap.get(rootVal);
        root.left = build(preorder, inLeft, index - 1);
        root.right = build(preorder, index + 1, inRight);
        return root;
    }
}