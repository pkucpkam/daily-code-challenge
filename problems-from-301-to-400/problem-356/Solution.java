class Solution {
    public boolean find132pattern(int[] nums) {
        if (nums == null || nums.length < 3) {
            return false;
        }
        
        int n = nums.length;
        int[] stack = new int[n];
        int top = n; 
        int third = Integer.MIN_VALUE; 
        
        for (int i = n - 1; i >= 0; i--) {
      
            if (nums[i] < third) {
                return true;
            }

            while (top < n && nums[i] > stack[top]) {
                third = stack[top++];
            }
            
            stack[--top] = nums[i];
        }
        
        return false;
    }
}