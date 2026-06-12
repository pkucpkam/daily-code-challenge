# 413. Arithmetic Slices - Giải thích thuật toán

Bài toán yêu cầu chúng ta tìm số lượng dãy con liên tục (subarray) có ít nhất 3 phần tử tạo thành một cấp số cộng (arithmetic progression).

Để giải quyết bài toán này một cách tối ưu, chúng ta có thể sử dụng phương pháp **Quy hoạch động (Dynamic Programming)** với độ phức tạp thời gian $O(N)$ và không gian $O(1)$.

---

## Ý tưởng cốt lõi

Một mảng con gồm ít nhất 3 phần tử `nums[i-2], nums[i-1], nums[i]` tạo thành một cấp số cộng khi và chỉ khi hiệu giữa hai phần tử liên tiếp là bằng nhau:
$$nums[i] - nums[i-1] = nums[i-1] - nums[i-2]$$

Gọi `dp[i]` là số lượng mảng con cấp số cộng **kết thúc tại chỉ số `i`**.

### Công thức truy hồi:
1. Nếu `nums[i] - nums[i-1] == nums[i-1] - nums[i-2]`:
   - Mỗi mảng con cấp số cộng kết thúc tại `i-1` đều có thể kéo dài thêm phần tử `nums[i]` để tạo thành một mảng con cấp số cộng mới kết thúc tại `i`.
   - Ngoài ra, bản thân bộ ba phần tử `[nums[i-2], nums[i-1], nums[i]]` cũng tạo thành một cấp số cộng mới có độ dài 3.
   - Do đó: `dp[i] = dp[i-1] + 1`
2. Ngược lại, nếu hiệu không bằng nhau:
   - Không có mảng con cấp số cộng nào có thể kết thúc tại chỉ số `i`.
   - Do đó: `dp[i] = 0`

Tổng số lượng tất cả các mảng con cấp số cộng sẽ là tổng của tất cả các phần tử trong mảng `dp`:
$$\text{Total} = \sum_{i=2}^{N-1} dp[i]$$

### Tối ưu bộ nhớ:
Vì `dp[i]` chỉ phụ thuộc vào giá trị liền trước là `dp[i-1]`, chúng ta không cần khởi tạo cả mảng `dp` kích thước $N$. Thay vào đó, ta chỉ cần sử dụng một biến duy nhất `current` để lưu trữ số lượng cấp số cộng kết thúc tại phần tử hiện tại.

---

## Các bước thuật toán

1. **Kiểm tra điều kiện biên:** Nếu độ dài mảng `nums` nhỏ hơn 3, trả về 0 ngay lập tức.
2. **Khởi tạo:**
   - `total = 0`: Tổng số lượng mảng con cấp số cộng tìm được.
   - `current = 0`: Số lượng mảng con cấp số cộng kết thúc tại chỉ số đang xét.
3. **Duyệt qua mảng:** Bắt đầu từ chỉ số `i = 2` đến hết mảng:
   - Nếu `nums[i] - nums[i-1] == nums[i-1] - nums[i-2]` thì:
     - Tăng `current` lên 1 đơn vị.
     - Cộng `current` vào `total`.
   - Ngược lại, reset `current = 0`.
4. **Trả về** `total`.

---

## Ví dụ minh họa với `nums = [1, 2, 3, 4]`

- **Khởi tạo:** `total = 0`, `current = 0`
- **i = 2 (`nums[2] = 3`):**
  - Xét bộ ba: `[1, 2, 3]` -> hiệu là `2-1 = 1` và `3-2 = 1` (bằng nhau).
  - `current = current + 1 = 1`.
  - `total = total + current = 1`.
  - *Mảng con tìm được:* `[1, 2, 3]`.
- **i = 3 (`nums[3] = 4`):**
  - Xét bộ ba: `[2, 3, 4]` -> hiệu là `3-2 = 1` và `4-3 = 1` (bằng nhau).
  - `current = current + 1 = 2`.
  - `total = total + current = 1 + 2 = 3`.
  - *Các mảng con mới kết thúc tại 4:* `[2, 3, 4]` (độ dài 3) và `[1, 2, 3, 4]` (độ dài 4, kéo dài từ `[1, 2, 3]`).

Kết quả trả về: `3` (thỏa mãn yêu cầu ví dụ 1).

---

## Phân tích độ phức tạp

- **Độ phức tạp thời gian (Time Complexity):** $O(N)$
  - Chỉ cần duyệt qua mảng `nums` đúng một lần duy nhất từ chỉ số `2` đến $N-1$.
- **Độ phức tạp không gian (Space Complexity):** $O(1)$
  - Chỉ sử dụng 2 biến phụ là `total` và `current` để tính toán mà không cần cấp phát thêm bộ nhớ động.
