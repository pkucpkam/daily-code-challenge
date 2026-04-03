# Maximum Depth of Binary Tree

## 1. Mô tả bài toán
Cho `root` của một cây nhị phân, hãy trả về độ sâu tối đa của cây đó.
Độ sâu tối đa là số lượng node nằm trên con đường dài nhất từ node gốc (root) xuống đến node lá (leaf) xa nhất.

## 2. Ý tưởng cốt lõi
- Độ sâu của một cây có thể được tính dựa trên độ sâu của các cây con của nó.
- Đây là một bài toán con (sub-problem) điển hình: 
  `Độ sâu của cây tại node hiện tại = 1 + max(Độ sâu cây con trái, Độ sâu cây con phải)`.
- Chúng ta sử dụng phương pháp **Đệ quy (DFS)** để duyệt xuống tận cùng các nhánh lá.

## 3. Giải thích thuật toán
1. Trường hợp cơ sở: Nếu `root` là `null`, độ sâu bằng `0`.
2. Gọi đệ quy để tính độ sâu của cây con bên trái: `leftDepth = maxDepth(root.left)`.
3. Gọi đệ quy để tính độ sâu của cây con bên phải: `rightDepth = maxDepth(root.right)`.
4. Kết quả trả về cho node hiện tại là giá trị lớn hơn giữa `leftDepth` và `rightDepth`, cộng thêm `1` (chính là node hiện tại).

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) với `n` là tổng số node trong cây. Ta cần ghé thăm mọi node để xác định độ sâu.
- **Không gian (Space Complexity)**: 
  - \(O(n)\) trong trường hợp xấu nhất (cây lệch).
  - \(O(\log n)\) trong trường hợp cây cân bằng (chiều cao của ngăn xếp đệ quy).

## 5. Code (Java)
```java
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null)
            return 0;
        
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        
        return Math.max(leftDepth, rightDepth) + 1;
    }
}
```
