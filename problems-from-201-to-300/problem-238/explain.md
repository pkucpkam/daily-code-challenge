# Nth Highest Salary (SQL)

## Yêu cầu bài toán

- Viết hàm `getNthHighestSalary(N)` để trả về mức lương cao thứ `N` (phân biệt theo giá trị, không tính trùng).
- Nếu không có đủ `N` mức lương khác nhau, trả về `NULL`.

**Bảng `Employee`:**
- `id`: Khóa chính.
- `salary`: Mức lương nhân viên.

## Ý tưởng cốt lõi

1. Dùng `DISTINCT` để loại lương trùng.
2. Sắp xếp `salary` giảm dần để có thứ tự từ cao đến thấp.
3. Vì `OFFSET` bắt đầu từ `0`, cần đổi `N` thành `N - 1`.
4. Lấy đúng 1 dòng tại vị trí `OFFSET N - 1`.

## Truy vấn

```sql
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
    SET N = N - 1;
    RETURN (
        SELECT DISTINCT salary
        FROM Employee
        ORDER BY salary DESC
        LIMIT 1 OFFSET N
    );
END
```

## Vì sao đúng?

- Sau `DISTINCT` + `ORDER BY salary DESC`, ta có dãy lương duy nhất theo thứ tự giảm dần.
- Phần tử thứ `N` trong dãy này chính là phần tử ở chỉ số `N - 1`.
- `LIMIT 1 OFFSET N` (sau khi đã `SET N = N - 1`) trả về đúng phần tử đó.
- Nếu offset vượt quá số lượng bản ghi, truy vấn con không có dòng và hàm trả về `NULL`, đúng yêu cầu đề bài.

## Độ phức tạp

- Thời gian: `O(M log M)`, với `M` là số lượng bản ghi (do sắp xếp).
- Không gian: phụ thuộc vào xử lý `DISTINCT`/`ORDER BY` của hệ quản trị CSDL.
