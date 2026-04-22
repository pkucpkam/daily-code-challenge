# 289. Game of Life - Best Solution

## Tóm tắt bài toán

Cho một bảng `m x n` gồm các ô sống (`1`) và chết (`0`). Mỗi ô xét 8 hàng xóm xung quanh và cập nhật trạng thái tiếp theo theo 4 quy tắc của Game of Life.

Yêu cầu là cập nhật trực tiếp trên chính bảng đầu vào.

## Ý tưởng tối ưu

Ta lưu cả trạng thái cũ và trạng thái mới trong cùng một ô bằng 2 bit:

- Bit thấp nhất giữ trạng thái hiện tại.
- Bit thứ hai giữ trạng thái kế tiếp.

Cụ thể:

- `0` -> ô chết, tiếp tục chết.
- `1` -> ô sống, tiếp tục sống.
- `2` -> ô chết, trở thành sống.
- `3` -> ô sống, tiếp tục sống.

Khi đếm hàng xóm sống, chỉ cần lấy `board[r][c] & 1` để đọc trạng thái cũ.

Sau khi duyệt xong toàn bộ bảng, dịch phải 1 bit (`>>= 1`) để chốt trạng thái mới.

## Vì sao đúng

- Trạng thái cũ luôn được giữ trong bit thấp nhất, nên việc đếm hàng xóm không bị ảnh hưởng bởi các ô đã được đánh dấu cho thế hệ mới.
- Mỗi ô được gán trạng thái kế tiếp đúng theo 4 quy tắc của đề.
- Sau khi dịch bit, bảng chỉ còn lại thế hệ tiếp theo nên việc cập nhật là đồng thời và in-place.

## Độ phức tạp

- Time: `O(m * n)`
- Space: `O(1)`

## Java Code

```java
class Solution {
    public void gameOfLife(int[][] board) {
        int rows = board.length;
        int cols = board[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int liveNeighbors = countLiveNeighbors(board, row, col);

                if (board[row][col] == 1 && (liveNeighbors == 2 || liveNeighbors == 3)) {
                    board[row][col] = 3;
                }

                if (board[row][col] == 0 && liveNeighbors == 3) {
                    board[row][col] = 2;
                }
            }
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col] >>= 1;
            }
        }
    }

    private int countLiveNeighbors(int[][] board, int row, int col) {
        int rows = board.length;
        int cols = board[0].length;
        int liveNeighbors = 0;

        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {
                if (dRow == 0 && dCol == 0) {
                    continue;
                }

                int nextRow = row + dRow;
                int nextCol = col + dCol;

                if (nextRow >= 0 && nextRow < rows && nextCol >= 0 && nextCol < cols) {
                    liveNeighbors += board[nextRow][nextCol] & 1;
                }
            }
        }

        return liveNeighbors;
    }
}
```
