# Missing Number

## 1. Mô tả bài toán
Cho một mảng `nums` chứa `n` số khác nhau trong phạm vi từ `[0, n]`. Hãy tìm và trả về số duy nhất bị thiếu trong mảng đó.
Ví dụ:
- `nums = [3,0,1]`. Ở đây `n = 3`, dải số là `[0, 1, 2, 3]`. Số thiếu là `2`.

## 2. Ý tưởng cốt lõi
Có hai cách tiếp cận phổ biến cho bài toán này:
- **Cách 1 (Toán học - Tổng Gauss)**: Tính tổng lý thuyết của tất cả các số từ `0` đến `n` bằng công thức \(S = \frac{n(n+1)}{2}\). Sau đó tính tổng thực tế các phần tử trong mảng. Hiệu số giữa tổng lý thuyết và tổng thực tế chính là số bị thiếu.
- **Cách 2 (Bit Manipulation - XOR)**: Tận dụng tính chất \(a \oplus a = 0\) và \(a \oplus 0 = a\). XOR tất cả các chỉ số từ `0` đến `n` và tất cả các số trong mảng, kết quả cuối cùng sẽ là số bị thiếu.

## 3. Giải thích thuật toán
*(Dựa theo giải pháp toán học trong file Solution.java)*:
1. Lấy độ dài mảng `n = nums.length`.
2. Tính tổng lý thuyết của dãy số từ `0` đến `n`: `total = n * (n + 1) / 2`.
3. Duyệt qua mảng `nums` và tính tổng các phần tử thực tế (`sum`).
4. Kết quả là `total - sum`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Duyệt qua mảng đúng một lần để tính tổng.
- **Không gian (Space Complexity)**: \(O(1)\) - Chỉ sử dụng một vài biến số nguyên để lưu trữ các giá trị tổng.

## 5. Code (Java)
```java
class Solution {
    public int missingNumber(int[] nums) {
        int n = nums.length;
        // Tổng lý thuyết của dãy 0..n
        int expectedSum = n * (n + 1) / 2;
        
        // Tổng thực tế của các phần tử trong mảng
        int actualSum = 0;
        for (int num : nums) {
            actualSum += num;
        }
        
        // Số bị thiếu là phần chênh lệch
        return expectedSum - actualSum;
    }
}
```
*(Mẹo: Cách sử dụng XOR có ưu điểm là không lo bị tràn số (overflow) nếu n cực kỳ lớn).*
