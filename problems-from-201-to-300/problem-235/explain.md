# Factorial Trailing Zeroes

## Yêu cầu bài toán

- Cho số nguyên `n`.
- Trả về số lượng chữ số `0` ở cuối của `n!`.

Ví dụ:

- `n = 5` => `5! = 120` => có `1` số 0 ở cuối.

## Ý tưởng cốt lõi

Một chữ số 0 ở cuối được tạo bởi một cặp thừa số `2 * 5`.

Trong `n!`, số lượng thừa số `2` luôn nhiều hơn rất nhiều so với thừa số `5`, nên:

- Số chữ số 0 ở cuối `n!`
- chính là số lượng thừa số `5` có trong tích `1 * 2 * ... * n`.

## Cách đếm số lượng thừa số 5

Ta không cần tính trực tiếp `n!` (vì rất lớn). Chỉ cần đếm:

- Số bội của `5`: `floor(n / 5)` (mỗi số đóng góp ít nhất 1 thừa số 5).
- Số bội của `25`: `floor(n / 25)` (mỗi số này có thêm 1 thừa số 5 nữa).
- Số bội của `125`: `floor(n / 125)` (thêm 1 nữa), ...

Vậy công thức:

`answer = floor(n/5) + floor(n/25) + floor(n/125) + ...`

Khi lũy thừa của `5` lớn hơn `n` thì dừng.

## Vì sao đúng?

- Mỗi bội của `5` góp ít nhất một thừa số `5`.
- Mỗi bội của `25` có hai thừa số `5`, nên cần cộng thêm một lần nữa.
- Mỗi bội của `125` có ba thừa số `5`, nên cộng thêm tiếp, ...

Phép cộng theo các mức `5, 25, 125, ...` chính xác là cách đếm đầy đủ tổng số thừa số `5` trong `n!`.
Do đó kết quả thu được đúng bằng số chữ số 0 ở cuối.

## Độ phức tạp

- Thời gian: `O(log_5 n)` (mỗi vòng chia `n` cho `5`).
- Không gian phụ: `O(1)`.

## Code (Java)

```java
class Solution {
    public int trailingZeroes(int n) {
        int count = 0;

        while (n > 0) {
            n /= 5;
            count += n;
        }

        return count;
    }
}
```
