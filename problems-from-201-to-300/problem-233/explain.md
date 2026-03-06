# Fraction to Recurring Decimal (Problem 233)

## Yêu cầu bài toán

- Chắp cho bạn cặp số gồm Tử số `numerator` và Mẫu số `denominator`.
- Cầu xin bạn vạch trần ra kết quả bằng phép toán chia thập phân của nó, nhét chuỗi kết quả đóng đít rập lót bằng chữ `String`.
- Phạt siêu nặng: Nếu vũng dãi dẹo của cái phép chia sau dấu xẻng lẻ thập phân có nứt vòng lặp liên hoan nhịp lướt chu kì "vô hạn tuần hoàn" chắp sập đi lại. Phải kìm đầu mổ bọc ngoặc cái phân cung tuần hoàn đó bằng móc kẹp `(...)`.
- Trả vát lố chuỗi, ví dụ: 4/333 xỏ móc `"0.(012)"`. 

*(Bài này tương tự bài 232, xin vui lòng xem giải thích chi tiết tại bài 232 phía trên cùng kho).*
*(This problem is exactly the same as problem 232. The algorithmic idea and implementation are identical, mapping remainders to detect recurring cycles.)*

## Code (Java)

```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) {
            return "0";
        }

        StringBuilder result = new StringBuilder();

        if ((numerator < 0) ^ (denominator < 0)) {
            result.append('-');
        }

        long dividend = Math.abs((long) numerator);
        long divisor = Math.abs((long) denominator);

        result.append(dividend / divisor);

        long remainder = dividend % divisor;
        if (remainder == 0) {
            return result.toString();
        }

        result.append('.');

        Map<Long, Integer> remainderToIndex = new HashMap<>();

        while (remainder != 0) {
            if (remainderToIndex.containsKey(remainder)) {
                int repeatStart = remainderToIndex.get(remainder);
                result.insert(repeatStart, '(');
                result.append(')');
                break;
            }

            remainderToIndex.put(remainder, result.length());
            remainder *= 10;
            result.append(remainder / divisor);
            remainder %= divisor;
        }

        return result.toString();
    }
}
```