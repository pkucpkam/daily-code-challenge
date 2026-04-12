# 240. Search a 2D Matrix II - Best Solution

## Mô tả bài toán

Cho ma trận số nguyên `matrix` kích thước `m x n`:

- Mỗi hàng tăng dần từ trái sang phải.
- Mỗi cột tăng dần từ trên xuống dưới.

Hãy kiểm tra xem giá trị `target` có tồn tại trong ma trận hay không.

## Ý tưởng tối ưu: Staircase Search

Bắt đầu từ góc trên bên phải (`row = 0`, `col = n - 1`).

Tại mỗi bước, gọi `current = matrix[row][col]`:

- Nếu `current == target` -> tìm thấy, trả về `true`.
- Nếu `current > target` -> bỏ cả cột hiện tại, đi sang trái (`col--`).
- Nếu `current < target` -> bỏ cả hàng hiện tại, đi xuống (`row++`).

Lý do loại bỏ được cả hàng/cột:

- Ở vị trí góc trên phải, mọi phần tử bên dưới lớn hơn hoặc bằng `current`.
- Mọi phần tử bên trái nhỏ hơn hoặc bằng `current`.

Nên khi so sánh với `target`, ta luôn loại được một dải phần tử chắc chắn không thể là đáp án.

## Vì sao đúng

Ta duy trì bất biến:

- Vùng còn có thể chứa `target` là hình chữ nhật từ `row..m-1` và `0..col`.

Mỗi bước:

- Nếu `current > target`, cột `col` không thể chứa `target` trong vùng xét -> giảm `col`.
- Nếu `current < target`, hàng `row` không thể chứa `target` trong vùng xét -> tăng `row`.

Vùng tìm kiếm giảm ít nhất 1 hàng hoặc 1 cột sau mỗi bước, nên thuật toán kết thúc và không bỏ sót nghiệm.

## Độ phức tạp

- Thời gian: `O(m + n)`
- Bộ nhớ phụ: `O(1)`

## Java Code

```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int row = 0;
        int col = cols - 1;

        while (row < rows && col >= 0) {
            int current = matrix[row][col];

            if (current == target) {
                return true;
            }

            if (current > target) {
                col--;
            } else {
                row++;
            }
        }

        return false;
    }
}
```
