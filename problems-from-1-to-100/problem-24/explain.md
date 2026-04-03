# Convert Sorted Array to Binary Search Tree

## 1. Mô tả bài toán
Cho một mảng số nguyên `nums` đã được sắp xếp tăng dần. Hãy chuyển đổi mảng này thành một **Cây tìm kiếm nhị phân (Binary Search Tree - BST)** cân bằng về chiều cao.
Một cây cân bằng về chiều cao là cây mà độ sâu của hai cây con của mọi node không bao giờ chênh lệch quá 1.

## 2. Ý tưởng cốt lõi
- Để tạo ra một cây BST cân bằng từ một mảng đã sắp xếp, phần tử ở **giữa mảng** nên được chọn làm node gốc (root).
- Các phần tử bên trái của phần tử giữa sẽ tạo thành cây con bên trái.
- Các phần tử bên phải của phần tử giữa sẽ tạo thành cây con bên phải.
- Quá trình này được lặp lại một cách đệ quy cho các mảng con trái và phải.

## 3. Giải thích thuật toán
1. Sử dụng một hàm đệ quy `constructBST(nums, left, right)` lấy mảng và phạm vi chỉ số hiện tại.
2. Điều kiện dừng: Nếu `left > right`, trả về `null`.
3. Tìm chỉ số ở giữa: `mid = left + (right - left) / 2`.
4. Tạo một node mới `root` với giá trị là `nums[mid]`.
5. Đệ quy xây dựng cây con bên trái: `root.left = constructBST(nums, left, mid - 1)`.
6. Đệ quy xây dựng cây con bên phải: `root.right = constructBST(nums, mid + 1, right)`.
7. Trả về node `root`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) với `n` là số lượng phần tử trong mảng. Mỗi phần tử được thăm và tạo node đúng một lần.
- **Không gian (Space Complexity)**: \(O(\log n)\) để lưu trữ ngăn xếp đệ quy vì cây được tạo ra luôn cân bằng.

## 5. Code (Java)
```java
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        return constructBST(nums, 0, nums.length - 1);
    }

    private TreeNode constructBST(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        
        root.left = constructBST(nums, left, mid - 1);
        root.right = constructBST(nums, mid + 1, right);
        
        return root;
    }
}
```
