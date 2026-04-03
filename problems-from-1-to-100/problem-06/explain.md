## 1. Mô tả bài toán

Bài toán yêu cầu kiểm tra xem một số nguyên `x` có phải là số Palindrome hay không. Một số được gọi là Palindrome nếu nó đọc giống nhau từ trái sang phải và từ phải sang trái.

Ví dụ:
*   `121` là số Palindrome vì đọc từ trái sang phải là `121` và từ phải sang trái cũng là `121`.
*   `-121` không phải là số Palindrome vì đọc từ trái sang phải là `-121`, nhưng từ phải sang trái sẽ là `121-`.
*   `10` không phải là số Palindrome vì đọc từ trái sang phải là `10`, nhưng từ phải sang trái sẽ là `01`.

## 2. Ý tưởng cốt lõi

Ý tưởng cốt lõi để giải quyết bài toán này mà không chuyển đổi số sang chuỗi là tạo ra một số đảo ngược từ số ban đầu. Sau đó, so sánh số đảo ngược này với số ban đầu. Nếu chúng bằng nhau, số đó là Palindrome.

Có hai trường hợp đặc biệt cần xử lý:
1.  **Số âm**: Bất kỳ số âm nào cũng không thể là Palindrome (ví dụ: -121 đọc ngược là 121-).
2.  **Số dương kết thúc bằng 0**: Bất kỳ số dương nào kết thúc bằng 0 (ngoại trừ số 0) cũng không thể là Palindrome (ví dụ: 10 đọc ngược là 01). Nếu số đó kết thúc bằng 0, thì số đảo ngược của nó phải bắt đầu bằng 0, nhưng một số không thể bắt đầu bằng 0 (trừ khi nó chỉ có một chữ số 0).

## 3. Giải thích thuật toán

Thuật toán hoạt động theo các bước sau:

1.  **Xử lý các trường hợp đặc biệt**:
    *   Nếu `x` nhỏ hơn 0, trả về `false` ngay lập tức.
    *   Nếu `x` lớn hơn 0 và `x` chia hết cho 10 (tức là chữ số cuối cùng là 0), trả về `false` ngay lập tức. (Ví dụ: `10`, `120`)

2.  **Khởi tạo biến**:
    *   `reversed`: Biến này sẽ lưu trữ số được đảo ngược, khởi tạo bằng 0.
    *   `original`: Biến này lưu trữ giá trị gốc của `x` để so sánh sau này, khởi tạo bằng `x`.

3.  **Vòng lặp để đảo ngược số**:
    *   Sử dụng một vòng lặp `while` tiếp tục cho đến khi `x` trở thành 0.
    *   Trong mỗi lần lặp:
        *   Lấy chữ số cuối cùng của `x` bằng phép toán `x % 10`.
        *   Thêm chữ số này vào `reversed`: `reversed = reversed * 10 + (x % 10)`. Phép nhân với 10 dịch chuyển các chữ số hiện có của `reversed` sang trái, tạo chỗ cho chữ số mới.
        *   Loại bỏ chữ số cuối cùng khỏi `x` bằng phép toán `x /= 10`.

4.  **So sánh kết quả**:
    *   Sau khi vòng lặp kết thúc, `reversed` sẽ chứa số đảo ngược của `original`.
    *   So sánh `original` với `reversed`. Nếu chúng bằng nhau, trả về `true`. Ngược lại, trả về `false`.

**Ví dụ minh họa với `x = 121`:**

1.  `x = 121`. Không rơi vào trường hợp đặc biệt (`x < 0` hoặc `(x % 10 == 0 && x != 0)`).
2.  `reversed = 0`, `original = 121`.
3.  **Vòng lặp:**
    *   **Lần 1:**
        *   `x = 121`
        *   `reversed = 0 * 10 + 121 % 10 = 0 + 1 = 1`
        *   `x = 121 / 10 = 12`
    *   **Lần 2:**
        *   `x = 12`
        *   `reversed = 1 * 10 + 12 % 10 = 10 + 2 = 12`
        *   `x = 12 / 10 = 1`
    *   **Lần 3:**
        *   `x = 1`
        *   `reversed = 12 * 10 + 1 % 10 = 120 + 1 = 121`
        *   `x = 1 / 10 = 0`
    *   Vòng lặp kết thúc vì `x` là 0.
4.  **So sánh**: `original (121) == reversed (121)`. Trả về `true`.

## 4. Độ phức tạp

*   **Độ phức tạp thời gian (Time Complexity)**: O(log N)
    *   Trong mỗi bước của vòng lặp `while`, chúng ta chia `x` cho 10. Điều này có nghĩa là số lượng chữ số của `x` giảm đi 1 trong mỗi bước.
    *   Số lượng chữ số của một số N tỉ lệ thuận với log cơ số 10 của N (log₁₀ N). Do đó, số lần lặp của vòng lặp là khoảng log₁₀ N.
    *   Các phép toán bên trong vòng lặp (nhân, chia, cộng, modulo) là các phép toán có độ phức tạp O(1).
    *   Vậy, độ phức tạp thời gian tổng thể là O(log N).

*   **Độ phức tạp không gian (Space Complexity)**: O(1)
    *   Chúng ta chỉ sử dụng một vài biến có kích thước cố định (`x`, `reversed`, `original`) để lưu trữ dữ liệu.
    *   Lượng bộ nhớ sử dụng không tăng theo kích thước của số đầu vào `x`.
    *   Vậy, độ phức tạp không gian là O(1).

## 5. Code

java
class Solution {
    public boolean isPalindrome(int x) {
        // Xử lý các trường hợp đặc biệt:
        // 1. Số âm không thể là Palindrome.
        // 2. Số dương kết thúc bằng 0 (ngoại trừ số 0 chính nó) không thể là Palindrome.
        //    Ví dụ: 10 đọc ngược là 01, không phải Palindrome.
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int reversed = 0; // Biến lưu trữ số đã đảo ngược
        int original = x; // Lưu giữ giá trị ban đầu của x

        // Vòng lặp để xây dựng số đảo ngược
        while (x > 0) {
            // Lấy chữ số cuối cùng của x và thêm vào reversed
            reversed = reversed * 10 + x % 10;
            // Loại bỏ chữ số cuối cùng khỏi x
            x /= 10;
        }

        // So sánh số ban đầu với số đã đảo ngược
        return original == reversed;
    }
}