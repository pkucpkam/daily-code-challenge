# Intersection of Two Linked Lists

## 1. Mô tả bài toán
Cho `headA` và `headB` là hai danh sách liên kết đơn. Hãy tìm và trả về node mà tại đó hai danh sách này giao nhau (bắt đầu trùng nhau về tham chiếu bộ nhớ). Nếu hai danh sách không giao nhau, trả về `null`.
Lưu ý: Cấu trúc của danh sách gốc phải được giữ nguyên sau khi hàm thực hiện xong.

## 2. Ý tưởng cốt lõi
- Nếu hai danh sách có độ dài bằng nhau, ta chỉ cần duyệt song song, nếu gặp node trùng nhau thì đó là điểm giao.
- Tuy nhiên, độ dài thường khác nhau. Ý tưởng thông minh ở đây là:
    - Khi con trỏ `a` của danh sách A đi hết, hãy cho nó bắt đầu lại ở đầu danh sách B.
    - Khi con trỏ `b` của danh sách B đi hết, hãy cho nó bắt đầu lại ở đầu danh sách A.
- Khi làm như vậy, cả hai con trỏ đều sẽ đi một quãng đường có tổng độ dài là `len(A) + len(B)`. Điểm mà chúng gặp nhau lần đầu tiên chính là điểm giao nhau (hoặc cả hai cùng chạm `null` nếu không giao).

## 3. Giải thích thuật toán
1. Kiểm tra nếu một trong hai đầu danh sách là `null`, trả về `null`.
2. Khởi tạo hai con trỏ `a = headA` và `b = headB`.
3. Trong khi `a` khác `b`:
   - Nếu `a` đi tới cuối (`null`), gắn `a` vào đầu danh sách B (`headB`). Ngược lại di chuyển `a = a.next`.
   - Nếu `b` đi tới cuối (`null`), gắn `b` vào đầu danh sách A (`headA`). Ngược lại di chuyển `b = b.next`.
4. Khi thoát vòng lặp, nếu `a == b`, đó chính là node giao nhau (hoặc `null`).

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(m + n)\) với `m, n` là độ dài của hai danh sách. Mỗi con trỏ đi tối đa 2 lượt qua các node.
- **Không gian (Space Complexity)**: \(O(1)\) - Chỉ sử dụng hai con trỏ, không tốn thêm bộ nhớ.

## 5. Code (Java)
```java
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        ListNode a = headA;
        ListNode b = headB;

        // Hai con trỏ sẽ gặp nhau sau tối đa len(A) + len(B) bước
        while (a != b) {
            a = (a == null) ? headB : a.next;
            b = (b == null) ? headA : b.next;
        }

        return a; 
    }
}
```
