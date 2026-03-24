# 204. Count Primes - Best Solution

## Ý tưởng tối ưu

Lời giải tốt nhất cho bài này là Sàng Eratosthenes.

Ta tạo một mảng đánh dấu số nguyên tố từ 0 đến n - 1.

- Ban đầu, coi tất cả số từ 2 trở đi là nguyên tố.
- Duyệt từng số i từ 2 đến khi i * i < n.
- Nếu i còn là nguyên tố, ta loại tất cả bội của i bắt đầu từ i * i.

Lý do bắt đầu từ i * i:

- Các bội nhỏ hơn i * i như i * 2, i * 3, ... đã bị loại bởi các số nhỏ hơn i trước đó.

## Vì sao đây là best solution

- Nhanh hơn rất nhiều so với kiểm tra từng số rồi chia thử.
- Đáp ứng tốt ràng buộc n lên đến 5 * 10^6.
- Cài đặt gọn và chuẩn cho bài đếm số nguyên tố.

## Thuật toán

1. Nếu n <= 2 thì trả về 0.
2. Tạo mảng boolean isPrime có kích thước n.
3. Gán isPrime[i] = true với i từ 2 đến n - 1.
4. Với i từ 2 đến khi i * i < n:
- Nếu isPrime[i] là true, duyệt j từ i * i đến n - 1, bước nhảy i, rồi gán isPrime[j] = false.
5. Đếm số lượng chỉ số i có isPrime[i] = true trong đoạn từ 2 đến n - 1.
6. Trả về kết quả đếm được.

## Code (Java)

```java
class Solution {
    public int countPrimes(int n) {
        if (n <= 2) {
            return 0;
        }

        boolean[] isPrime = new boolean[n];
        for (int i = 2; i < n; i++) {
            isPrime[i] = true;
        }

        for (int i = 2; i * i < n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j < n; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime[i]) {
                count++;
            }
        }

        return count;
    }
}
```

## Độ phức tạp

- Time: O(n log log n)
- Space: O(n)

## Ví dụ nhanh

Với n = 10:

- Ban đầu đánh dấu 2..9 là nguyên tố.
- Với i = 2, loại 4, 6, 8.
- Với i = 3, loại 9.
- Kết quả còn lại: 2, 3, 5, 7.

Số lượng là 4.



