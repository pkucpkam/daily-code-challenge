# Giải Thích Bài Toán: 322. Coin Change

Mục tiêu của bài toán là tìm số lượng đồng xu ít nhất để tạo thành một số tiền `amount` cho trước từ một danh sách các mệnh giá đồng xu `coins`.

## Phương Pháp: Quy Hoạch Động (Bottom-Up)

Chúng ta sử dụng **Quy hoạch động (Dynamic Programming - DP)** vì bài toán này có hai tính chất quan trọng:
1.  **Bài toán con trùng lặp (Overlapping Subproblems)**: Để tính số đồng xu cho `amount=11`, chúng ta có thể cần biết kết quả của `amount=10`, `amount=9`, và `amount=6`.
2.  **Cấu trúc con tối ưu (Optimal Substructure)**: Giải pháp tối ưu cho một số tiền lớn được xây dựng từ các giải pháp tối ưu của các số tiền nhỏ hơn.

### 1. Định Nghĩa Trạng Thái
Gọi `dp[i]` là **số lượng đồng xu ít nhất** cần thiết để tạo ra số tiền `i`.

### 2. Công Thức Chuyển Trạng Thái
Với mỗi số tiền `i` từ `1` đến `amount`, và với mỗi đồng xu trong danh sách `coins`:
- Nếu `i >= coin`, thì `dp[i] = min(dp[i], dp[i - coin] + 1)`.

**Ý nghĩa:** "Số đồng xu ít nhất để có số tiền `i` sẽ là giá trị nhỏ nhất giữa: giá trị hiện tại của nó HOẶC là 1 (chính là đồng xu đang xét) cộng với số đồng xu ít nhất để tạo ra phần tiền còn lại (`i - coin`)."

### 3. Khởi Tạo và Trường Hợp Cơ Sở
- `dp[0] = 0`: Cần 0 đồng xu để tạo ra số tiền bằng 0.
- `dp[1...amount] = amount + 1`: Khởi tạo với một giá trị lớn hơn bất kỳ kết quả khả thi nào (vì số lượng đồng xu tối đa chỉ có thể là `amount` nếu tất cả là đồng xu mệnh giá 1).

### 4. Phân Tích Độ Phức Tạp
- **Độ phức tạp thời gian**: $O(S \times n)$, trong đó $S$ là `amount` và $n$ là số lượng mệnh giá đồng xu. Chúng ta duyệt qua mảng `dp` một lần, và với mỗi phần tử, chúng ta kiểm tra tất cả các loại đồng xu.
- **Độ phức tạp không gian**: $O(S)$, để lưu trữ mảng `dp` có kích thước `amount + 1`.

## Ví Dụ Minh Họa
**Ví dụ:** `coins = [1, 2, 5]`, `amount = 5`

| Số tiền (i) | Cách tính | Số xu ít nhất (`dp[i]`) |
| :--- | :--- | :--- |
| 0 | Trường hợp cơ sở | 0 |
| 1 | `dp[1-1]+1 = 1` | 1 |
| 2 | `min(dp[2-1]+1, dp[2-2]+1) = min(2, 1)` | 1 |
| 3 | `min(dp[3-1]+1, dp[3-2]+1) = min(2, 2)` | 2 |
| 4 | `min(dp[4-1]+1, dp[4-2]+1) = min(3, 2)` | 2 |
| 5 | `min(dp[5-1]+1, dp[5-2]+1, dp[5-5]+1) = min(3, 3, 1)` | 1 |

**Kết quả:** `dp[5] = 1` (Sử dụng một đồng xu mệnh giá 5).
