import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    /**
     * Best Solution: Đệ quy Quay lui kết hợp Ghi nhớ (DFS Backtracking with Memoization)
     * kết hợp Nén trạng thái (Bitmask Encoding) và Tỉa nhánh (Branch and Bound Pruning).
     *
     * Time Complexity: O(M * S) trong đó:
     *   - M là số lượng gói ưu đãi hợp lệ sau khi lọc (M <= special.size() <= 100).
     *   - S là số lượng trạng thái nhu cầu (needs) có thể đạt tới.
     *     Với n <= 6 và needs[i] <= 10, trong thực tế số trạng thái tiếp cận được từ
     *     initialNeeds thường chỉ khoảng vài chục đến vài trăm trạng thái.
     *   - Nhờ việc nén trạng thái vào một số nguyên 32-bit (mỗi món dùng 4 bits),
     *     mọi thao tác trích xuất và trừ số lượng đều diễn ra trong O(1) thời gian bitwise.
     *
     * Space Complexity: O(S) + O(depth) - Lưu trữ bảng nhớ memo (Map<Integer, Integer>)
     *   và độ sâu ngăn xếp đệ quy (tối đa sum(needs) <= 60).
     */
    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        int n = price.size();

        // Bước 1: Tiền xử lý & lọc các gói ưu đãi thực sự có lợi
        List<List<Integer>> validList = new ArrayList<>();
        for (List<Integer> offer : special) {
            int normalPrice = 0;
            int totalItems = 0;
            boolean exceeds = false;

            for (int i = 0; i < n; i++) {
                int qty = offer.get(i);
                if (qty > needs.get(i)) {
                    exceeds = true;
                    break;
                }
                totalItems += qty;
                normalPrice += qty * price.get(i);
            }

            // Chỉ giữ lại gói:
            // 1. Không vượt quá số lượng cần mua ban đầu của bất kỳ món nào.
            // 2. Chứa ít nhất một món đồ (totalItems > 0).
            // 3. Giá gói ưu đãi phải rẻ hơn tổng giá mua lẻ các món đó.
            if (!exceeds && totalItems > 0 && offer.get(n) < normalPrice) {
                validList.add(offer);
            }
        }

        // Bước 2: Chuyển sang mảng nguyên thủy (primitive arrays) để tối ưu tốc độ truy xuất cache
        int m = validList.size();
        int[][] offers = new int[m][n];
        int[] offerPrices = new int[m];
        for (int i = 0; i < m; i++) {
            List<Integer> off = validList.get(i);
            for (int j = 0; j < n; j++) {
                offers[i][j] = off.get(j);
            }
            offerPrices[i] = off.get(n);
        }

        int[] prices = new int[n];
        int initialState = 0;
        for (int i = 0; i < n; i++) {
            prices[i] = price.get(i);
            // Nén mỗi số lượng nhu cầu needs[i] vào 4 bit (tối đa biểu diễn đến 15, needs[i] <= 10)
            initialState |= (needs.get(i) << (4 * i));
        }

        Map<Integer, Integer> memo = new HashMap<>();
        return dfs(initialState, offers, offerPrices, prices, n, memo);
    }

    private int dfs(int state, int[][] offers, int[] offerPrices, int[] prices, int n, Map<Integer, Integer> memo) {
        // Trạng thái cơ sở: Đã mua đủ tất cả các món đồ cần thiết
        if (state == 0) {
            return 0;
        }

        // Kiểm tra bảng nhớ
        if (memo.containsKey(state)) {
            return memo.get(state);
        }

        // Chi phí cận trên mặc định: Mua lẻ tất cả các món đồ còn lại theo giá niêm yết
        int minCost = 0;
        for (int i = 0; i < n; i++) {
            int need = (state >> (4 * i)) & 0xF;
            minCost += need * prices[i];
        }

        // Thử áp dụng từng gói ưu đãi hợp lệ
        for (int k = 0; k < offers.length; k++) {
            // Tỉa nhánh: Nếu riêng tiền mua gói này đã >= chi phí mua lẻ toàn bộ món đồ còn lại
            if (offerPrices[k] >= minCost) {
                continue;
            }

            int nextState = 0;
            boolean canApply = true;

            for (int i = 0; i < n; i++) {
                int need = (state >> (4 * i)) & 0xF;
                int qty = offers[k][i];

                // Nếu số lượng món đồ trong gói vượt quá số lượng cần mua còn lại
                if (qty > need) {
                    canApply = false;
                    break;
                }
                nextState |= ((need - qty) << (4 * i));
            }

            // Nếu áp dụng được gói ưu đãi, cập nhật chi phí tối thiểu
            if (canApply) {
                minCost = Math.min(minCost, offerPrices[k] + dfs(nextState, offers, offerPrices, prices, n, memo));
            }
        }

        memo.put(state, minCost);
        return minCost;
    }
}