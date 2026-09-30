# 📚 Giải thích Chi tiết Bài 611 - Valid Triangle Number

**Mục tiêu:** Cho một mảng các số nguyên không âm `nums`, hãy đếm và trả về số lượng bộ ba chỉ số $(i, j, k)$ đôi một khác nhau (chọn 3 cạnh từ mảng) có thể tạo thành ba cạnh của một tam giác hợp lệ.

---

## 🎯 Phân tích bài toán

### 1. Đặc điểm đầu vào & Ràng buộc
- `1 <= nums.length <= 1000`: Kích thước mảng lên tới $1000$.
- `0 <= nums[i] <= 1000`: Giá trị mỗi phần tử là số nguyên không âm (chú ý: có thể bằng `0`).
- Nếu mảng có ít hơn 3 phần tử ($n < 3$), không thể chọn được bộ 3 cạnh $\implies$ kết quả luôn là `0`.

---

### 2. Bản chất Toán học: Bất đẳng thức Tam giác (Triangle Inequality)
Ba độ dài đoạn thẳng $a, b, c$ ($a, b, c > 0$) tạo thành một tam giác hợp lệ khi và chỉ khi thỏa mãn đồng thời 3 bất đẳng thức:
1. $a + b > c$
2. $a + c > b$
3. $b + c > a$

Nếu kiểm tra theo cách thông thường mà không sắp xếp, ta phải duyệt qua mọi bộ ba $(i, j, k)$ và kiểm tra cả 3 điều kiện trên, dẫn tới độ phức tạp thời gian $\mathcal{O}(N^3)$. Với $N = 1000$, $\frac{N^3}{6} \approx 1.66 \times 10^8$ phép tính, sẽ bị quá thời gian chạy (Time Limit Exceeded - TLE).

---

### 3. Ý tưởng bước ngoặt: Tối ưu điều kiện nhờ Sắp xếp (Sorting Insight)
Giả sử ta sắp xếp mảng theo thứ tự tăng dần sao cho:
$$a \le b \le c$$

Khi đó:
- Do $c \ge b$ và $a > 0 \implies a + c > b$ luôn luôn đúng!
- Do $c \ge a$ và $b > 0 \implies b + c > a$ luôn luôn đúng!

$\implies$ **Điều kiện duy nhất cần kiểm tra chỉ còn là:**
$$a + b > c$$

> **Lưu ý về số 0:** Nếu một cạnh bằng `0`, ví dụ $a = 0, b = 2, c = 2$, thì $a + b > c \iff 0 + 2 > 2$ là sai. Bất đẳng thức $a + b > c$ tự động loại bỏ các cạnh có độ dài bằng 0 mà không cần xử lý ngoại lệ riêng biệt!

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Sắp xếp + Hai con trỏ (Two Pointers)

### 💡 Ý tưởng đột phá (Key Insights)

Thay vì cố định hai cạnh nhỏ hơn rồi tìm cạnh thứ ba, ta hãy làm ngược lại: **Cố định cạnh lớn nhất $c = \text{nums}[k]$** (duyệt từ cuối mảng về đầu: $k = n - 1$ giảm dần đến $2$).

Với mỗi $k$, bài toán quy về: Tìm tất cả các cặp $(i, j)$ với $0 \le i < j < k$ sao cho:
$$\text{nums}[i] + \text{nums}[j] > \text{nums}[k]$$

Đây chính là bài toán kinh điển tương tự 2Sum, có thể giải quyết trong thời gian tuyến tính $\mathcal{O}(k)$ bằng kỹ thuật **Hai con trỏ**:
- Đặt `left = 0` (đại diện cho cạnh nhỏ nhất $a$).
- Đặt `right = k - 1` (đại diện cho cạnh nhì $b$).

Khi xét tổng `nums[left] + nums[right]`:
1. **Nếu `nums[left] + nums[right] > nums[k]`:**
   - Vì mảng đã được sắp xếp tăng dần, nên với cùng một cạnh `nums[right]`, bất kỳ vị trí nào nằm giữa `left` và `right - 1` khi cộng với `nums[right]` cũng đều chắc chắn lớn hơn `nums[k]`:
     $$\text{nums}[left + 1] + \text{nums}[right] \ge \text{nums}[left] + \text{nums}[right] > \text{nums}[k]$$
     $$\dots$$
     $$\text{nums}[right - 1] + \text{nums}[right] \ge \text{nums}[left] + \text{nums}[right] > \text{nums}[k]$$
   - Vậy với cạnh `nums[right]` cố định, có đúng **`right - left`** cặp $(i, \text{right})$ thỏa mãn!
   - Ta cộng ngay `right - left` vào biến đếm `count`.
   - Sau đó giảm `right--` để tiếp tục tìm kiếm với cạnh nhì nhỏ hơn.
2. **Nếu `nums[left] + nums[right] <= nums[k]`:**
   - Tổng của hai cạnh nhỏ nhất và nhì hiện tại không đủ lớn để thắng cạnh $c$.
   - Để tăng tổng, ta bắt buộc phải chọn một cạnh nhỏ lớn hơn $\implies$ tăng `left++`.

---

### 🌟 Ưu điểm vượt trội của giải pháp
- **Độ phức tạp tối ưu $\mathcal{O}(N^2)$:** Vòng lặp ngoài chạy $N$ lần, vòng lặp hai con trỏ bên trong chạy $N$ lần. Tổng số phép toán chỉ khoảng $\approx \frac{N^2}{2} \approx 5 \times 10^5$, thực thi trong khoảng **10ms - 15ms** trên LeetCode (Beats 95-100%).
- **Không gian phụ cực tiểu $\mathcal{O}(1)$ / $\mathcal{O}(\log N)$:** Chỉ dùng các biến con trỏ nguyên bản, không cấp phát thêm cấu trúc dữ liệu nào.
- **Tự nhiên xử lý các số `0`:** Số `0` luôn nằm ở đầu mảng sau khi sắp xếp, khi gặp số `0` thì `nums[left] + nums[right]` không bao giờ lớn hơn `nums[k]`, con trỏ `left` sẽ tự động nhảy qua số `0`.

---

### 💻 Mã nguồn Java (Best Solution)

```java
import java.util.Arrays;

class Solution {
    /**
     * Best Solution: Sắp xếp + Kỹ thuật Hai con trỏ (Two Pointers).
     * 
     * Time Complexity: O(N^2)
     * Space Complexity: O(log N) do thuật toán Dual-Pivot Quicksort của Arrays.sort()
     */
    public int triangleNumber(int[] nums) {
        if (nums == null || nums.length < 3) {
            return 0;
        }

        // Bước 1: Sắp xếp mảng theo thứ tự tăng dần
        Arrays.sort(nums);

        int count = 0;
        int n = nums.length;

        // Bước 2: Cố định cạnh lớn nhất nums[k] từ phải qua trái (từ n - 1 về 2)
        for (int k = n - 1; k >= 2; k--) {
            int left = 0;
            int right = k - 1;

            // Bước 3: Dùng hai con trỏ tìm các cặp (left, right) sao cho nums[left] + nums[right] > nums[k]
            while (left < right) {
                if (nums[left] + nums[right] > nums[k]) {
                    // Do mảng đã được sắp xếp tăng dần:
                    // nums[left] + nums[right] > nums[k]
                    // => nums[left + 1] + nums[right] > nums[k]
                    // ...
                    // => nums[right - 1] + nums[right] > nums[k]
                    // Do đó, có tất cả (right - left) cặp hợp lệ kết hợp với nums[right]
                    count += (right - left);
                    right--; // Thu hẹp biên phải để xét các cặp cạnh khác
                } else {
                    left++; // Tổng hai cạnh <= cạnh lớn nhất -> cần tăng cạnh nhỏ hơn
                }
            }
        }

        return count;
    }
}
```

---

### ⚙️ Phân tích độ phức tạp (Complexity Analysis)

| Thành phần | Độ phức tạp | Giải thích chi tiết |
| :--- | :--- | :--- |
| **Thời gian (Time Complexity)** | $\mathcal{O}(N^2)$ | • Bước sắp xếp mảng `Arrays.sort()` tốn $\mathcal{O}(N \log N)$.<br>• Vòng lặp ngoài chạy $k$ từ $n-1$ về $2$ ($N - 2$ lần).<br>• Với mỗi $k$, hai con trỏ `left` và `right` di chuyển tổng cộng $k$ bước ($\mathcal{O}(k)$).<br>• Tổng số bước lặp: $\sum_{k=2}^{N-1} k = \frac{(N-1)N}{2} - 1 = \mathcal{O}(N^2)$.<br>• Tổng thời gian: $\mathcal{O}(N \log N + N^2) = \mathcal{O}(N^2)$. |
| **Không gian (Space Complexity)** | $\mathcal{O}(\log N)$ | Thuật toán chạy hoàn toàn tại chỗ (in-place). Chi phí bộ nhớ phụ duy nhất là ngăn xếp đệ quy của thuật toán Dual-Pivot Quicksort trong `Arrays.sort(nums)` trong Java ($\mathcal{O}(\log N)$). |

---

## 🔍 Cách hoạt động từng bước (Walkthrough)

### Ví dụ: `nums = [2, 2, 3, 4]`

1. **Sau khi sắp xếp:** `nums = [2, 2, 3, 4]` ($n = 4$).
2. **Khởi tạo:** `count = 0`.

---

#### 📍 Vòng lặp 1: Cố định $k = 3$ (`nums[k] = 4`)
- Cần tìm các cặp trong đoạn $[0, 2]$: `nums[0..2] = [2, 2, 3]` sao cho `nums[left] + nums[right] > 4`.
- Khởi tạo: `left = 0` (`nums[0] = 2`), `right = 2` (`nums[2] = 3`).

| Bước | `left` | `right` | `nums[left]` | `nums[right]` | Tổng (`left + right`) | So sánh với `nums[k] = 4` | Hành động | `count` mới |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :--- | :---: |
| 1.1 | 0 | 2 | 2 | 3 | $2 + 3 = 5$ | $5 > 4$ (Thỏa mãn) | Cộng `right - left = 2 - 0 = 2` bộ ba: <br>• $(nums[0], nums[2], nums[3]) = (2, 3, 4)$<br>• $(nums[1], nums[2], nums[3]) = (2, 3, 4)$<br>Giảm `right--` thành 1. | 2 |
| 1.2 | 0 | 1 | 2 | 2 | $2 + 2 = 4$ | $4 \le 4$ (Không thỏa) | Tăng `left++` thành 1. | 2 |
| 1.3 | 1 | 1 | - | - | - | `left == right` | Dừng vòng lặp while. | 2 |

---

#### 📍 Vòng lặp 2: Cố định $k = 2$ (`nums[k] = 3`)
- Cần tìm các cặp trong đoạn $[0, 1]$: `nums[0..1] = [2, 2]` sao cho `nums[left] + nums[right] > 3`.
- Khởi tạo: `left = 0` (`nums[0] = 2`), `right = 1` (`nums[1] = 2`).

| Bước | `left` | `right` | `nums[left]` | `nums[right]` | Tổng (`left + right`) | So sánh với `nums[k] = 3` | Hành động | `count` mới |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :--- | :---: |
| 2.1 | 0 | 1 | 2 | 2 | $2 + 2 = 4$ | $4 > 3$ (Thỏa mãn) | Cộng `right - left = 1 - 0 = 1` bộ ba:<br>• $(nums[0], nums[1], nums[2]) = (2, 2, 3)$<br>Giảm `right--` thành 0. | 3 |
| 2.2 | 0 | 0 | - | - | - | `left == right` | Dừng vòng lặp while. | 3 |

---

**Kết quả cuối cùng:** `count = 3`. (Chính xác với ví dụ đề bài).

---

## 🔄 CÁC CÁCH TIẾP CẬN KHÁC (Alternative Approaches)

### Cách 1: Vét cạn 3 vòng lặp (Brute Force)
Duyệt qua tất cả các tổ hợp $(i, j, k)$ với $0 \le i < j < k < n$.

```java
class SolutionBruteForce {
    public int triangleNumber(int[] nums) {
        int count = 0;
        int n = nums.length;
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] > nums[k] &&
                        nums[i] + nums[k] > nums[j] &&
                        nums[j] + nums[k] > nums[i]) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
```
- **Thời gian:** $\mathcal{O}(N^3)$ — Bị **Time Limit Exceeded (TLE)** khi $N = 1000$.
- **Không gian:** $\mathcal{O}(1)$.

---

### Cách 2: Sắp xếp + Tìm kiếm nhị phân (Binary Search)
Sau khi sắp xếp mảng, với mỗi cặp $(i, j)$ ($i < j$), ta tìm vị trí $k$ lớn nhất sao cho $\text{nums}[k] < \text{nums}[i] + \text{nums}[j]$ bằng tìm kiếm nhị phân (`binarySearch`).

```java
class SolutionBinarySearch {
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int count = 0;
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            int k = i + 2;
            for (int j = i + 1; j < n - 1 && nums[i] != 0; j++) {
                // Tìm kiếm nhị phân tìm chỉ số k xa nhất thỏa mãn nums[k] < nums[i] + nums[j]
                int low = j + 1, high = n - 1, target = nums[i] + nums[j];
                int validK = j;
                while (low <= high) {
                    int mid = low + (high - low) / 2;
                    if (nums[mid] < target) {
                        validK = mid;
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                }
                count += (validK - j);
            }
        }
        return count;
    }
}
```
- **Thời gian:** $\mathcal{O}(N^2 \log N)$ — Chấp nhận được trên LeetCode (~50ms - 100ms) nhưng chậm hơn đáng kể so với Hai con trỏ.
- **Không gian:** $\mathcal{O}(\log N)$.

---

## ⚠️ Các trường hợp biên quan trọng (Edge Cases)

| Trường hợp | Ví dụ | Cách thuật toán xử lý |
| :--- | :--- | :--- |
| **Mảng ít hơn 3 phần tử** | `nums = [1, 2]` | Điều kiện `nums.length < 3` trả về `0` ngay lập tức. |
| **Mảng chứa nhiều số 0** | `nums = [0, 0, 0, 2, 3, 4]` | Các số 0 nằm ở đầu mảng. Khi `left` trỏ tới số 0, `0 + nums[right] <= nums[k]`, con trỏ `left` sẽ tự động tăng dần lên, bỏ qua số 0 một cách an toàn. |
| **Các phần tử bằng nhau** | `nums = [3, 3, 3, 3]` | Tạo thành các tam giác đều. Thuật toán đếm đúng $\binom{4}{3} = 4$ tam giác. |
| **Không có tam giác nào** | `nums = [1, 2, 4, 8, 16]` | Dãy tăng theo cấp số nhân, tổng 2 số bất kỳ luôn nhỏ hơn số tiếp theo $\implies$ thuật toán trả về `0`. |

---

## 📊 Bảng so sánh các phương pháp

| Tiêu chí | Vét cạn (Brute Force) | Sắp xếp + Nhị phân (Binary Search) | ⭐ Sắp xếp + Hai con trỏ (Two Pointers) |
| :--- | :---: | :---: | :---: |
| **Thời gian (Time)** | $\mathcal{O}(N^3)$ | $\mathcal{O}(N^2 \log N)$ | $\mathcal{O}(N^2)$ |
| **Không gian (Space)** | $\mathcal{O}(1)$ | $\mathcal{O}(\log N)$ | $\mathcal{O}(\log N)$ |
| **Tốc độ thực tế** | TLE ($> 2000$ ms) | ~80 ms | **~10 ms (Top 95-100%)** |
| **Độ phức tạp cài đặt** | Đơn giản nhưng chạy chậm | Cần viết cẩn thận hàm nhị phân | **Ngắn gọn, thanh thoát, tối ưu** |

---

## 💡 Mẹo & Lời khuyên khi phỏng vấn (Interview Tips)

1. **Tại sao lại duyệt $k$ từ phải qua trái thay vì trái qua phải?**
   - Nếu cố định cạnh nhỏ nhất trước và cho 2 con trỏ chạy tìm 2 cạnh lớn hơn, khi `nums[i] + nums[left] > nums[right]`, ta không thể suy ra ngay lập tức số lượng cặp hợp lệ một cách liên tục (vì tăng `left` hay giảm `right` đều có thể vi phạm điều kiện).
   - Khi cố định cạnh **lớn nhất $c = \text{nums}[k]$**, điều kiện trở thành `nums[left] + nums[right] > c`. Khi một cặp đã thỏa mãn, vì mảng tăng dần nên **mọi phần tử từ `left` đến `right - 1` kết hợp với `right` cũng đều thỏa mãn**. Nhờ đó ta đếm được ngay lập tức `(right - left)` cặp chỉ trong $\mathcal{O}(1)$!
2. **Kỹ thuật liên hệ:**
   - Dạng bài này có chung tư duy với bài toán **3Sum** (LeetCode 15) và **3Sum Closest** (LeetCode 16). Việc nắm vững mô hình "Sắp xếp + Cố định 1 biến + Hai con trỏ cho 2 biến còn lại" là vũ khí tối thượng cho nhóm bài toán bộ ba (Triplets).
