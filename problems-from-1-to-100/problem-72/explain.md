# Reverse Vowels of a String

## 1. Mô tả bài toán
Cho một chuỗi `s`. Hãy đảo ngược **chỉ các nguyên âm** trong chuỗi đó và trả về kết quả.
Các nguyên âm bao gồm: 'a', 'e', 'i', 'o', 'u' (chấp nhận cả chữ hoa và chữ thường). Các ký tự khác (phụ âm, số, ký hiệu) phải giữ nguyên vị trí.

## 2. Ý tưởng cốt lõi
- Tương tự bài toán đảo ngược chuỗi thông thường, nhưng ta chỉ thực hiện hoán đổi khi cả hai con trỏ đều đang trỏ vào một nguyên âm.
- Sử dụng kỹ thuật **Hai con trỏ**:
    1. Con trỏ `left` tìm nguyên âm từ đầu chuỗi đi tới.
    2. Con trỏ `right` tìm nguyên âm từ cuối chuỗi đi lùi lại.
- Khi cả hai tìm thấy nguyên âm, ta hoán đổi chúng và tiếp tục di chuyển.

## 3. Giải thích thuật toán
1. Chuyển chuỗi thành mảng ký tự để có thể thay đổi: `char[] chars = s.toCharArray()`.
2. Khởi tạo `left = 0`, `right = chars.length - 1`.
3. Tạo một chuỗi chứa tất cả các nguyên âm `"aeiouAEIOU"` để kiểm tra nhanh.
4. Trong khi `left < right`:
   - Di chuyển `left` sang phải cho đến khi gặp một nguyên âm (`vowels.indexOf(chars[left]) != -1`).
   - Di chuyển `right` sang trái cho đến khi gặp một nguyên âm (`vowels.indexOf(chars[right]) != -1`).
   - Nếu `left < right`, thực hiện hoán đổi `chars[left]` và `chars[right]`. Sau đó tăng `left`, giảm `right`.
5. Chuyển mảng ký tự trở lại thành chuỗi và trả về.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Mỗi ký tự trong chuỗi được duyệt tối đa một lần bởi các con trỏ.
- **Không gian (Space Complexity)**: \(O(n)\) - Cần $O(n)$ bộ nhớ để lưu trữ mảng ký tự (do chuỗi trong Java không thể thay đổi - immutable).

## 5. Code (Java)
```java
class Solution {
    public String reverseVowels(String s) {
        char[] chars = s.toCharArray();
        int left = 0, right = chars.length - 1;
        String vowels = "aeiouAEIOU"; // Danh sách nguyên âm

        while (left < right) {
            // Tìm nguyên âm bên trái
            while (left < right && vowels.indexOf(chars[left]) == -1) {
                left++;
            }
            // Tìm nguyên âm bên phải
            while (left < right && vowels.indexOf(chars[right]) == -1) {
                right--;
            }
            
            // Hoán đổi hai nguyên âm tìm được
            if (left < right) {
                char temp = chars[left];
                chars[left] = chars[right];
                chars[right] = temp;
                left++;
                right--;
            }
        }

        return new String(chars);
    }
}
```
