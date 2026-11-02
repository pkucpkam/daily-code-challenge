# 📚 Giải thích Chi tiết Bài 667 - Beautiful Arrangement II

**Mục tiêu:** Cho hai số nguyên $n$ và $k$. Hãy xây dựng một mảng `answer` gồm $n$ số nguyên dương phân biệt từ $1$ đến $n$ sao cho danh sách các hiệu tuyệt đối giữa các phần tử liền kề $[|a_1 - a_2|, |a_2 - a_3|, \dots, |a_{n-1} - a_n|]$ có đúng $k$ số nguyên phân biệt. Nếu có nhiều đáp án hợp lệ, trả về bất kỳ đáp án nào.

---

## 🎯 Phân tích bài toán

### 1. Bản chất Hoán vị & Hiệu tuyệt đối liền kề

- Mảng kết quả là một **hoán vị** của tập hợp $\{1, 2, 3, \dots, n\}$.
- Mảng có độ dài $n$, do đó có đúng $n - 1$ cặp phần tử liền kề và tạo ra $n - 1$ giá trị hiệu:
  $$d_i = |answer[i] - answer[i + 1]| \quad (0 \le i < n - 1)$$
- Vì mỗi phần tử thuộc đoạn $[1, n]$, khoảng cách nhỏ nhất giữa hai số phân biệt là $1$, và khoảng cách lớn nhất có thể đạt được là $n - 1$ (khi $|1 - n| = n - 1$).
- Do đó, tập hợp các khoảng cách phân biệt luôn là tập con của $\{1, 2, 3, \dots, n - 1\}$, nghĩa là:
  $$1 \le k \le n - 1$$

---

### 2. Hai Trường hợp Cực biên (Extreme Cases)

Quan sát hai cận biên của $k$ giúp ta tìm ra quy luật kiến tạo:

#### Trường hợp 1: $k = 1$ (Tối thiểu)
- Ta chỉ cần đúng **1 khoảng cách phân biệt** (khoảng cách là $1$).
- Cách bố trí đơn giản nhất là sắp xếp các số tăng dần liên tiếp:
  $$[1, 2, 3, 4, \dots, n]$$
- Các hiệu liền kề: $|2 - 1| = 1, |3 - 2| = 1, |4 - 3| = 1, \dots$
- Tập khoảng cách: $\{1\} \implies$ Số lượng khoảng cách phân biệt là $1$.

#### Trường hợp 2: $k = n - 1$ (Tối đa)
- Ta cần tạo ra **$n - 1$ khoảng cách phân biệt**, tức là toàn bộ các giá trị từ $1$ đến $n - 1$:
  $$\{n - 1, n - 2, n - 3, \dots, 2, 1\}$$
- Để tạo ra các bước nhảy giảm dần từ lớn nhất về nhỏ nhất, ta dùng kỹ thuật **đan xen hai đầu (Zig-zag / Two Pointers)** giữa số nhỏ nhất và số lớn nhất còn lại:
  $$[1, n, 2, n - 1, 3, n - 2, \dots]$$
- Quan sát các khoảng cách:
  - $|n - 1| = n - 1$
  - $|2 - n| = n - 2$
  - $|(n - 1) - 2| = n - 3$
  - $|3 - (n - 1)| = n - 4$
  - $\dots$
- Mỗi bước đan xen làm giảm độ chênh lệch đi đúng $1$ đơn vị, sinh ra toàn bộ $n - 1$ khoảng cách khác nhau!

---

### 3. Ý tưởng Xây dựng (Constructive Strategy) cho $1 \le k < n$

Thay vì tìm kiếm hay vét cạn, ta kết hợp linh hoạt hai kỹ thuật trên bằng cách chia mảng thành **2 giai đoạn độc lập**:

```text
Mảng kết quả (n phần tử):
┌──────────────────────────────────────┬───────────────────────────────┐
│     Giai đoạn 1: k + 1 phần tử đầu    │ Giai đoạn 2: n - k - 1 còn lại │
│   Xếp đan xen Zig-zag (Two Pointers)  │   Xếp tăng dần liên tiếp       │
│    Tạo đúng k khoảng cách: k, ..., 1  │   Chỉ duy trì khoảng cách 1   │
└──────────────────────────────────────┴───────────────────────────────┘
Chỉ số: 0, 1, 2, ..., k                  k + 1, k + 2, ..., n - 1
```

1. **Giai đoạn 1 (Chỉ số $0 \dots k$):**
   - Dùng đúng $k + 1$ số nguyên đầu tiên: $\{1, 2, 3, \dots, k + 1\}$.
   - Đan xen hai con trỏ `left = 1` và `right = k + 1`:
     $$[1, k + 1, 2, k, 3, k - 1, \dots]$$
   - $k + 1$ phần tử này tạo ra đúng $k$ khoảng cách liền kề:
     $$k, k - 1, k - 2, \dots, 2, 1$$

2. **Giai đoạn 2 (Chỉ số $k + 1 \dots n - 1$):**
   - Các số chưa dùng là $\{k + 2, k + 3, \dots, n\}$.
   - Điền thẳng theo thứ tự tăng dần liên tiếp:
     $$answer[i] = i + 1 \quad (\text{với } i = k + 1 \dots n - 1)$$
   - Các khoảng cách liền kề trong đoạn này đều bằng $1$ (khoảng cách $1$ vốn đã có ở giai đoạn 1, nên không làm tăng thêm số lượng hiệu phân biệt).

---

### 4. Ràng buộc & Đánh giá quy mô (Constraints)

- $1 \le k < n \le 10^4$.
- Với $n = 10^4$, một thuật toán quay lui hoán vị $(\mathcal{O}(n!))$ hay $\mathcal{O}(n^2)$ sẽ gặp lỗi quá thời gian (TLE).
- Thuật toán kiến thiết (Constructive Algorithm) duyệt tuyến tính $\mathcal{O}(n)$ sẽ hoàn thành trong thời gian dưới $1 \text{ ms}$, đạt hiệu năng $0 \text{ ms}$ (100%) tuyệt đối trên LeetCode.

---

## 🔍 Các hướng tiếp cận (Approaches Comparison)

| Tiêu chí | Cách 1: Quay lui / Sinh hoán vị (Backtracking) | Cách 2: Sinh ngẫu nhiên (Randomized Shuffle) | Cách 3: Thuật toán Kiến thiết Hai con trỏ (Constructive Two Pointers ⭐) |
| :--- | :--- | :--- | :--- |
| **Ý tưởng** | Thử mọi hoán vị của $[1 \dots n]$, đếm số hiệu phân biệt rồi dừng | Xáo trộn mảng ngẫu nhiên và kiểm tra điều kiện $k$ hiệu | Tạo trực tiếp mảng thỏa mãn: $k + 1$ phần tử đầu zig-zag, phần còn lại tăng dần |
| **Độ phức tạp Thời gian** | $\mathcal{O}(n!)$ | Không xác định (vô hạn trong trường hợp xấu) | $\mathcal{O}(n)$ - Duyệt đúng 1 lượt qua mảng |
| **Độ phức tạp Bộ nhớ** | $\mathcal{O}(n)$ ngăn xếp đệ quy | $\mathcal{O}(n)$ mảng băm kiểm tra | $\mathcal{O}(1)$ bộ nhớ phụ trợ (In-place gán vào mảng kết quả) |
| **Đánh giá** | TLE ngay với $n \ge 12$ | Không ổn định, dễ bị Time Limit Exceeded | Đơn giản, thanh lịch, đạt $0 \text{ ms}$ (Top 100%) |

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Thuật Toán Kiến Thiết Hai Con Trỏ (Constructive Two Pointers)

### 💡 Ý tưởng thuật toán cốt lõi (Core Insights)

Ta khởi tạo mảng kết quả `answer` kích thước $n$ cùng hai con trỏ:
- `left = 1`
- `right = k + 1`

1. **Vòng lặp 1:** Duyệt $i$ từ $0$ đến $k$:
   - Nếu $i$ chẵn (vị trí $0, 2, 4, \dots$): lấy giá trị từ đầu trái $\implies answer[i] = left++$.
   - Nếu $i$ lẻ (vị trí $1, 3, 5, \dots$): lấy giá trị từ đầu phải $\implies answer[i] = right--$.
2. **Vòng lặp 2:** Duyệt $i$ từ $k + 1$ đến $n - 1$:
   - Điền giá trị tăng dần liên tiếp: $answer[i] = i + 1$.

---

### 📐 Chứng minh Toán học tính đúng đắn (Mathematical Proof)

Để chứng minh thuật toán luôn tạo ra **chính xác $k$ hiệu phân biệt**, ta cần chứng minh 3 mệnh đề toán học:

#### Mệnh đề 1: Giai đoạn 1 tạo ra đủ $k$ hiệu phân biệt $\{1, 2, \dots, k\}$
Xét dãy phần tử tại các chỉ số $0 \le i \le k$:
- Các phần tử được gán lần lượt là: $1, k + 1, 2, k, 3, k - 1, \dots$
- Khoảng cách giữa các phần tử liền kề:
  $$d_0 = |(k + 1) - 1| = k$$
  $$d_1 = |2 - (k + 1)| = k - 1$$
  $$d_2 = |k - 2| = k - 2$$
  $$\dots$$
  $$d_{k-1} = 1$$
- Như vậy, các khoảng cách liền kề trong đoạn $[0, k]$ bao phủ chính xác tập hợp:
  $$D_1 = \{k, k - 1, k - 2, \dots, 2, 1\}$$
- Tập hợp $D_1$ có đúng $k$ phần tử phân biệt.

#### Mệnh đề 2: Hiệu tại điểm nối giữa Giai đoạn 1 và Giai đoạn 2 không tạo ra khoảng cách mới
- Điểm nối là cặp $(answer[k], answer[k + 1])$.
- Theo thuật toán: $answer[k + 1] = k + 2$.
- Phần tử $answer[k]$ là một giá trị thuộc tập $\{1, 2, \dots, k + 1\}$.
- Đặc biệt, vì $answer[0] = 1$ và tất cả $k + 1$ số đều phân biệt, nên $answer[k] \neq 1$. Do đó:
  $$2 \le answer[k] \le k + 1$$
- Khoảng cách tại điểm nối là:
  $$d_k = |answer[k + 1] - answer[k]| = (k + 2) - answer[k]$$
- Thay cận của $answer[k]$:
  $$(k + 2) - (k + 1) \le d_k \le (k + 2) - 2$$
  $$1 \le d_k \le k$$
- Kết luận: Khoảng cách $d_k$ tại điểm nối luôn nằm trong đoạn $[1, k]$, tức là $d_k \in D_1$. **Không xuất hiện bất kỳ khoảng cách mới nào tại điểm chuyển tiếp!**

#### Mệnh đề 3: Giai đoạn 2 không tạo ra khoảng cách mới
- Với mọi $i$ từ $k + 1$ đến $n - 2$:
  $$d_i = |answer[i + 1] - answer[i]| = ((i + 1) + 1) - (i + 1) = 1$$
- Khoảng cách $1$ đã có sẵn trong tập $D_1$.

> [!IMPORTANT]
> Tổng hợp 3 mệnh đề: Toàn bộ mảng chỉ chứa các khoảng cách thuộc tập $\{1, 2, \dots, k\}$. Vì tập này có đúng $k$ phần tử và tất cả đều đã xuất hiện ở Giai đoạn 1, số lượng khoảng cách phân biệt của toàn bộ mảng là **chính xác $k$**.

---

### 📊 Sơ đồ minh họa trực quan (Visual Walkthrough)

#### Mô hình đan xen Zig-zag hai con trỏ trên trục số (với $k = 4$):

```text
Trục số các giá trị [1, 2, 3, 4, 5]:

    left=1                      right=5
      ▼                            ▼
      1      2      3      4      5
      │      ▲      ▲      │      ▲
      │      │      │      │      │
      └──────┼──────┼──────┘      │  (Bước 0 -> 1: nhảy từ 1 lên 5, d = 4)
             │      └─────────────┘  (Bước 1 -> 2: nhảy từ 5 về 2, d = 3)
             └─────────────┐         (Bước 2 -> 3: nhảy từ 2 lên 4, d = 2)
                           └──────┐  (Bước 3 -> 4: nhảy từ 4 về 3, d = 1)
                                  ▼
Các khoảng cách tạo ra: [4, 3, 2, 1] -> Đủ 4 khoảng cách phân biệt!
```

---

## 🔄 Từng bước thực thi (Dry-run / Trace qua Ví dụ cụ thể)

### Ví dụ 1: $n = 3, k = 1$

- Khởi tạo: `answer` độ dài 3, `left = 1`, `right = 2`.
- **Giai đoạn 1 ($i \le 1$):**
  - $i = 0$ (chẵn): `answer[0] = left++ = 1` (`left = 2`).
  - $i = 1$ (lẻ): `answer[1] = right-- = 2` (`right = 1`).
- **Giai đoạn 2 ($i$ từ $2$ đến $2$):**
  - $i = 2$: `answer[2] = i + 1 = 3`.
- Kết quả: `answer = [1, 2, 3]`.
- Các hiệu: $|2 - 1| = 1, |3 - 2| = 1 \implies \{1\}$ (Đúng $1$ hiệu phân biệt $\implies$ **PASS**).

---

### Ví dụ 2: $n = 3, k = 2$

- Khởi tạo: `answer` độ dài 3, `left = 1`, `right = 3`.
- **Giai đoạn 1 ($i \le 2$):**
  - $i = 0$ (chẵn): `answer[0] = left++ = 1` (`left = 2`).
  - $i = 1$ (lẻ): `answer[1] = right-- = 3` (`right = 2`).
  - $i = 2$ (chẵn): `answer[2] = left++ = 2` (`left = 3`).
- **Giai đoạn 2 ($i$ từ $3$ đến $2$):** Không chạy vì $3 \ge n$.
- Kết quả: `answer = [1, 3, 2]`.
- Các hiệu: $|3 - 1| = 2, |2 - 3| = 1 \implies \{1, 2\}$ (Đúng $2$ hiệu phân biệt $\implies$ **PASS**).

---

### Ví dụ 3: $n = 6, k = 3$

- Khởi tạo: `answer` độ dài 6, `left = 1`, `right = 4`.
- **Giai đoạn 1 ($i$ từ $0$ đến $k = 3$):**
  - $i = 0$: `answer[0] = left++ = 1` (`left = 2`).
  - $i = 1$: `answer[1] = right-- = 4` (`right = 3`).
  - $i = 2$: `answer[2] = left++ = 2` (`left = 3`).
  - $i = 3$: `answer[3] = right-- = 3` (`right = 2`).
  - Mảng tạm: `[1, 4, 2, 3, _, _]`.
  - Các hiệu tạo ra: $|4 - 1| = 3, |2 - 4| = 2, |3 - 2| = 1$ (gồm các hiệu $\{3, 2, 1\}$).
- **Giai đoạn 2 ($i$ từ $4$ đến $5$):**
  - $i = 4$: `answer[4] = 4 + 1 = 5`.
    - Hiệu điểm nối: $|5 - 3| = 2$ (đã có trong tập $\{3, 2, 1\}$).
  - $i = 5$: `answer[5] = 5 + 1 = 6`.
    - Hiệu tiếp theo: $|6 - 5| = 1$ (đã có trong tập $\{3, 2, 1\}$).
- Kết quả hoàn chỉnh: `answer = [1, 4, 2, 3, 5, 6]`.
- Tập hiệu: $\{3, 2, 1\}$ $\implies$ Đúng $3$ hiệu phân biệt $\implies$ **PASS**!

---

### Ví dụ 4: $n = 6, k = 5$ (Trường hợp $k = n - 1$)

- $k = 5 \implies$ Giai đoạn 1 chiếm toàn bộ $6$ phần tử ($i = 0 \dots 5$).
- Quá trình đan xen:
  - $i = 0 \implies 1$
  - $i = 1 \implies 6$
  - $i = 2 \implies 2$
  - $i = 3 \implies 5$
  - $i = 4 \implies 3$
  - $i = 5 \implies 4$
- Kết quả: `[1, 6, 2, 5, 3, 4]`.
- Các hiệu liền kề:
  $$|6 - 1| = 5, \quad |2 - 6| = 4, \quad |5 - 2| = 3, \quad |3 - 5| = 2, \quad |4 - 3| = 1$$
- Tập hiệu: $\{5, 4, 3, 2, 1\}$ $\implies$ Đúng $5$ hiệu phân biệt $\implies$ **PASS**!

---

## 💻 Cài đặt Thuật toán Chi tiết (Java Implementation)

```java
class Solution {
    /**
     * Thuật toán Tối ưu: Thuật toán Kiến thiết (Constructive Algorithm) kết hợp Hai con trỏ (Two Pointers)
     * 
     * Phân tích kỹ thuật:
     * - Đề bài yêu cầu tạo hoán vị của các số từ 1 đến n sao cho có đúng k hiệu tuyệt đối liền kề khác nhau.
     * 
     * - Nhận xét then chốt:
     *   1. Nếu xếp tăng dần liên tiếp [1, 2, 3, ..., n], ta chỉ có đúng 1 hiệu duy nhất: {1}.
     *   2. Nếu xen kẽ số nhỏ nhất và lớn nhất (Zig-zag) bằng hai con trỏ [1, k+1, 2, k, 3, k-1, ...],
     *      các hiệu liền kề sẽ lần lượt là k, k-1, k-2, ..., 1 (tạo ra đúng k hiệu khác biệt).
     *   3. Sau khi đã dùng hết k + 1 số từ 1 đến k + 1 để tạo đủ k hiệu khác nhau, các số còn lại
     *      từ k + 2 đến n chỉ cần điền tăng dần liên tiếp [k + 2, k + 3, ..., n].
     *      + Hiệu giữa hai phần tử liên tiếp còn lại là 1 (đã có trong tập k hiệu).
     *      + Hiệu giữa phần tử cuối của phần zig-zag và (k + 2) luôn nằm trong khoảng [1, k] (không phát sinh hiệu mới).
     * 
     * Độ phức tạp:
     * - Thời gian: O(n) - Duyệt qua mảng kết quả đúng 1 lần để gán giá trị.
     * - Không gian phụ trợ: O(1) - Không sử dụng cấu trúc dữ liệu phụ, chỉ tốn O(n) bộ nhớ cho mảng kết quả trả về.
     */
    public int[] constructArray(int n, int k) {
        int[] answer = new int[n];
        int left = 1;
        int right = k + 1;

        // Giai đoạn 1: Dùng k + 1 phần tử đầu tiên đan xen để tạo đúng k hiệu: k, k-1, ..., 1
        for (int i = 0; i <= k; i++) {
            if (i % 2 == 0) {
                answer[i] = left++;
            } else {
                answer[i] = right--;
            }
        }

        // Giai đoạn 2: Điền các phần tử còn lại từ k + 2 đến n theo thứ tự tăng dần
        for (int i = k + 1; i < n; i++) {
            answer[i] = i + 1;
        }

        return answer;
    }
}
```

---

## 🔍 Giải thích từng dòng lệnh chi tiết (Line-by-Line Walkthrough)

1. `int[] answer = new int[n];`
   - Cấp phát mảng chứa kết quả có độ dài $n$ theo đúng yêu cầu đề bài.
2. `int left = 1; int right = k + 1;`
   - Khởi tạo hai con trỏ đại diện cho giá trị nhỏ nhất và lớn nhất của đoạn $\{1, 2, \dots, k + 1\}$.
   - Đoạn này chứa đúng $k + 1$ phần tử, đủ để tạo ra $k$ khoảng cách giảm dần từ $k$ về $1$.
3. `for (int i = 0; i <= k; i++)`
   - Vòng lặp chạy qua đúng $k + 1$ chỉ số đầu tiên ($0, 1, \dots, k$).
   - Nếu chỉ số `i` là số chẵn (`i % 2 == 0`): gán `answer[i] = left++` (lấy giá trị từ đầu trái và tăng `left`).
   - Nếu chỉ số `i` là số lẻ: gán `answer[i] = right--` (lấy giá trị từ đầu phải và giảm `right`).
   - Kỹ thuật zig-zag này đảm bảo khoảng cách giữa hai bước kề nhau luôn giảm $1$: $k, k - 1, k - 2, \dots, 1$.
4. `for (int i = k + 1; i < n; i++)`
   - Duyệt các vị trí còn lại từ chỉ số $k + 1$ đến $n - 1$.
   - Gán `answer[i] = i + 1`: tương ứng với việc điền các số còn lại từ $k + 2$ đến $n$ theo thứ tự tăng dần.
   - Nhờ đó, tất cả các hiệu tiếp theo đều là $1$, hoàn toàn không làm phát sinh thêm khoảng cách mới nào khác ngoài $k$ khoảng cách đã có.
5. `return answer;`
   - Trả về mảng hoán vị hợp lệ.

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### 1. Độ phức tạp Thời gian (Time Complexity): $\mathcal{O}(n)$
- Vòng lặp thứ nhất duyệt qua $k + 1$ phần tử.
- Vòng lặp thứ hai duyệt qua $n - k - 1$ phần tử còn lại.
- Tổng số bước thực thi bằng đúng $n$. Mỗi bước chỉ gồm một phép gán giá trị và tăng/giảm con trỏ trong $\mathcal{O}(1)$.
- Không có bất kỳ phép sắp xếp hay tìm kiếm nào $\implies$ Thời gian thực thi là tuyến tính $\mathcal{O}(n)$, tương đương **$0 \text{ ms}$** trên hệ thống chấm bài.

### 2. Độ phức tạp Không gian (Space Complexity): $\mathcal{O}(1)$ phụ trợ
- Mảng `answer` kích thước $n$ là bộ nhớ bắt buộc dùng để chứa kết quả trả về theo chữ ký hàm của bài toán.
- Thuật toán chỉ sử dụng thêm hai biến nguyên `left` và `right` để điều khiển con trỏ.
- Không dùng thêm bất kỳ cấu trúc dữ liệu trung gian nào như `HashSet`, `ArrayList`, hay mảng phụ $\implies$ Bộ nhớ phụ trợ đạt tối ưu tuyệt đối $\mathcal{O}(1)$.

---

## 💡 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways & Interview Tips)

### 1. Tư duy Bài toán Xây dựng (Constructive Algorithm)
- Rất nhiều bài toán yêu cầu *"tìm/xây dựng một cấu hình thỏa mãn điều kiện"* khiến ứng viên dễ sa vào bẫy thử-sai bằng Backtracking hoặc sinh hoán vị.
- **Kinh nghiệm:** Khi đề bài cho phép *"trả về bất kỳ đáp án nào nếu có nhiều nghiệm"*, hãy luôn nghĩ đến việc **tự tay xây dựng nghiệm (Constructive Approach)** thay vì duyệt tìm nghiệm. Hãy tìm ra một quy luật hình học hoặc toán học đơn giản nhất để chủ động tạo ra cấu hình mong muốn.

### 2. Kỹ thuật Phân tách Bài toán qua Trường hợp Cực biên
- Khi gặp bài toán có tham số $k$ nằm giữa $[1, n - 1]$:
  - Hãy xét $k = 1$: sắp xếp liên tiếp tạo ra khoảng cách đồng nhất $1$.
  - Hãy xét $k = n - 1$: đảo chiều hai đầu (Zig-zag / Two Pointers) tạo ra các khoảng cách phân biệt cực đại.
  - Sau đó ghép hai kỹ thuật lại: Dùng phần zig-zag cho $k$ khoảng cách đầu tiên, và dùng phần liên tiếp cho phần còn lại. Đây là mẫu hình tư duy cực kỳ phổ biến trong các bài toán hoán vị và mảng!
