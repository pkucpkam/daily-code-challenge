# Sum Root to Leaf Numbers

## Yêu cầu bài toán

- Cho node gốc (root) của một cây nhị phân, trong đó mỗi node đều có giá trị là 1 chữ số từ `0` đến `9`.
- Mỗi đường dẫn từ gốc tới lá đều đại diện cho 1 con số cụ thể. Chẳng hạn, đường dẫn `1 -> 2 -> 3` ứng với con số `123`.
- node lá (leaf node) là node không có các nhánh con nào khác.
- Yêu cầu: Trả về **Tổng** của tất cả các con số hợp lệ được tạo ra từ việc gộp các đường dẫn gốc-tới-lá trong cây. (Dữ liệu đảm bảo tổng số fit trong số nguyên 32-bit).

## Ý tưởng cốt lõi

Bài toán yêu cầu kết nối các con số từ trên đi xuống một cách logic: một số ở hàng chục, trăm đè lên số hàng dưới. Do đó:
- Chúng ta có thể dùng thuật toán **Duyệt theo chiều sâu (DFS - Depth-First Search)** để đi liên tục xuống các node cành dọc theo 1 đường truyền. 
- Tại mỗi đỉnh được thăm, ta mang theo biến `cur` (số tạo thành từ gốc tới ngay trên đầu node hiện tại). Giá trị mới của `cur` để truyền đi tiếp bằng thủ thuật toán học: `cur = cur * 10 + node.val`. (Do hệ thập phân, mỗi lần ghép số tương đương với đẩy các số cũ lên gấp 10 và thế chữ số của nhánh hiện tại vào đuôi).

## Thuật toán
1. Hàm đệ quy DFS `dfs(node, cur)` nhận tham số là node hiện tại và giá trị `cur` (tích lũy được).
2. node hiện tại đang null thì báo cáo rỗng đường (trả về 0).
3. Cập nhật giá trị đường dẫn nối tại đó bằng `cur = cur * 10 + node.val`.
4. Nếu kiểm tra node này là node LÁ (Không có nhánh con trái hay nhánh con phải), đường truyền đã hoàn thành mục tiêu xây số -> `return cur`.
5. Nếu chưa phải lá, chúng ta gọi lại hàm đệ quy để tiếp tục dấn xuống nháy trái, sau đó nhánh phải. Lấy tổng 2 trị gộp vô nhau (của nhánh trái và nhấn phải đi về).

## Vì sao đúng?
- Phương pháp trên đi men dọc qua tất cả các cạnh đồ thị dưới mốc `root` mà không sót bất cứ mé nhánh lá nào. 
- Công thức `*10 + val` đúng với định dạng cơ số hệ 10 khi ta trượt từ đỉnh số hàng chục tới các số hàng đơn vị, đảm bảo mô phỏng chuẩn xác thao tác ghép các con số cho 1 chuỗi đường đi.

## Độ phức tạp

- Thời gian: `O(N)` với $N$ là tổng số lượng node có trong cây. Thuật toán gọi đệ quy thông qua mỗi đỉnh của cây nhị phân đúng duy nhất 1 lần.
- Không gian phụ: `O(H)` với $H$ là chiều cao sâu nhất của cây (phù hợp với số slot trong Call Stack kích đệ quy tự tạo). Chậm nhất (cây thành gậy) mất chi phí $O(N)$, bình thường thì chi phí $O(\log N)$.

## Code (Java)

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode { ... }
 */
class Solution {
    public int sumNumbers(TreeNode root) {
        return dfs(root, 0);
    }

    private int dfs(TreeNode node, int cur) {
        if (node == null) return 0;
        
        cur = cur * 10 + node.val;
        
        if (node.left == null && node.right == null) return cur;
        
        return dfs(node.left, cur) + dfs(node.right, cur);
    }
}
```
