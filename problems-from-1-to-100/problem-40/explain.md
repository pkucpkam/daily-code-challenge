# Employees Earning More Than Their Managers

## 1. Mô tả bài toán
Cho bảng `Employee` chứa các cột: `id`, `name`, `salary`, và `managerId`. 
`managerId` chính là `id` của một nhân viên khác trong cùng bảng đó.
Yêu cầu: Tìm tất cả các nhân viên có mức lương (`salary`) cao hơn mức lương của quản lý trực tiếp của họ.

## 2. Ý tưởng cốt lõi
- Đây là một bài toán xử lý trên một bảng duy nhất (Self-Join).
- Chúng ta cần so sánh dữ liệu của nhân viên với dữ liệu của cấp trên của chính nhân viên đó.
- Ta coi bảng `Employee` như hai thực thể khác nhau: một đóng vai trò là "Nhân viên" (`e`) và một đóng vai trò là "Quản lý" (`m`).
- Ta kết nối hai bản ghi này thông qua điều kiện `e.managerId = m.id`.

## 3. Giải thích thuật toán
1. Sử dụng lệnh `SELECT` để lấy cột `name` của nhân viên.
2. Dùng phép cộng bảng (Join) bảng `Employee` với chính nó:
   - Một bảng đại diện cho Employee (bí danh `e`).
   - Một bảng đại diện cho Manager (bí danh `m`).
3. Điều kiện nối: `e.managerId = m.id`.
4. Điều kiện lọc: `e.salary > m.salary`.
5. Trả về tên nhân viên dưới tiêu đề cột `Employee`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(N^2)\) trong trường hợp tệ nhất của phép Join, nhưng thực tế với hệ quản trị cơ sở dữ liệu có chỉ mục (Index) trên `id`, độ phức tạp thường là \(O(N \log N)\) hoặc \(O(N)\).
- **Không gian (Space Complexity)**: \(O(1)\) (không kể không gian lưu trữ kết quả).

## 5. Code (SQL)
```sql
SELECT e.name AS Employee
FROM Employee e
JOIN Employee m
  ON e.managerId = m.id
WHERE e.salary > m.salary;
```
