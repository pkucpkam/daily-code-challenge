# 236. Lowest Common Ancestor of a Binary Tree - Best Solution

## Mô tả bài toán

Cho cây nhị phân bất kỳ (không phải BST) và hai node `p`, `q`. Hãy tìm **tổ tiên chung thấp nhất** (LCA) của chúng.

## Ý tưởng tối ưu

Vì đây là cây nhị phân thường, ta không thể dùng so sánh giá trị như BST.

Ta dùng DFS đệ quy hậu tự (post-order):

- Nếu `root` là `null`, trả về `null`.
- Nếu `root == p` hoặc `root == q`, trả về chính `root`.
- Gọi đệ quy xuống trái và phải.
- Nếu cả hai bên đều trả về khác `null`, tức là `p` và `q` nằm ở hai nhánh khác nhau, nên `root` là LCA.
- Nếu chỉ một bên khác `null`, truyền kết quả đó lên trên.

## Cách làm

1. Xử lý base case tại node hiện tại.
2. Tìm trong cây con trái và cây con phải.
3. Hợp nhất kết quả theo quy tắc:
   - `left != null && right != null` -> trả về `root`.
   - Ngược lại trả về node khác `null`.

## Vì sao đúng

- Mỗi lời gọi trả về 1 trong 3 trạng thái:
  - Không tìm thấy `p`/`q` trong cây con hiện tại -> `null`.
  - Tìm thấy đúng 1 node (`p` hoặc `q`) -> trả về node đó.
  - Tìm thấy cả hai node ở hai nhánh khác nhau -> trả về LCA.
- Node đầu tiên trong quá trình đi ngược lên có cả trái và phải cùng khác `null` chính là tổ tiên chung thấp nhất.

## Độ phức tạp

- Thời gian: `O(n)` vì mỗi node được thăm tối đa 1 lần.
- Bộ nhớ phụ: `O(h)` do stack đệ quy, với `h` là chiều cao cây.
  - Cây lệch: `O(n)`.
  - Cây cân bằng: `O(log n)`.

## Java code

```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) {
            return root;
        }

        return left != null ? left : right;
    }
}
```
