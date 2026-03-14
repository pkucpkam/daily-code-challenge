# Ý tưởng tối ưu (Best Solution)

Ta cần xoay mảng sang phải `k` bước và yêu cầu follow-up là làm in-place với `O(1)` bộ nhớ phụ.

Giải pháp tối ưu là dùng kỹ thuật **đảo mảng 3 lần**:

1. Đảo toàn bộ mảng.
2. Đảo đoạn đầu có độ dài `k`.
3. Đảo đoạn còn lại từ `k` đến hết mảng.

## Ví dụ trực quan

`nums = [1,2,3,4,5,6,7], k = 3`

- Đảo toàn bộ: `[7,6,5,4,3,2,1]`
- Đảo `k=3` phần tử đầu: `[5,6,7,4,3,2,1]`
- Đảo phần còn lại: `[5,6,7,1,2,3,4]`

Đúng kết quả cần tìm.

## Các bước triển khai

1. Chuẩn hóa `k`:
  - `k = k % n` để tránh xoay thừa vòng.
2. Nếu `k == 0` thì không cần làm gì.
3. Gọi hàm `reverse(nums, l, r)` cho 3 đoạn theo thứ tự ở trên.

## Tại sao đúng

Sau khi đảo toàn bộ, thứ tự các phần tử bị đảo ngược hoàn toàn.

- Đoạn đầu dài `k` lúc này chính là `k` phần tử cuối của mảng ban đầu, nhưng đang ngược thứ tự.
- Đảo lại đoạn đầu để khôi phục đúng thứ tự của nhóm này.
- Đoạn còn lại cũng đang ngược thứ tự, nên đảo lại để khôi phục.

Kết quả cuối cùng đúng bằng mảng ban đầu xoay phải `k` bước.

## Độ phức tạp

- Thời gian: `O(n)` (mỗi phần tử được hoán đổi số lần hằng số).
- Bộ nhớ: `O(1)` (chỉ dùng biến tạm khi swap).

## So sánh nhanh với cách khác

- Dùng mảng phụ: dễ hiểu nhưng tốn `O(n)` bộ nhớ.
- Dịch từng bước `k` lần: có thể lên tới `O(n*k)`, chậm khi `k` lớn.
- Đảo mảng 3 lần: nhanh, gọn, đúng yêu cầu follow-up.
