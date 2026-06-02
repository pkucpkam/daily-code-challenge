# Giải thích thuật toán - Kiểm tra chuỗi UTF-8 hợp lệ (UTF-8 Validation)

## Ý tưởng chính
Một ký tự UTF-8 có thể dài từ 1 đến 4 byte. Chúng ta cần duyệt qua từng byte trong mảng `data` và kiểm tra các quy tắc mã hóa. Bằng cách sử dụng các phép toán thao tác bit (bit manipulation), chúng ta có thể dễ dàng kiểm tra các bit đầu tiên của mỗi byte.

## Cách tiếp cận
Chúng ta sử dụng một biến `remainingBytes` để theo dõi số lượng byte tiếp theo (continuation bytes) cần được xử lý.

1.  **Nếu `remainingBytes == 0`**: Nghĩa là chúng ta đang ở byte bắt đầu của một ký tự UTF-8 mới. Ta sẽ kiểm tra các bit cao (đầu tiên) của byte này:
    *   `val >> 7 == 0b0`: Ký tự 1-byte. `remainingBytes` giữ nguyên là 0.
    *   `val >> 5 == 0b110`: Ký tự 2-byte. Gán `remainingBytes = 1`.
    *   `val >> 4 == 0b1110`: Ký tự 3-byte. Gán `remainingBytes = 2`.
    *   `val >> 3 == 0b11110`: Ký tự 4-byte. Gán `remainingBytes = 3`.
    *   Nếu không thuộc các trường hợp trên, chuỗi này không hợp lệ, lập tức trả về `false`.

2.  **Nếu `remainingBytes > 0`**: Nghĩa là byte hiện tại là một "continuation byte" (byte tiếp nối) thuộc cùng ký tự của byte bắt đầu trước đó.
    *   Quy tắc của byte tiếp nối là phải bắt đầu bằng `10`.
    *   Ta kiểm tra bằng phép dịch bit: `val >> 6 == 0b10`.
    *   Nếu đúng, ta giảm `remainingBytes` đi 1.
    *   Nếu sai, chuỗi không hợp lệ, trả về `false`.

3.  Cuối cùng, sau khi duyệt xong toàn bộ mảng `data`, biến `remainingBytes` phải bằng 0 (tức là tất cả các ký tự nhiều byte đều đã nhận đủ số byte tiếp nối). Nếu bằng 0 thì trả về `true`, ngược lại `false`.

## Phân tích độ phức tạp
- **Thời gian (Time Complexity)**: $O(N)$ với $N$ là độ dài của mảng `data`. Thuật toán duyệt qua mảng đúng một lần.
- **Không gian bộ nhớ (Space Complexity)**: $O(1)$. Chỉ sử dụng một số lượng biến cố định (`remainingBytes`), không phụ thuộc vào kích thước mảng đầu vào.

## Bảng thao tác bit
Trong Java/C++, ta có thể dịch bit số nguyên `val` sang phải để lấy một số lượng bit cao cấp (most significant bits) tương ứng và so sánh trực tiếp, ví dụ:
*   `0b0` = 0 (Hệ thập phân)
*   `0b110` = 6
*   `0b1110` = 14
*   `0b11110` = 30
*   `0b10` = 2

Điều này giúp code trở nên rất trực quan, ngắn gọn và có hiệu suất thực thi cao nhất.
