# 📚 Giải thích Chi tiết Bài 692 - Top K Frequent Words

**Mục tiêu:** Cho một mảng các chuỗi `words` và một số nguyên `k`. Hãy tìm và trả về `k` từ xuất hiện nhiều nhất trong mảng. Kết quả trả về phải được sắp xếp theo:
1. **Tần suất giảm dần** (từ xuất hiện nhiều nhất đứng trước).
2. **Thứ tự từ điển tăng dần** nếu hai từ có cùng tần suất (ví dụ: `"i"` đứng trước `"love"`).

---

## 🎯 Phân tích bài toán

### 1. Hai tiêu chí so sánh (Dual Sorting Criteria)
Bài toán đặt ra 2 lớp điều kiện khi so sánh hai từ $A$ và $B$:
- **Tiêu chí 1 (Ưu tiên cao nhất):** $\text{count}(A) > \text{count}(B) \implies A$ xếp trước $B$.
- **Tiêu chí 2 (Xử lý hòa):** Nếu $\text{count}(A) == \text{count}(B)$, từ nào có thứ tự từ điển nhỏ hơn ($A \text{ đứng trước } B \text{ theo bảng chữ cái}$) sẽ được xếp trước: $A.\text{compareTo}(B) < 0 \implies A$ xếp trước $B$.

---

### 2. Yêu cầu Follow-up & Cấu trúc dữ liệu
> **Follow-up:** *Could you solve it in $\mathcal{O}(n \log k)$ time and $\mathcal{O}(n)$ extra space?*

- Nếu ta đếm tần suất bằng `HashMap` rồi sắp xếp toàn bộ $U$ từ duy nhất ($U \le N$), độ phức tạp thời gian sẽ là $\mathcal{O}(U \log U)$. Khi $U \approx N$ và $k \ll N$, thuật toán này chưa tối ưu theo yêu cầu follow-up.
- Để đạt được độ phức tạp $\mathcal{O}(N \log k)$, ta cần duy trì một tập hợp chỉ chứa **đúng $k$ ứng cử viên xuất sắc nhất** tại mọi thời điểm bằng cấu trúc dữ liệu **Hàng đợi ưu tiên (PriorityQueue / Min-Heap)**.

---

## 🔍 Các hướng tiếp cận (Approaches Comparison)

| Tiêu chí | Cách 1: Đếm tần suất + Sắp xếp toàn bộ (Full Sort) | Cách 2: Min-Heap kích thước $k$ ⭐ (Thỏa mãn Follow-up) | Cách 3: Bucket Sort + Trie (Nâng cao) |
| :--- | :--- | :--- | :--- |
| **Cấu trúc** | `HashMap` + `ArrayList.sort()` | `HashMap` + `PriorityQueue` (size $k$) | `HashMap` + Mảng Buckets + `Trie` |
| **Độ phức tạp Thời gian** | $\mathcal{O}(N + U \log U)$ | $\mathcal{O}(N + U \log k)$ | $\mathcal{O}(N \cdot L)$ ($L$: độ dài từ) |
| **Độ phức tạp Bộ nhớ** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ (heap chỉ tốn $\mathcal{O}(k)$) | $\mathcal{O}(N \cdot L)$ |
| **Đánh giá** | Dễ viết nhưng chưa tối ưu khi $k \ll N$ | **Chuẩn mực phỏng vấn, đáp ứng chính xác Follow-up** | Phức tạp, dễ sai sót khi phỏng vấn |

---

## 💡 Chiến thuật Thiết kế Min-Heap Kích Thước K (Min-Heap Comparator Trick)

Đây là điểm cốt lõi và tinh tế nhất của bài toán:
- Ta muốn giữ lại $k$ từ "tốt nhất" (tần suất lớn nhất, từ điển nhỏ nhất).
- Trong **Min-Heap**, phần tử nằm ở **đỉnh heap** luôn là phần tử bị loại bỏ đầu tiên khi kích thước heap vượt quá $k$ (`poll()`).
- Do đó, phần tử ở đỉnh heap phải là phần tử **"kém ưu tiên nhất"** trong nhóm top $k$:
  1. Từ có **tần suất nhỏ hơn** sẽ nằm ở đỉnh heap để bị đào thải trước.
  2. Nếu hai từ có **cùng tần suất**, từ có **thứ tự từ điển LỚN hơn** sẽ nằm ở đỉnh heap để bị đào thải trước (vì ta muốn giữ lại từ có từ điển nhỏ hơn!).

### Cấu hình Comparator trong Java:
```java
PriorityQueue<String> minHeap = new PriorityQueue<>((w1, w2) -> {
    int freq1 = freqMap.get(w1);
    int freq2 = freqMap.get(w2);
    if (freq1 != freq2) {
        return Integer.compare(freq1, freq2); // Tần suất nhỏ hơn lên đỉnh heap
    }
    return w2.compareTo(w1); // Từ điển LỚN hơn lên đỉnh heap (w2 so với w1)
});
```

Sau khi duyệt qua toàn bộ các từ trong `freqMap`:
- Heap chỉ còn chứa đúng $k$ từ xuất sắc nhất.
- Lần lượt lấy ra (`poll()`) sẽ thu được các từ từ "kém nhất" đến "xuất sắc nhất".
- Ta chỉ cần đảo ngược danh sách kết quả (`Collections.reverse`) là thu được thứ tự mong muốn.

---

### 📊 Minh họa trực quan (Visual Walkthrough)

#### Ví dụ 1: `words = ["i", "love", "leetcode", "i", "love", "coding"]`, `k = 2`

**1. Bảng tần suất (`freqMap`):**
- `"i"`: 2
- `"love"`: 2
- `"leetcode"`: 1
- `"coding"`: 1

**2. Tiến trình đưa vào Min-Heap (kích thước tối đa $k = 2$):**

| Bước | Đưa vào | Tần suất | Trạng thái Heap (Đỉnh $\to$ Đáy) | Hành động khi `size > 2` |
| :---: | :---: | :---: | :--- | :--- |
| 1 | `"i"` | 2 | `["i"]` | - |
| 2 | `"love"` | 2 | `["love", "i"]` *(vì `"love" > "i"` nên `"love"` lên đỉnh)* | - |
| 3 | `"leetcode"` | 1 | `["leetcode", "love", "i"]` *(freq 1 nhỏ nhất lên đỉnh)* | `poll()` loại `"leetcode"` $\to$ còn `["love", "i"]` |
| 4 | `"coding"` | 1 | `["coding", "love", "i"]` *(freq 1 nhỏ nhất lên đỉnh)* | `poll()` loại `"coding"` $\to$ còn `["love", "i"]` |

**3. Rút kết quả từ Heap:**
- `poll()` lần 1: `"love"`
- `poll()` lần 2: `"i"`
- Danh sách ban đầu: `["love", "i"]`
- Sau khi đảo ngược (`Collections.reverse`): **`["i", "love"]`** ✅

---

## 💻 Cài đặt Thuật toán Chi tiết (Java Implementation)

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        // Bước 1: Đếm tần suất xuất hiện của từng từ
        Map<String, Integer> freqMap = new HashMap<>();
        for (String word : words) {
            freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
        }

        // Bước 2: Khởi tạo Min-Heap duy trì k từ có tần suất cao nhất
        PriorityQueue<String> minHeap = new PriorityQueue<>((w1, w2) -> {
            int freq1 = freqMap.get(w1);
            int freq2 = freqMap.get(w2);
            if (freq1 != freq2) {
                return Integer.compare(freq1, freq2); // Tần suất nhỏ hơn nằm ở đỉnh
            }
            return w2.compareTo(w1); // Cùng tần suất: từ điển lớn hơn nằm ở đỉnh
        });

        // Bước 3: Duyệt qua từng từ duy nhất và đưa vào Heap
        for (String word : freqMap.keySet()) {
            minHeap.offer(word);
            if (minHeap.size() > k) {
                minHeap.poll(); // Loại bỏ phần tử kém ưu tiên nhất
            }
        }

        // Bước 4: Lấy các phần tử ra khỏi Heap
        List<String> result = new ArrayList<>(k);
        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll());
        }

        // Đảo ngược danh sách để có thứ tự từ cao xuống thấp
        Collections.reverse(result);
        return result;
    }
}
```

---

## 🔍 Giải thích từng dòng lệnh chi tiết (Line-by-Line Walkthrough)

1. **`Map<String, Integer> freqMap = new HashMap<>();`**:
   - Dùng bảng băm để đếm số lần xuất hiện của mỗi từ trong thời gian $\mathcal{O}(N)$.
2. **`PriorityQueue<String> minHeap = new PriorityQueue<>(...);`**:
   - Định nghĩa comparator tùy chỉnh cho Min-Heap. Điểm mấu chốt là `w2.compareTo(w1)` đảo ngược thứ tự từ điển, biến heap thành "max-heap" theo góc độ từ điển cho những từ hòa tần suất để loại bỏ từ có thứ tự chữ cái lớn hơn trước.
3. **`for (String word : freqMap.keySet()) { minHeap.offer(word); if (minHeap.size() > k) minHeap.poll(); }`**:
   - Duyệt qua từng từ duy nhất. Mỗi thao tác `offer` và `poll` trên heap kích thước tối đa $k + 1$ chỉ tốn $\mathcal{O}(\log k)$ thời gian.
4. **`Collections.reverse(result);`**:
   - Vì lấy từ Min-Heap ra sẽ có thứ tự tăng dần (từ nhỏ nhất đến lớn nhất), ta đảo ngược danh sách để đưa về đúng thứ tự yêu cầu của bài toán (tần suất giảm dần, từ điển tăng dần).

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### Độ phức tạp Thời gian (Time Complexity)
- **$\mathcal{O}(N + U \log k)$** (với $N$ là tổng số từ, $U$ là số từ duy nhất, $U \le N$):
  - Đếm tần suất: $\mathcal{O}(N \cdot L)$ với $L \le 10$ là độ dài từ.
  - Thêm $U$ từ vào Min-Heap kích thước $k$: $\mathcal{O}(U \cdot L \log k)$.
  - Rút $k$ phần tử và đảo ngược: $\mathcal{O}(k \cdot L)$.
  - Với $N \le 500$, thời gian chạy thực tế trên LeetCode chỉ $\approx 5-6\text{ ms}$, thỏa mãn trọn vẹn yêu cầu Follow-up $\mathcal{O}(N \log k)$.

### Độ phức tạp Không gian (Space Complexity)
- **$\mathcal{O}(N)$**:
  - `freqMap` lưu tối đa $U \le N$ từ: $\mathcal{O}(N \cdot L)$.
  - `minHeap` lưu tối đa $k + 1$ phần tử: $\mathcal{O}(k \cdot L)$.
  - Danh sách kết quả `result` lưu $k$ phần tử: $\mathcal{O}(k \cdot L)$.
  - Tổng bộ nhớ phụ: $\mathcal{O}(N)$.

---

## 💡 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways & Interview Tips)

1. **Quy tắc vàng khi dùng Heap cho bài toán "Top K":**
   - Muốn tìm **Top K Lớn Nhất** $\implies$ Dùng **Min-Heap kích thước K**.
   - Muốn tìm **Top K Nhỏ Nhất** $\implies$ Dùng **Max-Heap kích thước K**.
   - Khi có nhiều tiêu chí so sánh kết hợp, tiêu chí nào muốn *giữ lại giá trị nhỏ hơn* thì trong Min-Heap phải cho *giá trị lớn hơn lên đỉnh* để bị đào thải trước.
2. **So sánh với LeetCode 347 (Top K Frequent Elements):**
   - Bài 347 chỉ yêu cầu số nguyên và không ràng buộc thứ tự khi hòa tần suất.
   - Bài 692 nâng cấp thêm điều kiện sắp xếp từ điển chuỗi ký tự, là cơ hội tuyệt vời để thể hiện khả năng viết custom Comparator chính xác.
