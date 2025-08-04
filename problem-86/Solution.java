class Solution {
    public int thirdMax(int[] nums) {
        Arrays.sort(nums);
        int count = 0;
        for (int i = nums.length - 1; i >= 0; i--) {
            count++;
            if (count == 3) {
                return nums[i];
            }
            while (i > 0 && nums[i] == nums[i - 1]) {
                i--;
            }
        }
        return nums[nums.length - 1];
    }
}