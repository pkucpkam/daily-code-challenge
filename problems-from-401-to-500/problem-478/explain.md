# 📚 Giải thích Chi tiết Bài 671 - Second Minimum Node In a Binary Tree

**Mục tiêu:** Cho một cây nhị phân đặc biệt không rỗng, trong đó mỗi nút có đúng 0 hoặc 2 nút con. Nếu một nút có 2 con, giá trị của nút đó luôn bằng giá trị nhỏ hơn giữa 2 con: `root.val = min(root.left.val, root.right.val)`. Hãy tìm và trả về giá trị nhỏ thứ nhì trong tập hợp tất cả các giá trị của các nút trên cây. Nếu không tồn tại giá trị nhỏ thứ nhì (tất cả các nút đều cùng một giá trị), trả về `-1`.

---

## 🎯 Phân tích bài toán

### 1. Đặc tính Cốt lõi của Cây Nhị Phân Đặc Biệt (Tree Invariants)
- **Tính chất phân nhánh (Full Binary Tree):** Mỗi nút chỉ có thể là nút lá (0 con) hoặc có đầy đủ 2 con (không bao giờ có nút chỉ có 1 con).
- **Tính chất đơn điệu theo chiều sâu (Monotonicity along tree paths):**
  Với mỗi nút nội hàm (internal node) $u$:
  $$u.val = \min(u.left.val, u.right.val)$$
  Điều này dẫn đến hệ quả tất yếu:
  $$u.val \le u.left.val \quad \text{và} \quad u.val \le u.right.val$$
  Nói cách khác: **Giá trị của mọi nút con cháu luôn lớn hơn hoặc bằng giá trị của nút cha/tổ tiên**.
  Càng đi sâu xuống dưới các tầng của cây, giá trị của các nút chỉ có thể **giữ nguyên hoặc tăng lên**, tuyệt đối không bao giờ giảm đi.
- **Nút gốc là giá trị nhỏ nhất toàn cục (Global Minimum):**
  Do tính chất đơn điệu không giảm nêu trên:
  $$\forall v \in \text{Tree}, \quad root.val \le v.val$$
  Nút gốc `root.val` luôn là giá trị nhỏ nhất số 1 trên toàn bộ cây!

---

### 2. Định nghĩa Giá trị Nhỏ Thứ Nhì (Second Minimum Value)
- Giá trị nhỏ thứ nhì không đơn thuần là phần tử đứng thứ 2 nếu tính cả trùng lặp, mà là **giá trị nhỏ nhất trong số các phần tử có giá trị THỰC SỰ LỚN HƠN nút gốc**:
  $$\text{secondMin} = \min \{ node.val \mid node \in \text{Tree} \text{ và } node.val > root.val \}$$
- **Trường hợp không tồn tại:**
  Nếu toàn bộ các nút trên cây đều có cùng giá trị với nút gốc (ví dụ cây `[2, 2, 2]`), tập hợp các giá trị lớn hơn `root.val` là rỗng ($\emptyset$). Khi đó ta trả về `-1`.

---

### 3. Ràng buộc & Đánh giá quy mô (Constraints)
- Số lượng nút: $1 \le N \le 25$. Cây có kích thước rất nhỏ, nhưng giải thuật cần chuẩn mực và tối ưu với mọi quy mô $N$.
- $1 \le Node.val \le 2^{31} - 1$.
- ⚠️ **Cảnh giác bẫy `Integer.MAX_VALUE`:**
  Vì giá trị của nút có thể đạt tới $2^{31} - 1$ (`Integer.MAX_VALUE`), nếu ta khởi tạo biến lưu kết quả `min2 = Integer.MAX_VALUE` rồi kiểm tra `if (min2 == Integer.MAX_VALUE) return -1;`, chương trình sẽ cho kết quả **SAI** khi giá trị nhỏ thứ nhì của cây thực sự là $2^{31} - 1$!
  Do đó, cách tiếp cận thông qua đệ quy trực tiếp trả về `-1` khi không tìm thấy là kỹ thuật an toàn và chuẩn mực nhất.

---

## 🔍 Các hướng tiếp cận (Approaches Comparison)

| Tiêu chí | Cách 1: Duyệt toàn bộ cây (Brute Force DFS/BFS + Set) | Cách 2: Duyệt theo tầng có Cắt tỉa (BFS Level-order Pruning) | Cách 3: Đệ quy DFS Cắt tỉa nhánh (Recursive DFS Pruning ⭐) |
| :--- | :--- | :--- | :--- |
| **Ý tưởng** | Duyệt qua mọi nút của cây, thêm vào `Set<Integer>`, lấy phần tử nhỏ thứ nhì | Dùng hàng đợi duyệt theo tầng; nút nào $> root.val$ thì lấy làm ứng viên và không duyệt tiếp con của nó | Gọi đệ quy; nếu gặp `node.val > root.val` lập tức trả về giá trị đó (cắt tỉa toàn bộ cây con bên dưới) |
| **Tận dụng đặc tính cây** | ❌ Bỏ qua hoàn toàn tính chất $u.val = \min(left, right)$ | ✅ Cắt tỉa nhánh khi gặp giá trị lớn hơn `root.val` | ✅ Tối đa hóa khả năng cắt tỉa ngay từ lời gọi đệ quy |
| **Bẫy `Integer.MAX_VALUE`** | Dễ vướng nếu dùng biến min | Có thể tránh được nếu dùng `long` | 🛡️ Tránh hoàn toàn, an toàn tuyệt đối |
| **Độ phức tạp Thời gian** | $\mathcal{O}(N \log N)$ hoặc $\mathcal{O}(N)$ | $\mathcal{O}(N)$ trường hợp xấu nhất, trung bình $\mathcal{O}(H)$ | $\mathcal{O}(N)$ trường hợp xấu nhất, trung bình $\mathcal{O}(H)$, tốt nhất $\mathcal{O}(1)$ |
| **Độ phức tạp Bộ nhớ** | $\mathcal{O}(N)$ (lưu HashSet/TreeSet) | $\mathcal{O}(W)$ (Queue lưu trữ tầng rộng nhất) | $\mathcal{O}(H)$ (Call stack đệ quy) |
| **Đánh giá** | Kém tối ưu, không thể hiện tư duy cấu trúc dữ liệu | Tốt, trực quan khi tư duy lặp | **Tối ưu nhất, thanh lịch nhất, chuẩn mực phỏng vấn** |

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Đệ Quy DFS Cắt Tỉa Nhánh (Recursive Branch Pruning)

### 💡 Ý tưởng thuật toán cốt lõi (Core Insights)

Mấu chốt của thuật toán tối ưu nằm ở **Quy tắc Cắt tỉa nhánh (Branch Pruning Rule)**:
1. Xét một nút `node` bất kỳ trong quá trình đệ quy:
   - Nếu `node == null`: trả về `-1` (nhánh rỗng).
   - Nếu `node.val > minVal` (với `minVal = root.val`):
     - Theo đặc tính cây đặc biệt, mọi con cháu của `node` đều có giá trị $\ge node.val$.
     - Vì vậy, **không có bất kỳ nút con cháu nào của `node` có thể có giá trị nhỏ hơn `node.val`**!
     - Do đó, `node.val` chính là giá trị nhỏ nhất lớn hơn `root.val` trong toàn bộ cây con có gốc tại `node`.
     - 👉 **Ta CẮT TỈA NGAY LẬP TỨC: dừng duyệt nhánh cây con này và trả về `node.val`!**
   - Nếu `node.val == minVal`:
     - Giá trị này không thể là giá trị nhỏ thứ nhì (vì nó bằng giá trị nhỏ nhất).
     - Ta bắt buộc phải đệ quy tìm kiếm sâu hơn ở cả 2 nhánh con:
       ```java
       int left = dfs(node.left, minVal);
       int right = dfs(node.right, minVal);
       ```
2. **Tổng hợp kết quả từ hai nhánh con (Combine Step):**
   - Nếu cả 2 nhánh đều tìm thấy giá trị hợp lệ (`left != -1 && right != -1`):
     - Ta lấy giá trị nhỏ hơn: $\min(left, right)$.
   - Nếu chỉ một trong hai nhánh tìm thấy:
     - Trả về nhánh tìm thấy (`left != -1 ? left : right`).
   - Nếu cả hai nhánh đều trả về `-1`:
     - Trả về `-1`.

---

### 📊 Sơ đồ minh họa trực quan (Visual Walkthrough)

#### Ví dụ 1: `root = [2, 2, 5, null, null, 5, 7]`

```text
                  [2] (root)
                 /   \
                /     \
             [2]       [5]  <-- 5 > 2: CẮT TỈA NGAY! Trả về 5, KHÔNG duyệt con!
                      /   \
                    [5]   [7] (Bị bỏ qua nhờ cắt tỉa)
```

**Diễn giải quá trình:**
1. Đứng tại gốc `root = 2`:
   - `dfs(root.left, 2)`: Đến nút con trái `2`:
     - Nút `2` có `val == 2`, tiếp tục đệ quy hai con:
     - `left.left == null` $\implies -1$.
     - `left.right == null` $\implies -1$.
     - $\implies$ Nhánh trái trả về `-1`.
   - `dfs(root.right, 2)`: Đến nút con phải `5`:
     - Vì `5 > 2`, kích hoạt cắt tỉa: lập tức trả về `5` mà không cần duyệt hai con `5` và `7` bên dưới!
2. Tổng hợp tại gốc:
   - `left = -1`, `right = 5`.
   - Kết quả cuối cùng: `5`.

---

#### Ví dụ 2: `root = [2, 2, 2]`

```text
                  [2] (root)
                 /   \
               [2]   [2]
```

**Diễn giải quá trình:**
1. Đứng tại gốc `root = 2`:
   - Nút con trái `2 == 2`: gọi hai con của nó (đều `null`) $\implies -1$.
   - Nút con phải `2 == 2`: gọi hai con của nó (đều `null`) $\implies -1$.
2. Tổng hợp tại gốc:
   - `left = -1`, `right = -1`.
   - Cả 2 nhánh đều `-1` $\implies$ Kết quả cuối cùng: `-1`.

---

#### Ví dụ 3: Cả hai nhánh đều chứa giá trị lớn hơn `root.val`

```text
                  [2] (root)
                 /   \
               [3]   [5]
```

- Nhánh trái: `3 > 2` $\implies$ Cắt tỉa, trả về `3`.
- Nhánh phải: `5 > 2` $\implies$ Cắt tỉa, trả về `5`.
- Tổng hợp tại gốc: Cả 2 đều khác `-1` $\implies \min(3, 5) = 3$.

---

## 🔄 Từng bước thực thi (Dry-run / Trace qua Bảng trạng thái)

Xét cây với cấu trúc:
```text
           (2)
          /   \
        (2)   (5)
       /   \
     (3)   (2)
```

| Bước | Lời gọi hàm | Nút hiện tại (`node.val`) | So với `minVal = 2` | Hành động thực hiện | Kết quả trả về |
| :---: | :--- | :---: | :---: | :--- | :---: |
| **1** | `dfs(root, 2)` | `2` | $2 == 2$ | Gọi nhánh trái và phải của nút gốc | $\min(3, 5) = 3$ |
| **2** | `dfs(node_left, 2)` | `2` | $2 == 2$ | Gọi nhánh trái và phải của nút con trái | $\min(3, -1) = 3$ |
| **3** | `dfs(node_left.left, 2)` | `3` | $3 > 2$ | **Kích hoạt cắt tỉa!** Dừng duyệt nhánh này | **3** |
| **4** | `dfs(node_left.right, 2)` | `2` | $2 == 2$ | Hai con là `null` $\to$ cả 2 trả về `-1` | **-1** |
| **5** | Nhánh trái nút gốc tổng hợp | - | - | `left = 3`, `right = -1` $\implies$ trả về `3` | **3** |
| **6** | `dfs(node_right, 2)` | `5` | $5 > 2$ | **Kích hoạt cắt tỉa!** Dừng duyệt nhánh này | **5** |
| **7** | Nút gốc tổng hợp | - | - | `left = 3`, `right = 5` $\implies \min(3, 5) = 3$ | **3** |

---

## 💻 Cài đặt Thuật toán Chi tiết (Java Implementation)

### Cách 1: Giải pháp Tối ưu - Đệ Quy DFS Cắt Tỉa (Recursive Branch Pruning) ⭐

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int findSecondMinimumValue(TreeNode root) {
        if (root == null) {
            return -1;
        }
        return dfs(root, root.val);
    }

    private int dfs(TreeNode node, int minVal) {
        // Điểm dừng 1: Nút rỗng
        if (node == null) {
            return -1;
        }

        // Điểm dừng 2 (Cắt tỉa): Nếu giá trị nút lớn hơn minVal (root.val),
        // thì node.val chính là giá trị nhỏ nhất > minVal của toàn bộ cây con này.
        // Ta dừng đệ quy tại đây, không duyệt xuống các con cháu nữa!
        if (node.val > minVal) {
            return node.val;
        }

        // Nếu node.val == minVal, tiếp tục tìm kiếm ở cả hai nhánh con
        int left = dfs(node.left, minVal);
        int right = dfs(node.right, minVal);

        // Tổng hợp kết quả từ hai nhánh con:
        // Nếu cả hai nhánh đều tìm được ứng viên hợp lệ, lấy giá trị nhỏ hơn
        if (left != -1 && right != -1) {
            return Math.min(left, right);
        }

        // Nếu chỉ một trong hai nhánh tìm được (hoặc cả hai đều là -1)
        return left != -1 ? left : right;
    }
}
```

---

### Cách 2 (Tham khảo): Lặp BFS Duyệt theo Tầng có Cắt tỉa (Iterative BFS with Pruning)

```java
import java.util.ArrayDeque;
import java.util.Queue;

class SolutionBFS {
    public int findSecondMinimumValue(TreeNode root) {
        if (root == null) {
            return -1;
        }

        int minVal = root.val;
        long secondMin = Long.MAX_VALUE; // Dùng kiểu long để tránh nhầm lẫn với Integer.MAX_VALUE
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();

            // Nếu giá trị nút lớn hơn minVal, cập nhật secondMin và CẮT TỈA (không đẩy con vào queue)
            if (curr.val > minVal) {
                if (curr.val < secondMin) {
                    secondMin = curr.val;
                }
                continue; // Cắt tỉa: không duyệt các con cháu của nút này
            }

            // Nếu curr.val == minVal, tiếp tục đẩy các nút con vào queue để duyệt
            if (curr.left != null) {
                queue.offer(curr.left);
            }
            if (curr.right != null) {
                queue.offer(curr.right);
            }
        }

        return secondMin == Long.MAX_VALUE ? -1 : (int) secondMin;
    }
}
```

---

## 🔍 Giải thích từng dòng lệnh chi tiết (Line-by-Line Walkthrough)

Dưới đây là chi tiết từng thành phần trong giải pháp tối ưu:

1. **`public int findSecondMinimumValue(TreeNode root)`**:
   - Khởi đầu thuật toán, kiểm tra `root == null` để phòng thủ.
   - Gọi hàm đệ quy `dfs(root, root.val)` truyền giá trị của nút gốc làm mốc so sánh toàn cục (`minVal`).
2. **`if (node == null) return -1;`**:
   - Điều kiện dừng cơ bản khi đi quá các nút lá.
3. **`if (node.val > minVal) return node.val;`**:
   - **Dòng lệnh quan trọng nhất của thuật toán:** Kích hoạt cơ chế cắt tỉa nhánh. Thay vì phải duyệt qua toàn bộ cây con sâu phía dưới, ta khẳng định ngay không có nút nào trong nhánh này có thể nhỏ hơn `node.val`. Trả về luôn `node.val` giúp giảm số phép duyệt một cách tối đa.
4. **`int left = dfs(node.left, minVal); int right = dfs(node.right, minVal);`**:
   - Khi `node.val == minVal`, nút hiện tại không thể là giá trị nhỏ thứ nhì, bắt buộc phải chia để trị (Divide and Conquer) xuống 2 cây con trái và phải.
5. **`if (left != -1 && right != -1) return Math.min(left, right);`**:
   - Cả 2 nhánh đều có giá trị lớn hơn `root.val`, ta cần tìm giá trị nhỏ thứ nhì toàn cục nên chọn giá trị bé nhất giữa hai nhánh.
6. **`return left != -1 ? left : right;`**:
   - Cú pháp toán tử ba ngôi ngắn gọn xử lý đồng thời 2 trường hợp: một nhánh hợp lệ hoặc cả hai nhánh đều trả về `-1`.

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### Độ phức tạp Thời gian (Time Complexity)
- **Trường hợp tốt nhất (Best Case):** $\mathcal{O}(1)$
  - Nếu ngay tầng 1, hai con của gốc đều lớn hơn gốc (`root.left.val > root.val` và `root.right.val > root.val`), thuật toán chỉ xét đúng 3 nút rồi dừng ngay nhờ cắt tỉa.
- **Trường hợp trung bình (Average Case):** $\mathcal{O}(H)$
  - Với $H$ là chiều cao của cây. Thuật toán chỉ đi theo các nhánh có giá trị bằng `root.val` và dừng lại ngay khi chạm tới lớp biên đầu tiên có giá trị lớn hơn.
- **Trường hợp xấu nhất (Worst Case):** $\mathcal{O}(N)$
  - Khi hầu hết các nút trên cây đều có giá trị bằng `root.val` (ví dụ tất cả các nút đều bằng nhau, hoặc chỉ có 1 nút lá duy nhất ở tầng sâu nhất khác giá trị). Khi đó thuật toán sẽ duyệt qua $N$ nút.
  - Vì ràng buộc bài toán $N \le 25$, thời gian thực thi là tức thì ($< 1\text{ ms}$).

### Độ phức tạp Không gian (Space Complexity)
- $\mathcal{O}(H)$ tương ứng với bộ nhớ của ngăn xếp đệ quy (Call Stack).
  - Cây cân bằng: $H = \mathcal{O}(\log N)$.
  - Cây suy biến (lệch hoàn toàn): $H = \mathcal{O}(N)$.
- Với $N \le 25$, chiều cao tối đa của call stack $\le 25$ frame, tiêu tốn lượng bộ nhớ cực kỳ nhỏ ($\mathcal{O}(1)$ trên thực tế).

---

## 💡 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways & Interview Tips)

1. **Hiểu rõ tính chất cây có thứ tự một chiều (Heap Property):**
   - Đề bài $root.val = \min(root.left.val, root.right.val)$ chính là tính chất của một **Min-Heap**.
   - Khác với **Binary Search Tree** (nhánh trái $<$ gốc $<$ nhánh phải), ở Min-Heap giá trị chỉ tăng dần từ trên xuống dưới theo từng nhánh.
   - Nhờ đó, việc cắt tỉa theo chiều sâu phát huy hiệu quả tối đa: **hễ gặp một nút lớn hơn mốc, mọi nút bên dưới nó đều không thể nhỏ hơn**.

2. **Cảnh giác bẫy giá trị biên `Integer.MAX_VALUE`:**
   - Trong Java, khi bài toán yêu cầu tìm giá trị nhỏ nhất / nhỏ thứ nhì, nhiều ứng viên có thói quen gán giá trị khởi tạo bằng `Integer.MAX_VALUE`.
   - Với đề bài $Node.val \le 2^{31} - 1$, giá trị của nút hoàn toàn có thể bằng `Integer.MAX_VALUE`.
   - Thiết kế thuật toán trả về `-1` trực tiếp qua logic đệ quy giúp mã nguồn hoàn toàn độc lập với giá trị biên của kiểu dữ liệu.

3. **Giao tiếp khi phỏng vấn:**
   - Hãy bắt đầu bằng việc chỉ ra: *"Vì $root.val$ luôn là giá trị nhỏ nhất toàn cây, nên bài toán tương đương với việc tìm giá trị nhỏ nhất trong số các nút có giá trị lớn hơn $root.val$."*
   - Sau đó nêu bật: *"Ta có thể cắt tỉa toàn bộ nhánh con ngay khi gặp một nút có giá trị lớn hơn $root.val$ mà không cần duyệt sâu hơn."*
   - Điều này thể hiện khả năng phân tích toán học và tối ưu thuật toán vượt trội so với việc chỉ duyệt vét cạn (brute force).
