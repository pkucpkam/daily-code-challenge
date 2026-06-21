/*
// Definition for a Node.
class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;
};
*/

class Solution {
    public Node flatten(Node head) {
        if (head == null) {
            return null;
        }
        
        Node curr = head;
        while (curr != null) {
            // If the current node has a child
            if (curr.child != null) {
                // Find the tail of the child level
                Node tail = curr.child;
                while (tail.next != null) {
                    tail = tail.next;
                }
                
                // Connect the tail of the child level to curr.next
                if (curr.next != null) {
                    curr.next.prev = tail;
                    tail.next = curr.next;
                }
                
                // Connect curr to the child
                curr.next = curr.child;
                curr.child.prev = curr;
                curr.child = null; // Important: remove the child pointer
            }
            
            // Move to the next node
            curr = curr.next;
        }
        
        return head;
    }
}