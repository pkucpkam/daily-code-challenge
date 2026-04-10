# 237. Delete Node in a Linked List - Best Solution

## Mô tả bài toán

Bạn được cho trực tiếp node `node` cần xóa trong danh sách liên kết đơn.
Không có con trỏ `head`, và đề bài đảm bảo `node` không phải node cuối.

Mục tiêu là làm cho giá trị của `node` biến mất khỏi danh sách và số lượng node giảm đi 1.

## Ý tưởng tối ưu

Vì không có `head`, ta không thể duyệt để tìm node đứng trước `node` rồi nối lại như cách xóa thông thường.

Mẹo tối ưu:

1. Sao chép giá trị của node kế tiếp (`node.next.val`) vào `node` hiện tại.
2. Bỏ qua node kế tiếp bằng cách nối `node.next = node.next.next`.

Sau 2 bước này, giá trị cũ của `node` đã biến mất khỏi list.

## Vì sao đúng

- Trước thao tác: `... -> node(a) -> next(b) -> ...`
- Sau thao tác sao chép và nối lại: `... -> node(b) -> ...`

Node có giá trị `a` đã không còn xuất hiện trong danh sách.
Hiệu ứng tương đương xóa node ban đầu cần xóa.

Điều này chỉ hợp lệ vì đề bài đảm bảo `node` không phải tail.

## Độ phức tạp

- Thời gian: `O(1)`
- Bộ nhớ phụ: `O(1)`

## Java Code

```java
class Solution {
  public void deleteNode(ListNode node) {
    node.val = node.next.val;
    node.next = node.next.next;
  }
}
```
