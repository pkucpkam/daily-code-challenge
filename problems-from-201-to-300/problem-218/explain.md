# Linked List Cycle II

## Yêu cầu bài toán

- Cho đầu (`head`) của một danh sách liên kết.
- Nhiệm vụ: Xác định xem danh sách có chu trình hay không. Nếu có, hãy trả về **node nơi chu trình bắt đầu**. Nếu không có chu trình, trả về `null`.
- Yêu cầu không được thay đổi cấu trúc của danh sách liên kết ban đầu.

## Ý tưởng cốt lõi

Bài toán này có thể giải quyết bằng **Thuật toán tìm chu trình của Floyd** (hay còn gọi là thuật toán Rùa và Thỏ).
Thuật toán sử dụng hai con trỏ di chuyển với tốc độ khác nhau:
- **Con trỏ chậm (slow)**: Di chuyển 1 bước mỗi lần.
- **Con trỏ nhanh (fast)**: Di chuyển 2 bước mỗi lần.

Nếu có chu trình, hai con trỏ chắc chắn sẽ gặp nhau tại một điểm nào đó bên trong chu trình. Sau khi gặp nhau, chúng ta có thể sử dụng một tính chất toán học để tìm điểm bắt đầu của chu trình:
- Gọi $L_1$ là khoảng cách từ điểm bắt đầu danh sách tới điểm bắt đầu chu trình.
- Gọi $L_2$ là khoảng cách từ điểm bắt đầu chu trình tới điểm gặp nhau.
- Gọi $C$ là độ dài của chu trình.

Tại điểm gặp nhau, con trỏ nhanh đã đi được quãng đường gấp đôi con trỏ chậm. Qua các bước biến đổi toán học, ta chứng minh được rằng: Khoảng cách từ điểm bắt đầu danh sách tới điểm bắt đầu chu trình bằng đúng khoảng cách từ điểm gặp nhau đi thêm một số vòng để quay về điểm bắt đầu chu trình.

## Thuật toán

1. **Giai đoạn 1: Phát hiện chu trình**:
   - Khởi tạo `slow` và `fast` tại `head`.
   - Di chuyển `slow` 1 bước, `fast` 2 bước cho đến khi `fast` hoặc `fast.next` là `null` (không có chu trình) hoặc `slow == fast` (có chu trình).
2. **Giai đoạn 2: Tìm điểm bắt đầu chu trình**:
   - Nếu phát hiện có chu trình, giữ nguyên `fast` tại điểm gặp nhau và đưa `slow` quay trở lại `head`.
   - Di chuyển cả `slow` và `fast` mỗi người 1 bước mỗi lần.
   - Node nơi chúng gặp nhau lần thứ hai chính là điểm bắt đầu của chu trình.

## Độ phức tạp
- **Thời gian**: $O(N)$ - Trong trường hợp tệ nhất, chúng ta duyệt qua các node một vài lần.
- **Không gian**: $O(1)$ - Chỉ sử dụng hai con trỏ.

## Code (Java)

```java
public class Solution {
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null)
            return null;

        ListNode slow = head;
        ListNode fast = head;

        // Bước 1: Tìm điểm gặp nhau trong chu trình
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                // Có chu trình, chuyển sang bước 2
                slow = head;
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }

        return null;
    }
}
```
