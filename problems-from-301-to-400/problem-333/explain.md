# 417. Pacific Atlantic Water Flow - Giải thích thuật toán

Bài toán yêu cầu tìm các ô trong một ma trận kích thước $m \times n$ mà từ đó nước mưa có thể chảy đến cả hai đại dương: **Thái Bình Dương (Pacific Ocean)** ở phía trên và bên trái, và **Đại Tây Dương (Atlantic Ocean)** ở phía dưới và bên phải. 

Nước chỉ có thể chảy từ ô hiện tại sang ô lân cận (4 hướng: Đông, Tây, Nam, Bắc) nếu ô lân cận có chiều cao nhỏ hơn hoặc bằng ô hiện tại.

---

## Phân tích & Ý tưởng cốt lõi

Nếu chúng ta duyệt từ từng ô trong ma trận và tìm đường đi ra cả hai đại dương, độ phức tạp sẽ rất lớn ($O((m \times n)^2)$) do có nhiều ô bị duyệt lặp đi lặp lại.

Thay vào đó, ta có thể áp dụng tư duy **ngược lại (Reversed Search)**:
1. **Bắt đầu từ rìa đại dương** (nơi nước chắc chắn chảy trực tiếp vào đại dương đó) và đi ngược vào sâu trong đất liền.
2. Từ một ô ở rìa đại dương, chúng ta chỉ có thể di chuyển đến ô lân cận có **chiều cao lớn hơn hoặc bằng** ô hiện tại (vì nước chảy từ cao xuống thấp, nên khi đi ngược dòng, ta phải leo lên cao).
3. Sử dụng hai mảng đánh dấu `boolean[][] pacific` và `boolean[][] atlantic` kích thước $m \times n$ để ghi nhận các ô có thể kết nối tương ứng với từng đại dương.
4. Chạy thuật toán tìm kiếm theo chiều sâu (**DFS - Depth First Search**) hoặc theo chiều rộng (**BFS**):
   - Bắt đầu DFS/BFS cho Pacific Ocean từ hàng trên cùng ($r = 0$) và cột bên trái cùng ($c = 0$).
   - Bắt đầu DFS/BFS cho Atlantic Ocean từ hàng dưới cùng ($r = m - 1$) và cột bên phải cùng ($c = n - 1$).
5. Cuối cùng, giao của hai tập hợp này (các ô $(r, c)$ thỏa mãn cả `pacific[r][c] == true` và `atlantic[r][c] == true`) chính là kết quả cần tìm.

---

## Các bước thực hiện thuật toán

1. **Khởi tạo:**
   - Hai ma trận boolean `pacific` và `atlantic` đều có kích thước $m \times n$, giá trị mặc định ban đầu là `false`.
2. **Duyệt hàng trên cùng và hàng dưới cùng:**
   - Chạy DFS từ hàng trên cùng (`r = 0`) để đánh dấu các ô có thể chảy ra Thái Bình Dương (`pacific`).
   - Chạy DFS từ hàng dưới cùng (`r = m - 1`) để đánh dấu các ô có thể chảy ra Đại Tây Dương (`atlantic`).
3. **Duyệt cột bên trái cùng và cột bên phải cùng:**
   - Chạy DFS từ cột bên trái cùng (`c = 0`) để đánh dấu các ô có thể chảy ra Thái Bình Dương (`pacific`).
   - Chạy DFS từ cột bên phải cùng (`c = n - 1`) để đánh dấu các ô có thể chảy ra Đại Tây Dương (`atlantic`).
4. **Hàm DFS:**
   - Nhận vào tọa độ ô $(r, c)$, ma trận đánh dấu của đại dương tương ứng, và chiều cao của ô trước đó `prevHeight`.
   - Nếu ô hiện tại nằm ngoài ma trận, đã được đánh dấu, hoặc có chiều cao nhỏ hơn `prevHeight`, ta dừng tìm kiếm (quay lui).
   - Đánh dấu ô hiện tại là `true`.
   - Tiếp tục gọi đệ quy DFS cho 4 ô lân cận với `prevHeight` cập nhật là chiều cao của ô hiện tại.
5. **Tìm ô giao nhau:**
   - Duyệt qua toàn bộ ma trận, những ô nào có cả `pacific[r][c]` và `atlantic[r][c]` là `true` thì thêm tọa độ `[r, c]` vào danh sách kết quả.

---

## Phân tích độ phức tạp

- **Độ phức tạp thời gian (Time Complexity):** $O(m \times n)$
  - Mỗi ô trong ma trận được thăm tối đa một vài lần thông qua các lời gọi DFS (tối đa 1 lần cho Pacific và 1 lần cho Atlantic).
- **Độ phức tạp không gian (Space Complexity):** $O(m \times n)$
  - Do ta sử dụng 2 ma trận boolean kích thước $m \times n$ để lưu trữ trạng thái có thể tiếp cận đại dương. Ngăn xếp đệ quy (Call Stack) của DFS trong trường hợp xấu nhất cũng có thể sâu tới $O(m \times n)$.

