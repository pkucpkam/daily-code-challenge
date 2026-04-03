# Best Time to Buy and Sell Stock

## 1. Mô tả bài toán
Cho một mảng `prices` trong đó `prices[i]` là giá của một cổ phiếu vào ngày thứ `i`.
Bạn muốn tối đa hóa lợi nhuận của mình bằng cách chọn một ngày để mua cổ phiếu và chọn một ngày khác trong tương lai để bán cổ phiếu đó.
Hãy trả về lợi nhuận tối đa có thể đạt được. Nếu không thể đạt được lợi nhuận nào, hãy trả về 0.

## 2. Ý tưởng cốt lõi
- Để có lợi nhuận lớn nhất, ta cần "mua ở đáy và bán ở đỉnh" (trong đó ngày mua phải trước ngày bán).
- Ta có thể duyệt qua mảng một lần (One Pass).
- Trong quá trình duyệt, ta luôn ghi nhớ mức giá thấp nhất đã gặp cho đến thời điểm hiện tại (`minPrice`).
- Đối với mỗi ngày tiếp theo, lợi nhuận tiềm năng sẽ là `price_hiện_tại - minPrice`. Ta cập nhật lợi nhuận tối đa (`maxProfit`) nếu tìm thấy mức lợi nhuận lớn hơn.

## 3. Giải thích thuật toán
1. Khởi tạo `maxProfit = 0`.
2. Khởi tạo `minPrice` là một giá trị rất lớn (`Integer.MAX_VALUE`).
3. Duyệt qua từng mức giá `price` trong mảng `prices`:
   - Nếu `price` hiện tại nhỏ hơn `minPrice`, cập nhật `minPrice = price` (như vậy ta tìm thấy một ngày mua lý tưởng mới).
   - Ngược lại (nếu bán vào ngày này có lãi), hãy tính `profit = price - minPrice`. Cập nhật `maxProfit = Math.max(maxProfit, profit)`.
4. Trả về `maxProfit`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Ta chỉ duyệt qua mảng `prices` một lần duy nhất.
- **Không gian (Space Complexity)**: \(O(1)\) - Chỉ sử dụng hai biến đơn giản để lưu trữ trạng thái.

## 5. Code (Java)
```java
class Solution {
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        int minPrice = Integer.MAX_VALUE;

        for (int price : prices) {
            // Cập nhật giá mua thấp nhất
            if (price < minPrice) {
                minPrice = price; 
            } 
            // Cập nhật lợi nhuận tối đa có thể đạt được
            else {
                maxProfit = Math.max(maxProfit, price - minPrice); 
            }
        }

        return maxProfit; 
    }
}
```
