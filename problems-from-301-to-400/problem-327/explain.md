# 399. Evaluate Division - Giải thích thuật toán

Bài toán này có thể được giải quyết bằng 2 phương pháp chính: **Duyệt Đồ Thị (DFS/BFS)** và **Union-Find (Cấu trúc dữ liệu Tập hợp rời rạc)**. 

---

## Cách 1: Duyệt đồ thị (Graph Traversal - DFS/BFS)

Cách đơn giản và trực quan nhất là biểu diễn bài toán dưới dạng đồ thị có hướng (Directed Graph):
- **Đỉnh (Node):** Các biến (ví dụ: `"a"`, `"b"`).
- **Cạnh (Edge):** Nếu có phương trình `a / b = 2.0`, ta có một cạnh có hướng từ `a` đến `b` với trọng số `2.0`. Đồng thời, ta cũng có cạnh ngược từ `b` về `a` với trọng số `1 / 2.0 = 0.5`.
- **Truy vấn (Query):** Để tính `c / d`, ta cần tìm đường đi từ đỉnh `c` đến đỉnh `d`. Giá trị trả về chính là tích của tất cả các trọng số trên đường đi đó.

*Độ phức tạp:*
- Khởi tạo đồ thị: $\mathcal{O}(E)$ (với $E$ là số phương trình).
- Trả lời truy vấn: Mỗi truy vấn mất tối đa $\mathcal{O}(V + E)$ (với $V$ là số lượng biến). Tổng thời gian: $\mathcal{O}(Q \times (V + E))$. Do ràng buộc của đề bài số phương trình khá nhỏ ($\le 20$), BFS/DFS chạy cực kỳ ổn và nhanh. Đây cũng là cách bạn đã sử dụng trước đó.

---

## Cách 2: Union-Find (Disjoint Set) - Cách Tối Ưu (Best Solution)

Đây là cách giải "elegant" (tinh tế) và được đánh giá là **Best Solution** cho loại bài toán đánh giá tỉ lệ tương đương này. Tôi đã viết lại file `Solution.java` bằng phương pháp này.

### Ý tưởng cơ bản
Sử dụng cấu trúc Disjoint Set (Tập hợp rời rạc) để nhóm các biến có liên quan đến nhau (có thể suy ra tỉ lệ) vào chung một tập hợp. Mỗi tập hợp có một đỉnh làm "gốc" (root).

Với mỗi đỉnh `x`, ta duy trì 2 thông tin:
1. `parent`: Con trỏ trỏ đến cha của nó (hoặc gốc).
2. `weight`: Tỉ lệ giữa nó và cha của nó (tức là giá trị `x / parent`).

### Các thao tác
- **Find (Tìm gốc):** Đi tìm gốc của biến `x`. Đồng thời áp dụng kỹ thuật **Path Compression (Nén đường)**: trong quá trình đệ quy tìm gốc, ta cập nhật để `x` trỏ trực tiếp đến gốc và cập nhật lại `weight` (bằng tích các trọng số trên đường đi). Kỹ thuật này giúp các lần tìm kiếm sau chỉ mất thời gian xấp xỉ $\mathcal{O}(1)$.
- **Union (Gộp tập hợp):** Khi có `u / v = value`, ta tìm gốc của `u` (gọi là `rootU`) và gốc của `v` (gọi là `rootV`). Nếu chúng khác nhau, ta gộp lại bằng cách cho `rootU` làm con của `rootV`. 
  - Ta có `u / rootU = weightU` và `v / rootV = weightV`.
  - Vậy: `rootU / rootV = (u / weightU) / (v / weightV) = (u / v) * (weightV / weightU) = value * weightV / weightU`.
  - Từ đó ta cập nhật `weight` mới cho `rootNodeU`.
- **Truy vấn:** Để tính `c / d`, trước tiên kiểm tra chúng có tồn tại và có chung một gốc không. Nếu có chung gốc (gọi là `root`), ta có:
  `c / d = (c / root) / (d / root) = weightC / weightD`.

### Độ phức tạp
- Nhờ kỹ thuật Path Compression, thuật toán Union-Find có độ phức tạp trung bình cho mỗi truy vấn là xấp xỉ $\mathcal{O}(1)$.
- Khởi tạo: $\mathcal{O}(E)$.
- Xử lý các query: $\mathcal{O}(Q)$.
- **Tổng thời gian:** $\mathcal{O}(E + Q)$, nhanh và tối ưu hơn việc dùng đồ thị BFS/DFS do không cần duyệt lại các đỉnh và cạnh đã biết cho từng câu truy vấn mới.
