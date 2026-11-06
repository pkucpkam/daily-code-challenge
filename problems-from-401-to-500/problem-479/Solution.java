class Solution {
    /**
     * Thuật toán Tối ưu: Phân tích Toán học & Thao tác Bit (Mathematical Reduction)
     * 
     * Các tính chất cốt lõi:
     * 1. Tính độc lập của thứ tự & Tính chẵn lẻ (Commutative & Parity):
     *    - Thao tác đảo trạng thái (XOR) có tính giao hoán và kết hợp: bấm nút A rồi B giống hệt B rồi A.
     *    - Bấm cùng một nút 2 lần sẽ triệt tiêu lẫn nhau (X ^ X = 0). Do đó mỗi nút chỉ có 2 trạng thái
     *      hiệu dụng: bấm 0 lần (chẵn) hoặc 1 lần (lẻ).
     * 
     * 2. Quan hệ phụ thuộc tuyến tính giữa các nút:
     *    - Nút 1 (đảo tất cả) = Nút 2 (đảo chẵn) ^ Nút 3 (đảo lẻ).
     *    - Do đó, nếu bấm cả 3 nút (1, 2, 3) cùng lúc, toàn bộ các bóng đèn sẽ không thay đổi trạng thái!
     * 
     * 3. Chu kỳ và Sự thu hẹp không gian bóng đèn:
     *    - Các thao tác có chu kỳ lần lượt là 1, 2, 2, 3. Chu kỳ chung là bội chung nhỏ nhất: BCNN(2, 3) = 6.
     *    - Trạng thái của 3 bóng đèn đầu tiên (1, 2, 3) đủ để phân biệt tất cả các trạng thái có thể có của toàn bộ n bóng đèn.
     *    - Vì vậy, với mọi n >= 3, ta chỉ cần xét n = 3 bóng đèn đầu tiên.
     * 
     * 4. Bảng quyết định hữu hạn theo (n, presses):
     *    - presses == 0: Luôn chỉ có 1 trạng thái (tất cả đều bật).
     *    - n == 1: Có 2 trạng thái [on] hoặc [off].
     *    - n == 2: presses == 1 có 3 trạng thái; presses >= 2 có 4 trạng thái.
     *    - n >= 3: presses == 1 có 4 trạng thái; presses == 2 có 7 trạng thái; presses >= 3 có 8 trạng thái.
     * 
     * Độ phức tạp:
     * - Thời gian: O(1) - Số phép toán hằng số.
     * - Không gian bộ nhớ: O(1) - Không sử dụng bộ nhớ phụ trợ.
     */
    public int flipLights(int n, int presses) {
        if (presses == 0) {
            return 1;
        }
        if (n == 1) {
            return 2;
        }
        if (n == 2) {
            return presses == 1 ? 3 : 4;
        }
        // Trường hợp n >= 3
        if (presses == 1) {
            return 4;
        }
        if (presses == 2) {
            return 7;
        }
        return 8;
    }
}
