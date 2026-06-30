import java.util.Stack;

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
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        
        while (l1 != null) {
            stack1.push(l1.val);
            l1 = l1.next;
        }
        
        while (l2 != null) {
            stack2.push(l2.val);
            l2 = l2.next;
        }
        
        ListNode head = null;
        int carry = 0;
        
        while (!stack1.isEmpty() || !stack2.isEmpty() || carry != 0) {
            int sum = carry;
            if (!stack1.isEmpty()) sum += stack1.pop();
            if (!stack2.isEmpty()) sum += stack2.pop();
            
            ListNode newNode = new ListNode(sum % 10);
            newNode.next = head;
            head = newNode;
            
            carry = sum / 10;
        }
        
        return head;
    }
}