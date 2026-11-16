# 📚 Giải thích Chi tiết Bài 687 - Longest Univalue Path

**Mục tiêu:** Cho gốc của một cây nhị phân, hãy tìm độ dài lớn nhất (tính theo **số cạnh**) của một đường đi mà tất cả các nút trên đường đi đó đều có **cùng một giá trị**. Đường đi này có thể đi qua hoặc không đi qua nút gốc.

---

## 🎯 Phân tích bài toán

### 1. Định nghĩa đường đi trong Cây nhị phân
- Một **đường đi (path)** trong cây là chuỗi các nút liên tiếp nối với nhau qua các cạnh cha - con sao cho không có nút nào bị đi qua hai lần.
- **Độ dài đường đi** được tính bằng **số cạnh** nối giữa các nút:
  $$\text{Độ dài} = (\text{Số lượng nút trên đường đi}) - 1$$
- Đường đi này có thể nằm hoàn toàn trong một cây con hoặc đi vòng qua một nút làm đỉnh uốn cong.

---

### 2. Mô hình đỉnh uốn cong (Apex Node / Turning Point)
Đây là dạng bài toán kinh điển tương tự như **LeetCode 543 (Diameter of Binary Tree)** và **LeetCode 124 (Binary Tree Maximum Path Sum)**:
- Mọi đường đi hợp lệ trong cây nhị phân đều có duy nhất một **nút đỉnh (apex node)** – là nút có độ sâu nhỏ nhất trên đường đi đó (nút cao nhất).
- Từ đỉnh này, đường đi phân thành tối đa 2 nhánh đi xuống:
  - Một nhánh đi xuống cây con bên trái (gồm các nút có cùng giá trị với nút đỉnh).
  - Một nhánh đi xuống cây con bên phải (gồm các nút có cùng giá trị với nút đỉnh).
- Nếu ta duyệt qua mọi nút trong cây và coi mỗi nút là một "đỉnh uốn cong" tiềm năng, ta sẽ bao phủ được **100% tất cả các đường đi có thể có**.

---

### 3. Sự khác biệt giữa "Kỷ lục tại nút" và "Giá trị trả về cho nút cha"
Đây là điểm mấu chốt quan trọng nhất khi thiết kế hàm đệ quy DFS:
1. **Tại nút hiện tại `node` (đỉnh uốn cong):**
   - Đường đi có thể nối cả hai phía: rẽ trái rồi vòng sang phải.
   - Tổng số cạnh của đường đi uốn cong này là:
     $$\text{currentPath} = \text{leftPath} + \text{rightPath}$$
   - Ta dùng giá trị này để cập nhật biến kỷ lục toàn cục `maxLen = Math.max(maxLen, currentPath)`.
2. **Giá trị trả về cho nút cha:**
   - Nút cha của `node` chỉ có thể mở rộng đường đi bằng cách đi qua `node` rồi tiếp tục đi xuống **duy nhất một trong hai nhánh** (trái hoặc phải).
   - Nút cha **không thể** đi qua cả nhánh trái lẫn nhánh phải của `node` vì như vậy đường đi sẽ bị phân nhánh hình chữ Y (không còn là một đường đi đơn giản).
   - Vì vậy, hàm đệ quy chỉ được phép trả về độ dài của nhánh đơn dài nhất:
     $$\text{return } \max(\text{leftPath}, \text{rightPath})$$

---

### 4. Ràng buộc & Đánh giá quy mô (Constraints)
- Số lượng nút trong cây: $N \in [0, 10^4]$.
- Giá trị nút: $-1000 \le Node.val \le 1000$.
- Chiều sâu của cây không vượt quá $1000$.
- Thuật toán DFS Hậu thứ tự (Post-order Traversal) duyệt qua mỗi nút đúng 1 lần sẽ đạt độ phức tạp $\mathcal{O}(N)$ thời gian và $\mathcal{O}(H)$ bộ nhớ ngăn xếp, xử lý tức thì cây $10^4$ nút ($< 3\text{ ms}$).

---

## 🔍 Các hướng tiếp cận (Approaches Comparison)

| Tiêu chí | Cách 1: DFS hai tầng (Top-Down DFS) | Cách 2: Đệ quy Hậu thứ tự kết hợp Bottom-Up (Post-order DFS ⭐) |
| :--- | :--- | :--- |
| **Ý tưởng** | Với mỗi nút, chạy thêm một hàm DFS phụ để tìm đường đi dài nhất tỏa ra từ nút đó | Duyệt từ dưới lên (Bottom-Up). Mỗi nút nhận kết quả từ 2 con, cập nhật kỷ lục toàn cục rồi trả về nhánh dài nhất cho cha |
| **Độ phức tạp Thời gian** | $\mathcal{O}(N^2)$ (trường hợp cây thoái hóa) | $\mathcal{O}(N)$ (mỗi nút thăm đúng 1 lần) |
| **Độ phức tạp Bộ nhớ** | $\mathcal{O}(H)$ | $\mathcal{O}(H)$ |
| **Đánh giá** | Trùng lặp tính toán, dễ TLE | **Tối ưu tuyệt đối, chuẩn mực phỏng vấn Big Tech** |

---

## 📐 Chứng minh tính đúng đắn (Correctness Proof)

### Bổ đề:
Bằng phương pháp duyệt Hậu thứ tự (Post-order), tại mỗi nút `node`:
1. Hàm `dfs(node)` tính toán chính xác độ dài nhánh đơn dài nhất đi xuống từ `node` mà các nút đều có giá trị bằng `node.val`.
2. Kỷ lục toàn cục `maxLen` được cập nhật với mọi đường đi univalue có thể có.

**Chứng minh:**
- **Trường hợp cơ sở:** Nếu `node == null`, không có cạnh nào, hàm trả về 0. Điều này hiển nhiên đúng.
- **Bước quy nạp:** Giả sử các lời gọi `dfs(node.left)` và `dfs(node.right)` đã trả về đúng độ dài nhánh đơn của con trái (`left`) và con phải (`right`).
  - Nếu `node.left != null && node.left.val == node.val`: cạnh nối `node -> node.left` là hợp lệ, nhánh trái mở rộng thêm 1 cạnh: `leftPath = left + 1`. Ngược lại, giá trị khác nhau nên không thể nối tiếp: `leftPath = 0`.
  - Tương tự cho nhánh phải: `rightPath = right + 1` nếu cùng giá trị, ngược lại bằng 0.
  - Đường đi univalue dài nhất có `node` là đỉnh uốn cong có độ dài đúng bằng `leftPath + rightPath`. Bằng cách cập nhật `maxLen = Math.max(maxLen, leftPath + rightPath)` tại mọi nút, ta đã duyệt qua tất cả các đỉnh uốn cong tiềm năng của cây.
  - Để cung cấp cho nút cha của `node`, chỉ có một nhánh đơn đi xuống có thể được chọn, do đó giá trị trả về $\max(\text{leftPath}, \text{rightPath})$ là tối ưu và chính xác. ∎

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): DFS Hậu Thứ Tự (Post-Order DFS)

### 💡 Thuật toán chi tiết

1. Khởi tạo biến toàn cục `maxLen = 0`.
2. Gọi hàm đệ quy `dfs(root)`.
3. Trong hàm `dfs(node)`:
   - Nếu `node == null`, trả về `0`.
   - Đệ quy sang hai cây con:
     - `int left = dfs(node.left);`
     - `int right = dfs(node.right);`
   - Khởi tạo `leftPath = 0`, `rightPath = 0`.
   - Nếu `node.left != null && node.left.val == node.val`:
     - `leftPath = left + 1;`
   - Nếu `node.right != null && node.right.val == node.val`:
     - `rightPath = right + 1;`
   - Cập nhật kỷ lục:
     - `maxLen = Math.max(maxLen, leftPath + rightPath);`
   - Trả về nhánh đơn dài nhất:
     - `return Math.max(leftPath, rightPath);`
4. Trả về `maxLen`.

---

### 📊 Minh họa trực quan (Visual Walkthrough)

#### Ví dụ 1: `root = [5, 4, 5, 1, 1, null, 5]`

```text
            5 (root)
          /   \
         4     5
        / \     \
       1   1     5

Tại cây con bên trái:
- Nút 1 (lá): trả về 0.
- Nút 4: có 2 con là 1 != 4 -> leftPath = 0, rightPath = 0.
  -> maxLen = max(0, 0 + 0) = 0.
  -> Trả về cho root: 0.

Tại cây con bên phải:
- Nút 5 (lá ở đáy): trả về 0.
- Nút 5 (con của root):
  + Con phải có val == 5 -> rightPath = 0 + 1 = 1.
  + maxLen = max(0, 0 + 1) = 1.
  + Trả về cho root: max(0, 1) = 1.

Tại nút gốc 5:
- Con trái có val = 4 != 5 -> leftPath = 0.
- Con phải có val = 5 == 5 -> rightPath = 1 + 1 = 2.
- Cập nhật kỷ lục tại gốc: maxLen = max(1, 0 + 2) = 2.
- Đường đi tìm được: 5 (root) -> 5 -> 5 (gồm 2 cạnh).

👉 Kết quả: maxLen = 2.
```

---

## 💻 Cài đặt Thuật toán Chi tiết (Java Implementation)

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
    private int maxLen = 0;

    public int longestUnivaluePath(TreeNode root) {
        maxLen = 0;
        dfs(root);
        return maxLen;
    }

    private int dfs(TreeNode node) {
        if (node == null) {
            return 0;
        }

        // Đệ quy tìm độ dài nhánh đơn cùng giá trị lớn nhất từ cây con trái và phải
        int left = dfs(node.left);
        int right = dfs(node.right);

        int leftPath = 0;
        int rightPath = 0;

        // Nếu nút con bên trái có cùng giá trị với nút hiện tại
        if (node.left != null && node.left.val == node.val) {
            leftPath = left + 1;
        }

        // Nếu nút con bên phải có cùng giá trị với nút hiện tại
        if (node.right != null && node.right.val == node.val) {
            rightPath = right + 1;
        }

        // Cập nhật kỷ lục đường đi uốn cong tại đỉnh `node` (nối cả nhánh trái và phải)
        maxLen = Math.max(maxLen, leftPath + rightPath);

        // Trả về cho nút cha nhánh đơn dài nhất có thể kéo dài lên
        return Math.max(leftPath, rightPath);
    }
}
```

---

## 🔍 Giải thích từng dòng lệnh chi tiết (Line-by-Line Walkthrough)

1. **`private int maxLen = 0;`**:
   - Biến toàn cục theo dõi độ dài đường đi univalue lớn nhất tìm thấy trong toàn bộ cây. Khởi tạo bằng 0 (trường hợp cây rỗng hoặc chỉ có 1 nút thì không có cạnh nào).
2. **`int left = dfs(node.left); int right = dfs(node.right);`**:
   - Thuật toán Bottom-Up bắt buộc phải thăm và lấy kết quả từ hai cây con trước khi xử lý nút hiện tại (Post-order Traversal).
3. **`if (node.left != null && node.left.val == node.val) leftPath = left + 1;`**:
   - Kiểm tra nếu nút con trái trùng giá trị với `node`, cạnh nối giữa chúng hợp lệ $\implies$ độ dài nhánh trái tăng thêm 1. Nếu khác giá trị, chuỗi univalue bị đứt gãy $\implies$ `leftPath` giữ nguyên là 0.
4. **`maxLen = Math.max(maxLen, leftPath + rightPath);`**:
   - Đoạn đường đi qua `node` kết hợp cả nhánh trái và phải có tổng số cạnh là `leftPath + rightPath`. Cập nhật kỷ lục nếu giá trị này lớn hơn `maxLen`.
5. **`return Math.max(leftPath, rightPath);`**:
   - Trả về cho nút cha nhánh đơn dài nhất để nút cha có thể tiếp tục mở rộng đường đi lên phía trên nếu nút cha cũng có cùng giá trị.

---

## ⏱️ Đánh giá Độ phức tạp (Complexity Analysis)

### Độ phức tạp Thời gian (Time Complexity)
- **$\mathcal{O}(N)$**:
  - Với $N$ là tổng số nút trong cây nhị phân ($N \le 10^4$).
  - Thuật toán DFS duyệt qua mỗi nút đúng một lần duy nhất. Tại mỗi nút chỉ thực hiện các phép so sánh số nguyên và phép gán hằng số $\mathcal{O}(1)$.
  - Thời gian chạy trên LeetCode chỉ $\approx 1-2\text{ ms}$, đạt hiệu năng tối đa.

### Độ phức tạp Không gian (Space Complexity)
- **$\mathcal{O}(H)$**:
  - Với $H$ là chiều cao của cây nhị phân ($H \le 1000$).
  - Không sử dụng thêm cấu trúc dữ liệu bổ trợ nào.
  - Bộ nhớ phụ chỉ tiêu tốn cho ngăn xếp đệ quy (call stack):
    - Trường hợp tốt nhất (cây cân bằng): $H = \mathcal{O}(\log N)$.
    - Trường hợp xấu nhất (cây thoái hóa thành đường thẳng): $H = \mathcal{O}(N)$, nhưng đề bài đảm bảo chiều sâu cây không quá 1000, do đó không lo gặp lỗi `StackOverflowError`.

---

## 💡 Bài học kinh nghiệm & Mẹo phỏng vấn (Key Takeaways & Interview Tips)

1. **Bộ 3 bài toán kinh điển về Đường đi trong Cây nhị phân (Tree Path Trilogy):**
   - **LeetCode 543 - Diameter of Binary Tree:** Tìm đường đi dài nhất giữa hai nút bất kỳ (không quan tâm giá trị).
   - **LeetCode 124 - Binary Tree Maximum Path Sum:** Tìm đường đi có tổng giá trị nút lớn nhất (Hard).
   - **LeetCode 687 - Longest Univalue Path:** Tìm đường đi dài nhất có cùng giá trị (Medium).
   - **Cả 3 bài đều có chung một khuôn mẫu thiết kế:**
     - Duyệt Bottom-Up (Post-order DFS).
     - Cập nhật kỷ lục đường đi uốn cong tại nút đỉnh: `left + right`.
     - Chỉ trả về nhánh đơn dài nhất cho nút cha: `max(left, right)`.
2. **Lưu ý đơn vị tính:**
   - Đề bài yêu cầu tính **số cạnh (number of edges)** chứ không phải số nút. Vì vậy một nút đơn lẻ trả về 0 cạnh, và mỗi bước nối lên cha chỉ cộng thêm 1.
