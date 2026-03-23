# 201. Bitwise AND of Numbers Range - Best Solution

## Ý tưởng tối ưu

Phép AND trên một dải số chỉ giữ lại **phần bit tiền tố chung** của `left` và `right`.

Nếu một bit nào đó thay đổi từ 0 sang 1 (hoặc ngược lại) trong đoạn `[left, right]`, thì bit đó chắc chắn bị AND về `0`.

Vì vậy, ta dịch phải cả `left` và `right` cho đến khi chúng bằng nhau (tức đã về cùng tiền tố), đếm số lần dịch, rồi dịch trái lại đúng số lần đó.

## Tại sao đây là "best solution"

- Không cần duyệt từng số trong đoạn, tránh `O(right - left + 1)`.
- Chỉ xử lý tối đa số bit của số nguyên (với `int` là khoảng 31 lần).
- Code ngắn, rõ, đúng bản chất bitwise.

## Thuật toán

1. Khởi tạo `shifts = 0`.
2. Trong khi `left < right`:
- Dịch phải `left` 1 bit.
- Dịch phải `right` 1 bit.
- Tăng `shifts`.
3. Khi vòng lặp kết thúc, `left == right` là tiền tố chung.
4. Trả về `left << shifts`.

## Code (Java)

```java
class Solution {
    public int rangeBitwiseAnd(int left, int right) {
        int shifts = 0;

        while (left < right) {
            left >>= 1;
            right >>= 1;
            shifts++;
        }

        return left << shifts;
    }
}
```

## Độ phức tạp

- Time: `O(31)` ~ `O(1)` với kiểu `int`
- Space: `O(1)`

## Ví dụ nhanh

Với `left = 5 (101)` và `right = 7 (111)`:

- Dịch 1 lần: `10` và `11`
- Dịch 2 lần: `1` và `1` (đã bằng nhau)
- Dịch trái lại 2 lần: `1 << 2 = 100 (4)`

Kết quả: `4`



