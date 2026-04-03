# Binary Tree Preorder Traversal

## 1. Mô tả bài toán
Cho `root` của một cây nhị phân, hãy trả về danh sách giá trị của các node theo thứ tự duyệt **tiền thứ tự (Preorder Traversal)**.
Thứ tự duyệt Preorder là: Node gốc -> Nhánh trái -> Nhánh phải.

## 2. Ý tưởng cốt lõi
- Duyệt cây Preorder có thể thực hiện bằng đệ quy hoặc sử dụng cấu trúc dữ liệu **Ngăn xếp (Stack)** để thực hiện vòng lặp (Iterative).
- Ở đây, giải pháp sử dụng **Stack** để mô phỏng lại quá trình gọi đệ quy:
    - Khi lấy một node ra khỏi Stack (xử lý gốc), ta cần đẩy các con của nó vào Stack.
    - Vì Stack hoạt động theo cơ chế LIFO (Vào sau ra trước), ta phải đẩy con bên **phải** vào trước, sau đó mới đẩy con bên **trái** vào. Như vậy, con bên trái sẽ được lấy ra và xử lý trước con bên phải (đúng theo quy tắc Preorder).

## 3. Giải thích thuật toán
1. Khởi tạo danh sách `result`. Nếu `root == null`, trả về danh sách trống.
2. Khởi tạo một `Stack` và đẩy `root` vào Stack.
3. Trong khi Stack không trống:
   - Lấy node trên cùng ra khỏi Stack (`pop`).
   - Thêm giá trị của node đó vào `result`.
   - Nếu node có con bên phải (`node.right != null`), đẩy con bên phải vào Stack.
   - Nếu node có con bên trái (`node.left != null`), đẩy con bên trái vào Stack.
4. Trả về `result`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Ta ghé thăm và xử lý mỗi node trong cây đúng một lần.
- **Không gian (Space Complexity)**: \(O(h)\) - Với `h` là chiều cao của cây (kích thước tối đa của Stack). Trong trường hợp xấu nhất (cây lệch), không gian là \(O(n)\).

## 5. Code (Java)
```java
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            
            // Đẩy con bên PHẢI vào trước để xử lý TRÁI sau
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        
        return result;
    }
}
```
