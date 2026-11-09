class MagicDictionary {
    /**
     * Thuật toán Tối ưu: Cây Tiền Tố (Trie) kết hợp Duyệt nhánh sai đúng 1 ký tự không đệ quy
     * (Iterative Trie 1-Edit Search)
     *
     * Phân tích ý tưởng:
     * - Một từ `w` trong từ điển khớp với `searchWord` sau khi thay đổi ĐÚNG 1 ký tự tại vị trí `i`
     *   (0 <= i < L) khi và chỉ khi:
     *   1. Tiền tố [0 .. i - 1] của `w` trùng khớp hoàn toàn với `searchWord[0 .. i - 1]` (trên đường `curr`).
     *   2. Tại vị trí `i`, `w` rẽ sang một nhánh con `k != (searchWord[i] - 'a')` tồn tại trong Trie.
     *   3. Hậu tố [i + 1 .. L - 1] từ nhánh `k` trùng khớp hoàn toàn với `searchWord[i + 1 .. L - 1]`
     *      và dừng tại một nút có `isEnd == true`.
     *
     * - Cơ chế khử đệ quy (Zero Recursion Overhead):
     *   + Thay vì gọi DFS đệ quy ở mỗi bước, ta dùng một vòng lặp duyệt `curr` dọc theo tiền tố
     *     khớp chính xác của `searchWord`.
     *   + Tại mỗi vị trí `i`, thử tất cả các nhánh con `k != origIdx` đang tồn tại và kiểm tra tuyến tính
     *     phần hậu tố còn lại qua `exactMatch` (không cần rẽ nhánh thêm lần nào nữa).
     *   + Sau đó tiến `curr = curr.children[origIdx]`; nếu `curr == null` thì dừng sớm (Early Termination)
     *     vì không thể khớp tiền tố dài hơn nữa.
     *
     * Độ phức tạp:
     * - buildDict: O(N * L) với N là số từ trong dictionary, L là độ dài trung bình của mỗi từ.
     * - search: O(Sigma * L^2) trong trường hợp xấu nhất (Sigma = 26, hoàn toàn độc lập với N).
     * - Không gian bộ nhớ: O(N * L * Sigma) cho cây Trie (các tiền tố chung được nén dùng chung nút)
     *   và O(1) bộ nhớ ngăn xếp do không dùng đệ quy.
     */
    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd;
    }

    private TrieNode root;

    public MagicDictionary() {
        this.root = new TrieNode();
    }

    public void buildDict(String[] dictionary) {
        this.root = new TrieNode();
        if (dictionary == null) {
            return;
        }
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
    }

    public boolean search(String searchWord) {
        if (searchWord == null || searchWord.isEmpty()) {
            return false;
        }

        TrieNode curr = root;
        int len = searchWord.length();

        for (int i = 0; i < len; i++) {
            int origIdx = searchWord.charAt(i) - 'a';

            // Thử đổi ký tự tại vị trí i sang mọi nhánh k != origIdx có trong Trie
            for (int k = 0; k < 26; k++) {
                if (k != origIdx && curr.children[k] != null) {
                    if (exactMatch(curr.children[k], searchWord, i + 1)) {
                        return true;
                    }
                }
            }

            // Giữ nguyên ký tự tại vị trí i để xét các vị trí thay đổi phía sau
            curr = curr.children[origIdx];
            if (curr == null) {
                return false; // Dừng sớm: không còn từ nào trong Trie giữ được tiền tố [0 .. i]
            }
        }

        return false;
    }

    private boolean exactMatch(TrieNode node, String word, int start) {
        int len = word.length();
        TrieNode curr = node;
        for (int i = start; i < len; i++) {
            int idx = word.charAt(i) - 'a';
            curr = curr.children[idx];
            if (curr == null) {
                return false;
            }
        }
        return curr.isEnd;
    }
}

/**
 * Your MagicDictionary object will be instantiated and called as such:
 * MagicDictionary obj = new MagicDictionary();
 * obj.buildDict(dictionary);
 * boolean param_2 = obj.search(searchWord);
 */
