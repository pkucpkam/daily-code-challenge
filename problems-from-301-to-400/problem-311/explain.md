# 377. Combination Sum IV (Tổng Hợp Kết Hợp IV)

**Độ khó:** Medium (Trung bình)

## Mô Tả Bài Toán

Cho một mảng các số nguyên phân biệt `nums` và một số nguyên đích `target`, hãy trả về số lượng các tổ hợp có thể cộng lại bằng target.

**Điểm quan trọng:** Các chuỗi khác nhau được tính là các tổ hợp khác nhau. Ví dụ: `[1, 2]` và `[2, 1]` là hai tổ hợp khác nhau.

---

## Phương Pháp Giải: Quy Hoạch Động (Dynamic Programming)

### Trực Giác Giải Quyết

Đây là một bài toán **cái túi không giới hạn (unbounded knapsack)** kinh điển có thể được giải bằng **Quy Hoạch Động**.

Ta sử dụng `dp[i]` để biểu thị **số lượng tổ hợp có tổng bằng `i`**.

### Chi Tiết Thuật Toán

1. **Khởi tạo**: Tạo mảng `dp` trong đó `dp[i]` lưu trữ số lượng tổ hợp có tổng bằng `i`.
   - `dp[0] = 1` (một cách để tạo ra 0: không chọn gì)

2. **Điền Bảng DP**: Với mỗi tổng từ 1 đến target:
   - Với mỗi số trong `nums`:
     - Nếu số đó ≤ tổng hiện tại, cộng các tổ hợp từ `dp[i - num]` vào `dp[i]`
     - Điều này có nghĩa: "cách để tạo tổng `i`" += "cách để tạo tổng `i - num`"

3. **Trả về**: `dp[target]` chứa câu trả lời

### Ví Dụ Chi Tiết

**Đầu vào:** `nums = [1,2,3], target = 4`

```
dp[0] = 1

i=1:
  - num=1: dp[1] += dp[1-1] = dp[0] = 1
  - num=2,3: bỏ qua (quá lớn)
  dp[1] = 1 → {(1)}

i=2:
  - num=1: dp[2] += dp[2-1] = dp[1] = 1
  - num=2: dp[2] += dp[2-2] = dp[0] = 1
  - num=3: bỏ qua (quá lớn)
  dp[2] = 2 → {(1,1), (2)}

i=3:
  - num=1: dp[3] += dp[3-1] = dp[2] = 2
  - num=2: dp[3] += dp[3-2] = dp[1] = 1
  - num=3: dp[3] += dp[3-3] = dp[0] = 1
  dp[3] = 4 → {(1,1,1), (1,2), (2,1), (3)}

i=4:
  - num=1: dp[4] += dp[4-1] = dp[3] = 4
  - num=2: dp[4] += dp[4-2] = dp[2] = 2
  - num=3: dp[4] += dp[4-3] = dp[1] = 1
  dp[4] = 7 → {(1,1,1,1), (1,1,2), (1,2,1), (2,1,1), (2,2), (1,3), (3,1)}
```

**Kết quả:** Có 7 cách khác nhau để tạo thành tổng 4.

---

## Phân Tích Độ Phức Tạp

- **Độ Phức Tạp Thời Gian:** $O(\text{target} \times \text{nums.length})$
  - Vòng lặp ngoài chạy `target` lần
  - Vòng lặp trong duyệt qua tất cả các số trong `nums`
  
- **Độ Phức Tạp Không Gian:** $O(\text{target})$
  - Mảng DP có kích thước `target + 1`

---

## So Sánh Với Các Bài Toán Khác

| Bài Toán | Khác Biệt |
|---------|----------|
| **Combination Sum I** | Tìm các tổ hợp thực tế (không phải đếm); không thể tái sử dụng phần tử |
| **Combination Sum II** | Xử lý phần tử trùng lặp; tìm các tổ hợp thực tế |
| **Combination Sum III** | Chính xác k số; tìm các tổ hợp thực tế |
| **Combination Sum IV** | Đếm tổ hợp (không tìm chúng); có thể tái sử dụng phần tử; **thứ tự quan trọng** |

---

## Mã Code (Java)

```java
class Solution {
    public int combinationSum4(int[] nums, int target) {
        // dp[i] biểu thị số lượng tổ hợp có tổng bằng i
        int[] dp = new int[target + 1];
        dp[0] = 1; // Một cách để tạo ra 0: không chọn gì
        
        // Với mỗi tổng từ 1 đến target
        for (int i = 1; i <= target; i++) {
            // Thử mỗi số trong nums
            for (int num : nums) {
                if (num <= i) {
                    // Nếu số hiện tại có thể được sử dụng, 
                    // cộng các tổ hợp từ (i - num)
                    dp[i] += dp[i - num];
                }
            }
        }
        
        return dp[target];
    }
}
```

---

## Giải Thích Từng Dòng Code

```java
int[] dp = new int[target + 1];
```
- Tạo mảng DP với kích thước `target + 1` để lưu trữ số cách cho mỗi tổng từ 0 đến target.

```java
dp[0] = 1;
```
- Khởi tạo `dp[0] = 1` vì có 1 cách duy nhất để tạo tổng 0 (không chọn gì).

```java
for (int i = 1; i <= target; i++) {
```
- Duyệt qua tất cả các tổng từ 1 đến target.

```java
for (int num : nums) {
```
- Với mỗi tổng, duyệt qua tất cả các số trong mảng `nums`.

```java
if (num <= i) {
    dp[i] += dp[i - num];
}
```
- Nếu số hiện tại nhỏ hơn hoặc bằng tổng hiện tại:
  - Cộng số cách để tạo tổng `(i - num)` vào `dp[i]`
  - Điều này tương đương với việc thêm số `num` vào tất cả các cách để tạo tổng `(i - num)`

```java
return dp[target];
```
- Trả về kết quả là số cách để tạo tổng `target`.

---

## Bài Toán Tiếp Theo (Follow-up)

**Câu hỏi:** Nếu các số âm được phép trong mảng thì sao?

**Trả lời:** 
- Thuật toán sẽ cần một **điều kiện dừng** để tránh vòng lặp vô hạn
- Ví dụ: nếu ta có `[1, -1]` và `target = 1`, ta có thể cộng 1 và trừ 1 vô hạn lần
- Để giải quyết vấn đề này, ta cần:
  1. Giới hạn số lần sử dụng các số âm
  2. Hoặc chỉ định rằng tối thiểu phải có bao nhiêu số dương
  3. Hoặc thêm ràng buộc: "Mỗi số có thể được sử dụng tối đa `k` lần"

---

## Ghi Chú Quan Trọng

✅ **Ưu điểm của phương pháp này:**
- Đơn giản và dễ hiểu
- Hiệu quả về thời gian và không gian
- Dễ mở rộng cho các biến thể khác

⚠️ **Lưu ý khi triển khai:**
- Đảm bảo xử lý trường hợp `target = 0`
- Kiểm tra xem `nums` có chứa các số âm không
- Cần lưu ý về độ lớn của kết quả (có thể vượt quá `int`)