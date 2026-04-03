# Count Complete Tree Nodes

## 1. Mô tả bài toán
Cho `root` của một cây nhị phân đầy đủ (**complete binary tree**), hãy trả về số lượng node trong cây đó.
Theo định nghĩa, trong một cây nhị phân đầy đủ, mọi cấp độ đều được lấp đầy ngoại trừ có thể là cấp độ cuối cùng, và tất cả các node ở cấp độ cuối cùng nằm càng xa về bên trái càng tốt.
Yêu cầu: Thiết kế thuật toán có độ phức tạp thời gian nhỏ hơn \(O(n)\).

## 2. Ý tưởng cốt lõi
- Mặc dù cách tiếp cận đệ quy thông thường `1 + count(left) + count(right)` trả về kết quả đúng, nhưng độ phức tạp của nó là \(O(n)\).
- Để đạt được hiệu năng tốt hơn (\(O(\log^2 n)\)), ta cần tận dụng tính chất của **cây nhị phân đầy đủ**:
    - Nếu chiều cao của nhánh trái ngoài cùng bằng chiều cao của nhánh phải ngoài cùng, thì đó là một cây nhị phân hoàn hảo (perfect binary tree), số lượng node sẽ là \(2^h - 1\).
    - Nếu không, ta đệ quy tính toán cho từng cây con.

## 3. Giải thích thuật toán
*(Dựa trên code hiện tại trong Solution.java)*:
1. Nếu `root` là `null`, trả về `0`.
2. Trả về `1` (node hiện tại) cộng với số lượng node của cây con bên trái và cây con bên phải.
3. Đây là cách tiếp cận duyệt toàn bộ cây.

*(Lưu ý về cách tối ưu \(O(\log^2 n)\))*: 
- Ta so sánh chiều cao trái (`leftHeight`) và chiều cao phải (`rightHeight`).
- Nếu `leftHeight == rightHeight`, số node = `(1 << leftHeight) - 1`.
- Nếu không, tiếp tục đệ quy.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) với cách giải hiện tại trong file. (Với cách tối ưu là \(O(\log^2 n)\)).
- **Không gian (Space Complexity)**: \(O(h)\) với `h` là chiều cao của cây (ngăn xếp đệ quy).

## 5. Code (Java)
```java
class Solution {
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // Đệ quy tính tổng số node của cây con trái và phải cộng 1
        return 1 + countNodes(root.left) + countNodes(root.right);
    }
}
```
