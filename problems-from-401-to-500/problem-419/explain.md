# 📚 Giải thích Bài 577 - Employee Bonus

**Mục tiêu:** Báo cáo tên (`name`) và số tiền thưởng (`bonus`) của mỗi nhân viên thỏa mãn một trong hai điều kiện:
1. Có tiền thưởng nhỏ hơn `1000`.
2. Không nhận được bất kỳ khoản tiền thưởng nào (không có trong bảng `Bonus` hoặc tiền thưởng là `null`).

---

## 🎯 Phân tích bài toán

Bài toán cung cấp hai bảng:
- **`Employee`**: Chứa thông tin nhân viên (`empId`, `name`, `supervisor`, `salary`). Cột `empId` là khóa chính (chứa các giá trị duy nhất).
- **`Bonus`**: Chứa thông tin tiền thưởng (`empId`, `bonus`). Cột `empId` là khóa ngoại trỏ tới bảng `Employee`.

### ⚠️ Cạm bẫy thường gặp (Common Pitfalls):

1. **Sử dụng `INNER JOIN` thay vì `LEFT JOIN`:**
   - Nhiều nhân viên không hề có tên trong bảng `Bonus` (như Brad và John trong ví dụ).
   - Nếu sử dụng `INNER JOIN`, các nhân viên không có bản ghi thưởng tương ứng sẽ bị loại bỏ hoàn toàn khỏi kết quả.
   - 👉 **Quy tắc:** Khi bài toán yêu cầu giữ lại cả các đối tượng "không có dữ liệu liên quan", ta phải sử dụng `LEFT JOIN`.

2. **Bẫy giá trị `NULL` trong SQL (Three-Valued Logic):**
   - Trong SQL, phép toán so sánh với `NULL` (ví dụ: `NULL < 1000`) không trả về `TRUE` mà trả về `UNKNOWN` (logic 3 giá trị: `TRUE`, `FALSE`, `UNKNOWN`).
   - Mệnh đề `WHERE` chỉ giữ lại các dòng có kết quả đánh giá là `TRUE`. Do đó, nếu chỉ viết `WHERE bonus < 1000`, tất cả các dòng có `bonus IS NULL` sẽ bị loại bỏ!
   - 👉 **Cách giải quyết:** Bắt buộc phải thêm điều kiện `OR bonus IS NULL` (hoặc sử dụng hàm `IFNULL(bonus, 0) < 1000`).

---

## ⭐ BEST SOLUTION: LEFT JOIN với Điều kiện NULL

Giải pháp chuẩn mực ANSI SQL, tối ưu và tương thích tốt nhất với bộ tối ưu hóa truy vấn (Query Optimizer):

```sql
SELECT 
    e.name, 
    b.bonus
FROM Employee e
LEFT JOIN Bonus b ON e.empId = b.empId
WHERE b.bonus < 1000 OR b.bonus IS NULL;
```

---

## 🔍 Cách hoạt động từng bước

### Bước 1: Kết nối ngoại bên trái (`LEFT JOIN`)

- Bắt đầu với bảng `Employee` (bảng bên trái) và kết nối với bảng `Bonus` dựa trên điều kiện `e.empId = b.empId`.
- Với các nhân viên không có bản ghi trong bảng `Bonus`, các cột thuộc bảng `Bonus` (như `bonus`) sẽ tự động được điền giá trị `NULL`.

**Minh họa dữ liệu sau khi `LEFT JOIN`:**

| e.empId | e.name | e.supervisor | e.salary | b.empId | b.bonus |
|:--------|:-------|:-------------|:---------|:--------|:--------|
| 3       | Brad   | null         | 4000     | null    | null    |
| 1       | John   | 3            | 1000     | null    | null    |
| 2       | Dan    | 3            | 2000     | 2       | 500     |
| 4       | Thomas | 3            | 4000     | 4       | 2000    |

---

### Bước 2: Lọc dữ liệu qua mệnh đề `WHERE`

Điều kiện lọc: `b.bonus < 1000 OR b.bonus IS NULL`

Đánh giá từng dòng:
- **Brad**: `b.bonus` là `NULL` $\to$ `NULL < 1000` là `UNKNOWN`, nhưng `b.bonus IS NULL` là `TRUE` $\to$ **Thỏa mãn** ✅
- **John**: `b.bonus` là `NULL` $\to$ `b.bonus IS NULL` là `TRUE` $\to$ **Thỏa mãn** ✅
- **Dan**: `b.bonus = 500 < 1000` là `TRUE` $\to$ **Thỏa mãn** ✅
- **Thomas**: `b.bonus = 2000 < 1000` là `FALSE`, `b.bonus IS NULL` là `FALSE` $\to$ **Loại bỏ** ❌

---

### Bước 3: Lựa chọn các cột đầu ra (`SELECT e.name, b.bonus`)

Lấy ra 2 cột theo yêu cầu: tên nhân viên (`name`) và số tiền thưởng tương ứng (`bonus`).

**Bảng kết quả cuối cùng:**

| name | bonus |
|:-----|:------|
| Brad | null  |
| John | null  |
| Dan  | 500   |

---

## 🔄 Các giải pháp thay thế (Alternative Solutions)

### Alternative 1: Sử dụng `IFNULL()` hoặc `COALESCE()`

Thay vì kiểm tra `OR b.bonus IS NULL`, ta có thể chuyển đổi giá trị `NULL` thành `0`:

```sql
SELECT 
    e.name, 
    b.bonus
FROM Employee e
LEFT JOIN Bonus b ON e.empId = b.empId
WHERE IFNULL(b.bonus, 0) < 1000;
```

*(Hoặc dùng hàm chuẩn ANSI `COALESCE(b.bonus, 0) < 1000`)*

- **Ưu điểm:** Mệnh đề `WHERE` ngắn gọn hơn.
- **Lưu ý:** Việc bọc cột `b.bonus` bên trong một hàm (`IFNULL`/`COALESCE`) có thể khiến câu query trở nên non-sargable (khó tận dụng chỉ mục B-Tree trên cột `bonus` nếu có so với điều kiện so sánh trực tiếp).

---

### Alternative 2: Tách biệt bằng `UNION ALL`

Tách bài toán thành hai truy vấn rõ ràng:
1. Những người có thưởng và thưởng $< 1000$.
2. Những người không có trong bảng thưởng (`NOT IN` hoặc `NOT EXISTS`).

```sql
-- Phần 1: Nhân viên có thưởng < 1000
SELECT e.name, b.bonus
FROM Employee e
JOIN Bonus b ON e.empId = b.empId
WHERE b.bonus < 1000

UNION ALL

-- Phần 2: Nhân viên không nhận thưởng
SELECT e.name, NULL AS bonus
FROM Employee e
WHERE e.empId NOT IN (SELECT empId FROM Bonus);
```

- **Ưu điểm:** Tách bạch 2 luồng nghiệp vụ riêng biệt.
- **Nhược điểm:** Dài dòng và phải quét qua bảng `Employee` hai lần.

---

## 📊 So sánh các giải pháp

| Phương pháp | Độ phức tạp thời gian | Tận dụng Index | Chuẩn ANSI SQL | Đánh giá khi phỏng vấn |
|:------------|:----------------------|:---------------|:---------------|:-----------------------|
| **LEFT JOIN + OR IS NULL (Best)** ⭐ | $\mathcal{O}(N + M)$ | Rất tốt (Index on `Bonus.empId`) | Chuẩn ANSI cao | ✅ **Khuyên dùng số 1** |
| **LEFT JOIN + IFNULL / COALESCE** | $\mathcal{O}(N + M)$ | Tốt | Phổ biến trong MySQL | ⭐ Ngắn gọn, dễ viết |
| **UNION ALL** | $\mathcal{O}(N + M)$ | Tốt | Chuẩn ANSI cao | Hơi dài dòng |

---

## 💡 Điểm lưu ý quan trọng (Key Takeaways)

1. **Hiểu rõ Three-Valued Logic (3VL):**
   - Trong SQL, kết quả của một biểu thức logic có thể là `TRUE`, `FALSE`, hoặc `UNKNOWN`.
   - Bất kỳ phép so sánh toán học nào với `NULL` (`= NULL`, `!= NULL`, `< NULL`, `> NULL`) đều cho ra `UNKNOWN`.
   - Muốn kiểm tra giá trị `NULL`, bắt buộc phải dùng toán tử `IS NULL` hoặc `IS NOT NULL`.

2. **Khi nào dùng `LEFT JOIN` thay vì `INNER JOIN`:**
   - Dùng `INNER JOIN` khi bạn chỉ muốn lấy những bản ghi có quan hệ ở cả hai bảng.
   - Dùng `LEFT JOIN` khi bạn muốn giữ lại **toàn bộ bản ghi của bảng bên trái**, bất kể bảng bên phải có dữ liệu tương ứng hay không.

3. **Index Optimization (Tối ưu hóa chỉ mục):**
   - Bảng `Bonus` nên có index trên cột `empId` (thường là khóa ngoại) để phép `LEFT JOIN` đạt độ phức tạp tìm kiếm $\mathcal{O}(1)$ cho mỗi dòng của bảng `Employee`.

---

## 📈 Độ phức tạp (Complexity Analysis)

- **Độ phức tạp thời gian (Time Complexity):** $\mathcal{O}(N + M)$ trong trường hợp Hash Join, hoặc $\mathcal{O}(N \log M)$ nếu sử dụng Index Nested Loop Join (với $N$ là số lượng nhân viên trong `Employee` và $M$ là số lượng bản ghi trong `Bonus`).
- **Độ phức tạp không gian (Space Complexity):** $\mathcal{O}(1)$ bộ nhớ phụ trợ ngoài không gian lưu trữ kết quả trả về.
