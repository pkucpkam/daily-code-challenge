# Valid Parentheses

## 1. Mô tả bài toán
Cho một chuỗi `s` chỉ chứa các ký tự '(', ')', '{', '}', '[' và ']', hãy xác định xem chuỗi đầu vào có hợp lệ hay không.

Một chuỗi đầu vào được coi là hợp lệ nếu:
1. Các dấu ngoặc mở phải được đóng bởi các dấu ngoặc cùng loại.
2. Các dấu ngoặc mở phải được đóng theo đúng thứ tự.
3. Mỗi dấu ngoặc đóng phải có một dấu ngoặc mở tương ứng cùng loại.

## 2. Ý tưởng cốt lõi
- Bài toán này có thể giải quyết một cách hiệu quả bằng cấu trúc dữ liệu **Ngăn xếp (Stack)** do tính chất "Vào sau Ra trước" (LIFO) của nó.
- Khi gặp một dấu ngoặc mở, ta chưa thể xác định được nó sẽ đóng khi nào, nhưng ta biết rằng dấu ngoặc mở gần nhất cần được đóng đầu tiên.
- Do đó, ta duyệt qua chuỗi:
  - Nếu gặp dấu ngoặc mở, đẩy dấu ngoặc đóng tương ứng của nó vào ngăn xếp.
  - Nếu gặp dấu ngoặc đóng, kiểm tra xem nó có khớp với phần tử trên cùng của ngăn xếp hay không. Nếu không khớp hoặc ngăn xếp rỗng thì chuỗi không hợp lệ.

## 3. Giải thích thuật toán
1. Khởi tạo một `Stack`.
2. Duyệt qua từng ký tự `c` trong chuỗi `s`:
   - Nếu `c` là '(', đẩy ')' vào `Stack`.
   - Nếu `c` là '{', đẩy '}' vào `Stack`.
   - Nếu `c` là '[', đẩy ']' vào `Stack`.
   - Nếu `c` là dấu ngoặc đóng (không rơi vào 3 trường hợp trên):
     - Kiểm tra nếu `Stack` rỗng, nghĩa là có dấu ngoặc đóng nhưng không có dấu mở tương ứng -> Trả về `false`.
     - Rút phần tử trên cùng của `Stack` (`pop`) và so sánh với `c`. Nếu không khớp, chuỗi không hợp lệ -> Trả về `false`.
3. Sau khi kết thúc vòng lặp, kiểm tra trạng thái của `Stack`:
   - Nếu `Stack` rỗng, mọi dấu ngoặc đều đã được đóng hợp lệ -> Trả về `true`.
   - Nếu `Stack` không rỗng, còn dấu ngoặc mở chưa được đóng -> Trả về `false`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Ta chỉ duyệt qua chuỗi `s` một lần duy nhất. Kích thước chuỗi `n`. Mỗi thao tác `push` và `pop` trên Stack có độ phức tạp là \(O(1)\).
- **Không gian (Space Complexity)**: \(O(n)\) - Trong trường hợp xấu nhất (toàn dấu ngoặc mở), ta phải lưu tất cả các dấu ngoặc mở vào `Stack`, kích thước là bộ nhớ lưu trữ `n` kí tự.

## 5. Code (Java)
```java
import java.util.Stack;

public class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(')');
            } else if (c == '{') {
                stack.push('}');
            } else if (c == '[') {
                stack.push(']');
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.pop();
                if (top != c) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
```
