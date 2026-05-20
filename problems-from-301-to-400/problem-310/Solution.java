class Solution {
    public int wiggleMaxLength(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length < 2) {
            return nums.length;
        }
        
        int count = 1;
        int prediff = 0;
        
        for (int i = 1; i < nums.length; i++) {
            int curdiff = nums[i] - nums[i - 1];
            if ((curdiff > 0 && prediff <= 0) || (curdiff < 0 && prediff >= 0)) {
                count++;
                prediff = curdiff;
            }
        }
        
        return count;
    }
}