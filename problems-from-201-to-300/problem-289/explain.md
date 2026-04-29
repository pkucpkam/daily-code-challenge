# 313. Super Ugly Number - Lời giải tối ưu

## Tóm tắt bài toán

Cho `n` và mảng `primes`. Cần tìm số siêu xấu thứ `n`, tức là số dương nhỏ nhất trong dãy tăng dần có các thừa số nguyên tố chỉ thuộc `primes`.

## Ý tưởng tối ưu: Dynamic programming với nhiều con trỏ

Ta xây dựng dãy `ugly[]` theo thứ tự tăng dần, trong đó:

- `ugly[0] = 1`
- Với mỗi prime `primes[j]`, ta giữ một con trỏ `indices[j]`
- Giá trị ứng viên tiếp theo từ prime đó là `ugly[indices[j]] * primes[j]`

Mỗi bước:

1. Lấy giá trị nhỏ nhất trong các ứng viên hiện tại.
2. Gán giá trị đó vào dãy `ugly`.
3. Với mọi prime nào tạo ra đúng giá trị nhỏ nhất, tăng con trỏ tương ứng và tính lại ứng viên mới.

Vì nhiều prime có thể tạo ra cùng một giá trị, ta phải cập nhật tất cả con trỏ trùng khớp để tránh thêm phần tử trùng lặp.

## Vì sao đúng

- Dãy `ugly` luôn được xây theo thứ tự tăng dần vì mỗi bước ta chọn ứng viên nhỏ nhất hiện có.
- Mỗi ứng viên đều là tích của một số đã có trong dãy với một prime hợp lệ, nên kết quả sinh ra chỉ chứa thừa số nguyên tố thuộc `primes`.
- Khi một ứng viên được chọn, tăng con trỏ tương ứng sẽ chuyển sang phần tử tiếp theo trong dãy, đảm bảo không bỏ sót số hợp lệ nào và không sinh trùng lặp.
- Sau `n - 1` bước, `ugly[n - 1]` chính là số siêu xấu thứ `n`.

## Độ phức tạp

- Thời gian: `O(n * k)`, với `k = primes.length`.
- Bộ nhớ phụ: `O(n + k)`.

## Java implementation

```java
class Solution {
    public int nthSuperUglyNumber(int n, int[] primes) {
        int k = primes.length;
        int[] ugly = new int[n];
        int[] indices = new int[k];
        long[] nextValues = new long[k];

        ugly[0] = 1;
        for (int i = 0; i < k; i++) {
            nextValues[i] = primes[i];
        }

        for (int i = 1; i < n; i++) {
            long nextUgly = nextValues[0];

            for (int j = 1; j < k; j++) {
                if (nextValues[j] < nextUgly) {
                    nextUgly = nextValues[j];
                }
            }

            ugly[i] = (int) nextUgly;

            for (int j = 0; j < k; j++) {
                if (nextValues[j] == nextUgly) {
                    indices[j]++;
                    nextValues[j] = (long) ugly[indices[j]] * primes[j];
                }
            }
        }

        return ugly[n - 1];
    }
}
```
