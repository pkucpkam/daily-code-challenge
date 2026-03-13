# Ý tưởng tối ưu (Best Solution)

Ta cần tìm mọi chuỗi con độ dài 10 xuất hiện nhiều hơn 1 lần.

Nếu so sánh trực tiếp từng chuỗi con bằng String thì vẫn chạy được, nhưng tạo quá nhiều object và tốn bộ nhớ/CPU hơn cần thiết.

Giải pháp tối ưu là:

- Mã hóa mỗi ký tự DNA bằng 2 bit:
  - `A -> 00`
  - `C -> 01`
  - `G -> 10`
  - `T -> 11`
- Một chuỗi dài 10 ký tự sẽ tương ứng đúng `20 bit`.
- Dùng rolling hash bitwise để trượt cửa sổ độ dài 10 qua chuỗi.

## Cách làm

1. Duyệt từng ký tự, cập nhật `hash`:
   - `hash = (hash << 2) | code(ký_tự_hiện_tại)`
   - Sau đó giữ lại đúng 20 bit cuối bằng mask:
   - `hash = hash & ((1 << 20) - 1)`

2. Khi đã đi đủ 10 ký tự (tức `i >= 9`):
   - Nếu `hash` chưa thấy trước đó: đưa vào `seen`.
   - Nếu đã thấy và chưa từng thêm vào kết quả: thêm substring hiện tại vào đáp án.

3. Dùng thêm `added` để đảm bảo mỗi chuỗi lặp chỉ thêm 1 lần.

## Vì sao đúng

- Mỗi cửa sổ 10 ký tự luôn được biểu diễn bởi 20 bit cuối của `hash`.
- Mã hóa 2 bit cho 4 ký tự là ánh xạ một-một, nên hai chuỗi 10 ký tự khác nhau sẽ cho mã khác nhau.
- Khi một mã xuất hiện lần thứ hai trở lên, chuỗi tương ứng chắc chắn là chuỗi lặp.
- `added` ngăn việc đưa trùng lặp nhiều lần vào kết quả.

## Độ phức tạp

- Thời gian: `O(n)` với `n = s.length()`.
- Bộ nhớ: `O(n)` trong trường hợp xấu nhất do lưu các mã đã gặp.

## Ghi chú triển khai Java

- `mask = (1 << 20) - 1` dùng để cắt đúng 20 bit cuối.
- Chỉ khi phát hiện lặp mới gọi `substring`, nên giảm tạo String không cần thiết.
