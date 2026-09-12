# 📚 Giải thích Bài 584 - Find Customer Referee

**Mục tiêu:** Tìm tên (`name`) của tất cả các khách hàng thỏa mãn một trong hai điều kiện:
1. Được giới thiệu bởi khách hàng có `id != 2`.
2. Không được giới thiệu bởi bất kỳ ai (`referee_id` có giá trị `null`).

---

## 🎯 Phân tích bài toán

Bảng `Customer` có cấu trúc:
- `id`: Khóa chính (chứa các giá trị duy nhất đại diện cho từng khách hàng).
- `name`: Tên của khách hàng.
- `referee_id`: `id` của khách hàng đã giới thiệu khách hàng này. Cột này có thể nhận giá trị `NULL` nếu khách hàng không qua ai giới thiệu.

### ⚠️ Cạm bẫy lớn nhất: Three-Valued Logic (3VL) với giá trị NULL trong SQL

Rất nhiều bạn mới làm bài này sẽ viết ngay câu truy vấn:
```sql
-- ❌ CÂU LỆNH SAI THƯỜNG GẶP
SELECT name 
FROM Customer 
WHERE referee_id != 2;
```

**Tại sao câu lệnh trên lại SAI?**
1. Trong đại số Boole thông thường (như trong Java, C++, Python), một biểu thức điều kiện chỉ có 2 giá trị: `TRUE` hoặc `FALSE`.
2. Nhưng trong SQL, hệ thống logic tuân theo **Three-Valued Logic (3VL)** gồm 3 trạng thái:
   - `TRUE`
   - `FALSE`
   - `UNKNOWN` (không xác định)
3. Khi thực hiện phép so sánh với `NULL` (ví dụ: `NULL != 2` hoặc `NULL = 2`), SQL không trả về `TRUE` hay `FALSE`, mà trả về **`UNKNOWN`**.
4. Mệnh đề `WHERE` chỉ lọc và giữ lại các hàng có biểu thức điều kiện đánh giá ra **`TRUE`**. Bất kỳ dòng nào cho kết quả `FALSE` hoặc `UNKNOWN` đều bị loại bỏ.
5. Vì vậy, điều kiện `WHERE referee_id != 2` sẽ loại bỏ toàn bộ các khách hàng có `referee_id` là `NULL` (như Will, Jane, Bill), khiến kết quả bị thiếu nghiêm trọng!

👉 **Kết luận:** Bắt buộc phải xử lý tường minh trường hợp `referee_id IS NULL`.

---

## ⭐ BEST SOLUTION: Mệnh đề WHERE với OR IS NULL

Giải pháp chuẩn mực quốc tế theo tiêu chuẩn ANSI SQL, tương thích với mọi hệ quản trị cơ sở dữ liệu (MySQL, PostgreSQL, Oracle, SQL Server, SQLite):

```sql
SELECT name
FROM Customer
WHERE referee_id != 2 OR referee_id IS NULL;
```

*(Lưu ý: Có thể dùng `<>` thay cho `!=` vì `<>` là toán tử khác nhau chuẩn ANSI SQL)*.

### 🌟 Tại sao đây là giải pháp tốt nhất?
1. **Chuẩn ANSI SQL tuyệt đối:** Chạy trên bất kỳ hệ quản trị cơ sở dữ liệu nào mà không cần sửa đổi.
2. **Khả năng tối ưu chỉ mục (Sargable):** Không bọc cột `referee_id` trong bất kỳ hàm số học nào, giúp Query Optimizer có thể tận dụng index trên cột `referee_id` nếu có.
3. **Ý đồ rõ ràng, dễ đọc:** Bất kỳ lập trình viên nào đọc vào cũng hiểu ngay logic nghiệp vụ: "khách hàng không do khách 2 giới thiệu, hoặc không có người giới thiệu".

---

## 🔍 Cách hoạt động từng bước

Xét bảng `Customer` ban đầu:

| id | name | referee_id |
|:---|:-----|:-----------|
| 1  | Will | null       |
| 2  | Jane | null       |
| 3  | Alex | 2          |
| 4  | Bill | null       |
| 5  | Zack | 1          |
| 6  | Mark | 2          |

### Đánh giá điều kiện `referee_id != 2 OR referee_id IS NULL` trên từng dòng:

| id | name | referee_id | `referee_id != 2` | `referee_id IS NULL` | Kết quả `OR` | Trạng thái |
|:---|:-----|:-----------|:------------------|:---------------------|:-------------|:-----------|
| 1  | Will | `null`     | `UNKNOWN`         | `TRUE`               | **`TRUE`**   | ✅ Giữ lại |
| 2  | Jane | `null`     | `UNKNOWN`         | `TRUE`               | **`TRUE`**   | ✅ Giữ lại |
| 3  | Alex | `2`        | `FALSE`           | `FALSE`              | **`FALSE`**  | ❌ Loại bỏ |
| 4  | Bill | `null`     | `UNKNOWN`         | `TRUE`               | **`TRUE`**   | ✅ Giữ lại |
| 5  | Zack | `1`        | `TRUE`            | `FALSE`              | **`TRUE`**   | ✅ Giữ lại |
| 6  | Mark | `2`        | `FALSE`           | `FALSE`              | **`FALSE`**  | ❌ Loại bỏ |

### Bảng kết quả cuối cùng:

| name |
|:-----|
| Will |
| Jane |
| Bill |
| Zack |

---

## 🔄 Các giải pháp thay thế (Alternative Solutions)

### Alternative 1: Sử dụng `IFNULL()` hoặc `COALESCE()`

Thay vì viết hai điều kiện với `OR`, ta có thể chuyển giá trị `NULL` thành một giá trị mặc định khác 2 (ví dụ `0` hoặc `-1`):

```sql
SELECT name
FROM Customer
WHERE IFNULL(referee_id, 0) != 2;
```

Hoặc dùng hàm chuẩn ANSI `COALESCE`:
```sql
SELECT name
FROM Customer
WHERE COALESCE(referee_id, 0) <> 2;
```

- **Ưu điểm:** Mệnh đề `WHERE` ngắn gọn hơn.
- **Nhược điểm:** Việc bọc cột `referee_id` trong hàm khiến biểu thức trở thành **non-sargable** trong nhiều hệ cơ sở dữ liệu, có thể khiến bộ tối ưu không tận dụng được index B-tree thông thường trên cột `referee_id`.

---

### Alternative 2: Sử dụng toán tử an toàn với NULL `<=>` trong MySQL

Trong MySQL, toán tử `<=>` (Null-Safe Equal) so sánh hai giá trị và coi hai giá trị `NULL` là bằng nhau, đồng thời so sánh `NULL` với một giá trị số sẽ trả về `0 (FALSE)` thay vì `UNKNOWN`:

```sql
SELECT name
FROM Customer
WHERE NOT (referee_id <=> 2);
```

Hoặc:
```sql
SELECT name
FROM Customer
WHERE (referee_id <=> 2) = 0;
```

- **Ưu điểm:** Cực kỳ súc tích, xử lý triệt để bẫy 3VL mà không cần rẽ nhánh `OR`.
- **Nhược điểm:** Là cú pháp đặc thù của MySQL/MariaDB, không thuộc chuẩn ANSI SQL (không chạy được trên SQL Server, PostgreSQL, Oracle).

---

### Alternative 3: Phủ định tập hợp con (Subquery với `NOT IN` / `EXCEPT`)

Tìm danh sách các khách hàng có `referee_id = 2`, sau đó loại bỏ họ khỏi tập kết quả:

```sql
SELECT name
FROM Customer
WHERE id NOT IN (
    SELECT id
    FROM Customer
    WHERE referee_id = 2
);
```

- **Ưu điểm:** Tách biệt rõ ràng tập hợp cần loại trừ (`referee_id = 2`).
- **Nhược điểm:** Truy vấn con tạo thêm overhead không cần thiết cho một bài toán lọc đơn giản trên cùng một bảng.

---

## 📊 So sánh các giải pháp

| Phương pháp | Thời gian | Không gian | Tận dụng Index (Sargability) | Chuẩn ANSI SQL | Đánh giá khi phỏng vấn |
|:---|:---|:---|:---|:---|:---|
| **OR IS NULL (Best)** ⭐ | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Rất tốt (Sargable) | ✅ Đầy đủ | 🥇 **Lựa chọn tối ưu nhất** |
| **IFNULL / COALESCE** | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Kém hơn (Non-sargable) | ✅ `COALESCE` chuẩn ANSI | ⭐ Ngắn gọn, dễ nhớ |
| **NULL-Safe `<=>`** | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Tốt trên MySQL | ❌ Chỉ có ở MySQL | Thể hiện hiểu biết sâu về MySQL |
| **Subquery NOT IN** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | Trung bình | ✅ Đầy đủ | Hơi cồng kềnh |

---

## 💡 Điểm lưu ý quan trọng (Key Takeaways)

### 1. Bảng chân trị Three-Valued Logic (3VL) cần nhớ

| A | B | A AND B | A OR B | NOT A |
|:---|:---|:---|:---|:---|
| `TRUE` | `UNKNOWN` | `UNKNOWN` | **`TRUE`** | `FALSE` |
| `FALSE` | `UNKNOWN` | `FALSE` | `UNKNOWN` | `TRUE` |
| `UNKNOWN` | `UNKNOWN` | `UNKNOWN` | `UNKNOWN` | **`UNKNOWN`** |

> **Ghi nhớ:** Với toán tử `OR`, nếu một trong hai vế là `TRUE`, kết quả chắc chắn là `TRUE` ngay cả khi vế còn lại là `UNKNOWN`. Đó chính là lý do vì sao `referee_id != 2 OR referee_id IS NULL` hoạt động hoàn hảo khi `referee_id` là `NULL`!

### 2. Khái niệm Sargable (Search Argument Able)
- Khi viết truy vấn cơ sở dữ liệu lớn, hãy tránh viết `WHERE FUNC(column) = value`.
- Hãy giữ cột nguyên vẹn ở một vế của phép toán so sánh để chỉ mục (Index) có thể được sử dụng trực tiếp qua Index Seek / Range Scan thay vì phải Full Table Scan.

---

## 📈 Độ phức tạp (Complexity Analysis)

- **Độ phức tạp thời gian (Time Complexity):** $\mathcal{O}(N)$ với $N$ là tổng số dòng trong bảng `Customer`. Hệ quản trị CSDL chỉ cần quét qua bảng một lần (Table Scan) và kiểm tra điều kiện lọc trên từng dòng. Nếu cột `referee_id` có index, việc lọc có thể nhanh hơn bằng Index Range Scan kết hợp đọc NULL bitmap.
- **Độ phức tạp không gian (Space Complexity):** $\mathcal{O}(1)$ bộ nhớ phụ trợ ngoài bộ đệm kết quả trả về của câu lệnh `SELECT`.
