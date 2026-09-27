# 📚 Giải thích Chi tiết Bài 608 - Tree Node

**Mục tiêu:** Xác định loại nút (`Root`, `Inner`, `Leaf`) cho từng nút trong cây từ bảng `Tree`.

---

## 🎯 Phân tích bài toán

Bảng `Tree` có cấu trúc:
- `id` (int): Định danh duy nhất của nút trong cây.
- `p_id` (int): Định danh của nút cha (parent node).
- Đề bài đảm bảo dữ liệu đầu vào luôn tạo thành một cây hợp lệ.

### 🌳 Khái niệm các loại nút trong cây

Một cây gồm 3 loại nút:
1. **Nút gốc (`Root`)**: Là nút cao nhất trong cây, không có nút cha $\rightarrow$ `p_id IS NULL`.
2. **Nút lá (`Leaf`)**: Là nút ở tầng cuối, có nút cha nhưng **không có bất kỳ nút con nào** $\rightarrow$ không có dòng nào khác trong bảng có `p_id` bằng với `id` của nút này.
3. **Nút trung gian / nút trong (`Inner`)**: Là nút nằm ở giữa, vừa có nút cha (`p_id IS NOT NULL`), vừa có ít nhất một nút con.

> **Trường hợp đặc biệt (Edge Case):** Nếu cây chỉ có duy nhất 1 nút (Ví dụ 2), nút đó không có cha (`p_id IS NULL`), theo quy ước đề bài ta xem đó là **`Root`** (không phải `Leaf`).

---

## ⭐ GIẢI PHÁP TRONG CODE: Mệnh đề `CASE WHEN` kết hợp `NOT EXISTS`

Đoạn mã truy vấn trong [`Solution.sql`](file:///f:/daily-code-challenge/problems-from-401-to-500/problem-438/Solution.sql):

```sql
SELECT id,
CASE
    WHEN p_id IS NULL THEN 'Root'
    WHEN NOT EXISTS (
        SELECT 1
        FROM Tree t2
        WHERE t2.p_id = Tree.id
    ) THEN 'Leaf'
    ELSE 'Inner'
END AS type
FROM Tree;
```

---

## 🔍 Cơ chế hoạt động & Thứ tự đánh giá

Mệnh đề `CASE WHEN` trong SQL đánh giá tuần tự từ trên xuống dưới theo nguyên lý **Short-circuit Evaluation** (dừng ngay khi gặp điều kiện đầu tiên thỏa mãn):

### 1. Nhánh 1: `WHEN p_id IS NULL THEN 'Root'`
- Kiểm tra xem nút có nút cha hay không.
- Nếu `p_id IS NULL`, kết luận ngay nút đó là **`Root`** và bỏ qua toàn bộ các điều kiện phía sau.
- Điều này cũng xử lý chuẩn xác trường hợp cây chỉ có 1 nút duy nhất.

### 2. Nhánh 2: `WHEN NOT EXISTS (...) THEN 'Leaf'`
- Vì đã vượt qua nhánh 1, tại thời điểm này ta **chắc chắn $100\%$ rằng nút hiện tại có cha** (`p_id IS NOT NULL`).
- Subquery kiểm tra: Có bất kỳ nút `t2` nào trong bảng mà `t2.p_id = Tree.id` hay không?
  - Nếu **KHÔNG TỒN TẠI** (`NOT EXISTS`), nghĩa là nút hiện tại không sinh ra nút con nào $\rightarrow$ kết luận là **`Leaf`**.

### 3. Nhánh 3: `ELSE 'Inner'`
- Nếu rơi vào `ELSE`, nút này thỏa mãn đồng thời hai điều kiện:
  - Không phải `Root` (có cha).
  - Không phải `Leaf` (có ít nhất 1 con).
- Do đó, chắc chắn nút này là **`Inner`**.

---

## 💡 Cạm bẫy kinh điển: Tại sao `NOT EXISTS` an toàn hơn `NOT IN`?

Một câu hỏi phỏng vấn rất phổ biến: *Tại sao không viết `WHEN id NOT IN (SELECT p_id FROM Tree) THEN 'Leaf'`?*

### ⚠️ Hiểm họa logic 3 giá trị (Three-Valued Logic) với `NULL`:
- Cột `p_id` chứa giá trị `NULL` (nút gốc).
- Khi chạy `SELECT p_id FROM Tree`, tập kết quả trả về sẽ có dạng: `(NULL, 1, 2)`.
- Phép toán `id NOT IN (NULL, 1, 2)` được hệ quản trị cơ sở dữ liệu mở rộng thành:
  $$\text{id} \ne \text{NULL} \quad \text{AND} \quad \text{id} \ne 1 \quad \text{AND} \quad \text{id} \ne 2$$
- Trong SQL:
  - Bất kỳ so sánh nào với `NULL` (`id = NULL` hoặc `id != NULL`) đều trả về **`UNKNOWN`** chứ không phải `TRUE` hay `FALSE`.
  - Biểu thức `AND` chứa giá trị `UNKNOWN` sẽ đánh giá thành `UNKNOWN` hoặc `FALSE`, **không bao giờ thành `TRUE`**.
- **Hậu quả:** Điều kiện `NOT IN` sẽ luôn thất bại, khiến mọi nút lá đều bị rơi nhầm vào nhánh `ELSE 'Inner'`.

> ✔️ **Kết luận:** Sử dụng `NOT EXISTS` là lựa chọn cực kỳ an toàn vì `NOT EXISTS` chỉ quan tâm đến việc có dòng nào thỏa mãn điều kiện `t2.p_id = Tree.id` hay không, hoàn toàn miễn nhiễm với giá trị `NULL` của các dòng khác.

---

## 🔍 Walkthrough từng bước với dữ liệu mẫu

### Ví dụ 1:
```text
Tree:
+----+------+
| id | p_id |
+----+------+
| 1  | null |
| 2  | 1    |
| 3  | 1    |
| 4  | 2    |
| 5  | 2    |
+----+------+
```

| `id` | `p_id` | `p_id IS NULL`? | Tồn tại con (`t2.p_id = id`)? | Kết quả | Giải thích |
|:---:|:---:|:---:|:---:|:---:|:---|
| **1** | `null` | **TRUE** | (Bỏ qua) | **`Root`** | Không có cha $\rightarrow$ Gốc |
| **2** | `1` | FALSE | **CÓ** (id 4, 5 có `p_id = 2`) | **`Inner`** | Có cha (1) và có con (4, 5) |
| **3** | `1` | FALSE | **KHÔNG** | **`Leaf`** | Có cha (1), không có con |
| **4** | `2` | FALSE | **KHÔNG** | **`Leaf`** | Có cha (2), không có con |
| **5** | `2` | FALSE | **KHÔNG** | **`Leaf`** | Có cha (2), không có con |

---

## 🔄 CÁC CÁCH TIẾP CẬN KHÁC

### Cách 1: Sử dụng `CASE WHEN` với `IN` (Kiểm tra `Inner` trước)
Toán tử `IN` không bị ảnh hưởng bởi `NULL` theo cách tương tự: nếu tìm thấy giá trị bằng nhau thì biểu thức trả về `TRUE`. Vì vậy ta có thể đảo thứ tự kiểm tra `Inner` trước `Leaf`:

```sql
SELECT id,
CASE
    WHEN p_id IS NULL THEN 'Root'
    WHEN id IN (SELECT p_id FROM Tree) THEN 'Inner'
    ELSE 'Leaf'
END AS type
FROM Tree;
```

### Cách 2: Sử dụng `LEFT JOIN`
Ta có thể tự kết nối bảng `Tree` với chính nó để kiểm tra nút con:

```sql
SELECT DISTINCT t1.id,
CASE
    WHEN t1.p_id IS NULL THEN 'Root'
    WHEN t2.id IS NULL THEN 'Leaf'
    ELSE 'Inner'
END AS type
FROM Tree t1
LEFT JOIN Tree t2 ON t1.id = t2.p_id;
```

### Cách 3: Sử dụng hàm điều kiện lồng nhau `IF()` (MySQL)

```sql
SELECT id,
IF(p_id IS NULL, 'Root',
    IF(id IN (SELECT p_id FROM Tree), 'Inner', 'Leaf')
) AS type
FROM Tree;
```

---

## ⚠️ Các lỗi thường gặp (Common Pitfalls)

1. **Dùng `NOT IN` mà quên lọc `NULL`:**
   - ❌ `WHERE id NOT IN (SELECT p_id FROM Tree)` $\rightarrow$ Trả về rỗng do `NULL`.
   - ✔️ `WHERE id NOT IN (SELECT p_id FROM Tree WHERE p_id IS NOT NULL)` $\rightarrow$ Đúng.
2. **Sai thứ tự điều kiện trong `CASE WHEN`:**
   - Nếu kiểm tra điều kiện `Leaf` hoặc `Inner` trước khi kiểm tra `p_id IS NULL`, nút gốc có thể bị phân loại sai (đặc biệt khi cây chỉ có 1 nút duy nhất).
3. **Quên từ khóa `DISTINCT` khi dùng `LEFT JOIN`:**
   - Nếu một nút cha có nhiều nút con, phép `LEFT JOIN` sẽ tạo ra nhiều dòng trùng lặp cho nút cha đó nếu không gom nhóm hoặc dùng `DISTINCT`.

---

## 📊 Đánh giá độ phức tạp

- **Độ phức tạp thời gian (Time Complexity):**
  - Khi không có index: $\mathcal{O}(N^2)$ với $N$ là số lượng nút trong bảng, do subquery tương quan quét lại bảng cho từng dòng.
  - Khi có index trên cột `p_id`: $\mathcal{O}(N)$ do việc tìm kiếm sự tồn tại của `p_id` chỉ tốn $\mathcal{O}(1)$ tới $\mathcal{O}(\log N)$.
- **Độ phức tạp không gian (Space Complexity):** $\mathcal{O}(1)$ bộ nhớ phụ trợ (ngoại trừ không gian lưu bảng kết quả $\mathcal{O}(N)$).