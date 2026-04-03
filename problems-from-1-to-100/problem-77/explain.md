# Ransom Note

## 1. Mô tả bài toán
Cho hai chuỗi `ransomNote` và `magazine`. Hãy trả về `true` nếu `ransomNote` có thể được tạo thành từ các ký tự có trong `magazine`, ngược lại trả về `false`.
Mỗi ký tự trong `magazine` chỉ có thể được sử dụng **một lần** duy nhất.
Ví dụ:
- `ransomNote = "aa", magazine = "aab"` -> `true` (có 2 chữ 'a' và 1 chữ 'b' để dùng).
- `ransomNote = "aa", magazine = "ab"` -> `false` (vì chỉ có 1 chữ 'a').

## 2. Ý tưởng cốt lõi
- Để biết `magazine` có đủ ký tự cung cấp cho `ransomNote` hay không, ta cần đếm tần suất xuất hiện của từng ký tự trong `magazine`.
- Vì dữ liệu chỉ gồm các chữ cái tiếng Anh viết thường, ta có thể sử dụng một mảng số nguyên có kích thước 26 thay vì dùng HashMap để tiết kiệm bộ nhớ và tăng tốc độ.

## 3. Giải thích thuật toán
1. Tạo một mảng `charCount` kích thước 26 để theo dõi số lượng từng chữ cái từ 'a' đến 'z'.
2. Duyệt qua chuỗi `magazine`:
   - Với mỗi ký tự `c`, tăng giá trị đếm tương ứng trong mảng: `charCount[c - 'a']++`.
3. Duyệt qua chuỗi `ransomNote`:
   - Với mỗi ký tự `c`, kiểm tra xem nó còn có sẵn trong `magazine` hay không:
     - Nếu `charCount[c - 'a'] == 0`: Trả về `false` ngay lập tức (thiếu ký tự).
     - Ngược lại: Giảm số lượng ký tự đó đi 1 (`charCount[c - 'a']--`).
4. Nếu duyệt hết chuỗi `ransomNote` mà không gặp vấn đề gì, trả về `true`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(M + R)\) - Với $M$ là độ dài của `magazine` và $R$ là độ dài của `ransomNote`.
- **Không gian (Space Complexity)**: \(O(1)\) - Ta luôn sử dụng một mảng kích thước cố định là 26, bất kể độ dài của chuỗi đầu vào.

## 5. Code (Java)
```java
class Solution {
    public boolean canConstruct(String ransomNote, String magazine) {
        // Mảng đếm tần suất các chữ cái từ 'a' đến 'z'
        int[] charCount = new int[26];
        
        // Đếm các ký tự có sẵn trong magazine
        for (char c : magazine.toCharArray()) {
            charCount[c - 'a']++;
        }
        
        // Kiểm tra xem magazine có đủ ký tự để tạo ransomNote không
        for (char c : ransomNote.toCharArray()) {
            if (charCount[c - 'a'] == 0) {
                // Không còn ký tự này để sử dụng
                return false;
            }
            charCount[c - 'a']--;
        }
        
        return true;
    }
}
```
*(Ghi chú: Bài toán này rất giống với bài toán kiểm tra Anagram nhưng chỉ yêu cầu tập hợp con).*
