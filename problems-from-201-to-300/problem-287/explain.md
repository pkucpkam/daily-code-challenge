# 309. Best Time to Buy and Sell Stock with Cooldown - Lời giải tối ưu

## Tóm tắt bài toán

Bạn có thể mua bán nhiều lần, nhưng sau khi bán thì ngày tiếp theo phải nghỉ 1 ngày, không được mua.

Mục tiêu là tìm lợi nhuận lớn nhất.

## Ý tưởng tối ưu: Dynamic Programming với 3 trạng thái

Tại mỗi ngày, ta chỉ cần theo dõi 3 trạng thái:

- `hold`: lợi nhuận lớn nhất nếu đang giữ 1 cổ phiếu.
- `sold`: lợi nhuận lớn nhất nếu vừa bán cổ phiếu hôm nay.
- `rest`: lợi nhuận lớn nhất nếu không giữ cổ phiếu và cũng không bị ràng buộc bởi việc vừa bán.

Chuyển trạng thái:

- `hold = max(hold, rest - price)`
- `sold = hold_previous + price`
- `rest = max(rest, sold_previous)`

Kết quả cuối cùng là `max(sold, rest)` vì lúc đó không còn nắm cổ phiếu.

## Vì sao đúng

- Nếu đang ở trạng thái `hold`, hoặc ta tiếp tục giữ, hoặc ta mua mới từ trạng thái `rest` của ngày trước.
- Nếu ở trạng thái `sold`, cách duy nhất là bán ra từ trạng thái `hold` của ngày trước.
- Nếu ở trạng thái `rest`, ta либо đã nghỉ từ trước hoặc vừa kết thúc ngày cooldown sau khi bán.
- Ba trạng thái này bao phủ toàn bộ tình huống hợp lệ, nên đáp án lấy từ chúng là tối ưu.

## Độ phức tạp

- Thời gian: `O(n)`.
- Bộ nhớ phụ: `O(1)`.

## Java implementation

```java
class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int hold = -prices[0];
        int sold = 0;
        int rest = 0;

        for (int i = 1; i < prices.length; i++) {
            int price = prices[i];

            int prevHold = hold;
            int prevSold = sold;
            int prevRest = rest;

            hold = Math.max(prevHold, prevRest - price);
            sold = prevHold + price;
            rest = Math.max(prevRest, prevSold);
        }

        return Math.max(sold, rest);
    }
}
```
