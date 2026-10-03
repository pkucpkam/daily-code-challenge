# 📚 Giải thích Bài 620 - Not Boring Movies

**Mục tiêu:** Truy vấn danh sách các bộ phim có `id` là số lẻ và phần miêu tả (`description`) khác `"boring"`, sau đó sắp xếp kết quả theo điểm đánh giá (`rating`) giảm dần.

---

## 🎯 Phân tích bài toán

Bảng `Cinema` có cấu trúc:
- `id` (`int`): Khóa chính chứa giá trị định danh duy nhất của mỗi bộ phim.
- `movie` (`varchar`): Tên bộ phim.
- `description` (`varchar`): Thể loại / mô tả nội dung bộ phim.
- `rating` (`float`): Điểm đánh giá (2 chữ số thập phân, trong khoảng $[0, 10]$).

### 📋 Yêu cầu cốt lõi
1. **Lọc `id` lẻ (Odd-numbered ID):** Cần lọc các dòng có `id` là số lẻ ($1, 3, 5, \dots$).
   - Có thể dùng phép chia lấy dư: `id % 2 = 1` hoặc hàm `MOD(id, 2) = 1`.
   - Hoặc toán tử bitwise: `(id & 1) = 1`.
2. **Loại bỏ các bộ phim "boring" (Description not boring):**
   - Điều kiện chuỗi: `description <> 'boring'` hoặc `description != 'boring'`.
3. **Kết hợp điều kiện:** Cả hai điều kiện phải đồng thời thỏa mãn, do đó sử dụng toán tử logic `AND`.
4. **Sắp xếp kết quả:** Sắp xếp theo cột `rating` theo thứ tự giảm dần (`ORDER BY rating DESC`).

---

## ⭐ BEST SOLUTION: Mệnh đề `WHERE` kết hợp toán tử `%` (hoặc `MOD`) và `ORDER BY`

Đoạn mã truy vấn tối ưu trong [`Solution.sql`](file:///f:/daily-code-challenge/problems-from-401-to-500/problem-444/Solution.sql):

```sql
SELECT 
    id, 
    movie, 
    description, 
    rating
FROM Cinema
WHERE id % 2 = 1
  AND description <> 'boring'
ORDER BY rating DESC;
```

---

### 🌟 Tại sao đây là giải pháp tốt nhất?

1. **Đơn giản, trực quan và tốc độ thực thi cao:**
   - Truy vấn chỉ gồm các mệnh đề cơ bản của SQL: `SELECT`, `FROM`, `WHERE`, `ORDER BY`.
   - Bộ tối ưu hóa truy vấn (Query Optimizer) có thể dễ dàng quét bảng (Table Scan) hoặc sử dụng chỉ mục (Index) trên `id` để lọc dữ liệu cực nhanh.

2. **Liệt kê rõ ràng danh sách các cột (`SELECT id, movie, ...` thay vì `SELECT *`):**
   - Trong môi trường thực tế (Production), luôn liệt kê tường minh các cột cần lấy thay vì dùng `SELECT *`. Điều này giúp:
     - Tránh lỗi ứng dụng nếu schema của bảng `Cinema` thay đổi hoặc bổ sung cột mới trong tương lai.
     - Giảm băng thông truyền tải dữ liệu và tối ưu hóa việc sử dụng Covering Index.

3. **Chuẩn ANSI SQL (`<>` và `MOD`):**
   - Toán tử khác `<>` là toán tử chuẩn ISO/ANSI SQL, được hỗ trợ trên 100% các hệ RDBMS (MySQL, PostgreSQL, Oracle, SQL Server, SQLite).

---

## 🔍 Cách hoạt động từng bước

### Xét dữ liệu bảng `Cinema` mẫu:

| id | movie | description | rating |
|:---:|:---|:---|:---:|
| 1 | War | great 3D | 8.9 |
| 2 | Science | fiction | 8.5 |
| 3 | irish | boring | 6.2 |
| 4 | Ice song | Fantacy | 8.6 |
| 5 | House card | Interesting | 9.1 |

#### Bước 1: Kiểm tra điều kiện `id % 2 = 1` (Tìm các dòng có ID lẻ)
- `id = 1`: $1 \pmod 2 = 1$ ✅ Thỏa mãn
- `id = 2`: $2 \pmod 2 = 0$ ❌ Loại
- `id = 3`: $3 \pmod 2 = 1$ ✅ Thỏa mãn
- `id = 4`: $4 \pmod 2 = 0$ ❌ Loại
- `id = 5`: $5 \pmod 2 = 1$ ✅ Thỏa mãn

Các dòng được giữ lại: `id` là 1, 3, 5.

#### Bước 2: Kiểm tra điều kiện `description <> 'boring'`
Trong các dòng có `id` lẻ:
- `id = 1`: `description = 'great 3D'` (khác `'boring'`) ✅ Thỏa mãn
- `id = 3`: `description = 'boring'` ❌ Bị loại
- `id = 5`: `description = 'Interesting'` (khác `'boring'`) ✅ Thỏa mãn

Các dòng thỏa mãn cả 2 điều kiện: `id` là 1 và 5.

#### Bước 3: Sắp xếp theo `rating DESC`
- `id = 5`: `rating = 9.1`
- `id = 1`: `rating = 8.9`

Vì $9.1 > 8.9$, dòng `id = 5` được đưa lên đầu, tiếp theo là dòng `id = 1`.

#### Kết quả cuối cùng:
```text
+----+------------+-------------+--------+
| id | movie      | description | rating |
+----+------------+-------------+--------+
| 5  | House card | Interesting | 9.1    |
| 1  | War        | great 3D    | 8.9    |
+----+------------+-------------+--------+
```

---

## 🔄 CÁC CÁCH TIẾP CẬN KHÁC (ALTERNATIVE APPROACHES)

### 1. Sử dụng hàm `MOD(id, 2)` (Chuẩn ANSI SQL)

```sql
SELECT id, movie, description, rating
FROM Cinema
WHERE MOD(id, 2) = 1
  AND description <> 'boring'
ORDER BY rating DESC;
```
- **Ưu điểm:** Hàm `MOD(N, M)` là hàm tích hợp chuẩn trong ANSI SQL, tương thích cao với Oracle, MySQL, PostgreSQL.
- **Lưu ý:** Với các số nguyên dương $id \ge 1$, `MOD(id, 2) = 1` hoặc `MOD(id, 2) <> 0` đều lọc được các số lẻ.

---

### 2. Sử dụng phép toán Bitwise `(id & 1) = 1`

```sql
SELECT id, movie, description, rating
FROM Cinema
WHERE (id & 1) = 1
  AND description != 'boring'
ORDER BY rating DESC;
```
- **Cơ chế:** Phép toán `AND` nhị phân trên bit trọng số thấp nhất (LSB). Nếu bit cuối là `1` thì số đó là số lẻ, nếu bit cuối là `0` thì là số chẵn.
- **Ưu điểm:** Tốc độ tính toán cấp độ vi xử lý (CPU bitwise) cực nhanh.
- **Nhược điểm:** Kém trực quan với người đọc code SQL thông thường và phụ thuộc vào sự hỗ trợ toán tử bitwise của từng engine CSDL.

---

### 3. Sử dụng `NOT LIKE` hoặc `NOT (description = 'boring')`

```sql
SELECT id, movie, description, rating
FROM Cinema
WHERE id % 2 = 1
  AND description NOT LIKE 'boring'
ORDER BY rating DESC;
```
- **Đánh giá:** `NOT LIKE 'boring'` so khớp chuỗi không khớp mẫu `'boring'`. Tuy nhiên với so sánh giá trị chính xác cố định, toán tử so sánh chuỗi chuẩn (`<>` hoặc `!=`) luôn tối ưu hơn `LIKE`.

---

## ⏱️ Đánh giá độ phức tạp (Complexity Analysis)

| Tiêu chí | Đánh giá | Chi tiết |
|:---|:---|:---|
| **Thời gian (Time Complexity)** | $\mathcal{O}(N \log N)$ | Duyệt qua toàn bộ $N$ bản ghi để lọc điều kiện mất $\mathcal{O}(N)$. Sắp xếp kết quả thỏa mãn mất $\mathcal{O}(K \log K)$ với $K \le N$, trong trường hợp xấu nhất là $\mathcal{O}(N \log N)$. |
| **Không gian (Space Complexity)** | $\mathcal{O}(N)$ | Cần bộ nhớ đệm (sort buffer) để lưu trữ tập kết quả lọc được và thực hiện thuật toán sắp xếp. |

---

## 💡 Tổng kết kinh nghiệm phỏng vấn & Best Practices (Key Takeaways)

1. **Chuẩn hóa toán tử so sánh khác:** Trong SQL, ký hiệu `<>` là chuẩn ISO/ANSI được khuyến nghị sử dụng thay cho `!=` để đảm bảo code tương thích trên mọi hệ quản trị CSDL.
2. **Cẩn thận với giá trị `NULL` trong phép so sánh:**
   - Trong SQL theo logic 3 giá trị (Three-valued logic: `TRUE`, `FALSE`, `UNKNOWN`), nếu cột `description` chứa giá trị `NULL`, biểu thức `NULL <> 'boring'` sẽ trả về `UNKNOWN` và dòng đó sẽ bị loại bởi mệnh đề `WHERE`. Nếu yêu cầu bài toán muốn lấy cả các dòng có `description` là `NULL`, ta cần viết tường minh: `(description <> 'boring' OR description IS NULL)`.
3. **Cách kiểm tra số lẻ an toàn:**
   - Với ID luôn là số nguyên dương $\ge 1$, `id % 2 = 1`, `MOD(id, 2) = 1`, hoặc `id % 2 != 0` đều cho kết quả chính xác như nhau.
