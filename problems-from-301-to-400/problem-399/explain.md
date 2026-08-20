# 📚 Giải thích Bài 550 - Game Play Analysis IV

**Mục tiêu:** Tính tỉ lệ (fraction) người chơi đăng nhập lại vào **ngày ngay sau ngày đầu tiên đăng nhập** (ngày đầu + 1 ngày), làm tròn 2 chữ số thập phân.

---

## 🎯 Phân tích bài toán

Công thức tính tỉ lệ (`fraction`):

$$\text{fraction} = \frac{\text{Số lượng người chơi có đăng nhập vào (Ngày đầu tiên + 1)}}{\text{Tổng số lượng người chơi duy nhất}}$$

Ví dụ:
- **Player 1**: Đăng nhập lần đầu `2016-03-01`, có đăng nhập `2016-03-02` (Ngày đầu + 1) ➔ **Thỏa mãn** ✅
- **Player 2**: Đăng nhập lần đầu `2017-06-25`, không có đăng nhập ngày tiếp theo ➔ **Không thỏa mãn** ❌
- **Player 3**: Đăng nhập lần đầu `2016-03-02`, ngày tiếp theo đăng nhập là `2018-07-03` (không phải ngày kế tiếp) ➔ **Không thỏa mãn** ❌

➜ Tổng người chơi = 3, số người thỏa mãn = 1 ➔ Kết quả = `1 / 3 = 0.33`

---

## ⭐ BEST SOLUTION: CTE (Subquery) + JOIN với `DATE_ADD`

```sql
WITH FirstLogins AS (
    SELECT 
        player_id, 
        MIN(event_date) AS first_date
    FROM Activity
    GROUP BY player_id
)
SELECT 
    ROUND(
        COUNT(a.player_id) * 1.0 / (SELECT COUNT(DISTINCT player_id) FROM Activity), 
        2
    ) AS fraction
FROM FirstLogins f
JOIN Activity a 
    ON f.player_id = a.player_id 
   AND a.event_date = DATE_ADD(f.first_date, INTERVAL 1 DAY);
```

---

## 🔍 Cách hoạt động từng bước

### Bước 1: Tìm ngày đăng nhập đầu tiên của mỗi player (CTE `FirstLogins`)

```sql
SELECT player_id, MIN(event_date) AS first_date
FROM Activity
GROUP BY player_id;
```

**Kết quả CTE `FirstLogins`:**

| player_id | first_date |
|:----------|:-----------|
| 1         | 2016-03-01 |
| 2         | 2017-06-25 |
| 3         | 2016-03-02 |

---

### Bước 2: JOIN với bảng `Activity` để kiểm tra ngày kế tiếp

Kết nối `f.player_id = a.player_id` VÀ `a.event_date = DATE_ADD(f.first_date, INTERVAL 1 DAY)`.

| f.player_id | f.first_date | a.event_date | Khớp điều kiện `first_date + 1 day`? |
|:------------|:-------------|:-------------|:------------------------------------|
| 1           | 2016-03-01   | 2016-03-02   | ✅ Khớp (`2016-03-01` + 1 ngày)     |
| 2           | 2017-06-25   | NULL         | ❌ Không có bản ghi                 |
| 3           | 2016-03-02   | NULL (`2018-07-03` không khớp) | ❌ Không có bản ghi |

---

### Bước 3: Tính tỉ lệ & Làm tròn

- `COUNT(a.player_id)` = 1 (chỉ player 1 khớp INNER JOIN)
- `SELECT COUNT(DISTINCT player_id) FROM Activity` = 3
- `ROUND(1 * 1.0 / 3, 2)` = `0.33`

---

## 🔄 Các giải pháp thay thế (Alternative Solutions)

### Alternative 1: Cú pháp Tuple `IN` (Ngắn gọn & Dễ đọc)

```sql
SELECT 
    ROUND(
        COUNT(DISTINCT player_id) * 1.0 / (SELECT COUNT(DISTINCT player_id) FROM Activity), 
        2
    ) AS fraction
FROM Activity
WHERE (player_id, DATE_SUB(event_date, INTERVAL 1 DAY)) IN (
    SELECT player_id, MIN(event_date)
    FROM Activity
    GROUP BY player_id
);
```

**Ưu điểm:** Cú pháp ngắn gọn, dễ viết trực tiếp trong 1 câu SQL.

---

### Alternative 2: Window Function `MIN() OVER (...)`

```sql
WITH RankedLogins AS (
    SELECT 
        player_id,
        event_date,
        MIN(event_date) OVER (PARTITION BY player_id) AS first_date
    FROM Activity
)
SELECT 
    ROUND(
        COUNT(DISTINCT CASE WHEN event_date = DATE_ADD(first_date, INTERVAL 1 DAY) THEN player_id END) * 1.0 
        / COUNT(DISTINCT player_id), 
        2
    ) AS fraction
FROM RankedLogins;
```

**Ưu điểm:** Phù hợp với database hiện đại, chỉ cần quét bảng qua Window Function.

---

## 📊 So sánh các giải pháp

| Phương pháp | Độ phức tạp | Tính dễ đọc | Đánh giá |
|:------------|:------------|:------------|:---------|
| **CTE + JOIN (Recommended)** | $\mathcal{O}(N \log N)$ | ⭐⭐⭐⭐⭐ | ✅ **Khuyên dùng (Nhanh & Tối ưu Index)** |
| **Tuple IN Subquery** | $\mathcal{O}(N \log N)$ | ⭐⭐⭐⭐ | ⭐ Rất tốt |
| **Window Function (MIN OVER)** | $\mathcal{O}(N \log N)$ | ⭐⭐⭐⭐ | ⭐ Phù hợp DB hiện đại |

---

## 💡 Điểm lưu ý quan trọng

1. **Ép kiểu Số thực (`* 1.0`)**: Trong SQL Server/PostgreSQL, phép chia 2 số nguyên (`1 / 3`) trả về `0`. Nhân với `1.0` để chuyển thành số thực trước khi chia.
2. **Cộng/Trừ Ngày (`DATE_ADD` / `DATE_SUB`)**:
   - MySQL: `DATE_ADD(date, INTERVAL 1 DAY)`
   - PostgreSQL: `date + INTERVAL '1 day'`
   - SQL Server: `DATEADD(day, 1, date)`
3. **Primary Key**: Bảng có primary key là `(player_id, event_date)`, đảm bảo một người chơi chỉ có tối đa 1 bản ghi trong một ngày.

---

## 📈 Độ phức tạp (Complexity)

- **Time Complexity:** $\mathcal{O}(N \log N)$ do thao tác `GROUP BY` / Index scan trên `player_id`.
- **Space Complexity:** $\mathcal{O}(N)$ cho bộ nhớ làm việc tạm thời của CTE / Subquery.
