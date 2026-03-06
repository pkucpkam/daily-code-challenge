# Reorder List

## Yêu cầu bài toán

- Cho một danh sách liên kết đơn có dạng: `L0 → L1 → … → Ln-1 → Ln`.
- Hãy sắp xếp lại danh sách thành dạng: `L0 → Ln → L1 → Ln-1 → L2 → Ln-2 → …`.
- Quy tắc: Không được thay đổi giá trị trong các node, chỉ được thay đổi các liên kết (`next`).

## Ý tưởng cốt lõi

Nếu quan sát kỹ cấu trúc mong muốn, chúng ta thấy rằng nó được tạo thành bằng cách đan xen các node từ nửa đầu của danh sách với các node từ nửa sau của danh sách (nhưng theo thứ tự ngược lại).
Vì vậy, bài toán có thể chia thành 3 bước nhỏ hơn:
1. **Tìm trung điểm**: Chia danh sách thành hai nửa.
2. **Đảo ngược nửa sau**: Đảo ngược thứ tự các node ở nửa danh sách thứ hai.
3. **Trộn (Merge) hai danh sách**: Đan xen các node từ hai danh sách đã có.

## Thuật toán

### Bước 1: Tìm trung điểm
Sử dụng hai con trỏ `slow` và `fast`. `slow` đi 1 bước, `fast` đi 2 bước. Khi `fast` đi hết danh sách, `slow` sẽ dừng ở giữa.
### Bước 2: Đảo ngược nửa sau
Bắt đầu từ node sau `slow`, chúng ta thực hiện đảo ngược danh sách liên kết đơn. Sau khi đảo ngược, chúng ta ngắt kết nối giữa nửa đầu và nửa sau bằng cách đặt `slow.next = null`.
### Bước 3: Trộn xen kẽ
Sử dụng hai con trỏ để trỏ vào đầu của hai danh sách. Lần lượt nối một node từ danh sách 1 với một node từ danh sách 2 cho đến khi hết node.

## Độ phức tạp
- **Thời gian**: $O(N)$ - Các bước tìm trung điểm, đảo ngược và trộn đều mất thời gian tuyến tính.
- **Không gian**: $O(1)$ - Chỉ sử dụng một vài con trỏ biến đổi trực tiếp trên danh sách hiện có.

## Code (Java)

```java
class Solution {
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        // 1. Tìm trung điểm
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Đảo ngược nửa sau
        ListNode prev = null, curr = slow.next;
        slow.next = null; // Ngắt danh sách
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }

        // 3. Trộn hai danh sách (head và prev)
        ListNode first = head, second = prev;
        while (second != null) {
            ListNode tmp1 = first.next;
            ListNode tmp2 = second.next;

            first.next = second;
            second.next = tmp1;

            first = tmp1;
            second = tmp2;
        }
    }
}
```
