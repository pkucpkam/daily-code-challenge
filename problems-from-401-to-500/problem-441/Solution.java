import java.util.Arrays;

class Solution {
    /**
     * Best Solution: Sắp xếp + Kỹ thuật Hai con trỏ (Two Pointers).
     * 
     * Time Complexity: O(N^2)
     * Space Complexity: O(log N) do thuật toán Dual-Pivot Quicksort của Arrays.sort()
     */
    public int triangleNumber(int[] nums) {
        if (nums == null || nums.length < 3) {
            return 0;
        }

        // Bước 1: Sắp xếp mảng theo thứ tự tăng dần
        Arrays.sort(nums);

        int count = 0;
        int n = nums.length;

        // Bước 2: Cố định cạnh lớn nhất nums[k] từ phải qua trái (từ n - 1 về 2)
        for (int k = n - 1; k >= 2; k--) {
            int left = 0;
            int right = k - 1;

            // Bước 3: Dùng hai con trỏ tìm các cặp (left, right) sao cho nums[left] + nums[right] > nums[k]
            while (left < right) {
                if (nums[left] + nums[right] > nums[k]) {
                    // Do mảng đã được sắp xếp tăng dần:
                    // nums[left] + nums[right] > nums[k]
                    // => nums[left + 1] + nums[right] > nums[k]
                    // ...
                    // => nums[right - 1] + nums[right] > nums[k]
                    // Do đó, có tất cả (right - left) cặp hợp lệ kết hợp với nums[right]
                    count += (right - left);
                    right--; // Thu hẹp biên phải để xét các cặp cạnh khác
                } else {
                    left++; // Tổng hai cạnh <= cạnh lớn nhất -> cần tăng cạnh nhỏ hơn
                }
            }
        }

        return count;
    }
}