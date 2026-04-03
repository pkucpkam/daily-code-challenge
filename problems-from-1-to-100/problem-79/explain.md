# Find the Difference

## 1. Mô tả bài toán
Cho hai chuỗi `s` và `t`.
Chuỗi `t` được tạo ra bằng cách xáo trộn ngẫu nhiên chuỗi `s` và sau đó thêm một chữ cái nữa vào một vị trí ngẫu nhiên bất kỳ.
Hãy tìm và trả về ký tự vừa được thêm vào chuỗi `t`.
Ví dụ: `s = "abcd"`, `t = "abcde"` -> Kết quả: `"e"`.

## 2. Ý tưởng cốt lõi
Có 3 cách tiếp cận chính:
1. **Sắp xếp (Sorting)**: Sắp xếp cả hai chuỗi, sau đó so sánh từng vị trí. Ký tự khác biệt đầu tiên chính là ký tự được thêm vào.
2. **Bảng đếm (Frequency Array)**: Đếm số lần xuất hiện của từng ký tự trong `s` và `t`, sau đó tìm ký tự có sự chênh lệch.
3. **Thao tác Bit (XOR)**: Đây là cách tối ưu nhất. Vì mọi ký tự trong `s` đều xuất hiện lại trong `t` đúng một lần, trừ ký tự mới thêm vào. Khi thực hiện XOR tất cả các ký tự của cả hai chuỗi, các cặp ký tự giống nhau sẽ triệt tiêu nhau về 0, kết quả còn lại duy nhất chính là ký tự cần tìm.

## 3. Giải thích thuật toán
*(Dựa theo cách giải sắp xếp trong file Solution.java)*:
1. Chuyển chuỗi `s` và `t` thành mảng ký tự.
2. Sử dụng `Arrays.sort()` để sắp xếp cả hai mảng theo thứ tự bảng chữ cái.
3. Duyệt qua mảng `tArray`:
   - Nếu đã duyệt hết `sArray` nhưng vẫn còn phần tử trong `tArray`, hoặc nếu `sArray[i] != tArray[i]`:
     - Trả về phần tử `tArray[i]`.
4. (Gợi ý cách XOR): Khởi tạo `res = 0`. Duyệt qua cả hai chuỗi và `res ^= char`. Kết quả cuối là ký tự cần tìm.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(N \log N)\) do thao tác sắp xếp (với $N$ là độ dài chuỗi). (Cách dùng XOR chỉ tốn $O(N)$).
- **Không gian (Space Complexity)**: \(O(N)\) để lưu trữ mảng ký tự đã sắp xếp.

## 5. Code (Java)
```java
import java.util.Arrays;

class Solution {
    public char findTheDifference(String s, String t) {
        char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();
        
        // Sắp xếp cả hai chuỗi
        Arrays.sort(sArray);
        Arrays.sort(tArray);
        
        // So sánh từng vị trí
        for (int i = 0; i < sArray.length; i++) {
            if (sArray[i] != tArray[i]) {
                return tArray[i];
            }
        }
        
        // Ký tự thêm vào nằm ở cuối tArray
        return tArray[tArray.length - 1];
    }
}
```
*(Lưu ý: Bạn nên sử dụng cách tiếp cận XOR hoặc tính tổng mã ASCII để đạt độ phức tạp O(n)).*
