# Fraction to Recurring Decimal

## Yêu cầu bài toán

- Cho hai số nguyên đại diện cho tử số (`numerator`) và mẫu số (`denominator`).
- Trả về giá trị của phân số dưới dạng chuỗi thập phân.
- Nếu phần thập phân có tính chất vô hạn tuần hoàn, hãy đặt phần lặp lại trong dấu ngoặc đơn `()`.

## Ý tưởng cốt lõi

Phép chia này có thể được thực hiện giống như cách chia tay trên giấy. Điểm mấu chốt để phát hiện phần lặp lại (tuần hoàn) là theo dõi các **số dư (remainders)**:
- Khi chúng ta gặp lại một số dư đã từng xuất hiện trước đó, nghĩa là chu kỳ lặp lại bắt đầu từ vị trí mà số dư đó xuất hiện lần đầu.
- Chúng ta sử dụng một `HashMap<Long, Integer>` để lưu trữ: `số dư -> vị trí hiện tại trong chuỗi kết quả`.

## Thuật toán

1. Kiểm tra nếu tử số bằng 0 thì trả về "0".
2. Xử lý dấu của kết quả (âm hay dương) bằng cách so sánh dấu của tử và mẫu.
3. Chuyển đổi các số sang kiểu `long` để tránh tràn số (ví dụ trường hợp `Integer.MIN_VALUE`). Tính giá trị tuyệt đối.
4. Tính phần nguyên (`num / den`) và phần dư ban đầu (`num % den`).
5. Nếu phần dư bằng 0, trả về kết quả ngay.
6. Thêm dấu chấm thập phân vào chuỗi.
7. Sử dụng một `HashMap` để ghi lại vị trí các số dư trong phần thập phân.
8. Trong khi phần dư khác 0:
   - Nếu số dư đã có trong `HashMap`:
     - Chèn dấu ngoặc đơn mở `(` vào vị trí đã lưu.
     - Thêm dấu ngoặc đơn đóng `)` vào cuối chuỗi.
     - Trả về kết quả.
   - Lưu số dư và vị trí hiện tại vào `HashMap`.
   - Nhân số dư với 10, tính chữ số tiếp theo (`remainder / den`) và cập nhật số dư mới (`remainder % den`).
9. Trả về toàn bộ chuỗi kết quả.

## Độ phức tạp
- **Thời gian**: $O(\text{độ dài kết quả})$ - Thông thường bị giới hạn bởi yêu cầu của bài toán (ví dụ tối đa $10^4$ ký tự).
- **Không gian**: $O(\text{độ dài kết quả})$ - Lưu trữ số dư trong HashMap.

## Code (Java)

```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) return "0";
        
        StringBuilder res = new StringBuilder();
        // Kiểm tra dấu
        if ((numerator < 0) ^ (denominator < 0)) res.append("-");
        
        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);
        
        // Phần nguyên
        res.append(num / den);
        long remainder = num % den;
        if (remainder == 0) return res.toString();
        
        // Phần thập phân
        res.append(".");
        Map<Long, Integer> map = new HashMap<>();
        while (remainder != 0) {
            if (map.containsKey(remainder)) {
                res.insert(map.get(remainder), "(");
                res.append(")");
                break;
            }
            map.put(remainder, res.length());
            remainder *= 10;
            res.append(remainder / den);
            remainder %= den;
        }
        
        return res.toString();
    }
}
```
