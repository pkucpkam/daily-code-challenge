# Fizz Buzz

## 1. Mô tả bài toán
Cho một số nguyên `n`. Hãy trả về một danh sách các chuỗi từ 1 đến `n` (đánh chỉ số từ 1) theo quy tắc:
- Trả về `"FizzBuzz"` nếu số đó chia hết cho cả 3 và 5.
- Trả về `"Fizz"` nếu số đó chia hết cho 3.
- Trả về `"Buzz"` nếu số đó chia hết cho 5.
- Trả về chính số đó (dưới dạng chuỗi) nếu không thuộc các trường hợp trên.

## 2. Ý tưởng cốt lõi
- Đây là một bài toán lập trình cơ bản thường dùng để kiểm tra tư duy logic và cách xử lý cấu trúc điều kiện.
- Quy tắc quan trọng: Cần kiểm tra điều kiện chia hết cho cả 3 và 5 **trước tiên**, vì nếu chia hết cho 15 thì chắc chắn nó cũng chia hết cho 3 và 5 đơn lẻ.

## 3. Giải thích thuật toán
1. Tạo một danh sách rỗng `result` để chứa các chuỗi.
2. Chạy vòng lặp `i` từ 1 đến `n`.
3. Kiểm tra các điều kiện theo thứ tự:
   - Nếu `i % 15 == 0` (tương đương `i % 3 == 0 && i % 5 == 0`): Thêm `"FizzBuzz"`.
   - Nếu `i % 3 == 0`: Thêm `"Fizz"`.
   - Nếu `i % 5 == 0`: Thêm `"Buzz"`.
   - Ngược lại: Thêm giá trị chuỗi của `i` (`String.valueOf(i)`).
4. Trả về danh sách `result`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Duyệt qua từng số từ 1 đến `n`.
- **Không gian (Space Complexity)**: \(O(1)\) - Không tính không gian lưu trữ danh sách kết quả trả về.

## 5. Code (Java)
```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> fizzBuzz(int n) {
        List<String> result = new ArrayList<>();
        
        for (int i = 1; i <= n; i++) {
            // Kiểm tra đồng thời cả hai điều kiện trước
            if (i % 3 == 0 && i % 5 == 0) {
                result.add("FizzBuzz");
            } else if (i % 3 == 0) {
                result.add("Fizz");
            } else if (i % 5 == 0) {
                result.add("Buzz");
            } else {
                // Chuyển số i sang dạng chuỗi
                result.add(Integer.toString(i));
            }
        }
        
        return result;
    }
}
```
*(Mẹo: Trong các ứng dụng thực tế với nhiều điều kiện chia hết, bạn có thể sử dụng phép cộng chuỗi để code sạch hơn).*
