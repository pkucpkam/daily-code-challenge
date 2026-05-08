# 337. House Robber III - Giải thích thuật toán

## Phân tích bài toán
Bài toán yêu cầu tìm số tiền lớn nhất có thể trộm từ một hệ thống nhà được sắp xếp theo cấu trúc **cây nhị phân**. Quy tắc duy nhất là: **không được trộm hai nhà nằm cạnh nhau (có liên kết trực tiếp)**.

Đây là một bài toán điển hình về **Quy hoạch động trên cây (Dynamic Programming on Trees)**.

---

## Phương pháp: Quy hoạch động (DP)

Tại mỗi nút (nhà), chúng ta có hai lựa chọn:
1. **Trộm nhà này**: Nếu trộm nhà này, chúng ta **không được phép** trộm các con trực tiếp của nó.
2. **Không trộm nhà này**: Nếu không trộm nhà này, chúng ta **có thể chọn** trộm hoặc không trộm các con của nó (tùy thuộc vào lựa chọn nào mang lại nhiều tiền hơn).

### 1. Trạng thái DP
Chúng ta định nghĩa một hàm trả về một mảng gồm 2 phần tử `[không_trộm, có_trộm]` cho mỗi nút:
- `res[0]`: Số tiền lớn nhất thu được nếu **không trộm** nút hiện tại.
- `res[1]`: Số tiền lớn nhất thu được nếu **có trộm** nút hiện tại.

### 2. Công thức truy hồi
Giả sử ta đang ở nút `root`, và đã tính toán được kết quả cho con bên trái (`left`) và con bên phải (`right`):

- **Nếu trộm `root`**:
  `res[1] = root.val + left[0] + right[0]`
  (Vì đã trộm `root`, nên chỉ được lấy kết quả "không trộm" từ các con).

- **Nếu không trộm `root`**:
  `res[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1])`
  (Vì không trộm `root`, ta có quyền chọn phương án tốt nhất từ mỗi nhánh con độc lập với nhau).

### 3. Điều kiện dừng
Nếu gặp nút `null`, trả về `[0, 0]`.

---

## Độ phức tạp thuật toán
- **Thời gian**: `O(n)` - Chúng ta duyệt qua mỗi nút đúng một lần (DFS).
- **Không gian**: `O(h)` - Với `h` là chiều cao của cây, dùng cho ngăn xếp đệ quy.

## Tại sao cách này tối ưu?
Cách tiếp cận này tránh được việc tính toán lặp lại (redundant calculations) bằng cách truyền thông tin từ dưới lên trên (bottom-up) trong một lần duyệt cây duy nhất. So với cách đệ quy thông thường có ghi nhớ (Memoization), cách này tiết kiệm bộ nhớ hơn vì không cần dùng thêm HashMap để lưu trữ kết quả của từng nút.
