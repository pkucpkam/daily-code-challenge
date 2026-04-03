# Combine Two Tables

## 1. Mô tả bài toán
Cho hai bảng:
- Bảng `Person`: Chứa thông tin về ID (`personId`), tên đệm (`lastName`) và tên (`firstName`).
- Bảng `Address`: Chứa thông tin địa chỉ (`addressId`, `personId`, `city`, `state`).

Yêu cầu: Viết một câu lệnh truy vấn SQL để trả về `firstName`, `lastName`, `city` và `state` cho mỗi người trong bảng `Person`. Nếu địa chỉ của một `personId` không có trong bảng `Address`, hãy báo cáo `null` cho các trường `city` và `state`.

## 2. Ý tưởng cốt lõi
- Đây là một bài toán truy vấn SQL cơ bản về việc kết hợp dữ liệu từ hai bảng.
- Vì yêu cầu trả về thông tin cho **tất cả mọi người** trong bảng `Person` (kể cả những người không có thông tin địa chỉ), chúng ta phải sử dụng phép nối **LEFT JOIN**.
- Nếu dùng INNER JOIN, những người không có địa chỉ sẽ bị loại khỏi kết quả, điều này trái với yêu cầu bài toán.

## 3. Giải thích thuật toán
1. Chọn các cột cần thiết: `p.firstName`, `p.lastName`, `a.city`, `a.state`.
2. Lấy dữ liệu chính từ bảng `Person` (đặt bí danh là `p`).
3. Thực hiện phép nối `LEFT JOIN` với bảng `Address` (đặt bí danh là `a`).
4. Điều kiện nối: `p.personId = a.personId`.
5. Kết quả sẽ tự động điền `NULL` cho `city` và `state` nếu không tìm thấy bản ghi tương ứng trong bảng `Address`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(N + M)\) trong đó `N` là số hàng của bảng `Person` và `M` là số hàng của bảng `Address` (giả sử có chỉ mục trên `personId`).
- **Không gian (Space Complexity)**: \(O(N)\) để lưu trữ kết quả trả về.

## 5. Code (SQL)
```sql
SELECT 
    p.firstName,
    p.lastName,
    a.city,
    a.state
FROM 
    Person p
LEFT JOIN 
    Address a
ON 
    p.personId = a.personId;
```
