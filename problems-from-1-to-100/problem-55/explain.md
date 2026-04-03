# Power of Two

## 1. Mô tả bài toán
Cho một số nguyên `n`. Hãy trả về `true` nếu `n` là một lũy thừa của 2 (\(n = 2^x\) với `x` là một số nguyên), ngược lại trả về `false`.
Ví dụ:
- `1` (vì \(2^0 = 1\)): `true`
- `16` (vì \(2^4 = 16\)): `true`
- `3`: `false`

## 2. Ý tưởng cốt lõi
- Nếu một số là lũy thừa của 2, trong biểu diễn nhị phân của nó chỉ có **duy nhất một bit 1** (ví dụ: `2 = 10`, `4 = 100`, `8 = 1000`).
- Một kỹ thuật bit manipulation rất hay để kiểm tra điều này là sử dụng biểu thức: `n & (n - 1)`.
- Nếu `n` chỉ có một bit 1, thì `n - 1` sẽ có tất cả các bit phía sau bit 1 đó là 1 và chính bit 1 đó trở thành 0. 
- Do đó, `n & (n - 1)` sẽ bằng 0 nếu `n` là lũy thừa của 2.

## 3. Giải thích thuật toán
1. Đầu tiên, kiểm tra `n <= 0`. Lũy thừa của 2 (`2^x`) luôn là số dương, vì vậy nếu `n <= 0` trả về `false`.
2. Sử dụng phép toán bit: `(n & (n - 1)) == 0`.
   - Nếu kết quả là 0, nghĩa là `n` có duy nhất 1 bit 1 -> Trả về `true`.
   - Các trường hợp khác trả về `false`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(1)\) - Chỉ thực hiện một phép toán bit duy nhất.
- **Không gian (Space Complexity)**: \(O(1)\) - Không sử dụng thêm bộ nhớ phụ.

## 5. Code (Java)
```java
class Solution {
    public boolean isPowerOfTwo(int n) {
        // Lũy thừa của 2 phải là số dương
        if (n <= 0) {
            return false;
        }
        // Một số là lũy thừa của 2 nếu n & (n - 1) == 0
        return (n & (n - 1)) == 0;
    }
}
```
