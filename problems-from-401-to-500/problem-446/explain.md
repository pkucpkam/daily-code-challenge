# 📚 Giải thích Chi tiết Bài 622 - Design Circular Queue

**Mục tiêu:** Thiết kế và cài đặt cấu trúc dữ liệu **Hàng đợi vòng (Circular Queue / Ring Buffer)** có dung lượng cố định $k$ theo nguyên lý **FIFO (First In First Out)**. Điểm đặc trưng của Circular Queue là phần tử cuối cùng được kết nối vòng lại với vị trí đầu tiên để tạo thành một đường tròn, cho phép tận dụng triệt để các ô trống ở đầu mảng sau khi phần tử được lấy ra (`deQueue`).

---

## 🎯 Phân tích bài toán

### 1. Hạn chế của Hàng đợi tuyến tính trên Mảng (Linear Queue on Array)
Trong một hàng đợi thông thường được cài đặt bằng mảng tuyến tính:
- Khi thêm phần tử (`enQueue`), con trỏ đuôi `tail` tiến về phía trước.
- Khi lấy phần tử (`deQueue`), con trỏ đầu `head` dịch sang phải.
- **Hệ quả (Hiện tượng tràn giả - False Overflow):** Sau một số thao tác `enQueue` và `deQueue`, `tail` sẽ chạm đến cuối mảng (`tail == capacity - 1`). Lúc này hàng đợi báo đầy và không thể thêm phần tử mới, mặc dù các ô nhớ ở phía trước `head` đã hoàn toàn trống rỗng!
- Nếu dời toàn bộ phần tử sang trái để giải phóng không gian phía sau, thao tác `deQueue` sẽ mất chi phí thời gian $\mathcal{O}(N)$, đánh mất ưu điểm cốt lõi của cấu trúc Queue.

---

### 2. Nguyên lý Hàng đợi vòng (Circular Queue / Ring Buffer)
Để khắc phục nhược điểm trên, ta kết nối vị trí cuối mảng với vị trí đầu mảng bằng phép chia lấy dư (**Modulo Operator `% capacity`**):
- Khi con trỏ tiến đến cuối mảng và cần tăng thêm 1 bước:
  $$\text{nextIndex} = (\text{currentIndex} + 1) \pmod{\text{capacity}}$$
- Nhờ đó, nếu các vị trí đầu mảng đã được giải phóng bởi `deQueue()`, phần tử mới hoàn toàn có thể được ghi đè vào các ô này một cách an toàn và tự nhiên.

```text
               [0]  <-- head (nếu đã deQueue các phần tử cũ)
             /     \
          [3]       [1]  <-- tail mới vòng lại ô 0 hoặc 1
             \     /
               [2]
```

---

### 3. Vấn đề cốt lõi: Phân biệt `isEmpty` và `isFull`
Trong một circular queue cổ điển chỉ dùng hai con trỏ `head` và `tail`:
- Khi hàng đợi **rỗng**: `head == tail`.
- Khi hàng đợi **đầy**: `head == tail` (vì con trỏ `tail` sau khi đi một vòng tròn sẽ gặp lại `head`).

Để giải quyết sự nhập nhằng này, có hai hướng tiếp cận:
1. **Dành riêng một ô trống (Dummy Slot / Size $k + 1$):** Cấp phát mảng kích thước $k + 1$. Hàng đợi đầy khi `(tail + 1) % (k + 1) == head`. Cách này gây lãng phí 1 ô nhớ và công thức modulo phức tạp hơn.
2. **Sử dụng biến đếm `count` (Khuyên dùng - Best Practice):** Quản lý trực tiếp số lượng phần tử hiện có trong queue bằng biến `count`:
   - `isEmpty()` $\iff$ `count == 0`
   - `isFull()` $\iff$ `count == capacity`
   - Cách tiếp cận này giúp mã nguồn trong sáng, không lãng phí ô nhớ và cực kỳ an toàn.

---

## 💡 Thiết kế giải pháp tối ưu: Mảng cố định (Fixed-size Array) + Biến đếm (Count)

### Các thuộc tính cần quản lý:
1. `data`: Mảng số nguyên một chiều có kích thước đúng bằng $k$.
2. `capacity`: Sức chứa tối đa của hàng đợi ($k$).
3. `head`: Chỉ số index của phần tử ở đầu hàng đợi (phần tử sẽ được lấy ra đầu tiên).
4. `count`: Số lượng phần tử hiện tại đang nằm trong hàng đợi.

### Các công thức tính toán chỉ số:
- **Vị trí chèn phần tử mới (`enQueue`):**
  $$\text{tail} = (\text{head} + \text{count}) \pmod{\text{capacity}}$$
- **Vị trí phần tử cuối cùng hiện tại (`Rear`):**
  $$\text{rearIndex} = (\text{head} + \text{count} - 1) \pmod{\text{capacity}}$$
- **Dịch chuyển đầu hàng đợi khi xóa (`deQueue`):**
  $$\text{head} = (\text{head} + 1) \pmod{\text{capacity}}$$

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Mảng cố định + Con trỏ Head & Count

### 🌟 Ưu điểm vượt trội
- **Thời gian $\mathcal{O}(1)$ cho mọi thao tác:** Tất cả phương thức (`enQueue`, `deQueue`, `Front`, `Rear`, `isEmpty`, `isFull`) đều chỉ thực hiện các phép toán gán và modulo số học trên mảng, không có vòng lặp.
- **Tối ưu bộ nhớ $\mathcal{O}(k)$ & Thân thiện CPU Cache:** Mảng nằm liên tục trong bộ nhớ (contiguous memory), giúp CPU cache prefetch cực tốt, nhanh hơn rất nhiều so với Linked List.
- **Không sinh rác (No Garbage Collection Overhead):** Mảng được cấp phát một lần duy nhất tại hàm khởi tạo, không phát sinh chi phí phân bổ đối tượng (Node) hay thu gom rác trong suốt quá trình chạy.

---

### 💻 Mã nguồn Java (Best Solution)

Đoạn mã được cài đặt hoàn chỉnh trong [`Solution.java`](file:///f:/daily-code-challenge/problems-from-401-to-500/problem-446/Solution.java):

```java
class MyCircularQueue {
    /**
     * Best Solution: Cài đặt Hàng đợi vòng (Circular Queue / Ring Buffer) bằng Mảng cố định (Fixed-size Array)
     * kết hợp con trỏ head và biến đếm kích thước count.
     * 
     * Time Complexity: O(1) cho tất cả các thao tác (enQueue, deQueue, Front, Rear, isEmpty, isFull).
     * Space Complexity: O(k) với mảng dữ liệu có dung lượng k cố định, tối ưu bộ nhớ và tận dụng CPU Cache.
     */
    private final int[] data;
    private final int capacity;
    private int head;
    private int count;

    public MyCircularQueue(int k) {
        this.capacity = k;
        this.data = new int[k];
        this.head = 0;
        this.count = 0;
    }
    
    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        int tail = (head + count) % capacity;
        data[tail] = value;
        count++;
        return true;
    }
    
    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        head = (head + 1) % capacity;
        count--;
        return true;
    }
    
    public int Front() {
        if (isEmpty()) {
            return -1;
        }
        return data[head];
    }
    
    public int Rear() {
        if (isEmpty()) {
            return -1;
        }
        int tail = (head + count - 1) % capacity;
        return data[tail];
    }
    
    public boolean isEmpty() {
        return count == 0;
    }
    
    public boolean isFull() {
        return count == capacity;
    }
}
```

---

### 🔍 Giải thích chi tiết từng phương thức

1. **`MyCircularQueue(int k)`:**
   - Khởi tạo mảng `data` với kích thước $k$.
   - Gán `capacity = k`, `head = 0`, `count = 0`.
2. **`boolean enQueue(int value)`:**
   - Kiểm tra `isFull()`. Nếu đầy $\implies$ trả về `false`.
   - Tính vị trí thêm mới: `tail = (head + count) % capacity`.
   - Gán `data[tail] = value`, tăng `count++` và trả về `true`.
3. **`boolean deQueue()`:**
   - Kiểm tra `isEmpty()`. Nếu rỗng $\implies$ trả về `false`.
   - Dịch chuyển đầu hàng đợi sang ô tiếp theo: `head = (head + 1) % capacity`.
   - Giảm `count--` và trả về `true`. (Không cần xóa giá trị cũ trong mảng vì nó sẽ được ghi đè ở lần `enQueue` tiếp theo).
4. **`int Front()`:**
   - Nếu `isEmpty()` $\implies$ trả về `-1`.
   - Ngược lại, trả về giá trị tại `data[head]`.
5. **`int Rear()`:**
   - Nếu `isEmpty()` $\implies$ trả về `-1`.
   - Vị trí của phần tử cuối cùng được tính bằng `(head + count - 1) % capacity`.
   - Do `count >= 1` và `head >= 0`, giá trị bên trong ngoặc luôn $\ge 0$, đảm bảo chỉ số hợp lệ. Trả về `data[tail]`.
6. **`boolean isEmpty()`:**
   - Trả về `count == 0`.
7. **`boolean isFull()`:**
   - Trả về `count == capacity`.

---

## 🔬 Trace ví dụ từng bước (Step-by-step Dry Run)

Thực hiện lần lượt các thao tác trong **Example 1**:
Khởi tạo: `MyCircularQueue myCircularQueue = new MyCircularQueue(3)` $\implies$ `capacity = 3`, `data = [0, 0, 0]`, `head = 0`, `count = 0`.

| Bước | Lệnh gọi | Trạng thái mảng `data` | `head` | `count` | Chỉ số `tail` tính được | Kết quả | Giải thích |
| :---: | :--- | :---: | :---: | :---: | :---: | :---: | :--- |
| **1** | `enQueue(1)` | `[1, 0, 0]` | `0` | `1` | `(0 + 0) % 3 = 0` | `true` | Thêm `1` vào vị trí `0`. |
| **2** | `enQueue(2)` | `[1, 2, 0]` | `0` | `2` | `(0 + 1) % 3 = 1` | `true` | Thêm `2` vào vị trí `1`. |
| **3** | `enQueue(3)` | `[1, 2, 3]` | `0` | `3` | `(0 + 2) % 3 = 2` | `true` | Thêm `3` vào vị trí `2`. Hàng đợi đầy. |
| **4** | `enQueue(4)` | `[1, 2, 3]` | `0` | `3` | - | `false` | `isFull() == true` do `count == capacity == 3`. Từ chối thêm. |
| **5** | `Rear()` | `[1, 2, 3]` | `0` | `3` | `(0 + 3 - 1) % 3 = 2` | `3` | Phần tử tại `data[2]` là `3`. |
| **6** | `isFull()` | `[1, 2, 3]` | `0` | `3` | - | `true` | `count == 3 == capacity`. |
| **7** | `deQueue()` | `[1, 2, 3]` | `1` | `2` | - | `true` | Lấy phần tử ở `head = 0` ra. `head` dịch sang `1`, `count` giảm còn `2`. |
| **8** | `enQueue(4)` | `[4, 2, 3]` | `1` | `3` | `(1 + 2) % 3 = 0` | `true` | **Vòng lại ô đầu!** Thêm `4` vào vị trí `0`. |
| **9** | `Rear()` | `[4, 2, 3]` | `1` | `3` | `(1 + 3 - 1) % 3 = 0` | `4` | Phần tử tại `data[0]` là `4`. |

---

## 🔄 GIẢI PHÁP THAY THẾ: Danh sách liên kết đôi (Doubly Linked List)

Ngoài việc sử dụng mảng cố định, ta có thể cài đặt bằng **Danh sách liên kết đôi (Doubly Linked List)** kết hợp 2 node giả (`head` và `tail` sentinel nodes).

### Mã nguồn tham khảo (Doubly Linked List Approach)

```java
class MyCircularQueueLinkedList {
    private static class Node {
        int val;
        Node prev;
        Node next;
        Node(int val) {
            this.val = val;
        }
    }

    private final int capacity;
    private int count;
    private final Node head;
    private final Node tail;

    public MyCircularQueueLinkedList(int k) {
        this.capacity = k;
        this.count = 0;
        this.head = new Node(-1);
        this.tail = new Node(-1);
        head.next = tail;
        tail.prev = head;
    }
    
    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        Node newNode = new Node(value);
        // Chèn vào ngay trước tail sentinel
        newNode.prev = tail.prev;
        newNode.next = tail;
        tail.prev.next = newNode;
        tail.prev = newNode;
        count++;
        return true;
    }
    
    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        // Xóa node ngay sau head sentinel
        Node toRemove = head.next;
        head.next = toRemove.next;
        toRemove.next.prev = head;
        count--;
        return true;
    }
    
    public int Front() {
        return isEmpty() ? -1 : head.next.val;
    }
    
    public int Rear() {
        return isEmpty() ? -1 : tail.prev.val;
    }
    
    public boolean isEmpty() {
        return count == 0;
    }
    
    public boolean isFull() {
        return count == capacity;
    }
}
```

---

## 📊 So sánh các phương pháp

| Tiêu chí | ⭐ Mảng cố định + Count (Best Solution) | 🔄 Mảng kích thước $k + 1$ (2 con trỏ) | 🔗 Danh sách liên kết đôi (Doubly Linked List) |
| :--- | :--- | :--- | :--- |
| **Độ phức tạp Thời gian** | $\mathcal{O}(1)$ cho mọi thao tác | $\mathcal{O}(1)$ cho mọi thao tác | $\mathcal{O}(1)$ cho mọi thao tác |
| **Độ phức tạp Không gian** | $\mathcal{O}(k)$ (chính xác $k$ ô nhớ) | $\mathcal{O}(k + 1)$ (dư 1 ô nhớ) | $\mathcal{O}(k)$ (tốn thêm bộ nhớ cho con trỏ `prev`, `next`) |
| **Khả năng Cache (Locality)** | **Cực tốt** (Bộ nhớ liên tục) | **Cực tốt** (Bộ nhớ liên tục) | Kém (Các node rải rác trong Heap) |
| **Tạo rác bộ nhớ (GC Pressure)**| **Không có** (cấp phát 1 lần ban đầu) | **Không có** | Có (mỗi `enQueue` tạo 1 `Node`, `deQueue` loại bỏ `Node`) |
| **Độ phức tạp cài đặt** | Cực kỳ ngắn gọn, dễ hiểu | Cần nhớ quy tắc tính ô đầy với $k + 1$ | Dài hơn, cần quản lý con trỏ cẩn thận |
| **Đánh giá** | **Khuyên dùng tối đa (Best Solution)** | Giải pháp truyền thống trong SGK | Tốt khi cần kích thước co giãn linh hoạt |

---

## 💡 Tổng kết & Bài học kinh nghiệm

1. **Ứng dụng thực tế của Circular Buffer:** Ring Buffer là cấu trúc dữ liệu nền tảng trong hệ điều hành và lập trình hệ thống:
   - Bộ đệm bàn phím (Keyboard buffer) và bộ đệm cổng nối tiếp (Serial port buffer).
   - Truyền phát âm thanh/video trực tuyến (Audio/Video streaming buffers).
   - Mô hình Producer-Consumer đa luồng (Disruptor pattern, Lock-free Ring Buffer).
   - Giao tiếp I/O bất đồng bộ hiệu năng cao (Linux `io_uring` submission/completion queues).
2. **Kỹ thuật Modulo Wrap-around:** Phép toán `% capacity` là kỹ thuật kinh điển để biến một chuỗi chỉ số tuyến tính vô hạn thành một vòng lặp tuần hoàn có giới hạn.
3. **Quản lý trạng thái bằng biến đếm:** Khi xây dựng các cấu trúc dữ liệu vòng, việc bổ sung một biến đếm kích thước (`count` hoặc `size`) thường giúp loại bỏ hoàn toàn các trường hợp biên mơ hồ, giúp mã nguồn sáng rõ và dễ bảo trì.
