class Solution {
    /**
     * Best Solution: Duyệt mảng một lần (Single Pass / Linear Scan).
     * Tìm 3 số lớn nhất (max1 >= max2 >= max3) và 2 số nhỏ nhất (min1 <= min2).
     * 
     * Time Complexity: O(N) - Duyệt qua mảng nums đúng một lần duy nhất.
     * Space Complexity: O(1) - Sử dụng 5 biến số nguyên cố định, không dùng bộ nhớ phụ.
     */
    public int maximumProduct(int[] nums) {
        // Khởi tạo 3 giá trị lớn nhất: max1 >= max2 >= max3
        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        int max3 = Integer.MIN_VALUE;

        // Khởi tạo 2 giá trị nhỏ nhất: min1 <= min2
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;

        for (int n : nums) {
            // Cập nhật 3 giá trị lớn nhất
            if (n > max1) {
                max3 = max2;
                max2 = max1;
                max1 = n;
            } else if (n > max2) {
                max3 = max2;
                max2 = n;
            } else if (n > max3) {
                max3 = n;
            }

            // Cập nhật 2 giá trị nhỏ nhất
            if (n < min1) {
                min2 = min1;
                min1 = n;
            } else if (n < min2) {
                min2 = n;
            }
        }

        // Tích lớn nhất chỉ có thể đến từ 1 trong 2 trường hợp:
        // 1. Tích của 3 số lớn nhất: max1 * max2 * max3
        // 2. Tích của 2 số âm nhỏ nhất (âm * âm = dương lớn) và số lớn nhất: min1 * min2 * max1
        return Math.max(max1 * max2 * max3, min1 * min2 * max1);
    }
}