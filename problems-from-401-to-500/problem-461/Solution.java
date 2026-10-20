class Solution {
    /**
     * Best Solution: Kỹ thuật Hai con trỏ mở rộng từ tâm (Expand Around Center).
     *
     * Tư tưởng cốt lõi: Mỗi chuỗi con đối xứng (palindrome) đều đối xứng qua một tâm.
     * Một chuỗi có độ dài N sẽ có 2N - 1 tâm khả dĩ:
     * - N tâm đơn lẻ tại vị trí các ký tự (i, i) cho các chuỗi đối xứng có độ dài lẻ.
     * - N - 1 tâm nằm giữa hai ký tự liền kề (i, i + 1) cho các chuỗi đối xứng có độ dài chẵn.
     *
     * Với mỗi tâm, ta dùng hai con trỏ mở rộng sang hai phía (trái và phải) chừng nào
     * hai ký tự còn bằng nhau. Mỗi bước mở rộng thành công ghi nhận thêm một chuỗi con đối xứng mới.
     *
     * Time Complexity: O(N^2) trong trường hợp xấu nhất (chuỗi toàn ký tự giống nhau như "aaaa"),
     * nhưng trong thực tế chạy cực nhanh nhờ cơ chế dừng sớm (Early Termination) khi gặp ký tự không khớp.
     * Space Complexity: O(1) auxiliary space (hoặc O(N) để lưu mảng char[] giúp tăng tốc độ truy xuất).
     */
    public int countSubstrings(String s) {
        int n = s.length();
        int count = 0;
        char[] chars = s.toCharArray();

        for (int i = 0; i < n; i++) {
            // Trường hợp 1: Mở rộng từ tâm lẻ (độ dài lẻ, tâm tại vị trí i)
            count += expand(chars, i, i);

            // Trường hợp 2: Mở rộng từ tâm chẵn (độ dài chẵn, tâm nằm giữa i và i + 1)
            count += expand(chars, i, i + 1);
        }

        return count;
    }

    private int expand(char[] chars, int left, int right) {
        int count = 0;
        while (left >= 0 && right < chars.length && chars[left] == chars[right]) {
            count++;
            left--;
            right++;
        }
        return count;
    }
}