# LRU Cache

## Yêu cầu bài toán

- Thiết kế một cấu trúc dữ liệu cho bộ nhớ đệm (Cache) theo cơ chế **LRU - Least Recently Used**. Cơ chế này sẽ ưu tiên loại bỏ các phần tử ít được sử dụng nhất khi bộ nhớ đầy.
- Triển khai lớp `LRUCache` với các phương thức:
  - `LRUCache(int capacity)`: Khởi tạo cache với dung lượng tối đa là `capacity`.
  - `int get(int key)`: Trả về giá trị của `key` nếu tồn tại, ngược lại trả về `-1`. Mỗi khi một phần tử được truy cập, nó sẽ được coi là "mới sử dụng".
  - `void put(int key, int value)`: Cập nhật giá trị nếu `key` đã tồn tại, hoặc thêm mới nếu chưa có. Nếu số lượng phần tử vượt quá `capacity`, hãy loại bỏ phần tử ít được sử dụng nhất.
- Yêu cầu quan trọng: Cả hai thao tác `get` và `put` đều phải đạt độ phức tạp thời gian trung bình là $O(1)$.

## Ý tưởng cốt lõi

Để đạt được tốc độ $O(1)$ cho cả việc truy cập và cập nhật thứ tự, chúng ta kết hợp hai cấu trúc dữ liệu:
1. **HashMap**: Lưu trữ cặp `(key, Node)` để truy cập bất kỳ phần tử nào trong thời gian $O(1)$.
2. **Danh sách liên kết đôi (Doubly Linked List)**: Lưu trữ các phần tử theo thứ tự sử dụng.
   - Node ở đầu danh sách (gần `head`) là node mới được sử dụng nhất.
   - Node ở cuối danh sách (gần `tail`) là node ít được sử dụng nhất.

Mỗi khi một node được truy cập hoặc cập nhật, chúng ta sẽ đưa nó lên đầu danh sách liên kết đôi. Khi cần xóa phần tử (do đầy bộ nhớ), chúng ta chỉ cần xóa node ở cuối danh sách.

## Thuật toán

1. **Cấu trúc Node**: Mỗi `Node` chứa `key`, `value` và hai con trỏ `prev`, `next`.
2. **Khởi tạo**: Sử dụng hai node giả `head` và `tail` để đánh dấu điểm đầu và cuối danh sách, giúp các thao tác thêm/xóa node trở nên đơn giản hơn.
3. **Thao tác `get(key)`**:
   - Kiểm tra trong HashMap. Nếu không thấy, trả về `-1`.
   - Nếu thấy, di chuyển node đó lên đầu danh sách (ngay sau `head`) để đánh dấu là mới sử dụng, sau đó trả về giá trị.
4. **Thao tác `put(key, value)`**:
   - Nếu `key` đã tồn tại: Cập nhật giá trị mới và đưa node lên đầu danh sách.
   - Nếu `key` chưa tồn tại:
     - Nếu bộ nhớ đầy (`size == capacity`): Xóa phần tử ở cuối danh sách (trước `tail`) và xóa cả trong HashMap.
     - Tạo node mới, thêm vào đầu danh sách và cập nhật vào HashMap.

## Độ phức tạp
- **Thời gian**: $O(1)$ cho cả `get` và `put`.
- **Không gian**: $O(C)$ - Với $C$ là dung lượng (capacity) của cache.

## Code (Java)

```java
import java.util.*;

class LRUCache {
    private static class Node {
        int key, val;
        Node prev, next;
        Node(int k, int v) {
            key = k;
            val = v;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> map;
    private final Node head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        moveToHead(node);
        return node.val;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            moveToHead(node);
        } else {
            if (map.size() == capacity) {
                Node lru = tail.prev;
                removeNode(lru);
                map.remove(lru.key);
            }
            Node newNode = new Node(key, value);
            map.put(key, newNode);
            addNode(newNode);
        }
    }

    private void moveToHead(Node node) {
        removeNode(node);
        addNode(node);
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addNode(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
}
```
