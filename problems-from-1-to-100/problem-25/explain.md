# Balanced Binary Tree

## 1. Mô tả bài toán
Cho một cây nhị phân, hãy xác định xem nó có **cân bằng về chiều cao** hay không.
Một cây nhị phân được gọi là cân bằng nếu đối với mỗi node trong cây, độ sâu của hai cây con (trái và phải) của nó không bao giờ chênh lệch quá 1.

## 2. Ý tưởng cốt lõi
- Bài toán yêu cầu kiểm tra điều kiện cân bằng tại mọi node.
- Thay vì tính chiều cao nhiều lần (gây tốn thời gian), ta có thể kết hợp việc tính chiều cao và kiểm tra tính cân bằng trong cùng một quá trình duyệt đệ quy từ dưới lên (Bottom-up).
- Nếu tại bất kỳ node nào mà cây con không cân bằng, ta sẽ "đánh dấu" bằng một giá trị đặc biệt (ví dụ: `-1`) để truyền lên phía trên.

## 3. Giải thích thuật toán
1. Hàm chính `isBalanced(root)` gọi hàm trợ giúp `checkHeight(root)`.
2. Trong hàm `checkHeight(node)`:
   - Nếu `node == null`, chiều cao là `0`.
   - Tính chiều cao cây con trái `leftHeight`. Nếu bằng `-1`, trả về `-1`.
   - Tính chiều cao cây con phải `rightHeight`. Nếu bằng `-1`, trả về `-1`.
   - Kiểm tra chênh lệch: `Math.abs(leftHeight - rightHeight) > 1`. Nếu đúng, trả về `-1` (không cân bằng).
   - Nếu cân bằng, trả về chiều cao thực sự: `Math.max(leftHeight, rightHeight) + 1`.
3. Kết quả cuối cùng là `true` nếu `checkHeight(root) != -1`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) với `n` là số node. Mỗi node được duyệt đúng một lần.
- **Không gian (Space Complexity)**: \(O(h)\) với `h` là chiều cao của cây, tương ứng với độ sâu của ngăn xếp đệ quy.

## 5. Code (Java)
```java
class Solution {
    public boolean isBalanced(TreeNode root) {
        return checkHeight(root) != -1;
    }
    
    private int checkHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        
        int leftHeight = checkHeight(node.left);
        if (leftHeight == -1) return -1; 
        
        int rightHeight = checkHeight(node.right);
        if (rightHeight == -1) return -1; 
        
        // Kiểm tra điều kiện cân bằng
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1; 
        }
        
        return Math.max(leftHeight, rightHeight) + 1;
    }
}
```
