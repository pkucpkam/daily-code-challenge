# Convert a Number to Hexadecimal

## 1. Mô tả bài toán
Cho một số nguyên 32-bit `num`. Hãy trả về chuỗi biểu diễn nó dưới dạng số **thập lục phân (Hexadecimal)**. 
- Đối với số âm, sử dụng phương pháp **Bù 2 (Two's complement)**.
- Kết quả không được chứa các số 0 vô nghĩa ở đầu (ngoại trừ chính số 0).
- Không được sử dụng các hàm thư viện có sẵn để chuyển đổi trực tiếp.

## 2. Ý tưởng cốt lõi
- Hệ thập lục phân (cơ số 16) sử dụng 16 ký tự: `0-9` và `a-f`.
- Mỗi ký tự trong hệ thập lục phân tương ứng với đúng **4 bit** trong hệ nhị phân.
- Vì số là 32-bit, nên kết quả tối đa sẽ có 8 ký tự hexadecimal.
- Ta có thể sử dụng phép toán bit:
    - `num & 15` (hoặc `num & 0xF`) để lấy giá trị của 4 bit cuối cùng.
    - `num >>> 4` (dịch phải không dấu) để dịch chuyển sang 4 bit tiếp theo.

## 3. Giải thích thuật toán
1. Nếu `num == 0`, trả về `"0"`.
2. Tạo một mảng ký tự ánh xạ `hexChars = "0123456789abcdef"`.
3. Sử dụng `StringBuilder` để xây dựng chuỗi kết quả.
4. Trong khi `num != 0` và chuỗi kết quả chưa đủ 8 ký tự (để xử lý số âm):
   - Lấy 4 bit cuối bằng `index = num & 15`.
   - Lấy ký tự tương ứng tại `hexChars[index]` và chèn vào **đầu** chuỗi kết quả.
   - Dịch chuyển `num` sang phải 4 bit bằng toán tử dịch phải không dấu `>>>`.
5. Trả về chuỗi kết quả.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(1)\) - Vì số đầu vào luôn là 32-bit, vòng lặp chạy tối đa 8 lần.
- **Không gian (Space Complexity)**: \(O(1)\) - Không sử dụng bộ nhớ phụ đáng kể.

## 5. Code (Java)
```java
class Solution {
    public String toHex(int num) {
        if (num == 0) return "0";
        
        char[] hexChars = "0123456789abcdef".toCharArray();
        StringBuilder hex = new StringBuilder();
        
        // Duyệt qua tối đa 8 nhóm bit (mỗi nhóm 4 bit)
        while (num != 0 && hex.length() < 8) {
            // Lấy giá trị của 4 bit cuối cùng (0-15)
            int val = num & 15;
            // Thêm ký tự tương ứng vào đầu chuỗi
            hex.insert(0, hexChars[val]);
            // Dịch phải không dấu 4 bit
            num >>>= 4;
        }
        
        return hex.toString();
    }
}
```
*(Ghi chú: Việc sử dụng dịch phải không dấu `>>>` rất quan trọng để xử lý số âm trong biểu diễn bù 2).*
