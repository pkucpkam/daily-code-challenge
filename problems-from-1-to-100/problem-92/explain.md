# Repeated Substring Pattern

## 1. Mô tả bài toán
Cho một chuỗi `s`. Hãy kiểm tra xem chuỗi đó có thể được tạo thành bằng cách lặp lại một chuỗi con của chính nó hay không.
Ví dụ:
- `abab` -> `true` (lặp lại `ab` 2 lần).
- `aba` -> `false`.
- `abcabcabc` -> `true` (lặp lại `abc` 3 lần).

## 2. Ý tưởng cốt lõi
Có một mẹo toán học (String Manipulation trick) rất thú vị để giải bài này:
1. Nhân đôi chuỗi `s` thành `doubled = s + s`.
2. Loại bỏ ký tự đầu tiên và ký tự cuối cùng của chuỗi `doubled` để có chuỗi mới `modified`.
3. Nếu `s` vẫn xuất hiện bên trong `modified`, thì `s` chắc chắn được tạo thành từ các chuỗi con lặp lại.

**Tại sao?** Nếu \(s = P + P + ... + P\) (lặp lại $n$ lần), thì \(s + s = P + P + ... + P + P + P + ... + P\) (lặp lại $2n$ lần). Khi bỏ ký tự ở hai đầu, ta vẫn còn ít nhất một bản sao hoàn chỉnh của \(s\) nằm ở giữa các chuỗi con $P$.

## 3. Giải thích thuật toán
1. Lấy độ dài `n = s.length()`.
2. Tạo chuỗi `doubled = s + s`.
3. Cắt chuỗi từ chỉ số 1 đến `2n - 2`: `doubled.substring(1, 2 * n - 1)`.
4. Sử dụng phương thức `contains(s)` để kiểm tra sự tồn tại của `s` trong phần còn lại.
5. Nếu chứa, trả về `true`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Các thao tác cộng chuỗi và `contains` (thường sử dụng KMP hoặc thuật toán tìm kiếm chuỗi khác) có độ phức tạp tuyến tính.
- **Không gian (Space Complexity)**: \(O(n)\) - Tạo chuỗi mới có độ dài gấp đôi.

## 5. Code (Java)
```java
class Solution {
    public boolean repeatedSubstringPattern(String s) {
        // Mẹo: Nếu s có cấu trúc lặp, khi ghép s + s 
        // và bỏ đi 2 ký tự đầu cuối, s vẫn sẽ xuất hiện ở giữa
        String doubled = s + s;
        String modified = doubled.substring(1, doubled.length() - 1);
        
        return modified.contains(s);
    }
}
```
*(Ghi chú: Cách tiếp cận này rất ngắn gọn và thông minh, tránh việc phải duyệt thử từng độ dài của chuỗi con).*
