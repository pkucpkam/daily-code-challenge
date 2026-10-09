# 📚 Giải thích Chi tiết Bài 627 - Swap Salary

**Mục tiêu:** Viết một câu lệnh cập nhật duy nhất (`UPDATE` statement) để hoán đổi toàn bộ giá trị giới tính trong cột `sex` của bảng `Salary`: từ `'m'` thành `'f'` và ngược lại từ `'f'` thành `'m'`. Không được sử dụng bất kỳ bảng tạm trung gian nào (`no intermediate temporary tables`) và không được dùng câu lệnh `SELECT`.

---

## 🎯 Phân tích bài toán

### 1. Cấu trúc dữ liệu đầu vào
Bảng `Salary` gồm:
- `id` (`int`): Khóa chính chứa ID duy nhất của từng nhân viên.
- `name` (`varchar`): Tên nhân viên.
- `sex` (`ENUM('m', 'f')`): Giới tính của nhân viên, chỉ nhận một trong hai giá trị `'m'` (male) hoặc `'f'` (female).
- `salary` (`int`): Lương của nhân viên.

---

### 2. Các ràng buộc nghiêm ngặt của đề bài
1. **Chỉ dùng một câu lệnh `UPDATE` duy nhất (`single update statement`):** Không được tách thành hai lệnh tuần tự như `UPDATE ... SET sex = 'f' WHERE sex = 'm'` rồi chạy tiếp `UPDATE ... SET sex = 'm' WHERE sex = 'f'`. Nếu làm vậy, lệnh thứ hai sẽ ghi đè toàn bộ dữ liệu vừa cập nhật thành `'m'`, dẫn đến tất cả nhân viên đều trở thành nam!
2. **Không dùng bảng tạm trung gian (`no intermediate temporary tables`):** Việc lưu tạm ra bảng khác hoặc biến tạm để hoán đổi là không được phép.
3. **Không dùng lệnh `SELECT` (`do not write any select statement`):** Tuyệt đối không dùng truy vấn phụ hay kiểm tra subquery.

Từ đó, ta cần một biểu thức điều kiện hoặc hàm biến đổi giá trị của từng dòng **ngay tại chỗ (in-place)** trong một lần quét dữ liệu duy nhất.

---

## ⭐ GIẢI PHÁP TỐI ƯU 1 (Khuyên dùng): `CASE WHEN` (Chuẩn ANSI SQL)

Đây là giải pháp chuẩn mực quốc tế, được khuyến khích sử dụng nhiều nhất trong công việc thực tế và phỏng vấn kỹ thuật vì tương thích với mọi hệ cơ sở dữ liệu (MySQL, PostgreSQL, Oracle, SQL Server, SQLite,...).

### 📝 Mã truy vấn SQL

Đoạn mã có sẵn trong [`Solution.sql`](file:///f:/daily-code-challenge/problems-from-401-to-500/problem-450/Solution.sql):

```sql
UPDATE Salary
SET sex = CASE 
    WHEN sex = 'm' THEN 'f'
    ELSE 'm'
END;
```

*(Hoặc dạng Simple CASE Expression)*:
```sql
UPDATE Salary
SET sex = CASE sex
    WHEN 'm' THEN 'f'
    ELSE 'm'
END;
```

---

### 🔍 Cơ chế hoạt động chi tiết
- Hệ RDBMS duyệt qua từng hàng của bảng `Salary`.
- Biểu thức `CASE ... END` đánh giá giá trị hiện tại của cột `sex`:
  - Nếu `sex = 'm'`, gán giá trị mới là `'f'`.
  - Ngược lại (`ELSE`), gán giá trị mới là `'m'`.
- Toàn bộ quá trình diễn ra nguyên tử (atomic) trên từng dòng mà không bị hiện tượng xung đột dữ liệu như khi chạy hai câu lệnh tuần tự.

#### Minh họa từng bước với dữ liệu mẫu:

| id | name | sex ban đầu | Biểu thức kiểm tra | sex mới cập nhật |
| :-: | :---: | :-: | :--- | :-: |
| **1** | A | `'m'` | `sex = 'm'` $\rightarrow$ True | `'f'` |
| **2** | B | `'f'` | `sex = 'm'` $\rightarrow$ False $\rightarrow$ `ELSE` | `'m'` |
| **3** | C | `'m'` | `sex = 'm'` $\rightarrow$ True | `'f'` |
| **4** | D | `'f'` | `sex = 'm'` $\rightarrow$ False $\rightarrow$ `ELSE` | `'m'` |

---

### 🌟 Ưu điểm của giải pháp `CASE WHEN`
- **Chuẩn ANSI SQL:** Chạy được trên 100% các RDBMS phổ biến mà không cần chỉnh sửa cú pháp.
- **Dễ đọc & dễ bảo trì:** Rõ ràng, dễ hiểu đối với bất kỳ lập trình viên nào đọc code.
- **Tối ưu hiệu năng:** Ánh xạ trực tiếp giá trị trong bộ nhớ với chi phí tính toán $\mathcal{O}(1)$ cho mỗi dòng.

---

## 🚀 GIẢI PHÁP 2 (Ngắn gọn trong MySQL): Hàm `IF()`

Nếu làm việc cụ thể trên MySQL hoặc MariaDB, hệ thống hỗ trợ sẵn hàm điều kiện bậc ba `IF(expr, v_true, v_false)`.

### 📝 Mã truy vấn SQL

```sql
UPDATE Salary
SET sex = IF(sex = 'm', 'f', 'm');
```

### 🔍 Cơ chế hoạt động
Hàm `IF(condition, value_if_true, value_if_false)` hoạt động tương tự như toán tử ba ngôi (ternary operator `condition ? a : b`) trong các ngôn ngữ lập trình như JavaScript hay C++:
- Nếu `sex = 'm'`, trả về `'f'`.
- Nếu không, trả về `'m'`.

> [!NOTE]
> Hàm `IF()` này là hàm đặc thù của MySQL/MariaDB. Các hệ quản trị khác như PostgreSQL hay Oracle không hỗ trợ cú pháp này (SQL Server dùng hàm tương đương là `IIF()`).

---

## 💡 CÁC THỦ THUẬT SÁNG TẠO KHÁC (Interview Fun / Tricks)

Trong các buổi phỏng vấn, đôi khi phỏng vấn viên thử thách: *"Hãy giải quyết bài này mà không dùng bất kỳ câu lệnh rẽ nhánh nào (`CASE` hay `IF`)"*. Dưới đây là các thủ thuật toán học và xử lý chuỗi:

### Cách 3: Thủ thuật chuỗi với hàm `REPLACE()`
Thay vì kiểm tra điều kiện, ta tạo một chuỗi mẫu chứa cả 2 ký tự `'mf'`. Khi thay thế ký tự hiện tại bằng chuỗi rỗng `''`, ký tự còn lại sẽ chính là kết quả cần đổi!

```sql
UPDATE Salary
SET sex = REPLACE('mf', sex, '');
```

- Nếu `sex = 'm'` $\implies$ `REPLACE('mf', 'm', '')` $\rightarrow$ kết quả là `'f'`.
- Nếu `sex = 'f'` $\implies$ `REPLACE('mf', 'f', '')` $\rightarrow$ kết quả là `'m'`.

---

### Cách 4: Thủ thuật số học với bảng mã ASCII
Trong bảng mã ASCII:
- Ký tự `'m'` có mã thập phân là $109$.
- Ký tự `'f'` có mã thập phân là $102$.
- Tổng hai mã: $109 + 102 = 211$.

Ta có công thức bù trừ:
$$\text{char\_mới} = 211 - \text{char\_cũ}$$

- Nếu ký tự cũ là `'m'` ($109$): $211 - 109 = 102$ (mã của `'f'`).
- Nếu ký tự cũ là `'f'` ($102$): $211 - 102 = 109$ (mã của `'m'`).

Mã SQL:
```sql
UPDATE Salary
SET sex = CHAR(ASCII('m') + ASCII('f') - ASCII(sex));
-- hoặc tính sẵn hằng số:
-- UPDATE Salary SET sex = CHAR(211 - ASCII(sex));
```

---

### Cách 5: Thủ thuật toán tử Bitwise XOR ($\oplus$)
Dựa trên tính chất của phép toán XOR: $A \oplus B \oplus A = B$.
- $109 \oplus 102 = 11$ (Dạng nhị phân: $01101101_2 \oplus 01100110_2 = 00001011_2 = 11$).
- Khi XOR mã ASCII của giới tính với số $11$:
  - $109 \oplus 11 = 102$ (`'m'` biến thành `'f'`).
  - $102 \oplus 11 = 109$ (`'f'` biến thành `'m'`).

Mã SQL:
```sql
UPDATE Salary
SET sex = CHAR(ASCII(sex) ^ 11);
```

---

## 📊 Bảng so sánh tổng hợp các phương pháp

| Tiêu chí | Cách 1: `CASE WHEN` | Cách 2: MySQL `IF()` | Cách 3: `REPLACE()` | Cách 4: ASCII bù trừ | Cách 5: Bitwise XOR |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Độ phức tạp thời gian** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Độ phức tạp bộ nhớ** | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ |
| **Tính chuẩn hóa (ANSI SQL)** | ✔️ Chuẩn 100% | ❌ Chỉ MySQL/MariaDB | ✔️ Hầu hết RDBMS | ✔️ Chuẩn hàm chuỗi | ❌ Cú pháp XOR khác nhau |
| **Độ dễ đọc & bảo trì** | ⭐⭐⭐⭐⭐ (Tối ưu nhất) | ⭐⭐⭐⭐⭐ (Rất ngắn gọn) | ⭐⭐⭐⭐ (Thú vị) | ⭐⭐ (Thủ thuật toán) | ⭐⭐ (Khó hiểu nếu không ghi chú) |
| **Khả năng mở rộng (3+ giá trị)**| Dễ dàng thêm nhánh `WHEN` | Phải lồng nhiều `IF` | Khó áp dụng | Không áp dụng được | Không áp dụng được |

---

## 💡 Lưu ý thực tế & Kinh nghiệm phỏng vấn

1. **Tại sao không thể dùng 2 câu lệnh UPDATE tuần tự?**
   - Sai lầm kinh điển của người mới bắt đầu:
     ```sql
     -- SAI LẦM:
     UPDATE Salary SET sex = 'f' WHERE sex = 'm';
     UPDATE Salary SET sex = 'm' WHERE sex = 'f';
     ```
   - Lệnh 1 biến toàn bộ `'m'` thành `'f'`. Lúc này toàn bộ các hàng trong bảng đều mang giá trị `'f'`.
   - Lệnh 2 chạy ngay sau đó sẽ biến toàn bộ `'f'` (bao gồm cả các hàng ban đầu là `'f'` lẫn các hàng vừa bị biến đổi ở lệnh 1) thành `'m'`. Hậu quả: toàn bộ bảng đều thành `'m'`!
2. **Tại sao `CASE WHEN` là giải pháp an toàn nhất cho Production?**
   - `CASE WHEN` đánh giá giá trị trên từng bản ghi độc lập trước khi thực hiện ghi dữ liệu, đảm bảo tính nguyên tử (atomic) trên từng hàng và loại trừ hoàn toàn nguy cơ ghi đè chéo.
   - Trình tối ưu hóa truy vấn (Query Optimizer) của hầu hết các CSDL lớn đều biên dịch khối `CASE` thành các chỉ lệnh máy cấp thấp cực nhanh mà không sinh thêm bất kỳ overhead nào.
