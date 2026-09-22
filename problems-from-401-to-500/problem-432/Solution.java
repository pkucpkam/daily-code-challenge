class Solution {
    /**
     * Best Solution: Giao của các hình chữ nhật (Intersection of Rectangles).
     *
     * Mọi phép toán [a, b] đều tăng giá trị trong hình chữ nhật từ (0, 0) đến (a - 1, b - 1).
     * Do ô (0, 0) luôn nằm trong tất cả các hình chữ nhật nên ô (0, 0) luôn đạt giá trị lớn nhất.
     * Để một ô bất kỳ (x, y) cũng đạt giá trị lớn nhất, ô đó phải nằm trong giao của TẤT CẢ các phép toán:
     *   0 <= x < min(a_i) và 0 <= y < min(b_i)
     *
     * Số lượng ô có giá trị lớn nhất chính là diện tích phần giao: minRow * minCol.
     *
     * Time Complexity: O(k) với k = ops.length (duyệt qua mảng ops đúng 1 lần).
     * Space Complexity: O(1) (không cấp phát thêm bộ nhớ).
     */
    public int maxCount(int m, int n, int[][] ops) {
        int minRow = m;
        int minCol = n;

        for (int[] op : ops) {
            minRow = Math.min(minRow, op[0]);
            minCol = Math.min(minCol, op[1]);
        }

        return minRow * minCol;
    }
}