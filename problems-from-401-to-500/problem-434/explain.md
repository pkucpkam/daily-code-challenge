# 📚 Giải thích Chi tiết Bài 602 - Friend Requests II: Who Has the Most Friends

**Mục tiêu:** Tìm người (hoặc những người) có số lượng bạn bè nhiều nhất và tổng số bạn bè của người đó từ bảng `RequestAccepted`.

---

## 🎯 Phân tích bài toán

Bảng `RequestAccepted` có cấu trúc:
- `requester_id` (int): ID của người gửi yêu cầu kết bạn.
- `accepter_id` (int): ID của người chấp nhận lời mời kết bạn.
- `accept_date` (date): Ngày yêu cầu được chấp nhận.
- `(requester_id, accepter_id)` là **Khóa chính (Primary Key)**: Đảm bảo giữa hai người bất kỳ chỉ có tối đa một lời mời kết bạn được ghi nhận.

### 💡 Bản chất của mối quan hệ bạn bè (Mối quan hệ vô hướng - Undirected Relationship)
- Trong mạng xã hội, khi một lời mời kết bạn được chấp nhận thì **mối quan hệ bạn bè là hai chiều**:
  - Nếu người `A` gửi lời mời cho người `B` và `B` chấp nhận:
    - `A` là bạn của `B`.
    - Đồng thời `B` cũng là bạn của `A`.
- Do đó, số lượng bạn bè của một người $X$ bất kỳ là tổng số lần $X$ xuất hiện với vai trò là:
  - Người gửi: $X$ nằm ở cột `requester_id`.
  - **HOẶC** Người nhận: $X$ nằm ở cột `accepter_id`.

### 📋 Yêu cầu đề bài
1. Tìm người có số bạn bè nhiều nhất (`id`) và số lượng bạn bè tương ứng (`num`).
2. Đề bài đảm bảo dữ liệu test chỉ có **duy nhất một người** có số bạn nhiều nhất.
3. **Follow-up:** Trong thực tế, có thể nhiều người cùng đạt số lượng bạn bè cao nhất (đồng hạng nhất). Làm thế nào để tìm được tất cả những người đó?

---

## ⭐ BEST SOLUTION: Gom bảng bằng `UNION ALL` kết hợp `GROUP BY` & `ORDER BY ... LIMIT 1`

Đây là giải pháp kinh điển, ngắn gọn và đạt hiệu năng tối ưu nhất trong SQL:

```sql
SELECT id, COUNT(*) AS num
FROM (
    SELECT requester_id AS id FROM RequestAccepted
    UNION ALL
    SELECT accepter_id AS id FROM RequestAccepted
) AS all_friends
GROUP BY id
ORDER BY num DESC
LIMIT 1;
```

### 🌟 Tại sao đây là giải pháp tốt nhất?
1. **Biến quan hệ 2 chiều thành 1 chiều thống nhất:** Thay vì phải xử lý logic phức tạp giữa hai cột, `UNION ALL` gom tất cả các "lượt kết bạn" của từng người vào một cột `id` duy nhất.
2. **`UNION ALL` thay vì `UNION`:**
   - `UNION` sẽ tự động thực hiện thao tác khử trùng lặp (Deduplication). Nếu một người gửi hoặc nhận nhiều lời mời, `UNION` sẽ chỉ giữ lại đúng 1 dòng đại diện cho người đó, làm sai hoàn toàn kết quả đếm.
   - `UNION ALL` giữ nguyên toàn bộ các bản ghi, không tốn tài nguyên sắp xếp/khử trùng nên chạy rất nhanh.
3. **Đơn giản & Tối ưu:** Sau khi gom nhóm bằng `GROUP BY id`, chỉ cần sắp xếp giảm dần theo số lượng `num DESC` và lấy phần tử đầu tiên bằng `LIMIT 1`.

---

## 🔍 Cách hoạt động từng bước (Walkthrough)

Xét dữ liệu mẫu trong ví dụ:

#### Bảng `RequestAccepted`:
| requester_id | accepter_id | accept_date |
|:-------------|:------------|:------------|
| 1            | 2           | 2016/06/03  |
| 1            | 3           | 2016/06/08  |
| 2            | 3           | 2016/06/08  |
| 3            | 4           | 2016/06/09  |

---

### Bước 1: Hợp nhất 2 cột bằng `UNION ALL`

Tách từng dòng thành hai bản ghi riêng biệt:
- Cột `requester_id` đóng góp một lượt bạn bè cho người gửi.
- Cột `accepter_id` đóng góp một lượt bạn bè cho người nhận.

```sql
SELECT requester_id AS id FROM RequestAccepted
UNION ALL
SELECT accepter_id AS id FROM RequestAccepted
```

Kết quả của bảng tạm `all_friends`:

| id | Nguồn gốc |
|:---|:----------|
| 1  | requester_id từ dòng 1 |
| 1  | requester_id từ dòng 2 |
| 2  | requester_id từ dòng 3 |
| 3  | requester_id từ dòng 4 |
| 2  | accepter_id từ dòng 1  |
| 3  | accepter_id từ dòng 2  |
| 3  | accepter_id từ dòng 3  |
| 4  | accepter_id từ dòng 4  |

---

### Bước 2: Gom nhóm theo `id` và đếm tổng số lần xuất hiện `COUNT(*)`

Thực hiện `GROUP BY id`:
- `id = 1`: xuất hiện 2 lần $\rightarrow$ `num = 2` (bạn với 2, 3)
- `id = 2`: xuất hiện 2 lần $\rightarrow$ `num = 2` (bạn với 1, 3)
- `id = 3`: xuất hiện 3 lần $\rightarrow$ `num = 3` (bạn với 1, 2, 4)
- `id = 4`: xuất hiện 1 lần $\rightarrow$ `num = 1` (bạn với 3)

| id | num |
|:---|:----|
| 1  | 2   |
| 2  | 2   |
| 3  | 3   |
| 4  | 1   |

---

### Bước 3: Sắp xếp giảm dần và lấy người đứng đầu `ORDER BY num DESC LIMIT 1`

- Sau khi sắp xếp giảm dần theo `num`:
  1. `id = 3`, `num = 3`
  2. `id = 1`, `num = 2`
  3. `id = 2`, `num = 2`
  4. `id = 4`, `num = 1`
- Mệnh đề `LIMIT 1` chọn ra dòng đầu tiên:

```text
+----+-----+
| id | num |
+----+-----+
| 3  | 3   |
+----+-----+
```

---

## 🚀 GIẢI QUYẾT BÀI TOÁN MỞ RỘNG (FOLLOW-UP)

> **Câu hỏi Follow-up:** *"Trong thực tế, có thể có nhiều người cùng có số lượng bạn bè nhiều nhất. Làm thế nào để tìm được TẤT CẢ những người này?"*

Nếu sử dụng `LIMIT 1`, ta sẽ vô tình bỏ sót các ứng viên đồng hạng nhất nếu có nhiều người cùng có số bạn bè bằng nhau. Có hai cách tiếp cận tiêu chuẩn trong SQL để giải quyết triệt để trường hợp này:

---

### Cách 1: Sử dụng Hàm xếp hạng Cửa sổ `DENSE_RANK()` (Khuyên dùng ⭐)

Hàm `DENSE_RANK()` (hoặc `RANK()`) sẽ gán hạng 1 cho tất cả các bản ghi có giá trị `num` lớn nhất. Sau đó ta chỉ cần lọc ra những bản ghi có `rnk = 1`.

```sql
WITH FriendCounts AS (
    SELECT id, COUNT(*) AS num
    FROM (
        SELECT requester_id AS id FROM RequestAccepted
        UNION ALL
        SELECT accepter_id AS id FROM RequestAccepted
    ) AS all_friends
    GROUP BY id
),
RankedFriends AS (
    SELECT 
        id, 
        num, 
        DENSE_RANK() OVER (ORDER BY num DESC) AS rnk
    FROM FriendCounts
)
SELECT id, num
FROM RankedFriends
WHERE rnk = 1;
```

- **Ưu điểm:** Cú pháp hiện đại, rõ ràng, không cần quét lại bảng phụ để tìm `MAX(num)`. Xử lý đồng hạng hoàn hảo cho mọi cơ sở dữ liệu hỗ trợ Window Functions (MySQL 8.0+, PostgreSQL, SQL Server, Oracle).

---

### Cách 2: Sử dụng Subquery tìm giá trị `MAX(num)`

Ta gom nhóm và đếm số bạn bè, sau đó lọc những người có `num` bằng với giá trị lớn nhất trong danh sách:

```sql
WITH FriendCounts AS (
    SELECT id, COUNT(*) AS num
    FROM (
        SELECT requester_id AS id FROM RequestAccepted
        UNION ALL
        SELECT accepter_id AS id FROM RequestAccepted
    ) AS all_friends
    GROUP BY id
)
SELECT id, num
FROM FriendCounts
WHERE num = (SELECT MAX(num) FROM FriendCounts);
```

- **Ưu điểm:** Tương thích với cả các phiên bản MySQL cũ (MySQL 5.7 trở về trước chưa có Window Functions).

---

## 🔄 CÁC CÁCH TIẾP CẬN KHÁC

### 1. Viết dưới dạng CTE (Common Table Expression)

Để câu truy vấn chính trông sáng sủa và dễ bảo trì hơn, ta có thể tách khối `UNION ALL` thành một CTE:

```sql
WITH AllFriends AS (
    SELECT requester_id AS id FROM RequestAccepted
    UNION ALL
    SELECT accepter_id AS id FROM RequestAccepted
)
SELECT id, COUNT(*) AS num
FROM AllFriends
GROUP BY id
ORDER BY num DESC
LIMIT 1;
```

---

## ⚠️ Các lỗi thường gặp (Common Pitfalls)

### 1. Nhầm lẫn giữa `UNION` và `UNION ALL`
- ❌ **Sai:** Dùng `UNION`.
  ```sql
  SELECT requester_id AS id FROM RequestAccepted
  UNION
  SELECT accepter_id AS id FROM RequestAccepted
  ```
  `UNION` khử trùng lặp các ID, khiến mỗi người chỉ xuất hiện 1 lần duy nhất trong toàn bộ kết quả, dẫn đến việc `COUNT(*)` sau đó luôn trả về `1` cho mọi người!
- ✔️ **Đúng:** Bắt buộc dùng `UNION ALL` để bảo toàn mọi lần kết bạn.

### 2. Chỉ tính một chiều (chỉ đếm `requester_id` hoặc chỉ đếm `accepter_id`)
- Một người có thể chủ yếu là người nhận lời mời (ít khi chủ động gửi). Nếu chỉ `GROUP BY requester_id`, ta sẽ bỏ sót hoàn toàn số lượng bạn bè mà họ nhận được.

### 3. Dùng `FULL OUTER JOIN` giữa bảng đếm người gửi và bảng đếm người nhận
- Mặc dù ý tưởng tính riêng số lần gửi và số lần nhận rồi cộng lại `COALESCE(sent, 0) + COALESCE(received, 0)` là đúng về mặt logic, nhưng cách viết này rất dài dòng, MySQL không hỗ trợ trực tiếp cú pháp `FULL OUTER JOIN`, và hiệu năng kém hơn rất nhiều so với `UNION ALL`.

---

## 📊 Đánh giá độ phức tạp

- **Độ phức tạp thời gian (Time Complexity):** $\mathcal{O}(N \log N)$ (với $N$ là tổng số bản ghi trong `RequestAccepted`):
  - `UNION ALL` tốn $\mathcal{O}(N)$ do chỉ nối trực tiếp hai danh sách.
  - Gom nhóm `GROUP BY` tốn $\mathcal{O}(N)$ khi dùng Hash Table gom nhóm.
  - Sắp xếp `ORDER BY num DESC LIMIT 1` tốn $\mathcal{O}(K \log K)$ với $K$ là số người dùng riêng biệt ($K \le 2N$). Nhiều Database engine tối ưu `ORDER BY ... LIMIT 1` bằng Top-1 Heap trong $\mathcal{O}(K)$.
- **Độ phức tạp không gian (Space Complexity):** $\mathcal{O}(N)$ để lưu trữ bảng tạm gồm $2N$ dòng từ phép `UNION ALL` và bảng băm đếm số lượng bạn bè theo `id`.
