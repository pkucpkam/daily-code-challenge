# Merge Two Sorted Lists

## 1. Mô tả bài toán
Bạn được cung cấp 2 danh sách liên kết đơn (linked lists) `list1` và `list2` đã được sắp xếp tăng dần. 
Nhiệm vụ của bạn là phải hợp nhất hai danh sách này thành một danh sách liên kết duy nhất cũng được sắp xếp tăng dần, bằng cách ghép nối các node của cả 2 danh sách lại với nhau.
Trả về `head` của danh sách đã hợp nhất.

## 2. Ý tưởng cốt lõi
- Vì cả 2 danh sách đã được sắp xếp theo thứ tự, ta có thể dùng kỹ thuật sử dụng hai con trỏ (hoặc ở đây là duyệt trực tiếp qua 2 danh sách) để so sánh từng phần tử một.
- Tạo một node "giả" (`dummy node`) làm phần đầu cho danh sách mới, như vậy có thể tránh được việc xử lý phức tạp khi khởi tạo phần tử đầu tiên.
- Duyệt đồng thời qua 2 danh sách:
  - Ở mỗi bước, chọn node có giá trị nhỏ hơn để gắn vào `next` của pointer hiện tại của danh sách mới.
  - Tiến con trỏ của danh sách vừa có node được chọn lên một bước.
- Khi một trong 2 danh sách đã hết, chỉ cần gắn phần còn lại của danh sách kia vào đuôi danh sách mới vì phần này đã được sắp xếp sẵn.

## 3. Giải thích thuật toán
1. Tạo một `dummy Node` cùng một con trỏ `current` trỏ đến `dummy` này. `current` được dùng để duyệt và xây dựng danh sách mới.
2. Dùng vòng lặp chạy tới khi `list1` hoặc `list2` bằng `null`:
   - Nếu `list1.val < list2.val`: gán `current.next = list1` và tiếp tục tới node tiếp theo của `list1` (`list1 = list1.next`).
   - Ngược lại (`list1.val >= list2.val`): gán `current.next = list2` và tiếp tục tới node tiếp theo của `list2` (`list2 = list2.next`).
   - Cập nhật con trỏ danh sách mới `current = current.next`.
3. Sau khi vòng lặp kết thúc, có thể một trong hai danh sách vẫn còn phần tử. Ta kiểm tra:
   - Nếu `list1` khác `null`, móc tất cả node còn lại của `list1` vào `current.next` (gán `current.next = list1`).
   - Ngược lại gán `current.next = list2`.
4. Cuối cùng trả về `dummy.next` (là node đầu tiên thực sự của danh sách kết quả, bỏ qua node giả ban đầu).

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n + m)\) trong đó `n` và `m` là số lượng node của danh sách `list1` và `list2`. Ta duyệt qua mỗi node của cả hai danh sách đúng một lần.
- **Không gian (Space Complexity)**: \(O(1)\). Thuật toán chỉ cấp phát thêm một biến (dummy node) và tận dụng lại ngay những node đã có trên `list1` và `list2`. Không tốn thêm bộ nhớ theo kích thước input.

## 5. Code (Java)
```java
public class Solution {
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }
        ListNode(int val) {
            this.val = val;
        }
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode();
        ListNode current = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }

        // Gắn phần còn lại nếu 1 trong 2 dánh sách chưa hết
        if (list1 != null) {
            current.next = list1;
        } else {
            current.next = list2;
        }

        return dummy.next;
    }
}
```
