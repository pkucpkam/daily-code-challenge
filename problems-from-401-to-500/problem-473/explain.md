# 📚 Giải thích Chi tiết Bài 662 - Maximum Width of Binary Tree

**Mục tiêu:** Cho nút gốc `root` của một cây nhị phân. Hãy tính và trả về **độ rộng lớn nhất (maximum width)** trong tất cả các tầng (levels) của cây. Độ rộng của một tầng được định nghĩa là khoảng cách giữa nút khác `null` ngoài cùng bên trái và nút khác `null` ngoài cùng bên phải, trong đó tất cả các nút `null` nằm xen giữa hai nút này (nếu cây là cây nhị phân hoàn chỉnh kéo dài xuống tầng đó) đều được tính vào độ dài. Đề bài đảm bảo kết quả nằm trong giới hạn số nguyên có dấu 32-bit.

---

## 🎯 Phân tích bài toán

### 1. Bản chất Độ rộng của một Tầng (Width of a Level)

Hãy phân tích kỹ định nghĩa về độ rộng mà bài toán đặt ra:
- Tại một tầng bất kỳ, ta xác định:
  - Nút khác `null` xuất hiện **sớm nhất** (ngoài cùng bên trái).
  - Nút khác `null` xuất hiện **muộn nhất** (ngoài cùng bên phải).
- Tất cả các vị trí trống (`null`) nằm **ở giữa** hai nút này đều được đếm.
- Các vị trí `null` nằm ở **hai phía bên ngoài** (bên trái nút ngoài cùng bên trái, hoặc bên phải nút ngoài cùng bên phải) sẽ **không** được tính.

```text
Ví dụ minh họa tầng thứ 3 của một cây nhị phân:
Vị trí trong cây đầy đủ:    [0]     [1]     [2]     [3]     [4]     [5]     [6]     [7]
Trạng thái nút thực tế:    null    [ 5 ]   null    null    [ 3 ]   null    [ 9 ]   null
                                     ▲                                       ▲
                             Trái cùng (Left)                        Phải cùng (Right)
                                     └─────────────────── 6 vị trí ──────────┘
                              Độ rộng của tầng = Index(Right) - Index(Left) + 1
                                               = 6 - 1 + 1 = 6
```

---

### 2. Kỹ thuật Đánh số Chỉ số theo Cây Nhị phân Đầy đủ (Binary Heap Indexing)

Trong cấu trúc dữ liệu **Binary Heap** (thường dùng cài đặt PriorityQueue), một cây nhị phân đầy đủ có thể được ánh xạ phẳng thành một mảng 1 chiều nhờ quy tắc đánh số vị trí có tính chất toán học kỳ diệu:

Nếu ta đánh số nút gốc từ $0$ (0-indexed):
- Với một nút cha có chỉ số là $i$:
  - Nút con bên trái sẽ có chỉ số:
    $$\text{index}_{\text{left}} = 2 \times i + 1$$
  - Nút con bên phải sẽ có chỉ số:
    $$\text{index}_{\text{right}} = 2 \times i + 2$$

*(Nếu đánh số từ 1, con trái là $2i$ và con phải là $2i + 1$).*

> 💡 **Tính chất cốt lõi:**
> Nhờ cách đánh số này, dù giữa hai nút có bao nhiêu nhánh con bị khuyết (`null`), chỉ số của mỗi nút luôn luôn tương ứng với vị trí thực tế của nó trên cây nhị phân đầy đủ.
> 
> Khi đó, độ rộng của một tầng chỉ đơn giản là:
> $$\text{Width} = \text{index}_{\text{rightmost}} - \text{index}_{\text{leftmost}} + 1$$

---

### 3. Cạm bẫy Chết người: Tràn số Nguyên (The Fatal Integer Overflow Trap)

Ràng buộc bài toán nêu rõ:
- Số lượng nút của cây lên tới $3000$ nút.
- Cây không bắt buộc phải cân bằng; nó có thể bị suy biến thành một cây lệch hoàn toàn về một phía (skewed tree) với độ sâu lên tới $3000$.

Ở tầng thứ $d$ ($d$ từ $0$ đến $3000$):
- Chỉ số nút con ở tầng $d$ có thể lên tới cấp số nhân:
  $$\text{index} \approx 2^d$$
- Với $d = 31$: $2^{31} - 1 \approx 2.14 \times 10^9$ $\implies$ Vượt quá giới hạn của `int` 32-bit!
- Với $d = 63$: $2^{63} - 1 \approx 9.22 \times 10^{18}$ $\implies$ Vượt quá giới hạn của `long` 64-bit!
- Với $d = 3000$: $2^{3000}$ là một con số khổng lồ có hơn $900$ chữ số!

Nếu ta cứ nhân đôi chỉ số một cách ngây thơ ($2 \times i + 1$), chương trình chắc chắn sẽ bị **tràn số âm**, dẫn đến phép trừ $\text{rightmost} - \text{leftmost}$ cho ra kết quả sai lệch hoàn toàn.

---

### 4. Giải pháp: Chuẩn hóa Chỉ số theo Tầng (Coordinate Offset Normalization)

Quan sát mấu chốt giải quyết vấn đề:
- Đề bài đảm bảo rằng **đáp án cuối cùng (độ rộng tối đa) luôn nằm trong giới hạn số nguyên có dấu 32-bit**:
  $$\text{Width} = R - L + 1 < 2^{31}$$
- Độ rộng của một tầng chỉ phụ thuộc vào **khoảng cách tương đối** giữa các nút trong cùng tầng đó, chứ **hoàn toàn không phụ thuộc** vào giá trị tuyệt đối của chỉ số:
  $$R - L + 1 = (R - L) - (L - L) + 1$$

> ✨ **Chiến lược Chuẩn hóa (Normalization):**
> Ở mỗi tầng, ngay khi bắt đầu duyệt:
> 1. Gọi chỉ số của nút đầu tiên (nút ngoài cùng bên trái) trong Queue là $minIndex$.
> 2. Trừ $minIndex$ khỏi chỉ số của tất cả các nút ở tầng đó:
>    $$\text{normalizedIndex} = \text{curr.index} - minIndex$$
> 3. Nút đầu tiên sẽ có chỉ số tương đối là $0$.
> 4. Chỉ số sinh ra cho các nút con ở tầng kế tiếp sẽ được tính dựa trên $\text{normalizedIndex}$ này:
>    - Con trái: $2 \times \text{normalizedIndex} + 1$
>    - Con phải: $2 \times \text{normalizedIndex} + 2$
>
> Vì độ rộng tối đa $< 2^{31}$, chỉ số tương đối $\text{normalizedIndex}$ ở mọi tầng luôn luôn nhỏ hơn độ rộng của tầng đó. Do đó, **nguy cơ tràn số bị triệt tiêu hoàn toàn 100%!**

---

## 🔍 Các hướng tiếp cận (Approaches Comparison)

| Tiêu chí | BFS (Duyệt theo Tầng - Khuyên dùng ⭐) | DFS (Duyệt theo Chiều sâu Pre-order) |
| :--- | :--- | :--- |
| **Cơ chế hoạt động** | Dùng `Queue`, xử lý dứt điểm từng tầng một | Đệ quy đi sâu, dùng danh sách ghi lại chỉ số nút đầu tiên mỗi tầng |
| **Chống tràn số** | **Hoàn hảo & Tự nhiên**: Tại mỗi tầng, phần tử đầu Queue luôn là nút trái cùng, dễ dàng lấy làm mốc $minIndex$ để trừ | **Khó xử lý triệt để**: Khi đi sâu nhánh phải, nhánh trái chưa chắc đã tới tầng đó để thiết lập mốc, dễ tràn số `long` nếu cây quá sâu |
| **Độ phức tạp Thời gian** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Độ phức tạp Bộ nhớ** | $\mathcal{O}(W)$ (với $W$ là độ rộng lớn nhất của cây) | $\mathcal{O}(H)$ (với $H$ là chiều cao của cây) |
| **Tính trực quan** | Cực kỳ rõ ràng, ánh xạ trực tiếp với khái niệm "Độ rộng tầng" | Cần khéo léo dùng Pre-order (Left trước Right) để nút đầu tiên luôn tới trước |

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): BFS Level-Order với Chuẩn Hóa Chỉ Số

### 💡 Thuật toán từng bước

1. **Khởi tạo:**
   - Nếu `root == null`, trả về $0$.
   - Sử dụng một hàng đợi `Queue<NodeIndex>` lưu trữ cặp `(nút, chỉ số)`.
   - Đưa `(root, 0)` vào hàng đợi. Khởi tạo `maxWidth = 0`.
2. **Vòng lặp duyệt theo từng tầng (`while (!queue.isEmpty())`):**
   - Lấy kích thước hiện tại của hàng đợi: `size = queue.size()`.
   - Lấy chỉ số của phần tử đầu tiên làm mốc: `minIndex = queue.peek().index`.
   - Khởi tạo hai biến `first = 0` và `last = 0`.
3. **Xử lý tất cả các nút trong tầng (`for i = 0 -> size - 1`):**
   - Lấy phần tử ra khỏi hàng đợi: `curr = queue.poll()`.
   - Tính chỉ số đã chuẩn hóa: `normalizedIndex = curr.index - minIndex`.
   - Nếu $i == 0$: `first = normalizedIndex` (chính là $0$).
   - Nếu $i == size - 1$: `last = normalizedIndex`.
   - Thêm con trái (nếu có): `queue.offer(new NodeIndex(curr.node.left, 2 * normalizedIndex + 1))`.
   - Thêm con phải (nếu có): `queue.offer(new NodeIndex(curr.node.right, 2 * normalizedIndex + 2))`.
4. **Cập nhật độ rộng tối đa:**
   $$\text{currentWidth} = last - first + 1$$
   $$\text{maxWidth} = \max(\text{maxWidth}, \text{currentWidth})$$
5. **Kết thúc:** Trả về `maxWidth`.

---

## 💻 Cài đặt Thuật toán Chi tiết (Java Implementation)

```java
import java.util.ArrayDeque;
import java.util.Queue;

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
    /**
     * Lớp bổ trợ lưu trữ cặp đối tượng (Node, Index)
     */
    private static class NodeIndex {
        TreeNode node;
        long index;

        NodeIndex(TreeNode node, long index) {
            this.node = node;
            this.index = index;
        }
    }

    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<NodeIndex> queue = new ArrayDeque<>();
        queue.offer(new NodeIndex(root, 0));
        int maxWidth = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            long minIndex = queue.peek().index; // Chỉ số mốc nhỏ nhất của tầng hiện tại
            long first = 0;
            long last = 0;

            for (int i = 0; i < size; i++) {
                NodeIndex curr = queue.poll();
                // Chuẩn hóa chỉ số bằng cách trừ đi minIndex để chống tràn số
                long normalizedIndex = curr.index - minIndex;

                if (i == 0) {
                    first = normalizedIndex;
                }
                if (i == size - 1) {
                    last = normalizedIndex;
                }

                // Gán chỉ số cho các nút con dựa trên chỉ số đã chuẩn hóa của nút cha
                if (curr.node.left != null) {
                    queue.offer(new NodeIndex(curr.node.left, 2 * normalizedIndex + 1));
                }
                if (curr.node.right != null) {
                    queue.offer(new NodeIndex(curr.node.right, 2 * normalizedIndex + 2));
                }
            }

            // Cập nhật độ rộng tối đa
            int currentWidth = (int) (last - first + 1);
            if (currentWidth > maxWidth) {
                maxWidth = currentWidth;
            }
        }

        return maxWidth;
    }
}
```

---

## 🔄 Từng bước thực thi (Dry-run / Trace qua Ví dụ cụ thể)

### Xét Ví dụ 1: `root = [1,3,2,5,3,null,9]`

Cấu trúc cây:
```text
            1 (idx: 0)
          /   \
     3 (idx: 1)  2 (idx: 2)
       /   \        \
  5 (idx: 3) 3 (idx: 4) 9 (idx: 6)
```

#### Quá trình duyệt BFS từng tầng:

| Tầng (Level) | Hàng đợi đầu tầng | $minIndex$ | Các nút được duyệt (chỉ số gốc $\to$ chuẩn hóa) | Con sinh ra đưa vào Queue | Độ rộng tầng | `maxWidth` |
| :---: | :--- | :---: | :--- | :--- | :---: | :---: |
| **0** | `[(1, idx=0)]` | $0$ | Nút $1$: $0 - 0 = 0$ | Trái: $(3, 1)$, Phải: $(2, 2)$ | $0 - 0 + 1 = \mathbf{1}$ | $1$ |
| **1** | `[(3, idx=1), (2, idx=2)]` | $1$ | - Nút $3$: $1 - 1 = 0$<br>- Nút $2$: $2 - 1 = 1$ | Từ $3$: Trái $(5, 1)$, Phải $(3, 2)$<br>Từ $2$: Phải $(9, 4)$ | $1 - 0 + 1 = \mathbf{2}$ | $2$ |
| **2** | `[(5, idx=1), (3, idx=2), (9, idx=4)]` | $1$ | - Nút $5$: $1 - 1 = 0$<br>- Nút $3$: $2 - 1 = 1$<br>- Nút $9$: $4 - 1 = 3$ | Không có con | $3 - 0 + 1 = \mathbf{4}$ | $\mathbf{4}$ |

$\implies$ Kết quả cuối cùng trả về: **$4$**.

---

### Xét Ví dụ 2: `root = [1,3,2,5,null,null,9,6,null,7]`

Cấu trúc cây:
```text
                  1 (idx: 0)
                /   \
           3 (idx: 1)   2 (idx: 2)
           /              \
      5 (idx: 1)          9 (idx: 4)
      /                   /
  6 (idx: 1)          7 (idx: 7)
```

Ở tầng thứ 3:
- Nút $6$ có chỉ số chuẩn hóa là $0$.
- Nút $7$ có chỉ số chuẩn hóa là $6$.
- Độ rộng của tầng:
  $$\text{Width} = 6 - 0 + 1 = \mathbf{7}$$
- Các vị trí ở tầng này tương ứng với: `[6, null, null, null, null, null, 7]`, gồm đúng $7$ phần tử.

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### 1. Độ phức tạp Thời gian (Time Complexity): $\mathcal{O}(N)$
- Mỗi nút trong cây nhị phân được đưa vào hàng đợi (`queue.offer`) đúng một lần và lấy ra (`queue.poll`) đúng một lần.
- Các phép tính cộng, trừ, nhân, chuẩn hóa chỉ số và cập nhật biến cực đại đều thực hiện trong thời gian hằng số $\mathcal{O}(1)$.
- Do đó, tổng thời gian duyệt là $\mathcal{O}(N)$, với $N$ là số lượng nút trong cây ($N \le 3000$).

### 2. Độ phức tạp Không gian (Space Complexity): $\mathcal{O}(W) \le \mathcal{O}(N)$
- Bộ nhớ phụ trợ chủ yếu đến từ hàng đợi `Queue`.
- Tại bất kỳ thời điểm nào, số lượng phần tử tối đa nằm trong hàng đợi không vượt quá số lượng nút ở tầng rộng nhất của cây ($W$).
- Trong trường hợp xấu nhất (cây nhị phân hoàn hảo), tầng đáy có thể chứa tới $\approx \lceil N / 2 \rceil$ nút $\implies \mathcal{O}(N)$ không gian bộ nhớ.

---

## 💡 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways & Interview Tips)

### 1. Phân biệt các mô hình đánh số cây nhị phân
- **0-indexed:** Gốc là $0$. Con trái là $2i + 1$, con phải là $2i + 2$.
- **1-indexed:** Gốc là $1$. Con trái là $2i$, con phải là $2i + 1$.
- Dù chọn cách nào, công thức khoảng cách luôn là $\text{Right} - \text{Left} + 1$.

### 2. Kỹ thuật Offset Subtraction (Dịch mốc tọa độ)
- Khi gặp bài toán có giá trị chỉ số tăng theo hàm mũ ($2^d$) nhưng bài toán chỉ quan tâm tới **hiệu số tương đối**, hãy luôn nghĩ tới việc trừ đi giá trị mốc nhỏ nhất ở đầu mỗi chu kỳ duyệt.
- Đây là "bảo bối" giúp bạn ghi điểm tuyệt đối trong mắt nhà tuyển dụng khi bàn luận về khả năng xử lý biên và chống tràn số (overflow handling).

### 3. Các bài toán liên quan cần luyện tập
- **102. Binary Tree Level Order Traversal:** Nền tảng duyệt BFS theo từng tầng.
- **103. Binary Tree Zigzag Level Order Traversal:** Duyệt đổi hướng theo tầng.
- **199. Binary Tree Right Side View:** Lấy nút ngoài cùng bên phải của mỗi tầng.
- **958. Check Completeness of a Binary Tree:** Kiểm tra tính hoàn chỉnh của cây nhị phân dựa trên chỉ số Heap Indexing.
