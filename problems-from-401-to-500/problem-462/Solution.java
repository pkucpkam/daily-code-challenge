import java.util.List;

class Solution {
    /**
     * Best Solution: Cây Tiền Tố (Trie - Prefix Tree) kết hợp quét chuỗi không cấp phát thừa (Zero-Allocation Scanning).
     *
     * Tư tưởng cốt lõi:
     * 1. Xây dựng cây Trie từ tập hợp các tiền tố (roots) trong dictionary:
     *    - Mỗi nút chứa mảng 26 con (tương ứng các chữ cái 'a' -> 'z') và cờ `isEnd`.
     * 2. Quét qua từng từ trong câu (sentence):
     *    - Thay vì dùng sentence.split(" ") (gây cấp phát hàng loạt chuỗi String rác trong heap),
     *      ta dùng hai con trỏ [start, end) quét trực tiếp trên chuỗi sentence gốc.
     *    - Với mỗi từ [start, end), duyệt từ ký tự đầu tiên đi xuống cây Trie.
     *    - Ngay khi gặp một nút có `isEnd == true`, ta dừng ngay lập tức và chọn tiền tố này
     *      vì duyệt từ trên xuống dưới (từ gốc xuống lá) đảm bảo tìm thấy tiền tố NGẮN NHẤT sớm nhất.
     *    - Nếu gặp con trỏ null (không có nhánh tiếp theo trong Trie), ta dừng sớm (Early Termination)
     *      và giữ nguyên từ gốc.
     * 3. Ghép kết quả bằng StringBuilder được khởi tạo trước dung lượng bằng sentence.length().
     *    - Sử dụng result.append(sentence, start, start + rootLen) giúp sao chép trực tiếp char array
     *      mà không tạo thêm bất kỳ đối tượng String trung gian nào.
     *
     * Time Complexity: O(D * L_root + S) trong đó:
     *   - D là số lượng từ trong dictionary (D <= 1000).
     *   - L_root là độ dài trung bình của root (L_root <= 100).
     *   - S là tổng độ dài chuỗi sentence (S <= 10^6).
     *   - Với mỗi từ trong sentence, ta chỉ duyệt trên Trie tối đa đến độ dài của root ngắn nhất (<= 100 bước),
     *     hoặc dừng ngay khi không khớp, do đó tổng thời gian xử lý toàn bộ câu là tuyến tính O(S).
     *
     * Space Complexity: O(D * L_root * 26) cho bộ nhớ cây Trie (tối đa ~1000 * 100 * 26 con trỏ),
     *   và O(S) cho StringBuilder lưu kết quả đầu ra. Không phát sinh chi phí Garbage Collection thừa.
     */
    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd = false;
    }

    public String replaceWords(List<String> dictionary, String sentence) {
        TrieNode root = new TrieNode();

        // Bước 1: Xây dựng cây Trie từ danh sách roots
        for (String word : dictionary) {
            TrieNode curr = root;
            int len = word.length();
            for (int i = 0; i < len; i++) {
                int idx = word.charAt(i) - 'a';
                if (curr.children[idx] == null) {
                    curr.children[idx] = new TrieNode();
                }
                curr = curr.children[idx];
            }
            curr.isEnd = true;
        }

        // Bước 2: Quét chuỗi sentence và thay thế từng từ bằng root ngắn nhất
        int n = sentence.length();
        StringBuilder result = new StringBuilder(n);
        int i = 0;

        while (i < n) {
            // Khoảng trắng giữa các từ
            if (sentence.charAt(i) == ' ') {
                result.append(' ');
                i++;
                continue;
            }

            // Xác định ranh giới của từ hiện tại: [start, end)
            int start = i;
            while (i < n && sentence.charAt(i) != ' ') {
                i++;
            }
            int end = i;

            // Tìm độ dài của root ngắn nhất khớp với từ
            int rootLen = findShortestRootLength(root, sentence, start, end);
            if (rootLen > 0) {
                result.append(sentence, start, start + rootLen);
            } else {
                result.append(sentence, start, end);
            }
        }

        return result.toString();
    }

    private int findShortestRootLength(TrieNode root, String sentence, int start, int end) {
        TrieNode curr = root;
        for (int i = start; i < end; i++) {
            int idx = sentence.charAt(i) - 'a';
            if (curr.children[idx] == null) {
                return -1; // Mismatch sớm: không có root nào là tiền tố của từ này
            }
            curr = curr.children[idx];
            if (curr.isEnd) {
                return i - start + 1; // Gặp root ngắn nhất đầu tiên -> Trả về độ dài ngay
            }
        }
        return -1; // Đã duyệt hết từ nhưng không gặp root kết thúc nào
    }
}