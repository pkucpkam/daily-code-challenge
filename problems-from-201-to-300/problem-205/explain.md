# Longest Consecutive Sequence

## Yêu cầu bài toán

- Cho một mảng các số nguyên chưa sắp xếp `nums`.
- Trả về chiều dài của chuỗi dãy số liên tiếp tăng dần dài nhất được tạo ra khi lấy ra vài phần tử trong mảng. (Các phần tử này không bắt buộc phải đứng gần nhau hoặc thứ tự giữ nguyên trong mảng ban đầu).
- Yêu cầu bắt buộc: **Thuật toán phải chạy trong thời gian `O(N)`**.

Ví dụ: `nums = [100, 4, 200, 1, 3, 2]` -> Cấu hình dãy số tốt nhất là `[1, 2, 3, 4]` với độ dài là 4.

## Ý tưởng cốt lõi

Nếu sử dụng tính năng "Sắp xếp" (Sort) mảng trước, thuật toán sẽ mặc định là `O(N log N)`.
Để đáp ứng điều kiện O(n), ta có thể sử dụng cấu trúc dữ liệu **HashSet** để lưu các số và tận dụng khả năng tra cứu `O(1)`. Bằng cách chỉ bắt đầu tìm một chuỗi liên tiếp nếu giá trị đó là **phần tử khởi đầu chuỗi**. Một phần tử là điểm đầu chuỗi nếu trong mảng không tồn tại giá trị nào nhỏ hơn nó một đơn vị.

## Thuật toán

1. Tạo một `HashSet`, thêm đủ mọi phần tử nằm trong mảng `nums` vào trong Set.
2. Lặp qua các phần tử `num` của `set` đó:
   - Hãy xem `num` có xứng đáng làm phần tử xuất phát hay không bằng cách tra: `set.contains(num - 1)`. 
   - Nếu `num - 1` **KHÔNG TỒN TẠI**: có nghĩa `num` đang là "đáy" (số nhỏ nhất) của một dãy số tăng đếm.
   - Ta bắt đầu một vòng lặp phụ: tra cứu `num + 1`, `num + 2`, v.v. bằng biến đếm của vòng `while` bên trong, cho đến khi bị gãy chuỗi thì thôi.
   - Sau đó cập nhật chiều dài tổng số (biến `best`) nếu cái dãy vừa tra lớn hơn thành tíc độ dài cũ.

## Vì sao đúng và chạy O(N)?

- Về tính đúng: Tất cả phần tử xuất phát tiềm năng đều được kiểm tra, cho nên không lo có một dải dài lại bị bỏ qua. 
- Về độ phức tạp thời gian: Bạn có thể thắc mắc tại sao vòng lặp kép là `while` lồng trong `for` lại cấu thành O(N). Câu trả lời là vì **lệnh `while` chỉ được kích hoạt (nhảy vào trong) đúng lúc `num` đứng tự do hoặc đứng đầu một dải**. Nên tổng thể, vòng `while` bên trong chỉ chạy duyệt qua các số nguyên trong dãy _đúng 1 lần duy nhất trên toàn cục_. Cho nên thuật toán đạt giới hạn cận biên thời gian $O(N)$ nghiêm ngặt.

## Độ phức tạp
- Thời gian: `O(N)` với $N$ là độ rộng của mảng (Thêm `N` phần tử vào Hashset tốn $O(N)$, duyệt lần 2 lại tốn $O(N)$). 
- Không gian phụ: `O(N)` do phải lưu một HashSet.

## Code (Java)

```java
import java.util.*;

class Solution {
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        Set<Integer> set = new HashSet<>();
        for (int num : nums) set.add(num);

        int best = 0;
        for (int num : set) {
            // Chỉ bắt đầu đếm nếu num là giá trị xuất phát (đầu) một chuỗi
            if (!set.contains(num - 1)) {
                int curr = 1;
                int x = num + 1;
                while (set.contains(x)) {
                    curr++;
                    x++;
                }
                best = Math.max(best, curr);
            }
        }
        return best;
    }
}
```
