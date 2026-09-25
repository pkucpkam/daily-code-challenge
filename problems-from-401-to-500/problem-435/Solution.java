class Solution {
    /**
     * Best Solution: Thuật toán Tham lam (Greedy) kết hợp Tối ưu bước nhảy (Jump Optimization).
     * 
     * Nguyên lý:
     * - Tại vị trí i:
     *   + Nếu flowerbed[i] == 1: Vị trí i + 1 chắc chắn không thể trồng, nhảy ngay sang i += 2.
     *   + Nếu flowerbed[i] == 0:
     *     - Nếu vị trí tiếp theo (i + 1) cũng là 0 (hoặc i là vị trí cuối cùng):
     *       Ta có thể trồng hoa ngay tại i! Giảm n đi 1.
     *       Nếu n <= 0, ta có thể kết thúc sớm và trả về true.
     *       Do đã trồng tại i, vị trí i + 1 bị khóa, ta nhảy ngay sang i += 2.
     *     - Nếu vị trí tiếp theo (i + 1) là 1:
     *       Ta không thể trồng tại i, và vì tại i + 1 đã có hoa nên i + 2 cũng bị khóa,
     *       ta có thể nhảy cóc thẳng sang i += 3.
     *
     * Ưu điểm:
     * - Không sửa đổi (mutate) mảng dữ liệu đầu vào.
     * - Giảm số bước lặp (trung bình chỉ duyệt N/2 đến N/3 phần tử).
     * - Early-exit ngay khi n <= 0.
     * 
     * Time Complexity: O(N) - duyệt tối đa N phần tử, thực tế nhanh hơn nhiều nhờ bước nhảy.
     * Space Complexity: O(1) - chỉ dùng biến con trỏ, không cấp phát bộ nhớ phụ.
     */
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        if (n <= 0) {
            return true;
        }

        int length = flowerbed.length;
        int i = 0;

        while (i < length) {
            if (flowerbed[i] == 1) {
                // Đã có hoa tại i -> vị trí i + 1 không được trồng -> nhảy sang i + 2
                i += 2;
            } else if (i == length - 1 || flowerbed[i + 1] == 0) {
                // flowerbed[i] == 0 và vị trí kế tiếp cũng là 0 (hoặc cuối mảng)
                // -> Trồng hoa tại i!
                n--;
                if (n <= 0) {
                    return true;
                }
                // Sau khi trồng tại i -> vị trí i + 1 bị khóa -> nhảy sang i + 2
                i += 2;
            } else {
                // flowerbed[i] == 0 nhưng flowerbed[i + 1] == 1
                // -> Không thể trồng tại i, và hoa tại i + 1 sẽ khóa luôn vị trí i + 2 -> nhảy sang i + 3
                i += 3;
            }
        }

        return n <= 0;
    }
}