# Construct the Rectangle

## 1. Mô tả bài toán
Một nhà phát triển web cần thiết kế một trang web hình chữ nhật có diện tích `area`. Bạn cần tìm chiều dài `L` và chiều rộng `W` thỏa mãn 3 điều kiện:
1. Diện tích `L * W = area`.
2. Chiều dài không nhỏ hơn chiều rộng: `L >= W`.
3. Chênh lệch giữa `L` và `W` phải là nhỏ nhất có thể.

## 2. Ý tưởng cốt lõi
- Để `L` và `W` gần nhau nhất, cả hai nên ở gần giá trị căn bậc hai của `area` nhất. 
- Chiến thuật: Ta bắt đầu thử từ giá trị `W = floor(sqrt(area))`. Vì ta muốn `L >= W`, ta sẽ tìm số nguyên `W` lớn nhất nhỏ hơn hoặc bằng căn bậc hai sao cho `area` chia hết cho `W`.
- Khi đó, hiệu ứng tự nhiên là `L = area / W` sẽ lớn hơn hoặc bằng `W` và khoảng cách giữa chúng sẽ là tối thiểu.

## 3. Giải thích thuật toán
1. Tính `w = (int) Math.sqrt(area)`.
2. Sử dụng vòng lặp `while (area % w != 0)`:
   - Giảm `w` dần cho đến khi `area` chia hết cho `w`.
3. Khi tìm được `w`, tính `l = area / w`.
4. Trả về mảng `[l, w]`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(\sqrt{area})\) - Trong trường hợp xấu nhất (số nguyên tố), vòng lặp sẽ chạy từ $\sqrt{area}$ về 1.
- **Không gian (Space Complexity)**: \(O(1)\) - Không tốn thêm bộ nhớ.

## 5. Code (Java)
```java
class Solution {
    public int[] constructRectangle(int area) {
        // Bắt đầu từ căn bậc hai của diện tích để L và W gần nhau nhất
        int width = (int) Math.sqrt(area);
        
        // Tìm số nguyên lớn nhất nhỏ hơn sqrt(area) mà area chia hết
        while (area % width != 0) {
            width--;
        }
        
        // Sau khi có W lớn nhất, tính L tương ứng
        int length = area / width;
        
        return new int[]{length, width};
    }
}
```
*(Ghi chú: Phương pháp này đảm bảo L >= W và hiệu số L-W là nhỏ nhất).*
