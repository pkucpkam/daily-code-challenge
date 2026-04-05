# 227. Basic Calculator II - Best Solution

## Mô tả bài toán

Cho một chuỗi `s` biểu diễn một biểu thức số học gồm các số nguyên không âm và các toán tử `+`, `-`, `*`, `/`.

Yêu cầu: tính giá trị biểu thức và trả về kết quả.

Phép chia phải được làm tròn về phía 0.

## Ý tưởng cốt lõi

Bài này không cần dùng stack đầy đủ nếu ta giữ hai biến trung gian:

- `result`: tổng các phần đã chốt xong.
- `lastNumber`: giá trị gần nhất chưa cộng vào `result` vì còn có thể bị nhân hoặc chia tiếp.

Khi gặp một toán tử mới hoặc đi đến cuối chuỗi:

- Nếu toán tử trước là `+`, cộng `lastNumber` vào `result` rồi gán `currentNumber` cho `lastNumber`.
- Nếu là `-`, cộng `lastNumber` vào `result` rồi gán `-currentNumber` cho `lastNumber`.
- Nếu là `*`, cập nhật trực tiếp `lastNumber = lastNumber * currentNumber`.
- Nếu là `/`, cập nhật trực tiếp `lastNumber = lastNumber / currentNumber`.

Cuối cùng trả về `result + lastNumber`.

## Vì sao đúng

Phép `*` và `/` có độ ưu tiên cao hơn `+` và `-`.

Khi gặp `*` hoặc `/`, ta không chốt ngay vào `result`, mà sửa trực tiếp `lastNumber`. Nhờ vậy, toàn bộ chuỗi số liên tiếp được xử lý đúng thứ tự ưu tiên mà không cần cấu trúc dữ liệu phức tạp.

## Minh họa nhanh

Ví dụ: `3+2*2`

- Đọc `3`, gặp `+` nên `lastNumber = 3`
- Đọc `2`, gặp `*` nên chốt `3` vào `result`, rồi `lastNumber = 2`
- Đọc `2`, hết chuỗi nên `lastNumber = 2 * 2 = 4`
- Kết quả: `result + lastNumber = 3 + 4 = 7`

Ví dụ: ` 3+5 / 2 `

- `3` -> `lastNumber = 3`
- gặp `+` -> chốt `3`
- `5` -> `lastNumber = 5`
- gặp `/` -> chốt `5`
- `2` -> `lastNumber = 5 / 2 = 2`
- Kết quả: `3 + 2 = 5`

## Độ phức tạp

- Thời gian: `O(n)`
- Bộ nhớ: `O(1)`

## Java code

```java
class Solution {
   public int calculate(String s) {
      int result = 0;
      int lastNumber = 0;
      int currentNumber = 0;
      char operation = '+';

      for (int index = 0; index < s.length(); index++) {
         char currentChar = s.charAt(index);

         if (Character.isDigit(currentChar)) {
            currentNumber = currentNumber * 10 + (currentChar - '0');
         }

         if (!Character.isDigit(currentChar) && currentChar != ' ' || index == s.length() - 1) {
            if (operation == '+') {
               result += lastNumber;
               lastNumber = currentNumber;
            } else if (operation == '-') {
               result += lastNumber;
               lastNumber = -currentNumber;
            } else if (operation == '*') {
               lastNumber = lastNumber * currentNumber;
            } else if (operation == '/') {
               lastNumber = lastNumber / currentNumber;
            }

            operation = currentChar;
            currentNumber = 0;
         }
      }

      return result + lastNumber;
   }
}
```
