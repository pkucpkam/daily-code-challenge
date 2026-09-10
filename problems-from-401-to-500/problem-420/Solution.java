class Solution {
    public int findUnsortedSubarray(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }

        int n = nums.length;
        int left = -1;
        int right = -1;

        int maxSoFar = nums[0];
        int minSoFar = nums[n - 1];

        for (int i = 1; i < n; i++) {
            // Left-to-right pass:
            // Find the rightmost element that is smaller than the running maximum
            maxSoFar = Math.max(maxSoFar, nums[i]);
            if (nums[i] < maxSoFar) {
                right = i;
            }

            // Right-to-left pass:
            // Find the leftmost element that is larger than the running minimum
            int j = n - 1 - i;
            minSoFar = Math.min(minSoFar, nums[j]);
            if (nums[j] > minSoFar) {
                left = j;
            }
        }

        return right == -1 ? 0 : right - left + 1;
    }
}