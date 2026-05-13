# 303. Count Numbers with Unique Digits

**Độ khó:** Medium

## Mô Tả Bài Toán

Cho số nguyên `n`, hãy đếm số lượng các số có các chữ số khác nhau sao cho `0 <= x < 10^n`.

## Ý Tưởng Giải

Ta không cần duyệt từng số. Bài này có thể giải bằng **tổ hợp**:

- Với `n = 0`, chỉ có số `0`, nên kết quả là `1`.
- Với `n >= 1`, ta đếm theo số chữ số:
  - Số có 1 chữ số: có `10` số, từ `0` đến `9`.
  - Số có 2 chữ số khác nhau: chữ số đầu có `9` cách chọn, chữ số sau có `9` cách chọn.
  - Số có 3 chữ số khác nhau: `9 * 9 * 8` cách.
  - Tiếp tục như vậy cho đến khi hết chữ số khả dụng.

Vì chỉ có 10 chữ số từ `0` đến `9`, nên sau khi chọn đủ 10 chữ số thì không còn số mới nào có chữ số khác nhau nữa.

## Công Thức

Gọi:

- `result` là tổng số lượng số hợp lệ
- `uniqueDigits` là số lượng số hợp lệ của độ dài hiện tại
- `availableDigits` là số chữ số còn có thể chọn

Ta khởi tạo:

- `result = 10`
- `uniqueDigits = 9`
- `availableDigits = 9`

Sau đó lặp từ độ dài `2` đến `n`:

- `uniqueDigits *= availableDigits`
- `result += uniqueDigits`
- `availableDigits--`

## Ví Dụ

Với `n = 2`:

- Số 1 chữ số: `10`
- Số 2 chữ số khác nhau: `9 * 9 = 81`
- Tổng: `10 + 81 = 91`

## Độ Phức Tạp

- Thời gian: `O(n)`
- Không gian: `O(1)`

## Code

```java
class Solution {
   public int countNumbersWithUniqueDigits(int n) {
      if (n == 0) {
         return 1;
      }

      int result = 10;
      int uniqueDigits = 9;
      int availableDigits = 9;

      for (int length = 2; length <= n && availableDigits > 0; length++) {
         uniqueDigits *= availableDigits;
         result += uniqueDigits;
         availableDigits--;
      }

      return result;
   }
}
```