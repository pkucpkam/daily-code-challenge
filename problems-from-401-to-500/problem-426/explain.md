# 📚 Giải thích Bài 590 - N-ary Tree Postorder Traversal

**Mục tiêu:** Cho node gốc (`root`) của một cây đa phân (N-ary Tree), trả về thứ tự duyệt **hậu thứ tự (postorder traversal)** của các giá trị node.

---

## 🎯 Phân tích bài toán

### 1. N-ary Tree & Thứ tự duyệt Postorder
- Trong cây nhị phân: `Postorder = Left -> Right -> Root`.
- Trong cây đa phân (N-ary Tree): Mỗi node có danh sách các node con (`children`).
  - Thứ tự duyệt **Postorder**: **Thăm lần lượt toàn bộ cây con của từng node con từ trái sang phải $\rightarrow$ Thăm node hiện tại (Root)**.
  - Thứ tự tổng quát: $\text{Child}_1 \rightarrow \text{Child}_2 \rightarrow \dots \rightarrow \text{Child}_k \rightarrow \text{Root}$.

### 2. Follow-up
> *Recursive solution is trivial, could you do it iteratively?*  
> (Cách giải đệ quy thì quá dễ/hiển nhiên, bạn có thể làm bằng phương pháp lặp không?)

Do đó, **giải pháp tốt nhất (Best Solution)** là triển khai bằng **phương pháp lặp (Iterative) sử dụng Stack**, giải quyết trọn vẹn cả câu hỏi mở rộng và tránh lỗi `StackOverflowError` khi cây có độ sâu lớn.

---

## ⭐ GIẢI PHÁP TỐI ƯU: Iterative với Stack (`ArrayDeque`) + Đảo ngược (`Reverse`)

### 💡 Ý tưởng cốt lõi (Key Insight)

1. **Mối quan hệ thú vị giữa Preorder và Postorder:**
   - Thứ tự Postorder mong muốn: `Left -> Right -> Root`.
   - Nếu ta đảo ngược thứ tự này lại:
     $$\text{Reversed}(\text{Left} \rightarrow \text{Right} \rightarrow \text{Root}) = \text{Root} \rightarrow \text{Right} \rightarrow \text{Left}$$
   - Thứ tự `Root -> Right -> Left` thực chất chính là một biến thể của **Preorder**, trong đó nhánh con bên **phải** được thăm trước nhánh con bên **trái**!

2. **Cách tiếp cận:**
   - Sử dụng một `Stack` (khuyến nghị dùng `ArrayDeque` trong Java để đạt tốc độ cao nhất).
   - Đẩy `root` vào stack.
   - Trong vòng lặp `while (!stack.isEmpty())`:
     - Lấy node trên đỉnh stack ra: `curr = stack.pop()`.
     - Thêm `curr.val` vào danh sách kết quả.
     - Đẩy lần lượt các con của `curr` từ **trái sang phải** vào stack. Vì Stack là cơ chế **LIFO (Last-In, First-Out)**, node con bên phải đẩy vào sau sẽ nằm ở đỉnh stack và được `pop()` ra xử lý trước ở vòng lặp kế tiếp.
     - Thứ tự các giá trị được thêm vào danh sách là: `Root -> Right -> Left`.
   - Cuối cùng, đảo ngược danh sách kết quả bằng `Collections.reverse(result)`, ta sẽ thu được chính xác thứ tự Postorder: `Left -> Right -> Root`.

3. **⚡ Tại sao chọn `ArrayList` + `Collections.reverse()` thay vì `LinkedList.addFirst()`?**
   - `LinkedList.addFirst()`: Mỗi lần chèn vào đầu cần cấp phát (allocate) một đối tượng `Node` mới trên heap. Với cây lên đến $10^4$ phần tử, thao tác này tạo áp lực lớn lên Garbage Collector (GC) và làm giảm hiệu năng CPU cache.
   - `ArrayList` + `Collections.reverse()`: `ArrayList` lưu trữ trên mảng liên tục, tận dụng tối đa CPU cache locality. Thao tác `Collections.reverse()` chỉ là hoán đổi hai con trỏ trên mảng với chi phí $\mathcal{O}(N)$ cực kỳ nhẹ và nhanh.

---

### 💻 Code Java (Iterative)

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

class Solution {
    // Best Solution: Iterative using Stack (ArrayDeque) + Reverse - solves the Follow-up
    public List<Integer> postorder(Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Deque<Node> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            result.add(current.val);

            // Đẩy các con từ trái sang phải vào Stack
            // Do LIFO, con bên phải sẽ được lấy ra xử lý trước
            // Thứ tự duyệt tạm thời: Root -> Right -> Left
            if (current.children != null) {
                for (Node child : current.children) {
                    stack.push(child);
                }
            }
        }

        // Đảo ngược (Root -> Right -> Left) thành (Left -> Right -> Root) = Postorder
        Collections.reverse(result);
        return result;
    }
}
```

---

## 🔄 Giải pháp phụ: Đệ quy (Recursive DFS)

Để tham khảo và so sánh với cách duyệt tự nhiên theo định nghĩa Postorder:

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> postorder(Node root) {
        List<Integer> result = new ArrayList<>();
        dfs(root, result);
        return result;
    }

    private void dfs(Node node, List<Integer> result) {
        if (node == null) return;

        // Thăm đệ quy toàn bộ các cây con từ trái sang phải trước
        if (node.children != null) {
            for (Node child : node.children) {
                dfs(child, result);
            }
        }

        // Thăm node hiện tại sau cùng (Postorder)
        result.add(node.val);
    }
}
```

---

## 📊 Đánh giá độ phức tạp (Complexity Analysis)

| Phương pháp | Time Complexity | Space Complexity | Đánh giá |
| :--- | :--- | :--- | :--- |
| **Iterative (Stack + Reverse)** | $\mathcal{O}(N)$ | $\mathcal{O}(H)$ / $\mathcal{O}(N)$ | **Tối ưu nhất**, giải quyết hoàn hảo Follow-up, an toàn với Call Stack của JVM. |
| **Recursive (DFS)** | $\mathcal{O}(N)$ | $\mathcal{O}(H)$ | Cực kỳ ngắn gọn, tự nhiên nhưng dễ bị `StackOverflowError` nếu cây có chiều cao lớn. |

- **$N$**: Tổng số node trong cây (lên đến $10^4$). Mỗi node được thăm đúng 1 lần và thao tác đảo ngược mảng cũng mất $\mathcal{O}(N) \rightarrow \text{Tổng thời gian } \mathcal{O}(N)$.
- **$H$**: Chiều cao của cây. Bộ nhớ Stack lưu trữ tối đa số node trên một nhánh/tầng $\rightarrow \mathcal{O}(H)$ trong trường hợp cây cân bằng, và tối đa $\mathcal{O}(N)$ trong trường hợp cây suy biến thành đường thẳng.
