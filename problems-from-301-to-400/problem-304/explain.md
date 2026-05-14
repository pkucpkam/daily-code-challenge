# 365. Water and Jug Problem

**Độ khó:** Medium

## Mô Tả Bài Toán

Bạn có hai cái bình với dung tích là `x` và `y` lít. Bạn có một nguồn nước vô hạn. Bạn cần xác định xem có thể đong được chính xác `target` lít nước hay không.

Các thao tác cho phép:
- Đổ đầy nước vào một bình.
- Đổ hết nước trong một bình ra ngoài.
- Đổ nước từ bình này sang bình kia cho đến khi bình nhận đầy hoặc bình đổ hết.

## Ý Tưởng Giải

Bài toán này thực chất là một bài toán về **Số học** (Number Theory), cụ thể là liên quan đến **Định lý Bézout** (Bézout's Identity).

### Định lý Bézout
Định lý phát biểu rằng: Với hai số nguyên `a` và `b` không đồng thời bằng 0, phương trình:
`ax + by = d`
có nghiệm nguyên `x, y` khi và chỉ khi `d` là bội số của `gcd(a, b)` (ước chung lớn nhất của `a` và `b`).

### Áp dụng vào bài toán
Trong bài toán đong nước, mọi trạng thái lượng nước tổng cộng mà chúng ta có thể tạo ra luôn có dạng `m*x + n*y` (với `m, n` là các số nguyên, có thể âm nếu ta đổ nước ra ngoài).

Vì vậy, ta có hai điều kiện để có thể đong được `target` lít:
1. Tổng dung tích của hai bình phải lớn hơn hoặc bằng `target`: `x + y >= target`.
2. `target` phải chia hết cho `gcd(x, y)`.

## Ví Dụ

**Ví dụ 1:** `x = 3, y = 5, target = 4`
- `gcd(3, 5) = 1`
- `4 % 1 == 0` (Thỏa mãn)
- `3 + 5 >= 4` (Thỏa mãn)
- Kết quả: **True**

**Ví dụ 2:** `x = 2, y = 6, target = 5`
- `gcd(2, 6) = 2`
- `5 % 2 != 0` (Không thỏa mãn)
- Kết quả: **False**

## Độ Phức Tạp

- **Thời gian:** `O(log(min(x, y)))` - chính là độ phức tạp của thuật toán Euclid tìm GCD.
- **Không gian:** `O(1)`.

## Code (Java)

```java
class Solution {
    public boolean canMeasureWater(int x, int y, int target) {
        if (x + y < target) {
            return false;
        }
        return target % gcd(x, y) == 0;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
```