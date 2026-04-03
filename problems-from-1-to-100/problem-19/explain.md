## 1. Mô tả bài toán
Bạn đang leo một cầu thang gồm `n` bậc. Mỗi lần, bạn có thể leo 1 hoặc 2 bậc. Bài toán yêu cầu tìm tổng số cách khác nhau để leo đến đỉnh cầu thang.

**Ví dụ:**
*   **Input: n = 2**
    *   **Output: 2**
    *   Giải thích: Có hai cách để leo lên đỉnh.
        1.  1 bước + 1 bước
        2.  2 bước
*   **Input: n = 3**
    *   **Output: 3**
    *   Giải thích: Có ba cách để leo lên đỉnh.
        1.  1 bước + 1 bước + 1 bước
        2.  1 bước + 2 bước
        3.  2 bước + 1 bước

## 2. Ý tưởng cốt lõi
Bài toán này là một ví dụ điển hình của quy hoạch động (Dynamic Programming). Để tìm số cách leo lên bậc `n`, chúng ta có thể xem xét bước cuối cùng. Bước cuối cùng có thể là một bước 1 bậc từ bậc `n-1` hoặc một bước 2 bậc từ bậc `n-2`.

Do đó, số cách để leo lên bậc `n` chính là tổng số cách để leo lên bậc `n-1` cộng với số cách để leo lên bậc `n-2`. Công thức đệ quy này rất giống với dãy Fibonacci.

`ways(n) = ways(n-1) + ways(n-2)`

Với các trường hợp cơ sở:
*   `ways(1) = 1` (chỉ có 1 cách: 1 bước)
*   `ways(2) = 2` (có 2 cách: 1+1 hoặc 2 bước)

## 3. Giải thích thuật toán
Thuật toán sử dụng phương pháp quy hoạch động với mảng để lưu trữ kết quả của các bài toán con, tránh tính toán lại.

1.  **Xử lý trường hợp cơ sở đặc biệt:**
    *   Nếu `n = 1`, chỉ có một cách để leo lên (1 bước). Trả về 1 ngay lập tức.

2.  **Khởi tạo mảng Quy hoạch động (DP array):**
    *   Tạo một mảng `dp` có kích thước `n + 1`. `dp[i]` sẽ lưu trữ số cách để leo đến bậc thứ `i`.
    *   Thiết lập các giá trị ban đầu cho các trường hợp cơ sở:
        *   `dp[1] = 1` (Một cách để leo lên bậc 1)
        *   `dp[2] = 2` (Hai cách để leo lên bậc 2: 1+1 hoặc 2)

3.  **Lặp và tính toán các giá trị DP:**
    *   Bắt đầu một vòng lặp từ `i = 3` đến `n`.
    *   Trong mỗi lần lặp, áp dụng công thức đệ quy đã tìm thấy:
        `dp[i] = dp[i - 1] + dp[i - 2]`
    *   Điều này có nghĩa là, số cách để leo lên bậc `i` là tổng số cách để leo lên bậc `i-1` (và sau đó bước 1 bậc) và số cách để leo lên bậc `i-2` (và sau đó bước 2 bậc).

4.  **Trả về kết quả:**
    *   Sau khi vòng lặp hoàn tất, giá trị `dp[n]` sẽ chứa tổng số cách khác nhau để leo đến đỉnh cầu thang `n` bậc.

## 4. Độ phức tạp

*   **Độ phức tạp thời gian (Time Complexity): O(n)**
    *   Thuật toán thực hiện một vòng lặp duy nhất từ `i = 3` đến `n`. Mỗi thao tác bên trong vòng lặp (cộng và gán) mất thời gian hằng số O(1). Do đó, tổng thời gian thực thi là tuyến tính với `n`.

*   **Độ phức tạp không gian (Space Complexity): O(n)**
    *   Chúng ta sử dụng một mảng `dp` có kích thước `n + 1` để lưu trữ các kết quả trung gian. Kích thước của mảng này tỷ lệ thuận với `n`.

## 5. Code
java
class Solution {
    public int climbStairs(int n) {
        // Trường hợp cơ sở: Nếu chỉ có 1 bậc, chỉ có 1 cách để leo.
        if (n == 1) {
            return 1;
        }

        // Khởi tạo mảng dp để lưu số cách leo đến mỗi bậc.
        // dp[i] sẽ lưu trữ số cách để leo đến bậc thứ i.
        int[] dp = new int[n + 1];

        // Thiết lập các trường hợp cơ sở:
        // Có 1 cách để leo lên bậc 1 (bước 1).
        dp[1] = 1;
        // Có 2 cách để leo lên bậc 2 (1+1 hoặc 2).
        dp[2] = 2;

        // Tính toán số cách cho các bậc từ 3 đến n.
        // dp[i] = dp[i-1] + dp[i-2]
        // Số cách leo lên bậc i là tổng số cách leo lên bậc i-1
        // (và sau đó bước 1 bậc) và số cách leo lên bậc i-2
        // (và sau đó bước 2 bậc).
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        // Kết quả cuối cùng là số cách để leo lên bậc n.
        return dp[n];
    }
}