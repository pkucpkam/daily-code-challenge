# Sum of Left Leaves

## 1. Mô tả bài toán
Cho `root` của một cây nhị phân. Hãy tính tổng của tất cả các **lá bên trái (left leaves)**.
Một node được gọi là lá nếu nó không có con. Một lá bên trái là một node lá và đồng thời là con bên trái của một node khác.

## 2. Ý tưởng cốt lõi
- Ta duyệt qua toàn bộ cây (có thể dùng DFS hoặc BFS).
- Tại mỗi node, ta không chỉ kiểm tra bản thân node đó mà còn phải kiểm tra node con bên trái của nó.
- Nếu node con bên trái tồn tại và nó là một **node lá**, ta cộng giá trị của nó vào tổng.
- Tiếp tục đệ quy cho cây con bên trái và cây con bên phải.

## 3. Giải thích thuật toán
1. Trường hợp cơ sở: Nếu `root == null`, trả về 0.
2. Khởi tạo `sum = 0`.
3. Kiểm tra xem con bên trái của node hiện tại có phải là node lá không:
   - Điều kiện: `root.left != null` AND `root.left.left == null` AND `root.left.right == null`.
   - Nếu đúng: `sum += root.left.val`.
4. Đệ quy để tính tiếp cho các nhánh:
   - `sum += sumOfLeftLeaves(root.left)`.
   - `sum += sumOfLeftLeaves(root.right)`.
5. Trả về `sum`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Ta ghé thăm mỗi node trong cây đúng một lần.
- **Không gian (Space Complexity)**: \(O(h)\) - Với `h` là chiều cao của cây (độ sâu của ngăn xếp đệ quy). Trong trường hợp xấu nhất cây bị lệch, độ phức tạp là $O(n)$.

## 5. Code (Java)
```java
class Solution {
    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) return 0;
        
        int sum = 0;
        
        // Kiểm tra xem node con bên trái có phải là node lá hay không
        if (root.left != null && root.left.left == null && root.left.right == null) {
            sum += root.left.val;
        }
        
        // Cộng dồn tổng từ các cây con bên trái và bên phải
        sum += sumOfLeftLeaves(root.left);
        sum += sumOfLeftLeaves(root.right);
        
        return sum;
    }
}
```
*(Ghi chú: Cần lưu ý phân biệt rõ ràng giữa "node con bên trái" và "node lá bên trái").*
