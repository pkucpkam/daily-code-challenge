# Valid Anagram

## 1. Mô tả bài toán
Cho hai chuỗi `s` và `t`. Hãy trả về `true` nếu `t` là một **anagram** của `s`, ngược lại trả về `false`.
Một **anagram** là một từ được tạo ra bằng cách hoán đổi vị trí các chữ cái của một từ khác, sử dụng tất cả các chữ cái ban đầu đúng một lần.
Ví dụ: `anagram` và `nagaram` là anagram của nhau.

## 2. Ý tưởng cốt lõi
- Nếu `t` là anagram của `s`, thì tần suất xuất hiện của mỗi chữ cái (từ 'a' đến 'z') trong cả hai chuỗi phải hoàn toàn giống hệt nhau.
- Ta có thể sử dụng một mảng kích thước 26 (tương ứng 26 chữ cái tiếng Anh) để đếm số lần xuất hiện của từng ký tự.

## 3. Giải thích thuật toán
1. Đầu tiên, kiểm tra độ dài. Nếu `s.length() != t.length()` thì chắc chắn không phải là anagram -> Trả về `false`.
2. Khởi tạo mảng `count[26]`.
3. Duyệt qua chuỗi `s`, tăng giá trị đếm tại vị trí `c - 'a'`: `count[c - 'a']++`.
4. Duyệt qua chuỗi `t`, giảm giá trị đếm tại vị trí tương ứng: `count[c - 'a']--`.
5. Cuối cùng, duyệt qua mảng `count`. Nếu có bất kỳ phần tử nào khác 0, nghĩa là số lượng chữ cái không khớp nhau -> Trả về `false`.
6. Trả về `true`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Với `n` là độ dài của chuỗi. Ta duyệt qua mỗi chuỗi đúng một lần.
- **Không gian (Space Complexity)**: \(O(1)\) - Ta chỉ sử dụng một mảng có kích thước cố định là 26, không phụ thuộc vào độ dài của chuỗi đầu vào.

## 5. Code (Java)
```java
class Solution {
    public boolean isAnagram(String s, String t) {
        // Điều kiện cần: Độ dài bằng nhau
        if (s.length() != t.length()) {
            return false;
        }

        // Sử dụng mảng đếm tần suất cho 26 chữ cái tiếng Anh
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        for (char c : t.toCharArray()) {
            count[c - 'a']--;
        }
        
        // Kiểm tra xem tất cả các ví trí có về 0 hay không
        for (int c : count) {
            if (c != 0) {
                return false;
            }
        }
        return true;
    }
}
```
*(Lưu ý: Nếu input chứa các ký tự Unicode, ta nên sử dụng HashMap thay vì mảng cố định).*
