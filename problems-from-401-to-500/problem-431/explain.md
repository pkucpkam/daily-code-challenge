# 📚 Giải thích Bài 596 - Classes With at Least 5 Students

**Mục tiêu:** Tìm tất cả các lớp học (`class`) có từ 5 học sinh trở lên từ bảng `Courses`.

---

## 🎯 Phân tích bài toán

Bảng `Courses` có cấu trúc:
- `student` (varchar): Tên học sinh.
- `class` (varchar): Tên lớp học mà học sinh đó đăng ký tham gia.
- `(student, class)` là **Khóa chính (Primary Key)**: Đảm bảo mỗi cặp `(student, class)` là duy nhất, tức một học sinh không bao giờ xuất hiện hai lần trong cùng một lớp học.

### 📋 Yêu cầu cốt lõi
1. Đếm số lượng học sinh trong từng lớp học.
2. Lọc ra những lớp học có **tối thiểu 5 học sinh** ($\ge 5$).
3. Trả về cột `class` theo thứ tự bất kỳ (`Return the result table in any order`).

### 💡 Điểm mấu chốt cần lưu ý
- **Toán tử so sánh:** Đề bài yêu cầu "at least 5" (ít nhất 5 / từ 5 trở lên), do đó điều kiện phải là **lớn hơn hoặc bằng** (`>= 5`), không phải lớn hơn nghiêm ngặt (`> 5`).
- **Khóa chính `(student, class)`:** Vì cặp `(student, class)` đã là khóa chính, trong mỗi nhóm `class` thì mỗi sinh viên chỉ xuất hiện đúng 1 lần. Do đó, `COUNT(student)` hay `COUNT(*)` đều cho kết quả chuẩn xác mà không cần tốn chi phí `DISTINCT`.

---

## ⭐ BEST SOLUTION: Mệnh đề `GROUP BY` kết hợp `HAVING`

Đây là cách tiếp cận chuẩn mực, ngắn gọn và đạt hiệu năng cao nhất trong SQL:

```sql
SELECT class
FROM Courses
GROUP BY class
HAVING COUNT(student) >= 5;
```

*(Lưu ý: Có thể dùng `HAVING COUNT(*) >= 5` thay thế, hiệu năng và kết quả là tương đương).*

### 🌟 Tại sao đây là giải pháp tốt nhất?
1. **Chuẩn mực SQL (Idiomatic SQL):** Việc gom nhóm dữ liệu theo một thuộc tính (`class`) và lọc các nhóm sau khi tổng hợp bằng mệnh đề `HAVING` là mô hình thiết kế chuẩn của ngôn ngữ SQL.
2. **Ngắn gọn, dễ đọc:** Chỉ 4 dòng truy vấn thể hiện trực tiếp yêu cầu đề bài.
3. **Hiệu năng cao:** Chỉ cần một lượt quét bảng (Single Pass Table Scan) để gom nhóm (bằng Hash Table hoặc B-Tree Index trên cột `class`), không tạo bảng tạm lồng nhau.

---

## 🔍 Cách hoạt động từng bước

Xét dữ liệu mẫu đầu vào của bảng `Courses`:

| student | class    |
|:--------|:---------|
| A       | Math     |
| B       | English  |
| C       | Math     |
| D       | Biology  |
| E       | Math     |
| F       | Computer |
| G       | Math     |
| H       | Math     |
| I       | Math     |

### Bước 1: Gom nhóm theo lớp học `GROUP BY class`

Hệ cơ sở dữ liệu sẽ phân chia toàn bộ các bản ghi thành các nhóm riêng biệt theo giá trị của cột `class`:
- Nhóm `Math`: gồm các học sinh `A, C, E, G, H, I`
- Nhóm `English`: gồm học sinh `B`
- Nhóm `Biology`: gồm học sinh `D`
- Nhóm `Computer`: gồm học sinh `F`

### Bước 2: Tính toán hàm tổng hợp `COUNT(student)` cho từng nhóm

| class    | COUNT(student) |
|:---------|:---------------|
| Math     | 6              |
| English  | 1              |
| Biology  | 1              |
| Computer | 1              |

### Bước 3: Lọc nhóm thỏa mãn điều kiện `HAVING COUNT(student) >= 5`

- `Math`: $6 \ge 5$ $\rightarrow$ **Thỏa mãn (Giữ lại)**
- `English`: $1 < 5$ $\rightarrow$ Loại bỏ
- `Biology`: $1 < 5$ $\rightarrow$ Loại bỏ
- `Computer`: $1 < 5$ $\rightarrow$ Loại bỏ

### Kết quả cuối cùng:

```text
+-------+
| class |
+-------+
| Math  |
+-------+
```

---

## 🔄 CÁC CÁCH TIẾP CẬN KHÁC

### 1. Cách tiếp cận phòng thủ: `COUNT(DISTINCT student)`

Trong phiên bản cũ của bài toán này trên LeetCode (hoặc trong hệ thống thực tế khi dữ liệu chưa được làm sạch và bảng không có khóa chính `(student, class)`), một học sinh có thể bị nhập trùng nhiều lần vào cùng một lớp. Khi đó ta cần dùng `DISTINCT`:

```sql
SELECT class
FROM Courses
GROUP BY class
HAVING COUNT(DISTINCT student) >= 5;
```

- **Ưu điểm:** An toàn tuyệt đối trước dữ liệu trùng lặp.
- **Nhược điểm:** Tốn thêm chi phí CPU và bộ nhớ để thực hiện khử trùng (Dedup) các tên học sinh trong mỗi nhóm. Vì đề bài hiện tại đã quy định `(student, class)` là Primary Key nên không bắt buộc dùng `DISTINCT`.

---

### 2. Sử dụng truy vấn con (Subquery / Derived Table)

Ta có thể gom nhóm và đếm trước trong một bảng tạm, sau đó dùng `WHERE` ở truy vấn bên ngoài để lọc:

```sql
SELECT class
FROM (
    SELECT class, COUNT(student) AS student_count
    FROM Courses
    GROUP BY class
) AS temp
WHERE student_count >= 5;
```

- **Đánh giá:** Dài dòng hơn, không cần thiết khi SQL đã cung cấp sẵn mệnh đề `HAVING` để lọc nhóm trực tiếp.

---

## ⚠️ Các lỗi thường gặp (Common Pitfalls)

### 1. Nhầm lẫn giữa `WHERE` và `HAVING`
- ❌ **Sai:**
  ```sql
  SELECT class
  FROM Courses
  WHERE COUNT(student) >= 5
  GROUP BY class;
  ```
  *(Sẽ gây lỗi `Invalid use of group function` vì mệnh đề `WHERE` được thực thi **trước** khi dữ liệu được gom nhóm, nên không thể tính toán các hàm tổng hợp như `COUNT`, `SUM`, `AVG`).*
- ✔️ **Đúng:** Dùng `HAVING` để lọc **sau** khi đã gom nhóm.

### 2. Sai điều kiện biên
- Nhầm "at least 5" thành `> 5` thay vì `>= 5`. Khi một lớp có đúng 5 học sinh, điều kiện `> 5` sẽ bỏ sót lớp đó.

---

## 📊 Đánh giá độ phức tạp

- **Độ phức tạp thời gian (Time Complexity):** $\mathcal{O}(N)$ với $N$ là tổng số dòng trong bảng `Courses`. Hệ quản trị CSDL chỉ cần quét qua các dòng một lần để gom nhóm và đếm.
- **Độ phức tạp không gian (Space Complexity):** $\mathcal{O}(K)$ với $K$ là số lượng lớp học khác nhau, dùng để lưu trữ bảng băm (Hash Table) gom nhóm kết quả.
