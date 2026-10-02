# 📚 Giải thích Bài 619 - Biggest Single Number

**Mục tiêu:** Tìm số đơn lẻ (số chỉ xuất hiện đúng 1 lần) có giá trị lớn nhất trong bảng `MyNumbers`. Nếu không có số nào xuất hiện đúng 1 lần, trả về `null`.

---

## 🎯 Phân tích bài toán

Bảng `MyNumbers` có cấu trúc:
- `num` (`int`): Chứa một số nguyên.
- Bảng **không có khóa chính** và có thể chứa các giá trị trùng lặp.

### 📋 Yêu cầu cốt lõi
1. **Định nghĩa số đơn lẻ (Single Number):** Một số nguyên được gọi là "số đơn lẻ" nếu và chỉ nếu nó xuất hiện đúng **1 lần** duy nhất trong toàn bộ bảng `MyNumbers` (tức là tần suất xuất hiện `COUNT(num) = 1`).
2. **Tìm giá trị lớn nhất:** Trong tất cả các số đơn lẻ tìm được, chọn ra số có giá trị lớn nhất (`MAX`).
3. **Xử lý trường hợp biên (Edge Case quan trọng):** Nếu không có bất kỳ số đơn lẻ nào (hoặc bảng rỗng), kết quả trả về bắt buộc phải là **`null`** trong 1 dòng với tên cột là `num`, chứ **không được trả về tập kết quả rỗng (0 dòng)**.

---

## ⭐ BEST SOLUTION: Mệnh đề `GROUP BY ... HAVING` kết hợp hàm `MAX()`

Đoạn mã truy vấn tối ưu trong [`Solution.sql`](file:///f:/daily-code-challenge/problems-from-401-to-500/problem-443/Solution.sql):

```sql
SELECT MAX(num) AS num
FROM (
    SELECT num
    FROM MyNumbers
    GROUP BY num
    HAVING COUNT(num) = 1
) AS single_numbers;
```

---

### 🌟 Tại sao đây là giải pháp tốt nhất?

1. **Tự động xử lý `NULL` chuẩn xác tuyệt đối (Graceful NULL Handling):**
   - Theo chuẩn **ANSI SQL**, các hàm tổng hợp như `MAX()`, `MIN()`, `SUM()`, `AVG()` khi thực thi trên một tập dữ liệu rỗng (0 bản ghi) sẽ **tự động trả về `NULL`** dưới dạng một bảng kết quả gồm 1 dòng 1 cột.
   - Nhờ đặc tính này, nếu subquery bên trong không tìm thấy số đơn lẻ nào (trả về 0 dòng), `MAX(num)` bên ngoài sẽ lập tức trả về `null` mà ta **không cần** dùng đến các hàm điều kiện phức tạp như `IFNULL`, `COALESCE`, `UNION` hay `CASE WHEN`.

2. **Hiệu năng tối ưu ($\mathcal{O}(N)$):**
   - Subquery gom nhóm các số bằng `GROUP BY num` và lọc qua `HAVING COUNT(num) = 1` chỉ cần một lượt duyệt bảng (Single Pass Table Scan) thông qua bảng băm (Hash Aggregation).
   - Truy vấn ngoài chỉ cần tìm giá trị lớn nhất trong danh sách các số đơn lẻ thông qua phép toán `MAX()` với độ phức tạp tuyến tính $\mathcal{O}(K)$ (với $K$ là số lượng số đơn lẻ), hoàn toàn **không cần sắp xếp toàn bộ dữ liệu** ($\mathcal{O}(K \log K)$).

3. **Chuẩn mực và tương thích tuyệt đối (Universal SQL Compatibility):**
   - Cú pháp trên chuẩn 100% ANSI SQL, tương thích và chạy tối ưu trên mọi hệ quản trị cơ sở dữ liệu quan hệ (MySQL, PostgreSQL, Oracle, Microsoft SQL Server, SQLite).

---

## 🔍 Cách hoạt động từng bước

### Xét Ví dụ 1:
```text
MyNumbers: [8, 8, 3, 3, 1, 4, 5, 6]
```

#### Bước 1: Subquery gom nhóm và tính số lần xuất hiện
Hệ quản trị CSDL nhóm các dòng có cùng giá trị `num` và đếm:

| num | COUNT(num) |
|:---:|:---:|
| 8   | 2   |
| 3   | 2   |
| 1   | 1   |
| 4   | 1   |
| 5   | 1   |
| 6   | 1   |

#### Bước 2: Lọc các số có `COUNT(num) = 1` qua mệnh đề `HAVING`
Các số có tần suất xuất hiện bằng 1 là: `1, 4, 5, 6`.

Subquery `single_numbers` trả về:
```text
+-----+
| num |
+-----+
| 1   |
| 4   |
| 5   |
| 6   |
+-----+
```

#### Bước 3: Hàm `MAX(num)` tìm giá trị lớn nhất
Trong tập `{1, 4, 5, 6}`, giá trị lớn nhất là `6`.

**Kết quả:**
```text
+-----+
| num |
+-----+
| 6   |
+-----+
```

---

### Xét Ví dụ 2:
```text
MyNumbers: [8, 8, 7, 7, 3, 3, 3]
```

#### Bước 1 & 2: Subquery gom nhóm và lọc
- Số `8`: xuất hiện 2 lần.
- Số `7`: xuất hiện 2 lần.
- Số `3`: xuất hiện 3 lần.

Sau mệnh đề `HAVING COUNT(num) = 1`, không có giá trị nào thỏa mãn. Subquery trả về tập rỗng (**0 dòng**).

#### Bước 3: Hàm `MAX(num)` xử lý tập rỗng
`MAX()` áp dụng trên tập rỗng sẽ tự động trả về giá trị `null`.

**Kết quả:**
```text
+------+
| num  |
+------+
| null |
+------+
```

---

## 🔄 CÁC CÁCH TIẾP CẬN KHÁC (ALTERNATIVE APPROACHES)

### 1. Scalar Subquery kết hợp `ORDER BY ... DESC LIMIT 1`

```sql
SELECT (
    SELECT num
    FROM MyNumbers
    GROUP BY num
    HAVING COUNT(num) = 1
    ORDER BY num DESC
    LIMIT 1
) AS num;
```

- **Cơ chế:** Khi đặt một truy vấn con trực tiếp vào mệnh đề `SELECT` (gọi là *Scalar Subquery*), nếu truy vấn con trả về 0 dòng, SQL sẽ tự động chuyển đổi giá trị đó thành `NULL`.
- **Ưu điểm:** Viết ngắn gọn.
- **Nhược điểm:**
  - Cần sắp xếp giảm dần toàn bộ các số đơn lẻ (`ORDER BY num DESC`), tốn chi phí $\mathcal{O}(K \log K)$ so với $\mathcal{O}(K)$ của hàm `MAX()`.
  - Phụ thuộc cú pháp phân trang của từng hệ CSDL: MySQL dùng `LIMIT 1`, SQL Server dùng `TOP 1`, Oracle dùng `FETCH FIRST 1 ROWS ONLY`.

---

### 2. Sử dụng CTE (Common Table Expression - Mệnh đề `WITH`)

```sql
WITH SingleNumbers AS (
    SELECT num
    FROM MyNumbers
    GROUP BY num
    HAVING COUNT(num) = 1
)
SELECT MAX(num) AS num
FROM SingleNumbers;
```

- **Đánh giá:** Logic hoàn toàn tương tự như giải pháp tốt nhất (Best Solution), nhưng tách riêng khối logic tìm số đơn lẻ ra phần CTE giúp câu lệnh mạch lạc, dễ đọc và dễ bảo trì khi truy vấn phức tạp hơn.

---

### 3. Sử dụng Window Function `COUNT(*) OVER (PARTITION BY num)`

```sql
WITH NumberFrequency AS (
    SELECT num, COUNT(*) OVER (PARTITION BY num) AS freq
    FROM MyNumbers
)
SELECT MAX(num) AS num
FROM NumberFrequency
WHERE freq = 1;
```

- **Đánh giá:** Phù hợp trong các kịch bản thực tế khi bảng dữ liệu có nhiều cột thông tin bổ sung và ta không muốn dùng `GROUP BY` làm gộp các dòng lại. Tuy nhiên đối với bài toán chỉ có duy nhất cột `num`, giải pháp này tốn nhiều chi phí phân vùng (partition) hơn so với `GROUP BY`.

---

## ⏱️ Đánh giá độ phức tạp (Complexity Analysis)

| Tiêu chí | Giải pháp tốt nhất (`MAX` + Subquery) | Sắp xếp (`ORDER BY` + `LIMIT 1`) |
|:---|:---|:---|
| **Thời gian (Time Complexity)** | $\mathcal{O}(N)$ | $\mathcal{O}(N + K \log K)$ |
| **Không gian (Space Complexity)** | $\mathcal{O}(U)$ | $\mathcal{O}(U)$ |

- Trong đó:
  - $N$ là tổng số dòng trong bảng `MyNumbers`.
  - $U$ là số lượng giá trị số phân biệt (unique numbers).
  - $K$ là số lượng số đơn lẻ ($K \le U \le N$).

---

## 💡 Tổng kết kinh nghiệm phỏng vấn (Key Takeaways)

1. **Hiểu rõ hành vi của hàm tổng hợp với tập dữ liệu rỗng:** Khi gặp bài toán SQL yêu cầu trả về `NULL` nếu không tìm thấy dữ liệu thỏa mãn điều kiện, hãy ưu tiên sử dụng hàm tổng hợp như `MAX()` hoặc `MIN()` vì chúng tự sinh `NULL` một cách an toàn mà không cần thêm logic điều kiện rườm rà.
2. **Ưu tiên `MAX()` hơn `ORDER BY ... LIMIT 1`:** Khi chỉ cần tìm giá trị lớn nhất/nhỏ nhất, hàm `MAX()` luôn tối ưu hơn việc sắp xếp (`ORDER BY`), đặc biệt khi tập dữ liệu trung gian lớn.
