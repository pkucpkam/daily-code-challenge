# 376. Wiggle Subsequence

**Độ khó:** Medium

## Mô Tả Bài Toán

Một dãy số được gọi là **wiggle sequence** nếu hiệu giữa các phần tử liên tiếp đổi dấu xen kẽ giữa dương ($+$) và âm ($-$).
* Phần tử đầu tiên (nếu có) có thể có hiệu số tiếp theo là dương hoặc âm.
* Dãy chỉ có $1$ phần tử hoặc $2$ phần tử khác nhau mặc định là dãy wiggle.

**Ví dụ:**
* `[1, 7, 4, 9, 2, 5]` là một dãy wiggle vì các hiệu số liên tiếp là `(6, -3, 5, -7, 3)` đổi dấu xen kẽ liên tục.
* `[1, 4, 7, 2, 5]` không phải dãy wiggle vì hai hiệu đầu tiên đều dương `(3, 3)`.
* `[1, 7, 4, 5, 5]` không phải dãy wiggle vì hiệu cuối cùng bằng `0`.

Cho mảng số nguyên `nums`, hãy tìm độ dài của **dãy con wiggle dài nhất** trích xuất từ `nums` (bằng cách xóa một số phần tử hoặc không xóa, giữ nguyên thứ tự ban đầu).

---

## Ý Tưởng Giải (Thuật Toán Tham Lam - Greedy Algorithm)

Bài toán này có thể giải bằng Quy hoạch động (Dynamic Programming), nhưng giải pháp tối ưu và đơn giản nhất là áp dụng **Thuật toán Tham lam (Greedy)** với độ phức tạp thời gian chỉ $O(n)$ và không gian bộ nhớ $O(1)$.

### Phân tích dưới góc độ Hình học (Đỉnh và Đáy - Peaks & Valleys)

Nếu chúng ta biểu diễn mảng số nguyên thành một đồ thị gấp khúc:
* Các đoạn tăng biểu thị sườn dốc đi lên.
* Các đoạn giảm biểu thị sườn dốc đi xuống.

Một dãy wiggle thực chất là một chuỗi luân phiên các **điểm cực đại (peaks)** và **điểm cực tiểu (valleys)** trên đồ thị này. Việc tìm dãy con wiggle dài nhất tương đương với việc đếm số lượng đỉnh núi và đáy thung lũng.

```
       (Peak)
        / \
       /   \
      /     \
(Valley)    (Valley)
```

Khi đi dọc theo một sườn dốc đang tăng (ví dụ: `1 -> 3 -> 5 -> 10 -> 2`), ta chỉ nên chọn điểm cao nhất là `10` làm đỉnh (peak) thay vì chọn `3` hoặc `5`. Việc chọn điểm cao nhất này sẽ tối đa hóa cơ hội tìm được phần tử nhỏ hơn tiếp theo ở sườn dốc đi xuống phía sau.

### Thuật toán cụ thể:

1. **Khởi tạo**:
   * Nếu số phần tử của mảng bé hơn 2, trả về chính kích thước của mảng.
   * Ta dùng biến `count` để đếm số phần tử trong dãy wiggle. Ban đầu `count = 1` (phần tử đầu tiên luôn được chọn).
   * Dùng biến `prediff` biểu thị hướng của hiệu số liền trước đó (dương, âm hoặc bằng 0). Ban đầu `prediff = 0`.

2. **Duyệt mảng**:
   Duyệt qua các phần tử từ chỉ số $1$ đến hết mảng, tính hiệu hiện tại `curdiff = nums[i] - nums[i - 1]`:
   * Nếu có sự đổi hướng so với trước đó:
     * Đi lên từ một đáy/điểm đầu: `curdiff > 0` và `prediff <= 0`.
     * Đi xuống từ một đỉnh/điểm đầu: `curdiff < 0` and `prediff >= 0`.
   * Khi phát hiện sự đổi hướng này, ta tăng `count` lên 1 và cập nhật `prediff = curdiff`.
   * **Điểm mấu chốt**: Ta chỉ cập nhật `prediff` khi hướng đi thực sự đảo chiều. Điều này giúp bỏ qua tất cả các điểm trung gian nằm trên cùng một sườn dốc thẳng tuột hoặc những điểm bằng nhau (`curdiff == 0`).

---

## Độ Phức Tạp

* **Thời gian:** $O(n)$ với $n$ là độ dài của mảng `nums`, do chỉ cần duyệt qua mảng duy nhất một lần.
* **Không gian:** $O(1)$ vì chỉ sử dụng một vài biến số nguyên đơn giản để lưu trữ trạng thái.

---

## Code (Java)

```java
class Solution {
    public int wiggleMaxLength(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length < 2) {
            return nums.length;
        }
        
        int count = 1;
        int prediff = 0;
        
        for (int i = 1; i < nums.length; i++) {
            int curdiff = nums[i] - nums[i - 1];
            // Phát hiện sự đổi dấu của hiệu số (Peak hoặc Valley)
            if ((curdiff > 0 && prediff <= 0) || (curdiff < 0 && prediff >= 0)) {
                count++;
                prediff = curdiff; // Cập nhật hướng hiệu số trước đó
            }
        }
        
        return count;
    }
}
```
