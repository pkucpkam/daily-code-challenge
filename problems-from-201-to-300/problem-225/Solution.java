class Solution {
    public int maxProduct(int[] nums) {
        int best = nums[0];
        int currMax = nums[0];
        int currMin = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int val = nums[i];

            if (val < 0) {
                int tmp = currMax;
                currMax = currMin;
                currMin = tmp;
            }

            currMax = Math.max(val, currMax * val);
            currMin = Math.min(val, currMin * val);
            best = Math.max(best, currMax);
        }

        return best;
    }
}