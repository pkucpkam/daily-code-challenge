class Solution {
    public int lastRemaining(int n) {
        int head = 1;
        int step = 1;
        int remaining = n;
        boolean leftToRight = true;
        
        while (remaining > 1) {
            // Update head when moving Left to Right OR 
            // when moving Right to Left and the count of remaining elements is odd.
            if (leftToRight || remaining % 2 == 1) {
                head += step;
            }
            
            remaining /= 2;
            step *= 2;
            leftToRight = !leftToRight;
        }
        
        return head;
    }
}