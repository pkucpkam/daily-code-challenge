class Solution {
    public int rob(int[] nums) {
        int robPrevious = 0;
        int skipPrevious = 0;

        for (int money : nums) {
            int robCurrent = skipPrevious + money;
            int skipCurrent = Math.max(skipPrevious, robPrevious);

            robPrevious = robCurrent;
            skipPrevious = skipCurrent;
        }

        return Math.max(robPrevious, skipPrevious);
    }
}
