# 230. Kth Smallest Element in a BST - Best Solution

## Mô tả bài toán

Cho một cây nhị phân tìm kiếm (BST) và một số nguyên `k`, hãy trả về giá trị nhỏ thứ `k` trong các node của cây.

## Ý tưởng cốt lõi

Điểm quan trọng của BST là duyệt theo thứ tự inorder sẽ cho ra dãy giá trị tăng dần.

Vì vậy, thay vì duyệt hết cây rồi mới tìm phần tử thứ `k`, ta chỉ cần:
- Duyệt inorder bằng stack.
- Đếm số node đã thăm.
- Khi đếm đến node thứ `k`, trả về ngay giá trị của node đó.

Đây là cách làm tối ưu và rất tự nhiên cho bài này.

## Cách hoạt động

### Bước 1: Đi sang nhánh trái sâu nhất

Luôn đẩy các node con trái vào stack trước để đảm bảo thứ tự inorder.

### Bước 2: Lấy node nhỏ nhất chưa thăm

Khi không còn đi trái được nữa, lấy node trên đỉnh stack ra. Đây là node nhỏ nhất hiện tại chưa được xử lý.

### Bước 3: Đếm và dừng sớm

Mỗi lần lấy được một node, giảm `k` đi 1.

Nếu `k == 0`, node hiện tại chính là đáp án.

### Bước 4: Chuyển sang nhánh phải

Sau khi xử lý một node, chuyển sang cây con phải của nó và lặp lại quá trình.

## Vì sao đúng

- Inorder traversal của BST luôn cho thứ tự tăng dần.
- Node thứ `k` trong thứ tự inorder chính là phần tử nhỏ thứ `k`.
- Ta dừng ngay khi tìm được node thứ `k`, nên không cần duyệt phần còn lại của cây.

## Độ phức tạp

- Thời gian: `O(h + k)` trung bình, tệ nhất là `O(n)`.
- Bộ nhớ phụ: `O(h)`, với `h` là chiều cao của cây.

## Java code

```java
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            current = stack.pop();
            if (--k == 0) {
                return current.val;
            }

            current = current.right;
        }

        return -1;
    }
}
```

## Ghi chú thêm

Nếu cây được cập nhật thường xuyên và cần truy vấn `k` nhỏ nhất liên tục, có thể nâng cấp mỗi node để lưu kích thước cây con. Khi đó, truy vấn `k`th nhỏ nhất có thể giảm xuống gần `O(h)` hoặc tốt hơn tùy cấu trúc cân bằng.
