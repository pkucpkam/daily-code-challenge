import java.util.*;

/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return empty list if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
public class NestedIterator implements Iterator<Integer> {
    private Deque<NestedInteger> stack;

    public NestedIterator(List<NestedInteger> nestedList) {
        stack = new ArrayDeque<>();
        // Đẩy các phần tử vào stack theo thứ tự ngược lại để phần tử đầu tiên nằm ở đỉnh stack
        pushListToStack(nestedList);
    }

    @Override
    public Integer next() {
        // hasNext() đảm bảo phần tử ở đỉnh stack luôn là một số nguyên
        if (!hasNext()) return null;
        return stack.pop().getInteger();
    }

    @Override
    public boolean hasNext() {
        // Trong khi đỉnh stack là một danh sách, ta giải nén nó
        while (!stack.isEmpty() && !stack.peek().isInteger()) {
            List<NestedInteger> list = stack.pop().getList();
            pushListToStack(list);
        }
        return !stack.isEmpty();
    }

    private void pushListToStack(List<NestedInteger> list) {
        for (int i = list.size() - 1; i >= 0; i--) {
            stack.push(list.get(i));
        }
    }
}

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */