import java.util.Arrays;

class Solution {
    public boolean makesquare(int[] matchsticks) {
        if (matchsticks == null || matchsticks.length < 4) {
            return false;
        }
        
        int sum = 0;
        for (int matchstick : matchsticks) {
            sum += matchstick;
        }
        
        if (sum % 4 != 0) {
            return false;
        }
        
        int target = sum / 4;
        
        Arrays.sort(matchsticks);
        reverse(matchsticks);
        
        if (matchsticks[0] > target) {
            return false;
        }
        
        int[] sides = new int[4];
        return dfs(matchsticks, sides, 0, target);
    }
    
    private void reverse(int[] nums) {
        int i = 0, j = nums.length - 1;
        while (i < j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
            i++;
            j--;
        }
    }
    
    private boolean dfs(int[] matchsticks, int[] sides, int index, int target) {
        if (index == matchsticks.length) {
            return true;
        }
        
        for (int i = 0; i < 4; i++) {
            if (sides[i] + matchsticks[index] > target) {
                continue;
            }
            
            int j = 0;
            for (; j < i; j++) {
                if (sides[j] == sides[i]) {
                    break;
                }
            }
            if (j < i) {
                continue;
            }
            
            sides[i] += matchsticks[index];
            if (dfs(matchsticks, sides, index + 1, target)) {
                return true;
            }
            sides[i] -= matchsticks[index];
        }
        
        return false;
    }
}