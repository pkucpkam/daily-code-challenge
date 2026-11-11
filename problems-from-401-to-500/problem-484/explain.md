# Giải thích bài 678 - Valid Parenthesis String

## Ý tưởng

Ta duy trì một khoảng giá trị có thể có của số ngoặc mở chưa được ghép sau
mỗi ký tự:

- `minOpen`: số ngoặc mở chưa ghép nhỏ nhất.
- `maxOpen`: số ngoặc mở chưa ghép lớn nhất.

Với từng ký tự:

- `'('`: tăng cả `minOpen` và `maxOpen`.
- `')'`: giảm cả hai giá trị.
- `'*'`: có thể là `'('`, `')'`, hoặc chuỗi rỗng, nên giảm `minOpen` và
  tăng `maxOpen`.

Nếu `maxOpen < 0`, số ngoặc đóng đã vượt quá mọi khả năng ghép hợp lệ, nên
chuỗi chắc chắn không hợp lệ. Vì số ngoặc mở không thể nhỏ hơn `0`, ta đặt
`minOpen = 0` nếu giá trị này bị âm.

Sau khi duyệt hết chuỗi, chuỗi hợp lệ khi `minOpen == 0`, nghĩa là có ít nhất
một cách diễn giải các dấu `'*'` để không còn ngoặc mở chưa ghép.

## Chứng minh tính đúng đắn

### Bất biến

Sau khi xử lý mỗi tiền tố của chuỗi, khoảng `[minOpen, maxOpen]` chứa tất cả
các giá trị có thể có của số ngoặc mở chưa ghép.

### Chứng minh

- Với `'('`, số ngoặc mở chưa ghép tăng một đơn vị trong mọi cách diễn giải,
  nên cả hai cận đều tăng một.
- Với `')'`, số ngoặc mở chưa ghép giảm một đơn vị, nên cả hai cận đều giảm
  một.
- Với `'*'`, ta có thể chọn `'('`, `')'`, hoặc rỗng. Vì vậy giá trị nhỏ nhất
  giảm một và giá trị lớn nhất tăng một.

Do đó bất biến luôn được duy trì. Nếu `maxOpen < 0`, không có cách diễn giải
nào đủ ngoặc mở để ghép các ngoặc đóng, nên trả về `false`. Nếu duyệt hết
chuỗi mà `minOpen == 0`, tồn tại một cách diễn giải tạo thành chuỗi cân bằng,
nên trả về `true`.

## Độ phức tạp

Với `n` là độ dài chuỗi:

- **Thời gian:** `O(n)`
- **Bộ nhớ:** `O(1)`

## Mã nguồn

```java
class Solution {
    public boolean checkValidString(String s) {
        int minOpen = 0;
        int maxOpen = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                minOpen++;
                maxOpen++;
            } else if (c == ')') {
                minOpen--;
                maxOpen--;
            } else {
                minOpen--;
                maxOpen++;
            }

            if (maxOpen < 0) {
                return false;
            }

            minOpen = Math.max(minOpen, 0);
        }

        return minOpen == 0;
    }
}
```
