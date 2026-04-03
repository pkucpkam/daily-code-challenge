# Is Subsequence

## 1. Mô tả bài toán
Cho hai chuỗi `s` và `t`. Hãy trả về `true` nếu `s` là một **chuỗi con (subsequence)** của `t`, ngược lại trả về `false`.
Một chuỗi con được hình thành từ chuỗi gốc bằng cách xóa đi một số ký tự (hoặc không xóa ký tự nào) mà không làm thay đổi thứ tự tương đối của các ký tự còn lại.
Ví dụ: `"ace"` là chuỗi con của `"abcde"`, nhưng `"aec"` thì không phải.

## 2. Ý tưởng cốt lõi
- Để kiểm tra tính chuỗi con, ta cần duyệt qua chuỗi `t` và tìm kiếm các ký tự của `s` theo đúng thứ tự xuất hiện của chúng.
- Sử dụng kỹ thuật **Hai con trỏ**:
    - Con trỏ `j` dùng để theo dõi vị trí của ký tự hiện tại trong `s` mà ta đang tìm kiếm.
    - Con trỏ `i` dùng để duyệt qua toàn bộ chuỗi `t`.

## 3. Giải thích thuật toán
1. Khởi tạo `j = 0` (chỉ số của chuỗi `s`).
2. Nếu `s` là chuỗi rỗng, nó luôn là chuỗi con của bất kỳ chuỗi nào -> Trả về `true`.
3. Duyệt qua từng ký tự của chuỗi `t` bằng biến chạy `i`:
   - Kiểm tra `if (j < s.length() && s.charAt(j) == t.charAt(i))`:
     - Nếu ký tự tại vị trí `i` của `t` trùng với ký tự ta đang cần tìm ở `s` (vị trí `j`).
     - Tăng `j` lên 1 để tìm ký tự tiếp theo của `s`.
4. Sau khi duyệt hết chuỗi `t`, kiểm tra xem `j` có bằng độ dài của `s` hay không.
   - Nếu `j == s.length()`, nghĩa là ta đã tìm thấy toàn bộ các ký tự của `s` xuất hiện trong `t` theo đúng thứ tự.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(T)\) - Với $T$ là độ dài của chuỗi `t`. Ta chỉ duyệt qua chuỗi `t` một lần duy nhất.
- **Không gian (Space Complexity)**: \(O(1)\) - Chỉ sử dụng một biến chỉ số `j`.

## 5. Code (Java)
```java
class Solution {
    public boolean isSubsequence(String s, String t) {
        // Nếu s trống thì s luôn là chuỗi con
        if (s.length() == 0) return true;
        
        int j = 0; // Con trỏ theo dõi các ký tự trong s
        
        // Duyệt qua chuỗi t
        for (int i = 0; i < t.length(); i++) {
            // Nếu tìm thấy ký tự trùng khớp
            if (s.charAt(j) == t.charAt(i)) {
                j++;
                // Nếu đã tìm thấy toàn bộ ký tự trong s thì dừng luôn
                if (j == s.length()) return true;
            }
        }
        
        return j == s.length();
    }
}
```
