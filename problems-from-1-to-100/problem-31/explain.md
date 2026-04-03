# Valid Palindrome

## 1. Mô tả bài toán
Một chuỗi được gọi là **đối xứng (palindrome)** nếu sau khi chuyển tất cả chữ hoa thành chữ thường và loại bỏ tất cả các ký tự không phải chữ cái và số (non-alphanumeric), nó đọc xuôi và đọc ngược đều giống nhau.
Cho một chuỗi `s`, trả về `true` nếu nó là chuỗi đối xứng, ngược lại trả về `false`.

## 2. Ý tưởng cốt lõi
- Ta cần làm sạch dữ liệu đầu vào: Loại bỏ các ký tự đặc biệt (dấu phẩy, dấu cách, dấu hai chấm...) và đưa về cùng một định dạng chữ thường.
- Sử dụng phương pháp **hai con trỏ (Two Pointers)**: Một con trỏ bắt đầu từ đầu chuỗi (`left`), một con trỏ bắt đầu từ cuối chuỗi (`right`).
- So sánh các cặp ký tự tại hai đầu, nếu chúng khác nhau thì không phải chuỗi đối xứng.

## 3. Giải thích thuật toán
1. Sử dụng `StringBuilder` để lọc chuỗi `s`: Duyệt qua từng ký tự, nếu là chữ cái hoặc số (sử dụng `Character.isLetterOrDigit`), hãy chuyển thành chữ thường và thêm vào `StringBuilder`.
2. Chuyển `StringBuilder` thành một chuỗi sạch `str`.
3. Khởi tạo con trỏ `left = 0` và `right = str.length() - 1`.
4. Trong khi `left < right`:
   - Nếu `str.charAt(left)` khác `str.charAt(right)`, trả về `false`.
   - Tăng `left` và giảm `right`.
5. Nếu duyệt hết vòng lặp mà không gặp sự khác biệt, trả về `true`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Ta duyệt qua chuỗi `s` một lần để lọc, sau đó duyệt qua chuỗi đã lọc một lần nữa để so sánh.
- **Không gian (Space Complexity)**: \(O(n)\) - Ta tạo ra một chuỗi mới (filtered string) để lưu trữ các ký tự hợp lệ. 
  *(Lưu ý: Có thể tối ưu về \(O(1)\) không gian bằng cách so sánh trực tiếp trên chuỗi gốc và bỏ qua các ký tự không hợp lệ bằng con trỏ).*

## 5. Code (Java)
```java
class Solution {
    public boolean isPalindrome(String s) {
        StringBuilder filtered = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                filtered.append(Character.toLowerCase(c));
            }
        }
        
        String str = filtered.toString();
        int left = 0, right = str.length() - 1;
        
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        
        return true;
    }
}
```
