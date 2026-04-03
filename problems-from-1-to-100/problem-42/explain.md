# Customers Who Never Order

## 1. Mô tả bài toán
Cho hai bảng:
- Bảng `Customers`: Chứa `id` và `name` của khách hàng.
- Bảng `Orders`: Chứa `id` của đơn hàng và `customerId` (khóa ngoại trỏ đến bảng Customers).

Yêu cầu: Tìm tất cả khách hàng chưa từng đặt bất kỳ đơn hàng nào.

## 2. Ý tưởng cốt lõi
Có hai cách tiếp cận chính cho bài toán này:
1. **Sử dụng Subquery (Vòng lặp không):** Tìm tất cả các ID khách hàng trong bảng `Orders`, sau đó lọc bảng `Customers` để lấy những người có ID **không nằm trong** danh sách đó.
2. **Sử dụng LEFT JOIN:** Kết nối bảng `Customers` với bảng `Orders`. Những khách hàng không có đơn hàng sẽ có giá trị `NULL` ở các cột của bảng `Orders` sau khi nối. Ta chỉ cần lọc những bản ghi có giá trị `NULL` này.

## 3. Giải thích thuật toán
### Cách 1: Sử dụng `NOT IN`
1. Lấy danh sách ID khách hàng đã từng đặt hàng từ bảng `Orders`: `SELECT customerId FROM Orders`.
2. Truy vấn bảng `Customers` bằng điều kiện `WHERE id NOT IN (...)` danh sách trên.

### Cách 2: Sử dụng `LEFT JOIN` (Thường hiệu quả hơn)
1. Thực hiện `LEFT JOIN` giữa `Customers c` và `Orders o` trên điều kiện `c.id = o.customerId`.
2. Với những khách hàng chưa đặt hàng, cột `o.id` (khóa chính của bảng Orders) sẽ là `NULL`.
3. Thêm điều kiện `WHERE o.id IS NULL`.
4. Lấy cột `name` và đặt bí danh là `Customers`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(N + M)\) với `N, M` là số hàng của hai bảng.
- **Không gian (Space Complexity)**: \(O(1)\) (không tính không gian kết quả).

## 5. Code (SQL)
```sql
-- Cách sử dụng LEFT JOIN (Khuyên dùng)
SELECT c.name AS Customers
FROM Customers c
LEFT JOIN Orders o ON c.id = o.customerId
WHERE o.id IS NULL;
```
*(Lưu ý: Bạn có thể sử dụng `NOT IN`, nhưng lưu ý vấn đề hiệu năng và lỗi nếu danh sách ID có giá trị NULL).*
