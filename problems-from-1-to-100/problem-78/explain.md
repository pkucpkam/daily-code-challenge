# First Unique Character in a String

## 1. Mô tả bài toán
Cho một chuỗi `s`. Hãy tìm ký tự đầu tiên không lặp lại trong chuỗi đó và trả về chỉ số (index) của nó. Nếu không tồn tại ký tự nào như vậy, trả về `-1`.
Ví dụ:
- `s = "leetcode"` -> Ký tự 'l' tại index `0` là duy nhất đầu tiên.
- `s = "aabb"` -> Không có ký tự duy nhất, trả về `-1`.

## 2. Ý tưởng cốt lõi
- Để biết một ký tự có lặp lại hay không, ta cần biết tổng số lần nó xuất hiện trong chuỗi.
- Sử dụng mảng tần suất (Frequency Array) kích thước 26 để đếm số lần xuất hiện của mỗi chữ cái tiếng Anh viết thường.
- Sau khi có bảng đếm, ta duyệt lại chuỗi một lần nữa (từ trái sang phải) để tìm ký tự đầu tiên có số lần xuất hiện bằng 1.

## 3. Giải thích thuật toán
1. Tạo mảng đếm `charCount[26]`.
2. Duyệt qua chuỗi `s` lần thứ nhất:
   - Cập nhật số lần xuất hiện: `charCount[c - 'a']++`.
3. Duyệt qua chuỗi `s` lần thứ hai (theo thứ tự chỉ số từ 0 đến `n-1`):
   - Kiểm tra `charCount` của ký tự tại vị trí hiện tại:
     - Nếu `charCount[s.charAt(i) - 'a'] == 1`: Trả về `i` ngay lập tức.
4. Nếu đi hết vòng lặp mà không thấy ký tự nào có số lần đếm bằng 1, trả về `-1`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Duyệt qua chuỗi hai lần độc lập, mỗi lần tốn $O(n)$.
- **Không gian (Space Complexity)**: \(O(1)\) - Mảng tần suất có kích thước cố định là 26.

## 5. Code (Java)
```java
class Solution {
    public int firstUniqChar(String s) {
        // Bước 1: Đếm tần suất xuất hiện của từng ký tự
        int[] charCount = new int[26];
        for (char c : s.toCharArray()) {
            charCount[c - 'a']++;
        }
        
        // Bước 2: Tìm ký tự đầu tiên có tần suất là 1
        for (int i = 0; i < s.length(); i++) {
            if (charCount[s.charAt(i) - 'a'] == 1) {
                return i;
            }
        }
        
        return -1;
    }
}
```
*(Mẹo: Bạn có thể sử dụng mảng int[256] nếu chuỗi chứa các ký tự ASCII mở rộng).*
