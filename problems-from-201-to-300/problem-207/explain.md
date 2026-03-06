# Surrounded Regions

## Yêu cầu bài toán

- Cho một ma trận `board` kích thước `m x n` chứa toàn ký tự `'X'` và `'O'`.
- Bạn có quyền bắt (bao phủ) những khu vực có ký tự `'O'` bị tứ phía bọc kín (trên, dưới, trái, phải) bởi ký tự `'X'`. Lúc đó tất cả các ô trong khu vực `O` sẽ hóa thành `X`.
- Quy tắc loại trừ: Một khu vực vòng ngoài `'O'` nếu đang bám dính vào cạnh biên/viền của lưới ma trận thì coi như "Hở bọc", chưa bị vây kín hoàn toàn, do đó toàn bộ `'O'` trong mảng liên đới tới khu vực "hở bọc" này không bị bắt và vẫn giữ nguyên là `'O'`.
- Thay thế trực tiếp (in-place) ngay trên bảng tham chiếu.

## Ý tưởng cốt lõi

Thay vì mòn mỏi tìm từng khóm `O` và cố gắng phán đoán xem nó có lọt viền hay không, chúng ta sẽ bắt góc nhìn bài toán từ yếu tố quy tắc loại trừ:
**"Bất kỳ khóm `O` nào nằm ở sát mép viền, và cả tất cả các `O` nằm sâu bên trong nhưng có thể chạm được tới chúng thông qua cạnh sát kề nằm ngang xéo đều VÔ NHIỄM!"**.

Vậy thì:
1. Bạn hãy duyệt quét tất cả các mép khía cạnh (Trái, Phải, Trên Cùng, Dưới Cùng). Tìm những cá thể `'O'` lọt vùng này và đánh dấu riêng cho chúng thành một ký tự cứu thân, ví dụ như `'S'` (Safe/Survive). Thêm chúng vào hàng đợi của phép BFS để bung dần sự lây lan bảo vệ.
2. Qua BFS, lây nhiễm tất cả `'O'` bên trong vốn dính dáng kết nối đến `'O'` vùng viên thành `'S'` hết lớp này tới lớp khác.
3. Loanh quanh thì lúc này, tất cả mạng nội ô `O` bị kẹt sâu thực sự, do không dính vào vòng cứu thân `'S'` nào đều đang ở nguyên hình trạng là số không `'O'`.
4. Vi vu cập nhật ma trận vòng thứ 2 thôi: Chuyển toàn bộ `'O'` khốn khổ chịu cắn thành `'X'`, đồng thời rửa tội phục hồi lại các `'S'` sống sớt thành `'O'` chuẩn nghĩa. 

## Thuật toán

1. Kiểm tra kích thước xem biên bảng có quá kém (<0).
2. Thực hiện đưa vào queue BFS mọi khu viền quanh cạnh bảng: (1) Cạnh dòng trên, (2) Cạnh dòng dưới, (3) Cột cạnh trái, (4) Cột cạnh phải. Ghi mã là quy ước `'S'`.
3. Vận hành BFS cho `queue`: từ danh sách đỉnh có sẵn, ta lan truyền liên tiếp sang hàng dọc, ngang bốn hướng. Ai bị chạm (`'O'`) thì đổi thành `'S'` và đưa vào queue. Cứ làm cho đến khi cạn kiệt.
4. Một vòng lặp 2 chiều for(j) -> for(i) quét khắp mảng. `'O'` thành `'X'` còn `'S'` quay lại `'O'`.

## Vì sao đúng?

- Cách lây nhiễm từ ngoài vào trong đảm bảo được rằng ta phủ nhận không sót một điểm `O` ngoại lệ nào. Nhờ kỹ thuật lây truyền BFS mà ta kiểm tra được chuỗi liên kết hở của `O` một cách chắc chắn và không làm đảo lộn logic tìm hở ban đầu.

## Độ phức tạp
- Thời gian: `O(M * N)` (Trong trường hợp tệ nhất toàn bảng là O, bạn sẽ đi đúng M * N bước với Queue, vòng lặp thứ 2 duyệt bảng lại M * N bước). Thời gian chạy hằng số quá ổn định phụ thuộc kích thước ma trận.
- Không gian phụ: Tối đa cho trường hợp xấu nhất (`board` nguyên dải `'O'`) BFS sẽ đưa toàn bộ vào queue $\rightarrow$ mất khoảng chi phí không gian `O(M * N)`. 

## Code (Java)

```java
import java.util.*;

class Solution {
    public void solve(char[][] board) {
        if (board == null || board.length == 0) return;
        int m = board.length, n = board[0].length;
        Deque<int[]> q = new ArrayDeque<>();

        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') { board[i][0] = 'S'; q.offer(new int[]{i, 0}); }
            if (board[i][n - 1] == 'O') { board[i][n - 1] = 'S'; q.offer(new int[]{i, n - 1}); }
        }
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') { board[0][j] = 'S'; q.offer(new int[]{0, j}); }
            if (board[m - 1][j] == 'O') { board[m - 1][j] = 'S'; q.offer(new int[]{m - 1, j}); }
        }

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        while (!q.isEmpty()) {
            int[] p = q.poll();
            int r = p[0], c = p[1];
            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < m && nc >= 0 && nc < n && board[nr][nc] == 'O') {
                    board[nr][nc] = 'S';
                    q.offer(new int[]{nr, nc});
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') board[i][j] = 'X';
                else if (board[i][j] == 'S') board[i][j] = 'O';
            }
        }
    }
}
```
