# Implement Queue using Stacks

## 1. Mô tả bài toán
Hãy triển khai một **Hàng đợi (Queue)** (vào trước ra trước - FIFO) bằng cách sử dụng hai **Ngăn xếp (Stack)**.
Queue cần hỗ trợ các hàm: `push(x)`, `pop()`, `peek()`, và `empty()`.
Lưu ý: Chỉ được sử dụng các thao tác chuẩn của Stack (`push` vào đỉnh, `pop`/`peek` từ đỉnh).

## 2. Ý tưởng cốt lõi
- Stack là LIFO (Vào sau ra trước), trong khi Queue là FIFO (Vào trước ra trước).
- Để đảo ngược thứ tự các phần tử từ LIFO sang FIFO, ta sử dụng hai Stack:
    - `back`: Stack chuyên dùng để nhận phần tử khi `push`.
    - `front`: Stack chuyên dùng để lấy phần tử ra khi `pop` hoặc `peek`.
- Khi cần lấy phần tử ra mà `front` đang trống, ta chuyển toàn bộ phần tử từ `back` sang `front`. Khi đó, các phần tử sẽ bị đảo ngược thứ tự và trở thành đúng thứ tự của Queue.

## 3. Giải thích thuật toán
1. **Thao tác `push(x)`**: Luôn đẩy vào stack `back`.
2. **Thao tác `pop()`**:
   - Nếu stack `front` đang trống, ta dùng vòng lặp `pop` từ `back` và `push` vào `front` cho đến khi `back` rỗng.
   - Trả về `front.pop()`.
3. **Thao tác `peek()`**:
   - Tương tự `pop`, nếu `front` trống thì chuyển dữ liệu từ `back` sang.
   - Trả về `front.peek()`.
4. **Thao tác `empty()`**: Queue trống khi cả hai stack `front` và `back` đều trống.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: 
    - `push`: \(O(1)\).
    - `pop`, `peek`: \(O(1)\) (Amortized - Chi phí trung bình). Mặc dù có những lúc phải chuyển phần tử (\(O(n)\)), nhưng mỗi phần tử chỉ bị chuyển tối đa một lần từ `back` sang `front`.
- **Không gian (Space Complexity)**: \(O(n)\).

## 5. Code (Java)
```java
import java.util.LinkedList;

class MyQueue {
    // Sử dụng LinkedList để mô phỏng Stack
    private LinkedList<Integer> front; 
    private LinkedList<Integer> back;

    public MyQueue() {
        front = new LinkedList<>();
        back = new LinkedList<>();
    }
    
    public void push(int x) {
        back.push(x);
    }
    
    public int pop() {
        prepareFront();
        return front.pop();
    }
    
    public int peek() {
        prepareFront();
        return front.peek();
    }
    
    private void prepareFront() {
        // Nếu front trống, chuyển toàn bộ lứa phần tử cũ nhất từ back sang front
        if (front.isEmpty()) {
            while (!back.isEmpty()) {
                front.push(back.pop());
            }
        }
    }
    
    public boolean empty() {
        return front.isEmpty() && back.isEmpty();
    }
}
```
*(Lưu ý: Giải pháp này rất hiệu quả vì việc chuyển đổi stack chỉ xảy ra khi thực sự cần thiết).*
