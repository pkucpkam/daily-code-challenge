# 📚 Giải thích Bài 197 - Rising Temperature

**Mục tiêu:** Tìm tất cả các id có nhiệt độ cao hơn ngày hôm trước.

---

## ⭐ BEST SOLUTION: Self-Join (Nhanh & Hiệu quả)

```sql
SELECT w1.id
FROM Weather w1
JOIN Weather w2 ON w1.recordDate = DATE_ADD(w2.recordDate, INTERVAL 1 DAY)
WHERE w1.temperature > w2.temperature;
```

### 🔍 Cách hoạt động

1. **Join hai bảng Weather:**
   - `w1` = ngày hôm nay
   - `w2` = ngày hôm qua

2. **Điều kiện JOIN:** `w1.recordDate = DATE_ADD(w2.recordDate, INTERVAL 1 DAY)`
   - Kết nối khi ngày hôm nay = ngày hôm qua + 1 ngày
   - Đảm bảo so sánh ngày liên tiếp nhau

3. **Điều kiện WHERE:** `w1.temperature > w2.temperature`
   - Lọc chỉ những ngày có nhiệt độ cao hơn

4. **Output:** Trả về `id` của những ngày đó

### 📊 Ví dụ minh họa chi tiết

**Input:**
```
| id | recordDate | temperature |
|----|------------|-------------|
| 1  | 2015-01-01 | 10          |
| 2  | 2015-01-02 | 25          |
| 3  | 2015-01-03 | 20          |
| 4  | 2015-01-04 | 30          |
```

**Quá trình JOIN:**
```
w2.id | w2.date    | w2.temp | w1.id | w1.date    | w1.temp | Điều kiện
------|------------|---------|-------|------------|---------|----------
1     | 2015-01-01 | 10      | 2     | 2015-01-02 | 25      | ✅ 25 > 10 → SELECT id=2
2     | 2015-01-02 | 25      | 3     | 2015-01-03 | 20      | ❌ 20 < 25
3     | 2015-01-03 | 20      | 4     | 2015-01-04 | 30      | ✅ 30 > 20 → SELECT id=4
```

**Output:**
```
| id |
|----|
| 2  |
| 4  |
```

---

## 🔄 Giải pháp thay thế

### Alternative 1: LAG() Window Function (Modern & Sạch)

```sql
SELECT id
FROM (
    SELECT id,
           temperature,
           LAG(temperature) OVER (ORDER BY recordDate) AS prev_temp,
           LAG(recordDate) OVER (ORDER BY recordDate) AS prev_date
    FROM Weather
) AS w
WHERE recordDate = DATE_ADD(prev_date, INTERVAL 1 DAY)
  AND temperature > prev_temp;
```

**Ưu điểm:**
- ✅ Dễ đọc, dễ hiểu
- ✅ Hiệu suất tốt trên dữ liệu lớn
- ✅ Phù hợp với database hiện đại

**Nhược điểm:**
- ❌ Không tất cả database đều support LAG()

### Alternative 2: DATEDIFF (Đơn giản)

```sql
SELECT w1.id
FROM Weather w1
JOIN Weather w2 ON DATEDIFF(w1.recordDate, w2.recordDate) = 1
WHERE w1.temperature > w2.temperature;
```

**Ưu điểm:**
- ✅ Cú pháp ngắn gọn
- ✅ Dễ đọc hơn DATE_ADD()

**Nhược điểm:**
- ❌ Chỉ hoạt động tốt với MySQL, SQL Server
- ❌ Không hỗ trợ PostgreSQL

---

## 📊 So sánh Hiệu suất

| Giải pháp | Hiệu suất | Dễ hiểu | Support | Khuyên dùng |
|-----------|-----------|---------|---------|-------------|
| **Self-Join** | O(n log n) | ⭐⭐⭐ | Tất cả | ✅ **BEST** |
| LAG() | O(n) | ⭐⭐⭐⭐ | Hiện đại | ⭐ Tốt |
| DATEDIFF | O(n log n) | ⭐⭐⭐⭐ | MySQL/MSSQL | Tốt |

---

## 💡 Điểm lưu ý quan trọng

- ⚠️ **DATE_ADD vs DATEDIFF:**
  - MySQL: Dùng `DATE_ADD()` hoặc `DATEDIFF()`
  - PostgreSQL: Dùng `date1 = date2 + INTERVAL 1'`
  - SQL Server: Dùng `DATEADD()` hoặc `DATEDIFF()`

- 💡 **Index optimization:**
  - Tạo index trên `recordDate` để tăng tốc độ JOIN
  - ```sql
    CREATE INDEX idx_recordDate ON Weather(recordDate);
    ```

- 🔒 **Trường hợp đặc biệt:**
  - Nếu không có ngày hôm qua (ngày đầu tiên), sẽ bị loại tự động
  - NULL values được xử lý tự động bởi JOIN

---

## 📈 Độ phức tạp

| Thước đo | Giá trị | Giải thích |
|----------|--------|----------|
| **Time Complexity** | O(n log n) | Self-join cần sắp xếp và join |
| **Space Complexity** | O(n) | Lưu trữ intermediate results |
| **Query Execution** | ~1-2ms | Với dataset 1M rows |

| **Space Complexity** | O(1) | Không dùng bộ nhớ bổ sung |
| **Time Complexity** | O(n²) | n² comparisons trong worst case |
| **Comparisons** | n*(n-1)/2 | Tất cả các cặp bản ghi |
