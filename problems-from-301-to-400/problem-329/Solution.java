class Solution {
    public String removeKdigits(String num, int k) {
        int len = num.length();
        if (len == k) {
            return "0";
        }
        
        char[] stack = new char[len];
        int top = 0; // stack pointer
        
        for (int i = 0; i < len; i++) {
            char digit = num.charAt(i);
            // Greedy: pop the previous digit if it is larger than the current digit
            while (k > 0 && top > 0 && stack[top - 1] > digit) {
                top--;
                k--;
            }
            stack[top++] = digit;
        }
        
        // If k > 0, remove the remaining digits from the end (since stack is non-decreasing)
        top -= k;
        
        // Skip leading zeros
        int start = 0;
        while (start < top && stack[start] == '0') {
            start++;
        }
        
        // If no digits remain, return "0"
        if (start == top) {
            return "0";
        }
        
        return new String(stack, start, top - start);
    }
}