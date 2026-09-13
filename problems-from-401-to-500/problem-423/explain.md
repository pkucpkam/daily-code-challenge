# 📚 Giải thích Bài 585 - Investments in 2016

**Mục tiêu:** Tính tổng giá trị đầu tư năm 2016 (`tiv_2016`), làm tròn 2 chữ số thập phân, cho tất cả người tham gia bảo hiểm thỏa mãn đồng thời hai điều kiện:
1. Có cùng giá trị `tiv_2015` với ít nhất một người tham gia bảo hiểm khác.
2. Không nằm cùng thành phố với bất kỳ ai khác (cặp tọa độ `(lat, lon)` là duy nhất).

---

## 🎯 Phân tích bài toán

Bảng `Insurance` có cấu trúc:
- `pid`: Khóa chính (Primary Key - định danh duy nhất cho từng hợp đồng).
- `tiv_2015`: Tổng giá trị đầu tư năm 2015 (Total Investment Value 2015).
- `tiv_2016`: Tổng giá trị đầu tư năm 2016 (Total Investment Value 2016).
- `lat`: Vĩ độ vị trí thành phố của người mua bảo hiểm (không NULL).
- `lon`: Kinh độ vị trí thành phố của người mua bảo hiểm (không NULL).

### 📋 Hai điều kiện lọc mấu chốt

1. **Điều kiện 1 (Trùng giá trị `tiv_2015`):**
   - Giá trị `tiv_2015` của người này phải xuất hiện ở **tối thiểu một người khác** trong bảng.
   - Nói cách khác: Số lần xuất hiện của giá trị `tiv_2015` đó trên toàn bộ bảng phải **lớn hơn 1** (`COUNT(tiv_2015) > 1`).

2. **Điều kiện 2 (Tọa độ địa lý độc nhất):**
   - Vị trí của người này không được trùng với bất kỳ ai khác.
   - Nói cách khác: Cặp tọa độ `(lat, lon)` chỉ được xuất hiện **đúng 1 lần** trên toàn bộ bảng (`COUNT(lat, lon) = 1`).

3. **Yêu cầu định dạng đầu ra:**
   - Tính tổng các giá trị `tiv_2016` thỏa mãn: `SUM(tiv_2016)`.
   - Làm tròn đến 2 chữ số thập phân: `ROUND(SUM(tiv_2016), 2) AS tiv_2016`.

---

### ⚠️ Những cạm bẫy và sai lầm thường gặp

1. **Xét riêng lẻ `lat` và `lon` thay vì cặp `(lat, lon)`:**
   - Tọa độ địa lý được xác định bởi cả hai giá trị kinh độ và vĩ độ cùng lúc. Hai điểm `(10, 20)` và `(10, 30)` là hai vị trí khác nhau dù có cùng `lat = 10`.
   - Do đó, việc kiểm tra vị trí độc nhất phải luôn thực hiện trên **bộ đôi thuộc tính `(lat, lon)`**.

2. **Cú pháp Tuple `(lat, lon) IN (...)` trên các hệ CSDL khác nhau:**
   - Trong MySQL và PostgreSQL, cú pháp so sánh tuple `(lat, lon) IN (SELECT ...)` hoạt động rất mượt mà.
   - Tuy nhiên, trong SQL Server (T-SQL) hoặc một số phiên bản cũ của Oracle, cú pháp tuple này không được hỗ trợ, gây lỗi cú pháp nếu viết trong môi trường doanh nghiệp.

3. **Nhầm lẫn điều kiện đếm:**
   - Với `tiv_2015`: Điều kiện là xuất hiện **nhiều hơn 1 lần** (`> 1`).
   - Với `(lat, lon)`: Điều kiện là xuất hiện **đúng 1 lần duy nhất** (`= 1`).

---

## ⭐ BEST SOLUTION: Sử dụng Window Functions (`COUNT(*) OVER`)

Trong SQL hiện đại (MySQL 8.0+, PostgreSQL, SQL Server, Oracle), kỹ thuật sử dụng **Window Functions** kết hợp với **CTE (Common Table Expression)** là giải pháp tối ưu và chuyên nghiệp nhất:

```sql
WITH InsuranceStats AS (
    SELECT 
        tiv_2016,
        COUNT(*) OVER(PARTITION BY tiv_2015) AS count_tiv_2015,
        COUNT(*) OVER(PARTITION BY lat, lon) AS count_lat_lon
    FROM Insurance
)
SELECT ROUND(SUM(tiv_2016), 2) AS tiv_2016
FROM InsuranceStats
WHERE count_tiv_2015 > 1 
  AND count_lat_lon = 1;
```

### 🌟 Tại sao đây là giải pháp tốt nhất?
1. **Chuẩn ANSI SQL:2003 hiện đại:** Hoạt động nhất quán trên hầu hết mọi hệ quản trị CSDL phổ biến (MySQL 8+, PostgreSQL, SQL Server, Oracle).
2. **Khắc phục triệt để hạn chế Tuple:** Hoàn toàn không phụ thuộc vào cú pháp `(lat, lon) IN (...)`, giúp code tương thích 100% kể cả trên SQL Server.
3. **Hiệu năng cao:** Không cần phải viết 2 subquery độc lập gây quét bảng nhiều lần. Bộ tối ưu hóa (Query Optimizer) có thể thực hiện tính toán các phân vùng cửa sổ một cách tối ưu.
4. **Mã nguồn sạch, dễ bảo trì:** Phép tính phân vùng thống kê được đóng gói gọn gàng trong CTE `InsuranceStats`, câu lệnh `SELECT` ngoài cùng chỉ việc lọc điều kiện và tính tổng.

---

## 🔍 Cách hoạt động từng bước

Xét dữ liệu mẫu của bảng `Insurance`:

| pid | tiv_2015 | tiv_2016 | lat | lon |
|:----|:---------|:---------|:----|:----|
| 1   | 10       | 5        | 10  | 10  |
| 2   | 20       | 20       | 20  | 20  |
| 3   | 10       | 30       | 20  | 20  |
| 4   | 10       | 40       | 40  | 40  |

### Bước 1: Tính số lần xuất hiện bằng Window Functions (CTE `InsuranceStats`)

- `COUNT(*) OVER(PARTITION BY tiv_2015)`: Đếm số lượng dòng có cùng giá trị `tiv_2015`.
  - Giá trị `10`: Xuất hiện ở pid 1, 3, 4 $\rightarrow$ count = `3`.
  - Giá trị `20`: Xuất hiện ở pid 2 $\rightarrow$ count = `1`.
- `COUNT(*) OVER(PARTITION BY lat, lon)`: Đếm số lượng dòng có cùng cặp tọa độ `(lat, lon)`.
  - Cặp `(10, 10)`: Xuất hiện ở pid 1 $\rightarrow$ count = `1`.
  - Cặp `(20, 20)`: Xuất hiện ở pid 2, 3 $\rightarrow$ count = `2`.
  - Cặp `(40, 40)`: Xuất hiện ở pid 4 $\rightarrow$ count = `1`.

Kết quả tạm thời trong CTE:

| pid | tiv_2016 | count_tiv_2015 | count_lat_lon |
|:----|:---------|:---------------|:--------------|
| 1   | 5        | 3              | 1             |
| 2   | 20       | 1              | 2             |
| 3   | 30       | 3              | 2             |
| 4   | 40       | 3              | 1             |

### Bước 2: Kiểm tra điều kiện trong mệnh đề `WHERE`

Áp dụng điều kiện: `count_tiv_2015 > 1 AND count_lat_lon = 1`:

| pid | tiv_2016 | `count_tiv_2015 > 1` | `count_lat_lon = 1` | Đạt cả 2 tiêu chí? | Trạng thái |
|:----|:---------|:---------------------|:--------------------|:-------------------|:-----------|
| 1   | 5        | `3 > 1` (TRUE)       | `1 = 1` (TRUE)      | **TRUE**           | ✅ **Chọn** |
| 2   | 20       | `1 > 1` (FALSE)      | `2 = 1` (FALSE)     | **FALSE**          | ❌ Loại bỏ |
| 3   | 30       | `3 > 1` (TRUE)       | `2 = 1` (FALSE)     | **FALSE**          | ❌ Loại bỏ |
| 4   | 40       | `3 > 1` (TRUE)       | `1 = 1` (TRUE)      | **TRUE**           | ✅ **Chọn** |

### Bước 3: Tính tổng và làm tròn

- Các dòng được chọn: pid 1 (`tiv_2016 = 5`) và pid 4 (`tiv_2016 = 40`).
- Tổng `tiv_2016` $= 5 + 40 = 45$.
- Áp dụng `ROUND(45, 2)` $\rightarrow$ **`45.00`**.

Kết quả cuối cùng:
```text
+----------+
| tiv_2016 |
+----------+
| 45.00    |
+----------+
```

---

## 🔄 Các giải pháp thay thế (Alternative Solutions)

### Alternative 1: Mệnh đề `IN` kết hợp `GROUP BY` & `HAVING` (Kinh điển trên LeetCode)

Đây là cách giải phổ biến nhất trên LeetCode do cú pháp bám sát 100% từng câu chữ của đề bài:

```sql
SELECT ROUND(SUM(tiv_2016), 2) AS tiv_2016
FROM Insurance
WHERE tiv_2015 IN (
    SELECT tiv_2015
    FROM Insurance
    GROUP BY tiv_2015
    HAVING COUNT(*) > 1
)
AND (lat, lon) IN (
    SELECT lat, lon
    FROM Insurance
    GROUP BY lat, lon
    HAVING COUNT(*) = 1
);
```

- **Ưu điểm:** Cực kỳ trực quan, dễ hiểu, logic rõ ràng với 2 mệnh đề lọc độc lập.
- **Nhược điểm:**
  - Bảng `Insurance` bị quét (scan) tới 3 lần (1 lần bảng chính + 2 lần trong 2 subquery).
  - Cú pháp so sánh tuple `(lat, lon) IN (...)` không chạy được trên SQL Server (T-SQL).

---

### Alternative 2: Truy vấn con tương quan với `EXISTS` và `NOT EXISTS`

Sử dụng cơ chế kiểm tra sự tồn tại (Correlated Subquery):

```sql
SELECT ROUND(SUM(i.tiv_2016), 2) AS tiv_2016
FROM Insurance i
WHERE EXISTS (
    SELECT 1
    FROM Insurance i2
    WHERE i2.pid <> i.pid AND i2.tiv_2015 = i.tiv_2015
)
AND NOT EXISTS (
    SELECT 1
    FROM Insurance i3
    WHERE i3.pid <> i.pid AND i3.lat = i.lat AND i3.lon = i.lon
);
```

- **Ưu điểm:**
  - **Cơ chế dừng sớm (Short-circuiting):** `EXISTS` sẽ dừng ngay lập tức khi tìm thấy bản ghi đầu tiên thỏa mãn mà không cần đếm toàn bộ số lượng dòng.
  - Tối ưu cực tốt nếu bảng có chỉ mục (Index) trên `(tiv_2015)` và `(lat, lon)`.
- **Nhược điểm:**
  - Nếu không có Index phù hợp, câu truy vấn có thể biến thành Nested Loop Join dẫn tới độ phức tạp thời gian $\mathcal{O}(N^2)$.

---

## 📊 So sánh các giải pháp

| Phương pháp | Thời gian | Không gian | Hỗ trợ đa RDBMS | Tận dụng Index | Đánh giá khi phỏng vấn |
|:---|:---|:---|:---|:---|:---|
| **Window Functions (Best)** ⭐ | $\mathcal{O}(N \log N)$ | $\mathcal{O}(N)$ | ✅ MySQL 8+, Postgres, SQL Server, Oracle | Tốt (Partition Scan) | 🥇 **Lựa chọn hiện đại, chuẩn mực** |
| **Subquery with `IN`** | $\mathcal{O}(N \log N)$ | $\mathcal{O}(N)$ | ⚠️ Kém trên SQL Server (lỗi tuple) | Trung bình (Tạo bảng tạm gom nhóm) | ⭐ Phổ biến, trực quan nhất |
| **`EXISTS` & `NOT EXISTS`** | $\mathcal{O}(N)$ đến $\mathcal{O}(N^2)$ | $\mathcal{O}(1)$ | ✅ Chuẩn ANSI mọi RDBMS | 🚀 Cực nhanh nếu có Index | Thể hiện tư duy tối ưu hóa hiệu năng |

---

## 💡 Điểm lưu ý quan trọng (Key Takeaways)

### 1. Phân biệt Window Function và Aggregate Function thông thường
- Các hàm như `COUNT()`, `SUM()` khi đi kèm `GROUP BY` sẽ **thu gọn** các dòng thành một dòng duy nhất cho mỗi nhóm.
- Khi đi kèm `OVER(PARTITION BY ...)`, hàm sẽ tính toán trên từng nhóm cửa sổ nhưng **giữ nguyên số lượng dòng ban đầu**, cho phép giữ lại toàn bộ thuộc tính của từng dòng (như `pid`, `tiv_2016`) để tiếp tục xử lý ở bước sau.

### 2. Kỹ thuật Tuple Comparison trong SQL
- Cú pháp `(col1, col2) IN (SELECT col1, col2 FROM ...)` là một tính năng mạnh mẽ của SQL chuẩn nhưng mức độ hỗ trợ giữa các vendor là khác nhau.
- Khi làm việc trong các dự án sử dụng Microsoft SQL Server, nếu muốn áp dụng phương pháp Subquery, cần phải nối chuỗi như `CONCAT(lat, ',', lon) IN (...)` hoặc sử dụng CTE/Window Functions để tránh lỗi.

### 3. Quy tắc làm tròn số thực
- Trong SQL, hàm `ROUND(x, d)` sẽ làm tròn số `x` đến `d` chữ số thập phân. Luôn bọc hàm `ROUND` bên ngoài hàm `SUM` (`ROUND(SUM(...), 2)`) thay vì làm tròn từng phần tử rồi mới cộng để tránh sai số tích lũy.

---

## 📈 Độ phức tạp (Complexity Analysis)

- **Độ phức tạp thời gian (Time Complexity):**
  - Với **Window Functions**: $\mathcal{O}(N \log N)$ do RDBMS cần thực hiện sắp xếp hoặc băm (hash) các dòng theo khóa phân vùng (`tiv_2015` và `(lat, lon)`).
  - Với **Subquery with `IN`**: $\mathcal{O}(N \log N)$ cho mỗi lần gom nhóm `GROUP BY`.
  - Với **`EXISTS` có Index**: $\mathcal{O}(N \log N)$ với tìm kiếm trên B-Tree Index.
- **Độ phức tạp không gian (Space Complexity):**
  - $\mathcal{O}(N)$ để lưu trữ dữ liệu bộ đệm phân vùng cửa sổ (Window buffers) hoặc bảng băm nhóm tạm thời trước khi trả về kết quả tổng hợp.
