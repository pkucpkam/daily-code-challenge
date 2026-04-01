class Solution {
    public int findKthLargest(int[] nums, int k) {
        final int OFFSET = 10000;
        final int SIZE = 20001;
        int[] freq = new int[SIZE];

        for (int num : nums) {
            freq[num + OFFSET]++;
        }

        int remain = k;
        for (int idx = SIZE - 1; idx >= 0; idx--) {
            remain -= freq[idx];
            if (remain <= 0) {
                return idx - OFFSET;
            }
        }

        return -1;
    }
}