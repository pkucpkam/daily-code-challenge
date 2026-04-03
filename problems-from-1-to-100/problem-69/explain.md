# Counting Bits

## 1. Mô tả bài toán
Cho một số nguyên `n`. Hãy trả về một mảng `ans` có độ dài `n + 1` sao cho với mỗi `i` (\(0 \le i \le n\)), `ans[i]` là số lượng bit '1' (Hamming weight) trong biểu diễn nhị phân của `i`.
Ví dụ: `n = 2` -> Output: `[0,1,1]` (0 là 0, 1 là 1, 2 là 10).

## 2. Ý tưởng cốt lõi
Có hai cách tiếp cận:
- **Cách 1 (Sử dụng hàm thư viện/Lặp)**: Duyệt từ 0 đến `n`, với mỗi số ta dùng hàm `Integer.bitCount(i)` của Java để đếm bit 1. Độ phức tạp là $O(n \cdot \text{sizeOfInt})$.
- **Cách 2 (Quy hoạch động - Dynamic Programming)**: Đây là cách tối ưu hơn ($O(n)$) bằng cách tận dụng kết quả của những số đã tính trước đó.
    - Với số chẵn: `countBits(i) = countBits(i >> 1)`.
    - Với số lẻ: `countBits(i) = countBits(i >> 1) + 1`.

## 3. Giải thích thuật toán
*(Dựa theo cách giải trong file Solution.java)*:
1. Tạo một mảng `result` có kích thước `n + 1`.
2. Sử dụng vòng lặp duyệt từ `0` đến `n`.
3. Với mỗi chỉ số `i`, sử dụng phương thức `Integer.bitCount(i)` có sẵn trong Java để tính số lượng bit 1.
4. Lưu giá trị vào `result[i]`.
5. Trả về mảng kết quả.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n \cdot k)\) với `k` là số lượng bit (32 bit đối với kiểu int). Có thể coi là \(O(n)\) nhưng hệ số lớn hơn cách dùng DP.
- **Không gian (Space Complexity)**: \(O(n)\) để lưu trữ mảng kết quả trả về.

## 5. Code (Java)
```java
class Solution {
    public int[] countBits(int n) {
        int[] result = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            // Sử dụng hàm built-in của Java để đếm số bit 1
            result[i] = Integer.bitCount(i);
        }
        return result;
    }
}
```
*(Gợi ý tối ưu bằng DP: result[i] = result[i >> 1] + (i & 1);)*
