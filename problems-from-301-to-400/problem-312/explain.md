# 377. Combination Sum IV

**Độ khó:** Medium (Trung bình)

---

## 1. Đề Bài

Cho một mảng các số nguyên phân biệt `nums` và một số nguyên đích `target`. Hãy trả về **số lượng tổ hợp** có thể tạo ra từ các phần tử của `nums` sao cho tổng của chúng bằng `target`.

*Lưu ý:* Các chuỗi số có thứ tự khác nhau được tính là các tổ hợp khác nhau. Ví dụ: `(1, 2)` và `(2, 1)` là hai cách kết hợp hoàn toàn khác nhau.

### Ví Dụ 1:
- **Đầu vào:** `nums = [1, 2, 3]`, `target = 4`
- **Đầu ra:** `7`
- **Giải thích:** Các tổ hợp có thể tạo thành tổng `4` là:
  - `(1, 1, 1, 1)`
  - `(1, 1, 2)`
  - `(1, 2, 1)`
  - `(1, 3)`
  - `(2, 1, 1)`
  - `(2, 2)`
  - `(3, 1)`

### Ví Dụ 2:
- **Đầu vào:** `nums = [9]`, `target = 3`
- **Đầu ra:** `0`

### Ràng Buộc (Constraints):
- `1 <= nums.length <= 200`
- `1 <= nums[i] <= 1000`
- Các phần tử trong `nums` là duy nhất.
- `1 <= target <= 1000`

---

## 2. Ý Tưởng Giải Quyết: Quy Hoạch Động (Dynamic Programming)

Bài toán này thực chất là bài toán **Cái Túi Không Giới Hạn (Unbounded Knapsack)**, trong đó thứ tự của các vật phẩm là quan trọng (Hoán vị / Permutations).

### Trực Giác (Intuition)
Giả sử chúng ta muốn tìm số cách để đạt được tổng `target = i`. 
Nếu chúng ta chọn số `num` trong mảng `nums` làm số cuối cùng của tổ hợp, thì số lượng cách để đạt được tổng `i` với số cuối là `num` chính bằng **số lượng cách để đạt được tổng `i - num`**.

Do đó, công thức truy hồi sẽ là:
$$dp[i] = \sum_{num \in nums} dp[i - num] \quad (\text{với } i \ge num)$$

### Định Nghĩa Trạng Thái DP
- Gọi `dp[i]` là số lượng tổ hợp có tổng bằng `i`.
- **Trường hợp cơ sở (Base case):** `dp[0] = 1`. Chỉ có duy nhất 1 cách để đạt được tổng bằng `0`, đó là không chọn phần tử nào.

---

## 3. Các Bước Thực Hiện (Bottom-Up DP)

1. **Khởi tạo:** Tạo mảng `dp` có kích thước `target + 1`. Gán `dp[0] = 1`.
2. **Vòng lặp bên ngoài:** Duyệt qua từng giá trị tổng từ `1` đến `target`.
3. **Vòng lặp bên trong:** Với mỗi giá trị tổng `i`, duyệt qua từng số `num` trong `nums`.
   - Nếu `i >= num`, cộng dồn giá trị `dp[i - num]` vào `dp[i]`:
     `dp[i] += dp[i - num]`
4. **Kết quả:** Trả về `dp[target]`.

---

## 4. Minh Họa Từng Bước Chạy (Dry Run)

Với `nums = [1, 2, 3]` và `target = 4`:

| `i` (Tổng) | Duyệt qua các `num` | Công thức / Tính toán | Giá trị `dp[i]` sau khi duyệt |
| :--- | :--- | :--- | :--- |
| **0** | - | *Base case* | `dp[0] = 1` |
| **1** | `num = 1`<br>`num = 2`<br>`num = 3` | `dp[1] += dp[1-1] = dp[0] = 1`<br>Bỏ qua vì $1 < 2$<br>Bỏ qua vì $1 < 3$ | `dp[1] = 1` |
| **2** | `num = 1`<br>`num = 2`<br>`num = 3` | `dp[2] += dp[2-1] = dp[1] = 1`<br>`dp[2] += dp[2-2] = dp[0] = 1`<br>Bỏ qua vì $2 < 3$ | `dp[2] = 2` |
| **3** | `num = 1`<br>`num = 2`<br>`num = 3` | `dp[3] += dp[3-1] = dp[2] = 2`<br>`dp[3] += dp[3-2] = dp[1] = 1`<br>`dp[3] += dp[3-3] = dp[0] = 1` | `dp[3] = 4` |
| **4** | `num = 1`<br>`num = 2`<br>`num = 3` | `dp[4] += dp[4-1] = dp[3] = 4`<br>`dp[4] += dp[4-2] = dp[2] = 2`<br>`dp[4] += dp[4-3] = dp[1] = 1` | `dp[4] = 7` |

---

## 5. Mã Nguồn Java Chi Tiết

Dưới đây là mã nguồn tối ưu sử dụng phương pháp **Bottom-Up DP**:

```java
class Solution {
    public int combinationSum4(int[] nums, int target) {
        // dp[i] sẽ lưu trữ số lượng tổ hợp có tổng bằng i
        int[] dp = new int[target + 1];
        
        // Trường hợp cơ sở: Có chính xác 1 cách để có tổng bằng 0 (không chọn gì cả)
        dp[0] = 1;
        
        // Tính toán số lượng tổ hợp cho tất cả các tổng từ 1 đến target
        for (int i = 1; i <= target; i++) {
            for (int num : nums) {
                if (i >= num) {
                    dp[i] += dp[i - num];
                }
            }
        }
        
        return dp[target];
    }
}
```

---

## 6. Phân Tích Độ Phức Tạp

- **Độ phức tạp thời gian (Time Complexity):** $\mathcal{O}(T \times N)$
  - Trong đó $T$ là `target` và $N$ là số lượng phần tử của mảng `nums`.
  - Chúng ta sử dụng hai vòng lặp lồng nhau: vòng ngoài chạy từ `1` đến $T$, vòng trong chạy qua $N$ phần tử của `nums`.
- **Độ phức tạp không gian (Space Complexity):** $\mathcal{O}(T)$
  - Cần mảng `dp` có kích thước `target + 1` để lưu trữ trạng thái.

---

## 7. Trả Lời Câu Hỏi Mở Rộng (Follow-Up)

> **Câu hỏi:** Điều gì xảy ra nếu mảng `nums` chứa các số âm? Nó thay đổi bài toán như thế nào? Cần thêm ràng buộc gì để bài toán vẫn có lời giải hợp lệ?

### Phân tích
Nếu cho phép các số âm, chúng ta có thể rơi vào tình trạng **vòng lặp vô hạn (infinite loops)** của các tổ hợp triệt tiêu nhau nhưng vẫn tạo ra tổng mong muốn.
Ví dụ: `nums = [1, -1]`, `target = 1`.
Ta có thể chọn:
- `(1)`
- `(1, 1, -1)`
- `(1, 1, -1, 1, -1)`
- ... và cứ thế tiếp tục vô hạn lần.

### Ràng buộc cần thêm:
Để bài toán với số âm có thể giải được và không sinh ra vô hạn tổ hợp, chúng ta cần đặt thêm một trong các giới hạn sau:
1. **Giới hạn số lượng phần tử tối đa** được phép sử dụng trong một tổ hợp (ví dụ: chiều dài tổ hợp không vượt quá `K`).
2. **Mỗi phần tử chỉ được sử dụng tối đa một số lần** cố định nào đó (trở về biến thể giống Combination Sum I/II).
3. **Không cho phép các chuỗi tuần hoàn triệt tiêu** (như tổng bằng 0) xuất hiện trong tổ hợp.

---

## 8. So Sánh Các Phiên Bản Combination Sum

| Bài toán | Có xét thứ tự không? | Tái sử dụng phần tử? | Mục tiêu |
| :--- | :--- | :--- | :--- |
| **Combination Sum I** | Không | Có | Tìm tất cả các tổ hợp duy nhất |
| **Combination Sum II** | Không | Không | Tìm tất cả các tổ hợp duy nhất (mỗi số dùng 1 lần) |
| **Combination Sum III** | Không | Không | Tìm tất cả tổ hợp độ dài `k` sử dụng các số `1-9` |
| **Combination Sum IV** | **Có (Xét thứ tự)** | **Có** | **Chỉ đếm số lượng tổ hợp** |