# Add Binary

## 1. Mô tả bài toán
Cho hai chuỗi nhị phân `a` và `b`. Nhiệm vụ là trả về tổng của chúng dưới dạng một chuỗi nhị phân.

## 2. Ý tưởng cốt lõi
- Bài toán này tương tự như phép cộng hai số nguyên bằng tay ở hệ thập phân, nhưng áp dụng cho hệ nhị phân (cơ số 2).
- Chúng ta duyệt từ cuối chuỗi (hàng đơn vị) lên đầu chuỗi.
- Sử dụng một biến `carry` (biến nhớ) để lưu giá trị cộng dồn vượt quá 1.

## 3. Giải thích thuật toán
1. Sử dụng `StringBuilder` để xây dựng chuỗi kết quả một cách hiệu quả.
2. Khởi tạo hai con trỏ `i` và `j` trỏ vào cuối hai chuỗi `a` và `b`, cùng biến `carry = 0`.
3. Vòng lặp chạy khi vẫn còn ký tự ở `a`, `b` hoặc biến `carry` vẫn còn giá trị (> 0):
   - Lấy giá trị của ký tự tại `i` (nếu có) và cộng vào `sum`.
   - Lấy giá trị của ký tự tại `j` (nếu có) và cộng vào `sum`.
   - Cộng thêm biến `carry` vào `sum`.
   - Ký tự mới ở vị trí hiện tại sẽ là `sum % 2` (phần dư).
   - Biến nhớ mới sẽ là `carry = sum / 2` (phần nguyên).
4. Do ta thêm các số vào cuối `StringBuilder`, chuỗi thu được sẽ bị ngược. Ta cần dùng hàm `reverse()` trước khi chuyển sang chuỗi (`toString()`).

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(\max(N, M))\) với \(N, M\) là độ dài của hai chuỗi. Ta chỉ duyệt qua mỗi chuỗi một lần.
- **Không gian (Space Complexity)**: \(O(\max(N, M))\) để lưu trữ chuỗi kết quả trong `StringBuilder`.

## 5. Code (Java)
```java
public class Solution {
    public static String addBinary(String a, String b) {
        StringBuilder result = new StringBuilder();
        int i = a.length() - 1, j = b.length() - 1, carry = 0;

        while (i >= 0 || j >= 0 || carry > 0) {
            int sum = carry;
            if (i >= 0) sum += a.charAt(i--) - '0';
            if (j >= 0) sum += b.charAt(j--) - '0';
            result.append(sum % 2);
            carry = sum / 2;
        }

        return result.reverse().toString();
    }
}
```
