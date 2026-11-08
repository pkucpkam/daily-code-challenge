class Solution {
    /**
     * Thuật toán Tối ưu: Quét một lần (One-Pass Linear Scan / Sliding Window)
     *
     * Phân tích ý tưởng:
     * - Dãy con tăng liên tục (Continuous Increasing Subsequence) thực chất là một mảng con (subarray)
     *   thỏa mãn điều kiện nums[i] < nums[i + 1] với mọi phần tử liền kề.
     * - Khi duyệt qua mảng từ trái sang phải:
     *   + Nếu phần tử hiện tại nums[i] lớn hơn phần tử đứng trước nums[i - 1], ta có thể mở rộng
     *     độ dài của chuỗi tăng hiện tại thêm 1 (curLen++).
     *   + Ngược lại, nếu nums[i] <= nums[i - 1], chuỗi tăng liên tục bị đứt gãy. Một chuỗi tăng mới
     *     bắt đầu tại chính vị trí i với độ dài ban đầu là 1 (curLen = 1).
     * - Ta liên tục duy trì độ dài lớn nhất ghi nhận được (maxLen).
     *
     * Độ phức tạp:
     * - Thời gian: O(N) với N là số lượng phần tử của mảng nums. Ta chỉ duyệt qua mảng đúng một lần.
     * - Không gian bộ nhớ: O(1) do chỉ sử dụng hai biến đếm nguyên thủy maxLen và curLen.
     */
    public int findLengthOfLCIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int maxLen = 1;
        int curLen = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                curLen++;
                if (curLen > maxLen) {
                    maxLen = curLen;
                }
            } else {
                curLen = 1;
            }
        }

        return maxLen;
    }
}
