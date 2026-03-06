# Binary Tree Maximum Path Sum

## Yêu cầu bài toán

- Đường dẫn trong cây nhị phân là một chuỗi các nốt có kết nối trực tiếp với nhau, không quay lại nốt cũ.
- Đường dẫn này KHÔNG BẮT BUỘC phải đi qua nốt gốc (root) của toàn bộ cây.
- Tổng đường dẫn (Path sum) là tổng giá trị của các nốt trên đường dẫn đó.
- Yêu cầu: Trả về **tổng lớn nhất** của một đường dẫn bất kỳ không rỗng trong cây.

## Ý tưởng cốt lõi

Nếu xét tại một nốt `node` bất kỳ để làm "đỉnh vòm" cao nhất của một đường dẫn, thì đường dẫn hợp lệ đi qua nó sẽ có cấu trúc:
**Đường nhánh trái tốt nhất** -> `node.val` -> **Đường nhánh phải tốt nhất**.

Do các nốt trong cây có thể nhận giá trị âm, có những nhánh cây mà tổng của chúng mang giá trị `< 0`. Nên nếu mảng nhánh nào có tổng âm, ta thà không lấy mảng nhánh đó (tức là coi như bằng `0`) sẽ đem lại tổng tốt hơn cho đường dẫn.

## Thuật toán

Ta có thể giải bằng đệ quy duyệt cây theo kỹ thuật DFS hướng từ dưới lên (Bottom-Up):
1. Hàm `maxGain(node)`: Trả về giá trị của đường dẫn tổng lớn nhất bắt đầu từ `node` đâm xuống một trong hai bên các nhánh con của nó. Đây là tổng một chiều mà `node` có thể "đóng góp" cho cha của nó.
2. Tại mỗi nốt, ta tính lượng đóng góp tối đa từ việc đi xuống nhánh con trái: `left = Math.max(0, maxGain(node.left))` (nếu tổng bị âm thì loại bỏ và coi như 0).
3. Tương tự với nhánh con phải: `right = Math.max(0, maxGain(node.right))`.
4. Tổng nhánh đường đi vắt chéo qua nốt này (tức xem nốt này như là đỉnh vòm) sẽ là: `node.val + left + right`. Ta lấy giá trị này dùng để cập nhật biến toàn cục giữ kết quả max là `maxSum`.
5. Cuối cùng, hàm trả về lượng đóng góp lớn nhất nốt này có thể đưa lên cha (chỉ được chọn 1 trong 2 nhánh con): `node.val + Math.max(left, right)`.

## Vì sao đúng?

Phương pháp này đảm bảo tất cả mọi nốt trong cây đều được thử nghiệm một lần làm mốc "đỉnh vòm" của một đường dẫn đi qua nó để thử cộng ra tổng lớn nhất. Đồng thời, do ta đệ quy DFS hàm lấy kết quả từ dưới lên nên không bị tính đi tính lại các nhánh con, đảm bảo tối ưu.

## Độ phức tạp

- Thời gian: `O(N)` với `N` là số lượng nốt của cây, vì ta phải duyệt mọi nốt đúng một lần.
- Không gian phụ: `O(H)` với `H` là chiều cao của cây, tương ứng với số lượng call stack gọi chéo ngầm trong đệ quy DFS. Trường hợp xấu nhất (cây suy biến thành danh sách liên kết) là `O(N)`, và tốt nhất là `O(log N)` với cây nhị phân cân bằng đầy đủ.

## Code (Java)

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode { ... }
 */
class Solution {
    private int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxGain(root);
        return maxSum;
    }

    private int maxGain(TreeNode node) {
        if (node == null)
            return 0;
        int left = Math.max(0, maxGain(node.left));
        int right = Math.max(0, maxGain(node.right));
        maxSum = Math.max(maxSum, node.val + left + right);
        return node.val + Math.max(left, right);
    }
}
```
