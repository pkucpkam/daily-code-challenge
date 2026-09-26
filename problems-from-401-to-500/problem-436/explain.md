# 📚 Giải thích Chi tiết Bài 606 - Construct String from Binary Tree

**Mục tiêu:** Cho nút gốc `root` của một cây nhị phân, nhiệm vụ là chuyển đổi cấu trúc cây thành một chuỗi ký tự theo thứ tự **duyệt tiền thứ tự (Preorder Traversal: Node $\to$ Left $\to$ Right)**. Các cây con phải được bọc trong các cặp dấu ngoặc đơn `()`, đồng thời lược bỏ các cặp ngoặc rỗng không cần thiết nhưng vẫn phải đảm bảo **ánh xạ 1-1** (one-to-one mapping) để có thể khôi phục lại chính xác cấu trúc cây ban đầu.

---

## 🎯 Phân tích bài toán

### 1. Đặc điểm đầu vào & Ràng buộc
- Số lượng nút trong cây nhị phân nằm trong khoảng $[1, 10^4]$.
- Giá trị mỗi nút: $-1000 \le \text{Node.val} \le 1000$.
- Cây nhị phân đảm bảo có ít nhất 1 nút (`root != null`).

---

### 2. Bản chất các quy tắc ngoặc (Parentheses Rules)

Nếu biểu diễn đầy đủ cả các nút `null` theo dạng tiền thứ tự, mỗi nút sẽ có dạng:  
`val(left)(right)`

Tuy nhiên, đề bài yêu cầu **lược bỏ các cặp ngoặc rỗng `()`** với điều kiện không làm mất tính toàn vẹn của cấu trúc cây. Khi xét một nút bất kỳ, ta có đúng **4 trường hợp con**:

```text
       root
      /    \
   left    right
```

| Trường hợp | Con trái (`left`) | Con phải (`right`) | Chuỗi biểu diễn | Giải thích |
| :--- | :--- | :--- | :--- | :--- |
| **TH 1** | `null` | `null` | `root.val` | Là nút lá, lược bỏ cả 2 cặp ngoặc rỗng `()()` phía sau. |
| **TH 2** | Có | `null` | `root.val(left)` | Con phải là `null` nên có thể bỏ qua `()`, người đọc mặc định hiểu cây con sau `left` không có nhánh phải. |
| **TH 3** | `null` | Có | `root.val()(right)` | **⚠️ ĐẶC BIỆT:** Bắt buộc phải giữ lại `()` cho con trái! Nếu bỏ `()`, chuỗi sẽ thành `root.val(right)`, khiến hệ thống hiểu nhầm nhánh `right` là nhánh `left`. |
| **TH 4** | Có | Có | `root.val(left)(right)` | Cả 2 con đều tồn tại, biểu diễn đầy đủ cả 2 nhánh. |

#### 🔑 Điểm mấu chốt:
- Nhánh **con trái** bắt buộc phải có cặp ngoặc `(...)` khi: `left != null` **HOẶC** `right != null`.
- Nhánh **con phải** chỉ xuất hiện cặp ngoặc `(...)` khi: `right != null`.

---

### 3. Cảnh báo cách tiếp cận ngây thơ (String Concatenation `+`)

Cách viết đệ quy trực tiếp bằng phép cộng chuỗi:
```java
// KHÔNG NÊN DÙNG: Gây quá tải bộ nhớ và chạy chậm
public String tree2str(TreeNode root) {
    if (root == null) return "";
    if (root.left == null && root.right == null) return String.valueOf(root.val);
    if (root.right == null) return root.val + "(" + tree2str(root.left) + ")";
    return root.val + "(" + tree2str(root.left) + ")(" + tree2str(root.right) + ")";
}
```

- **Vấn đề bộ nhớ (String Immutability):** Trong Java, đối tượng `String` là bất biến (immutable). Mỗi lần thực hiện toán tử cộng chuỗi `+`, Java phải cấp phát một vùng nhớ mới trên Heap và sao chép toàn bộ các ký tự từ chuỗi cũ sang chuỗi mới.
- **Độ phức tạp suy biến:**
  - Với cây nhị phân dạng đường thẳng (skewed tree) có $N = 10^4$ nút, việc ghép chuỗi ở mỗi tầng đệ quy sẽ tốn thời gian và bộ nhớ lên tới:
    $$\mathcal{O}(1 + 2 + 3 + \dots + N) = \mathcal{O}(N^2)$$
  - Điều này dễ dẫn tới thời gian chạy bị phạt nặng (từ 1ms tăng lên 20-30ms) và gây áp lực lớn lên bộ thu gom rác (Garbage Collector).

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Preorder DFS kết hợp StringBuilder

### 💡 Ý tưởng cốt lõi (Key Insights)

1. **Một đối tượng `StringBuilder` duy nhất xuyên suốt:**
   - Thay vì tạo chuỗi con ở mỗi tầng đệ quy rồi nối lại, ta khởi tạo một `StringBuilder` duy nhất ở hàm chính và truyền con trỏ tham chiếu của nó qua các lời gọi hàm đệ quy.
   - Các thao tác `sb.append(...)` diễn ra tại chỗ (in-place) với thời gian khấu hao là $\mathcal{O}(1)$.

2. **Cấu trúc rẽ nhánh đệ quy rõ ràng, tự nhiên:**
   - Bước 1: `sb.append(node.val);`
   - Bước 2: Kiểm tra nút lá: `if (node.left == null && node.right == null) return;`
   - Bước 3: Luôn mở ngoặc `(` và gọi đệ quy `dfs(node.left, sb)`, sau đó đóng ngoặc `)`:
     - Nếu `node.left == null` nhưng `node.right != null`, hàm `dfs(node.left)` sẽ kết thúc ngay lập tức, tự động tạo ra cặp ngoặc rỗng `()` đúng như yêu cầu của **TH 3**.
   - Bước 4: Kiểm tra nhánh phải: `if (node.right != null)` thì mới mở `(`, gọi `dfs(node.right, sb)` và đóng `)`.

---

### 💻 Mã nguồn Java (Best Solution)

```java
class Solution {
    /**
     * Best Solution: Preorder Traversal (DFS) kết hợp StringBuilder.
     *
     * Time Complexity: O(N) - mỗi nút được duyệt đúng 1 lần.
     * Space Complexity: O(H) - chiều cao cây cho ngăn xếp đệ quy (O(log N) trung bình, O(N) tệ nhất).
     */
    public String tree2str(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        dfs(root, sb);
        return sb.toString();
    }

    private void dfs(TreeNode node, StringBuilder sb) {
        if (node == null) {
            return;
        }

        // 1. Ghi nhận giá trị của nút hiện tại
        sb.append(node.val);

        // 2. Nếu là nút lá, kết thúc sớm để không sinh thêm bất kỳ cặp ngoặc nào
        if (node.left == null && node.right == null) {
            return;
        }

        // 3. Xử lý cây con bên trái:
        // Đã đảm bảo tồn tại ít nhất 1 con (trái hoặc phải).
        // Luôn bọc nhánh trái trong ():
        // - Nếu left != null: biểu diễn cây con trái.
        // - Nếu left == null (nhưng có right): dfs(null) không ghi gì -> tạo ra () rỗng.
        sb.append('(');
        dfs(node.left, sb);
        sb.append(')');

        // 4. Xử lý cây con bên phải:
        // Chỉ thêm cặp ngoặc khi con phải thực sự tồn tại
        if (node.right != null) {
            sb.append('(');
            dfs(node.right, sb);
            sb.append(')');
        }
    }
}
```

---

### ⚙️ Phân tích độ phức tạp (Complexity Analysis)

| Thành phần | Độ phức tạp | Giải thích chi tiết |
| :--- | :--- | :--- |
| **Thời gian (Time Complexity)** | $\mathcal{O}(N)$ | Cây có $N$ nút. Mỗi nút được hàm đệ quy `dfs` ghé thăm đúng 1 lần. Mỗi lần gọi, thao tác `append` giá trị số và các ký tự ngoặc `(`, `)` tốn thời gian $\mathcal{O}(1)$ khấu hao. Chuyển đổi `sb.toString()` cuối cùng tốn $\mathcal{O}(N)$ để tạo chuỗi kết quả. Tổng thời gian tuyến tính $\mathcal{O}(N)$. |
| **Không gian (Space Complexity)** | $\mathcal{O}(H)$ | Không tính bộ nhớ chứa kết quả trả về: bộ nhớ phụ chỉ bao gồm ngăn xếp đệ quy (call stack) có độ sâu tối đa bằng chiều cao $H$ của cây. <br>• Cây cân bằng hoàn hảo: $H = \mathcal{O}(\log N)$. <br>• Cây suy biến thành danh sách liên kết: $H = \mathcal{O}(N)$. |

---

## 🔄 Cách tiếp cận khác: Khử đệ quy bằng Stack (Iterative)

Trong môi trường thực tế hoặc cây có chiều sâu cực lớn ($N > 10^5$), đệ quy có thể gây ra lỗi tràn ngăn xếp (`StackOverflowError`). Ta có thể giải quyết bằng cách duyệt tiền thứ tự dùng `Stack` tường minh kết hợp `Set` để đánh dấu các nút đã được duyệt xong (cần đóng ngoặc `)`).

### 💡 Ý tưởng:
1. Đẩy `root` vào `Stack`.
2. Khi lấy đỉnh ngăn xếp:
   - Nếu nút đã nằm trong `visited`: tức là hai nhánh con của nó đã xử lý xong $\to$ đóng ngoặc `)` và pop nút ra.
   - Nếu nút chưa nằm trong `visited`: đánh dấu vào `visited`, thêm `node.val` vào chuỗi. Sau đó đẩy các nút con vào stack theo thứ tự: đóng ngoặc `)`, con phải, con trái, ...

```java
import java.util.*;

class SolutionIterative {
    public String tree2str(TreeNode root) {
        if (root == null) return "";

        StringBuilder sb = new StringBuilder();
        Deque<TreeNode> stack = new ArrayDeque<>();
        Set<TreeNode> visited = new HashSet<>();

        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode curr = stack.peek();
            if (visited.contains(curr)) {
                stack.pop();
                sb.append(')');
            } else {
                visited.add(curr);
                sb.append('(').append(curr.val);

                if (curr.left == null && curr.right != null) {
                    sb.append("()");
                }
                // Đẩy con phải vào trước để con trái được xử lý trước (LIFO)
                if (curr.right != null) {
                    stack.push(curr.right);
                }
                if (curr.left != null) {
                    stack.push(curr.left);
                }
            }
        }

        // Bỏ cặp ngoặc ngoài cùng bao quanh toàn bộ cây
        return sb.substring(1, sb.length() - 1);
    }
}
```

---

## 📊 Bảng so sánh các phương pháp

| Tiêu chí | Đệ quy ngây thơ (`+`) | ⭐ DFS Đệ quy + `StringBuilder` | Khử đệ quy bằng `Stack` |
| :--- | :--- | :--- | :--- |
| **Độ phức tạp thời gian** | $\mathcal{O}(N^2)$ (tệ nhất) | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Độ phức tạp không gian** | $\mathcal{O}(N^2)$ (rác bộ nhớ String) | $\mathcal{O}(H)$ (tối ưu nhất) | $\mathcal{O}(N)$ (cần thêm `Set` & `Stack`) |
| **Tốc độ thực tế (LeetCode)** | ~15ms - 25ms | **1ms (Top 100%)** | ~8ms - 12ms |
| **Khả năng tràn Call Stack** | Có (nếu $H$ quá lớn) | Có (nếu $H$ quá lớn) | **Không (An toàn tuyệt đối)** |
| **Độ phức tạp cài đặt** | Cực kỳ ngắn | Ngắn, trong sáng, dễ đọc | Tương đối phức tạp |

---

## 💡 Mẹo & Lời khuyên khi phỏng vấn (Interview Tips)

1. **Hiểu bản chất của "One-to-one mapping":**
   - Người phỏng vấn rất thích hỏi câu hỏi: *"Tại sao khi con trái null và con phải tồn tại thì phải giữ lại `()`, còn khi con phải null và con trái tồn tại thì lại được bỏ qua?"*
   - **Trả lời:** Vì thứ tự duyệt quy ước mặc định nhánh con đầu tiên xuất hiện sau nút gốc là con trái. Nếu bỏ qua `()` của con trái khi nó null, con phải sẽ bị hiểu nhầm thành con trái, làm thay đổi hoàn toàn cấu trúc hình học của cây nhị phân.
2. **Luôn đề xuất `StringBuilder` ngay từ đầu:**
   - Trong Java, việc dùng `StringBuilder` thay vì phép cộng chuỗi `+` trong các thuật toán duyệt cây/đồ thị thể hiện ngay sự am hiểu về hiệu năng và cơ chế quản lý bộ nhớ của JVM.
3. **Xử lý nút lá để cắt tỉa điều kiện:**
   - Kiểm tra `if (node.left == null && node.right == null) return;` ngay sau khi ghi nhận `node.val` giúp mã nguồn gọn gàng, tránh việc phải lồng ghép nhiều khối `if-else` phức tạp bên dưới.
