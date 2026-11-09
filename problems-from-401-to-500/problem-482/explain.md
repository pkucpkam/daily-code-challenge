# 📚 Giải thích Chi tiết Bài 676 - Implement Magic Dictionary

**Mục tiêu:** Thiết kế cấu trúc dữ liệu `MagicDictionary` hỗ trợ hai thao tác:
- `buildDict(dictionary)`: Khởi tạo từ điển từ mảng các chuỗi phân biệt.
- `search(searchWord)`: Trả về `true` nếu có thể **thay đổi chính xác 1 ký tự** trong `searchWord` để biến nó thành một từ nằm trong từ điển; ngược lại trả về `false`.

---

## 🎯 Phân tích bài toán

### 1. Đặc điểm đầu vào & Các ràng buộc (Constraints)

- `1 <= dictionary.length <= 100`: Số lượng từ $N$ tối đa là $100$.
- `1 <= dictionary[i].length <= 100` và `1 <= searchWord.length <= 100`: Độ dài $L$ tối đa của mỗi từ là $100$.
- Chỉ gồm các chữ cái tiếng Anh viết thường (`'a'` đến `'z'`, bảng chữ cái kích thước $\Sigma = 26$).
- Tất cả các từ trong `dictionary` đều **phân biệt (distinct)**.
- `buildDict` chỉ được gọi **đúng 1 lần** trước khi gọi `search`, và có tối đa $100$ lần gọi `search`.

---

### 2. Hai "cái bẫy" kinh điển cần tránh

1. **Không được thêm hay xóa ký tự:**
   - Đề bài yêu cầu *thay đổi chính xác 1 ký tự*. Do đó, từ trong từ điển khớp với `searchWord` bắt buộc phải có **cùng độ dài** (`word.length() == searchWord.length()`).
   - Ví dụ: `"hell"` và `"leetcoded"` không thể biến thành `"hello"` hay `"leetcode"`.
2. **Nếu `searchWord` đã nằm sẵn trong từ điển thì chưa chắc kết quả là `false`!**
   - Đúng là không được giữ nguyên $0$ thay đổi (`search("hello")` với `["hello"]` phải trả về `false`).
   - Tuy nhiên, nếu từ điển là `["hello", "hallo"]` và ta gọi `search("hello")`, ta có thể đổi `'e'` $\to$ `'a'` để tạo thành `"hallo"` (vốn có trong từ điển) $\implies$ kết quả phải là **`true`**!
   - ⚠️ **Sai lầm phổ biến:** Viết `if (dictSet.contains(searchWord)) return false;` ở ngay đầu hàm `search` sẽ làm sai test case này!

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Cây Tiền Tố (Trie) kết hợp Duyệt nhánh sai 1 ký tự không đệ quy

### 💡 Ý tưởng đột phá (Key Insights)

Bất kỳ từ `w` nào trong từ điển khớp với `searchWord` sau khi thay đổi **đúng 1 ký tự** tại vị trí $i$ ($0 \le i < L$) đều bắt buộc phải có cấu trúc gồm **3 phần**:

1. **Tiền tố khớp chính xác:** `w[0 .. i - 1] == searchWord[0 .. i - 1]` (đi theo đúng một đường từ `root` xuống nút `curr`).
2. **Điểm rẽ nhánh duy nhất tại vị trí $i$:** Từ nút `curr`, tồn tại một nhánh con `k` sao cho `k != searchWord[i] - 'a'` (đảm bảo **có đúng 1 ký tự bị thay đổi** tại $i$).
3. **Hậu tố khớp chính xác:** Từ nhánh con `curr.children[k]`, đoạn còn lại `searchWord[i + 1 .. L - 1]` phải khớp **100%** với đường đi trong Trie và dừng tại một nút có `isEnd == true`.

#### Tại sao khử đệ quy (Iterative 1-Edit Search) lại tối ưu hơn DFS thông thường?
- Trong bài toán cho phép sai **tối đa 1 ký tự**, một khi bạn đã sử dụng quyền "đổi ký tự" tại vị trí $i$, phần hậu tố $[i + 1 .. L - 1]$ **không còn được phép rẽ nhánh nữa**.
- Thay vì gọi đệ quy DFS tạo nhiều stack frame trên bộ nhớ, ta chỉ cần:
  - Một vòng lặp duy nhất đưa con trỏ `curr` đi dọc theo tiền tố khớp chính xác của `searchWord`.
  - Tại mỗi vị trí $i$, thử mọi nhánh con `k != origIdx` đang khác `null` và kiểm tra tuyến tính phần hậu tố còn lại bằng hàm `exactMatch`.
  - Sau đó bước tiếp `curr = curr.children[origIdx]`. Nếu `curr == null`, ta **dừng sớm (Early Termination)** vì không còn từ nào trong Trie khớp được tiền tố dài hơn nữa!
- **Khả năng mở rộng (Scalability):** Ngay cả khi $N$ tăng lên $10^6$ từ trong câu hỏi mở rộng phỏng vấn, thời gian `search` vẫn bị chặn trên bởi $\mathcal{O}(\Sigma \cdot L^2)$ — **hoàn toàn độc lập với số lượng từ $N$**!

---

### 📊 Sơ đồ minh họa trực quan (Visual Walkthrough)

Giả sử `dictionary = ["hello", "hallo", "leetcode"]`:

```text
                      (root)
                    /        \
                  'h'        'l'
                  / \          \
                'a' 'e'        'e'
                /     \          \
              'l'     'l'        'e'
              /         \          \
            'l'         'l'        't' -> 'c' -> 'o' -> 'd' -> 'e'*
            /             \
          'o'*            'o'*
       ("hallo")       ("hello")
```

#### Trace các truy vấn tiêu biểu:

1. **`search("hhllo")`:**
   - **$i = 0$ (`'h'`):**
     - `curr = root`. Thử nhánh khác `'h'` có `'l'` (`"leetcode"`).
     - `exactMatch(node('l'), "hhllo", 1)`: Ký tự kế tiếp trong Trie là `'e'`, nhưng trong `"hhllo"` là `'h'` $\implies$ thất bại ngay ở bước 1.
     - Đi tiếp theo ký tự gốc: `curr = root.children['h']`.
   - **$i = 1$ (`'h'`):**
     - Thử nhánh `k = 'a'`: `exactMatch(node('a'), "hhllo", 2)` khớp `'l' -> 'l' -> 'o'*` $\implies$ **Thành công! Trả về `true` ngay lập tức!** (Hoặc khớp qua nhánh `'e'` tùy thứ tự duyệt `'a'` trước `'e'`).

2. **`search("hello")` (khi trong từ điển có cả `"hello"` và `"hallo"`):**
   - **$i = 0$ (`'h'`):** Thử `'l'` $\implies$ thất bại; đi tiếp `curr = node('h')`.
   - **$i = 1$ (`'e'`):** Thử `k = 'a'` (vì `'a' != 'e'` và `curr.children['a'] != null`).
     - `exactMatch(node('a'), "hello", 2)` khớp `"llo"` và `isEnd == true` $\implies$ **Trả về `true`!** (Đổi `'e'` $\to$ `'a'` khớp `"hallo"`).

3. **`search("hello")` (khi từ điển chỉ có `["hello", "leetcode"]`):**
   - Tại $i = 0 \dots 4$, tại mỗi `curr` chỉ tồn tại đúng ký tự khớp với `searchWord[i]` (ngoại trừ ở `root` có `'l'` nhưng `exactMatch` thất bại).
   - Vì ta chỉ rẽ nhánh khi `k != origIdx`, đường khớp chính xác `"hello"` (0 thay đổi) không bao giờ được kiểm tra qua `exactMatch`.
   - Kết thúc vòng lặp $\implies$ **Trả về `false`**.

4. **`search("leetcoded")`:**
   - $i = 0 \dots 7$: Đi dọc nhánh `"leetcode"`.
   - $i = 8$ (`'d'`): `curr` đang ở nút `'e'*` của `"leetcode"`. Không có nhánh con nào khác; `curr = curr.children['d'] == null` $\implies$ **Dừng sớm, trả về `false`**.

---

### 💻 Mã nguồn Java (Best Solution)

Đoạn mã được cài đặt hoàn chỉnh trong [`Solution.java`](file:///f:/daily-code-challenge/problems-from-401-to-500/problem-482/Solution.java):

```java
class MagicDictionary {
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
```

---

### 🔍 Giải thích từng dòng lệnh chi tiết (Line-by-Line Walkthrough)

1. **`TrieNode` (`children[26]`, `isEnd`):**
   - Mỗi nút lưu mảng 26 con trỏ đại diện cho `'a' .. 'z'` (truy cập mảng $\mathcal{O}(1)$, thân thiện CPU cache hơn `HashMap<Character, TrieNode>`) và cờ `isEnd` đánh dấu điểm kết thúc một từ hợp lệ.
2. **`buildDict(String[] dictionary)`:**
   - Khởi tạo lại `root = new TrieNode()` để đảm bảo an toàn nếu hàm được gọi lại, sau đó chèn tuần tự từng từ trong `dictionary` vào cây Trie.
3. **`for (int i = 0; i < len; i++)` trong `search`:**
   - Biến `i` đại diện cho **vị trí duy nhất mà ta thử thay đổi ký tự**. Trước khi vào bước `i`, `curr` luôn trỏ tới nút tương ứng với tiền tố `searchWord[0 .. i - 1]`.
4. **`for (int k = 0; k < 26; k++)` với `k != origIdx && curr.children[k] != null`:**
   - Ràng buộc `k != origIdx` đảm bảo ký tự tại vị trí `i` **chắc chắn bị thay đổi**.
   - Nếu tồn tại nhánh `k` trong Trie, ta kiểm tra xem phần còn lại `searchWord[i + 1 .. len - 1]` có khớp chính xác kể từ `curr.children[k]` hay không.
5. **`curr = curr.children[origIdx]; if (curr == null) return false;`:**
   - Để thử thay đổi tại các vị trí $i + 1, i + 2, \dots$, tiền tố $[0 .. i]$ bắt buộc phải giữ nguyên. Nếu không có đường đi cho `origIdx`, không thể tồn tại từ nào sai khác ở các vị trí sau $\implies$ dừng sớm ngay!
6. **`exactMatch(TrieNode node, String word, int start)`:**
   - Duyệt tuyến tính từ `start` đến cuối `word`. Nếu gặp `null` giữa chừng thì trả về `false`; nếu đi đến hết chuỗi thì kiểm tra `curr.isEnd` để chắc chắn đây là một từ trọn vẹn trong từ điển (chứ không phải tiền tố của một từ dài hơn).

---

## 🔄 Các hướng tiếp cận khác (Alternative Approaches)

### Cách 1: Trie + DFS đệ quy (Recursive Trie + DFS)

#### Tư duy:
- Xây dựng Trie tương tự như trên.
- Trong hàm `search`, gọi hàm đệ quy `dfs(node, word, index, modified)`:
  - Nếu `index == word.length()`: trả về `modified && node.isEnd` (bắt buộc đã đổi đúng 1 lần và đang đứng ở cuối một từ).
  - Nếu đã đổi rồi (`modified == true`): chỉ đi tiếp đúng nhánh `word.charAt(index) - 'a'`.
  - Nếu chưa đổi (`modified == false`): thử đi tiếp nhánh gốc với `modified = false`, hoặc rẽ sang bất kỳ nhánh khác với `modified = true`.

#### Mã nguồn minh họa:
```java
class MagicDictionaryDFS {
    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd;
    }

    private final TrieNode root = new TrieNode();

    public void buildDict(String[] dictionary) {
        for (String word : dictionary) {
            TrieNode curr = root;
            for (int i = 0; i < word.length(); i++) {
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
        return dfs(root, searchWord, 0, false);
    }

    private boolean dfs(TrieNode node, String word, int idx, boolean modified) {
        if (idx == word.length()) {
            return modified && node.isEnd;
        }

        int charIdx = word.charAt(idx) - 'a';
        if (modified) {
            return node.children[charIdx] != null
                && dfs(node.children[charIdx], word, idx + 1, true);
        }

        // Nhánh 1: Giữ nguyên ký tự hiện tại
        if (node.children[charIdx] != null
                && dfs(node.children[charIdx], word, idx + 1, false)) {
            return true;
        }

        // Nhánh 2: Thay thế ký tự hiện tại bằng 1 trong 25 ký tự còn lại
        for (int k = 0; k < 26; k++) {
            if (k != charIdx && node.children[k] != null) {
                if (dfs(node.children[k], word, idx + 1, true)) {
                    return true;
                }
            }
        }
        return false;
    }
}
```

#### Đánh giá:
- Cùng độ phức tạp thuật toán với Best Solution, trực quan và dễ hiểu, nhưng tốn thêm $\mathcal{O}(L)$ bộ nhớ ngăn xếp gọi hàm (Call Stack) cho đệ quy.

---

### Cách 2: Gom nhóm theo độ dài từ (Length-Bucketed Linear Scan)

#### Tư duy:
- Hai từ chỉ có thể biến đổi cho nhau bằng 1 phép thay thế khi chúng **có cùng độ dài**.
- Lưu từ điển vào `Map<Integer, List<String>>` (hoặc mảng `List<String>[101]`) theo khóa `word.length()`.
- Khi `search(searchWord)`, ta chỉ duyệt qua các từ có đúng độ dài `searchWord.length()`, đếm số vị trí khác nhau `diff`. Nếu `diff > 1` thì `break` sớm; nếu hết chuỗi mà `diff == 1` thì trả về `true`.

#### Mã nguồn minh họa:
```java
import java.util.*;

class MagicDictionaryBuckets {
    private final Map<Integer, List<String>> buckets = new HashMap<>();

    public void buildDict(String[] dictionary) {
        buckets.clear();
        for (String word : dictionary) {
            buckets.computeIfAbsent(word.length(), k -> new ArrayList<>()).add(word);
        }
    }

    public boolean search(String searchWord) {
        List<String> candidates = buckets.get(searchWord.length());
        if (candidates == null) {
            return false;
        }

        for (String word : candidates) {
            int diff = 0;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) != searchWord.charAt(i)) {
                    diff++;
                    if (diff > 1) {
                        break; // Dừng sớm ngay khi sai khác >= 2 ký tự
                    }
                }
            }
            if (diff == 1) {
                return true;
            }
        }
        return false;
    }
}
```

#### Đánh giá:
- **Ưu điểm:** Code cực ngắn, không tốn bộ nhớ dựng Trie, không cấp phát chuỗi mới. Với ràng buộc nhỏ của đề ($N \le 100, L \le 100$), đây là cách thực dụng rất nhanh.
- **Nhược điểm:** Độ phức tạp `search` là $\mathcal{O}(N \cdot L)$. Nếu phỏng vấn đặt giả thiết $N = 10^6$ từ có cùng độ dài, mỗi lần `search` phải duyệt tuyến tính toàn bộ danh sách $\implies$ quá chậm!

---

### Cách 3: Bảng băm mẫu tổng quát (Generalized Neighbors / Wildcard HashMap)

#### Tư duy:
- Với mỗi từ trong `dictionary`, thay thế từng vị trí bằng ký tự đại diện `'*'` để tạo ra $L$ mẫu tổng quát. Ví dụ `"hello"` sinh ra `"*ello"`, `"h*llo"`, `"he*lo"`, `"hel*o"`, `"hell*"`.
- Lưu `Map<String, Integer> patternCount` (đếm có bao nhiêu từ trong từ điển tạo ra mẫu đó) và `Set<String> dictWords`.
- Khi `search(searchWord)`, sinh từng mẫu cho `searchWord`:
  - Nếu `count > 1`: chắc chắn tồn tại ít nhất 1 từ khác `searchWord` khớp mẫu này $\implies$ trả về `true`.
  - Nếu `count == 1` và `!dictWords.contains(searchWord)`: từ duy nhất tạo ra mẫu này chắc chắn khác `searchWord` tại đúng vị trí `'*'` $\implies$ trả về `true`.

#### Mã nguồn minh họa:
```java
import java.util.*;

class MagicDictionaryWildcard {
    private final Set<String> words = new HashSet<>();
    private final Map<String, Integer> patternCount = new HashMap<>();

    public void buildDict(String[] dictionary) {
        words.clear();
        patternCount.clear();
        for (String word : dictionary) {
            words.add(word);
            char[] arr = word.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                char orig = arr[i];
                arr[i] = '*';
                String pattern = new String(arr);
                patternCount.put(pattern, patternCount.getOrDefault(pattern, 0) + 1);
                arr[i] = orig;
            }
        }
    }

    public boolean search(String searchWord) {
        boolean existsExact = words.contains(searchWord);
        char[] arr = searchWord.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char orig = arr[i];
            arr[i] = '*';
            String pattern = new String(arr);
            int count = patternCount.getOrDefault(pattern, 0);
            if (count > 1 || (count == 1 && !existsExact)) {
                return true;
            }
            arr[i] = orig;
        }
        return false;
    }
}
```

#### Đánh giá:
- **Ưu điểm:** `search` chỉ mất $\mathcal{O}(L^2)$ thời gian tra cứu bảng băm.
- **Nhược điểm:** Tốn rất nhiều bộ nhớ $\mathcal{O}(N \cdot L^2)$ vì phải lưu tới $N \times L$ chuỗi mới trong `HashMap` và tạo nhiều rác GC trong quá trình cấp phát `String`.

---

## 📊 Bảng so sánh các giải pháp (Complexity Comparison)

| Tiêu chí | ⭐ Trie + Iterative 1-Edit (Best) | Trie + DFS đệ quy | Gom nhóm độ dài (Length Buckets) | Wildcard HashMap |
| :--- | :--- | :--- | :--- | :--- |
| **`buildDict` Time** | $\mathcal{O}(N \cdot L)$ | $\mathcal{O}(N \cdot L)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N \cdot L^2)$ |
| **`search` Time** | $\mathcal{O}(\Sigma \cdot L^2)$ *(độc lập với $N$)* | $\mathcal{O}(\Sigma \cdot L^2)$ | $\mathcal{O}(N \cdot L)$ | $\mathcal{O}(L^2)$ |
| **Bộ nhớ phụ (Space)** | $\mathcal{O}(N \cdot L \cdot \Sigma)$ *(nén tiền tố)* | $\mathcal{O}(N \cdot L \cdot \Sigma + L)$ | $\mathcal{O}(N)$ *(chỉ tham chiếu)* | $\mathcal{O}(N \cdot L^2)$ *(tốn nhiều RAM)* |
| **Stack / GC Overhead** | **Không đệ quy, không sinh String rác** | Tốn $\mathcal{O}(L)$ stack frame | Không sinh String rác | Sinh $N \cdot L$ đối tượng String |
| **Mở rộng khi $N = 10^6$** | **Rất tốt** (không phụ thuộc $N$) | Rất tốt | Chậm (quét tuyến tính $N$ từ) | Tràn bộ nhớ (lưu $10^6 \cdot L$ chuỗi) |
| **Đánh giá phỏng vấn** | **Lựa chọn số 1 (Chuẩn Big Tech)** | Tốt (nên tối ưu khử đệ quy) | Tốt khi $N$ nhỏ hoặc $L$ rất lớn | Tạm ổn, cần lưu ý trade-off RAM |

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### Độ phức tạp Thời gian (Time Complexity)
- **`buildDict`:** $\mathcal{O}(N \cdot L)$
  - Ta duyệt qua $N$ từ, mỗi từ có độ dài trung bình $L$, chèn mỗi ký tự vào Trie với chi phí $\mathcal{O}(1)$ mỗi bước.
- **`search`:** $\mathcal{O}(\Sigma \cdot L^2)$ trong trường hợp xấu nhất (với $\Sigma = 26$, có thể viết gọn là $\mathcal{O}(L^2)$ nếu coi $\Sigma$ là hằng số).
  - ⚠️ **Lưu ý quan trọng:** Nhiều người dễ nhầm lẫn độ phức tạp của Trie tìm kiếm sai 1 ký tự là $\mathcal{O}(26^L)$. Con số $26^L$ chỉ đúng nếu được phép sai *nhiều* ký tự (như bài *211. Design Add and Search Words* với nhiều dấu `.`).
  - Ở bài này, ta chỉ được phép đổi **đúng 1 ký tự**: tại mỗi trong $L$ vị trí, có tối đa $25$ nhánh rẽ, và mỗi nhánh rẽ chỉ chạy `exactMatch` tuyến tính mất tối đa $L - i$ bước (không phân nhánh thêm). Tổng số bước tối đa là:
    $$\sum_{i=0}^{L-1} 25 \cdot (L - i) = \frac{25 \cdot L(L + 1)}{2} = \mathcal{O}(\Sigma \cdot L^2)$$
  - Trong thực tế, hàm `exactMatch` thường dừng ngay ở ký tự đầu tiên khi không khớp, và tổng số nút thực sự được thăm cũng không bao giờ vượt quá số nút trong Trie ($\mathcal{O}(N \cdot L)$), do đó chặn chặt hơn là $\mathcal{O}(\min(\Sigma \cdot L^2, N \cdot L))$.

### Độ phức tạp Không gian (Space Complexity)
- $\mathcal{O}(N \cdot L \cdot \Sigma)$ cho cấu trúc cây Trie (với $N \le 100, L \le 100, \Sigma = 26$, số nút tối đa không quá $10^4$ nút, tiêu tốn chỉ vài trăm KB bộ nhớ; các từ chung tiền tố còn dùng chung nút).
- $\mathcal{O}(1)$ bộ nhớ ngăn xếp phụ trợ khi `search` nhờ cài đặt hoàn toàn bằng vòng lặp không đệ quy.

---

## 🧪 Kiểm thử với các ca biên đặc biệt (Dry Run & Edge Cases)

### Ca 1: Từ trùng khít hoàn toàn nhưng không có "hàng xóm"
- **Input:** `dictionary = ["hello"]`, `search("hello")`
- **Diễn biến:** Tại mọi vị trí $i = 0 \dots 4$, chỉ có đúng nhánh ký tự của `"hello"` (`origIdx`) tồn tại; không có nhánh `k != origIdx` nào khác.
- **Kết quả:** `false` (Chính xác, vì không thể giữ nguyên $0$ thay đổi).

### Ca 2: Từ trùng khít một phần tử NHƯNG có từ khác chỉ sai khác 1 ký tự
- **Input:** `dictionary = ["hello", "hallo"]`, `search("hello")`
- **Diễn biến:** Ở $i = 1$ (`'e'`), `curr.children['a'] != null` (`'a' != 'e'`). Hàm `exactMatch` kiểm tra hậu tố `"llo"` và thấy `isEnd == true`.
- **Kết quả:** `true` (Chính xác).

### Ca 3: Từ có độ dài bằng 1
- **Input:** `dictionary = ["a"]`
- **`search("b")`:** Tại $i = 0$, `k = 'a' != 'b'`, gọi `exactMatch(node('a'), "b", 1)`. Vì `start == word.length()`, trả về `node('a').isEnd == true` $\implies$ `true`.
- **`search("a")`:** Không có nhánh `k != 'a'` $\implies$ `false`.

### Ca 4: Tiền tố hoặc chuỗi mở rộng của từ trong từ điển
- **Input:** `dictionary = ["abc"]`
- **`search("ab")`:** Ở $i = 0, 1$ không có nhánh rẽ; khi thử rẽ ở đâu cũng không có nhánh khác độ dài 2 có `isEnd == true` $\implies$ `false`.
- **`search("abcd")`:** Ở $i = 3$ (`'d'`), `curr` đang ở `'c'*`, mọi `curr.children[k]` đều là `null`, và `curr = curr.children['d'] == null` $\implies$ dừng sớm và trả về `false`.

### Ca 5: Sai khác ở ngay ký tự đầu tiên
- **Input:** `dictionary = ["hello"]`, `search("xello")`
- **Diễn biến:** Ở $i = 0$, `origIdx = 'x' - 'a'`, thử `k = 'h' - 'a' != origIdx`. `exactMatch(node('h'), "xello", 1)` khớp toàn bộ `"ello"` $\implies$ trả về `true` ngay tại $i = 0$.

---

## 🚀 Kinh nghiệm rút ra & Bài học phỏng vấn (Interview Takeaways)

1. **Biết cách phân tích Trade-off giữa các cấu trúc dữ liệu:**
   - Khi phỏng vấn, hãy chủ động đề cập cả hai góc nhìn: **Length-Bucketed Scan** ($\mathcal{O}(N \cdot L)$) tối ưu khi $N$ nhỏ hoặc $L$ rất lớn, và **Trie** ($\mathcal{O}(\Sigma \cdot L^2)$) tối ưu khi từ điển $N$ lớn và cần tra cứu độc lập với $N$.
2. **Tư duy tách bài toán 1-mismatch để khử đệ quy:**
   - Bất kỳ bài toán so khớp sai đúng 1 vị trí nào cũng có thể tách thành *Tiền tố giống nhau + 1 điểm rẽ nhánh + Hậu tố giống nhau*. Kỹ thuật này giúp loại bỏ hoàn toàn call stack của DFS và tăng tốc độ thực thi đáng kể.
3. **Các bài toán liên quan trên LeetCode để rèn luyện thêm:**
   - **LeetCode 208:** Implement Trie (Prefix Tree) *(Nền tảng cốt lõi của Trie)*
   - **LeetCode 211:** Design Add and Search Words Data Structure *(Trie + DFS với nhiều ký tự đại diện `.`)*
   - **LeetCode 648:** Replace Words *(Trie tìm tiền tố ngắn nhất)*
   - **LeetCode 127:** Word Ladder *(Biến đổi từng bước 1 ký tự theo đồ thị BFS)*
   - **LeetCode 720:** Longest Word in Dictionary *(Duyệt cây Trie tìm từ dài nhất)*
