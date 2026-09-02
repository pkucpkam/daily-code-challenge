class Solution {
    /**
     * Finds the maximum length of set s[k] formed by array nesting.
     * 
     * Time Complexity: O(N) - each element is visited at most twice.
     * Space Complexity: O(1) - visited elements marked in-place with -1.
     */
    public int arrayNesting(int[] nums) {
        int maxLength = 0;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            if (nums[i] != -1) {
                int count = 0;
                int curr = i;

                while (nums[curr] != -1) {
                    int next = nums[curr];
                    nums[curr] = -1; // Mark as visited
                    curr = next;
                    count++;
                }

                maxLength = Math.max(maxLength, count);
            }
        }

        return maxLength;
    }
}