/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode prev = null, curr = slow;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }

        ListNode firstHalf = head, secondHalf = prev;
        while (secondHalf != null) {
            if (firstHalf.val != secondHalf.val) {
                return false;
            }
            firstHalf = firstHalf.next;
            secondHalf = secondHalf.next;
        }

        return true;
    }
}

// Better Solution
// class Solution {
// static{
// ListNode n = new ListNode(0);
// for(int i=0;i<500;i++)
// isPalindrome(n);
// }

// public static boolean isPalindrome(ListNode head) {
// ListNode h = head;
// //find mid & reverse first half on-the=fly.
// ListNode slow=head,fast = head;
// ListNode reverse = null;
// while(fast!=null && fast.next!=null){
// ListNode temp = slow;
// slow=slow.next;
// fast=fast.next.next;
// //reverse part;
// temp.next=reverse;
// reverse = temp;
// }

// //for odd-length lists;
// if(fast!=null) slow=slow.next;

// //compare two halves;
// while(slow!=null && reverse.val==slow.val){
// slow=slow.next;
// reverse=reverse.next;
// }

// return slow==null;
// }