# Max Points on a Line

## Yêu cầu bài toán

- Cho một mảng `points`, trong đó mỗi phần tử là một cặp tọa độ `[xi, yi]` trên mặt phẳng 2D. Các điểm này đều phân biệt.
- Nhiệm vụ: Tìm số lượng điểm tối đa cùng nằm trên một đường thẳng.

## Ý tưởng cốt lõi

Làm sao để xác định nhiều điểm cùng nằm trên một đường thẳng? 
Câu trả lời là: Nếu chúng ta chọn một điểm làm gốc (anchor), thì tất cả các điểm khác nằm trên cùng một đường thẳng đi qua gốc đó phải có cùng **Hệ số góc (Slope)**.

Tuy nhiên, việc tính hệ số góc bằng phép chia số thực ($dy/dx$) có thể dẫn đến sai số (precision error). Để giải quyết triệt để, chúng ta đại diện hệ số góc dưới dạng một phân số tối giản $\frac{dy}{dx}$ bằng cách chia cả `dy` và `dx` cho **Ước chung lớn nhất (GCD)** của chúng.

## Thuật toán

1. Nếu số lượng điểm nhỏ hơn hoặc bằng 2, trả về chính số lượng đó (vì 2 điểm bất kỳ luôn tạo thành một đường thẳng).
2. Duyệt qua từng điểm `points[i]` và coi nó là điểm gốc (anchor):
   - Tạo một `HashMap<String, Integer>` để đếm số lượng điểm có cùng hệ số góc đi qua điểm gốc này.
   - Duyệt qua các điểm còn lại `points[j]` (với $j > i$):
     - Tính $dx = x_j - x_i$ và $dy = y_j - y_i$.
     - Tìm ước chung lớn nhất $g = GCD(dx, dy)$ và tối giản $dx, dy$ bằng cách chia cho $g$.
     - Để đảm bảo tính duy nhất của "phân số" hệ số góc (ví dụ $-1/2$ và $1/-2$ là một), chúng ta có thể chuẩn hóa dấu (luôn để $dx > 0$).
     - Tạo một chuỗi định danh (key) ví dụ `"dy/dx"` và lưu vào HashMap.
   - Sau mỗi điểm gốc, cập nhật giá trị kết quả lớn nhất tìm được.
3. Cộng thêm 1 (chính là điểm gốc đang xét) vào kết quả đếm của HashMap để có tổng số điểm trên đường thẳng đó.

## Độ phức tạp
- **Thời gian**: $O(N^2)$ - Hai vòng lặp lồng nhau duyệt qua các cặp điểm.
- **Không gian**: $O(N)$ - HashMap để lưu trữ các hệ số góc cho mỗi điểm gốc.

## Code (Java)

```java
import java.util.*;

class Solution {
    public int maxPoints(int[][] points) {
        int n = points.length;
        if (n <= 2) return n;

        int maxPoints = 1;
        for (int i = 0; i < n; i++) {
            Map<String, Integer> map = new HashMap<>();
            int localMax = 0;
            for (int j = i + 1; j < n; j++) {
                int dx = points[j][0] - points[i][0];
                int dy = points[j][1] - points[i][1];
                
                int g = gcd(dx, dy);
                dx /= g;
                dy /= g;
                
                // Chuẩn hóa để đảm bảo cùng một hệ số góc có cùng một chìa khóa
                String key = dy + "/" + dx;
                map.put(key, map.getOrDefault(key, 0) + 1);
                localMax = Math.max(localMax, map.get(key));
            }
            maxPoints = Math.max(maxPoints, localMax + 1);
        }
        return maxPoints;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
```
