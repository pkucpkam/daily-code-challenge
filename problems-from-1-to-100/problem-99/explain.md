# Teemo Attacking

## 1. Mô tả bài toán
Teemo tấn công đối thủ Ashe bằng chất độc. Mỗi đòn tấn công tại thời điểm `t` sẽ gây độc trong `duration` giây. Nếu Teemo tấn công lần nữa khi đối thủ vẫn đang bị nhiễm độc, bộ đếm thời gian sẽ bị reset lại.
Cho mảng `timeSeries` tăng dần (thời điểm tấn công) và số nguyên `duration`. Hãy tính tổng thời gian đối thủ bị nhiễm độc.
Ví dụ:
- `timeSeries = [1, 4], duration = 2`:
  - Lần 1 (giây 1): bị độc giây [1, 2].
  - Lần 2 (giây 4): bị độc giây [4, 5]. Tổng là 4 giây.
- `timeSeries = [1, 2], duration = 2`:
  - Lần 1 (giây 1): độc [1, 2].
  - Lần 2 (giây 2): reset, độc [2, 3]. Tổng là 3 giây (1, 2, 3).

## 2. Ý tưởng cốt lõi
- Ta duyệt qua từng thời điểm tấn công. 
- Khoảng cách thời gian thực tế giữa hai đòn tấn công liên tiếp \(i\) và \(i+1\) là `timeSeries[i+1] - timeSeries[i]`.
- Nếu khoảng cách này nhỏ hơn `duration`, thời gian nhiễm độc tăng thêm chỉ bằng khoảng cách đó. 
- Nếu khoảng cách này lớn hơn hoặc bằng `duration`, thời gian nhiễm độc tăng thêm trọn vẹn `duration` giây.
- Riêng đòn tấn công cuối cùng luôn đóng góp đủ `duration` giây.

## 3. Giải thích thuật toán
1. Trường hợp mảng rỗng: trả về 0.
2. Khởi tạo `totalTime = 0`.
3. Chạy vòng lặp từ `i = 0` đến `n - 2` (phần tử áp chót):
   - Tính khoảng cách giữa đòn tấn công hiện tại và đòn tiếp theo: `diff = timeSeries[i+1] - timeSeries[i]`.
   - Kết hợp: `totalTime += Math.min(diff, duration)`.
4. Cộng thêm `duration` cho đòn tấn công cuối cùng: `totalTime += duration`.
5. Trả về `totalTime`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Duyệt qua mảng `timeSeries` một lần.
- **Không gian (Space Complexity)**: \(O(1)\) - Không sử dụng bộ nhớ phụ.

## 5. Code (Java)
```java
class Solution {
    public int findPoisonedDuration(int[] timeSeries, int duration) {
        if (timeSeries == null || timeSeries.length == 0) return 0;
        
        int totalPoisonedTime = 0;
        
        // Duyệt qua các đòn tấn công trừ đòn cuối cùng
        for (int i = 0; i < timeSeries.length - 1; i++) {
            // Tính khoảng cách giữa hai đòn tấn công
            int interval = timeSeries[i + 1] - timeSeries[i];
            
            // Nếu khoảng cách < duration, chỉ tính thời gian thực tế nhiễm độc
            // Nếu khoảng cách >= duration, tính đủ một lần duration
            totalPoisonedTime += Math.min(interval, duration);
        }
        
        // Đòn tấn công cuối cùng luôn có tác dụng đủ duration giây
        return totalPoisonedTime + duration;
    }
}
```
*(Ghi chú: Đây là một bài toán xử lý khoảng thời gian (intervals) khá trực quan).*
