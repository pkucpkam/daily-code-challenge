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
import java.util.*;

class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));
        for (ListNode node : lists) {
            if (node != null) pq.offer(node);
        }
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (!pq.isEmpty()) {
            ListNode node = pq.poll();
            tail.next = node;
            tail = tail.next;
            if (node.next != null) pq.offer(node.next);
        }
        return dummy.next;
    }
}

// Better Solution 

// class Solution {
//     public ListNode mergeKLists(ListNode[] lists) {
//         if(lists.length == 0) return null;
//         ListNode resNode = lists[0];

//         Queue<ListNode> processorQueue = new LinkedList<>();
//         for(ListNode x : lists) processorQueue.add(x);

//         while(processorQueue.size() != 1){
//             ListNode l1 = processorQueue.poll();
//             ListNode l2 = processorQueue.poll();
//             processorQueue.add(mergeTwoNode(l1, l2));
//         }

//         return processorQueue.poll();
//     }

//     private ListNode mergeTwoNode(ListNode list1, ListNode list2){
//         if(list1 == null) return list2;
//         if(list2 == null) return list1;

//         ListNode palceHolder = new ListNode(-1);
//         ListNode mergedNode = palceHolder;

//         while(list1 != null || list2 != null){
//             if(list1 == null){
//                 ListNode nextNode = list2.next;
//                 list2.next = null; // Detach the Node and Move to Merged Node
//                 mergedNode.next = list2;
//                 list2 = nextNode;
//             }else if(list2 == null){
//                 ListNode nextNode = list1.next;
//                 list1.next = null; // Detach the Node and Move to Merged Node
//                 mergedNode.next = list1;
//                 list1 = nextNode;
//             } else if (list1.val <= list2.val){
//                 ListNode nextNode = list1.next;
//                 list1.next = null; // Detach the Node and Move to Merged Node
//                 mergedNode.next = list1;
//                 list1 = nextNode;
//             }else{
//                 ListNode nextNode = list2.next;
//                 list2.next = null; // Detach the Node and Move to Merged Node
//                 mergedNode.next = list2;
//                 list2 = nextNode;
//             }

//             mergedNode = mergedNode.next;
//         }


//         return palceHolder.next;
//     }
// }