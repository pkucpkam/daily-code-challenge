# 235. Lowest Common Ancestor of a Binary Search Tree - Best Solution

## Mô tả bài toán

Cho cây nhị phân tìm kiếm (BST) và hai node `p`, `q`. Hãy tìm **tổ tiên chung thấp nhất** (LCA) của chúng.

## Ý tưởng tối ưu

Tận dụng tính chất BST:

- Nếu cả `p` và `q` nhỏ hơn node hiện tại, LCA nằm bên trái.
- Nếu cả `p` và `q` lớn hơn node hiện tại, LCA nằm bên phải.
- Ngược lại (một bên trái, một bên phải, hoặc trùng node hiện tại), node hiện tại chính là LCA.

Vì mỗi bước chỉ đi xuống một nhánh, đây là cách làm tối ưu nhất cho BST.

## Cách làm

1. Bắt đầu từ `root`.
2. So sánh `p.val`, `q.val` với `current.val`.
3. Di chuyển trái/phải theo quy tắc ở trên.
4. Khi tách nhánh (hoặc chạm `p`/`q`), trả về `current`.

## Vì sao đúng

- Trong BST, mọi node bên trái nhỏ hơn node gốc của cây con; mọi node bên phải lớn hơn.
- Nếu `p` và `q` cùng phía, LCA chắc chắn không thể ở phía còn lại.
- Node đầu tiên mà `p` và `q` không còn cùng phía chính là điểm thấp nhất mà cả hai còn là hậu duệ.

## Độ phức tạp

- Thời gian: `O(h)`, với `h` là chiều cao cây.
- Bộ nhớ phụ: `O(1)` (phiên bản lặp).

## Java code

```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        int pVal = p.val;
        int qVal = q.val;
        TreeNode current = root;

        while (current != null) {
            if (pVal < current.val && qVal < current.val) {
                current = current.left;
            } else if (pVal > current.val && qVal > current.val) {
                current = current.right;
            } else {
                return current;
            }
        }

        return null;
    }
}
```
