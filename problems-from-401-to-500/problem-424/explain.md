# 📚 Giải thích Bài 586 - Customer Placing the Largest Number of Orders

**Mục tiêu:** Tìm `customer_number` của khách hàng đã đặt số lượng đơn hàng nhiều nhất trong bảng `Orders`.

---

## 🎯 Phân tích bài toán

Bảng `Orders` có cấu trúc:
- `order_number`: Khóa chính (Primary Key) đại diện cho mã số của từng đơn hàng riêng biệt.
- `customer_number`: Mã định danh của khách hàng đặt đơn hàng đó. Mỗi khách hàng có thể đặt nhiều đơn hàng khác nhau.

### 📋 Yêu cầu cốt lõi
1. Đếm tổng số đơn hàng của từng khách hàng.
2. Xác định khách hàng có số lượng đơn hàng cao nhất.
3. Đề bài đảm bảo rằng: **Chỉ có duy nhất một khách hàng** có số đơn đặt nhiều hơn tất cả các khách hàng khác (không có trường hợp hòa điểm/đồng hạng nhất).

### 🚀 Câu hỏi mở rộng (Follow-up)
> **Follow up:** Nếu có nhiều hơn một khách hàng cùng có số lượng đơn hàng lớn nhất (đồng hạng nhất), bạn có thể tìm tất cả các `customer_number` trong trường hợp này không?

---

## ⭐ BEST SOLUTION (Cho bài toán gốc): `GROUP BY` + `ORDER BY COUNT DESC` + `LIMIT 1`

Vì đề bài đảm bảo rằng luôn có duy nhất một khách hàng có số đơn nhiều nhất, giải pháp trực tiếp, ngắn gọn và đạt hiệu năng cao nhất là gom nhóm theo khách hàng, đếm số đơn, sắp xếp giảm dần và lấy ra dòng đầu tiên:

```sql
SELECT customer_number
FROM Orders
GROUP BY customer_number
ORDER BY COUNT(*) DESC
LIMIT 1;
```

### 🌟 Tại sao đây là giải pháp tốt nhất cho bài toán gốc?
1. **Cực kỳ ngắn gọn và trực quan:** Chỉ 5 dòng SQL kinh điển, diễn đạt chính xác mục tiêu: "Gom nhóm theo khách hàng $\rightarrow$ Đếm số đơn $\rightarrow$ Sắp xếp giảm dần $\rightarrow$ Lấy người đứng đầu".
2. **Tối ưu hóa bởi Query Optimizer (Top-N Sort):** Hầu hết các hệ quản trị CSDL hiện đại (MySQL, PostgreSQL) khi gặp `ORDER BY ... LIMIT 1` sẽ sử dụng cấu trúc **Priority Queue (Max-Heap)** để lưu trữ 1 phần tử lớn nhất trong quá trình quét bảng thay vì phải thực hiện Full Sort trên toàn bộ các nhóm.
3. **Chi phí tài nguyên thấp:** Không cần tạo CTE hay truy vấn con phức tạp.

---

## 🔍 Cách hoạt động từng bước

Xét dữ liệu mẫu của bảng `Orders`:

| order_number | customer_number |
|:-------------|:----------------|
| 1            | 1               |
| 2            | 2               |
| 3            | 3               |
| 4            | 3               |

### Bước 1: Gom nhóm `GROUP BY customer_number` và đếm `COUNT(*)`

Cơ sở dữ liệu quét qua các dòng và đếm số lần xuất hiện của từng `customer_number`:
- `customer_number = 1`: Có 1 đơn hàng (order 1) $\rightarrow$ `COUNT(*) = 1`.
- `customer_number = 2`: Có 1 đơn hàng (order 2) $\rightarrow$ `COUNT(*) = 1`.
- `customer_number = 3`: Có 2 đơn hàng (order 3, 4) $\rightarrow$ `COUNT(*) = 2`.

Bảng tổng hợp trung gian:

| customer_number | COUNT(*) |
|:----------------|:---------|
| 1               | 1        |
| 2               | 1        |
| 3               | 2        |

### Bước 2: Sắp xếp giảm dần `ORDER BY COUNT(*) DESC`

Các nhóm được sắp xếp theo số lượng đơn hàng giảm dần:

| customer_number | COUNT(*) |
|:----------------|:---------|
| **3**           | **2**    |
| 1               | 1        |
| 2               | 1        |

### Bước 3: Giới hạn kết quả `LIMIT 1`

Lấy dòng đầu tiên trên cùng:
- Kết quả thu được: `customer_number = 3`.

Bảng kết quả cuối cùng:
```text
+-----------------+
| customer_number |
+-----------------+
| 3               |
+-----------------+
```

---

## 🚀 GIẢI QUYẾT CÂU HỎI FOLLOW-UP (Xử lý đồng hạng - Ties)

Nếu trong thực tế có **nhiều khách hàng cùng có số đơn hàng lớn nhất** (ví dụ: cả khách hàng 3 và khách hàng 4 đều có 5 đơn), câu lệnh `LIMIT 1` ở trên sẽ tùy tiện chọn 1 trong 2 người và bỏ sót người còn lại.

Dưới đây là các phương pháp chuẩn mực để xử lý triệt để bài toán Follow-up:

### Phương pháp 1: Sử dụng Window Function `DENSE_RANK()` (Khuyên dùng trong phỏng vấn) ⭐

Window Function là công cụ chuẩn mực của SQL hiện đại (MySQL 8.0+, PostgreSQL, SQL Server, Oracle):

```sql
WITH CustomerOrderRank AS (
    SELECT 
        customer_number,
        DENSE_RANK() OVER (ORDER BY COUNT(*) DESC) AS rnk
    FROM Orders
    GROUP BY customer_number
)
SELECT customer_number
FROM CustomerOrderRank
WHERE rnk = 1;
```

- **Cơ chế hoạt động:**
  - `DENSE_RANK() OVER (ORDER BY COUNT(*) DESC)` gán thứ hạng dựa trên số lượng đơn đặt.
  - Nếu khách hàng 3 và 4 đều có 5 đơn (nhiều nhất), cả hai người đều sẽ nhận giá trị `rnk = 1`.
  - Mệnh đề `WHERE rnk = 1` bên ngoài sẽ lấy ra **tất cả** các khách hàng đồng hạng nhất.
- **Ưu điểm:** Chuẩn ANSI SQL:2003, dễ đọc, dễ mở rộng (ví dụ: lấy top 3 khách hàng nhiều đơn nhất bằng `WHERE rnk <= 3`).

---

### Phương pháp 2: Sử dụng Subquery với `HAVING COUNT(*) = MAX(...)`

Phương pháp này tương thích với cả các phiên bản CSDL cũ không hỗ trợ Window Functions (như MySQL 5.7):

```sql
SELECT customer_number
FROM Orders
GROUP BY customer_number
HAVING COUNT(*) = (
    SELECT MAX(order_count)
    FROM (
        SELECT COUNT(*) AS order_count
        FROM Orders
        GROUP BY customer_number
    ) AS sub
);
```

- **Cơ chế hoạt động:**
  1. Truy vấn con `sub` gom nhóm và tính số lượng đơn của từng khách hàng.
  2. Hàm `MAX(order_count)` tìm ra số lượng đơn hàng cao nhất trong toàn bộ bảng.
  3. Mệnh đề `HAVING COUNT(*) = ...` lọc ra tất cả những khách hàng có số đơn bằng đúng giá trị lớn nhất đó.
- **Ưu điểm:** Tương thích với hầu như mọi phiên bản CSDL SQL trong lịch sử.
- **Nhược điểm:** Phải quét và gom nhóm bảng `Orders` 2 lần, cú pháp subquery lồng nhau hơi cồng kềnh.

---

### Phương pháp 3: Sử dụng toán tử `>= ALL`

```sql
SELECT customer_number
FROM Orders
GROUP BY customer_number
HAVING COUNT(*) >= ALL (
    SELECT COUNT(*)
    FROM Orders
    GROUP BY customer_number
);
```

- **Cơ chế hoạt động:** Lọc ra những khách hàng có số lượng đơn lớn hơn hoặc bằng **tất cả (`ALL`)** số lượng đơn của các khách hàng khác.
- **Lưu ý:** Cú pháp này ít khi được dùng trong thực tế sản xuất do độ tối ưu của toán tử `ALL` trên một số hệ quản trị CSDL không tốt bằng Window Functions.

---

## 📊 So sánh các giải pháp

| Giải pháp | Áp dụng cho | Thời gian | Không gian | Xử lý đồng hạng (Ties) | Đánh giá khi phỏng vấn |
|:---|:---|:---|:---|:---|:---|
| **`ORDER BY ... LIMIT 1`** ⭐ | Bài toán gốc (duy nhất 1 người) | $\mathcal{O}(N \log K)$ | $\mathcal{O}(K)$ | ❌ Chỉ lấy 1 dòng ngẫu nhiên | 🥇 **Tối ưu nhất cho bài gốc** |
| **`DENSE_RANK()`** ⭐ | Follow-up (nhiều người đồng hạng) | $\mathcal{O}(N \log K)$ | $\mathcal{O}(K)$ | ✅ Lấy toàn bộ người hạng 1 | 🥇 **Chuẩn mực cho bài Follow-up** |
| **`Subquery + HAVING MAX`** | Follow-up (CSDL cũ không có Window) | $\mathcal{O}(N \log K)$ | $\mathcal{O}(K)$ | ✅ Lấy toàn bộ người hạng 1 | ⭐ Thể hiện hiểu biết về subquery |
| **`HAVING >= ALL`** | Follow-up | $\mathcal{O}(N \cdot K)$ | $\mathcal{O}(K)$ | ✅ Lấy toàn bộ người hạng 1 | Ít dùng trong thực tế |

*(Với $N$ là tổng số đơn hàng trong bảng `Orders`, $K$ là số khách hàng duy nhất).*

---

## 💡 Điểm lưu ý quan trọng (Key Takeaways)

### 1. Sự khác biệt giữa `RANK()`, `DENSE_RANK()` và `ROW_NUMBER()`
Khi giải quyết bài toán xếp hạng trong SQL, hãy luôn nhớ quy tắc xếp hạng sau:
- **`ROW_NUMBER()`**: Đánh số thứ tự tăng dần từ 1, không bao giờ trùng lặp (kể cả khi bằng điểm: `1, 2, 3, 4`).
- **`RANK()`**: Bằng điểm thì cùng hạng, nhưng thứ hạng tiếp theo sẽ bị **nhảy cóc** (ví dụ: hai người hạng 1 thì người tiếp theo là hạng 3: `1, 1, 3, 4`).
- **`DENSE_RANK()`**: Bằng điểm thì cùng hạng, và thứ hạng tiếp theo **liền kề** không bị nhảy cóc (`1, 1, 2, 3`).
- 👉 Trong bài toán lấy danh sách người đứng đầu (`rnk = 1`), cả `RANK()` và `DENSE_RANK()` đều cho kết quả giống nhau, nhưng `DENSE_RANK()` an toàn hơn khi cần mở rộng sang Top K.

### 2. Tối ưu hóa Index
- Bảng `Orders` có khóa chính là `order_number`.
- Nếu bảng có hàng chục triệu đơn hàng, việc đánh chỉ mục (Index) trên cột `customer_number` (`CREATE INDEX idx_customer ON Orders(customer_number);`) sẽ giúp thao tác `GROUP BY customer_number` chuyển sang Index Scan mà không cần Full Table Scan.

---

## 📈 Độ phức tạp (Complexity Analysis)

- **Độ phức tạp thời gian (Time Complexity):** $\mathcal{O}(N \log K)$
  - Quét toàn bộ $N$ đơn hàng để gom nhóm vào $K$ khách hàng mất $\mathcal{O}(N)$ (nếu dùng Hash Aggregation) hoặc $\mathcal{O}(N \log N)$ (nếu dùng Sort Aggregation).
  - Thao tác sắp xếp để tìm Top 1 mất $\mathcal{O}(K \log 1) = \mathcal{O}(K)$ khi sử dụng Heap.
- **Độ phức tạp không gian (Space Complexity):** $\mathcal{O}(K)$
  - Bộ nhớ phụ trợ cần thiết để lưu trữ bảng băm gom nhóm $K$ khách hàng duy nhất.
