import java.util.HashMap;
import java.util.Map;

class Solution {
    public int findMaxLength(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        // Base case: prefix sum 0 at index -1
        map.put(0, -1);

        int maxLen = 0;
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            // Treat 1 as +1 and 0 as -1
            count += (nums[i] == 1) ? 1 : -1;

            if (map.containsKey(count)) {
                maxLen = Math.max(maxLen, i - map.get(count));
            } else {
                // Only store the first occurrence of each count to maximize length
                map.put(count, i);
            }
        }

        return maxLen;
    }
}