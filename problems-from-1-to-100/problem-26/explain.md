# Minimum Depth of Binary Tree

## 1. Mô tả bài toán
Cho một cây nhị phân, hãy tìm độ sâu tối thiểu của nó.
Độ sâu tối thiểu là số lượng node nằm trên con đường ngắn nhất từ node gốc (root) đến node lá (leaf) gần nhất.
Lưu ý: Một node lá là node không có con (cả trái và phải đều `null`).

## 2. Ý tưởng cốt lõi
- Bài toán này khác với tìm `maxDepth`. Nếu một node chỉ có một cây con, ta không được lấy giá trị 0 của phía `null` làm giá trị nhỏ nhất, vì con đường đó chưa kết thúc ở một node lá.
- Ta sử dụng đệ quy để xét từng trường hợp của node hiện tại.

## 3. Giải thích thuật toán
1. Nếu `root == null`, trả về `0`.
2. Nếu node hiện tại là lá (`left == null` và `right == null`), trả về `1`.
3. Nếu cây con bên trái bị `null`, ta phải đi theo nhánh bên phải: `minDepth(root.right) + 1`.
4. Nếu cây con bên phải bị `null`, ta phải đi theo nhánh bên trái: `minDepth(root.left) + 1`.
5. Nếu cả hai cây con đều tồn tại, ta lấy giá trị nhỏ nhất giữa hai nhánh rồi cộng 1: `Math.min(minDepth(root.left), minDepth(root.right)) + 1`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Phải duyệt qua các node để tìm đường ngắn nhất.
- **Không gian (Space Complexity)**: \(O(h)\) - Với `h` là chiều cao của cây (ngăn xếp đệ quy). Trong trường hợp xấu nhất cây là một đường thẳng thì là \(O(n)\).

## 5. Code (Java)
```java
class Solution {
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // Trường hợp là node lá
        if (root.left == null && root.right == null) {
            return 1;
        }
        // Nếu một trong hai cây con là null, phải đi theo hướng cây con kia
        if (root.left == null) {
            return minDepth(root.right) + 1;
        }
        if (root.right == null) {
            return minDepth(root.left) + 1;
        }
        // Nếu có cả hai, lấy min
        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }
}
```
