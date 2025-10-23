/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
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
    private ListNode headPtr;

    public TreeNode sortedListToBST(ListNode head) {
        int size = 0;
        ListNode p = head;
        while (p != null) {
            size++;
            p = p.next;
        }
        headPtr = head;
        return build(0, size - 1);
    }

    private TreeNode build(int left, int right) {
        if (left > right)
            return null;
        int mid = left + (right - left) / 2;
        TreeNode leftNode = build(left, mid - 1);
        TreeNode root = new TreeNode(headPtr.val);
        root.left = leftNode;
        headPtr = headPtr.next;
        root.right = build(mid + 1, right);
        return root;
    }
}