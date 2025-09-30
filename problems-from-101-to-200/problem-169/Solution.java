class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length <= 2) return nums.length;
        
        int writeIndex = 2; 
        
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] != nums[writeIndex - 2]) {
                nums[writeIndex] = nums[i];
                writeIndex++;
            }
        }
        
        return writeIndex;
    }
}