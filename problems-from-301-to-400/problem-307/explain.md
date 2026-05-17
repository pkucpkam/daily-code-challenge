# 372. Super Pow

**Độ khó:** Medium

## Mô Tả Bài Toán

Nhiệm vụ của bạn là tính toán `a^b mod 1337`, trong đó `a` là một số nguyên dương và `b` là một số nguyên cực kỳ lớn được biểu diễn dưới dạng một mảng các chữ số.

## Ý Tưởng Giải

Do `b` là một số nguyên có độ dài rất lớn (lên tới 2000 chữ số), ta không thể chuyển đổi `b` trực tiếp thành kiểu số nguyên nguyên bản (`int` hoặc `long`) trong Java để tính lũy thừa thông thường vì chắc chắn sẽ bị tràn số. Thay vào đó, chúng ta cần tận dụng các tính chất toán học của phép chia lấy dư (Modular Arithmetic).

### 1. Tính Chất Modulo Toán Học
* `(x * y) % m = ((x % m) * (y % m)) % m`
* `(x^y) % m = ((x % m)^y) % m`

### 2. Phân Tích Mảng Lũy Thừa (Duyệt Từ Trái Qua Phải)
Giả sử mảng số nguyên `b` biểu diễn cho một số nguyên cực kỳ lớn. 
Khi duyệt mảng `b` từ trái qua phải, chúng ta có thể xây dựng kết quả lũy thừa lũy kế bằng các phép nhân và lũy thừa đơn giản hơn.

Ví dụ: với `b = [1, 5, 6, 4]` (tương đương giá trị `1564`), ta phân tích:
* Với chữ số đầu tiên `1`: ta có `a^1`
* Với chữ số tiếp theo `5`: ta có `a^15 = (a^1)^10 * a^5`
* Với chữ số tiếp theo `6`: ta có `a^156 = (a^15)^10 * a^6`
* Với chữ số tiếp theo `4`: ta có `a^1564 = (a^156)^10 * a^4`

**Công thức tổng quát:** 
Nếu kết quả lũy kế sau khi xử lý một số chữ số đầu là `res`, thì khi đi đến chữ số tiếp theo là `digit`, giá trị kết quả mới sẽ là:
```
res_mới = (res^10 * a^digit) % 1337
```
Bằng cách này, chúng ta chỉ cần thực hiện tính lũy thừa với số mũ cố định là `10` và chữ số hiện tại `digit` (từ `0` đến `9`).

### 3. Thuật Toán Lũy Thừa Nhị Phân (Binary Exponentiation)
Để tính toán `base^exp % 1337` một cách tối ưu nhất, chúng ta sử dụng giải thuật **Lũy thừa nhị phân**. Giải thuật này giúp giảm thời gian tính toán từ `O(exp)` xuống `O(log exp)`.

---

## Độ Phức Tạp

* **Thời gian:** `O(N)` với `N` là số lượng phần tử của mảng `b` (tối đa 2000 phần tử). Ở mỗi chữ số, ta chỉ mất tối đa `O(log 10)` phép toán nhân nhờ phương pháp lũy thừa nhị phân, là thời gian hằng số `O(1)`.
* **Không gian:** `O(1)` vì chúng ta chỉ sử dụng một vài biến phụ trợ để lưu trữ trạng thái kết quả.

---

## Code (Java)

```java
class Solution {
    private static final int MOD = 1337;

    public int superPow(int a, int[] b) {
        a %= MOD;
        int res = 1;
        for (int digit : b) {
            res = (pow(res, 10) * pow(a, digit)) % MOD;
        }
        return res;
    }

    // Tính (base^exp) % MOD sử dụng lũy thừa nhị phân
    private int pow(int base, int exp) {
        int res = 1;
        base %= MOD;
        while (exp > 0) {
            if ((exp & 1) != 0) {
                res = (res * base) % MOD;
            }
            base = (base * base) % MOD;
            exp >>= 1;
        }
        return res;
    }
}
```
