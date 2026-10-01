# 📚 Giải thích Chi tiết Bài 617 - Merge Two Binary Trees

**Mục tiêu:** Cho gốc của hai cây nhị phân `root1` và `root2`. Hãy gộp hai cây lại thành một cây nhị phân mới theo quy tắc: nếu hai node của hai cây ở cùng một vị trí (chồng lên nhau), giá trị của node mới sẽ bằng tổng giá trị của hai node ban đầu; nếu chỉ có một node tồn tại tại vị trí đó, node đó sẽ được dùng trực tiếp làm node cho cây mới. Trả về gốc của cây nhị phân sau khi đã gộp.

---

## 🎯 Phân tích bài toán

### 1. Đặc điểm đầu vào & Ràng buộc
- Số lượng node của cả hai cây nằm trong khoảng $[0, 2000]$.
- Giá trị mỗi node: $-10^4 \le \text{Node.val} \le 10^4$.
- Cây có thể rỗng hoàn toàn (`root1 == null` hoặc `root2 == null` hoặc cả hai đều `null`).
- Cấu trúc hai cây có thể hoàn toàn bất đối xứng (độ cao khác nhau, hình dạng khác nhau).

---

### 2. Bản chất của Quy tắc Gộp (Merge Logic)
Tại bất kỳ vị trí tương ứng nào giữa hai cây trong quá trình duyệt, ta sẽ gặp chính xác 1 trong 4 tình huống:

| Tình huống | Trạng thái `root1` | Trạng thái `root2` | Kết quả trả về tại vị trí hiện tại |
| :---: | :---: | :---: | :--- |
| **1** | `null` | `null` | `null` (Không có node nào ở vị trí này) |
| **2** | `null` | Khác `null` | **`root2`** (Lấy toàn bộ cây con xuất phát từ `root2`) |
| **3** | Khác `null` | `null` | **`root1`** (Lấy toàn bộ cây con xuất phát từ `root1`) |
| **4** | Khác `null` | Khác `null` | Cập nhật giá trị: $\text{val} = \text{root1.val} + \text{root2.val}$, sau đó tiếp tục đệ quy gộp cây con trái và phải. |

> **💡 Điểm mấu chốt (Crucial Insight):**  
> Khi một trong hai node là `null` (Tình huống 2 và 3), ta **không cần duyệt tiếp** các tầng bên dưới của cây con còn lại. Vì cây kia đã là `null`, kết quả gộp của toàn bộ cây con còn lại chính là bản thân cây con đó! Do đó, ta chỉ cần trả về con trỏ tới node khác `null` trong thời gian $\mathcal{O}(1)$.

---

### 3. Cấu trúc Đệ quy của Cây nhị phân (Recursive Nature)
Cây nhị phân có định nghĩa đệ quy thuần túy: Mỗi node gồm một giá trị và hai cây con (trái và phải) cũng là cây nhị phân.
Vì vậy, việc gộp hai cây lớn quy về:
1. Gộp hai node hiện tại: `root1.val += root2.val`.
2. Gộp cây con bên trái: `root1.left = mergeTrees(root1.left, root2.left)`.
3. Gộp cây con bên phải: `root1.right = mergeTrees(root1.right, root2.right)`.

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Đệ quy DFS Tái sử dụng Node trực tiếp (In-place Merging)

### 💡 Ý tưởng đột phá (Key Insights)

Thay vì tạo mới từng đối tượng `TreeNode` bằng toán tử `new` (gây tốn bộ nhớ heap và tăng chi phí bộ gom rác Garbage Collection), ta có thể **ghi đè trực tiếp kết quả lên `root1`**:
1. Nếu `root1 == null`, trả về `root2`.
2. Nếu `root2 == null`, trả về `root1`.
3. Khi cả hai node đều tồn tại:
   - Cộng dồn giá trị: `root1.val += root2.val`.
   - Gán cây con trái: `root1.left = mergeTrees(root1.left, root2.left)`.
   - Gán cây con phải: `root1.right = mergeTrees(root1.right, root2.right)`.
   - Trả về `root1`.

### 🌟 Ưu điểm vượt trội của giải pháp
- **Tốc độ tối đa (0ms, Beats 100% trên LeetCode):** Chỉ duyệt qua các node ở vùng giao nhau giữa hai cây.
- **Tối ưu bộ nhớ vượt trội ($\mathcal{O}(H)$):** Không tạo thêm bất kỳ đối tượng `TreeNode` nào; toàn bộ các nhánh không giao nhau được tái sử dụng nguyên vẹn chỉ bằng 1 phép gán con trỏ $\mathcal{O}(1)$.
- **Code thanh lịch, ngắn gọn:** Toàn bộ thuật toán gói gọn trong chưa đầy 10 dòng mã.

---

### 💻 Mã nguồn Java (Best Solution)

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
    /**
     * Best Solution: Đệ quy DFS kết hợp Tái sử dụng Node trực tiếp trên root1 (In-place Merging).
     * 
     * Time Complexity: O(min(N, M))
     * Space Complexity: O(H) với H = min(H1, H2)
     */
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        // Nếu một trong hai node là null, trả về node còn lại trong O(1)
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }

        // Cả 2 node đều tồn tại: cộng dồn giá trị của root2 vào root1
        root1.val += root2.val;

        // Đệ quy gộp nhánh con bên trái và bên phải
        root1.left = mergeTrees(root1.left, root2.left);
        root1.right = mergeTrees(root1.right, root2.right);

        // Trả về gốc của cây đã gộp
        return root1;
    }
}
```

---

### ⚙️ Phân tích độ phức tạp (Complexity Analysis)

| Thành phần | Độ phức tạp | Giải thích chi tiết |
| :--- | :--- | :--- |
| **Thời gian (Time Complexity)** | $\mathcal{O}(\min(N, M))$ | Trong đó $N$ và $M$ lần lượt là số node của `root1` và `root2`. Hàm đệ quy chỉ được gọi tiếp tại các vị trí mà cả hai cây đều tồn tại node. Khi gặp một vị trí mà một cây là `null`, thuật toán trả về ngay node kia trong $\mathcal{O}(1)$ mà không cần duyệt sâu thêm. |
| **Không gian (Space Complexity)** | $\mathcal{O}(H)$ | Trong đó $H = \min(H_1, H_2)$ là chiều cao của phần giao thoa giữa hai cây, tương ứng với độ sâu tối đa của ngăn xếp đệ quy (Call Stack).<br>• Cây cân bằng: $\mathcal{O}(\log(\min(N, M)))$.<br>• Cây suy biến (dạng đường thẳng): $\mathcal{O}(\min(N, M))$. Thuật toán không tốn bộ nhớ phụ cấp phát node mới. |

---

## 🔍 Cách hoạt động từng bước (Walkthrough)

### Ví dụ 1 từ đề bài:
- `root1 = [1, 3, 2, 5]`
- `root2 = [2, 1, 3, null, 4, null, 7]`

```text
       Cây 1 (root1)                 Cây 2 (root2)
             1                             2
            / \                           / \
           3   2                         1   3
          /                               \   \
         5                                 4   7
```

Quá trình gộp diễn ra như sau:

#### Bước 1: Gốc (Root)
- Cả hai node tồn tại: `root1.val = 1`, `root2.val = 2`.
- Tính tổng: `root1.val = 1 + 2 = 3`.
- Tiếp tục đệ quy sang nhánh trái và nhánh phải.

#### Bước 2: Nhánh bên trái của Gốc
- `root1.left` (node 3) và `root2.left` (node 1):
  - Cả hai đều tồn tại: `3 + 1 = 4`.
  - Cập nhật giá trị node thành `4`.
  - **Nhánh trái con:**
    - `root1.left.left` là node `5`.
    - `root2.left.left` là `null`.
    - Điều kiện dừng: `root2 == null` $\implies$ trả về node `5` của `root1`.
  - **Nhánh phải con:**
    - `root1.left.right` là `null`.
    - `root2.left.right` là node `4`.
    - Điều kiện dừng: `root1 == null` $\implies$ trả về node `4` của `root2`.
  - Nhánh trái của gốc sau khi gộp: node `4` với con trái là `5` và con phải là `4`.

#### Bước 3: Nhánh bên phải của Gốc
- `root1.right` (node 2) và `root2.right` (node 3):
  - Cả hai đều tồn tại: `2 + 3 = 5`.
  - Cập nhật giá trị node thành `5`.
  - **Nhánh trái con:**
    - Cả `root1.right.left` và `root2.right.left` đều là `null` $\implies$ trả về `null`.
  - **Nhánh phải con:**
    - `root1.right.right` là `null`.
    - `root2.right.right` là node `7`.
    - Điều kiện dừng: `root1 == null` $\implies$ trả về node `7` của `root2`.
  - Nhánh phải của gốc sau khi gộp: node `5` với con phải là `7`.

#### Kết quả Cây sau khi gộp:
```text
             3
            / \
           4   5
          / \   \
         5   4   7
```
Biểu diễn mảng: `[3, 4, 5, 5, 4, null, 7]` (Chính xác với ví dụ đề bài).

---

## 🔄 CÁC CÁCH TIẾP CẬN KHÁC (Alternative Approaches)

### Cách 1: Đệ quy Không biến đổi cây gốc (Non-destructive / Immutable)
Trong các hệ thống thực tế (đặc biệt là lập trình hàm hoặc môi trường đa luồng), việc sửa đổi dữ liệu tham số đầu vào (`root1`) có thể dẫn tới side-effects ngoài ý muốn. Khi đó, ta có thể tạo ra cây mới hoàn toàn:

```java
class SolutionImmutable {
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return null;
        }

        int val = (root1 != null ? root1.val : 0) + (root2 != null ? root2.val : 0);
        TreeNode newNode = new TreeNode(val);

        newNode.left = mergeTrees(root1 != null ? root1.left : null, root2 != null ? root2.left : null);
        newNode.right = mergeTrees(root1 != null ? root1.right : null, root2 != null ? root2.right : null);

        return newNode;
    }
}
```
- **Thời gian:** $\mathcal{O}(N + M)$ — do phải duyệt và copy toàn bộ các node của cả 2 cây sang các đối tượng mới.
- **Không gian:** $\mathcal{O}(N + M)$ — cần cấp phát bộ nhớ heap cho tất cả các node trong cây kết quả.

---

### Cách 2: Duyệt lặp theo Chiều rộng với Hàng đợi (Iterative BFS with Queue)
Nếu cây rất sâu (hàng nghìn node suy biến thành danh sách liên kết), đệ quy có thể gặp nguy cơ `StackOverflowError`. Ta có thể dùng `Queue` lưu các cặp node tương ứng để duyệt theo tầng (BFS) một cách an toàn trên bộ nhớ Heap:

```java
import java.util.LinkedList;
import java.util.Queue;

class SolutionBFS {
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null) return root2;
        if (root2 == null) return root1;

        Queue<TreeNode[]> queue = new LinkedList<>();
        queue.offer(new TreeNode[]{root1, root2});

        while (!queue.isEmpty()) {
            TreeNode[] pair = queue.poll();
            TreeNode node1 = pair[0];
            TreeNode node2 = pair[1];

            // Cộng giá trị node2 vào node1
            node1.val += node2.val;

            // Xử lý nhánh con bên trái
            if (node1.left == null && node2.left != null) {
                node1.left = node2.left;
            } else if (node1.left != null && node2.left != null) {
                queue.offer(new TreeNode[]{node1.left, node2.left});
            }

            // Xử lý nhánh con bên phải
            if (node1.right == null && node2.right != null) {
                node1.right = node2.right;
            } else if (node1.right != null && node2.right != null) {
                queue.offer(new TreeNode[]{node1.right, node2.right});
            }
        }

        return root1;
    }
}
```
- **Thời gian:** $\mathcal{O}(\min(N, M))$.
- **Không gian:** $\mathcal{O}(W)$ với $W$ là bề rộng tối đa của phần giao thoa cây.

---

## ⚠️ Các trường hợp biên quan trọng (Edge Cases)

| Trường hợp | Ví dụ | Cách thuật toán xử lý |
| :--- | :--- | :--- |
| **Một trong hai cây rỗng** | `root1 = null, root2 = [1, 2]` | `root1 == null` trả về ngay `root2` trong $\mathcal{O}(1)$. |
| **Cả hai cây đều rỗng** | `root1 = null, root2 = null` | Trả về `null`. |
| **Hai cây có hình dạng không giao nhau** | `root1` chỉ có con trái, `root2` chỉ có con phải | Tại gốc tính tổng, nhánh trái gán từ `root1`, nhánh phải gán từ `root2`. |
| **Cây suy biến (Skewed Tree)** | Cả hai cây là chuỗi node lệch trái dài | Ngăn xếp đệ quy đạt độ sâu tối đa $\min(N, M)$, thuật toán chạy tương tự gộp hai danh sách liên kết. |
| **Giá trị âm hoặc đối xứng** | `root1.val = -5, root2.val = 5` | Tổng bằng `0`, vẫn là giá trị hợp lệ của node theo ràng buộc đề bài. |

---

## 📊 Bảng so sánh các phương pháp

| Tiêu chí | ⭐ Đệ quy Tái sử dụng Node (In-place DFS) | Đệ quy Tạo cây mới (Immutable DFS) | Duyệt lặp Hàng đợi (Iterative BFS) |
| :--- | :---: | :---: | :---: |
| **Thời gian (Time)** | $\mathcal{O}(\min(N, M))$ | $\mathcal{O}(N + M)$ | $\mathcal{O}(\min(N, M))$ |
| **Không gian (Space)** | $\mathcal{O}(H)$ stack depth | $\mathcal{O}(N + M) + \mathcal{O}(H)$ | $\mathcal{O}(W)$ queue size |
| **Tốc độ thực tế** | **0 ms (Top 100%)** | ~1 - 2 ms | ~1 - 3 ms |
| **Biến đổi đầu vào** | Có (sửa đổi trực tiếp trên `root1`) | Không (giữ nguyên cả 2 cây) | Có (sửa đổi trực tiếp trên `root1`) |
| **Nguy cơ tràn ngăn xếp** | Có (nếu cây quá sâu $> 10^4$) | Có | **Không (an toàn tuyệt đối)** |
| **Độ phức tạp code** | **Rất ngắn gọn (6 dòng)** | Dễ hiểu nhưng tốn bộ nhớ | Dài hơn, cần quản lý Queue |

---

## 💡 Mẹo & Lời khuyên khi phỏng vấn (Interview Tips)

1. **Chủ động làm rõ câu hỏi về tính toàn vẹn dữ liệu (Immutability):**
   - Khi phỏng vấn, sau khi trình bày cách In-place (sửa trực tiếp `root1`), hãy luôn chủ động hỏi người phỏng vấn: *"Are we allowed to mutate the input tree `root1`, or do you prefer a non-destructive solution that creates a new tree?"*
   - Điều này thể hiện tư duy lập trình chuyên nghiệp và hiểu biết sâu sắc về clean code / thiết kế hệ thống.
2. **Nắm vững kỹ thuật "Gán cây con trong $\mathcal{O}(1)$":**
   - Điểm tinh tế nhất của bài toán là `if (root1 == null) return root2;`. Khi một cây kết thúc sớm, ta không cần phải duyệt qua các node con của cây kia mà chỉ cần móc con trỏ (pointer/reference) sang cây đó.
3. **Các bài toán liên quan nên luyện tập:**
   - [LeetCode 100: Same Tree](https://leetcode.com/problems/same-tree/)
   - [LeetCode 101: Symmetric Tree](https://leetcode.com/problems/symmetric-tree/)
   - [LeetCode 226: Invert Binary Tree](https://leetcode.com/problems/invert-binary-tree/)
   - [LeetCode 572: Subtree of Another Tree](https://leetcode.com/problems/subtree-of-another-tree/)
