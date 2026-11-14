# 📚 Giải thích Chi tiết Bài 684 - Redundant Connection

**Mục tiêu:** Cho một đồ thị vô hướng gồm $n$ đỉnh và $n$ cạnh, được tạo thành từ một cây có $n$ đỉnh sau khi thêm đúng $1$ cạnh mới. Hãy tìm và trả về cạnh cần loại bỏ để đồ thị trở lại thành một cây hợp lệ. Nếu có nhiều đáp án thỏa mãn, trả về cạnh xuất hiện **sau cùng** trong danh sách đầu vào.

---

## 🎯 Phân tích bài toán

### 1. Đặc tính cốt lõi của một Cây (Tree Properties)
Theo lý thuyết đồ thị:
- Một cây (Tree) có $n$ đỉnh luôn có đúng **$n - 1$ cạnh**, liên thông và **không chứa chu trình (acyclic)**.
- Giữa hai đỉnh bất kỳ trong cây luôn tồn tại **duy nhất một đường đi đơn giản**.
- Đề bài cho đồ thị có $n$ đỉnh nhưng lại có **$n$ cạnh**. Nghĩa là đồ thị có thừa đúng $1$ cạnh, tạo ra **chính xác một chu trình đơn (simple cycle)**.

---

### 2. Ý nghĩa của việc "Loại bỏ cạnh xuất hiện sau cùng"
- Bất kỳ cạnh nào nằm trên chu trình đó khi bị loại bỏ đều sẽ phá vỡ chu trình và biến đồ thị trở lại thành một cây liên thông $n$ đỉnh.
- Đề bài yêu cầu: *Nếu có nhiều đáp án, trả về cạnh xuất hiện sau cùng trong mảng `edges`*.
- Giả sử ta bắt đầu với một đồ thị gồm $n$ đỉnh và $0$ cạnh, sau đó thêm lần lượt từng cạnh trong danh sách `edges`:
  - Chừng nào hai đỉnh $u$ và $v$ của cạnh $[u, v]$ thuộc hai thành phần liên thông khác nhau, việc thêm cạnh $[u, v]$ chỉ đơn giản là nối hai cây lại với nhau (không tạo ra chu trình).
  - Khi gặp cạnh $[u, v]$ đầu tiên mà $u$ và $v$ **đã cùng thuộc một thành phần liên thông** (đã có đường đi giữa chúng từ trước), việc nối thêm $[u, v]$ sẽ ngay lập tức **đóng lại chu trình**.
  - Vì đồ thị chỉ có đúng $n$ cạnh và đúng 1 chu trình, cạnh $[u, v]$ đóng lại chu trình này chính là cạnh xuất hiện sau cùng trong số các cạnh tạo nên chu trình! Do đó, $[u, v]$ chính là đáp án cần tìm.

---

### 3. Cấu trúc dữ liệu tối ưu: Disjoint Set Union (DSU / Union-Find)
- Ta cần liên tục thực hiện 2 thao tác:
  1. `find(x)`: Tìm gốc đại diện của tập hợp chứa đỉnh $x$ (kiểm tra liên thông).
  2. `union(x, y)`: Gộp hai tập hợp chứa $x$ và $y$ lại với nhau.
- Đây là bài toán kinh điển hoàn hảo nhất cho cấu trúc dữ liệu **Tập hợp các phần tử rời rạc (DSU)**.
- Khi kết hợp **Nén đường đi (Path Compression)** và **Gộp theo hạng (Union by Rank)**, mỗi thao tác `find` và `union` đạt độ phức tạp gần như hằng số $\mathcal{O}(\alpha(N))$, trong đó $\alpha$ là hàm nghịch đảo Ackermann ($\alpha(N) \le 4$ với mọi $N$ thực tế).

---

## 🔍 Các hướng tiếp cận (Approaches Comparison)

| Tiêu chí | Cách 1: DFS / BFS kiểm tra chu trình mỗi bước | Cách 2: Disjoint Set Union (DSU / Union-Find) ⭐ |
| :--- | :--- | :--- |
| **Ý tưởng** | Với mỗi cạnh $[u, v]$, chạy DFS từ $u$ xem đã đến được $v$ chưa. Nếu có đường đi thì đây là cạnh thừa; nếu chưa thì thêm cạnh vào đồ thị | Dùng DSU, kiểm tra `find(u) == find(v)`. Nếu bằng nhau thì phát hiện chu trình ngay lập tức |
| **Độ phức tạp Thời gian** | $\mathcal{O}(N^2)$ (mỗi cạnh chạy DFS mất $\mathcal{O}(N)$) | $\mathcal{O}(N \cdot \alpha(N)) \approx \mathcal{O}(N)$ (gần như tuyến tính) |
| **Độ phức tạp Bộ nhớ** | $\mathcal{O}(N)$ (danh sách kề + ngăn xếp đệ quy) | $\mathcal{O}(N)$ (mảng `parent` và `rank`) |
| **Đánh giá** | Chậm hơn khi $N$ lớn | **Chuẩn mực tuyệt đối, cực kỳ ngắn gọn và tối ưu** |

---

## 📐 Chứng minh tính đúng đắn (Correctness Proof)

### Định lý
Cạnh đầu tiên $[u, v]$ trong danh sách thỏa mãn `find(u) == find(v)` chính là cạnh thừa xuất hiện sau cùng tạo nên chu trình.

**Chứng minh:**
1. Giả sử trước khi xét cạnh $e = [u, v]$, ta đã xử lý tập các cạnh $E' \subset edges$. Do chưa có chu trình nào xuất hiện trước đó, đồ thị $G' = (V, E')$ là một rừng (forest - tập hợp các cây rời rạc).
2. Khi xét cạnh $e = [u, v]$, nếu $u$ và $v$ đã có cùng gốc đại diện (`find(u) == find(v)`), điều này có nghĩa là đã tồn tại một đường đi đơn giữa $u$ và $v$ trong $G'$.
3. Cạnh $e$ kết hợp với đường đi này tạo thành một chu trình đơn $C$.
4. Do đề bài cho biết đồ thị ban đầu chỉ có $n$ đỉnh và $n$ cạnh (thừa đúng $1$ cạnh so với cây), nên toàn bộ đồ thị chỉ tồn tại **duy nhất một chu trình đơn $C$**.
5. Mọi cạnh khác thuộc chu trình $C$ đều đã được xét và thêm vào trước cạnh $e$. Vì vậy, $e$ chính là cạnh xuất hiện sau cùng trong chu trình $C$ theo thứ tự xuất hiện trong mảng đầu vào. Loại bỏ $e$ sẽ phá vỡ chu trình $C$ mà vẫn đảm bảo tính liên thông của $n$ đỉnh. ∎

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): DSU với Path Compression & Union by Rank

### 💡 Thuật toán chi tiết

1. Khởi tạo hai mảng kích thước $n + 1$ (do các đỉnh đánh số từ $1$ đến $n$):
   - `parent[i] = i`: Ban đầu mỗi đỉnh tự là gốc của chính nó.
   - `rank[i] = 1`: Chiều cao xấp xỉ của cây khởi điểm là 1.
2. Duyệt qua từng cạnh `[u, v]` trong danh sách `edges`:
   - Tìm gốc: `rootU = find(u)`, `rootV = find(v)`.
   - Nếu `rootU == rootV`: Hai đỉnh đã cùng một thành phần liên thông $\implies$ Cạnh này đóng lại chu trình, lập tức trả về `[u, v]`.
   - Nếu `rootU != rootV`: Gộp hai gốc lại bằng quy tắc **Union by Rank** (gốc có `rank` nhỏ hơn gắn vào gốc có `rank` lớn hơn).

---

### 📊 Minh họa trực quan (Visual Walkthrough)

#### Ví dụ 2: `edges = [[1,2], [2,3], [3,4], [1,4], [1,5]]`

```text
Khởi tạo: Mỗi đỉnh là một tập hợp độc lập {1}, {2}, {3}, {4}, {5}

1. Xét [1, 2]:
   find(1) = 1, find(2) = 2 khác nhau -> Gộp: {1, 2}

2. Xét [2, 3]:
   find(2) = 1, find(3) = 3 khác nhau -> Gộp: {1, 2, 3}

3. Xét [3, 4]:
   find(3) = 1, find(4) = 4 khác nhau -> Gộp: {1, 2, 3, 4}

4. Xét [1, 4]:
   find(1) = 1, find(4) = 1 GIỐNG NHAU!
   -> Đỉnh 1 và 4 đã có đường đi nối từ trước: 1 - 2 - 3 - 4.
   -> Cạnh [1, 4] tạo thành chu trình: 1 - 2 - 3 - 4 - 1.
   -> Trả về ngay: [1, 4]!
```

---

## 💻 Cài đặt Thuật toán Chi tiết (Java Implementation)

```java
class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        int[] parent = new int[n + 1];
        int[] rank = new int[n + 1];

        // Khởi tạo mỗi đỉnh là một tập hợp độc lập
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }

        // Duyệt qua từng cạnh để xây dựng đồ thị
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            // Nếu u và v đã liên thông từ trước, cạnh này tạo ra chu trình
            if (!union(parent, rank, u, v)) {
                return edge;
            }
        }

        return new int[0];
    }

    // Hàm tìm gốc kết hợp Nén đường đi (Path Compression)
    private int find(int[] parent, int i) {
        if (parent[i] != i) {
            parent[i] = find(parent, parent[i]);
        }
        return parent[i];
    }

    // Hàm gộp hai tập hợp kết hợp Gộp theo hạng (Union by Rank)
    private boolean union(int[] parent, int[] rank, int u, int v) {
        int rootU = find(parent, u);
        int rootV = find(parent, v);

        // Đã cùng gốc đại diện -> phát hiện chu trình
        if (rootU == rootV) {
            return false;
        }

        // Gắn cây có hạng nhỏ hơn vào cây có hạng lớn hơn
        if (rank[rootU] < rank[rootV]) {
            parent[rootU] = rootV;
        } else if (rank[rootU] > rank[rootV]) {
            parent[rootV] = rootU;
        } else {
            parent[rootV] = rootU;
            rank[rootU]++;
        }

        return true;
    }
}
```

---

## 🔍 Giải thích từng dòng lệnh chi tiết (Line-by-Line Walkthrough)

1. **`int[] parent = new int[n + 1]; int[] rank = new int[n + 1];`**:
   - Mảng `parent` lưu cha của mỗi nút. Khởi tạo `parent[i] = i`.
   - Mảng `rank` lưu chiều cao xấp xỉ của cây đại diện để cân bằng cây khi gộp.
2. **`parent[i] = find(parent, parent[i]);` (Path Compression)**:
   - Khi tìm gốc của `i`, ta đồng thời trỏ trực tiếp `parent[i]` về gốc cao nhất. Các lần gọi `find(i)` tiếp theo chỉ mất thời gian $\mathcal{O}(1)$.
3. **`if (rootU == rootV) return false;`**:
   - Nếu hai đỉnh `u` và `v` đã có chung gốc, việc thêm cạnh `[u, v]` sẽ đóng kín một chu trình, hàm `union` trả về `false`.
4. **`if (!union(parent, rank, u, v)) return edge;`**:
   - Vì ta duyệt các cạnh theo thứ tự ban đầu, cạnh đầu tiên khiến `union` trả về `false` chính là cạnh thừa xuất hiện sau cùng tạo nên chu trình. Ta trả về ngay lập tức.

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### Độ phức tạp Thời gian (Time Complexity)
- **$\mathcal{O}(N \cdot \alpha(N))$**:
  - Với $N$ là số đỉnh và cũng là số cạnh ($N \le 1000$).
  - Thuật toán duyệt qua tối đa $N$ cạnh. Với mỗi cạnh, thao tác `find` và `union` có độ phức tạp là $\mathcal{O}(\alpha(N))$, trong đó $\alpha(N) \le 4$ với mọi giá trị $N \le 1000$.
  - Do đó thời gian chạy xấp xỉ tuyến tính $\mathcal{O}(N)$, chỉ mất $\approx 1\text{ ms}$ trên LeetCode.

### Độ phức tạp Không gian (Space Complexity)
- **$\mathcal{O}(N)$**:
  - Chỉ cần hai mảng `parent` và `rank` có kích thước $N + 1$, tiêu tốn bộ nhớ rất nhỏ.

---

## 💡 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways & Interview Tips)

1. **Dấu hiệu nhận biết bài toán DSU:**
   - Đồ thị vô hướng, yêu cầu kiểm tra tính liên thông động (dynamic connectivity), phát hiện chu trình (cycle detection), hoặc bài toán cây khung nhỏ nhất (Kruskal's Algorithm).
2. **Luôn kết hợp Path Compression và Union by Rank:**
   - Nếu chỉ dùng DSU thông thường, cây có thể bị thoái hóa thành một danh sách liên kết dẫn đến độ phức tạp $\mathcal{O}(N)$ cho mỗi phép `find`. Kết hợp hai kỹ thuật trên giúp đảm bảo cấu trúc cây luôn phẳng, đạt hiệu năng tối ưu tuyệt đối.
3. **Câu hỏi mở rộng (Follow-up): LeetCode 685 - Redundant Connection II:**
   - Trong bài 685, đồ thị là **có hướng (directed graph)**. Bài toán trở nên phức tạp hơn nhiều vì có 3 khả năng:
     1. Có 1 đỉnh có 2 cha (in-degree = 2), nhưng không có chu trình.
     2. Không có đỉnh nào có 2 cha, nhưng có 1 chu trình có hướng.
     3. Có 1 đỉnh có 2 cha VÀ đồng thời có 1 chu trình có hướng.
   - Khi phỏng vấn, nếu người phỏng vấn nhắc đến đồ thị có hướng, hãy chỉ ra ngay sự khác biệt quan trọng này!
