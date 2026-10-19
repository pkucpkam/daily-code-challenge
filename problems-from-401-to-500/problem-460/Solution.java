import java.util.Arrays;

class Solution {
    /**
     * Best Solution: Thuật toán Tham lam (Greedy - Interval Scheduling).
     *
     * Tư tưởng cốt lõi: Sắp xếp các cặp theo điểm kết thúc (right) tăng dần.
     * Cặp nào kết thúc càng sớm thì càng để lại nhiều khoảng trống phía sau
     * cho các cặp tiếp theo, từ đó tối đa hóa độ dài chuỗi có thể tạo thành.
     *
     * Time Complexity: O(N log N) với N = pairs.length <= 1000 (thời gian sắp xếp).
     * Space Complexity: O(log N) do không gian ngăn xếp của thuật toán Dual-Pivot Quicksort.
     */
    public int findLongestChain(int[][] pairs) {
        // Sắp xếp các cặp tăng dần theo điểm kết thúc (right endpoint)
        Arrays.sort(pairs, (a, b) -> Integer.compare(a[1], b[1]));

        int count = 0;
        int curEnd = Integer.MIN_VALUE;

        for (int[] pair : pairs) {
            // Điều kiện để nối chuỗi: b < c (tức là pair[0] > curEnd)
            // Vì pair[0] >= -1000 > Integer.MIN_VALUE, cặp đầu tiên luôn được chọn an toàn
            if (pair[0] > curEnd) {
                count++;
                curEnd = pair[1];
            }
        }

        return count;
    }
}