import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
}
*/

class Solution {
    // Best Solution: Iterative using Stack (ArrayDeque) + Reverse - solves the Follow-up
    public List<Integer> postorder(Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Deque<Node> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            result.add(current.val);

            // Push children from left to right so that right child is popped first
            // Traversal order: Root -> Right -> Left
            if (current.children != null) {
                for (Node child : current.children) {
                    stack.push(child);
                }
            }
        }

        // Reverse (Root -> Right -> Left) to get (Left -> Right -> Root) = Postorder
        Collections.reverse(result);
        return result;
    }
}