# 📚 Giải thích Chi tiết Bài 599 - Minimum Index Sum of Two Lists

**Mục tiêu:** Cho hai mảng chuỗi `list1` và `list2`, nhiệm vụ là tìm các chuỗi xuất hiện ở cả hai mảng sao cho **tổng chỉ số xuất hiện** ($i + j$, với `list1[i] == list2[j]`) đạt giá trị **nhỏ nhất**. Trả về tất cả các chuỗi thỏa mãn điều kiện đó theo thứ tự bất kỳ.

---

## 🎯 Phân tích bài toán

### 1. Đặc điểm đầu vào & Ràng buộc
- `1 <= list1.length, list2.length <= 1000`: Kích thước hai mảng tối đa 1000 phần tử.
- `1 <= list1[i].length, list2[i].length <= 30`: Mỗi chuỗi có độ dài tối đa 30 ký tự.
- **Tất cả chuỗi trong cùng một danh sách đều là duy nhất** (không có chuỗi nào xuất hiện 2 lần trong cùng `list1` hoặc cùng `list2`).
- **Luôn tồn tại ít nhất một chuỗi chung** giữa `list1` và `list2`.

### 2. Cảnh báo cách tiếp cận ngây thơ (Brute-force)
- Dùng 2 vòng lặp lồng nhau duyệt qua mọi cặp `(i, j)`:
  - Nếu `list1[i].equals(list2[j])`: tính tổng $i + j$.
  - Cập nhật giá trị nhỏ nhất và lưu danh sách kết quả.
- **Độ phức tạp:**
  - Thời gian: $\mathcal{O}(m \times n \times L)$ với $m, n$ là độ dài hai danh sách và $L$ là độ dài trung bình của chuỗi khi so sánh `equals`.
  - Với $m = 1000, n = 1000$, ta phải so sánh chuỗi tới $10^6$ lần. Dù vẫn có thể vượt qua bài toán do giới hạn nhỏ, nhưng cách này hoàn toàn không tối ưu khi dữ liệu mở rộng.

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Bảng băm (HashMap) + Cắt tỉa nhánh sớm (Early Termination)

### 💡 Ý tưởng cốt lõi (Key Insights)

1. **Tra cứu $\mathcal{O}(1)$ bằng Bảng băm (HashMap):**
   - Lưu trữ các chuỗi của một mảng vào `HashMap<String, Integer>`, trong đó `Key` là chuỗi và `Value` là chỉ số $i$ của nó trong mảng.
   - Khi duyệt mảng thứ hai tại chỉ số $j$, ta chỉ tốn $\mathcal{O}(L)$ thời gian để kiểm tra xem chuỗi `list2[j]` có nằm trong map hay không và lấy ra chỉ số $i$ tương ứng.
   - Tổng chỉ số là: $\text{sum} = i + j$.

2. **Tối ưu hóa bộ nhớ & thời gian băm (Đưa mảng ngắn hơn vào Map):**
   - Do tính chất giao hoán của phép cộng: $i + j = j + i$.
   - Nếu `list1.length > list2.length`, ta đổi vai trò của hai mảng bằng cách gọi đệ quy `findRestaurant(list2, list1)`.
   - Việc này đảm bảo `HashMap` chỉ cần chứa tối đa $\min(m, n)$ phần tử, giúp tiết kiệm bộ nhớ Heap và giảm số lượng phép tính mã băm (hashing).

3. **Tối ưu cắt tỉa nhánh cực mạnh (Early Termination):**
   - Khi duyệt qua `list2` từ $j = 0 \to n - 1$:
   - Do chỉ số $i$ trong `list1` luôn không âm ($i \ge 0$), nên:
     $$i + j \ge 0 + j = j$$
   - Khi đã tìm được một `minSum` tạm thời, nếu chỉ số $j$ hiện tại **vượt quá `minSum`** ($j > \text{minSum}$):
     - Mọi cặp chung ở các vị trí từ $j$ trở về sau chắc chắn sẽ có tổng $i + j \ge j > \text{minSum}$.
     - Do đó, ta có thể **`break` dừng vòng lặp ngay lập tức**, không cần xét tiếp các phần tử còn lại của `list2`!

---

### 💻 Mã nguồn Java (Best Solution)

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    /**
     * Best Solution: HashMap tra cứu O(1) kết hợp Early Termination.
     *
     * Time Complexity: O(m + n) - tối ưu hóa số phép toán.
     * Space Complexity: O(min(m, n)) - chỉ lưu mảng ngắn hơn vào Map.
     */
    public String[] findRestaurant(String[] list1, String[] list2) {
        // Đảm bảo list1 luôn là mảng có độ dài nhỏ hơn hoặc bằng list2
        // Giúp tiết kiệm dung lượng bộ nhớ HashMap và giảm số lần tính mã băm (hash)
        if (list1.length > list2.length) {
            return findRestaurant(list2, list1);
        }

        // Khởi tạo HashMap với dung lượng dự tính để tránh hiện tượng rehashing
        Map<String, Integer> map = new HashMap<>(list1.length * 4 / 3 + 1);
        for (int i = 0; i < list1.length; i++) {
            map.put(list1[i], i);
        }

        List<String> result = new ArrayList<>();
        int minSum = Integer.MAX_VALUE;

        for (int j = 0; j < list2.length; j++) {
            // Tối ưu cắt tỉa nhánh (Early Termination):
            // Do i >= 0 nên tổng chỉ số i + j >= j.
            // Nếu j > minSum thì mọi cặp sau này chắc chắn có tổng lớn hơn minSum.
            if (j > minSum) {
                break;
            }

            Integer i = map.get(list2[j]);
            if (i != null) {
                int sum = i + j;
                if (sum < minSum) {
                    minSum = sum;
                    result.clear();
                    result.add(list2[j]);
                } else if (sum == minSum) {
                    result.add(list2[j]);
                }
            }
        }

        return result.toArray(new String[result.size()]);
    }
}
```

---

### 🔍 Giải thích chi tiết từng dòng mã

1. **Đổi vị trí hai mảng nếu cần:**
   ```java
   if (list1.length > list2.length) {
       return findRestaurant(list2, list1);
   }
   ```
   - Đảm bảo `list1` luôn là mảng có kích thước ngắn hơn. `HashMap` sẽ có kích thước nhỏ nhất có thể.

2. **Khởi tạo HashMap với kích thước tối ưu:**
   ```java
   Map<String, Integer> map = new HashMap<>(list1.length * 4 / 3 + 1);
   ```
   - Hệ số tải mặc định của Java `HashMap` là `0.75`. Thiết lập trước `capacity = length / 0.75 + 1` giúp `HashMap` chứa đủ tất cả các phần tử mà **không bao giờ bị kích hoạt cơ chế `rehash` (nhân đôi mảng bucket)**, tăng đáng kể hiệu năng chạy thực tế.

3. **Cắt tỉa vòng lặp khi `j > minSum`:**
   ```java
   if (j > minSum) {
       break;
   }
   ```
   - Ví dụ: Giả sử chuỗi đầu tiên ở $j = 0$ trùng với chuỗi tại $i = 0$, ta có $\text{minSum} = 0$.
   - Ngay ở bước tiếp theo $j = 1$, điều kiện $1 > 0$ thỏa mãn và chương trình ngắt vòng lặp lập tức, duyệt đúng 1 lần thay vì 1000 lần!

4. **Cập nhật danh sách kết quả:**
   ```java
   if (sum < minSum) {
       minSum = sum;
       result.clear();
       result.add(list2[j]);
   } else if (sum == minSum) {
       result.add(list2[j]);
   }
   ```
   - Khi tìm thấy tổng nhỏ hơn: Xóa toàn bộ kết quả cũ bằng `result.clear()` và thêm phần tử mới.
   - Khi tìm thấy tổng bằng `minSum`: Thêm vào danh sách `result` (cho phép trả về nhiều nhà hàng có cùng tổng chỉ số nhỏ nhất).

---

## 🔍 Từng bước thực thi qua ví dụ (Step-by-Step Walkthrough)

### Ví dụ 1:
- `list1 = ["Shogun", "Tapioca Express", "Burger King", "KFC"]` (độ dài 4)
- `list2 = ["Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"]` (độ dài 4)

1. **Đưa `list1` vào `map`:**
   `{"Shogun": 0, "Tapioca Express": 1, "Burger King": 2, "KFC": 3}`
2. **Duyệt `list2`:**
   - $j = 0$: `"Piatti"` $\rightarrow$ không có trong map.
   - $j = 1$: `"The Grill at Torrey Pines"` $\rightarrow$ không có trong map.
   - $j = 2$: `"Hungry Hunter Steakhouse"` $\rightarrow$ không có trong map.
   - $j = 3$: `"Shogun"` $\rightarrow$ có trong map tại $i = 0$.
     - $\text{sum} = 0 + 3 = 3 < \infty \implies \text{minSum} = 3$, `result = ["Shogun"]`.
3. 👉 **Kết quả:** `["Shogun"]`.

---

### Ví dụ 2:
- `list1 = ["Shogun", "Tapioca Express", "Burger King", "KFC"]` (độ dài 4)
- `list2 = ["KFC", "Shogun", "Burger King"]` (độ dài 3)

1. **Tối ưu hoán đổi:** Vì độ dài `list1` (4) > độ dài `list2` (3), hoán đổi để đưa danh sách 3 phần tử vào map.
2. **Map chứa `list2`:**
   `{"KFC": 0, "Shogun": 1, "Burger King": 2}`
3. **Duyệt qua danh sách 4 phần tử:**
   - $j = 0$: `"Shogun"` $\rightarrow$ có trong map tại $i = 1$.
     - $\text{sum} = 1 + 0 = 1 < \infty \implies \text{minSum} = 1$, `result = ["Shogun"]`.
   - $j = 1$: `"Tapioca Express"` $\rightarrow$ không có trong map.
   - $j = 2$: Kiểm tra điều kiện ngắt: $j = 2 > \text{minSum} = 1 \implies$ **DỪNG VÒNG LẶP (BREAK)**!
   - Không cần phải xét `"Burger King"` hay `"KFC"` ở các vị trí sau.
4. 👉 **Kết quả:** `["Shogun"]`.

---

### Ví dụ 3 (Nhiều kết quả):
- `list1 = ["happy", "sad", "good"]`
- `list2 = ["sad", "happy", "good"]`

1. **Map chứa `list1`:** `{"happy": 0, "sad": 1, "good": 2}`.
2. **Duyệt `list2`:**
   - $j = 0$: `"sad"` $\rightarrow$ $i = 1 \implies \text{sum} = 1 + 0 = 1$. `minSum = 1`, `result = ["sad"]`.
   - $j = 1$: `"happy"` $\rightarrow$ $i = 0 \implies \text{sum} = 0 + 1 = 1$. $\text{sum} == \text{minSum} \implies `result = ["sad", "happy"]`.
   - $j = 2$: $j = 2 > \text{minSum} = 1 \implies$ **DỪNG VÒNG LẶP (BREAK)**! Bỏ qua `"good"`.
3. 👉 **Kết quả:** `["sad", "happy"]`.

---

## 📊 Đánh giá độ phức tạp (Complexity Analysis)

| Phương pháp | Time Complexity | Space Complexity | Đánh giá |
| :--- | :---: | :---: | :--- |
| **Vét cạn (Brute Force)** | $\mathcal{O}(m \cdot n \cdot L)$ | $\mathcal{O}(1)$ | Không tối ưu, lãng phí thời gian so sánh chuỗi lặp đi lặp lại. |
| **Standard HashMap** | $\mathcal{O}((m + n) \cdot L)$ | $\mathcal{O}(m \cdot L)$ | Tốt, nhưng duyệt hết mảng thứ hai mà không tận dụng cắt tỉa. |
| **Best Solution (HashMap + Early Exit)** ⭐ | $\mathcal{O}((m + n') \cdot L)$ với $n' \le \text{minSum} + 1 \le n$ | $\mathcal{O}(\min(m, n) \cdot L)$ | **Tối ưu tuyệt đối:** Tiết kiệm bộ nhớ nhất và dừng sớm khi $j > \text{minSum}$. |

- **Thời gian ($\mathcal{O}((m + n) \cdot L)$ trong trường hợp xấu nhất, trung bình nhanh hơn rất nhiều):**
  - Đưa mảng ngắn hơn vào map mất $\mathcal{O}(m \cdot L)$.
  - Vòng lặp duyệt mảng thứ hai chỉ chạy tối đa đến $\min(n, \text{minSum} + 1)$.
- **Không gian ($\mathcal{O}(\min(m, n) \cdot L)$):**
  - Chỉ lưu trữ các chuỗi của mảng ngắn hơn vào `HashMap`.

---

## ⚠️ Các trường hợp đặc biệt (Corner / Edge Cases)

1. **Chuỗi chung xuất hiện ngay tại `(0, 0)`:**
   - $\text{minSum} = 0$.
   - Ngay tại $j = 1$, điều kiện $j > \text{minSum}$ kích hoạt và dừng vòng lặp ngay lập tức. Thuật toán chỉ duyệt đúng 1 phần tử của `list2`.
2. **Tất cả các phần tử đều trùng nhau:**
   - Cặp có $i + j$ nhỏ nhất được chọn chính xác.
3. **Nhiều chuỗi có cùng tổng chỉ số nhỏ nhất:**
   - Cơ chế `sum == minSum` bổ sung liên tiếp tất cả các chuỗi thỏa mãn vào `result`.
4. **Độ dài hai danh sách chênh lệch lớn (ví dụ $m = 1, n = 1000$):**
   - Kỹ thuật hoán đổi giúp `HashMap` chỉ lưu đúng 1 phần tử thay vì 1000 phần tử, giảm thiểu tối đa tài nguyên cấp phát.
