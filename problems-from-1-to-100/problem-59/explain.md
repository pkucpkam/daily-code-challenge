# Binary Tree Paths

## 1. Mô tả bài toán
Cho `root` của một cây nhị phân. Hãy trả về tất cả các đường đi từ node gốc đến các node lá dưới dạng danh sách các chuỗi.
Một node lá là node không có con.
Ví dụ:
- Input: `root = [1,2,3,null,5]`
- Output: `["1->2->5", "1->3"]`

## 2. Ý tưởng cốt lõi
- Bài toán yêu cầu tìm tất cả các đường đi, vì vậy chúng ta sử dụng thuật toán **Duyệt theo chiều sâu (DFS)**.
- Khi duyệt qua mỗi node, ta cộng dồn giá trị của node đó vào chuỗi đường đi hiện tại.
- Khi chạm tới một node lá, dải đường đi đó đã hoàn thành, ta thêm nó vào danh sách kết quả.

## 3. Giải thích thuật toán
1. Khởi tạo danh sách `paths` để lưu kết quả.
2. Gọi hàm đệ quy `findPaths(node, currentPath, paths)`:
   - Nếu `node == null`, thoát đệ quy.
   - Thêm giá trị của node vào `currentPath`.
   - Kiểm tra nếu là node lá (`node.left == null && node.right == null`):
     - Thêm `currentPath` hoàn chỉnh vào danh sách `paths`.
   - Nếu không phải lá:
     - Thêm ký tự `->` vào `currentPath`.
     - Tiếp tục đệ quy sang nhánh trái và nhánh phải.
3. Trả về danh sách `paths`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n \times h)\) trong trường hợp xấu nhất, với `n` là số node và `h` là chiều cao của cây (do việc cộng chuỗi trong mỗi bước đệ quy).
- **Không gian (Space Complexity)**: \(O(h)\) cho ngăn xếp đệ quy, cộng với không gian lưu trữ danh sách kết quả.

## 5. Code (Java)
```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        if (root != null) {
            findPaths(root, "", paths);
        }
        return paths;
    }

    private void findPaths(TreeNode node, String path, List<String> paths) {
        // Thêm giá trị node hiện tại vào đường đi
        path += node.val;
        
        // Nếu là node lá, lưu đường đi vào kết quả
        if (node.left == null && node.right == null) {
            paths.add(path);
        } else {
            // Nếu có con, thêm dấu "->" và tiếp tục đệ quy
            if (node.left != null) {
                findPaths(node.left, path + "->", paths);
            }
            if (node.right != null) {
                findPaths(node.right, path + "->", paths);
            }
        }
    }
}
```
*(Lưu ý: Để tối ưu tốc độ cộng chuỗi, có thể sử dụng StringBuilder và kỹ thuật backtracking).*
