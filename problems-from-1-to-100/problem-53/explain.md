# Invert Binary Tree

## 1. Mô tả bài toán
Cho `root` của một cây nhị phân, hãy đảo ngược cây đó (giống như soi gương) và trả về `root` của cây đã đảo ngược.
Ví dụ:
- Input: `[4,2,7,1,3,6,9]`
- Output: `[4,7,2,9,6,3,1]`

## 2. Ý tưởng cốt lõi
- Để đảo ngược một cây nhị phân, ta cần hoán đổi cây con bên trái và cây con bên phải của từng node trong cây.
- Bài toán này có tính chất đệ quy tự nhiên: Đảo ngược một cây nghĩa là hoán đổi hai con của gốc, sau đó đảo ngược tiếp hai cây con đó.

## 3. Giải thích thuật toán
1. Trường hợp dừng: Nếu `root == null`, trả về `null`.
2. Thực hiện hoán đổi (swap) hai con của node hiện tại:
   - Dùng một biến tạm `temp` để lưu `root.left`.
   - Gán `root.left = root.right`.
   - Gán `root.right = temp`.
3. Đệ quy thực hiện tương tự cho nhánh con bên trái mới: `invertTree(root.left)`.
4. Đệ quy thực hiện tương tự cho nhánh con bên phải mới: `invertTree(root.right)`.
5. Trả về `root`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Ta ghé thăm mỗi node trong cây đúng một lần để thực hiện phép hoán đổi.
- **Không gian (Space Complexity)**: \(O(h)\) - Với `h` là chiều cao của cây, tương ứng với độ sâu của ngăn xếp đệ quy. Trong trường hợp xấu nhất là \(O(n)\).

## 5. Code (Java)
```java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        
        // Hoán đổi con trái và con phải
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        
        // Đệ quy cho các cây con
        invertTree(root.left);
        invertTree(root.right);
        
        return root;
    }
}
```
