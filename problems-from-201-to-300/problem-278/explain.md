# 279. Perfect Squares - Best Solution

## Tóm tắt bài toán

Cho số nguyên dương `n`, hãy tìm số lượng nhỏ nhất các số chính phương có tổng bằng `n`.

## Ý tưởng chính

Ta dùng định lý số học để xử lý trong `O(sqrt(n))`:

- Nếu `n` là số chính phương, đáp án là `1`.
- Nếu sau khi chia hết cho `4`, phần còn lại có dạng `8k + 7`, đáp án là `4`.
- Nếu tồn tại `a` sao cho `n - a^2` là số chính phương, đáp án là `2`.
- Các trường hợp còn lại là `3`.

Hai định lý đứng sau lời giải này là:

- Định lý Lagrange: mọi số nguyên dương đều biểu diễn được dưới dạng tổng của không quá 4 số chính phương.
- Định lý Legendre: một số cần đúng 4 số chính phương khi và chỉ khi nó có dạng `4^a(8b + 7)`.

## Vì sao đúng

- Kiểm tra số chính phương giúp nhận ra ngay trường hợp tối ưu nhất là `1`.
- Việc loại các thừa số `4` không làm thay đổi bản chất của biểu diễn bằng tổng các số chính phương, nhưng giúp nhận diện chính xác dạng bị buộc phải dùng `4` số.
- Nếu không thuộc dạng `4^a(8b + 7)`, ta chỉ cần thử trường hợp `2` bằng cách quét mọi `a^2 <= n`.
- Theo định lý Lagrange, luôn tồn tại lời giải với không quá `4` số, nên sau khi loại các trường hợp `1`, `2`, và `4`, đáp án còn lại chắc chắn là `3`.

## Độ phức tạp

- Time: `O(sqrt(n))`
- Space: `O(1)`

## Java Code

```java
class Solution {
    public int numSquares(int n) {
        if (isPerfectSquare(n)) {
            return 1;
        }

        while (n % 4 == 0) {
            n /= 4;
        }

        if (n % 8 == 7) {
            return 4;
        }

        for (int i = 1; i * i <= n; i++) {
            if (isPerfectSquare(n - i * i)) {
                return 2;
            }
        }

        return 3;
    }

    private boolean isPerfectSquare(int num) {
        int root = (int) Math.sqrt(num);
        return root * root == num;
    }
}
```
