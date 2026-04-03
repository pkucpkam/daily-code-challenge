# Ugly Number

## 1. Mô tả bài toán
Một số được gọi là **ugly number** (số xấu xí) nếu nó là một số nguyên dương và các ước số nguyên tố của nó chỉ bao gồm 2, 3 và 5.
Cho một số nguyên `n`, hãy trả về `true` nếu `n` là một ugly number, ngược lại trả về `false`.
Lưu ý: Số 1 được coi là một ugly number.

## 2. Ý tưởng cốt lõi
- Để kiểm tra xem một số có ước nguyên tố nào khác 2, 3, 5 hay không, ta thực hiện chia liên tiếp số đó cho 2, 3 và 5 cho đến khi không thể chia hết được nữa.
- Nếu sau quá trình chia, số còn lại là 1, nghĩa là nó chỉ có các ước nguyên tố trong bộ {2, 3, 5}.
- Nếu số còn lại lớn hơn 1, nghĩa là nó có ít nhất một ước nguyên tố khác (ví dụ 7, 11, ...).

## 3. Giải thích thuật toán
1. Trường hợp cơ sở: Nếu `n <= 0`, trả về `false` (vì ugly number phải là số dương).
2. Sử dụng vòng lặp `while` để chia `n` cho 2 cho đến khi không còn chia hết (`n % 2 == 0`).
3. Tương tự, sử dụng vòng lặp `while` để chia `n` cho 3.
4. Tương tự, sử dụng vòng lặp `while` để chia `n` cho 5.
5. Sau tất cả các bước chia, kiểm tra giá trị cuối cùng của `n`:
   - Nếu `n == 1`, trả về `true`.
   - Ngược lại, trả về `false`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(\log_2 n + \log_3 n + \log_5 n)\), có thể viết gọn là \(O(\log n)\). Số lần lặp phụ thuộc vào bao nhiêu lần `n` có thể chia hết cho 2, 3, 5.
- **Không gian (Space Complexity)**: \(O(1)\) - Không sử dụng thêm cấu trúc dữ liệu nào.

## 5. Code (Java)
```java
class Solution {
    public boolean isUgly(int n) {
        // Ugly number phải là số nguyên dương
        if (n <= 0) {
            return false;
        }

        // Chia cho 2 cho đến khi không chia hết
        while (n % 2 == 0) {
            n /= 2;
        }
        // Chia cho 3 cho đến khi không chia hết
        while (n % 3 == 0) {
            n /= 3;
        }
        // Chia cho 5 cho đến khi không chia hết
        while (n % 5 == 0) {
            n /= 5;
        }

        // Nếu kết quả cuối cùng là 1 thì đó là số ugly
        return n == 1;
    }
}
```
