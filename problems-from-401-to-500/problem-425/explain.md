# 📚 Giải thích Bài 589 - N-ary Tree Preorder Traversal

**Mục tiêu:** Cho node gốc (`root`) của một cây đa phân (N-ary Tree), trả về thứ tự duyệt **tiền thứ tự (preorder traversal)** của các giá trị node.

---

## 🎯 Phân tích bài toán

### 1. N-ary Tree & Thứ tự duyệt Preorder
- Trong cây nhị phân: `Preorder = Root -> Left -> Right`.
- Trong cây đa phân (N-ary Tree): Mỗi node có thể có 0, 1 hoặc nhiều node con (`children`).
  - Thứ tự duyệt **Preorder**: **Thăm node hiện tại trước (Root) $\rightarrow$ Thăm lần lượt từng node con từ trái sang phải**.

### 2. Follow-up
> *Recursive solution is trivial, could you do it iteratively?*  
> (Cách giải đệ quy thì quá dễ/hiển nhiên, bạn có thể làm bằng phương pháp lặp không?)

Do đó, **giải pháp tốt nhất (Best Solution)** là triển khai bằng **phương pháp lặp (Iterative) sử dụng Stack**, giải quyết trọn vẹn cả câu hỏi mở rộng và tránh lỗi `StackOverflowError` khi cây có độ sâu lớn.

---

## ⭐ GIẢI PHÁP TỐI ƯU: Iterative với Stack (`ArrayDeque`)

### 💡 Ý tưởng cốt lõi
1. Sử dụng một `Stack` (trong Java dùng `Deque<Node> stack = new ArrayDeque<>()` để đạt hiệu năng cao nhất, tránh chi phí đồng bộ của `java.util.Stack`).
2. Khởi tạo: Đẩy `root` vào stack (nếu `root != null`).
3. Vòng lặp `while (!stack.isEmpty())`:
   - Lấy node trên đỉnh stack ra (`Node curr = stack.pop()`).
   - Thêm `curr.val` vào danh sách kết quả `result`.
   - **Điểm mấu chốt (Key Insight):**
     Vì Stack hoạt động theo cơ chế **LIFO (Last-In, First-Out - Vào sau ra trước)**:
     - Để duyệt các con từ **trái sang phải**, ta phải đẩy các con vào Stack theo thứ tự **từ phải sang trái** (`from right to left`).
     - Nhờ đó, node con ngoài cùng bên trái được đẩy vào cuối cùng sẽ nằm ở đỉnh stack và được lấy ra xử lý đầu tiên ở lượt tiếp theo.

### 💻 Code Java (Iterative)

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class Solution {
    public List<Integer> preorder(Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Deque<Node> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            result.add(current.val);

            // Đẩy các con từ phải sang trái vào Stack
            // để con ngoài cùng bên trái được pop ra trước
            if (current.children != null) {
                for (int i = current.children.size() - 1; i >= 0; i--) {
                    stack.push(current.children.get(i));
                }
            }
        }

        return result;
    }
}
```

---

## 🔄 Giải pháp phụ: Đệ quy (Recursive DFS)

Để tham khảo và so sánh:

```java
class Solution {
    public List<Integer> preorder(Node root) {
        List<Integer> result = new ArrayList<>();
        dfs(root, result);
        return result;
    }

    private void dfs(Node node, List<Integer> result) {
        if (node == null) return;

        result.add(node.val); // Thăm root trước

        if (node.children != null) {
            for (Node child : node.children) {
                dfs(child, result); // Đệ quy từng con từ trái sang phải
            }
        }
    }
}
```

---

## 📊 Đánh giá độ phức tạp (Complexity Analysis)

| Phương pháp | Time Complexity | Space Complexity | Đánh giá |
| :--- | :--- | :--- | :--- |
| **Iterative (Stack)** | $\mathcal{O}(N)$ | $\mathcal{O}(H)$ / $\mathcal{O}(N)$ | **Tối ưu nhất**, giải quyết triệt để Follow-up, kiểm soát bộ nhớ stack rõ ràng. |
| **Recursive (DFS)** | $\mathcal{O}(N)$ | $\mathcal{O}(H)$ | Đơn giản, ngắn gọn nhưng phụ thuộc vào Call Stack của JVM (dễ `StackOverflow` nếu $H$ lớn). |

- **$N$**: Tổng số node trong cây (lên đến $10^4$). Mỗi node được thăm đúng 1 lần $\rightarrow \mathcal{O}(N)$.
- **$H$**: Chiều cao của cây. Bộ nhớ stack lưu tối đa số node trên một nhánh/tầng $\rightarrow \mathcal{O}(H)$ (tối đa $\mathcal{O}(N)$ trong trường hợp xấu nhất cây suy biến).
