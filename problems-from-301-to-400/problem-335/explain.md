# 419. Battleships in a Board - Giải thích thuật toán

Bài toán yêu cầu chúng ta đếm số lượng tàu chiến (battleship) trên một bảng kích thước $m \times n$. Các tàu chiến chỉ được đặt nằm ngang hoặc nằm dọc (kích thước $1 \times k$ hoặc $k \times 1$) và luôn được phân tách với nhau bởi ít nhất một ô trống (không có hai tàu chiến nào nằm kề cạnh nhau).

---

## Phân tích & Ý tưởng cốt lõi

### 1. Cách tiếp cận thông thường (DFS/BFS hoặc Đánh dấu lại bảng)
- Chúng ta có thể duyệt qua từng ô của bảng. Khi gặp một ô chứa tàu chiến `'X'`, chúng ta tăng biến đếm lên $1$ rồi dùng thuật toán tìm kiếm theo chiều sâu (DFS) hoặc chiều rộng (BFS) để "loang" ra toàn bộ tàu chiến này và đổi kí tự `'X'` thành `'.'` (hoặc một kí tự đánh dấu khác) để tránh đếm trùng.
- **Hạn chế:** Cách tiếp cận này yêu cầu chỉnh sửa bảng (modify the board) hoặc cần thêm bộ nhớ phụ nếu không muốn thay đổi dữ liệu gốc.

### 2. Cách tiếp cận tối ưu: Chỉ đếm "đầu" của tàu chiến
Theo luật của bài toán:
- Các tàu chiến là các đoạn thẳng nằm ngang hoặc thẳng đứng.
- Không có hai tàu chiến nào đặt sát cạnh nhau.

Vì thế, mỗi tàu chiến luôn có một ô **đầu tiên** (nằm ở phía trên cùng hoặc bên trái cùng của tàu đó). Ta gọi đây là **"đầu" (head)** của tàu chiến:
- Nếu tàu nằm ngang: ô đầu tiên là ô trái cùng. Ô này không có ô bên trái kề nó là `'X'`.
- Nếu tàu nằm dọc: ô đầu tiên là ô trên cùng. Ô này không có ô phía trên kề nó là `'X'`.

Do đó, một ô `board[i][j] == 'X'` được coi là đầu của một tàu chiến mới khi và chỉ khi:
1. Ô phía trên nó không phải là `'X'` (`board[i-1][j] != 'X'`).
2. Ô bên trái nó không phải là `'X'` (`board[i][j-1] != 'X'`).

Bằng cách này, chúng ta chỉ cần thực hiện duy nhất một lượt duyệt bảng (one-pass), đếm số lượng "đầu" tàu chiến tìm thấy. Thuật toán này không cần sửa đổi bảng và không cần sử dụng bất kỳ mảng phụ nào.

---

## Các bước thực hiện thuật toán

1. Khởi tạo biến đếm `count = 0`.
2. Duyệt qua từng ô `board[i][j]` của bảng từ trên xuống dưới, từ trái qua phải:
   - Nếu `board[i][j] == 'X'`:
     - Kiểm tra nếu `i > 0` và `board[i-1][j] == 'X'`: bỏ qua (ô này thuộc thân của một tàu dọc đã đếm).
     - Kiểm tra nếu `j > 0` và `board[i][j-1] == 'X'`: bỏ qua (ô này thuộc thân của một tàu ngang đã đếm).
     - Nếu không vi phạm cả hai điều kiện trên, tức là ta đã tìm thấy một đầu tàu mới $\rightarrow$ tăng `count` lên 1.
3. Trả về `count`.

---

## Phân tích độ phức tạp

- **Độ phức tạp thời gian (Time Complexity):** $O(m \times n)$
  - Chúng ta chỉ duyệt qua ma trận đúng một lần. Mỗi ô chỉ thực hiện kiểm tra các ô kề cạnh (phía trên và bên trái) với chi phí $O(1)$.
- **Độ phức tạp không gian (Space Complexity):** $O(1)$
  - Thuật toán không sử dụng cấu trúc dữ liệu phụ nào khác ngoài một vài biến chạy đơn giản.
