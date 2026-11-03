# 📚 Giải thích Chi tiết Bài 669 - Trim a Binary Search Tree

**Mục tiêu:** Cho nút gốc `root` của một cây tìm kiếm nhị phân (Binary Search Tree - BST) và hai ranh giới dưới và trên là `low` và `high`. Hãy cắt tỉa (trim) cây sao cho tất cả các giá trị của các nút còn lại đều nằm trong đoạn đóng $[low, high]$. Việc cắt tỉa không được làm thay đổi cấu trúc tương đối của các phần tử được giữ lại (nút con cháu của một nút nếu còn lại thì vẫn phải là con cháu của nút đó). Đề bài đảm bảo rằng kết quả là duy nhất. Trả về nút gốc của cây sau khi đã được cắt tỉa.

---

## 🎯 Phân tích bài toán

### 1. Đặc tính Bất biến của Cây Tìm Kiếm Nhị Phân (BST Invariant)

Trong một cây tìm kiếm nhị phân hợp lệ, với mọi nút $u$:
- Mọi nút $v$ thuộc **cây con bên trái** của $u$ đều thỏa mãn: $v.val < u.val$.
- Mọi nút $w$ thuộc **cây con bên phải** của $u$ đều thỏa mãn: $w.val > u.val$.

Đây là tính chất vàng cho phép ta đưa ra quyết định cắt bỏ **toàn bộ một nhánh cây** trong thời gian $\mathcal{O}(1)$ mà không cần duyệt sâu vào từng nút bên trong nhánh đó!

---

### 2. Bản chất của Thao tác "Cắt tỉa" (Pruning vs Rebuilding)

- Đề bài yêu cầu: *"Trimming the tree should not change the relative structure of the elements that will remain in the tree (i.e., any node's descendant should remain a descendant)."*
- Điều này có nghĩa: Ta **không được** phá hủy cấu trúc cây cũ để nhặt các phần tử hợp lệ rồi chèn lại từ đầu thành cây mới (rebuild). Thay vào đó, ta phải **giữ nguyên quan hệ cha - con** ban đầu giữa các nút hợp lệ.
- Khi một nút bị loại bỏ, nút con hợp lệ của nó sẽ được "kéo lên" thay thế vị trí của nút cha đã bị xóa.

---

### 3. Ba Trường hợp Quyết định Cắt tỉa (Pruning Decision Rules)

Khi hàm đệ quy đứng tại một nút `root`, ta có 3 trường hợp xảy ra:

#### Trường hợp 1: `root.val < low` (Nút hiện tại quá nhỏ)
- Vì $root.val < low$, theo đặc tính BST:
  $$\forall v \in \text{LeftSubtree}(root) \implies v.val < root.val < low$$
- Toàn bộ cây con bên trái của `root` chắc chắn đều nhỏ hơn `low`, do đó **cả nút `root` lẫn toàn bộ cây con bên trái đều vi phạm và bắt buộc bị loại bỏ**.
- Tuy nhiên, cây con bên phải chứa các giá trị lớn hơn `root.val`, nên **có thể chứa các nút nằm trong khoảng $[low, high]$**.
- Do đó: Kết quả cắt tỉa của cả cây gốc `root` chính là **kết quả cắt tỉa của cây con bên phải**!
  $$\text{trimBST}(root) = \text{trimBST}(root.right)$$

#### Trường hợp 2: `root.val > high` (Nút hiện tại quá lớn)
- Vì $root.val > high$, theo đặc tính BST:
  $$\forall w \in \text{RightSubtree}(root) \implies w.val > root.val > high$$
- Toàn bộ cây con bên phải của `root` chắc chắn đều lớn hơn `high`, do đó **cả nút `root` lẫn toàn bộ cây con bên phải đều vi phạm và bắt buộc bị loại bỏ**.
- Tuy nhiên, cây con bên trái chứa các giá trị nhỏ hơn `root.val`, nên **có thể chứa các nút nằm trong khoảng $[low, high]$**.
- Do đó: Kết quả cắt tỉa của cả cây gốc `root` chính là **kết quả cắt tỉa của cây con bên trái**!
  $$\text{trimBST}(root) = \text{trimBST}(root.left)$$

#### Trường hợp 3: `low <= root.val <= high` (Nút hiện tại hợp lệ)
- Nút `root` nằm trọn vẹn trong khoảng giá trị cho phép $\implies$ **Giữ lại nút `root`**.
- Ta chỉ cần đệ quy xuống cắt tỉa tiếp hai nhánh con của nó:
  $$root.left = \text{trimBST}(root.left, low, high)$$
  $$root.right = \text{trimBST}(root.right, low, high)$$
- Sau khi gắn hai cây con đã được cắt tỉa, ta trả về chính `root`.

---

### 4. Ràng buộc & Đánh giá quy mô (Constraints)

- Số lượng nút: $1 \le N \le 10^4$.
- $0 \le Node.val \le 10^4$.
- $0 \le low \le high \le 10^4$.
- Chiều cao $H$ của cây có thể dao động từ $\mathcal{O}(\log N)$ (cây cân bằng hoàn hảo) đến $\mathcal{O}(N)$ (cây suy biến thành một đường thẳng).
- Thuật toán duyệt qua mỗi nút tối đa 1 lần với độ phức tạp $\mathcal{O}(N)$ sẽ thực thi trong chưa tới $1 \text{ ms}$, đạt hiệu năng $0 \text{ ms}$ (Top 100%) tuyệt đối.

---

## 🔍 Các hướng tiếp cận (Approaches Comparison)

| Tiêu chí | Cách 1: Duyệt mảng rồi dựng lại cây (In-order Rebuild) | Cách 2: Đệ quy Cắt tỉa DFS (Recursive Pruning ⭐) | Cách 3: Lặp Cắt tỉa Hai con trỏ (Iterative Pruning) |
| :--- | :--- | :--- | :--- |
| **Ý tưởng** | Duyệt cây lấy các giá trị trong $[low, high]$ rồi dựng cây BST mới | Dùng đệ quy DFS loại bỏ nhánh vi phạm và kéo nhánh hợp lệ lên | Tìm root hợp lệ bằng vòng lặp, sau đó lần lượt tỉa nhánh trái và phải |
| **Bảo toàn cấu trúc** | ❌ Dễ làm biến đổi cấu trúc tương đối ban đầu | ✅ Giữ nguyên cấu trúc tương đối 100% | ✅ Giữ nguyên cấu trúc tương đối 100% |
| **Độ phức tạp Thời gian** | $\mathcal{O}(N \log N)$ hoặc $\mathcal{O}(N)$ | $\mathcal{O}(N)$ - Mỗi nút duyệt tối đa 1 lần | $\mathcal{O}(N)$ - Mỗi nút duyệt tối đa 1 lần |
| **Độ phức tạp Bộ nhớ** | $\mathcal{O}(N)$ mảng phụ | $\mathcal{O}(H)$ ngăn xếp đệ quy | $\mathcal{O}(1)$ bộ nhớ phụ trợ |
| **Đánh giá** | Dài dòng, sai yêu cầu bảo toàn cấu trúc | Ngắn gọn, thanh lịch, chuẩn mực phỏng vấn | Phù hợp khi người phỏng vấn hỏi follow-up chống tràn stack |

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Đệ Quy Cắt Tỉa Dựa Trên Đặc Tính BST (Recursive BST Pruning)

### 💡 Ý tưởng thuật toán cốt lõi (Core Insights)

Tận dụng tính đệ quy tự nhiên của cấu trúc cây:
1. **Điểm dừng (Base case):** Nếu cây rỗng (`root == null`), trả về `null`.
2. **Cắt tỉa nhánh trái:** Nếu `root.val < low`, bỏ qua nút `root` và cây con trái, trả về kết quả đệ quy của `root.right`.
3. **Cắt tỉa nhánh phải:** Nếu `root.val > high`, bỏ qua nút `root` và cây con phải, trả về kết quả đệ quy của `root.left`.
4. **Giữ nút và đệ quy 2 phía:** Nếu `low <= root.val <= high`, tiếp tục gọi đệ quy gán vào `root.left` và `root.right`, sau đó trả về `root`.

---

### 📊 Sơ đồ minh họa trực quan (Visual Walkthrough)

#### Ví dụ 1: `root = [1, 0, 2], low = 1, high = 2`

```text
Cây ban đầu:                Phân tích:                        Cây sau khi tỉa:
      1                       root = 1 in [1, 2] -> GIỮ              1
     / \                      /                \                      \
    0   2        node 0 < 1: BỎ           node 2 in [1, 2]: GIỮ        2
                 (và bỏ toàn bộ nhánh trái)
```

---

#### Ví dụ 2: `root = [3, 0, 4, null, 2, null, null, 1], low = 1, high = 3`

```text
Cây ban đầu:
             3                    [1, 3] -> 3 nằm trong khoảng -> GIỮ
            / \
           0   4                  [1, 3] -> 4 > 3 -> LOẠI BỎ 4 và nhánh phải của nó
            \
             2                    [1, 3] -> 0 < 1 -> LOẠI BỎ 0, KÉO NHÁNH PHẢI (2) LÊN!
            /
           1                      [1, 3] -> 2 nằm trong khoảng -> GIỮ
                                  [1, 3] -> 1 nằm trong khoảng -> GIỮ
```

Quá trình "kéo" nhánh hợp lệ lên khi nút `0` bị loại bỏ:
```text
Bước 1: Xét nút 3 (Hợp lệ)
        3
       / \
      ?   trim(4) -> vì 4 > 3 nên trả về trim(4.left) = trim(null) = null

Bước 2: Xét cây con trái của 3: nút 0
        Vì 0 < 1, bỏ nút 0 và trả về kết quả của nhánh phải: trim(2)

Bước 3: Xét nút 2 (Hợp lệ)
        2.left = trim(1) = 1 (Hợp lệ)
        2.right = trim(null) = null
        => Nhánh con này trở thành:
             2
            /
           1

Bước 4: Gắn lại vào nút 3 ban đầu:
             3
            /
           2
          /
         1
```

---

## 🔄 Từng bước thực thi (Dry-run / Trace qua Ví dụ cụ thể)

### Ví dụ 1: `root = [1, 0, 2], low = 1, high = 2`

1. Gọi `trimBST(node 1, 1, 2)`:
   - $1 \in [1, 2] \implies$ Giữ `node 1`.
   - `node 1.left = trimBST(node 0, 1, 2)`:
     - Tại `node 0`: $0 < 1$ (`root.val < low`).
     - Bỏ qua `node 0`, trả về `trimBST(0.right = null, 1, 2) = null`.
     - $\implies node 1.left = null$.
   - `node 1.right = trimBST(node 2, 1, 2)`:
     - Tại `node 2`: $2 \in [1, 2] \implies$ Giữ `node 2`.
     - `node 2.left = trimBST(null) = null`.
     - `node 2.right = trimBST(null) = null`.
     - Trả về `node 2`.
     - $\implies node 1.right = node 2$.
2. Trả về `node 1`.
3. Cây kết quả: `[1, null, 2]`.

---

### Ví dụ 2: `root = [3, 0, 4, null, 2, null, null, 1], low = 1, high = 3`

1. Tại gốc `node 3`: $3 \in [1, 3] \implies$ Giữ `node 3`.
2. Tính `node 3.right = trimBST(node 4, 1, 3)`:
   - Tại `node 4`: $4 > 3$ (`root.val > high`).
   - Bỏ `node 4`, trả về `trimBST(4.left = null, 1, 3) = null`.
   - $\implies node 3.right = null$.
3. Tính `node 3.left = trimBST(node 0, 1, 3)`:
   - Tại `node 0`: $0 < 1$ (`root.val < low`).
   - Bỏ qua `node 0`, trả về `trimBST(0.right = node 2, 1, 3)`:
     - Tại `node 2`: $2 \in [1, 3] \implies$ Giữ `node 2`.
     - `node 2.left = trimBST(node 1, 1, 3)`:
       - Tại `node 1`: $1 \in [1, 3] \implies$ Giữ `node 1`.
       - `1.left = null`, `1.right = null`.
       - Trả về `node 1`.
     - `node 2.right = trimBST(null) = null`.
     - Trả về `node 2`.
   - $\implies node 3.left = node 2$.
4. Trả về `node 3`.
5. Cây kết quả: `[3, 2, null, 1]`.

---

### Ví dụ 3 (Trường hợp Biên): Toàn bộ cây nằm ngoài khoảng $[low, high]$

Giả sử cây có các giá trị $\{5, 6, 7\}$ và $[low, high] = [1, 3]$:
- Tại gốc `node 6`: $6 > 3 \implies$ Bỏ `6` và nhánh phải, trả về `trimBST(node 5)`.
- Tại `node 5`: $5 > 3 \implies$ Bỏ `5` và nhánh phải, trả về `trimBST(node 5.left = null) = null`.
- Kết quả cuối cùng trả về `null` (cây rỗng) chính xác tuyệt đối.

---

## 💻 Cài đặt Thuật toán Chi tiết (Java Implementation)

### 1. Phiên bản Đệ quy Chuẩn (Standard Recursive DFS - Khuyên dùng)

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
     * Thuật toán Tối ưu: Đệ quy Cắt tỉa Nhánh dựa trên Đặc tính BST (Recursive BST Pruning)
     * 
     * Độ phức tạp:
     * - Thời gian: O(N) - Mỗi nút trong cây được ghé thăm tối đa 1 lần.
     * - Không gian: O(H) - Độ sâu ngăn xếp đệ quy bằng chiều cao của cây (O(log N) với BST cân bằng, O(N) với cây suy biến).
     */
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null) {
            return null;
        }

        // Nếu giá trị nút nhỏ hơn ngưỡng dưới:
        // Cả nút này và toàn bộ cây con bên trái đều < low -> Bỏ, chỉ xét tiếp cây con phải
        if (root.val < low) {
            return trimBST(root.right, low, high);
        }

        // Nếu giá trị nút lớn hơn ngưỡng trên:
        // Cả nút này và toàn bộ cây con bên phải đều > high -> Bỏ, chỉ xét tiếp cây con trái
        if (root.val > high) {
            return trimBST(root.left, low, high);
        }

        // Nếu low <= root.val <= high:
        // Nút hiện tại hợp lệ, đệ quy cắt tỉa hai cây con trái và phải
        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);

        return root;
    }
}
```

---

### 2. Phiên bản Lặp Tối ưu Bộ nhớ $\mathcal{O}(1)$ (Iterative Solution for Follow-up)

> [!TIP]
> Trong phỏng vấn, sau khi viết xong phiên bản đệ quy, người phỏng vấn thường hỏi câu hỏi follow-up: *"Nếu cây bị suy biến thành danh sách liên kết với độ sâu $10^4$, ngăn xếp đệ quy có thể bị tràn bộ nhớ (Stack Overflow). Bạn có thể giải quyết bài toán mà không tốn thêm bộ nhớ $\mathcal{O}(1)$ auxiliary space không?"*
> 
> Dưới đây là giải pháp lặp không dùng ngăn xếp (Iterative):

```java
class SolutionIterative {
    public TreeNode trimBST(TreeNode root, int low, int high) {
        // Bước 1: Tìm nút gốc mới hợp lệ đầu tiên (nằm trong khoảng [low, high])
        while (root != null && (root.val < low || root.val > high)) {
            if (root.val < low) {
                root = root.right;
            } else {
                root = root.left;
            }
        }

        if (root == null) {
            return null;
        }

        // Bước 2: Cắt tỉa nhánh con bên trái của root mới
        // Các nút trong nhánh trái luôn <= root.val <= high, nên chỉ cần kiểm tra xem có < low không
        TreeNode curr = root;
        while (curr != null) {
            while (curr.left != null && curr.left.val < low) {
                // Nhảy cóc qua nút vi phạm và toàn bộ nhánh trái của nó
                curr.left = curr.left.right;
            }
            curr = curr.left;
        }

        // Bước 3: Cắt tỉa nhánh con bên phải của root mới
        // Các nút trong nhánh phải luôn >= root.val >= low, nên chỉ cần kiểm tra xem có > high không
        curr = root;
        while (curr != null) {
            while (curr.right != null && curr.right.val > high) {
                // Nhảy cóc qua nút vi phạm và toàn bộ nhánh phải của nó
                curr.right = curr.right.left;
            }
            curr = curr.right;
        }

        return root;
    }
}
```

---

## 🔍 Giải thích từng dòng lệnh chi tiết (Line-by-Line Walkthrough)

1. `if (root == null) return null;`
   - Điều kiện cơ sở (Base case): Một cây rỗng sau khi cắt tỉa vẫn là cây rỗng.
2. `if (root.val < low) return trimBST(root.right, low, high);`
   - Nhờ tính chất BST, mọi nút ở `root.left` đều có giá trị $< root.val < low$.
   - Do đó, ta loại bỏ luôn cả `root` và `root.left`. Nghiệm duy nhất có thể có nằm ở cây con bên phải `root.right`.
3. `if (root.val > high) return trimBST(root.left, low, high);`
   - Tương tự, mọi nút ở `root.right` đều có giá trị $> root.val > high$.
   - Ta loại bỏ cả `root` và `root.right`. Nghiệm duy nhất có thể có nằm ở cây con bên trái `root.left`.
4. `root.left = trimBST(root.left, low, high);`
   - Khi `root.val` thỏa mãn $low \le root.val \le high$, nút này được giữ lại.
   - Ta đệ quy cắt tỉa nhánh trái và cập nhật con trỏ `root.left` trỏ tới gốc cây con trái sau khi tỉa.
5. `root.right = trimBST(root.right, low, high);`
   - Đệ quy cắt tỉa nhánh phải và cập nhật con trỏ `root.right`.
6. `return root;`
   - Trả về nút gốc đã được cập nhật liên kết.

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### 1. Độ phức tạp Thời gian (Time Complexity): $\mathcal{O}(N)$
- Mỗi nút trong cây được xét tối đa một lần.
- Tại mỗi nút, các phép so sánh số nguyên (`root.val < low`, `root.val > high`) và phép gán con trỏ đều diễn ra trong thời gian hằng số $\mathcal{O}(1)$.
- Khi loại bỏ một nhánh (trái hoặc phải), ta không cần duyệt qua các phần tử bị loại bỏ trong nhánh đó.
- Do đó, tổng thời gian duyệt là $\mathcal{O}(N)$, với $N \le 10^4$ thuật toán chạy trong **$0 \text{ ms}$**.

### 2. Độ phức tạp Không gian (Space Complexity): $\mathcal{O}(H)$
- Không gian phụ trợ chính là độ sâu của ngăn xếp gọi hàm đệ quy (Call Stack).
- Độ sâu tối đa của ngăn xếp bằng chiều cao $H$ của cây:
  - Trường hợp tốt nhất / trung bình (Cây cân bằng): $H = \mathcal{O}(\log N)$.
  - Trường hợp xấu nhất (Cây suy biến thành 1 đường thẳng): $H = \mathcal{O}(N)$.
- *Lưu ý:* Với phiên bản lặp (Iterative), không gian phụ trợ là $\mathcal{O}(1)$ tuyệt đối vì không dùng ngăn xếp hay mảng phụ.

---

## 💡 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways & Interview Tips)

### 1. Luôn khai thác Tính chất BST thay vì coi nó là Cây Nhị Phân thông thường
- Nếu bài toán cho cây nhị phân bình thường (Binary Tree), khi một nút vi phạm, ta phải tìm kiếm ở cả hai nhánh con trái và phải.
- Nhưng vì đề bài cho **BST**, giá trị của nút hiện tại sẽ quyết định dứt khoát nhánh nào chắc chắn vi phạm để loại bỏ cả nhánh ngay lập tức. Đây chính là điểm phân biệt giữa một lập trình viên hiểu cấu trúc dữ liệu và người giải bài máy móc.

### 2. Tư duy Kéo nhánh con (Subtree Promotion)
- Rất nhiều người học bị bối rối khi phải "xóa một nút trong cây": lo lắng về việc nối lại con trỏ thế nào, chọn nút nào thay thế.
- Trong bài này, vì cả `root` và toàn bộ một nhánh con bị loại bỏ, nhánh con còn lại tự nhiên trở thành nhánh kế thừa trực tiếp. Do đó, ta chỉ cần gán thẳng `return trimBST(root.right)` hoặc `return trimBST(root.left)`.
