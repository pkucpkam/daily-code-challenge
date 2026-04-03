# Power of Three

## 1. Mô tả bài toán
Cho một số nguyên `n`. Hãy trả về `true` nếu `n` là một lũy thừa của 3 (\(n = 3^x\)), ngược lại trả về `false`.
Ví dụ:
- `27` (\(27 = 3^3\)): `true`
- `0`, `-1`: `false`

## 2. Ý tưởng cốt lõi
Có nhiều cách để giải bài này:
- **Cách 1 (Vòng lặp/Đệ quy)**: Chia liên tiếp cho 3 cho đến khi không chia được nữa. Nếu kết quả cuối cùng là 1 thì đúng.
- **Cách 2 (Toán học - Logarithm)**: Kiểm tra \(\log_3 n\) có phải là số nguyên không.
- **Cách 3 (Số học tối ưu)**: Tìm số lũy thừa của 3 lớn nhất có thể biểu diễn được trong kiểu dữ liệu `int` (32-bit signed integer). Số đó là \(3^{19} = 1,162,261,467\). Vì 3 là số nguyên tố, nên bất kỳ số nào là lũy thừa của 3 cũng đều là ước của số lớn nhất này.

## 3. Giải thích thuật toán
*(Dựa theo cách giải tối ưu số học trong file Solution.java)*:
1. Xác định số $3^{19} = 1,162,261,467$ là lũy thừa của 3 lớn nhất trong phạm vi kiểu `int`.
2. Đầu tiên kiểm tra `n > 0`.
3. Kiểm tra xem `1162261467 % n == 0` có đúng hay không.
   - Nếu đúng, `n` chắc chắn là một lũy thừa của 3.
   - Nếu sai, `n` không phải là lũy thừa của 3.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(1)\) - Chỉ một phép so sánh và một phép chia lấy dư.
- **Không gian (Space Complexity)**: \(O(1)\) - Không tốn thêm bộ nhớ.

## 5. Code (Java)
```java
class Solution {
    public boolean isPowerOfThree(int n) {
        // Số 1162261467 là 3^19, lũy thừa lớn nhất của 3 trong kiểu int 32-bit.
        // Mọi lũy thừa nhỏ hơn của 3 đều phải là ước của số này.
        return n > 0 && 1162261467 % n == 0;
    }
}
```
*(Ghi chú: Cách giải này rất thông minh và đạt hiệu suất tối đa, tuy nhiên nó chỉ áp dụng được khi số đầu vào là số nguyên tố hoặc lũy thừa của một số nguyên tố).*
