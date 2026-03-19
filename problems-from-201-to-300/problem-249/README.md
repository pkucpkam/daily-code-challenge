# 197. Rising Temperature

## 📋 Mức độ: Easy

---

## 📊 Schema

**Table: Weather**

| Column Name   | Type    |
|:--------------|:--------|
| id            | int     |
| recordDate    | date    |
| temperature   | int     |

- **id** là cột unique key
- Không có các hàng với cùng recordDate
- Bảng chứa thông tin nhiệt độ theo ngày

---

## 🎯 Bài toán

Viết một câu lệnh SQL để tìm **tất cả các id có nhiệt độ cao hơn ngày hôm trước**.

Trả về kết quả theo thứ tự bất kỳ.

---

## 📝 Ví dụ

### Input: Weather table

| id  | recordDate | temperature |
|:----|:-----------|:------------|
| 1   | 2015-01-01 | 10          |
| 2   | 2015-01-02 | 25          |
| 3   | 2015-01-03 | 20          |
| 4   | 2015-01-04 | 30          |

### Output

| id  |
|:----|
| 2   |
| 4   |

### 📌 Giải thích

- **2015-01-02**: Nhiệt độ cao hơn ngày trước (10 → 25) ✅
- **2015-01-04**: Nhiệt độ cao hơn ngày trước (20 → 30) ✅

---

## 💭 Constraint

- Ngày ghi nhận không trùng lặp
- Cần so sánh ngày liên tiếp (hôm nay vs hôm qua)