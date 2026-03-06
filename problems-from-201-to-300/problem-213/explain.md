# Single Number II

## Yêu cầu bài toán

- Cho một mảng số nguyên `nums`.
- Trong mảng này, mọi phần tử đều xuất hiện đúng **3 lần**, ngoại trừ duy nhất một phần tử chỉ xuất hiện **1 lần**.
- Hãy tìm và trả về phần tử xuất hiện 1 lần đó.
- Yêu cầu: Giải thuật phải có độ phức tạp thời gian $O(N)$ và độ phức tạp không gian $O(1)$.

## Ý tưởng cốt lõi

Vì yêu cầu không gian $O(1)$, chúng ta không thể sử dụng HashMap hay HashSet để đếm số lần xuất hiện. Thay vào đó, chúng ta sử dụng kỹ thuật **Thao tác Bit (Bit Manipulation)**.

Ý tưởng là xây dựng một "bộ đếm" trạng thái cho mỗi bit. Vì mỗi số xuất hiện 3 lần, nếu chúng ta đếm số lượng bit 1 tại mỗi vị trí (từ 0 đến 31), tổng số bit 1 tại mỗi vị trí đó sẽ chia hết cho 3 nếy không có số đơn lẻ. Số dư của phép chia cho 3 tại mỗi vị trí bit chính là bit của số cần tìm.

Chúng ta sử dụng hai biến `ones` và `twos` để theo dõi trạng thái xuất hiện của các bit:
- `ones`: Lưu trữ các bit đã xuất hiện 1 lần (hoặc 4, 7... lần).
- `twos`: Lưu trữ các bit đã xuất hiện 2 lần (hoặc 5, 8... lần).
- Khi một bit xuất hiện lần thứ 3, nó sẽ bị xóa khỏi cả `ones` và `twos`.

## Thuật toán

Chạy qua từng số `num` trong mảng:
1. `ones = (ones ^ num) & ~twos`:
   - `ones ^ num` thêm các bit của `num` vào `ones`.
   - `& ~twos` đảm bảo rằng nếu một bit đã xuất hiện 2 lần (nằm trong `twos`), nó sẽ không được thêm vào `ones`.
2. `twos = (twos ^ num) & ~ones`:
   - `twos ^ num` thêm các bit đã xuất hiện lần thứ 2 vào `twos`.
   - `& ~ones` đảm bảo rằng nếu một bit vừa được thêm vào `ones` (xuất hiện lần 1), nó sẽ không nằm trong `twos`.

Sau khi duyệt hết mảng, biến `ones` sẽ giữ giá trị của số duy nhất xuất hiện 1 lần.

## Độ phức tạp
- **Thời gian**: $O(N)$ - Duyệt mảng một lần.
- **Không gian**: $O(1)$ - Chỉ sử dụng hai biến nguyên.

## Code (Java)

```java
class Solution {
    public int singleNumber(int[] nums) {
        int ones = 0; // Lưu các bit xuất hiện 1 lần
        int twos = 0; // Lưu các bit xuất hiện 2 lần
        for (int num : nums) {
            // Cập nhật ones: thêm bit mới nhưng loại bỏ nếu đã có trong twos
            ones = (ones ^ num) & ~twos;
            // Cập nhật twos: thêm bit mới nhưng loại bỏ nếu đã có trong ones
            twos = (twos ^ num) & ~ones;
        }
        return ones;
    }
}
```
