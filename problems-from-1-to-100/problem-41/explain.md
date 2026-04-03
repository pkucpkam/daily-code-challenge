# Duplicate Emails

## 1. Mô tả bài toán
Cho bảng `Person` gồm hai cột: `id` (khóa chính) và `email`.
Yêu cầu: Viết một câu lệnh SQL để tìm tất cả các email bị trùng lặp (xuất hiện nhiều hơn 1 lần) trong bảng.

## 2. Ý tưởng cốt lõi
- Để tìm các bản ghi trùng lặp dựa trên một cột, chúng ta cần nhóm dữ liệu theo cột đó bằng `GROUP BY`.
- Sau khi nhóm, chúng ta đếm số lượng bản ghi trong mỗi nhóm.
- Sử dụng điều kiện `HAVING` để lọc ra những nhóm có số lượng (`COUNT`) lớn hơn 1.

## 3. Giải thích thuật toán
1. Chọn cột `email` để hiển thị trong kết quả.
2. Từ bảng `Person`.
3. Nhóm dữ liệu theo cột `email`: `GROUP BY email`. Việc này gom tất cả các hàng có cùng email vào một nhóm duy nhất.
4. Điều kiện lọc sau khi nhóm: `HAVING COUNT(*) > 1`. Chỉ những email nào xuất hiện từ 2 lần trở lên mới được giữ lại.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(N)\) với `N` là số hàng trong bảng `Person` (giả sử việc GROUP BY được tối ưu hóa bằng Hash Table hoặc Sorting).
- **Không gian (Space Complexity)**: \(O(U)\) với `U` là số lượng email duy nhất trong bảng để lưu trữ các nhóm.

## 5. Code (SQL)
```sql
SELECT email AS Email
FROM Person
GROUP BY email
HAVING COUNT(*) > 1;
```
