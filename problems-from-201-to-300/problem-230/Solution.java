class Solution {
    public int maximumGap(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }

        int n = nums.length;
        int min = nums[0];
        int max = nums[0];

        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        if (min == max) {
            return 0;
        }

        int bucketSize = Math.max(1, (max - min) / (n - 1));
        int bucketCount = (max - min) / bucketSize + 1;

        int[][] buckets = new int[bucketCount][2];
        for (int i = 0; i < bucketCount; i++) {
            buckets[i][0] = Integer.MAX_VALUE; // min of bucket
            buckets[i][1] = Integer.MIN_VALUE; // max of bucket
        }

        for (int num : nums) {
            int idx = (num - min) / bucketSize;
            buckets[idx][0] = Math.min(buckets[idx][0], num);
            buckets[idx][1] = Math.max(buckets[idx][1], num);
        }

        int maxGap = 0;
        int prevMax = min;

        for (int i = 0; i < bucketCount; i++) {
            if (buckets[i][0] == Integer.MAX_VALUE) {
                continue;
            }
            maxGap = Math.max(maxGap, buckets[i][0] - prevMax);
            prevMax = buckets[i][1];
        }

        return maxGap;
    }
}