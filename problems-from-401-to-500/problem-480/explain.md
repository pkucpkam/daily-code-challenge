# 📚 Giải thích Chi tiết Bài 673 - Number of Longest Increasing Subsequence

**Mục tiêu:** Cho một mảng số nguyên `nums`. Hãy tìm và trả về số lượng các dãy con tăng nghiêm ngặt (strictly increasing subsequences) có độ dài lớn nhất (Longest Increasing Subsequence - LIS).

---

## 🎯 Phân tích bài toán

### 1. Dãy con tăng nghiêm ngặt (Strictly Increasing Subsequence)
- Một dãy con của `nums` được tạo thành bằng cách chọn ra một tập các chỉ số $i_0 < i_1 < \dots < i_{k-1}$ sao cho:
  $$nums[i_0] < nums[i_1] < \dots < nums[i_{k-1}]$$
- Tính "nghiêm ngặt" (strictly increasing) đòi hỏi các phần tử kế tiếp phải **lớn hơn hẳn** phần tử trước đó ($nums[i_m] < nums[i_{m+1}]$), hai phần tử bằng nhau không được tính là tăng.
- Độ dài của dãy con này là $k$.

---

### 2. Sự khác biệt then chốt so với bài toán LIS kinh điển (LeetCode 300)
- Trong bài toán LIS kinh điển ([LeetCode 300](https://leetcode.com/problems/longest-increasing-subsequence/)), ta chỉ cần tìm một con số duy nhất: **độ dài lớn nhất** $\max(k)$.
- Với bài toán này, mục tiêu gồm 2 phần liên hoàn:
  1. Xác định **độ dài lớn nhất** có thể đạt được: $maxLen = \max(\text{len})$.
  2. Đếm **tổng số lượng dãy con** đạt đúng độ dài kỷ lục $maxLen$ đó.

---

### 3. Ràng buộc & Đánh giá quy mô (Constraints)
- $1 \le nums.length \le 2000$.
- $-10^6 \le nums[i] \le 10^6$.
- Đề bài đảm bảo kết quả luôn vừa vặn trong kiểu số nguyên có dấu 32-bit (`int` trong Java).
- **Quy mô $N \le 2000$:**
  - Thuật toán Quy hoạch động $\mathcal{O}(N^2)$ sẽ thực hiện tối đa $\frac{N(N-1)}{2} \approx 2 \times 10^6$ phép so sánh.
  - Số lượng phép toán này chạy hoàn tất chỉ trong khoảng $10 - 20\text{ ms}$, hoàn toàn tối ưu và vượt qua mọi giới hạn thời gian (Time Limit Exceeded).

---

## 🔍 Các hướng tiếp cận (Approaches Comparison)

| Tiêu chí | Cách 1: Quy hoạch động 2 Mảng (Dynamic Programming ⭐) | Cách 2: Cây Phân Đoạn / Fenwick Tree (Segment Tree / BIT) |
| :--- | :--- | :--- |
| **Ý tưởng** | Duyệt 2 vòng lặp, duy trì đồng thời `len[i]` (độ dài tối đa) và `count[i]` (số cách đạt độ dài đó) | Nén tọa độ, dùng Segment Tree hoặc Fenwick Tree lưu trữ cặp `(maxLen, count)` |
| **Độ phức tạp Thời gian** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N \log N)$ |
| **Độ phức tạp Bộ nhớ** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Độ phức tạp Cài đặt** | Ngắn gọn, trực quan, hoàn toàn không có lỗi biên | Phức tạp, code dài, cần thêm bước nén tọa độ |
| **Đánh giá** | **Giải pháp chuẩn mực nhất trong phỏng vấn**, dễ giải thích và kiểm chứng | Thích hợp khi mở rộng bài toán cho $N \ge 10^5$ |

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Quy Hoạch Động 2 Mảng (Dynamic Programming)

### 💡 Ý tưởng thuật toán cốt lõi (Core Insights)

Ta mở rộng thuật toán Quy hoạch động kinh điển bằng cách sử dụng hai mảng song song:
- `len[i]`: Độ dài của dãy con tăng dài nhất kết thúc tại chỉ số $i$ (bắt buộc chứa $nums[i]$).
- `count[i]`: Số lượng dãy con tăng đạt độ dài `len[i]` kết thúc tại chỉ số $i$.

#### 1. Khởi tạo cơ sở (Base Cases)
- Mỗi phần tử đứng một mình luôn tạo thành một dãy con tăng có độ dài là 1:
  - `len[i] = 1`
  - `count[i] = 1` (chỉ có duy nhất 1 cách là chính phần tử đó)

#### 2. Công thức truy hồi (State Transitions)
Với mỗi vị trí $i$ từ $0$ đến $n - 1$, ta xét tất cả các vị trí $j$ đứng trước nó ($0 \le j < i$):
Nếu $nums[j] < nums[i]$ (phần tử $nums[i]$ có thể nối vào sau dãy con kết thúc tại $j$):
- **Trường hợp 1: `len[j] + 1 > len[i]` (Tìm thấy kỷ lục độ dài mới)**
  - Việc nối $nums[i]$ vào sau dãy con kết thúc ở $j$ giúp tạo ra một dãy con **dài hơn** bất kỳ dãy con nào từng kết thúc ở $i$ trước đó.
  - Ta cập nhật kỷ lục mới:
    $$len[i] = len[j] + 1$$
    $$count[i] = count[j]$$
  > [!NOTE]
  > Số cách tạo dãy mới tại $i$ lúc này được kế thừa toàn bộ từ $j$ (`count[i] = count[j]`). Tất cả các dãy con ngắn hơn từng kết thúc tại $i$ trước đây đều bị loại bỏ vì không còn đạt độ dài tối đa nữa.

- **Trường hợp 2: `len[j] + 1 == len[i]` (Bắt gặp thêm các dãy con có cùng độ dài tối đa)**
  - Nối $nums[i]$ vào sau dãy con kết thúc tại $j$ tạo ra một nhánh dãy con mới có độ dài **ngang bằng với kỷ lục hiện tại** của $i$.
  - Do đó, ta cộng dồn số lượng dãy con từ nhánh $j$ vào nhánh $i$:
    $$count[i] = count[i] + count[j]$$

- **Trường hợp 3: `len[j] + 1 < len[i]`**
  - Dãy con này có độ dài ngắn hơn kỷ lục tại $i$, ta bỏ qua không xét.

#### 3. Tổng hợp kết quả toàn cục (Final Aggregation)
- Tìm độ dài lớn nhất trên toàn bộ mảng:
  $$maxLen = \max_{0 \le i < n} (len[i])$$
- Số lượng dãy con tăng dài nhất toàn cục chính là tổng số cách `count[i]` của tất cả các vị trí $i$ mà tại đó $len[i] = maxLen$:
  $$result = \sum_{i: len[i] = maxLen} count[i]$$

---

## 🔄 Từng bước thực thi (Dry-run qua Ví dụ cụ thể)

### Ví dụ 1: `nums = [1, 3, 5, 4, 7]`

Ban đầu: `len = [1, 1, 1, 1, 1]`, `count = [1, 1, 1, 1, 1]`

| $i$ | $nums[i]$ | So sánh với các $j < i$ | Cập nhật `len[i]` | Cập nhật `count[i]` | Giải thích |
| :---: | :---: | :--- | :---: | :---: | :--- |
| **0** | `1` | Không có phần tử trước | `1` | `1` | Dãy: `[1]` |
| **1** | `3` | $j=0$ ($1 < 3$): $len[0] + 1 = 2 > 1$ | `2` | `1` | Dãy: `[1, 3]` |
| **2** | `5` | $j=0$ ($1 < 5$): $len[0] + 1 = 2 > 1$<br>$j=1$ ($3 < 5$): $len[1] + 1 = 3 > 2$ | `3` | `1` | Dãy: `[1, 3, 5]` |
| **3** | `4` | $j=0$ ($1 < 4$): $len[0] + 1 = 2 > 1$<br>$j=1$ ($3 < 4$): $len[1] + 1 = 3 > 2$<br>$j=2$ ($5 > 4$): Bỏ qua | `3` | `1` | Dãy: `[1, 3, 4]` |
| **4** | `7` | $j=0$ ($1 < 7$): $len[0] + 1 = 2 > 1$<br>$j=1$ ($3 < 7$): $len[1] + 1 = 3 > 2$<br>$j=2$ ($5 < 7$): $len[2] + 1 = 4 > 3$<br>$j=3$ ($4 < 7$): $len[3] + 1 = 4 == len[4]$ | `4` | `2` | Hai nhánh hội tụ:<br>- Nối sau 5: `[1, 3, 5, 7]`<br>- Nối sau 4: `[1, 3, 4, 7]`<br>$\implies count[4] = 1 + 1 = 2$ |

- Độ dài lớn nhất: $maxLen = \max(1, 2, 3, 3, 4) = 4$.
- Chỉ có chỉ số $i = 4$ có $len[4] = 4 \implies result = count[4] = 2$.
- 👉 **Kết quả: 2**.

---

### Ví dụ 2: `nums = [2, 2, 2, 2, 2]`

- Vì tất cả các phần tử đều bằng nhau, điều kiện tăng ngặt $nums[j] < nums[i]$ không bao giờ thỏa mãn.
- Cả hai mảng giữ nguyên giá trị khởi tạo:
  - `len = [1, 1, 1, 1, 1]`
  - `count = [1, 1, 1, 1, 1]`
- $maxLen = 1$.
- Tất cả các vị trí đều đạt $maxLen = 1 \implies result = \sum count[i] = 1 + 1 + 1 + 1 + 1 = 5$.
- 👉 **Kết quả: 5**.

---

## 💻 Cài đặt Thuật toán Chi tiết (Java Implementation)

### Cách 1: Quy hoạch động 2 Mảng $\mathcal{O}(N^2)$ (Khuyên dùng) ⭐

```java
import java.util.Arrays;

class Solution {
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return n;
        }

        // len[i]: Độ dài dãy con tăng dài nhất kết thúc tại i
        int[] len = new int[n];
        // count[i]: Số lượng dãy con tăng dài nhất kết thúc tại i
        int[] count = new int[n];

        // Khởi tạo mỗi phần tử đơn lẻ có độ dài 1 và số cách là 1
        Arrays.fill(len, 1);
        Arrays.fill(count, 1);

        int maxLen = 1;

        // Vòng lặp quy hoạch động
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    if (len[j] + 1 > len[i]) {
                        // Tìm thấy độ dài lớn hơn: cập nhật kỷ lục mới
                        len[i] = len[j] + 1;
                        count[i] = count[j]; // Reset số cách theo nhánh j
                    } else if (len[j] + 1 == len[i]) {
                        // Bắt gặp thêm nhánh có cùng độ dài tối đa: cộng dồn số cách
                        count[i] += count[j];
                    }
                }
            }
            // Ghi nhận độ dài lớn nhất trên toàn mảng
            if (len[i] > maxLen) {
                maxLen = len[i];
            }
        }

        // Tính tổng số lượng dãy con đạt độ dài kỷ lục maxLen
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (len[i] == maxLen) {
                result += count[i];
            }
        }

        return result;
    }
}
```

---

### Cách 2 (Mở rộng): Cây Chỉ Số Nhị Phân Fenwick Tree $\mathcal{O}(N \log N)$

Dành cho trường hợp quy mô mảng mở rộng lên tới $N = 10^5$:

```java
import java.util.Arrays;

class SolutionBIT {
    // Lớp nút lưu trữ cặp (độ dài lớn nhất, số lượng cách)
    static class Node {
        int length;
        int count;

        Node(int length, int count) {
            this.length = length;
            this.count = count;
        }
    }

    // Hàm hợp nhất hai nút
    private static Node merge(Node n1, Node n2) {
        if (n1.length > n2.length) return new Node(n1.length, n1.count);
        if (n2.length > n1.length) return new Node(n2.length, n2.count);
        if (n1.length == 0) return new Node(0, 0);
        return new Node(n1.length, n1.count + n2.count);
    }

    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        if (n <= 1) return n;

        // Nén tọa độ (Coordinate Compression)
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int m = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0 || sorted[i] != sorted[i - 1]) {
                sorted[m++] = sorted[i];
            }
        }

        // Fenwick Tree (1-indexed)
        Node[] tree = new Node[m + 1];
        for (int i = 0; i <= m; i++) {
            tree[i] = new Node(0, 0);
        }

        for (int x : nums) {
            // Tìm thứ hạng nén tọa độ (1-indexed)
            int rank = Arrays.binarySearch(sorted, 0, m, x) + 1;

            // Truy vấn tiền tố với các giá trị < x (từ 1 đến rank - 1)
            Node best = new Node(0, 0);
            for (int i = rank - 1; i > 0; i -= (i & -i)) {
                best = merge(best, tree[i]);
            }

            // Tạo trạng thái mới cho x
            int curLen = best.length + 1;
            int curCount = Math.max(best.count, 1);

            // Cập nhật vào Fenwick Tree tại vị trí rank
            Node cur = new Node(curLen, curCount);
            for (int i = rank; i <= m; i += (i & -i)) {
                tree[i] = merge(tree[i], cur);
            }
        }

        // Truy vấn toàn bộ để lấy kết quả
        Node total = new Node(0, 0);
        for (int i = m; i > 0; i -= (i & -i)) {
            total = merge(total, tree[i]);
        }

        return total.count;
    }
}
```

---

## 🔍 Giải thích từng dòng lệnh chi tiết (Line-by-Line Walkthrough)

Dưới đây là chi tiết từng bước trong giải pháp Quy hoạch động:

1. **`if (n <= 1) return n;`**:
   - Trường hợp suy biến: mảng rỗng trả về 0, mảng có đúng 1 phần tử trả về 1.
2. **`Arrays.fill(len, 1); Arrays.fill(count, 1);`**:
   - Thiết lập giá trị biên ban đầu cho toàn bộ mảng. Mọi phần tử đều có thể đứng độc lập như một dãy con độ dài 1.
3. **`for (int i = 0; i < n; i++)` & `for (int j = 0; j < i; j++)`**:
   - Hai vòng lặp lồng nhau duyệt qua từng cặp chỉ số $(i, j)$ với $j < i$.
4. **`if (nums[j] < nums[i])`**:
   - Kiểm tra điều kiện tăng nghiêm ngặt trước khi xem xét việc nối chuỗi.
5. **`if (len[j] + 1 > len[i]) { len[i] = len[j] + 1; count[i] = count[j]; }`**:
   - Khi tìm được dãy con có độ dài lớn hơn, ta cập nhật lại `len[i]` và thay thế hoàn toàn `count[i]` bằng số cách của `count[j]`.
6. **`else if (len[j] + 1 == len[i]) { count[i] += count[j]; }`**:
   - Khi tìm được một nhánh khác cũng cho ra cùng độ dài tối đa, ta cộng dồn số cách từ nhánh đó vào `count[i]`.
7. **`if (len[i] == maxLen) result += count[i];`**:
   - Vòng lặp cuối duyệt qua tất cả các vị trí kết thúc $i$: hễ vị trí nào đạt độ dài kỷ lục $maxLen$, ta cộng toàn bộ số lượng dãy con `count[i]` vào kết quả chung cuộc.

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### Độ phức tạp Thời gian (Time Complexity)
- $\mathcal{O}(N^2)$:
  - Vòng lặp ngoài chạy $N$ lần, vòng lặp trong chạy trung bình $\frac{N}{2}$ lần.
  - Tổng số bước lặp là $\frac{N(N-1)}{2} \approx \frac{N^2}{2}$.
  - Với $N = 2000$, số phép toán vào khoảng $2 \times 10^6$, máy tính thực thi trong khoảng $15\text{ ms}$.

### Độ phức tạp Không gian (Space Complexity)
- $\mathcal{O}(N)$:
  - Chỉ sử dụng 2 mảng số nguyên một chiều `len` và `count` với kích thước đúng bằng $N$.
  - Dung lượng bộ nhớ xấp xỉ $2 \times 2000 \times 4\text{ bytes} \approx 16\text{ KB}$, cực kỳ tiết kiệm.

---

## 💡 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways & Interview Tips)

1. **Mô hình hóa bài toán đếm trong Quy hoạch động:**
   - Khi một bài toán tối ưu hóa (Optimization - tìm $\max$ / $\min$) được chuyển thành bài toán đếm (Counting - đếm số cách đạt tối ưu), kỹ thuật thường dùng là **bổ sung thêm một mảng/biến đếm song song**.
   - Quy tắc vàng:
     - Gặp giá trị tốt hơn: **Gán lại (Reset)** mảng đếm (`count = previous_count`).
     - Gặp giá trị bằng: **Cộng dồn (Accumulate)** mảng đếm (`count += previous_count`).
     - Gặp giá trị kém hơn: **Bỏ qua (Ignore)**.

2. **Cách nhận diện bài toán nên dùng $\mathcal{O}(N^2)$ hay $\mathcal{O}(N \log N)$:**
   - Nhìn vào Constraints: $N \le 2000 \implies$ Người ra đề bài muốn bạn trình bày thuật toán $\mathcal{O}(N^2)$ rõ ràng, súc tích và chuẩn xác.
   - Nếu đề bài cho $N = 10^5$, hãy chủ động đề xuất giải pháp Fenwick Tree hoặc Cây phân đoạn (Segment Tree).

3. **Giao tiếp khi phỏng vấn:**
   - Hãy bắt đầu bằng cách nhắc lại bài toán LeetCode 300 (LIS) kinh điển để chứng minh nền tảng vững chắc.
   - Sau đó chỉ ra sự khác biệt: cần duy trì thêm mảng `count[i]` để đếm số lượng đường đi tối ưu.
   - Trình bày rõ ràng 2 nhánh điều kiện: `len[j] + 1 > len[i]` (thay thế) và `len[j] + 1 == len[i]` (cộng dồn) trước khi bắt tay vào viết code.
