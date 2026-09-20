# 📚 Giải thích Bài 595 - Big Countries

**Mục tiêu:** Tìm tên (`name`), dân số (`population`), và diện tích (`area`) của các quốc gia lớn (big countries) từ bảng `World`.

---

## 🎯 Phân tích bài toán

Bảng `World` lưu thông tin về các quốc gia với cấu trúc:
- `name` (varchar): Tên quốc gia (Khóa chính - Primary Key, đảm bảo giá trị là duy nhất).
- `continent` (varchar): Châu lục.
- `area` (int): Diện tích của quốc gia tính bằng $\text{km}^2$.
- `population` (int): Dân số của quốc gia.
- `gdp` (bigint): Tổng sản phẩm quốc nội (GDP).

### 📋 Điều kiện để một quốc gia được coi là "lớn" (Big Country)
Một quốc gia được xem là lớn nếu thỏa mãn **ít nhất một trong hai** điều kiện sau:
1. Có diện tích **tối thiểu $3{,}000{,}000\text{ km}^2$** ($\text{area} \ge 3{,}000{,}000$).
2. **HOẶC** Có dân số **tối thiểu $25{,}000{,}000$ người** ($\text{population} \ge 25{,}000{,}000$).

### ⚠️ Lưu ý quan trọng về định dạng kết quả
- **Thứ tự các cột cần lấy:** Đề bài yêu cầu trả về theo đúng thứ tự: `name`, `population`, `area`.
  *(Cần cẩn thận vì trong schema gốc của bảng `World`, cột `area` đứng trước `population`, nhưng kết quả đầu ra yêu cầu `population` đứng trước `area`)*.
- **Thứ tự các dòng:** Có thể trả về theo bất kỳ thứ tự nào (`Return the result table in any order`), do đó không cần dùng mệnh đề `ORDER BY`.
- **Toán tử so sánh:** Đề bài dùng cụm từ "at least" (ít nhất / tối thiểu), nghĩa là phép so sánh phải là **lớn hơn hoặc bằng** ($\ge$), không phải lớn hơn nghiêm ngặt ($>$).

---

## ⭐ BEST SOLUTION: Mệnh đề `WHERE` kết hợp toán tử `OR`

Đây là cách tiếp cận tự nhiên, trực quan và đạt hiệu năng tối ưu nhất trong phần lớn các hệ quản trị cơ sở dữ liệu thực tế và trên LeetCode.

```sql
SELECT name, population, area
FROM World
WHERE area >= 3000000 OR population >= 25000000;
```

### 💡 Phân tích cú pháp:
1. `SELECT name, population, area`: Chỉ định rõ ràng 3 cột cần xuất theo đúng thứ tự yêu cầu.
2. `FROM World`: Xác định nguồn dữ liệu từ bảng `World`.
3. `WHERE area >= 3000000 OR population >= 25000000`: Bộ lọc từng bản ghi. Chỉ cần một trong hai điều kiện đúng (hoặc cả hai cùng đúng) thì bản ghi đó sẽ được chọn vào kết quả.

---

## 🔄 CÁCH TIẾP CẬN 2: Sử dụng toán tử tập hợp `UNION`

Trong lý thuyết SQL và một số hệ cơ sở dữ liệu lớn có cấu trúc Index riêng biệt, ta có thể tách thành hai truy vấn con và hợp lại bằng `UNION`:

```sql
SELECT name, population, area
FROM World
WHERE area >= 3000000

UNION

SELECT name, population, area
FROM World
WHERE population >= 25000000;
```

### 💡 Cơ chế hoạt động của `UNION`:
- **Truy vấn 1:** Lấy tất cả các quốc gia có $\text{area} \ge 3{,}000{,}000$.
- **Truy vấn 2:** Lấy tất cả các quốc gia có $\text{population} \ge 25{,}000{,}000$.
- **Toán tử `UNION` (tương đương `UNION DISTINCT`):** Ghép kết quả của hai truy vấn trên lại với nhau và **tự động loại bỏ các bản ghi trùng lặp** (những quốc gia vừa có diện tích lớn vừa có dân số đông, thỏa mãn cả hai điều kiện).

---

## ⚖️ SO SÁNH CHUYÊN SÂU: `OR` vs `UNION` (Góc nhìn Query Optimizer & Phỏng vấn)

Đây là một chủ đề phỏng vấn kinh điển về tối ưu hóa truy vấn SQL:

| Tiêu chí | Dùng `WHERE ... OR ...` | Dùng `UNION` |
| :--- | :--- | :--- |
| **Độ phức tạp mã nguồn** | Ngắn gọn, súc tích, dễ đọc và bảo trì | Dài dòng hơn, lặp lại danh sách cột và bảng |
| **Hành vi quét dữ liệu (Scan)** | Chỉ quét bảng `World` 1 lần duy nhất (Single Table Scan) | Thực hiện 2 lần quét trên bảng `World` |
| **Chi phí khử trùng (Deduplication)** | **0** (Không cần khử trùng, mỗi dòng chỉ được duyệt 1 lần) | **Có** (Phải tạo bảng tạm hoặc sắp xếp/băm để khử trùng các dòng thỏa cả 2 điều kiện) |
| **Tận dụng Index đơn lẻ** | Trên các RDBMS cũ, `OR` có thể không dùng được đồng thời cả 2 B-Tree Index riêng rẽ trên `area` và `population` $\rightarrow$ Fallback về Full Table Scan. Tuy nhiên, RDBMS hiện đại có **Index Merge** (MySQL) hoặc **BitmapOr** (PostgreSQL). | Mỗi truy vấn con có thể tận dụng độc lập B-Tree Index trên từng cột tương ứng (`area` và `population`). |
| **Khuyên dùng khi** | - Bảng dữ liệu vừa hoặc nhỏ.<br>- Bảng không có sẵn index trên cả 2 cột.<br>- Cần code ngắn gọn, rõ ràng. | - Bảng cực lớn (hàng chục triệu dòng) và **đã có sẵn Index** trên `area` và `population`, đồng thời query engine không hỗ trợ tối ưu Index Merge. |

> 📌 **Kết luận phỏng vấn:**
> Trong bài toán LeetCode này (và trong đại đa số bài toán thực tế), bảng `World` chỉ có khoảng vài trăm quốc gia và không có chỉ mục phụ (secondary index). Việc dùng `WHERE ... OR ...` là giải pháp tốt nhất vì:
> 1. Tránh việc phải quét bảng 2 lần.
> 2. Tránh chi phí bộ nhớ đệm và CPU khi `UNION` phải tạo Temporary Table để khử trùng lặp dữ liệu.

---

## 🔍 Từng bước thực thi qua dữ liệu mẫu (Walkthrough)

### Bảng đầu vào `World`:

| name | continent | area | population | gdp |
| :--- | :--- | :--- | :--- | :--- |
| **Afghanistan** | Asia | 652230 | 25500100 | 20343000000 |
| **Albania** | Europe | 28748 | 2831741 | 12960000000 |
| **Algeria** | Africa | 2381741 | 37100000 | 188681000000 |
| **Andorra** | Europe | 468 | 78115 | 3712000000 |
| **Angola** | Africa | 1246700 | 20609294 | 100990000000 |

### Kiểm tra từng dòng theo điều kiện `area >= 3000000 OR population >= 25000000`:

1. **Afghanistan:**
   - $\text{area} = 652230 < 3000000$ (Sai)
   - $\text{population} = 25500100 \ge 25000000$ (Đúng)
   - Kết quả: **Thỏa mãn** $\implies$ Đưa vào kết quả: `(Afghanistan, 25500100, 652230)`.

2. **Albania:**
   - $\text{area} = 28748 < 3000000$ (Sai)
   - $\text{population} = 2831741 < 25000000$ (Sai)
   - Kết quả: **Không thỏa mãn** $\implies$ Bỏ qua.

3. **Algeria:**
   - $\text{area} = 2381741 < 3000000$ (Sai)
   - $\text{population} = 37100000 \ge 25000000$ (Đúng)
   - Kết quả: **Thỏa mãn** $\implies$ Đưa vào kết quả: `(Algeria, 37100000, 2381741)`.

4. **Andorra:**
   - $\text{area} = 468 < 3000000$ (Sai)
   - $\text{population} = 78115 < 25000000$ (Sai)
   - Kết quả: **Không thỏa mãn** $\implies$ Bỏ qua.

5. **Angola:**
   - $\text{area} = 1246700 < 3000000$ (Sai)
   - $\text{population} = 20609294 < 25000000$ (Sai)
   - Kết quả: **Không thỏa mãn** $\implies$ Bỏ qua.

### Bảng kết quả trả về:

```text
+-------------+------------+---------+
| name        | population | area    |
+-------------+------------+---------+
| Afghanistan | 25500100   | 652230  |
| Algeria     | 37100000   | 2381741 |
+-------------+------------+---------+
```

---

## 📊 Đánh giá độ phức tạp (Complexity Analysis)

- **Độ phức tạp thời gian (Time Complexity):** $\mathcal{O}(N)$
  - Trong đó $N$ là tổng số dòng (số quốc gia) trong bảng `World`.
  - Cơ sở dữ liệu quét tuần tự từng dòng trong bảng và kiểm tra hai phép so sánh số học đơn giản với chi phí $\mathcal{O}(1)$ cho mỗi dòng.
- **Độ phức tạp không gian (Space Complexity):** $\mathcal{O}(1)$ (bộ nhớ phụ trợ ngoài kết quả trả về)
  - Mệnh đề `WHERE ... OR ...` lọc trực tiếp dữ liệu theo luồng (streaming) mà không cần cấp phát cấu trúc bảng tạm (Temporary Table) hay cấu trúc băm (Hash/Sort Table) như khi dùng `UNION`.

---

## ⚠️ Các lỗi phổ biến cần tránh (Common Pitfalls)

1. **Sai thứ tự cột:** Đề bài yêu cầu `name, population, area`. Viết `SELECT name, area, population` sẽ bị chấm **Wrong Answer**.
2. **Dùng nhầm toán tử `AND`:** Đề bài yêu cầu quốc gia lớn nếu thỏa điều kiện diện tích **hoặc** dân số. Nếu dùng `AND`, ta sẽ bỏ sót các quốc gia chỉ thỏa 1 trong 2 điều kiện.
3. **Nhầm lẫn giữa `>` và `>=`:** Đề bài nêu rõ "at least" (ít nhất), nên điều kiện phải là `>= 3000000` và `>= 25000000`.
4. **Dùng `UNION ALL` thay vì `UNION` (nếu chọn cách tiếp cận 2):** `UNION ALL` không lọc trùng, dẫn đến các quốc gia vừa có diện tích $\ge 3{,}000{,}000$ vừa có dân số $\ge 25{,}000{,}000$ (như Mỹ, Trung Quốc, Ấn Độ, Brazil...) sẽ bị xuất hiện **hai lần** trong bảng kết quả.
