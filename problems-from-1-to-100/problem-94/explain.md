# Island Perimeter

## 1. Mô tả bài toán
Cho một lưới `grid` đại diện cho một bản đồ, trong đó `1` là đất và `0` là nước. Các ô được nối với nhau theo chiều ngang hoặc dọc. Bản đồ chỉ chứa đúng một hòn đảo duy nhất.
Hãy tính chu vi của hòn đảo này.
Lưu ý: Mỗi ô vuông có cạnh bằng 1.

## 2. Ý tưởng cốt lõi
- Mỗi ô đất (`1`) ban đầu đóng góp **4 cạnh** vào chu vi.
- Tuy nhiên, nếu hai ô đất nằm cạnh nhau, chúng sẽ chia sẻ một cạnh chung. Cạnh chung này nằm bên trong hòn đảo nên không được tính vào chu vi. Hơn nữa, vì có hai ô cùng mất đi cạnh đó, ta phải trừ đi **2 đơn vị** chu vi cho mỗi cặp ô kề nhau.
- Chiến thuật: Duyệt qua từng ô, nếu gặp đất, cộng 4, sau đó kiểm tra ô phía trên và bên trái, nếu cũng là đất thì trừ bớt đi.

## 3. Giải thích thuật toán
1. Khởi tạo `perimeter = 0`.
2. Duyệt qua từng ô `grid[i][j]` của lưới:
   - Nếu `grid[i][j] == 1`:
     - Cộng 4 vào chu vi: `perimeter += 4`.
     - Kiểm tra hàng phía trên (`i > 0`): Nếu `grid[i - 1][j] == 1`, trừ đi 2: `perimeter -= 2`.
     - Kiểm tra cột bên trái (`j > 0`): Nếu `grid[i][j - 1] == 1`, trừ đi 2: `perimeter -= 2`.
3. Trả về tổng chu vi cuối cùng.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(R \times C)\) - Với $R, C$ là số hàng và số cột của lưới. Ta duyệt qua mỗi ô đúng một lần.
- **Không gian (Space Complexity)**: \(O(1)\) - Không sử dụng thêm cấu trúc dữ liệu nào.

## 5. Code (Java)
```java
class Solution {
    public int islandPerimeter(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int perimeter = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 1) {
                    // Mặc định mỗi ô đất có 4 cạnh
                    perimeter += 4;
                    
                    // Nếu phía trên là đất, trừ đi 2 cạnh tiếp giáp
                    if (r > 0 && grid[r - 1][c] == 1) {
                        perimeter -= 2;
                    }
                    
                    // Nếu bên trái là đất, trừ đi 2 cạnh tiếp giáp
                    if (c > 0 && grid[r][c - 1] == 1) {
                        perimeter -= 2;
                    }
                }
            }
        }
        
        return perimeter;
    }
}
```
*(Ghi chú: Tại sao chỉ kiểm tra phía trên và bên trái? Vì khi duyệt tuần tự, việc kiểm tra này đảm bảo mỗi cặp tiếp giáp chỉ bị trừ một lần duy nhất).*
