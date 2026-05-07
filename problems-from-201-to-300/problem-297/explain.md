# 334. Increasing Triplet Subsequence - Giải thích thuật toán

## Phân tích bài toán
Mục tiêu là tìm xem có tồn tại ba chỉ số `i < j < k` sao cho `nums[i] < nums[j] < nums[k]` hay không. 
Yêu cầu quan trọng là giải thuật phải đạt độ phức tạp thời gian **O(n)** và độ phức tạp không gian **O(1)**.

---

## Phương pháp: Thuật toán Tham lam (Greedy)

Chúng ta sử dụng hai biến, `first` và `second`, để theo dõi các giá trị nhỏ nhất và nhỏ thứ hai tìm thấy cho đến nay, những giá trị này có khả năng tạo thành một bộ ba tăng dần.

### 1. Khởi tạo biến
- `first = Integer.MAX_VALUE`: Lưu giá trị nhỏ nhất tìm thấy được.
- `second = Integer.MAX_VALUE`: Lưu giá trị nhỏ thứ hai tìm thấy được (giá trị này phải lớn hơn `first`).

### 2. Logic lặp
Khi duyệt qua từng số `n` trong mảng `nums`:
1. **Nếu số hiện tại `n <= first`**:
   - Cập nhật `first = n`. Đây là giá trị nhỏ nhất chúng ta từng thấy.
2. **Nếu không, nếu số hiện tại `n <= second`**:
   - Cập nhật `second = n`. Điều này có nghĩa là chúng ta tìm thấy một số `n` lớn hơn `first` hiện tại nhưng nhỏ hơn hoặc bằng `second` hiện tại.
3. **Ngược lại (nghĩa là `n > first` và `n > second`)**:
   - Chúng ta đã tìm thấy một số lớn hơn cả `first` và `second`. Vì `second` chỉ được thiết lập khi chúng ta đã có một cặp `first < second` hợp lệ trước đó, số `n` này sẽ hoàn thành bộ ba. Trả về `true`.

### 3. Kết luận
Nếu kết thúc vòng lặp mà không tìm thấy số nào như vậy, trả về `false`.

---

## Tại sao thuật toán này hoạt động?
Bạn có thể thắc mắc: *Điều gì xảy ra nếu `first` được cập nhật thành một giá trị xuất hiện SAU `second` hiện tại?*

**Ví dụ:** `nums = [10, 20, 5, 25]`
1. `n = 10`: `first = 10`
2. `n = 20`: `second = 20`
3. `n = 5`: `first = 5` (Lúc này `first` nằm sau `second` trong mảng)
4. `n = 25`: `25 > first` (5) và `25 > second` (20). Trả về `true`.

Mặc dù `first` đã trở thành 5 (nằm sau 20), nhưng việc `second` đang là 20 ngầm định rằng **đã từng tồn tại** một giá trị (là 10) đứng trước 20 và nhỏ hơn 20. Do đó, bất kỳ số nào lớn hơn 20 vẫn sẽ tạo thành một bộ ba hợp lệ với cặp đôi ban đầu đó.

---

## Độ phức tạp
- **Độ phức tạp thời gian:** $O(n)$ - Chúng ta chỉ duyệt qua mảng một lần duy nhất.
- **Độ phức tạp không gian:** $O(1)$ - Chúng ta chỉ sử dụng hai biến số nguyên.
