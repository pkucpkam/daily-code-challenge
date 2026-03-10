# Rank Scores (SQL)

## Yêu cầu bài toán

- Xếp hạng điểm `score` theo thứ tự giảm dần.
- Nếu nhiều dòng có cùng `score` thì cùng `rank`.
- Thứ hạng là kiểu `dense rank` (không nhảy số sau khi hòa).

## Ý tưởng cốt lõi

Với mỗi dòng `s` trong bảng `Scores`, ta đếm có bao nhiêu mức điểm **phân biệt** lớn hơn hoặc bằng `s.score`.

- Số lượng đó chính là thứ hạng của `s`.
- Dùng `COUNT(DISTINCT s2.score)` để tránh đếm trùng.

## Truy vấn

```sql
SELECT
    s.score,
    (
        SELECT COUNT(DISTINCT s2.score)
        FROM Scores s2
        WHERE s2.score >= s.score
    ) AS `rank`
FROM Scores s
ORDER BY s.score DESC;
```

## Vì sao đúng?

- Giả sử `s.score = x`.
- Mọi giá trị điểm phân biệt thuộc tập `{score | score >= x}` đều đứng trước hoặc bằng `x` trong thứ tự giảm dần.
- Nếu có `k` giá trị phân biệt như vậy, thì `x` đứng ở vị trí thứ `k` theo quy tắc `dense rank`.
- Các dòng cùng điểm `x` tạo cùng tập đếm, nên cùng hạng.

## Độ phức tạp

- Thời gian: thường vào khoảng `O(n^2)` với truy vấn tương quan thuần túy.
- Không gian: phụ thuộc bộ tối ưu của hệ quản trị CSDL.

## Ghi chú

- Nếu hệ quản trị hỗ trợ hàm cửa sổ, có thể viết ngắn hơn bằng `DENSE_RANK()`.
