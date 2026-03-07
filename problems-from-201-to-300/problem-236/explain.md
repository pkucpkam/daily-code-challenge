# Binary Search Tree Iterator

## Yêu cầu bài toán

Thiết kế lớp `BSTIterator` cho cây nhị phân tìm kiếm (BST):

- `BSTIterator(TreeNode root)`: khởi tạo iterator.
- `next()`: di chuyển con trỏ sang phần tử kế tiếp theo thứ tự in-order và trả về giá trị đó.
- `hasNext()`: kiểm tra còn phần tử nào phía sau hay không.

Thứ tự duyệt cần là in-order (trái -> gốc -> phải), nên dãy sinh ra sẽ tăng dần với BST.

## Ý tưởng cốt lõi

Ta không cần lưu toàn bộ in-order ngay từ đầu. Dùng `stack` để mô phỏng đệ quy và chỉ chuẩn bị những node cần thiết:

- Trong constructor: đẩy toàn bộ nhánh trái từ `root` vào stack.
- `next()`:
  - Pop đỉnh stack ra, đó là phần tử nhỏ nhất chưa dùng.
  - Nếu node vừa pop có cây con phải, đẩy toàn bộ nhánh trái của cây con phải vào stack.
- `hasNext()` chỉ cần kiểm tra stack có rỗng hay không.

## Vì sao đúng?

- Stack luôn chứa đường đi đến node nhỏ nhất chưa được trả về.
- Khi pop một node `x`, mọi node nhỏ hơn `x` đã được xử lý xong.
- Sau đó, phần tử kế tiếp (nếu có) nằm trong cây con phải của `x`, cụ thể là node trái nhất của cây con phải đó. Việc gọi hàm đẩy nhánh trái đảm bảo đúng thứ tự in-order.

Vì vậy, mỗi lần gọi `next()` đều trả về đúng phần tử kế tiếp theo in-order.

## Độ phức tạp

- `hasNext()`: `O(1)`.
- `next()`: trung bình (amortized) `O(1)`, tệ nhất một lần gọi có thể `O(h)`.
- Bộ nhớ: `O(h)` với `h` là chiều cao cây.

## Code (Java)

```java
import java.util.ArrayDeque;
import java.util.Deque;

class BSTIterator {
    private final Deque<TreeNode> stack = new ArrayDeque<>();

    public BSTIterator(TreeNode root) {
        pushLeft(root);
    }

    public int next() {
        TreeNode node = stack.pop();
        if (node.right != null) {
            pushLeft(node.right);
        }
        return node.val;
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    private void pushLeft(TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }
}
```
