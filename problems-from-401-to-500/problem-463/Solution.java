import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    /**
     * Best Solution: Tham lam kết hợp Hai Hàng Đợi (Greedy with Two Queues).
     *
     * Tư tưởng cốt lõi:
     * 1. Chiến thuật tối ưu (Greedy Strategy):
     *    - Mỗi nghị sĩ khi đến lượt bỏ phiếu nên cấm quyền của nghị sĩ đối phương CÓ LƯỢT BỎ PHIẾU GẦN NHẤT.
     *    - Việc cấm ngay đối thủ kế tiếp ngăn chặn đối thủ đó có cơ hội bỏ phiếu và cấm ngược lại đồng minh của mình.
     * 
     * 2. Quản lý vòng đấu bằng Hàng đợi (Queue with Time Offsets):
     *    - Dùng 2 hàng đợi lưu chỉ số (vị trí ban đầu) của các nghị sĩ:
     *      + `radiantQueue` lưu chỉ số các nghị sĩ phe Radiant ('R').
     *      + `direQueue` lưu chỉ số các nghị sĩ phe Dire ('D').
     *    - Ở mỗi lượt đối đầu:
     *      + Lấy ra 2 chỉ số ở đầu 2 hàng đợi: `rIndex` và `dIndex`.
     *      + Ai có chỉ số nhỏ hơn nghĩa là đến lượt trước trong vòng hiện tại.
     *      + Người đến trước sẽ cấm quyền người đến sau (người có chỉ số lớn hơn bị loại vĩnh viễn).
     *      + Người thắng sẽ được tham gia vòng tiếp theo bằng cách đưa lại vào hàng đợi với chỉ số mới là `index + n`.
     *        (Chỉ số `+ n` đảm bảo người này sẽ bỏ phiếu ở vòng sau, sau tất cả các nghị sĩ còn lại của vòng này).
     * 
     * 3. Kết thúc trò chơi:
     *    - Quá trình lặp lại cho đến khi một trong hai hàng đợi rỗng.
     *    - Phe nào còn người trong hàng đợi sẽ là phe chiến thắng.
     *
     * Time Complexity: O(n) trong đó n là độ dài chuỗi senate (n <= 10^4).
     *   - Mỗi vòng đối đầu, ít nhất một nghị sĩ bị loại bỏ vĩnh viễn.
     *   - Do có tối đa n nghị sĩ, tổng số lần so sánh và loại bỏ không vượt quá n bước.
     *
     * Space Complexity: O(n) để lưu trữ các chỉ số trong hai hàng đợi.
     */
    public String predictPartyVictory(String senate) {
        int n = senate.length();
        Queue<Integer> radiant = new ArrayDeque<>();
        Queue<Integer> dire = new ArrayDeque<>();

        // Bước 1: Khởi tạo hai hàng đợi lưu vị trí xuất hiện ban đầu của từng phe
        for (int i = 0; i < n; i++) {
            if (senate.charAt(i) == 'R') {
                radiant.offer(i);
            } else {
                dire.offer(i);
            }
        }

        // Bước 2: Mô phỏng các vòng bỏ phiếu
        while (!radiant.isEmpty() && !dire.isEmpty()) {
            int rIndex = radiant.poll();
            int dIndex = dire.poll();

            // Nghị sĩ nào đến lượt trước sẽ cấm quyền của nghị sĩ đến sau
            if (rIndex < dIndex) {
                // Radiant đi trước -> Cấm Dire (dIndex bị loại bỏ)
                // Radiant được quyền tham gia vòng kế tiếp với thứ tự rIndex + n
                radiant.offer(rIndex + n);
            } else {
                // Dire đi trước -> Cấm Radiant (rIndex bị loại bỏ)
                // Dire được quyền tham gia vòng kế tiếp với thứ tự dIndex + n
                dire.offer(dIndex + n);
            }
        }

        // Bước 3: Phe nào còn người thì phe đó chiến thắng
        return radiant.isEmpty() ? "Dire" : "Radiant";
    }
}