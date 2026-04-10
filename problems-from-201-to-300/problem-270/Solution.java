/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public void deleteNode(ListNode node) {
        // Copy next node's value into current node, then skip next node.
        // This works because the given node is guaranteed not to be the tail.
        node.val = node.next.val;
        node.next = node.next.next;
    }
}