# Best Time to Buy and Sell Stock III

## Yêu cầu bài toán

- Cho mảng `prices` trong đó `prices[i]` là giá cổ phiếu trong ngày thứ `i`.
- Tìm mức lợi nhuận tối đa có thể đạt được. Bạn có thể hoàn thành tối đa **hai** giao dịch.
- Lưu ý: Không thể thực hiện nhiều giao dịch cùng lúc (phải bán cổ phiếu trước khi mua lại).

## Ý tưởng cốt lõi

Bài toán yêu cầu tìm lợi nhuận lớn nhất với tối đa 2 lần giao dịch (mua và bán). 
Để giải quyết bài toán này, ta có thể theo dõi 4 trạng thái trong suốt quá trình duyệt qua danh sách giá trị cổ phiếu:
1. `firstBuy`: Chi phí thấp nhất để mua lần 1 (nên chú ý ta muốn tối đa hóa số dư tài khoản sau khi mua, nên ta lưu theo dạng số âm `-price` để dần tịnh tiến lên).
2. `firstSell`: Lợi nhuận cao nhất sau khi bán lần 1 (`firstBuy + price`).
3. `secondBuy`: Lợi nhuận cao nhất sau khi trừ đi vốn nhả ra để mua lần 2 (`firstSell - price`).
4. `secondSell`: Lợi nhuận cao nhất sau khi bán lần 2 (`secondBuy + price`).

## Cách cập nhật các trạng thái
Với mỗi giá `p` trong mảng `prices`:
- Trạng thái `firstBuy`: Ta chọn cách đầu tư rẻ nhất cho đến giờ: `Math.max(firstBuy, -p)`.
- Trạng thái `firstSell`: Ta chọn cách bán tốt nhất ở giao dịch đầu nếu bán ở giá `p`: `Math.max(firstSell, firstBuy + p)`.
- Trạng thái `secondBuy`: Dựa trên lợi nhuận đã chốt ở lần `firstSell`, ta mua giao dịch thứ hai với giá `p`: `Math.max(secondBuy, firstSell - p)`.
- Trạng thái `secondSell`: Dựa trên lợi nhuận còn lại ở `secondBuy`, chốt lời ở giao dịch cuối: `Math.max(secondSell, secondBuy + p)`.

Do quy trình này chạy song song, đến cuối mảng, `secondSell` sẽ là lợi nhuận tốt nhất nếu ta làm việc với tối đa 2 giao dịch.

## Vì sao đúng?

- Tại mỗi mức giá `p`, ta luôn thử lấy quyết định tối ưu có lợi nhất từ những diễn biến đã qua.
- Các biểu thức cập nhật có thứ tự phụ thuộc (bán 2 phụ thuộc mua 2, mua 2 phụ thuộc bán 1, v.v.), nên việc tìm kiếm cục bộ sẽ dần dần cập nhật giá trị đến kết quả lợi nhuận lớn nhất ở cuối mảng. 
- Ngay cả khi ta chỉ thực hiện 1 giao dịch, trạng thái thứ 2 vẫn có thể "tái sử dụng" lại y nguyên trạng thái của giao dịch 1 nên kết quả `secondSell` luôn bao hàm cả trường hợp tối ưu 1 giao dịch hoặc 0 giao dịch.

## Độ phức tạp

- Thời gian: `O(N)` với `N` là số phần tử của mảng `prices` vì ta duyệt qua mảng đúng 1 lần.
- Không gian phụ: `O(1)` vì chỉ dùng 4 biến lưu trữ trạng thái hiện tại.

## Code (Java)

```java
class Solution {
    public int maxProfit(int[] prices) {
        int firstBuy = Integer.MIN_VALUE;
        int firstSell = 0;
        int secondBuy = Integer.MIN_VALUE;
        int secondSell = 0;

        for (int p : prices) {
            firstBuy = Math.max(firstBuy, -p);
            firstSell = Math.max(firstSell, firstBuy + p);
            secondBuy = Math.max(secondBuy, firstSell - p);
            secondSell = Math.max(secondSell, secondBuy + p);
        }
        return secondSell;
    }
}
```
