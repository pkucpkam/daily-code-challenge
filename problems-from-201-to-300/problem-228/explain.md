# Min Stack

## Yêu cầu bài toán

- Thiết kế một cấu trúc dữ liệu ngăn xếp (Stack) hỗ trợ các thao tác cơ bản:
  - `push(val)`: Thêm phần tử vào đỉnh ngăn xếp.
  - `pop()`: Xóa phần tử ở đỉnh ngăn xếp.
  - `top()`: Lấy giá trị của phần tử ở đỉnh ngăn xếp.
  - `getMin()`: Lấy giá trị nhỏ nhất trong ngăn xếp hiện tại.
- Yêu cầu: Tất cả các thao tác trên đều phải thực hiện trong thời gian hằng số $O(1)$.

## Ý tưởng cốt lõi

Thách thức chính là phương thức `getMin()` phải chạy trong $O(1)$. Nếu mỗi lần gọi chúng ta mới đi tìm giá trị nhỏ nhất thì sẽ mất $O(N)$.
Để giải quyết, tại mỗi thời điểm phần tử được đưa vào ngăn xếp, chúng ta sẽ lưu kèm với nó giá trị nhỏ nhất của ngăn xếp tính đến thời điểm đó.
Có thể hiểu đơn giản: Mỗi tầng của ngăn xếp sẽ "nhớ" giá trị thấp nhất của tất cả các tầng phía dưới nó cộng với chính nó.

## Thuật toán

Sử dụng một ngăn xếp lưu trữ các mảng số nguyên, trong đó mỗi mảng có 2 phần tử: `[giá trị_hiện_tại, giá trị_nhỏ_nhất_hiện_tại]`.

1. **`push(val)`**:
   - Nếu ngăn xếp trống: Đưa cặp `[val, val]` vào.
   - Nếu ngăn xếp không trống: Lấy giá trị nhỏ nhất hiện tại ở đỉnh ngăn xếp (`stack.peek()[1]`). So sánh nó với `val` để tìm giá trị nhỏ nhất mới. Sau đó đưa cặp `[val, min_mới]` vào.
2. **`pop()`**: Thực hiện `pop` như bình thường. Giá trị nhỏ nhất cũ sẽ tự động biến mất và giá trị nhỏ nhất của trạng thái trước đó sẽ lộ ra ở đỉnh mới.
3. **`top()`**: Trả về phần tử đầu tiên của cặp mảng ở đỉnh ngăn xếp.
4. **`getMin()`**: Trả về phần tử thứ hai của cặp mảng ở đỉnh ngăn xếp.

## Độ phức tạp
- **Thời gian**: $O(1)$ cho mọi thao tác.
- **Không gian**: $O(N)$ - Chúng ta lưu trữ thêm một giá trị nhỏ nhất cho mỗi phần tử trong ngăn xếp.

## Code (Java)

```java
import java.util.Stack;

class MinStack {
    private Stack<int[]> stack;

    public MinStack() {
        stack = new Stack<>();
    }

    public void push(int val) {
        if (stack.isEmpty()) {
            stack.push(new int[]{val, val});
        } else {
            int currentMin = Math.min(val, stack.peek()[1]);
            stack.push(new int[]{val, currentMin});
        }
    }

    public void pop() {
        stack.pop();
    }

    public int top() {
        return stack.peek()[0];
    }

    public int getMin() {
        return stack.peek()[1];
    }
}
```
