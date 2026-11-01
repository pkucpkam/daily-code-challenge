class Solution {
    /**
     * Thuật toán Tối ưu: Tham lam một lượt (Single-pass Greedy)
     * 
     * Phân tích kỹ thuật:
     * - Khi gặp nghịch thế đầu tiên tại chỉ số i thỏa mãn nums[i] > nums[i + 1]:
     *   Ta bắt buộc phải sửa đổi một trong hai phần tử nums[i] hoặc nums[i + 1] để duy trì
     *   tính không giảm.
     * 
     * - Có 2 lựa chọn sửa đổi:
     *   1. Lựa chọn 1 (Ưu tiên): Hạ nums[i] xuống bằng nums[i + 1] (nums[i] = nums[i + 1]).
     *      + Điều kiện áp dụng: Chỉ thực hiện được nếu i == 0 hoặc nums[i - 1] <= nums[i + 1].
     *      + Lý do ưu tiên: Giữ cho phần tử nums[i + 1] càng nhỏ càng tốt, tạo điều kiện thuận lợi nhất
     *        cho các phần tử tương lai (nums[i + 2], nums[i + 3], ...).
     * 
     *   2. Lựa chọn 2 (Bắt buộc khi lựa chọn 1 không khả thi): Nâng nums[i + 1] lên bằng nums[i] (nums[i + 1] = nums[i]).
     *      + Xảy ra khi nums[i - 1] > nums[i + 1], nghĩa là không thể hạ nums[i] vì sẽ vi phạm nums[i - 1] <= nums[i].
     *      + Lúc này giải pháp duy nhất là phải nâng nums[i + 1] lên bằng nums[i].
     * 
     * - Nếu phát hiện nghịch thế thứ 2 trong quá trình duyệt, ta kết luận ngay là KHÔNG THỂ
     *   (trả về false).
     * 
     * Độ phức tạp:
     * - Thời gian: O(n) - Duyệt qua mảng đúng 1 lần.
     * - Không gian phụ trợ: O(1) - Chỉ dùng 1 biến đếm vi phạm, không tốn thêm bộ nhớ.
     */
    public boolean checkPossibility(int[] nums) {
        int count = 0;
        int n = nums.length;

        for (int i = 0; i < n - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                count++;
                // Nếu cần sửa nhiều hơn 1 phần tử -> không thể thỏa mãn
                if (count > 1) {
                    return false;
                }

                // Quyết định chiến lược sửa đổi tham lam
                if (i == 0 || nums[i - 1] <= nums[i + 1]) {
                    // Lựa chọn 1: Hạ nums[i] xuống bằng nums[i + 1]
                    nums[i] = nums[i + 1];
                } else {
                    // Lựa chọn 2: Nâng nums[i + 1] lên bằng nums[i]
                    nums[i + 1] = nums[i];
                }
            }
        }

        return true;
    }
}