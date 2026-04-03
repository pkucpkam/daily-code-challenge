# Pascal's Triangle II

## 1. Mô tả bài toán
Cho một số nguyên `rowIndex` (0-indexed), hãy trả về hàng thứ `rowIndex` của Tam giác Pascal.
Yêu cầu tối ưu: Thử tối ưu hóa thuật toán để chỉ sử dụng \(O(k)\) không gian bộ nhớ phụ, với `k` là `rowIndex`.

## 2. Ý tưởng cốt lõi
- Ta có thể sử dụng công thức tổ hợp để tính trực tiếp từng phần tử của hàng thứ `n` trong Tam giác Pascal.
- Phần tử thứ `j` trong hàng `i` có giá trị là \(C(i, j) = \frac{i!}{j!(i-j)!}\).
- Để tránh tính giai thừa (dễ gây tràn số), ta có thể sử dụng tính chất: \(C(n, k) = C(n, k-1) \times \frac{n-k+1}{k}\).
- Điều này cho phép ta tính phần tử tiếp theo dựa trên phần tử trước đó trong cùng một hàng.

## 3. Giải thích thuật toán
1. Khởi tạo một danh sách `row` gồm `rowIndex + 1` phần tử, tất cả ban đầu là `1`.
2. Duyệt từ `j = 1` đến `rowIndex - 1`:
   - Tính toán giá trị tại vị trí `j` bằng công thức: `row[j] = row[j-1] * (rowIndex - j + 1) / j`.
   - Lưu ý: Cần ép kiểu sang `long` trong quá trình nhân để tránh tràn số số nguyên (`int`), sau đó mới ép kiểu ngược lại về `int`.
3. Trả về danh sách `row`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(rowIndex)\) - Ta chỉ cần một vòng lặp duy nhất để tính toán các phần tử trong hàng.
- **Không gian (Space Complexity)**: \(O(rowIndex)\) - Chỉ sử dụng một danh sách để chứa kết quả của hàng đó.

## 5. Code (Java)
```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Solution {
    public List<Integer> getRow(int rowIndex) {
        List<Integer> row = new ArrayList<>(Collections.nCopies(rowIndex + 1, 1));
        
        // Sử dụng công thức tổ hợp: C(n, k) = C(n, k-1) * (n-k+1) / k
        for (int j = 1; j <= rowIndex; j++) {
             // Để tránh lỗi j=0 và j=rowIndex đã là 1, ta có thể chạy đến rowIndex
             // Lưu ý dùng long để tránh tràn số khi nhân
             if (j < rowIndex) {
                row.set(j, (int) ((long) row.get(j - 1) * (rowIndex - j + 1) / j));
             }
        }
        return row;
    }
}
```
*(Lưu ý: Code trong file Solution.java của bạn có một chút khác biệt ở điều kiện dừng j < rowIndex, nhưng logic chung là sử dụng công thức tổ hợp).*
