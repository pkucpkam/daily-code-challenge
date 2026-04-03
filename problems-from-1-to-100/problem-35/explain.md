# Binary Tree Postorder Traversal

## 1. Mô tả bài toán
Cho `root` của một cây nhị phân, hãy trả về danh sách giá trị của các node theo thứ tự duyệt **hậu thứ tự (Postorder Traversal)**.
Thứ tự duyệt Postorder là: Nhánh trái -> Nhánh phải -> Node gốc.

## 2. Ý tưởng cốt lõi
- Duyệt cây Postorder theo cách lặp (Iterative) thường phức tạp hơn Preorder.
- Một cách tiếp cận thông minh là nhận thấy thứ tự Preorder (Root -> Left -> Right) nếu được sửa đổi thành (Root -> Right -> Left) thì sau đó đảo ngược toàn bộ kết quả ta sẽ thu được Postorder (Left -> Right -> Root).
- Ta sử dụng hai ngăn xếp (Stack): 
    - `stack`: Để duyệt cây.
    - `output`: Để lưu trữ các node theo thứ tự "đảo ngược" (Root -> Right -> Left).

## 3. Giải thích thuật toán
1. Kiểm tra trường hợp cơ sở: Nếu `root == null`, trả về danh sách rỗng.
2. Khởi tạo `stack` và đẩy `root` vào.
3. Khởi tạo `output` stack.
4. Trong khi `stack` không trống:
   - Lấy node trên cùng ra khỏi `stack` và đẩy nó vào `output`.
   - Nếu node có con bên trái, đẩy con bên trái vào `stack`.
   - Nếu node có con bên phải, đẩy con bên phải vào `stack`.
   - (Lưu ý: Đẩy trái trước phải sau để khi lấy ra từ `stack`, phải sẽ được xử lý trước trái).
5. Sau khi duyệt hết, lần lượt lấy các phần tử từ `output` stack ra và thêm vào danh sách kết quả (đây chính là bước đảo ngược).

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Duyệt qua mỗi node đúng một lần.
- **Không gian (Space Complexity)**: \(O(n)\) - Sử dụng tối đa hai ngăn xếp có kích thước tỉ lệ thuận với số node.

## 5. Code (Java)
```java
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> output = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            output.push(node);
            
            // Đưa vào stack để xử lý: Trái trước, Phải sau
            if (node.left != null) stack.push(node.left);
            if (node.right != null) stack.push(node.right);
        }
        
        // Đảo ngược thứ tự bằng cách lấy từ output stack
        while (!output.isEmpty()) {
            result.add(output.pop().val);
        }
        
        return result;
    }
}
```
