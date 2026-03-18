# Giải thích bài 248 - Delete Duplicate Emails

**Mục tiêu:** Xóa tất cả các email trùng lặp, giữ lại chỉ một record có ID nhỏ nhất.

## ⭐ Best Solution: DELETE với JOIN

```sql
DELETE p1
FROM Person p1, Person p2
WHERE p1.email = p2.email AND p1.id > p2.id;
```

### Cách hoạt động

- **JOIN ngầm định:** `FROM Person p1, Person p2` là CROSS JOIN, tạo ra tất cả các cặp bản ghi từ hai bảng.
- **Điều kiện email giống nhau:** `p1.email = p2.email` lọc chỉ các cặp có cùng email.
- **ID lớn hơn:** `p1.id > p2.id` chọn record có ID lớn hơn để xóa.
- **Kết quả:** Chỉ giữ lại record có ID nhỏ nhất cho mỗi email.

### Ví dụ minh họa

**Trước khi DELETE:**
```
| id | email            |
|----|------------------|
| 1  | john@example.com |
| 2  | bob@example.com  |
| 3  | john@example.com |
```

**Quá trình thực hiện:**

Khi JOIN hai bảng, ta có các cặp:
- (p1=1, p2=1): email = email ✓, 1 > 1 ✗
- (p1=1, p2=2): email ≠ email ✗
- (p1=1, p2=3): email = email ✓, 1 > 3 ✗
- (p1=2, p2=1): email ≠ email ✗
- (p1=2, p2=2): email = email ✓, 2 > 2 ✗
- (p1=2, p2=3): email ≠ email ✗
- **(p1=3, p2=1): email = email ✓, 3 > 1 ✓ → XÓA**
- (p1=3, p2=2): email ≠ email ✗
- (p1=3, p2=3): email = email ✓, 3 > 3 ✗

**Sau khi DELETE:**
```
| id | email            |
|----|------------------|
| 1  | john@example.com |
| 2  | bob@example.com  |
```

## Giải pháp thay thế 1: DELETE với NOT IN

```sql
DELETE FROM Person
WHERE id NOT IN (
    SELECT MIN(id)
    FROM Person
    GROUP BY email
);
```

**Ưu điểm:** Dễ đọc và hiểu  
**Nhược điểm:** Chậm hơn trên dữ liệu lớn vì phải chạy subquery

## Giải pháp thay thế 2: DELETE với CTE (Common Table Expression)

```sql
WITH dup AS (
    SELECT id,
           ROW_NUMBER() OVER (PARTITION BY email ORDER BY id) as rn
    FROM Person
)
DELETE FROM Person
WHERE id IN (SELECT id FROM dup WHERE rn > 1);
```

**Ưu điểm:** Sạch sẽ, dễ hiểu, hiệu suất tốt với dữ liệu lớn  
**Nhược điểm:** Cú pháp phức tạp hơn, không tất cả DB support CTE

## So sánh hiệu suất

| Giải pháp | Thời gian | Bộ nhớ | Khuyên dùng | Ghi chú |
|-----------|-----------|--------|-------------|--------|
| **DELETE JOIN** | O(n²) | O(1) | ✅ **BEST** | Nhanh, ít dùng bộ nhớ |
| NOT IN | O(n²) | O(n) | Trung bình | Dùng subquery |
| CTE/ROW_NUMBER | O(n log n) | O(n) | Tốt | Modern, sạch nhưng phức tạp |

## Điểm lưu ý quan trọng

- ⚠️ **DELETE không trả về dữ liệu:** Lệnh chỉ xóa trực tiếp, không có SELECT output.
- ⚠️ **Kiểm tra kỹ WHERE condition:** Nên test bằng SELECT trước để chắc chắn logic xóa đúng.
- ⚠️ **Backup trước:** Nên backup database trước khi thực hiện DELETE trên dữ liệu quan trọng.
- 💡 **LIMIT không dùng được:** DELETE không hỗ trợ LIMIT trong một số DB.

## Độ phức tạp chi tiết

| Thước đo | Giá trị | Giải thích |
|----------|--------|-----------|
| **Space Complexity** | O(1) | Không dùng bộ nhớ bổ sung |
| **Time Complexity** | O(n²) | n² comparisons trong worst case |
| **Comparisons** | n*(n-1)/2 | Tất cả các cặp bản ghi |
