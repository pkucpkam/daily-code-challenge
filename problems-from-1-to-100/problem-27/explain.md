# Path Sum

## 1. Mô tả bài toán
Cho `root` của một cây nhị phân và một số nguyên `targetSum`. Hãy xác định xem cây có **đường đi từ gốc đến lá** nào mà tổng giá trị các node trên đường đi đó bằng `targetSum` hay không.
Một node lá là node không có con.

## 2. Ý tưởng cốt lõi
- Chúng ta duyệt cây theo chiều sâu (DFS).
- Khi đi qua mỗi node, ta trừ giá trị của node đó vào `targetSum` và tiếp tục kiểm tra các cây con.
- Nếu đến được một node lá mà giá trị còn lại của `targetSum` đúng bằng giá trị của node lá đó, nghĩa là ta đã tìm thấy đường đi thỏa mãn.

## 3. Giải thích thuật toán
1. Nếu `root == null`, trả về `false` (vì không có node nào để tạo thành đường đi).
2. Kiểm tra nếu node hiện tại là lá (`left == null` và `right == null`):
   - So sánh `targetSum` với `root.val`. Nếu bằng nhau, trả về `true`.
3. Nếu không phải lá, ta đệ quy xuống hai nhánh con:
   - Trừ giá trị của node hiện tại vào `targetSum`: `newTarget = targetSum - root.val`.
   - Kết quả là `true` nếu nhánh trái có đường thỏa mãn HOẶC nhánh phải có đường thỏa mãn: `hasPathSum(root.left, newTarget) || hasPathSum(root.right, newTarget)`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Trong trường hợp xấu nhất, ta phải thăm qua mọi node của cây.
- **Không gian (Space Complexity)**: \(O(h)\) - Với `h` là chiều cao của cây (ngăn xếp đệ quy). \(O(n)\) trong trường hợp cây lệch.

## 5. Code (Java)
```java
class Solution {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        
        // Nếu là node lá, kiểm tra tổng
        if (root.left == null && root.right == null) {
            return targetSum == root.val;
        }
        
        // Đệ quy sang hai nhánh với targetSum đã trừ đi node hiện tại
        int nextSum = targetSum - root.val;
        return hasPathSum(root.left, nextSum) || hasPathSum(root.right, nextSum);
    }
}
```
