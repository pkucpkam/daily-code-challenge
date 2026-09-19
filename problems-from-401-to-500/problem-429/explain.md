# 📚 Giải thích Chi tiết Bài 594 - Longest Harmonious Subsequence

**Mục tiêu:** Cho một mảng số nguyên `nums`, hãy tìm độ dài của **dãy con hài hòa** (harmonious subsequence) dài nhất. Dãy con hài hòa được định nghĩa là một dãy con mà hiệu số giữa giá trị lớn nhất và giá trị nhỏ nhất của nó **chính xác bằng 1** ($\max - \min = 1$).

---

## 🎯 Phân tích bài toán

### 1. Định nghĩa Dãy con (Subsequence)
- Một **dãy con** được tạo thành bằng cách xóa đi $0$ hoặc nhiều phần tử khỏi mảng ban đầu mà không làm thay đổi thứ tự của các phần tử còn lại.
- **Nhận xét quan trọng:** Mặc dù định nghĩa của dãy con yêu cầu bảo toàn thứ tự ban đầu, nhưng giá trị lớn nhất ($\max$), giá trị nhỏ nhất ($\min$), và độ dài của một tập hợp các số **hoàn toàn không phụ thuộc vào vị trí hay thứ tự** xuất hiện của chúng trong mảng.
- Do đó, nếu ta quyết định chọn hai giá trị cụ thể, ta có thể chọn **toàn bộ tất cả các lần xuất hiện** của hai giá trị đó trong mảng `nums`.

### 2. Bản chất toán học của Mảng hài hòa (Harmonious Array)
Một dãy con thỏa mãn điều kiện $\max - \min = 1$ khi và chỉ khi:
1. Nó chỉ chứa **đúng 2 giá trị nguyên phân biệt** là $x$ và $x + 1$.
2. Cả $x$ và $x + 1$ **bắt buộc phải xuất hiện ít nhất một lần**.
   - Nếu mảng chỉ chứa duy nhất một giá trị $x$, thì $\max = \min = x \implies \max - \min = 0 \ne 1$ (không hợp lệ).
   - Nếu mảng chứa các giá trị có khoảng cách lớn hơn $1$ (ví dụ: $x$ và $x + 2$), thì $\max - \min \ge 2 \ne 1$ (không hợp lệ).

### 3. Công thức tính độ dài tối đa
Với mỗi giá trị $x$ xuất hiện trong mảng:
- Giả sử số lần xuất hiện của $x$ là $\text{count}(x)$.
- Nếu giá trị $x + 1$ cũng tồn tại trong mảng với số lần xuất hiện là $\text{count}(x + 1) > 0$:
  - Dãy con hài hòa dài nhất tạo bởi cặp số $(x, x + 1)$ sẽ có độ dài là:
    $$\text{Length}(x, x + 1) = \text{count}(x) + \text{count}(x + 1)$$
- Kết quả bài toán chính là giá trị lớn nhất trong tất cả các cặp $(x, x + 1)$ hợp lệ:
  $$\text{Result} = \max_{x \in \text{nums}, \, (x + 1) \in \text{nums}} \left( \text{count}(x) + \text{count}(x + 1) \right)$$
- Nếu không có bất kỳ cặp số liên tiếp $(x, x + 1)$ nào cùng xuất hiện, kết quả trả về là `0`.

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Bảng băm tần số (HashMap)

### 💡 Ý tưởng cốt lõi (Key Insight)
- Sử dụng `HashMap<Integer, Integer>` để đếm số lần xuất hiện (tần số) của từng số trong mảng `nums`.
- Sau khi có bảng tần số, duyệt qua từng khóa (key) $x$ trong map:
  - Nếu map có chứa khóa liền kề $x + 1$:
    - Tính tổng độ dài: $\text{count}(x) + \text{count}(x + 1)$.
    - Cập nhật độ dài lớn nhất: `maxLen = Math.max(maxLen, ...)`.
- **Tại sao chỉ cần kiểm tra $x + 1$ mà không cần kiểm tra cả $x - 1$?**
  - Vì nếu cặp $(x, x + 1)$ tồn tại, khi vòng lặp xét đến $x$, ta đã tính cặp $(x, x + 1)$.
  - Khi vòng lặp xét đến $x + 1$, nếu kiểm tra $(x + 1) - 1 = x$ thì chỉ là tính lại đúng cặp đó một lần nữa. Kiểm tra một chiều $x + 1$ giúp tránh việc tính toán trùng lặp.

---

### 💻 Mã nguồn Java (Best Solution)

```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    /**
     * Best Solution: Bảng băm (HashMap) đếm tần số xuất hiện.
     *
     * Time Complexity: O(n) - 1 lần duyệt mảng để đếm và 1 lần duyệt qua các key phân biệt.
     * Space Complexity: O(n) - lưu tối đa n phần tử trong HashMap.
     */
    public int findLHS(int[] nums) {
        Map<Integer, Integer> countMap = new HashMap<>();

        // Bước 1: Đếm số lần xuất hiện của từng số trong mảng
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        int maxLen = 0;

        // Bước 2: Tìm cặp (key, key + 1) có tổng tần số lớn nhất
        for (int key : countMap.keySet()) {
            if (countMap.containsKey(key + 1)) {
                int currentLen = countMap.get(key) + countMap.get(key + 1);
                maxLen = Math.max(maxLen, currentLen);
            }
        }

        return maxLen;
    }
}
```

---

## 🔄 CÁCH TIẾP CẬN 2: Sắp xếp + Cửa sổ trượt (Sorting + Sliding Window)

### 💡 Ý tưởng ($\mathcal{O}(1)$ bộ nhớ phụ)
- Nếu ta sắp xếp mảng `nums` theo thứ tự tăng dần:
  - Các phần tử giống nhau sẽ nằm cạnh nhau.
  - Mọi dãy con hài hòa bao gồm hai giá trị liền kề $(x, x + 1)$ sẽ trở thành một **đoạn con liên tục** (contiguous subarray) trong mảng đã sắp xếp.
- Ta dùng kỹ thuật **hai con trỏ / Cửa sổ trượt (Sliding Window)** với `left` và `right`:
  - Mở rộng con trỏ `right` từ `0` đến `n - 1`.
  - Trong khi chênh lệch giữa phần tử lớn nhất và nhỏ nhất của cửa sổ $\text{nums}[right] - \text{nums}[left] > 1$, ta thu hẹp cửa sổ bằng cách dịch `left++`.
  - Nếu $\text{nums}[right] - \text{nums}[left] == 1$, cửa sổ hiện tại hợp lệ với độ dài là $right - left + 1$. Cập nhật `maxLen = Math.max(maxLen, right - left + 1)`.

### 💻 Code tham khảo

```java
import java.util.Arrays;

class SolutionSlidingWindow {
    public int findLHS(int[] nums) {
        Arrays.sort(nums);

        int left = 0;
        int maxLen = 0;

        for (int right = 0; right < nums.length; right++) {
            // Khi khoảng cách giữa giá trị lớn nhất và nhỏ nhất > 1, thu hẹp cửa sổ
            while (nums[right] - nums[left] > 1) {
                left++;
            }

            // Khi khoảng cách chính xác bằng 1, cập nhật kết quả
            if (nums[right] - nums[left] == 1) {
                maxLen = Math.max(maxLen, right - left + 1);
            }
        }

        return maxLen;
    }
}
```

> **So sánh thực tế:** 
> - Mặc dù độ phức tạp thời gian là $\mathcal{O}(n \log n)$, nhưng trên mảng nguyên thủy `int[]`, thuật toán Dual-Pivot Quicksort của Java chạy cực nhanh và có tính định xứ bộ nhớ (cache locality) tuyệt vời.
> - Đặc biệt, cách này **không cấp phát bất kỳ đối tượng nào trên Heap** (Zero Object Allocation), không bị overhead của Autoboxing `Integer` hay Garbage Collection, nên trong thực tế chạy trên LeetCode thường nhanh tương đương hoặc hơn `HashMap`.

---

## 🔄 CÁCH TIẾP CẬN 3: Bảng băm một lần duyệt (Single-Pass HashMap)

### 💡 Ý tưởng
- Ta có thể vừa duyệt mảng, vừa thêm phần tử vào `HashMap`, đồng thời kiểm tra và cập nhật `maxLen` ngay tại bước chèn đó.
- Khi chèn một số `num`:
  - Cập nhật số lần xuất hiện của `num`.
  - Nếu map đã có `num + 1`: tính độ dài tạo bởi `num` và `num + 1`.
  - Nếu map đã có `num - 1`: tính độ dài tạo bởi `num` và `num - 1`.
  - Cập nhật `maxLen`.

### 💻 Code tham khảo

```java
import java.util.HashMap;
import java.util.Map;

class SolutionSinglePass {
    public int findLHS(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int maxLen = 0;

        for (int num : nums) {
            int count = map.getOrDefault(num, 0) + 1;
            map.put(num, count);

            if (map.containsKey(num + 1)) {
                maxLen = Math.max(maxLen, count + map.get(num + 1));
            }
            if (map.containsKey(num - 1)) {
                maxLen = Math.max(maxLen, count + map.get(num - 1));
            }
        }

        return maxLen;
    }
}
```

---

## 🔍 Từng bước thực thi qua ví dụ (Step-by-Step Walkthrough)

### Ví dụ 1: `nums = [1, 3, 2, 2, 5, 2, 3, 7]`

1. **Bước 1: Xây dựng bảng tần số (HashMap)**

| Giá trị (`key`) | Tần số (`count`) |
| :---: | :---: |
| `1` | 1 |
| `2` | 3 (tại các vị trí index 2, 3, 5) |
| `3` | 2 (tại các vị trí index 1, 6) |
| `5` | 1 |
| `7` | 1 |

2. **Bước 2: Xét các cặp `(key, key + 1)`**
- Xét `key = 1`: Có `key + 1 = 2` trong map.
  - Độ dài = $\text{count}(1) + \text{count}(2) = 1 + 3 = 4$.
  - $\text{maxLen} = \max(0, 4) = 4$.
- Xét `key = 2`: Có `key + 1 = 3` trong map.
  - Độ dài = $\text{count}(2) + \text{count}(3) = 3 + 2 = 5$.
  - $\text{maxLen} = \max(4, 5) = 5$.
- Xét `key = 3`: Không có `4` trong map $\rightarrow$ bỏ qua.
- Xét `key = 5`: Không có `6` trong map $\rightarrow$ bỏ qua.
- Xét `key = 7`: Không có `8` trong map $\rightarrow$ bỏ qua.

👉 **Kết quả:** `5` (dãy con tương ứng gồm: `[3, 2, 2, 2, 3]`).

---

### Ví dụ 2: `nums = [1, 2, 3, 4]`

- Bảng tần số: `{1: 1, 2: 1, 3: 1, 4: 1}`.
- Các cặp liền kề:
  - Cặp `(1, 2)`: độ dài = $1 + 1 = 2$.
  - Cặp `(2, 3)`: độ dài = $1 + 1 = 2$.
  - Cặp `(3, 4)`: độ dài = $1 + 1 = 2$.
- 👉 **Kết quả:** `2`.

---

### Ví dụ 3: `nums = [1, 1, 1, 1]`

- Bảng tần số: `{1: 4}`.
- Không tồn tại `key + 1 = 2` trong map.
- Không có cặp hợp lệ nào được tạo ra.
- 👉 **Kết quả:** `0`.

---

## 📊 Đánh giá độ phức tạp (Complexity Analysis)

| Phương pháp | Time Complexity | Space Complexity | Đánh giá |
| :--- | :---: | :---: | :--- |
| **Two-pass HashMap (Best Solution)** ⭐ | $\mathcal{O}(n)$ | $\mathcal{O}(n)$ | **Tối ưu về mặt lý thuyết.** Thời gian tuyến tính, thuật toán rõ ràng, dễ hiểu nhất. |
| **Sorting + Sliding Window** | $\mathcal{O}(n \log n)$ | $\mathcal{O}(1)$ hoặc $\mathcal{O}(\log n)$ | **Tối ưu về bộ nhớ.** Không tốn bộ nhớ cấp phát đối tượng trên Heap, chạy thực tế cực kỳ nhanh. |
| **Single-pass HashMap** | $\mathcal{O}(n)$ | $\mathcal{O}(n)$ | Duyệt 1 vòng lặp nhưng cần tra cứu map 2 lần (`num + 1` và `num - 1`) cho mỗi phần tử. |

---

## ⚠️ Các trường hợp đặc biệt (Corner / Edge Cases)

1. **Mảng toàn bộ các số giống nhau (ví dụ `[5, 5, 5, 5]`):**
   - Không có phần tử nào chênh lệch $1$ đơn vị ($\max - \min = 0 \ne 1$).
   - Thuật toán trả về `0` chính xác.
2. **Mảng chỉ có 1 phần tử (ví dụ `[1]`):**
   - Không thể tạo dãy con hài hòa.
   - Thuật toán trả về `0`.
3. **Các phần tử có khoảng cách lớn hơn 1 (ví dụ `[1, 3, 5, 7]`):**
   - Không có hai phần tử nào có hiệu bằng $1$.
   - Thuật toán trả về `0`.
4. **Phần tử có giá trị âm lớn và dương lớn ($-10^9 \le nums[i] \le 10^9$):**
   - `key + 1` có bị tràn số (overflow) không?
   - Giới hạn trên của `nums[i]` là $10^9$. Giá trị $10^9 + 1$ hoàn toàn nằm trong giới hạn của `int` 32-bit (khoảng $2.14 \times 10^9$), do đó không thể xảy ra hiện tượng tràn số nguyên.
