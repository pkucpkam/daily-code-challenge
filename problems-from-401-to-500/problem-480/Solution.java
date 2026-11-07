import java.util.Arrays;

class Solution {
    /**
     * Thuật toán Tối ưu: Quy hoạch động 2 Mảng (Dynamic Programming with Length and Count)
     *
     * Phân tích ý tưởng:
     * Ta mở rộng từ bài toán kinh điển LIS (Longest Increasing Subsequence) bằng cách
     * theo dõi đồng thời cả độ dài tối đa lẫn số lượng dãy con đạt độ dài đó:
     * 1. len[i]: Độ dài của dãy con tăng dài nhất kết thúc tại chỉ số i.
     * 2. count[i]: Số lượng dãy con tăng dài nhất (có độ dài len[i]) kết thúc tại i.
     *
     * Khởi tạo cơ sở:
     * Mỗi phần tử đơn lẻ nums[i] tự thân là một dãy con tăng độ dài 1:
     * - len[i] = 1
     * - count[i] = 1
     *
     * Công thức truy hồi:
     * Với mỗi vị trí i từ 0 đến n - 1, ta xét tất cả các vị trí j trước đó (0 <= j < i):
     * Nếu nums[j] < nums[i] (thỏa mãn điều kiện tăng ngặt):
     *   - Trường hợp 1: len[j] + 1 > len[i]
     *     Tìm thấy một chuỗi tăng dài hơn kết thúc tại i.
     *     -> Cập nhật len[i] = len[j] + 1 và kế thừa số cách count[i] = count[j].
     *   - Trường hợp 2: len[j] + 1 == len[i]
     *     Tìm thấy thêm các chuỗi tăng khác có cùng độ dài tối đa kết thúc tại i.
     *     -> Cộng dồn số cách: count[i] += count[j].
     *
     * Kết quả cuối cùng:
     * - Xác định maxLen = max(len[i]) trên toàn bộ mảng.
     * - Tổng hợp kết quả: cộng tất cả count[i] của các vị trí có len[i] == maxLen.
     *
     * Độ phức tạp:
     * - Thời gian: O(N^2) với N <= 2000, số phép so sánh tối đa ~ 2 * 10^6, thời gian chạy cực nhanh (~10-20 ms).
     * - Không gian bộ nhớ: O(N) để lưu trữ 2 mảng phụ trợ len và count.
     */
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return n;
        }

        int[] len = new int[n];
        int[] count = new int[n];
        Arrays.fill(len, 1);
        Arrays.fill(count, 1);

        int maxLen = 1;

        // Vòng lặp quy hoạch động
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    if (len[j] + 1 > len[i]) {
                        len[i] = len[j] + 1;
                        count[i] = count[j];
                    } else if (len[j] + 1 == len[i]) {
                        count[i] += count[j];
                    }
                }
            }
            if (len[i] > maxLen) {
                maxLen = len[i];
            }
        }

        // Tính tổng số lượng dãy con có độ dài lớn nhất
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (len[i] == maxLen) {
                result += count[i];
            }
        }

        return result;
    }
}
