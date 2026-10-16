# 📚 Giải thích Chi tiết Bài 641 - Design Circular Deque

**Mục tiêu:** Thiết kế và cài đặt cấu trúc dữ liệu **Hàng đợi hai đầu vòng (Circular Deque / Double-ended Ring Buffer)** có dung lượng tối đa $k$. Deque cho phép thêm và xóa phần tử ở cả hai đầu (Front và Rear) với chi phí thời gian $\mathcal{O}(1)$. Điểm đặc trưng của Circular Deque là khi con trỏ chạm tới một trong hai đầu của mảng, nó sẽ tự động "vòng lại" đầu đối diện nhờ phép chia lấy dư (**Modulo**), tận dụng triệt để bộ nhớ cố định mà không gây hiện tượng tràn giả.

---

## 🎯 Phân tích bài toán

### 1. Phân biệt Queue và Deque (Double-ended Queue)
- **Queue (Hàng đợi chuẩn - FIFO):** Chỉ cho phép thêm ở một đầu (`rear`) và lấy ra ở đầu còn lại (`front`).
- **Deque (Double-ended Queue):** Cung cấp khả năng thao tác ở cả hai đầu:
  - Thêm ở đầu: `insertFront`
  - Thêm ở đuôi: `insertLast`
  - Xóa ở đầu: `deleteFront`
  - Xóa ở đuôi: `deleteLast`
  - Xem phần tử đầu: `getFront`
  - Xem phần tử đuôi: `getRear`

---

### 2. Nguyên lý Hàng đợi hai đầu vòng (Circular Deque)
Nếu sử dụng mảng tĩnh thông thường mà không có cơ chế vòng tròn:
- Việc xóa ở đầu (`deleteFront`) làm con trỏ `front` lùi về phía sau, để lại các ô trống không thể tái sử dụng.
- Việc thêm ở đầu (`insertFront`) có thể bị chặn ngay khi `front == 0`, dù phía sau còn rất nhiều chỗ trống.
- Tương tự với việc thao tác ở đuôi (`insertLast`, `deleteLast`).

**Giải pháp:** Kết nối vị trí đầu và cuối mảng với nhau tạo thành một đường tròn khép kín:
- **Dịch chuyển về bên phải (chiều tiến):**
  $$\text{nextIndex} = (\text{currentIndex} + 1) \pmod{\text{capacity}}$$
- **Dịch chuyển về bên trái (chiều lùi):**
  $$\text{prevIndex} = (\text{currentIndex} - 1 + \text{capacity}) \pmod{\text{capacity}}$$

Cộng thêm `capacity` trước khi lấy dư giúp chỉ số luôn luôn không âm trong mọi trường hợp!

```text
                 [0]  <-- head ban đầu
               /     \
   insertFront        insertLast (tiến theo chiều kim đồng hồ)
   (lùi ngược lại)     \
            [3]       [1]
               \     /
                 [2]
```

---

### 3. Vấn đề cốt lõi: Phân biệt `isEmpty` và `isFull`
Trong các thiết kế cổ điển chỉ dùng hai con trỏ `head` và `tail`:
- Cả hai trạng thái rỗng và đầy đều dẫn đến tình trạng hai con trỏ gặp nhau (`head == tail`), rất dễ gây lỗi và code phức tạp.
- **Giải pháp tối ưu nhất (Best Practice):** Sử dụng một biến đếm `count` lưu số lượng phần tử hiện tại:
  - `isEmpty()` $\iff$ `count == 0`
  - `isFull()` $\iff$ `count == capacity`
- Nhờ biến `count`, logic trở nên cực kỳ trong sáng, không lãng phí ô nhớ dự phòng ($k + 1$), không lo xung đột chỉ số.

---

## 💡 Thiết kế giải pháp tối ưu: Mảng cố định (Fixed-size Array) + Biến đếm (Count)

### Các thuộc tính cần quản lý:
1. `data`: Mảng số nguyên một chiều có kích thước đúng bằng $k$.
2. `capacity`: Dung lượng tối đa của Deque ($k$).
3. `head`: Vị trí index của phần tử đang nằm ở đầu Deque (phần tử `Front`).
4. `count`: Số lượng phần tử hiện có trong Deque ($0 \le \text{count} \le \text{capacity}$).

### Các công thức tính toán chỉ số:
- **Thêm phần tử vào đầu (`insertFront`):**
  - Dịch con trỏ `head` lùi về phía trước một bước theo vòng tròn:
    $$\text{head} = (\text{head} - 1 + \text{capacity}) \pmod{\text{capacity}}$$
  - Gán giá trị vào `data[head]`, tăng `count++`.
- **Thêm phần tử vào đuôi (`insertLast`):**
  - Vị trí trống tiếp theo ở đuôi được tính dựa trên `head` và `count`:
    $$\text{tail} = (\text{head} + \text{count}) \pmod{\text{capacity}}$$
  - Gán giá trị vào `data[tail]`, tăng `count++`.
- **Xóa phần tử ở đầu (`deleteFront`):**
  - Dịch con trỏ `head` tiến về phía trước một bước:
    $$\text{head} = (\text{head} + 1) \pmod{\text{capacity}}$$
  - Giảm `count--`.
- **Xóa phần tử ở đuôi (`deleteLast`):**
  - Chỉ cần giảm `count--`. Vị trí đuôi cũ tự động nằm ngoài phạm vi hoạt động của Deque.
- **Lấy phần tử đầu (`getFront`):**
  - Nếu rỗng trả về `-1`, ngược lại trả về `data[head]`.
- **Lấy phần tử đuôi (`getRear`):**
  - Nếu rỗng trả về `-1`.
  - Chỉ số của phần tử cuối cùng hiện tại:
    $$\text{rearIndex} = (\text{head} + \text{count} - 1) \pmod{\text{capacity}}$$
  - Trả về `data[rearIndex]`.

---

## ⭐ GIẢI PHÁP TỐI ƯU (Best Solution): Mảng cố định + Con trỏ Head & Count

### 🌟 Ưu điểm vượt trội
- **Thời gian $\mathcal{O}(1)$ cho mọi thao tác:** Mọi phương thức đều thực hiện bằng các phép gán và số học cơ bản, không có bất kỳ vòng lặp nào.
- **Tối ưu bộ nhớ $\mathcal{O}(k)$ & Thân thiện CPU Cache:** Mảng nằm liên tục trên RAM, CPU Cache prefetch dữ liệu cực nhanh.
- **Không sinh rác (Zero Garbage Collection Overhead):** Mảng nguyên thủy `int[]` được cấp phát một lần duy nhất lúc khởi tạo, không sinh đối tượng rác trong suốt thời gian chạy.

---

### 💻 Mã nguồn Java (Best Solution)

Đoạn mã được cài đặt hoàn chỉnh trong [`Solution.java`](file:///f:/daily-code-challenge/problems-from-401-to-500/problem-457/Solution.java):

```java
class MyCircularDeque {
    /**
     * Best Solution: Cài đặt Hàng đợi hai đầu vòng (Circular Deque / Double-ended Ring Buffer)
     * bằng Mảng cố định (Fixed-size Array) kết hợp con trỏ head và biến đếm kích thước count.
     * 
     * Time Complexity: O(1) cho tất cả các thao tác (insertFront, insertLast, deleteFront, deleteLast, getFront, getRear, isEmpty, isFull).
     * Space Complexity: O(k) với mảng dữ liệu có dung lượng k cố định, tối ưu bộ nhớ và tận dụng CPU Cache.
     */
    private final int[] data;
    private final int capacity;
    private int head;
    private int count;

    public MyCircularDeque(int k) {
        this.capacity = k;
        this.data = new int[k];
        this.head = 0;
        this.count = 0;
    }
    
    public boolean insertFront(int value) {
        if (isFull()) {
            return false;
        }
        head = (head - 1 + capacity) % capacity;
        data[head] = value;
        count++;
        return true;
    }
    
    public boolean insertLast(int value) {
        if (isFull()) {
            return false;
        }
        int tail = (head + count) % capacity;
        data[tail] = value;
        count++;
        return true;
    }
    
    public boolean deleteFront() {
        if (isEmpty()) {
            return false;
        }
        head = (head + 1) % capacity;
        count--;
        return true;
    }
    
    public boolean deleteLast() {
        if (isEmpty()) {
            return false;
        }
        count--;
        return true;
    }
    
    public int getFront() {
        if (isEmpty()) {
            return -1;
        }
        return data[head];
    }
    
    public int getRear() {
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

1. **`MyCircularDeque(int k)`:**
   - Cấp phát mảng `data` kích thước đúng bằng $k$.
   - Thiết lập `capacity = k`, `head = 0`, `count = 0`.
2. **`boolean insertFront(int value)`:**
   - Nếu `isFull() == true` $\implies$ từ chối và trả về `false`.
   - Lùi `head` sang trái 1 vị trí theo vòng tròn: `head = (head - 1 + capacity) % capacity`.
   - Ghi giá trị `data[head] = value`, tăng `count++` và trả về `true`.
3. **`boolean insertLast(int value)`:**
   - Nếu `isFull() == true` $\implies$ từ chối và trả về `false`.
   - Tính ô trống tiếp theo ở cuối: `tail = (head + count) % capacity`.
   - Ghi giá trị `data[tail] = value`, tăng `count++` và trả về `true`.
4. **`boolean deleteFront()`:**
   - Nếu `isEmpty() == true` $\implies$ từ chối và trả về `false`.
   - Tiến `head` sang phải 1 vị trí: `head = (head + 1) % capacity`.
   - Giảm `count--` và trả về `true`.
5. **`boolean deleteLast()`:**
   - Nếu `isEmpty() == true` $\implies$ từ chối và trả về `false`.
   - Chỉ cần giảm `count--` và trả về `true`. (Ô đuôi cũ sẽ bị coi là ngoài phạm vi và sẽ được ghi đè ở lần chèn sau).
6. **`int getFront()`:**
   - Nếu `isEmpty() == true` $\implies$ trả về `-1`.
   - Ngược lại, trả về `data[head]`.
7. **`int getRear()`:**
   - Nếu `isEmpty() == true` $\implies$ trả về `-1`.
   - Vị trí của phần tử cuối cùng là `(head + count - 1) % capacity`.
   - Do `count >= 1` và `head >= 0`, giá trị biểu thức luôn $\ge 0$. Trả về `data[tail]`.
8. **`boolean isEmpty()`:** Trả về `count == 0`.
9. **`boolean isFull()`:** Trả về `count == capacity`.

---

## 🔬 Trace ví dụ từng bước (Step-by-step Dry Run)

Khởi tạo: `MyCircularDeque myCircularDeque = new MyCircularDeque(3)` $\implies$ `capacity = 3`, `data = [0, 0, 0]`, `head = 0`, `count = 0`.

| Bước | Lệnh gọi | Trạng thái mảng `data` | `head` | `count` | Chỉ số tính được | Kết quả | Giải thích trực quan |
| :---: | :--- | :---: | :---: | :---: | :---: | :---: | :--- |
| **1** | `insertLast(1)` | `[1, 0, 0]` | `0` | `1` | `tail = (0 + 0) % 3 = 0` | `true` | Chèn `1` vào đuôi tại vị trí `0`. |
| **2** | `insertLast(2)` | `[1, 2, 0]` | `0` | `2` | `tail = (0 + 1) % 3 = 1` | `true` | Chèn `2` vào đuôi tại vị trí `1`. |
| **3** | `insertFront(3)`| `[1, 2, 3]` | `2` | `3` | `head = (0 - 1 + 3) % 3 = 2` | `true` | **Vòng lại ô cuối!** Lùi `head` về `2`, chèn `3`. Hàng đợi đầy. |
| **4** | `insertFront(4)`| `[1, 2, 3]` | `2` | `3` | - | `false` | `isFull() == true` do `count == capacity == 3`. Từ chối thêm. |
| **5** | `getRear()` | `[1, 2, 3]` | `2` | `3` | `tail = (2 + 3 - 1) % 3 = 1` | `2` | Đuôi hiện tại ở ô `1`, giá trị là `2`. |
| **6** | `isFull()` | `[1, 2, 3]` | `2` | `3` | - | `true` | `count == capacity == 3`. |
| **7** | `deleteLast()` | `[1, 2, 3]` | `2` | `2` | - | `true` | Giảm `count` còn `2`. Phần tử ở đuôi (ô `1`) bị loại bỏ. |
| **8** | `insertFront(4)`| `[1, 4, 3]` | `1` | `3` | `head = (2 - 1 + 3) % 3 = 1` | `true` | Lùi `head` từ `2` về `1`. Ghi đè `4` vào vị trí `1`. Hàng đầy trở lại. |
| **9** | `getFront()` | `[1, 4, 3]` | `1` | `3` | - | `4` | Đầu hiện tại ở ô `head = 1`, giá trị là `4`. |

---

## 🔄 GIẢI PHÁP THAY THẾ: Danh sách liên kết đôi (Doubly Linked List)

Ngoài giải pháp mảng cố định, ta có thể cài đặt Deque bằng **Danh sách liên kết đôi** kết hợp 2 node giả (`head` và `tail` sentinels) để tránh xử lý các trường hợp biên con trỏ `null`.

### Mã nguồn tham khảo (Doubly Linked List Approach)

```java
class MyCircularDequeLinkedList {
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

    public MyCircularDequeLinkedList(int k) {
        this.capacity = k;
        this.count = 0;
        this.head = new Node(-1);
        this.tail = new Node(-1);
        head.next = tail;
        tail.prev = head;
    }
    
    public boolean insertFront(int value) {
        if (isFull()) return false;
        Node node = new Node(value);
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
        count++;
        return true;
    }
    
    public boolean insertLast(int value) {
        if (isFull()) return false;
        Node node = new Node(value);
        node.prev = tail.prev;
        node.next = tail;
        tail.prev.next = node;
        tail.prev = node;
        count++;
        return true;
    }
    
    public boolean deleteFront() {
        if (isEmpty()) return false;
        Node toRemove = head.next;
        head.next = toRemove.next;
        toRemove.next.prev = head;
        count--;
        return true;
    }
    
    public boolean deleteLast() {
        if (isEmpty()) return false;
        Node toRemove = tail.prev;
        tail.prev = toRemove.prev;
        toRemove.prev.next = tail;
        count--;
        return true;
    }
    
    public int getFront() {
        return isEmpty() ? -1 : head.next.val;
    }
    
    public int getRear() {
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

| Tiêu chí | ⭐ Mảng cố định + Count (Best Solution) | 🔄 Mảng kích thước $k + 1$ (Hai con trỏ) | 🔗 Danh sách liên kết đôi (Doubly Linked List) |
| :--- | :--- | :--- | :--- |
| **Độ phức tạp Thời gian** | $\mathcal{O}(1)$ cho mọi thao tác | $\mathcal{O}(1)$ cho mọi thao tác | $\mathcal{O}(1)$ cho mọi thao tác |
| **Độ phức tạp Không gian** | $\mathcal{O}(k)$ (chính xác $k$ ô nhớ) | $\mathcal{O}(k + 1)$ (thừa 1 ô nhớ) | $\mathcal{O}(k)$ (tốn thêm bộ nhớ cho con trỏ `prev`, `next`) |
| **Khả năng Cache Locality** | **Cực tốt** (Bộ nhớ liên tục) | **Cực tốt** (Bộ nhớ liên tục) | Kém (Các Node nằm phân tán trên Heap) |
| **Tạo rác bộ nhớ (GC Pressure)**| **Không có** (cấp phát 1 lần ban đầu) | **Không có** | Có (mỗi lần chèn tạo 1 Node mới, xóa bỏ Node) |
| **Độ phức tạp mã nguồn** | Rất ngắn gọn, dễ bảo trì | Cần nhớ điều kiện `(tail + 1) % (k + 1) == head` | Cần cẩn thận khi nối các con trỏ đôi |
| **Đánh giá tổng quan** | **Khuyên dùng tối đa (Best Solution)** | Giải pháp giáo khoa truyền thống | Hữu ích khi kích thước không cố định |

---

## 💡 Tổng kết & Ứng dụng thực tế

1. **Thuật toán Cửa sổ trượt (Sliding Window Maximum):** Monotonic Deque là cấu trúc dữ liệu nền tảng giúp tìm giá trị lớn nhất/nhỏ nhất trong cửa sổ trượt kích thước $k$ với độ phức tạp tuyến tính $\mathcal{O}(N)$ thay vì $\mathcal{O}(N \log k)$ bằng Heap.
2. **Hệ thống Undo/Redo có giới hạn:** Các phần mềm chỉnh sửa (Photoshop, IDEs) dùng Circular Deque dung lượng $k$ để lưu lại lịch sử hành động người dùng. Khi vượt quá $k$ thao tác, hành động cũ nhất bị loại bỏ ở đầu để nhường chỗ cho hành động mới nhất ở đuôi.
3. **Mô hình Work-Stealing trong đa luồng:** Các kiến trúc đa luồng hiện đại như Java `ForkJoinPool` hay Go Runtime scheduler sử dụng Deque: luồng sở hữu lấy và đẩy việc ở đầu (LIFO - cache friendly), trong khi các luồng rảnh rỗi khác "đánh cắp" việc từ đuôi (FIFO - tránh tranh chấp).
