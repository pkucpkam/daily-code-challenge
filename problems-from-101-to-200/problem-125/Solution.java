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
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0, head);
        ListNode groupPrev = dummy;

        while (true) {
            ListNode kth = groupPrev;
            for (int i = 0; i < k && kth != null; i++) {
                kth = kth.next;
            }
            if (kth == null) break; 

            ListNode groupNext = kth.next;
            ListNode prev = groupNext, curr = groupPrev.next;
            while (curr != groupNext) {
                ListNode tmp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = tmp;
            }
            ListNode tmp = groupPrev.next;
            groupPrev.next = kth;
            groupPrev = tmp;
        }
        return dummy.next;
    }
}


/*
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        if(head==null) return null;
        ListNode a;
        ListNode b;
        a = b = head;
        for(int i = 0;i<k;i++){
            if(b==null) return head;
            b = b.next;
        }
        ListNode newNode = reverseN(a,k);
        a.next = reverseKGroup(b,k);
        return newNode;
    }
    private ListNode reverseN(ListNode head, int n){
        if(head==null || head.next == null||n<=1){
            return head;
        }
        ListNode prev = null;
        ListNode current = head;
        ListNode next = null;
        int count = 0;
        while(current!=null && count<n){
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
            count++;
        }
        if(next!=null){
            head.next = next;
        }
        return prev;
    }
}
*/