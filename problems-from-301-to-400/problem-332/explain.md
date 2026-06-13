# 416. Partition Equal Subset Sum - Giải thích thuật toán

Bài toán yêu cầu chúng ta xác định xem có thể chia một mảng số nguyên dương `nums` thành hai tập con sao cho tổng các phần tử của hai tập con này bằng nhau hay không.

Để giải quyết bài toán này, chúng ta có thể quy nó về bài toán **Cái túi 0/1 (0/1 Knapsack Problem)** và sử dụng phương pháp **Quy hoạch động (Dynamic Programming)**.

---

## Phân tích & Ý tưởng cốt lõi

1. **Điều kiện cần:**
   - Gọi tổng tất cả các phần tử trong mảng là `sum`.
   - Nếu `sum` là số lẻ, ta không thể chia mảng thành hai phần có tổng bằng nhau (vì tổng của mỗi phần phải là số nguyên `sum / 2`). Trong trường hợp này, trả về `false` ngay lập tức.
   - Nếu `sum` là số chẵn, mục tiêu của chúng ta là tìm một tập con trong mảng sao cho tổng các phần tử của tập con đó đúng bằng `target = sum / 2`. Nếu tìm được, tập con còn lại cũng sẽ có tổng bằng `target`.

2. **Quy hoạch động (DP):**
   - Gọi `dp[i]` là một giá trị boolean biểu thị liệu có thể tạo ra tổng bằng `i` từ một tập con của các số đã xét hay không.
   - **Trạng thái ban đầu:** `dp[0] = true` (luôn có thể tạo ra tổng bằng 0 bằng cách chọn tập rỗng). Các vị trí khác khởi tạo là `false`.
   - **Công thức truy hồi:**
     Với mỗi số `num` trong mảng `nums`, ta cập nhật mảng `dp` từ phải qua trái (từ `target` xuống `num`):
     $$dp[i] = dp[i] \lor dp[i - num]$$
     *Lưu ý:* Cần duyệt ngược từ `target` xuống `num` để tránh việc một phần tử `num` được sử dụng nhiều lần (đặc trưng của bài toán Knapsack 0/1 khi tối ưu hóa không gian lưu trữ xuống 1 chiều).

3. **Tối ưu hóa dừng sớm (Early Exit):**
   - Nếu tại bất kỳ thời điểm nào sau khi duyệt một phần tử `num` mà `dp[target]` trở thành `true`, ta có thể lập tức trả về `true` mà không cần xét các phần tử còn lại.

---

## Các bước thuật toán

1. **Tính tổng** `sum` của tất cả các phần tử trong mảng `nums`.
2. **Kiểm tra tính chẵn lẻ:** Nếu `sum % 2 != 0`, trả về `false`.
3. **Xác định mục tiêu:** `target = sum / 2`.
4. **Khởi tạo mảng DP:** Khởi tạo mảng boolean `dp` có kích thước `target + 1`. Đặt `dp[0] = true`.
5. **Cập nhật DP:** Duyệt qua từng số `num` trong `nums`:
   - Duyệt `i` từ `target` giảm dần về `num`:
     - `dp[i] = dp[i] || dp[i - num]`
   - Nếu `dp[target]` bằng `true`, trả về `true`.
6. **Trả về** kết quả của `dp[target]`.

---

## Ví dụ minh họa với `nums = [1, 5, 11, 5]`

- **Bước 1:** `sum = 1 + 5 + 11 + 5 = 22` (chẵn).
- **Bước 2:** `target = 22 / 2 = 11`.
- **Bước 3:** Khởi tạo `dp` kích thước 12, `dp[0] = true`, các phần tử còn lại là `false`.
- **Vòng lặp duyệt các phần tử:**
  - **`num = 1`:** Cập nhật từ 11 về 1:
    - `dp[1] = dp[1] || dp[0] = true`. Mảng `dp` có `dp[0]`, `dp[1]` là `true`.
  - **`num = 5`:** Cập nhật từ 11 về 5:
    - `dp[6] = dp[6] || dp[1] = true`
    - `dp[5] = dp[5] || dp[0] = true`
    - Các giá trị `true`: `0, 1, 5, 6`.
  - **`num = 11`:** Cập nhật từ 11 về 11:
    - `dp[11] = dp[11] || dp[0] = true`.
    - Vì `dp[11] (dp[target])` đã là `true`, thuật toán dừng sớm và trả về `true`.

Kết quả trả về: `true` (thỏa mãn yêu cầu ví dụ 1).

---

## Phân tích độ phức tạp

- **Độ phức tạp thời gian (Time Complexity):** $O(N \times \text{target})$
  - Trong đó $N$ là số lượng phần tử của mảng `nums`, và $\text{target} = \text{sum} / 2$.
  - Hai vòng lặp lồng nhau chạy tối đa $N \times \text{target}$ lần.
- **Độ phức tạp không gian (Space Complexity):** $O(\text{target})$
  - Chúng ta chỉ sử dụng một mảng một chiều `dp` có kích thước `target + 1` để lưu trữ trạng thái quy hoạch động.
