# Word Ladder II

## Yêu cầu bài toán

- Cho hai từ `beginWord` và `endWord`, cùng với một từ điển `wordList`.
- Một chuỗi biến đổi từ `beginWord` đến `endWord` là một danh sách các từ sao cho:
  - Hai từ liền kề chỉ khác nhau đúng 1 chữ cái.
  - Mọi từ trong chuỗi (ngoại trừ `beginWord`) đều phải có mặt trong `wordList`.
- Trả về **tất cả các chuỗi biến đổi ngắn nhất** từ `beginWord` đến `endWord`. Nếu không có chuỗi nào, trả về danh sách rỗng.

## Ý tưởng cốt lõi

Bài toán yêu cầu tìm các đường đi **ngắn nhất** giữa `beginWord` và `endWord` trên một đồ thị (mỗi từ là 1 đỉnh, có cạnh nối nếu chúng khác nhau đúng 1 chữ cái).
- Để tìm khoảng cách ngắn nhất trên đồ thị không trọng số, Thuật toán BFS (Breadth-First Search) là công cụ phù hợp nhất.
- Tuy nhiên do không chỉ cần tìm độ dài mà còn phải trả về **tất cả** chuỗi đường đi cấu thành nên đường ngắn nhất đó, ta kết hợp thêm kỹ thuật lưu vết đỉnh cha (Parents/Predecessors) kết hợp với DFS (Depth-First Search) ở bước cuối để truy vết.

## Thuật toán

1. **Sinh đồ thị qua BFS**:
   - Dùng một tập `visited` thay vì chỉ đánh dấu từng nốt, ta thu thập một tập hợp các từ đã được đưa vào cấp (level) hiện tại.
   - Khi tạo một tầng (level) mới của đồ thị thông qua BFS, tạo cạnh ngược (lưu lại danh sách các "đỉnh tiền mặt" `preds`) cho mỗi từ hợp lệ. Từ hiện tại `nei` sẽ thêm từ gốc `word` vào danh sách `preds`.
   - Kết thúc BFS ngay khi một level mà chúng ta sinh ra có chứa `endWord` (vì nó là khoảng cách ngắn nhất, ta không cần đi sâu thêm vào cấp sau nữa).

2. **Truy vết cấu hình bằng DFS**:
   - Khởi tạo mảng đường đi `path` chứa `endWord`.
   - Gọi đệ quy DFS. Tại mỗi từ, duyệt lại danh sách các đỉnh tiền nhiệm (`p` trong `preds`).
   - DFS đi lùi từ `endWord`, chèn vào `path`, truy vết cho tới khi gặp được `beginWord`. Khi chạm `beginWord`, đảo ngược `path` rồi lưu thành kết quả. Do đi lùi nên mảng kết quả sẽ ngược, cần `Collections.reverse(path)`.

## Vì sao đúng?

- BFS luôn lan tỏa tìm kiếm theo các lớp (level) đồng tâm quanh `beginWord`. Vì thế, lần đầu tiên ta chạm tới `endWord`, ta chắc chắn số bước đã bước là ít nhất.
- Việc tạo đồ thị ngược `preds` trong lúc duyệt BFS chỉ trên chu trình ngắn nhất giúp tránh việc bị lặp đồ thị và dẽ dàng sinh mọi đường đi mà không bỏ sót nghiệm nào qua DFS đi giật lùi.

## Độ phức tạp

- Thời gian: Việc sinh cấp trong lúc BFS mất `O(N * L * 26)` với $N$ là số từ trong mảng, $L$ là độ dài chuỗi (rất nhỏ <= 5). Hàm DFS tuỳ thuộc số lượng đường đi ngắn nhất, xấu nhất là $O(V + E)$ (với biểu đồ dày).
- Không gian phụ: `O(N * L)` do phải chứa String vào HashSet, mảng Preds cho các node và hệ thống đệ quy (Stack Call) bằng với độ dài đường đi ngắn nhất.

## Code (Java)

```java
import java.util.*;

class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) return Collections.emptyList();

        Map<String, List<String>> preds = new HashMap<>(); 
        preds.put(beginWord, new ArrayList<>());

        Set<String> current = new HashSet<>();
        current.add(beginWord);

        boolean found = false;
        Set<String> visited = new HashSet<>();

        while (!current.isEmpty() && !found) {
            Set<String> nextLevel = new HashSet<>();
            visited.addAll(current);

            for (String word : current) {
                char[] chs = word.toCharArray();
                for (int i = 0; i < chs.length; i++) {
                    char old = chs[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == old) continue;
                        chs[i] = c;
                        String nei = new String(chs);
                        if (!dict.contains(nei) || visited.contains(nei)) continue;
                        preds.computeIfAbsent(nei, k -> new ArrayList<>()).add(word);
                        nextLevel.add(nei);
                        if (nei.equals(endWord)) found = true;
                    }
                    chs[i] = old;
                }
            }
            current = nextLevel;
        }

        List<List<String>> res = new ArrayList<>();
        if (!found) return res;

        LinkedList<String> path = new LinkedList<>();
        path.add(endWord);
        dfsBuild(endWord, beginWord, preds, path, res);
        return res;
    }

    private void dfsBuild(String word, String beginWord, Map<String, List<String>> preds,
                          LinkedList<String> path, List<List<String>> res) {
        if (word.equals(beginWord)) {
            List<String> one = new ArrayList<>(path);
            Collections.reverse(one);
            res.add(one);
            return;
        }
        List<String> prevs = preds.get(word);
        if (prevs == null) return;
        for (String p : prevs) {
            path.addLast(p);
            dfsBuild(p, beginWord, preds, path, res);
            path.removeLast();
        }
    }
}
```
