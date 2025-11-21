import java.util.*;

class Solution {
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        Set<Integer> set = new HashSet<>();
        for (int num : nums) set.add(num);

        int best = 0;
        for (int num : set) {
            if (!set.contains(num - 1)) {
                int curr = 1;
                int x = num + 1;
                while (set.contains(x)) {
                    curr++;
                    x++;
                }
                best = Math.max(best, curr);
            }
        }
        return best;
    }
}