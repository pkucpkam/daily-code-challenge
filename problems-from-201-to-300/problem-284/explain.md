# 304. Range Sum Query 2D - Immutable - Best Solution

## Tóm tắt bài toán

Cho ma trận `matrix`, cần trả lời nhiều truy vấn tổng hình chữ nhật từ `(row1, col1)` đến `(row2, col2)`.
Yêu cầu quan trọng: mỗi lần gọi `sumRegion` phải chạy trong `O(1)`.

## Ý tưởng tối ưu

Dùng **2D Prefix Sum** (cộng dồn 2 chiều).

Tạo mảng `prefix` kích thước `(m + 1) x (n + 1)`, trong đó:

- `prefix[r][c]` là tổng của vùng con từ `(0, 0)` đến `(r - 1, c - 1)` trong `matrix`.

Công thức xây dựng:

```text
prefix[r][c] = matrix[r - 1][c - 1]
             + prefix[r - 1][c]
             + prefix[r][c - 1]
             - prefix[r - 1][c - 1]
```

Khi cần tính tổng hình chữ nhật `(row1, col1) -> (row2, col2)`, dùng nguyên lý bao hàm - loại trừ:

```text
sum = prefix[row2 + 1][col2 + 1]
    - prefix[row1][col2 + 1]
    - prefix[row2 + 1][col1]
    + prefix[row1][col1]
```

## Vì sao đúng

- `prefix[row2 + 1][col2 + 1]` chứa toàn bộ phần cần lấy cộng thêm các vùng dư ở trên và bên trái.
- Trừ `prefix[row1][col2 + 1]` để bỏ phần dư phía trên.
- Trừ `prefix[row2 + 1][col1]` để bỏ phần dư bên trái.
- Vùng góc trên-trái bị trừ 2 lần, nên cộng lại `prefix[row1][col1]`.

Do đó kết quả còn đúng chính xác vùng truy vấn.

## Độ phức tạp

- Khởi tạo (`NumMatrix`): `O(m * n)`
- Mỗi truy vấn `sumRegion`: `O(1)`
- Bộ nhớ phụ: `O(m * n)`

## Java Code

```java
class NumMatrix {
    private final int[][] prefix;

    public NumMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        prefix = new int[rows + 1][cols + 1];

        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {
                prefix[r][c] = matrix[r - 1][c - 1]
                    + prefix[r - 1][c]
                    + prefix[r][c - 1]
                    - prefix[r - 1][c - 1];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        int r2 = row2 + 1;
        int c2 = col2 + 1;

        return prefix[r2][c2]
            - prefix[row1][c2]
            - prefix[r2][col1]
            + prefix[row1][col1];
    }
}
```
