# 📚 Giải thích Chi tiết Bài 626 - Exchange Seats

**Mục tiêu:** Viết truy vấn SQL hoán đổi vị trí chỗ ngồi (`id`) của từng cặp hai học sinh liên tiếp. Nếu tổng số lượng học sinh là số lẻ, học sinh ở vị trí cuối cùng sẽ giữ nguyên chỗ ngồi. Kết quả trả về phải được sắp xếp theo `id` tăng dần.

---

## 🎯 Phân tích bài toán

### 1. Cấu trúc dữ liệu đầu vào
Bảng `Seat` gồm hai cột:
- `id` (`int`): Khóa chính chứa giá trị định danh duy nhất của mỗi chỗ ngồi. Đề bài đảm bảo dãy `id` luôn bắt đầu từ 1 và tăng liên tục ($1, 2, 3, \dots$).
- `student` (`varchar`): Tên của học sinh đang ngồi ở ghế tương ứng.

---

### 2. Bản chất của việc đổi chỗ (Exchange Seats)
Quy tắc hoán đổi áp dụng cho **từng cặp liên tiếp**:
- Cặp 1: Ghế 1 $\leftrightarrow$ Ghế 2
- Cặp 2: Ghế 3 $\leftrightarrow$ Ghế 4
- Cặp $k$: Ghế $(2k - 1) \leftrightarrow$ Ghế $2k$

Từ quy luật trên, ta có hai góc nhìn để giải quyết bài toán:

| Góc nhìn | Bản chất giải thuật | Công cụ đề xuất |
| :--- | :--- | :--- |
| **Góc nhìn 1: Tráo đổi học sinh (`student`)** | Giữ nguyên cột `id`, đổi giá trị cột `student` cho nhau. | **Window Functions (`LEAD` & `LAG`)** |
| **Góc nhìn 2: Tráo đổi số ghế (`id`)** | Giữ nguyên cột `student`, tính toán lại giá trị cột `id` rồi sắp xếp lại. | **`CASE WHEN` + Toán tử số học / Bitwise** |

---

### 3. Trường hợp biên (Edge Case): Tổng số học sinh là số lẻ
Nếu tổng số học sinh $N$ là số lẻ:
- Các cặp $(1, 2), (3, 4), \dots, (N-2, N-1)$ vẫn đổi chỗ bình thường.
- Học sinh cuối cùng mang số ghế $N$ (là số lẻ) **không có ai đi kèm để đổi** $\rightarrow$ phải giữ nguyên vị trí.

---

## ⭐ GIẢI PHÁP TỐI ƯU 1 (Khuyên dùng): Window Functions (`LEAD` & `LAG`)

Đây là cách tiếp cận hiện đại, thanh lịch và tối ưu nhất trong các hệ quản trị CSDL hỗ trợ Window Functions (MySQL 8.0+, PostgreSQL, Oracle, SQL Server, SQLite 3.25+).

### 📝 Mã truy vấn SQL

Đoạn mã có sẵn trong [`Solution.sql`](file:///f:/daily-code-challenge/problems-from-401-to-500/problem-449/Solution.sql):

```sql
SELECT 
    id,
    CASE 
        WHEN id % 2 = 1 THEN COALESCE(LEAD(student) OVER (ORDER BY id), student)
        ELSE LAG(student) OVER (ORDER BY id)
    END AS student
FROM Seat
ORDER BY id ASC;
```

---

### 🔍 Cơ chế hoạt động chi tiết

Ta giữ nguyên số ghế `id` và dùng Window Functions để lấy tên học sinh cần hoán đổi:

1. **Khi `id` là số chẵn (`id % 2 = 0`):**
   - Học sinh ở ghế chẵn cần đổi chỗ với học sinh ở ghế lẻ ngay phía trước nó.
   - Hàm `LAG(student) OVER (ORDER BY id)` sẽ lấy giá trị `student` của dòng liền trước.

2. **Khi `id` là số lẻ (`id % 2 = 1`):**
   - Học sinh ở ghế lẻ cần đổi chỗ với học sinh ở ghế chẵn ngay phía sau nó.
   - Hàm `LEAD(student) OVER (ORDER BY id)` sẽ lấy giá trị `student` của dòng liền sau.
   - **Xử lý dòng lẻ cuối cùng:** Nếu đây là dòng cuối cùng của bảng, phía sau không còn dòng nào nữa $\rightarrow$ `LEAD(...)` sẽ trả về `NULL`.
   - Hàm `COALESCE(LEAD(...), student)` sẽ tự động phát hiện `NULL` và giữ nguyên tên học sinh hiện tại (`student`)!

#### Minh họa từng bước với dữ liệu mẫu:

| id | student gốc | id % 2 | Hàm Window | Kết quả student mới |
| :-: | :--- | :-: | :--- | :--- |
| **1** | Abbot | Lẻ | `COALESCE(LEAD('Doris'), 'Abbot')` | **Doris** |
| **2** | Doris | Chẵn | `LAG('Abbot')` | **Abbot** |
| **3** | Emerson | Lẻ | `COALESCE(LEAD('Green'), 'Emerson')` | **Green** |
| **4** | Green | Chẵn | `LAG('Emerson')` | **Emerson** |
| **5** | Jeames | Lẻ (Cuối) | `COALESCE(NULL, 'Jeames')` | **Jeames** |

---

### 🌟 Ưu điểm của giải pháp Window Functions
- **Không cần subquery phụ:** Không phải quét lại bảng để lấy `COUNT(*)` như phương pháp cổ điển.
- **Không thay đổi khóa chính `id`:** Tránh việc phải tính toán lại giá trị `id` rồi xáo trộn và tốn chi phí sắp xếp lại toàn bộ bảng.
- **Tự động xử lý biên:** `COALESCE` kết hợp với `LEAD()` xử lý trường hợp số lượng phần tử lẻ một cách tự nhiên mà không cần thêm điều kiện logic phức tạp.

---

## 🚀 GIẢI PHÁP 2 (Kinh điển / ANSI SQL): Đổi `id` bằng `CASE WHEN` & Subquery `COUNT(*)`

Phương pháp này phù hợp khi làm việc với các hệ RDBMS đời cũ hơn (như MySQL 5.7 trở về trước) chưa hỗ trợ Window Functions.

### 📝 Mã truy vấn SQL

```sql
SELECT 
    CASE 
        WHEN id % 2 = 1 AND id = (SELECT COUNT(*) FROM Seat) THEN id
        WHEN id % 2 = 1 THEN id + 1
        ELSE id - 1
    END AS id,
    student
FROM Seat
ORDER BY id ASC;
```

---

### 🔍 Cơ chế hoạt động
Trong cách này, ta giữ nguyên tên học sinh và gán lại số ghế `id`:
1. **Dòng lẻ cuối cùng:** `id % 2 = 1 AND id = (SELECT COUNT(*) FROM Seat)` $\rightarrow$ giữ nguyên `id`.
2. **Các dòng lẻ còn lại:** `id % 2 = 1` $\rightarrow$ tăng thành `id + 1` (ví dụ: ghế 1 thành 2, ghế 3 thành 4).
3. **Các dòng chẵn:** `ELSE` $\rightarrow$ giảm thành `id - 1` (ví dụ: ghế 2 thành 1, ghế 4 thành 3).
4. **Sắp xếp lại:** Bắt buộc phải có `ORDER BY id ASC` vì các giá trị `id` vừa được tính toán lại đã bị đảo lộn thứ tự ban đầu.

---

## 💡 CÁC GIẢI PHÁP THAM KHẢO KHÁC

### Cách 3: Sử dụng toán tử Bitwise XOR
Trong hệ nhị phân, phép XOR giữa một số và 1 có tính chất:
- `(id - 1) ^ 1 + 1`:
  - $id = 1 \implies (0 \oplus 1) + 1 = 1 + 1 = 2$
  - $id = 2 \implies (1 \oplus 1) + 1 = 0 + 1 = 1$
  - $id = 3 \implies (2 \oplus 1) + 1 = 3 + 1 = 4$
  - $id = 4 \implies (3 \oplus 1) + 1 = 2 + 1 = 3$

Mã truy vấn:
```sql
SELECT 
    CASE 
        WHEN id % 2 = 1 AND id = (SELECT COUNT(*) FROM Seat) THEN id
        ELSE (id - 1) ^ 1 + 1
    END AS id,
    student
FROM Seat
ORDER BY id ASC;
```

### Cách 4: Tự kết nối bảng (`Self LEFT JOIN`)
Ghép bảng `Seat s1` với chính nó `Seat s2`:
- Ghế lẻ nối với dòng sau: `s1.id % 2 = 1 AND s1.id + 1 = s2.id`
- Ghế chẵn nối với dòng trước: `s1.id % 2 = 0 AND s1.id - 1 = s2.id`

```sql
SELECT 
    s1.id,
    COALESCE(s2.student, s1.student) AS student
FROM Seat s1
LEFT JOIN Seat s2 
    ON (s1.id % 2 = 1 AND s1.id + 1 = s2.id)
    OR (s1.id % 2 = 0 AND s1.id - 1 = s2.id)
ORDER BY s1.id ASC;
```

---

## 📊 Bảng so sánh tổng hợp các phương pháp

| Tiêu chí | Cách 1: Window Functions (`LEAD`/`LAG`) | Cách 2: `CASE WHEN` + `COUNT(*)` | Cách 3: Bitwise XOR | Cách 4: Self `LEFT JOIN` |
| :--- | :--- | :--- | :--- | :--- |
| **Độ phức tạp thời gian** | $\mathcal{O}(N)$ | $\mathcal{O}(N \log N)$ | $\mathcal{O}(N \log N)$ | $\mathcal{O}(N)$ |
| **Độ phức tạp bộ nhớ** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Truy vấn phụ (Subquery)** | ❌ Không cần | ✔️ Cần `COUNT(*)` | ✔️ Cần `COUNT(*)` | ❌ Không cần |
| **Phải sort lại sau biến đổi** | ❌ (id giữ nguyên) | ✔️ Bắt buộc `ORDER BY` | ✔️ Bắt buộc `ORDER BY` | ❌ (id giữ nguyên) |
| **Tính tương thích phiên bản** | MySQL 8.0+, PG, SQL Server | Mọi phiên bản SQL | Hầu hết RDBMS | Mọi phiên bản SQL |
| **Độ trực quan & dễ bảo trì** | ⭐⭐⭐⭐⭐ (Rất cao) | ⭐⭐⭐⭐ (Khá) | ⭐⭐⭐ (Thủ thuật) | ⭐⭐⭐ (Hơi phức tạp) |

---

## 💡 Lưu ý thực tế & Kinh nghiệm phỏng vấn

1. **Short-circuit Evaluation trong `CASE WHEN`:**
   - Trong Cách 2, điều kiện kiểm tra dòng cuối cùng `id = (SELECT COUNT(*) FROM Seat)` **phải được đặt trước** điều kiện tổng quát `id % 2 = 1`. Nếu đảo thứ tự, dòng cuối cùng sẽ bị rơi vào nhánh `id + 1` và tạo ra số ghế vượt quá tổng số học sinh!
2. **`COALESCE` vs `IFNULL`:**
   - `COALESCE` là hàm chuẩn ANSI SQL được hỗ trợ trên tất cả các hệ cơ sở dữ liệu.
   - `IFNULL` là hàm đặc thù của riêng MySQL. Khi viết mã chuẩn để dễ migrate CSDL, ưu tiên dùng `COALESCE`.
3. **Hiệu năng thực tế:**
   - Khi bảng có hàng triệu bản ghi, cách 1 (`LEAD`/`LAG`) tận dụng clustered index trên cột `id` để stream dữ liệu một lần duy nhất theo thứ tự, mang lại hiệu năng cao và ổn định nhất.
