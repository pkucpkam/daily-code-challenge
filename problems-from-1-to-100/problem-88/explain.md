# Number of Segments in a String

## 1. Mô tả bài toán
Cho một chuỗi `s`. Hãy đếm số lượng các **phân đoạn (segments)** trong chuỗi đó.
Một phân đoạn được định nghĩa là một chuỗi liên tục các ký tự không phải là khoảng trắng.
Ví dụ: `"Hello, my name is John"` có 5 phân đoạn.

## 2. Ý tưởng cốt lõi
- Cách đơn giản nhất là duyệt qua chuỗi và nhận diện điểm bắt đầu của mỗi phân đoạn.
- Một ký tự tại vị trí `i` là điểm bắt đầu của một phân đoạn nếu:
    1. Bản thân ký tự đó không phải là khoảng trắng.
    2. Nó là ký tự đầu tiên của chuỗi (`i == 0`) HOẶC ký tự ngay trước đó (`i - 1`) là khoảng trắng.

## 3. Giải thích thuật toán
1. Khởi tạo biến `count = 0`.
2. Chạy vòng lặp duyệt qua chuỗi `s` từ đầu đến cuối.
3. Với mỗi vị trí `i`, kiểm tra điều kiện:
   - `s.charAt(i) != ' '` (ký tự hiện tại là một phần của phân đoạn).
   - `i == 0 || s.charAt(i - 1) == ' '` (phía trước nó là khoảng trắng hoặc nó là bắt đầu chuỗi).
4. Nếu cả hai điều kiện thỏa mãn, ta tìm thấy một phân đoạn mới, tăng `count` lên 1.
5. Sau khi kết thúc vòng lặp, trả về `count`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Duyệt qua chuỗi đúng một lần duy nhất.
- **Không gian (Space Complexity)**: \(O(1)\) - Chỉ sử dụng một biến đếm duy nhất.

## 5. Code (Java)
```java
class Solution {
    public int countSegments(String s) {
        int count = 0;
        
        for (int i = 0; i < s.length(); i++) {
            // Một phân đoạn bắt đầu khi ký tự hiện tại khác ' '
            // và nó là ký tự đầu tiên hoặc ký tự đứng trước nó là ' '
            if (s.charAt(i) != ' ' && (i == 0 || s.charAt(i - 1) == ' ')) {
                count++;
            }
        }
        
        return count;
    }
}
```
*(Lưu ý: Bạn có thể sử dụng hàm split("\\s+"), nhưng cần xử lý cẩn thận các trường hợp chuỗi chỉ toàn khoảng trắng hoặc rỗng để tránh kết quả sai).*
