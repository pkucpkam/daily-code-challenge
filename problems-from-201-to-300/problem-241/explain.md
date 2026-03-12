# Ý tưởng

Ta cần tìm các số xuất hiện **liên tiếp ít nhất 3 lần** theo thứ tự `id`.

Vì `id` tăng dần từng bước 1 (auto-increment), nên nếu một giá trị `num` lặp 3 lần liên tiếp thì sẽ tồn tại bộ 3 dòng:

- dòng đầu có `id = x`
- dòng thứ hai có `id = x + 1`
- dòng thứ ba có `id = x + 2`

và cả 3 dòng có cùng `num`.

## Truy vấn

```sql
SELECT DISTINCT l1.num AS ConsecutiveNums
FROM Logs l1
JOIN Logs l2 ON l2.id = l1.id + 1
JOIN Logs l3 ON l3.id = l1.id + 2
WHERE l1.num = l2.num
	AND l2.num = l3.num;
```

## Giải thích từng phần

- `l1`, `l2`, `l3` là 3 bản sao của bảng `Logs`.
- Điều kiện join:
	- `l2.id = l1.id + 1`
	- `l3.id = l1.id + 2`
	đảm bảo 3 dòng đứng cạnh nhau theo `id`.
- `WHERE l1.num = l2.num AND l2.num = l3.num` đảm bảo cùng một số lặp lại 3 lần liên tiếp.
- `DISTINCT` loại bỏ trùng lặp trong trường hợp một số có chuỗi dài hơn 3 (ví dụ 4 hoặc 5 lần liên tiếp).

## Độ đúng

- Nếu một số xuất hiện liên tiếp ít nhất 3 lần, chắc chắn sẽ có một bộ 3 dòng thỏa điều kiện join + where, nên số đó được chọn.
- Ngược lại, chỉ khi có đúng 3 dòng liên tiếp cùng `num` thì truy vấn mới trả về, nên kết quả không thừa.

## Độ phức tạp

Truy vấn dùng self-join 3 lần trên khóa `id`; với chỉ mục phù hợp trên `id` (thường có sẵn do PK), hiệu năng tốt trên dữ liệu thông dụng của bài toán.
