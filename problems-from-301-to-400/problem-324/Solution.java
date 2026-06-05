class Solution {
    public int maxRotateFunction(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int n = nums.length;
        long sum = 0;
        long f = 0;
        
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            f += (long) i * nums[i];
        }
        
        long maxF = f;
        
        for (int i = 1; i < n; i++) {
            f = f + sum - (long) n * nums[n - i];
            maxF = Math.max(maxF, f);
        }
        
        return (int) maxF;
    }
}