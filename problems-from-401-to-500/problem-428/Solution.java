import java.util.Arrays;

class Solution {
    /**
     * Best Solution: Tính bình phương khoảng cách giữa tất cả 6 cặp điểm,
     * sau đó sắp xếp và kiểm tra tính chất hình học của hình vuông.
     *
     * Time Complexity: O(1) - chỉ có 4 điểm, 6 khoảng cách.
     * Space Complexity: O(1) - mảng độ dài cố định 6 phần tử nguyên thủy.
     */
    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        int[] d = {
            dist(p1, p2),
            dist(p1, p3),
            dist(p1, p4),
            dist(p2, p3),
            dist(p2, p4),
            dist(p3, p4)
        };

        Arrays.sort(d);

        // Một hình vuông hợp lệ khi và chỉ khi:
        // 1. Độ dài cạnh phải > 0: d[0] > 0 (loại trừ trường hợp các điểm trùng nhau)
        // 2. 4 cạnh có độ dài bằng nhau: d[0] == d[1] == d[2] == d[3]
        // 3. 2 đường chéo có độ dài bằng nhau: d[4] == d[5]
        // 4. Quan hệ Pythagoras: đường chéo^2 = 2 * cạnh^2 (d[4] == 2 * d[0])
        return d[0] > 0
            && d[0] == d[1]
            && d[1] == d[2]
            && d[2] == d[3]
            && d[4] == d[5]
            && d[4] == 2 * d[0];
    }

    /**
     * Tính bình phương khoảng cách Euclid giữa 2 điểm a và b:
     * dist^2 = (a.x - b.x)^2 + (a.y - b.y)^2
     * Không dùng Math.sqrt() để tránh sai số số thực và tối ưu tốc độ.
     */
    private int dist(int[] a, int[] b) {
        int dx = a[0] - b[0];
        int dy = a[1] - b[1];
        return dx * dx + dy * dy;
    }
}