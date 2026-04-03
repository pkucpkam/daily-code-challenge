# Implement Stack using Queues

## 1. Mô tả bài toán
Hãy triển khai một cấu trúc dữ liệu **Ngăn xếp (Stack)** (vào sau ra trước - LIFO) bằng cách chỉ sử dụng hai **Hàng đợi (Queue)**.
Stack cần hỗ trợ các hàm: `push(x)`, `pop()`, `top()`, và `empty()`.
Lưu ý: Chỉ được sử dụng các thao tác chuẩn của Queue như: đẩy vào cuối (`offer`/`push`), lấy ra từ đầu (`poll`/`pop`), xem phần tử đầu (`peek`) và kiểm tra kích thước.

## 2. Ý tưởng cốt lõi
- Queue hoạt động theo nguyên tắc FIFO (vào trước ra trước), trong khi Stack là LIFO.
- Để biến Queue thành Stack, khó khăn nằm ở thao tác `push`. Khi thêm một phần tử mới, ta cần đảo ngược thứ tự sao cho phần tử mới nhất luôn nằm ở đầu Queue chính.
- Sử dụng hai Queue `q1` và `q2`:
    - `q1`: Queue chính chứa các phần tử theo thứ tự của Stack.
    - `q2`: Queue phụ dùng để tạm thời đảo thứ tự khi có phần tử mới.

## 3. Giải thích thuật toán
1. **Thao tác `push(x)`**:
   - Bước 1: Đưa `x` vào `q2`.
   - Bước 2: Lần lượt lấy tất cả phần tử trong `q1` chuyển sang `q2`. Lúc này `x` sẽ nằm ở đầu hàng đợi trong `q2`.
   - Bước 3: Hoán đổi tên gọi (swap) giữa `q1` và `q2`. Giờ đây `q1` chứa phần tử mới nhất ở đầu.
2. **Thao tác `pop()`**: Chỉ cần lấy phần tử ở đầu `q1` (`q1.poll()`).
3. **Thao tác `top()`**: Chỉ cần xem phần tử ở đầu `q1` (`q1.peek()`).
4. **Thao tác `empty()`**: Kiểm tra xem `q1` có trống hay không.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: 
    - `push`: \(O(n)\) vì phải chuyển toàn bộ phần tử giữa hai queue.
    - `pop`, `top`, `empty`: \(O(1)\).
- **Không gian (Space Complexity)**: \(O(n)\) để lưu trữ `n` phần tử trong các queue.

## 5. Code (Java)
```java
import java.util.LinkedList;
import java.util.Queue;

class MyStack {
    private Queue<Integer> q1; // Queue chính
    private Queue<Integer> q2; // Queue phụ

    public MyStack() {
        q1 = new LinkedList<>();
        q2 = new LinkedList<>();
    }

    public void push(int x) {
        // Đưa phần tử mới vào q2
        q2.offer(x);
        // Đưa toàn bộ q1 cũ vào sau x trong q2
        while (!q1.isEmpty()) {
            q2.offer(q1.poll());
        }
        // Tráo đổi q1 và q2 để x luôn nằm đầu q1
        Queue<Integer> temp = q1;
        q1 = q2;
        q2 = temp;
    }

    public int pop() {
        return q1.poll();
    }

    public int top() {
        return q1.peek();
    }

    public boolean empty() {
        return q1.isEmpty();
    }
}
```
*(Lưu ý: Có thể tối ưu chỉ dùng 1 Queue duy nhất bằng cách xoay vòng các phần tử cũ ra sau phần tử mới vừa add).*
