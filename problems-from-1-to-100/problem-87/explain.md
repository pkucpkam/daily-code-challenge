# Add Strings

## 1. Mô tả bài toán
Cho hai số nguyên không âm `num1` và `num2` dưới dạng chuỗi (String). Hãy trả về tổng của chúng cũng dưới dạng chuỗi.
Yêu cầu:
- Không được sử dụng các thư viện xử lý số lớn có sẵn (như `BigInteger`).
- Không được chuyển đổi trực tiếp chuỗi sang kiểu số nguyên (vì chuỗi có thể rất dài, gây tràn số).

## 2. Ý tưởng cốt lõi
- Mô phỏng quá trình cộng hai số theo cột dọc như khi làm toán trên giấy.
- Ta thực hiện cộng từng chữ số từ phải sang trái (từ hàng đơn vị lên).
- Cần duy trì một biến `carry` (biến nhớ) để lưu giá trị nhớ khi tổng của hai chữ số lớn hơn hoặc bằng 10.

## 3. Giải thích thuật toán
1. Khởi tạo `StringBuilder` để xây dựng chuỗi kết quả.
2. Sử dụng hai con trỏ `i` và `j` lần lượt trỏ vào cuối chuỗi `num1` và `num2`.
3. Trong khi vẫn còn chữ số để cộng (`i >= 0` hoặc `j >= 0`) hoặc vẫn còn giá trị nhớ (`carry > 0`):
   - Lấy giá trị chữ số tại `i` (nếu `i < 0` thì coi là 0): `x = num1.charAt(i) - '0'`.
   - Lấy giá trị chữ số tại `j` (nếu `j < 0` thì coi là 0): `y = num2.charAt(j) - '0'`.
   - Tính tổng hiện tại: `sum = x + y + carry`.
   - Chữ số ghi vào kết quả là `sum % 10`.
   - Cập nhật giá trị nhớ mới: `carry = sum / 10`.
4. Vì ta thêm các chữ số vào cuối `StringBuilder`, kết quả thu được sẽ bị ngược. Cần gọi hàm `reverse()` trước khi chuyển sang chuỗi.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(\max(N, M))\) - Với $N, M$ là độ dài của hai chuỗi. Ta duyệt qua mỗi chuỗi một lần.
- **Không gian (Space Complexity)**: \(O(\max(N, M))\) - Không gian để lưu trữ chuỗi kết quả.

## 5. Code (Java)
```java
class Solution {
    public String addStrings(String num1, String num2) {
        StringBuilder result = new StringBuilder();
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        int carry = 0;

        // Lặp khi còn chữ số hoặc còn nhớ
        while (i >= 0 || j >= 0 || carry > 0) {
            // Lấy chữ số tương ứng hoặc 0 nếu đã hết chuỗi
            int n1 = i >= 0 ? num1.charAt(i--) - '0' : 0;
            int n2 = j >= 0 ? num2.charAt(j--) - '0' : 0;
            
            // Tính tổng hàng hiện tại
            int sum = n1 + n2 + carry;
            result.append(sum % 10); // Ghi chữ số hàng đơn vị
            carry = sum / 10;        // Cập nhật biến nhớ
        }

        // Đảo ngược chuỗi vì ta cộng từ phải sang trái
        return result.reverse().toString();
    }
}
```
*(Ghi chú: Phương pháp này có thể áp dụng để cộng các số có độ dài hàng nghìn chữ số mà không gặp vấn đề về bộ nhớ).*
