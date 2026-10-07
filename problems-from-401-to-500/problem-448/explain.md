# 📚 Giải thích Chi tiết Bài 624 - Maximum Distance in Arrays

**Mục tiêu:** Cho $m$ mảng số nguyên, trong đó mỗi mảng đã được sắp xếp theo thứ tự tăng dần. Nhiệm vụ là chọn ra hai số nguyên từ **hai mảng khác nhau** (mỗi mảng chọn đúng một số) sao cho khoảng cách tuyệt đối $|a - b|$ giữa hai số đó đạt giá trị lớn nhất có thể. Trả về khoảng cách lớn nhất đó.

---

## 🎯 Phân tích bài toán

### 1. Đặc điểm đầu vào & Ràng buộc
- Số lượng mảng $m$: $2 \le m \le 10^5$.
- Độ dài mỗi mảng: $1 \le \text{arrays}[i].\text{length} \le 500$.
- Giá trị phần tử: $-10^4 \le \text{arrays}[i][j] \le 10^4$.
- **Mỗi mảng đã được sắp xếp tăng dần:**
  - Phần tử nhỏ nhất của mảng $i$ luôn nằm ở vị trí đầu tiên: `arrays.get(i).get(0)`.
  - Phần tử lớn nhất của mảng $i$ luôn nằm ở vị trí cuối cùng: `arrays.get(i).get(arrays.get(i).size() - 1)`.
- Tổng số phần tử trong toàn bộ các mảng không vượt quá $10^5$.

---

### 2. Bản chất bài toán & Phân tích khoảng cách
- Khoảng cách giữa hai số $a$ và $b$ là giá trị tuyệt đối $|a - b|$.
- Để $|a - b|$ đạt giá trị lớn nhất, ta luôn muốn chọn một số **nhỏ nhất có thể** và một số **lớn nhất có thể**.
- Vì mỗi mảng đã được sắp xếp tăng dần, số nhỏ nhất trong một mảng luôn là phần tử đầu tiên, và số lớn nhất luôn là phần tử cuối cùng. Bất kỳ phần tử nào ở giữa mảng đều nằm trong khoảng $[\text{min}, \text{max}]$ của mảng đó, do đó không bao giờ có thể tạo ra khoảng cách lớn hơn phần tử ở hai đầu.
- Do đó, ta **chỉ cần quan tâm đến phần tử đầu tiên và phần tử cuối cùng của mỗi mảng**. Mọi phần tử ở giữa đều có thể bỏ qua!

---

### 3. Cạm bẫy tiềm ẩn (Ràng buộc hai mảng khác nhau)
- Ràng buộc quan trọng nhất: **Hai số phải được chọn từ hai mảng khác nhau** ($i \neq j$).
- Nếu ta chỉ đơn giản tìm giá trị nhỏ nhất trên toàn bộ các mảng (`globalMin`) và giá trị lớn nhất trên toàn bộ các mảng (`globalMax`), có thể xảy ra trường hợp cả hai giá trị này cùng thuộc **một mảng duy nhất**.
  - *Ví dụ:* `arrays = [[1, 10], [2, 3]]`.
    - Phần tử nhỏ nhất toàn cục là `1` (ở mảng 0).
    - Phần tử lớn nhất toàn cục là `10` (cũng ở mảng 0).
    - Ta **không được** chọn cả `1` và `10` vì chúng cùng nằm ở mảng 0. Khoảng cách hợp lệ lớn nhất là $|10 - 2| = 8$ (hoặc $|3 - 1| = 2$).
- Do đó, thuật toán phải đảm bảo hai phần tử được ghép cặp luôn đến từ hai mảng riêng biệt.

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Tham lam duyệt một lượt (Single-Pass Greedy)

### 💡 Ý tưởng đột phá (Key Insights)
Thay vì so sánh từng cặp mảng ($\mathcal{O}(m^2)$), ta có thể duyệt qua các mảng theo thứ tự từ trái sang phải trong đúng **1 vòng lặp**:
1. Duy trì hai giá trị của các mảng **đã duyệt qua trước đó** ($0$ đến $i - 1$):
   - `minVal`: Giá trị nhỏ nhất từng gặp ở đầu các mảng trước đó.
   - `maxVal`: Giá trị lớn nhất từng gặp ở cuối các mảng trước đó.
2. Khi xét mảng hiện tại thứ $i$:
   - Phần tử nhỏ nhất của mảng hiện tại là `curMin = arrays.get(i).get(0)`.
   - Phần tử lớn nhất của mảng hiện tại là `curMax = arrays.get(i).get(arrays.get(i).size() - 1)`.
   - Ghép cặp mảng hiện tại $i$ với các mảng trước đó:
     - Trường hợp 1: Mảng hiện tại đóng góp giá trị lớn nhất `curMax`, mảng trước đóng góp giá trị nhỏ nhất `minVal`:
       $$\text{dist}_1 = \text{curMax} - \text{minVal}$$
     - Trường hợp 2: Mảng trước đóng góp giá trị lớn nhất `maxVal`, mảng hiện tại đóng góp giá trị nhỏ nhất `curMin`:
       $$\text{dist}_2 = \text{maxVal} - \text{curMin}$$
   - Cập nhật kết quả tối đa:
     $$\text{maxDist} = \max(\text{maxDist}, \text{dist}_1, \text{dist}_2)$$
3. **Cập nhật `minVal` và `maxVal` cho các bước sau:**
   - Sau khi đã tính xong khoảng cách giữa mảng $i$ và các mảng trước đó, ta mới gộp `curMin` và `curMax` vào `minVal` và `maxVal`:
     $$\text{minVal} = \min(\text{minVal}, \text{curMin})$$
     $$\text{maxVal} = \max(\text{maxVal}, \text{curMax})$$
4. **Tại sao điều này đảm bảo tính đúng đắn 100%?**
   - Với mọi cặp mảng tối ưu $(j, k)$ với $j < k$, khi vòng lặp xét đến mảng $k$, mảng $j$ chắc chắn đã được đưa vào `minVal` hoặc `maxVal`. Do đó cặp tối ưu này chắc chắn được kiểm tra.
   - Vì ta cập nhật `maxDist` **trước khi** cập nhật `minVal` và `maxVal` với mảng hiện tại, hai giá trị tạo nên khoảng cách luôn thuộc về hai mảng khác nhau.

### 🌟 Ưu điểm vượt trội của giải pháp
- **Tốc độ tối đa ($\mathcal{O}(m)$):** Chỉ cần một vòng lặp đơn qua danh sách mảng.
- **Tiết kiệm bộ nhớ tuyệt đối ($\mathcal{O}(1)$):** Chỉ dùng vài biến nguyên lưu trạng thái.
- **Mã nguồn ngắn gọn, thanh lịch:** Không cần sắp xếp lại mảng hay quản lý chỉ số phức tạp.

---

### 💻 Mã nguồn Java (Best Solution)

```java
import java.util.List;

class Solution {
    /**
     * Best Solution: Kỹ thuật Tham lam duyệt 1 lần (Single-Pass Greedy)
     * 
     * Time Complexity: O(m) - với m là số lượng mảng, mỗi mảng chỉ truy xuất phần tử đầu và cuối O(1).
     * Space Complexity: O(1) - chỉ dùng một vài biến phụ trợ để theo dõi min và max.
     */
    public int maxDistance(List<List<Integer>> arrays) {
        // Khởi tạo minVal và maxVal từ mảng đầu tiên
        List<Integer> firstArray = arrays.get(0);
        int minVal = firstArray.get(0);
        int maxVal = firstArray.get(firstArray.size() - 1);
        int maxDist = 0;

        int m = arrays.size();
        // Duyệt qua các mảng còn lại từ vị trí 1 đến m - 1
        for (int i = 1; i < m; i++) {
            List<Integer> currentArray = arrays.get(i);
            int currentMin = currentArray.get(0);
            int currentMax = currentArray.get(currentArray.size() - 1);

            // Cập nhật khoảng cách lớn nhất có thể giữa mảng hiện tại và các mảng trước đó
            // Đảm bảo 2 phần tử luôn thuộc 2 mảng khác nhau vì minVal và maxVal được tính từ các mảng [0..i-1]
            maxDist = Math.max(maxDist, Math.max(currentMax - minVal, maxVal - currentMin));

            // Cập nhật minVal và maxVal toàn cục cho các bước tiếp theo
            minVal = Math.min(minVal, currentMin);
            maxVal = Math.max(maxVal, currentMax);
        }

        return maxDist;
    }
}
```

---

### ⚙️ Phân tích độ phức tạp (Complexity Analysis)

| Thành phần | Độ phức tạp | Giải thích chi tiết |
| :--- | :--- | :--- |
| **Thời gian (Time Complexity)** | $\mathcal{O}(m)$ | Vòng lặp duyệt qua $m - 1$ mảng. Với mỗi mảng, thao tác `get(0)` và `get(size - 1)` trên `ArrayList` tốn thời gian $\mathcal{O}(1)$. Các phép so sánh `Math.max` và `Math.min` đều tốn $\mathcal{O}(1)$. Tổng thời gian là $\mathcal{O}(m)$. |
| **Không gian (Space Complexity)** | $\mathcal{O}(1)$ | Thuật toán chỉ sử dụng bốn biến kiểu số nguyên nguyên thủy (`minVal`, `maxVal`, `maxDist`, `m`, `currentMin`, `currentMax`), không tạo thêm mảng phụ hay cấu trúc dữ liệu nào. |

---

## 🔍 Cách hoạt động từng bước (Walkthrough)

### 1. Ví dụ 1 từ đề bài
`arrays = [[1, 2, 3], [4, 5], [1, 2, 3]]` ($m = 3$)

- **Khởi tạo:**
  - Lấy mảng 0: `[1, 2, 3]`
  - `minVal = 1`, `maxVal = 3`, `maxDist = 0`

- **Bước 1 ($i = 1$):**
  - Mảng 1: `[4, 5]` $\implies$ `curMin = 4`, `curMax = 5`
  - Khoảng cách tiềm năng:
    - $\text{curMax} - \text{minVal} = 5 - 1 = 4$
    - $\text{maxVal} - \text{curMin} = 3 - 4 = -1$
  - $\text{maxDist} = \max(0, 4, -1) = 4$
  - Cập nhật min/max:
    - `minVal = min(1, 4) = 1`
    - `maxVal = max(3, 5) = 5`

- **Bước 2 ($i = 2$):**
  - Mảng 2: `[1, 2, 3]` $\implies$ `curMin = 1`, `curMax = 3`
  - Khoảng cách tiềm năng:
    - $\text{curMax} - \text{minVal} = 3 - 1 = 2$
    - $\text{maxVal} - \text{curMin} = 5 - 1 = 4$
  - $\text{maxDist} = \max(4, 2, 4) = 4$
  - Cập nhật min/max:
    - `minVal = min(1, 1) = 1`
    - `maxVal = max(5, 3) = 5`

- **Kết quả trả về:** `maxDist = 4` (Chọn số 1 ở mảng 0 hoặc mảng 2 và số 5 ở mảng 1).

---

### 2. Ví dụ 2 từ đề bài
`arrays = [[1], [1]]` ($m = 2$)

- **Khởi tạo:**
  - Lấy mảng 0: `[1]`
  - `minVal = 1`, `maxVal = 1`, `maxDist = 0`

- **Bước 1 ($i = 1$):**
  - Mảng 1: `[1]` $\implies$ `curMin = 1`, `curMax = 1`
  - Khoảng cách tiềm năng:
    - $\text{curMax} - \text{minVal} = 1 - 1 = 0$
    - $\text{maxVal} - \text{curMin} = 1 - 1 = 0$
  - $\text{maxDist} = \max(0, 0, 0) = 0$
  - Cập nhật: `minVal = 1`, `maxVal = 1`

- **Kết quả trả về:** `maxDist = 0`.

---

### 3. Ví dụ cạm bẫy: Cực trị toàn cục nằm chung một mảng
`arrays = [[1, 10], [2, 3]]` ($m = 2$)

- Min toàn cục là `1` và Max toàn cục là `10`, cả hai đều thuộc mảng 0.
- **Khởi tạo:**
  - Mảng 0: `minVal = 1`, `maxVal = 10`, `maxDist = 0`
- **Bước 1 ($i = 1$):**
  - Mảng 1: `curMin = 2`, `curMax = 3`
  - Khoảng cách:
    - $\text{curMax} - \text{minVal} = 3 - 1 = 2$
    - $\text{maxVal} - \text{curMin} = 10 - 2 = 8$
  - $\text{maxDist} = \max(0, 2, 8) = 8$
- Thuật toán tự động tránh được việc ghép `1` và `10` với nhau và cho ra kết quả chính xác là `8` (ghép `10` ở mảng 0 với `2` ở mảng 1).

---

## 🔄 CÁC CÁCH TIẾP CẬN KHÁC (Alternative Approaches)

### Cách 1: Theo dõi 2 Min nhỏ nhất và 2 Max lớn nhất (Tracking Top 2 Mins & Top 2 Maxs)
Ta duyệt một lượt qua tất cả $m$ mảng để tìm:
- Phần tử nhỏ nhất toàn cục `min1` (thuộc mảng `minIdx1`) và nhỏ nhì `min2` (thuộc mảng `minIdx2`).
- Phần tử lớn nhất toàn cục `max1` (thuộc mảng `maxIdx1`) và lớn nhì `max2` (thuộc mảng `maxIdx2`).

Sau đó xét hai trường hợp:
1. Nếu `minIdx1 != maxIdx1`: Hai phần tử cực trị đến từ hai mảng khác nhau $\implies \text{Kết quả} = \text{max1} - \text{min1}$.
2. Nếu `minIdx1 == maxIdx1`: Cực trị cùng một mảng, ta ghép chéo:
   $$\text{Kết quả} = \max(\text{max1} - \text{min2}, \text{max2} - \text{min1})$$

```java
import java.util.List;

class SolutionTop2 {
    public int maxDistance(List<List<Integer>> arrays) {
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        int minIdx1 = -1, minIdx2 = -1;

        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE;
        int maxIdx1 = -1, maxIdx2 = -1;

        for (int i = 0; i < arrays.size(); i++) {
            List<Integer> arr = arrays.get(i);
            int first = arr.get(0);
            int last = arr.get(arr.size() - 1);

            // Cập nhật 2 min nhỏ nhất
            if (first < min1) {
                min2 = min1;
                minIdx2 = minIdx1;
                min1 = first;
                minIdx1 = i;
            } else if (first < min2) {
                min2 = first;
                minIdx2 = i;
            }

            // Cập nhật 2 max lớn nhất
            if (last > max1) {
                max2 = max1;
                maxIdx2 = maxIdx1;
                max1 = last;
                maxIdx1 = i;
            } else if (last > max2) {
                max2 = last;
                maxIdx2 = i;
            }
        }

        // Kiểm tra xem min1 và max1 có thuộc cùng 1 mảng không
        if (minIdx1 != maxIdx1) {
            return max1 - min1;
        }

        return Math.max(max1 - min2, max2 - min1);
    }
}
```
- **Thời gian:** $\mathcal{O}(m)$.
- **Không gian:** $\mathcal{O}(1)$.
- **Nhược điểm:** Phải quản lý nhiều biến trạng thái chỉ số, logic điều kiện phức tạp hơn cách duyệt tham lam 1 lượt.

---

### Cách 2: So sánh vét cạn mọi cặp mảng (Brute Force Pair Comparison)
Duyệt qua tất cả các cặp mảng $(i, j)$ với $0 \le i < j < m$:
$$\text{dist} = \max(|\text{arrays}[i].\text{last} - \text{arrays}[j].\text{first}|, |\text{arrays}[j].\text{last} - \text{arrays}[i].\text{first}|)$$

```java
import java.util.List;

class SolutionBruteForce {
    public int maxDistance(List<List<Integer>> arrays) {
        int maxDist = 0;
        int m = arrays.size();
        for (int i = 0; i < m - 1; i++) {
            List<Integer> a = arrays.get(i);
            for (int j = i + 1; j < m; j++) {
                List<Integer> b = arrays.get(j);
                int d1 = Math.abs(a.get(a.size() - 1) - b.get(0));
                int d2 = Math.abs(b.get(b.size() - 1) - a.get(0));
                maxDist = Math.max(maxDist, Math.max(d1, d2));
            }
        }
        return maxDist;
    }
}
```
- **Thời gian:** $\mathcal{O}(m^2)$. Với $m = 10^5$, số phép tính là $\approx \frac{10^{10}}{2} = 5 \times 10^9 \implies$ **Dẫn đến lỗi quá thời gian (Time Limit Exceeded - TLE)**.
- **Không gian:** $\mathcal{O}(1)$.

---

## ⚠️ Các trường hợp biên quan trọng (Edge Cases)

| Trường hợp | Ví dụ | Cách thuật toán xử lý |
| :--- | :--- | :--- |
| **Số lượng mảng tối thiểu ($m = 2$)** | `arrays = [[1, 5], [3, 4]]` | Vòng lặp chạy đúng 1 lần ($i = 1$). Kết quả tính giữa mảng 0 và 1, luôn chính xác. |
| **Mỗi mảng chỉ có đúng 1 phần tử** | `arrays = [[1], [1]]` | `curMin` trùng `curMax`. Kết quả tính hiệu giữa các phần tử đơn lẻ này. |
| **Giá trị âm** | `arrays = [[-10, -5], [-2, 0]]` | `Math.max` và phép trừ số âm vẫn bảo toàn độ lớn khoảng cách hình học. |
| **Các mảng hoàn toàn giống nhau** | `arrays = [[1, 2, 3], [1, 2, 3]]` | Khoảng cách cực đại giữa phần tử cuối của mảng này và đầu của mảng kia: $3 - 1 = 2$. |
| **Cực trị nằm chung một mảng** | `arrays = [[-100, 100], [0, 0]]` | Đảm bảo không ghép `-100` với `100`. Kết quả đúng là $100 - 0 = 100$. |

---

## 📊 Bảng so sánh các phương pháp

| Tiêu chí | ⭐ Tham lam 1 lượt (Single-Pass Greedy) | Theo dõi Top 2 Min/Max | Vét cạn (Brute Force) |
| :--- | :---: | :---: | :---: |
| **Độ phức tạp thời gian** | $\mathcal{O}(m)$ | $\mathcal{O}(m)$ | $\mathcal{O}(m^2)$ (TLE) |
| **Độ phức tạp không gian** | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ |
| **Số dòng mã** | **~25 dòng** | ~45 dòng | ~20 dòng |
| **Độ phức tạp tư duy** | Rất trực quan và khéo léo | Cần phân chia nhiều case | Đơn giản nhưng không thực thi được |
| **Tốc độ thực tế (LeetCode)** | **~4 - 5 ms (Beats 99 - 100%)** | ~5 - 6 ms | Quá thời gian (TLE) |

---

## 💡 Mẹo & Lời khuyên khi phỏng vấn (Interview Tips)

1. **Nhận diện dữ kiện "mảng đã sắp xếp":**
   - Khi bài toán cho biết mảng đã sắp xếp, giá trị cực đại và cực tiểu của mỗi mảng luôn nằm tại hai đầu. Bất cứ khi nào bạn thấy mình đang duyệt qua các phần tử ở giữa mảng, hãy dừng lại và đặt câu hỏi liệu các phần tử đó có cần thiết không.
2. **Kỹ thuật xử lý ràng buộc "khác mảng" khi duyệt trực tuyến:**
   - Việc so sánh mảng hiện tại với giá trị tóm tắt của các mảng **đã qua** trước khi cập nhật tóm tắt là một kỹ thuật kinh điển để đảm bảo hai phần tử thuộc hai tập hợp riêng biệt mà không cần lưu chỉ số (indices).
3. **Các bài toán liên quan nên luyện tập:**
   - [LeetCode 53: Maximum Subarray](https://leetcode.com/problems/maximum-subarray/)
   - [LeetCode 121: Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/)
   - [LeetCode 621: Task Scheduler](https://leetcode.com/problems/task-scheduler/)
   - [LeetCode 628: Maximum Product of Three Numbers](https://leetcode.com/problems/maximum-product-of-three-numbers/)
