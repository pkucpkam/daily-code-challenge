# 200. Number of Islands - Best Solution

## Ý tưởng tối ưu

Duyệt toàn bộ ma trận. Mỗi khi gặp một ô đất `'1'` chưa thăm, ta tăng số đảo lên 1 và dùng DFS lặp (stack) để "xóa" toàn bộ phần đất liên thông của đảo đó bằng cách đổi về `'0'`.

Điểm quan trọng:
- Dùng DFS lặp thay vì DFS đệ quy để tránh nguy cơ tràn stack khi lưới lớn.
- Đánh dấu trực tiếp trên `grid` để không cần mảng `visited`, tiết kiệm bộ nhớ.

## Tại sao đây là "best solution"

- Thời gian tối ưu: `O(m * n)` vì mỗi ô được xử lý tối đa 1 lần.
- Bộ nhớ tốt: không dùng `visited`, chỉ dùng stack cho các ô đất đang duyệt.
- An toàn với input lớn hơn so với đệ quy trong Java.

## Thuật toán

1. Nếu `grid` rỗng, trả về `0`.
2. Duyệt từng ô `(r, c)`:
- Nếu ô là nước `'0'`, bỏ qua.
- Nếu ô là đất `'1'`:
  - Tăng `islands`.
  - Đẩy ô này vào stack và đổi thành `'0'`.
  - Trong khi stack chưa rỗng:
  - Lấy một ô ra.
  - Kiểm tra 4 hướng (trên, dưới, trái, phải).
  - Ô nào là `'1'` thì đổi thành `'0'` và đẩy vào stack.
3. Kết thúc duyệt, trả về `islands`.

## Code (Java)

```java
class Solution {
  public int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0 || grid[0].length == 0) {
      return 0;
    }

    int rows = grid.length;
    int cols = grid[0].length;
    int islands = 0;

    java.util.ArrayDeque<Integer> stack = new java.util.ArrayDeque<>();

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (grid[r][c] != '1') {
          continue;
        }

        islands++;
        grid[r][c] = '0';
        stack.push(r * cols + c);

        while (!stack.isEmpty()) {
          int cell = stack.pop();
          int cr = cell / cols;
          int cc = cell % cols;

          if (cr > 0 && grid[cr - 1][cc] == '1') {
            grid[cr - 1][cc] = '0';
            stack.push((cr - 1) * cols + cc);
          }
          if (cr + 1 < rows && grid[cr + 1][cc] == '1') {
            grid[cr + 1][cc] = '0';
            stack.push((cr + 1) * cols + cc);
          }
          if (cc > 0 && grid[cr][cc - 1] == '1') {
            grid[cr][cc - 1] = '0';
            stack.push(cr * cols + (cc - 1));
          }
          if (cc + 1 < cols && grid[cr][cc + 1] == '1') {
            grid[cr][cc + 1] = '0';
            stack.push(cr * cols + (cc + 1));
          }
        }
      }
    }

    return islands;
  }
}
```

## Độ phức tạp

- Time: `O(m * n)`
- Space: `O(m * n)` trong trường hợp xấu nhất (toàn bộ là đất)

## Ví dụ nhanh

Input:

```text
[
  ["1","1","0","0","0"],
  ["1","1","0","0","0"],
  ["0","0","1","0","0"],
  ["0","0","0","1","1"]
]
```

Quá trình đếm:
- Gặp cụm đất đầu tiên -> đảo số 1
- Gặp cụm ở giữa -> đảo số 2
- Gặp cụm cuối bên phải -> đảo số 3

Kết quả: `3`



