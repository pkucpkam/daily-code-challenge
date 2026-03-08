# Second Highest Salary (SQL)

## Yêu cầu bài toán

- Tìm mức lương cao thứ hai (khác nhau) từ bảng `Employee`.
- Nếu không có mức lương cao thứ hai, kết quả phải là `null`.

**Cấu trúc bảng `Employee`:**
- `id`: Khóa chính.
- `salary`: Mức lương của nhân viên.

## Ý tưởng cốt lõi

Để tìm mức lương cao thứ hai, chúng ta có thể làm theo các bước:
1.  **Loại bỏ trùng lặp**: Sử dụng `DISTINCT` để chỉ xét các mức lương khác nhau.
2.  **Sắp xếp**: Sắp xếp các mức lương theo thứ tự giảm dần.
3.  **Lấy phần tử thứ hai**: Sử dụng `LIMIT 1 OFFSET 1`. `OFFSET 1` sẽ bỏ qua phần tử cao nhất, và `LIMIT 1` sẽ lấy phần tử tiếp theo.
4.  **Xử lý trường hợp không có kết quả**: Để trả về `null` thay vì không có dòng nào khi không tồn tại mức lương cao thứ hai, ta bọc câu truy vấn vào một câu lệnh `SELECT` lồng nhau.

## Cách tiếp cận: Sử dụng Subquery với LIMIT/OFFSET

Đây là cách tiếp cận phổ biến trong MySQL.

```sql
SELECT (
    SELECT DISTINCT salary
    FROM Employee
    ORDER BY salary DESC
    LIMIT 1 OFFSET 1
) AS SecondHighestSalary;
```

## Vì sao đúng?

- `SELECT DISTINCT salary FROM Employee ORDER BY salary DESC` tạo ra danh sách các mức lương từ cao xuống thấp và không trùng nhau.
- `LIMIT 1 OFFSET 1` lấy chính xác giá trị ở vị trí thứ 2.
- Việc đặt toàn bộ subquery trong dấu ngoặc của một `SELECT` chính sẽ trả về một giá trị duy nhất. Nếu subquery không có kết quả, SQL mặc định trả về `NULL`, đúng theo yêu cầu của bài toán.

## Độ phức tạp

- **Thời gian**: `O(N log N)` nếu không có index trên cột `salary` (do phải sắp xếp). Nếu có index, có thể nhanh hơn.
- **Không gian**: `O(N)` để lưu trữ danh sách tạm thời các mức lương duy nhất.

## Code (MySQL)

```sql
SELECT (
    SELECT DISTINCT salary
    FROM Employee
    ORDER BY salary DESC
    LIMIT 1 OFFSET 1
) AS SecondHighestSalary;
```
