# Pascal's Triangle

## 1. Mô tả bài toán
Cho một số nguyên `numRows`, hãy trả về `numRows` hàng đầu tiên của **Tam giác Pascal**.
Trong Tam giác Pascal, mỗi số là tổng của hai số ngay phía trên nó. Hàng đầu tiên luôn là `[1]`.

## 2. Ý tưởng cốt lõi
- Tam giác Pascal có cấu trúc:
    - Hàng `i` có `i+1` phần tử.
    - Phần tử đầu và phần tử cuối của mỗi hàng luôn là `1`.
    - Các phần tử ở giữa (từ vị trí `1` đến `i-1`) được tính bằng: `Triangle[i][j] = Triangle[i-1][j-1] + Triangle[i-1][j]`.
- Ta có thể xây dựng từng hàng một dựa trên hàng đã hoàn thành trước đó.

## 3. Giải thích thuật toán
1. Khởi tạo một danh sách các danh sách `triangle` để lưu kết quả.
2. Duyệt từ `i = 0` đến `numRows - 1`:
   - Tạo một danh sách `row` đại diện cho hàng hiện tại.
   - Ban đầu có thể lấp đầy hàng bằng các số `1`.
   - Đối với các vị trí ở giữa (`j` từ `1` đến `i-1`):
     - Gán giá trị của `row[j]` bằng tổng của hai phần tử tương ứng ở hàng trên (`triangle.get(i-1)`).
   - Thêm `row` vào `triangle`.
3. Trả về `triangle`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(numRows^2)\) - Vì hàng thứ `i` có `i` phần tử, tổng số lần thực hiện phép cộng là khoảng \(1 + 2 + ... + numRows = \frac{numRows(numRows+1)}{2}\).
- **Không gian (Space Complexity)**: \(O(numRows^2)\) - Để lưu trữ tất cả các hàng của tam giác.

## 5. Code (Java)
```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            // Tạo hàng mới với i + 1 phần tử, mặc định là 1
            List<Integer> row = new ArrayList<>(Collections.nCopies(i + 1, 1));
            
            // Tính toán các phần tử ở giữa (nếu có)
            for (int j = 1; j < i; j++) {
                row.set(j, triangle.get(i - 1).get(j - 1) + triangle.get(i - 1).get(j));
            }
            
            triangle.add(row);
        }
        return triangle;
    }
}
```
