# Binary Tree Inorder Traversal

## 1. Mô tả bài toán
Cho `root` của một cây nhị phân, hãy trả về danh sách các giá trị của các node theo thứ tự **duyệt giữa (Inorder Traversal)**.
Thứ tự duyệt Inorder là: Nhánh trái -> Node gốc -> Nhánh phải.

## 2. Ý tưởng cốt lõi
- Duyệt cây là một bài toán điển hình có thể giải quyết bằng **Đệ quy (Recursion)**.
- Với mỗi node, ta sẽ thực hiện quy trình:
    1. Đệ quy xuống cây con bên trái.
    2. Lưu giá trị của node hiện tại vào danh sách kết quả.
    3. Đệ quy xuống cây con bên phải.
- Điểm dừng của đệ quy: Khi node hiện tại là `null`.

## 3. Giải thích thuật toán
1. Khởi tạo một danh sách `result` để chứa kết quả.
2. Gọi hàm đệ quy phụ `inorder(root, result)`.
3. Trong hàm `inorder(node, result)`:
    - Kiểm tra điều kiện dừng: `if (node == null) return;`.
    - Thực hiện bước 1: Gọi `inorder(node.left, result)`.
    - Thực hiện bước 2: Thêm `node.val` vào danh sách `result`.
    - Thực hiện bước 3: Gọi `inorder(node.right, result)`.
4. Trả về danh sách `result`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) với `n` là số lượng node trong cây. Ta phải ghé thăm mỗi node đúng một lần.
- **Không gian (Space Complexity)**: 
    - \(O(n)\) cho trường hợp xấu nhất (cây lệch - skewed tree) để lưu ngăn xếp đệ quy (call stack).
    - \(O(\log n)\) cho trường hợp cây cân bằng.
    - Danh sách kết quả tốn thêm \(O(n)\) bộ nhớ.

## 5. Code (Java)
```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    private void inorder(TreeNode node, List<Integer> result) {
        if (node == null) return;

        inorder(node.left, result);      // Duyệt trái
        result.add(node.val);            // Xử lý node gốc
        inorder(node.right, result);     // Duyệt phải
    }
}
```
