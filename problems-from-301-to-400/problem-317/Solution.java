import java.util.ArrayDeque;
import java.util.Deque;

/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *     // Constructor initializes an empty nested list.
 *     public NestedInteger();
 *
 *     // Constructor initializes a single integer.
 *     public NestedInteger(int value);
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // Set this NestedInteger to hold a single integer.
 *     public void setInteger(int value);
 *
 *     // Set this NestedInteger to hold a nested list and adds a nested integer to it.
 *     public void add(NestedInteger ni);
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return empty list if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
class Solution {
    public NestedInteger deserialize(String s) {
        if (s.charAt(0) != '[') {
            return new NestedInteger(Integer.parseInt(s));
        }

        Deque<NestedInteger> stack = new ArrayDeque<>();
        NestedInteger current = null;

        for (int i = 0; i < s.length();) {
            char c = s.charAt(i);

            if (c == '[') {
                if (current != null) {
                    stack.push(current);
                }

                current = new NestedInteger();
                i++;
            } else if (c == ']') {
                if (!stack.isEmpty()) {
                    NestedInteger completed = current;
                    current = stack.pop();
                    current.add(completed);
                }

                i++;
            } else if (c == ',') {
                i++;
            } else {
                int start = i;

                if (s.charAt(i) == '-') {
                    i++;
                }

                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    i++;
                }

                current.add(new NestedInteger(Integer.parseInt(s.substring(start, i))));
            }
        }

        return current;
    }
}
