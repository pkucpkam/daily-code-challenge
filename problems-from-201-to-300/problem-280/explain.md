# 287. Find the Duplicate Number - Best Solution

## Tóm tắt bài toán

Cho một mảng `nums` có `n + 1` phần tử, mỗi phần tử nằm trong đoạn `[1, n]`. Chỉ có đúng một giá trị bị lặp lại, và giá trị đó xuất hiện ít nhất hai lần. Yêu cầu tìm giá trị lặp mà không được sửa mảng và chỉ dùng `O(1)` bộ nhớ phụ.

## Ý tưởng chính

Ta xem mảng như một đồ thị hàm:

- Mỗi chỉ số `i` trỏ tới `nums[i]`.
- Vì mọi giá trị đều nằm trong `[1, n]`, nên các “cạnh” luôn đi tới một chỉ số hợp lệ khác.
- Do có `n + 1` phần tử nhưng chỉ có `n` giá trị khả dĩ, chắc chắn sẽ có chu trình.

Giá trị bị lặp chính là điểm vào của chu trình đó. Đây là bài toán cổ điển có thể giải bằng thuật toán Floyd cycle detection:

- Giai đoạn 1: dùng hai con trỏ `slow` và `fast` để tìm điểm gặp nhau trong chu trình.
- Giai đoạn 2: đưa một con trỏ về đầu mảng, rồi di chuyển cả hai con trỏ từng bước một. Vị trí chúng gặp lại chính là số bị lặp.

## Vì sao đúng

- Mỗi phần tử của mảng tạo ra một bước nhảy sang một chỉ số khác, nên toàn bộ quá trình truy vết là một đồ thị có chu trình.
- Vì chỉ có một số bị lặp, chu trình này là duy nhất theo đường đi bắt đầu từ `nums[0]`.
- Floyd cycle detection đảm bảo hai con trỏ sẽ gặp nhau bên trong chu trình ở giai đoạn 1.
- Khi đưa một con trỏ về đầu, việc cho cả hai đi từng bước sẽ khiến chúng gặp nhau tại điểm vào chu trình, tức là số bị lặp.

## Độ phức tạp

- Time: `O(n)`
- Space: `O(1)`

## Java Code

```java
class Solution {
    public int findDuplicate(int[] nums) {
        int slow = nums[0];
        int fast = nums[0];

        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        slow = nums[0];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        return slow;
    }
}
```

## Ghi chú

Đây là lời giải tối ưu phổ biến nhất cho bài này vì vừa đạt thời gian tuyến tính, vừa không cần chỉnh sửa mảng đầu vào.
