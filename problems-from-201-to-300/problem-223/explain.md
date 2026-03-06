# Evaluate Reverse Polish Notation

## Yêu cầu bài toán

- Cho một mảng các chuỗi `tokens` biểu diễn một biểu thức toán học theo **Ký pháp Ba Lan ngược (Reverse Polish Notation - RPN)**.
- Nhiệm vụ: Tính toán giá trị của biểu thức và trả về kết quả dưới dạng số nguyên.
- Các toán tử bao gồm: `+`, `-`, `*`, `/`.
- Phép chia hai số nguyên sẽ lấy phần nguyên (làm tròn về 0).
- Biểu thức luôn đảm bảo hợp lệ và không có phép chia cho 0.

## Ý tưởng cốt lõi

Ký pháp RPN rất thuận tiện cho máy tính xử lý vì các toán tử luôn đứng sau các toán hạng tương ứng, giúp loại bỏ sự cần thiết của dấu ngoặc và thứ tự ưu tiên.
Cấu trúc dữ liệu phù hợp nhất để giải bài toán này là **Ngăn xếp (Stack)**:
- Khi gặp một số (toán hạng), chúng ta đưa nó vào ngăn xếp.
- Khi gặp một toán tử, chúng ta lấy ra hai số nằm trên cùng của ngăn xếp, thực hiện phép tính, sau đó đưa kết quả trở lại ngăn xếp.

## Thuật toán

1. Khởi tạo một ngăn xếp (sử dụng `ArrayDeque` trong Java cho hiệu năng tốt hơn).
2. Duyệt qua từng phần tử trong mảng `tokens`:
   - Nếu phần tử là một toán tử (`+`, `-`, `*`, hoặc `/`):
     - Lấy ra hai số trên cùng của ngăn xếp. Lưu ý: số lấy ra đầu tiên là toán hạng thứ hai, số lấy ra sau là toán hạng thứ nhất.
     - Thực hiện phép toán tương ứng.
     - Đưa kết quả trở lại ngăn xếp.
   - Nếu phần tử là một con số:
     - Chuyển đổi chuỗi thành số nguyên và đưa vào ngăn xếp.
3. Sau khi kết thúc vòng lặp, phần tử duy nhất còn lại trong ngăn xếp chính là kết quả của biểu thức.

## Độ phức tạp
- **Thời gian**: $O(N)$ - Duyệt qua mảng tokens một lần. Mỗi thao tác của ngăn xếp đều có độ phức tạp $O(1)$.
- **Không gian**: $O(N)$ - Trong trường hợp tệ nhất, tất cả các con số đều nằm trong ngăn xếp.

## Code (Java)

```java
import java.util.*;

class Solution {
    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();

        for (String s : tokens) {
            if (s.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            } else if (s.equals("-")) {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(a - b);
            } else if (s.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            } else if (s.equals("/")) {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(a / b);
            } else {
                stack.push(Integer.parseInt(s));
            }
        }
        return stack.pop();
    }
}
```
