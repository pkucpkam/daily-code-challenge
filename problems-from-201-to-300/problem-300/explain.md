# 343. Integer Break - Giải thích thuật toán

## Ý tưởng chính
Bài toán yêu cầu tách số nguyên `n` thành ít nhất hai số nguyên dương sao cho tích của chúng là lớn nhất.

Quan sát quan trọng là:
- Số `3` giúp tăng tích tốt nhất trong hầu hết các trường hợp.
- Nếu còn dư `1`, ta không nên giữ `3 + 1`, mà nên đổi thành `2 + 2` vì `2 × 2 = 4` lớn hơn `3 × 1 = 3`.
- Với `n <= 3`, ta vẫn phải tách thành ít nhất hai phần, nên kết quả là `n - 1`.

## Cách làm
1. Nếu `n <= 3`, trả về `n - 1`.
2. Trong khi `n > 4`, lấy ra một số `3` và nhân vào đáp án.
3. Khi `n <= 4`, nhân phần còn lại vào kết quả và trả về.

## Vì sao dừng ở 4?
Nếu còn `4`, ta giữ nguyên `4` thay vì tách tiếp thành `2 + 2`, vì hai cách cho cùng tích là `4`.
Điều này giúp công thức đơn giản và tránh trường hợp tạo ra `1` không cần thiết.

## Ví dụ
Với `n = 10`:
- Lấy `3`, còn `7`
- Lấy tiếp `3`, còn `4`
- Nhân phần còn lại `4`
- Kết quả: `3 × 3 × 4 = 36`

## Độ phức tạp
- Thời gian: `O(n / 3)`
- Không gian: `O(1)`

## Code tham khảo
```java
class Solution {
    public int integerBreak(int n) {
        if (n <= 3) {
            return n - 1;
        }

        int product = 1;
        while (n > 4) {
            product *= 3;
            n -= 3;
        }

        return product * n;
    }
}
```

