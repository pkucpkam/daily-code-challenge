# 📚 Giải thích Chi tiết Bài 695 - Max Area of Island

**Mục tiêu:** Cho một ma trận nhị phân `grid` kích thước $m \times n$. Một hòn đảo được định nghĩa là một nhóm các ô đất (giá trị `1`) kết nối với nhau theo **4 hướng** (trên, dưới, trái, phải). Diện tích của hòn đảo là số lượng ô `1` thuộc hòn đảo đó. Hãy tìm diện tích lớn nhất của một hòn đảo trong ma trận (trả về `0` nếu không có hòn đảo nào).

---

## 🎯 Phân tích bài toán

### 1. Mô hình hóa dưới dạng Đồ thị (Grid as Graph)
- Mỗi ô $(r, c)$ trên lưới ma trận có thể coi là một đỉnh trong đồ thị.
- Các ô đất liền kề nhau theo 4 hướng chính thống (không tính đường chéo) có cạnh nối với nhau.
- Mỗi hòn đảo chính là một **thành phần liên thông (Connected Component)** của các đỉnh có giá trị `1`.
- Nhiệm vụ của ta là tìm kích thước của thành phần liên thông có số đỉnh lớn nhất.

---

### 2. Kỹ thuật "Đánh chìm đảo" (Sink the Island / In-Place Modification)
- Trong các bài toán duyệt ma trận lưới (Grid Traversal), để tránh việc duyệt lặp lại các ô đã xét, ta thường có 2 lựa chọn:
  1. Dùng mảng phụ `boolean[][] visited` kích thước $m \times n$ (tốn thêm $\mathcal{O}(m \cdot n)$ bộ nhớ Heap).
  2. **Biến đổi trực tiếp tại chỗ:** Khi một ô `grid[r][c] == 1` được ghé thăm, ta đổi giá trị của nó thành `0` (`grid[r][c] = 0`). Thao tác này tương đương với việc "đánh chìm" phần đất đó xuống biển, giúp thuật toán không bao giờ duyệt lại ô đó nữa mà không tốn thêm bất kỳ byte bộ nhớ phụ nào!

---

### 3. Ràng buộc & Đánh giá quy mô (Constraints)
- $1 \le m, n \le 50$.
- Tổng số ô trên lưới tối đa là $m \times n \le 50 \times 50 = 2500$ ô.
- Thuật toán DFS hoặc BFS chỉ duyệt mỗi ô đúng một hằng số lần $\implies$ thời gian thực thi là $\mathcal{O}(m \cdot n) \le 2500$ phép tính, chạy tức thì trong khoảng $1-2\text{ ms}$.

---

## 🔍 Các hướng tiếp cận (Approaches Comparison)

| Tiêu chí | Cách 1: DFS Đệ quy (Flood Fill) ⭐ | Cách 2: BFS Hàng đợi (`Queue`) | Cách 3: Disjoint Set Union (DSU) |
| :--- | :--- | :--- | :--- |
| **Cơ chế duyệt** | Đi sâu theo từng nhánh bằng Call Stack | Loang theo từng tầng bằng `ArrayDeque` | Gộp các ô liền kề vào chung tập hợp |
| **Độ phức tạp Thời gian** | $\mathcal{O}(m \cdot n)$ | $\mathcal{O}(m \cdot n)$ | $\mathcal{O}(m \cdot n \cdot \alpha(m \cdot n))$ |
| **Độ phức tạp Bộ nhớ** | $\mathcal{O}(m \cdot n)$ (call stack tối đa 2500) | $\mathcal{O}(\min(m, n))$ (hàng đợi) | $\mathcal{O}(m \cdot n)$ (mảng `parent` + `size`) |
| **Độ dài mã nguồn** | **Cực kỳ ngắn gọn, dễ nhớ (15-20 dòng)** | Dài hơn, cần hàng đợi và mảng hướng | Phức tạp, dư thừa cho bài toán tĩnh |
| **Đánh giá** | **Lựa chọn tối ưu, chuẩn mực nhất cho phỏng vấn** | Thích hợp nếu muốn tránh đệ quy | Phù hợp khi lưới thay đổi động |

---

## 📐 Chứng minh tính đúng đắn (Correctness Proof)

### Bổ đề:
Mỗi thành phần liên thông của các ô `1` được hàm `dfs(r, c)` tính diện tích chính xác và chuyển toàn bộ các ô thuộc thành phần đó về `0`.

**Chứng minh:**
1. **Trường hợp cơ sở:** Khi gặp ô nằm ngoài biên lưới hoặc ô có giá trị `0`, hàm lập tức trả về `0`. Điều này hiển nhiên đúng vì không có diện tích đất nào được đóng góp.
2. **Bước quy nạp:** Khi gặp ô `grid[r][c] == 1`, diện tích khởi điểm là `1`. Sau khi đổi ô này thành `0`, mọi ô `1` lân cận kết nối trực tiếp với nó sẽ được gọi đệ quy.
3. Do đồ thị các ô `1` là hữu hạn ($m \times n \le 2500$) và mỗi ô sau khi thăm đều chuyển thành `0`, không thể xảy ra chu trình vô tận.
4. Khi hàm `dfs` của một ô gốc kết thúc, toàn bộ các ô thuộc cùng hòn đảo đã được chuyển thành `0` và tổng diện tích trả về bằng đúng số lượng đỉnh của thành phần liên thông đó.
5. Vòng lặp chính duyệt qua mọi ô $(r, c)$: những ô thuộc đảo đã duyệt trước đó đã thành `0` nên không bị tính lại, các đảo mới sẽ được tính độc lập. Do đó `maxArea` luôn lưu lại diện tích lớn nhất toàn cục. ∎

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): DFS Đánh Chìm Đảo Tại Chỗ

### 💡 Thuật toán chi tiết

1. Khởi tạo biến `maxArea = 0`.
2. Dùng 2 vòng lặp lồng nhau duyệt qua mọi ô $(r, c)$ từ $(0, 0)$ đến $(m - 1, n - 1)$:
   - Nếu `grid[r][c] == 1`:
     - Gọi `int area = dfs(grid, r, c, m, n)`.
     - Cập nhật kỷ lục: `maxArea = Math.max(maxArea, area)`.
3. Trong hàm `dfs(grid, r, c, m, n)`:
   - Điều kiện dừng: nếu $r < 0$ hoặc $r \ge m$ hoặc $c < 0$ hoặc $c \ge n$ hoặc `grid[r][c] != 1`, trả về `0`.
   - Đánh dấu đã thăm: `grid[r][c] = 0`.
   - Diện tích khởi điểm `area = 1`.
   - Gọi đệ quy và cộng dồn diện tích từ 4 hướng kề cạnh:
     - `area += dfs(grid, r + 1, c, m, n)` (dưới)
     - `area += dfs(grid, r - 1, c, m, n)` (trên)
     - `area += dfs(grid, r, c + 1, m, n)` (phải)
     - `area += dfs(grid, r, c - 1, m, n)` (trái)
   - Trả về `area`.
4. Trả về `maxArea`.

---

### 📊 Minh họa trực quan (Visual Walkthrough)

#### Giả sử xét một cụm đảo nhỏ:
```text
[1, 1, 0]
[0, 1, 0]
[0, 0, 1]
```

**Quá trình duyệt:**
1. Tại ô `(0, 0)`: `grid[0][0] == 1` $\implies$ gọi `dfs(0, 0)`:
   - `grid[0][0] = 0`, `area = 1`.
   - Loang sang phải `(0, 1)`: `grid[0][1] = 0`, `area += 1` (tổng = 2).
     - Từ `(0, 1)` loang xuống `(1, 1)`: `grid[1][1] = 0`, `area += 1` (tổng = 3).
     - Từ `(1, 1)` các hướng xung quanh đều là `0` hoặc ngoài biên $\implies$ trả về `1`.
     - Trở về `(0, 1)` $\implies$ trả về `2`.
   - Trở về `(0, 0)` $\implies$ tổng diện tích đảo 1 là **`3`**.
   - Cập nhật `maxArea = max(0, 3) = 3`.
2. Trạng thái ma trận lúc này:
   ```text
   [0, 0, 0]
   [0, 0, 0]
   [0, 0, 1]
   ```
3. Vòng lặp tiếp tục chạy đến `(2, 2)`: `grid[2][2] == 1` $\implies$ gọi `dfs(2, 2)`:
   - `grid[2][2] = 0`, diện tích = `1`.
   - `maxArea = max(3, 1) = 3`.
4. Kết quả cuối cùng: **`3`**.

---

## 💻 Cài đặt Thuật toán Chi tiết (Java Implementation)

### Cách 1: DFS Đệ Quy Tại Chỗ ⭐ (Khuyên dùng)

```java
class Solution {
    public int maxAreaOfIsland(int[][] grid) {
        int maxArea = 0;
        int m = grid.length;
        int n = grid[0].length;

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1) {
                    maxArea = Math.max(maxArea, dfs(grid, r, c, m, n));
                }
            }
        }

        return maxArea;
    }

    private int dfs(int[][] grid, int r, int c, int m, int n) {
        // Kiểm tra biên ma trận và điều kiện dừng (nước hoặc ô đã thăm)
        if (r < 0 || r >= m || c < 0 || c >= n || grid[r][c] != 1) {
            return 0;
        }

        // Đánh dấu ô đã duyệt bằng cách chuyển 1 thành 0 (kỹ thuật "đánh chìm đảo")
        grid[r][c] = 0;
        int area = 1;

        // Loang sang 4 ô kề cạnh: dưới, trên, phải, trái
        area += dfs(grid, r + 1, c, m, n);
        area += dfs(grid, r - 1, c, m, n);
        area += dfs(grid, r, c + 1, m, n);
        area += dfs(grid, r, c - 1, m, n);

        return area;
    }
}
```

---

### Cách 2 (Tham khảo): BFS Không Sửa Mảng Gốc (Dùng Mảng `visited`)

```java
import java.util.ArrayDeque;
import java.util.Queue;

class SolutionBFS {
    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int maxAreaOfIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        int maxArea = 0;

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1 && !visited[r][c]) {
                    maxArea = Math.max(maxArea, bfs(grid, visited, r, c, m, n));
                }
            }
        }

        return maxArea;
    }

    private int bfs(int[][] grid, boolean[][] visited, int startR, int startC, int m, int n) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{startR, startC});
        visited[startR][startC] = true;
        int area = 0;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            area++;

            for (int[] dir : DIRS) {
                int nr = curr[0] + dir[0];
                int nc = curr[1] + dir[1];

                if (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] == 1 && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        return area;
    }
}
```

---

## 🔍 Giải thích từng dòng lệnh chi tiết (Line-by-Line Walkthrough)

1. **`if (r < 0 || r >= m || c < 0 || c >= n || grid[r][c] != 1) return 0;`**:
   - Điều kiện phòng thủ gộp: vừa kiểm tra ô không bị văng ra khỏi biên ma trận, vừa kiểm tra ô đó có phải là đất chưa được duyệt hay không. Nếu không thỏa mãn, trả về ngay diện tích `0`.
2. **`grid[r][c] = 0;`**:
   - Ngay khi bước vào ô đất hợp lệ, chuyển giá trị thành `0` để các bước đệ quy tiếp theo không bao giờ quay ngược lại ô này.
3. **`area += dfs(grid, r + 1, c, m, n); ...`**:
   - Lần lượt gọi đệ quy tới 4 ô láng giềng và cộng dồn diện tích trả về vào biến `area`.
4. **`maxArea = Math.max(maxArea, dfs(grid, r, c, m, n));`**:
   - Sau khi kết thúc khám phá toàn bộ một hòn đảo, so sánh diện tích vừa tìm được với kỷ lục hiện tại.

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### Độ phức tạp Thời gian (Time Complexity)
- **$\mathcal{O}(M \times N)$**:
  - Với $M$ là số hàng và $N$ là số cột của lưới ma trận ($M, N \le 50$).
  - Mỗi ô trong lưới được duyệt qua đúng 1 lần từ 2 vòng for ngoài cùng.
  - Trong quá trình DFS, mỗi ô đất chỉ được thăm đúng một lần duy nhất vì ngay khi thăm nó đã được gán thành `0`.
  - Tổng số phép toán tỷ lệ tuyến tính với tổng số ô trong ma trận: $\mathcal{O}(M \times N) \le 2500$.

### Độ phức tạp Không gian (Space Complexity)
- **$\mathcal{O}(M \times N)$**:
  - Trường hợp xấu nhất (toàn bộ lưới đều là đất `1`), ngăn xếp đệ quy (call stack) có thể đạt độ sâu bằng tổng số ô là $M \times N = 2500$.
  - Mức độ sâu $2500$ nằm hoàn toàn trong giới hạn an toàn của Call Stack trong Java (mặc định cho phép $\approx 10,000$ tầng đệ quy), không xảy ra `StackOverflowError`.
  - Không sử dụng thêm bất kỳ bộ nhớ phụ nào khác nhờ kỹ thuật sửa trực tiếp ma trận.

---

## 💡 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways & Interview Tips)

1. **Hỏi người phỏng vấn về quyền chỉnh sửa mảng gốc:**
   - Trong phỏng vấn, trước khi viết `grid[r][c] = 0`, hãy chủ động hỏi: *"Em có được phép chỉnh sửa trực tiếp dữ liệu đầu vào của `grid` không, hay cần giữ nguyên mảng gốc?"*
   - Nếu được phép: dùng kỹ thuật "đánh chìm đảo" tại chỗ để tiết kiệm bộ nhớ.
   - Nếu không được phép: sử dụng mảng phụ `boolean[][] visited` (hoặc khôi phục lại mảng sau khi duyệt xong).
2. **Họ các bài toán tương tự về Đảo trên LeetCode:**
   - **LeetCode 200 - Number of Islands:** Đếm số lượng hòn đảo (Medium).
   - **LeetCode 695 - Max Area of Island:** Tìm diện tích hòn đảo lớn nhất (Medium).
   - **LeetCode 463 - Island Perimeter:** Tính chu vi hòn đảo (Easy).
   - **LeetCode 827 - Making A Large Island:** Tìm diện tích lớn nhất sau khi đổi tối đa một ô $0 \to 1$ (Hard).
