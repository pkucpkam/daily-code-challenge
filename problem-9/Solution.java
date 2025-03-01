import java.util.Stack;

public class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(')');
            } else if (c == '{') {
                stack.push('}');
            } else if (c == '[') {
                stack.push(']');
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.pop();
                if (top != c) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
