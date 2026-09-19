import java.util.HashMap;
import java.util.Map;

class Solution {
    public int findLHS(int[] nums) {
        Map<Integer, Integer> countMap = new HashMap<>();

        // Bước 1: Đếm số lần xuất hiện của từng số
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        int maxLen = 0;

        // Bước 2: Tìm cặp (key, key + 1) có tổng số lần xuất hiện lớn nhất
        for (int key : countMap.keySet()) {
            if (countMap.containsKey(key + 1)) {
                maxLen = Math.max(maxLen, countMap.get(key) + countMap.get(key + 1));
            }
        }

        return maxLen;
    }
}