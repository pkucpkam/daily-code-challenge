import java.util.Arrays;

class Solution {
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int i = 0;
        int j = nums.length - 1;
        int moves = 0;
        while (i < j) {
            moves += nums[j] - nums[i];
            i++;
            j--;
        }
        return moves;
    }
}