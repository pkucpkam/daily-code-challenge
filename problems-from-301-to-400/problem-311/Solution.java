class Solution {
    public int combinationSum4(int[] nums, int target) {
        // dp[i] represents the number of combinations that sum to i
        int[] dp = new int[target + 1];
        dp[0] = 1; // One way to make 0: select nothing

        // For each sum from 1 to target
        for (int i = 1; i <= target; i++) {
            // Try each number in nums
            for (int num : nums) {
                if (num <= i) {
                    // If current number can be used, add combinations from (i - num)
                    dp[i] += dp[i - num];
                }
            }
        }

        return dp[target];
    }
}