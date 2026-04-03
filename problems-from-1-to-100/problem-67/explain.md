# Range Sum Query - Immutable

## 1. Mô tả bài toán
Cho một mảng số nguyên `nums`. Hãy thực hiện tính toán tổng của các phần tử trong một khoảng từ chỉ số `left` đến `right` (bao gồm cả hai đầu).
Yêu cầu: Lớp `NumArray` sẽ được khởi tạo một lần và hàm `sumRange` có thể được gọi nhiều lần. Cần tối ưu hóa tốc độ cho các truy vấn `sumRange`.

## 2. Ý tưởng cốt lõi
- Nếu mỗi lần gọi `sumRange` ta lại dùng vòng lặp để tính tổng các phần tử, độ phức tạp sẽ là \(O(N)\) cho mỗi truy vấn. Với $10^4$ truy vấn, tổng thời gian sẽ rất lớn.
- Kỹ thuật tối ưu ở đây là **Mảng cộng dồn (Prefix Sum)**.
- Ta xây dựng một mảng `prefixSum` sao cho `prefixSum[i]` lưu tổng của các phần tử từ đầu mảng đến vị trí `i-1`.
- Khi đó, tổng dải `[left, right]` đơn giản là: `prefixSum[right + 1] - prefixSum[left]`.

## 3. Giải thích thuật toán
1. **Hàm khởi tạo `NumArray(nums)`**:
   - Tạo mảng `prefixSum` có kích thước `nums.length + 1`.
   - `prefixSum[0] = 0`.
   - Duyệt qua mảng `nums`, tính `prefixSum[i + 1] = prefixSum[i] + nums[i]`.
   - Bước này tốn \(O(N)\) và chỉ thực hiện một lần.
2. **Hàm `sumRange(left, right)`**:
   - Sử dụng công thức hiệu hai tổng cộng dồn để lấy kết quả trong $O(1)$.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**:
    - Khởi tạo: \(O(N)\).
    - Mỗi truy vấn `sumRange`: \(O(1)\).
- **Không gian (Space Complexity)**: \(O(N)\) để lưu trữ mảng `prefixSum`.

## 5. Code (Java)
```java
class NumArray {
    private int[] prefixSum;

    public NumArray(int[] nums) {
        // Khởi tạo mảng cộng dồn với kích thước n + 1
        prefixSum = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
    }
    
    public int sumRange(int left, int right) {
        // Tổng [left...right] = prefixSum[right + 1] - prefixSum[left]
        return prefixSum[right + 1] - prefixSum[left];
    }
}
```
