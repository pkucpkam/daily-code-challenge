# Giải thích bài toán: Sales Person

Bài toán yêu cầu: tìm tên của tất cả những nhân viên bán hàng chưa từng có bất kỳ đơn hàng nào liên quan đến công ty có tên là `RED`.

## Ý tưởng

Ta cần lọc ra những `sales_id` trong bảng `SalesPerson` mà:
- không có bất kỳ bản ghi nào trong `Orders` tham chiếu đến `Company` có tên là `RED`.

Nói cách khác, nếu một salesperson đã có đơn hàng với công ty RED, thì người đó sẽ bị loại bỏ.

## Cách giải

Ta dùng điều kiện `NOT EXISTS` để kiểm tra xem salesperson đó có tồn tại order nào liên quan đến công ty RED hay không.

```sql
SELECT SP.name
FROM SalesPerson SP
WHERE NOT EXISTS (
    SELECT 1
    FROM Orders O
    JOIN Company C ON O.com_id = C.com_id
    WHERE SP.sales_id = O.sales_id
      AND C.name = 'RED'
);
```

## Cách hoạt động

- Bước 1: Chọn tất cả người bán hàng từ bảng `SalesPerson`.
- Bước 2: Với mỗi người bán hàng, kiểm tra subquery:
  - nối `Orders` với `Company` theo `com_id`
  - tìm xem có order nào có `sales_id` trùng với salesperson hiện tại và `Company.name = 'RED'` không
- Bước 3:
  - nếu tìm thấy kết quả, thì `NOT EXISTS` sẽ là `FALSE` => người đó bị loại
  - nếu không tìm thấy kết quả, thì `NOT EXISTS` sẽ là `TRUE` => người đó được giữ lại

## Ví dụ

Từ dữ liệu mẫu:
- John có order với công ty RED
- Pam có order với công ty RED
- Amy, Mark, Alex không có order với công ty RED

Vì vậy kết quả là:

```text
Amy
Mark
Alex
```

## Độ phức tạp

- Subquery sẽ kiểm tra từng salesperson với các order liên quan
- Tổng thời gian khá tốt cho dữ liệu bảng trong bài toán SQL này
- Đây là cách tiếp cận rõ ràng và dễ hiểu hơn so với việc join rồi group by

## Kết luận

Cách giải này đúng vì nó chọn những người bán hàng mà không có bất kỳ giao dịch nào với công ty RED, theo đúng yêu cầu của đề bài.