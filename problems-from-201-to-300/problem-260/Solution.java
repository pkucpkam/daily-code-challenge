class Solution {
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }

        int case1 = robLinear(nums, 0, n - 2);
        int case2 = robLinear(nums, 1, n - 1);
        return Math.max(case1, case2);
    }

    private int robLinear(int[] nums, int left, int right) {
        int prev2 = 0;
        int prev1 = 0;

        for (int i = left; i <= right; i++) {
            int take = prev2 + nums[i];
            int skip = prev1;
            int cur = Math.max(take, skip);
            prev2 = prev1;
            prev1 = cur;
        }

        return prev1;
    }
}