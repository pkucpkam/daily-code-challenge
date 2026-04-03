## 1. Mô tả bài toán

Bạn được cung cấp một mảng số nguyên `nums` đã được sắp xếp theo thứ tự không giảm (non-decreasing order). Nhiệm vụ của bạn là loại bỏ các phần tử trùng lặp tại chỗ (in-place) sao cho mỗi phần tử duy nhất chỉ xuất hiện một lần. Thứ tự tương đối của các phần tử phải được giữ nguyên. Sau đó, hãy trả về số lượng các phần tử duy nhất `k`.

Cụ thể, bạn cần:
1.  Sửa đổi mảng `nums` sao cho `k` phần tử đầu tiên của mảng chứa các phần tử duy nhất theo đúng thứ tự ban đầu.
2.  Các phần tử còn lại trong mảng `nums` (từ chỉ số `k` trở đi) không quan trọng.
3.  Trả về giá trị `k`.

**Ví dụ:**
*   Input: `nums = [1,1,2]`
*   Output: `k = 2`, `nums` trở thành `[1,2,_]` (các phần tử sau chỉ số `k` không quan trọng).

## 2. Ý tưởng cốt lõi

Vì mảng đã được sắp xếp và yêu cầu xóa các phần tử tại chỗ (in-place) mà không sử dụng thêm không gian bộ nhớ đáng kể, ý tưởng cốt lõi là sử dụng kỹ thuật "hai con trỏ" (two-pointer technique).

*   Một con trỏ (`k`, hay còn gọi là `slow` pointer) sẽ theo dõi vị trí cuối cùng của phần tử duy nhất đã được xác định và ghi lại.
*   Một con trỏ khác (`i`, hay còn gọi là `fast` pointer) sẽ duyệt qua toàn bộ mảng để tìm các phần tử duy nhất.

Khi con trỏ `fast` tìm thấy một phần tử khác với phần tử mà con trỏ `slow` đang trỏ tới (tức là một phần tử duy nhất mới), chúng ta sẽ di chuyển phần tử đó đến vị trí tiếp theo của con trỏ `slow`.

## 3. Giải thích thuật toán

Thuật toán sử dụng kỹ thuật hai con trỏ như sau:

1.  **Xử lý trường hợp cơ sở:** Nếu mảng `nums` rỗng (`nums.length == 0`), không có phần tử duy nhất nào, trả về `0`.

2.  **Khởi tạo con trỏ:**
    *   Khởi tạo con trỏ `k` (tượng trưng cho vị trí của phần tử duy nhất tiếp theo cần được ghi) tại chỉ số `0`. `nums[0]` luôn được coi là phần tử duy nhất đầu tiên.
    *   Khởi tạo con trỏ `i` (tượng trưng cho con trỏ duyệt nhanh) tại chỉ số `1`. Con trỏ này sẽ bắt đầu duyệt từ phần tử thứ hai của mảng.

3.  **Duyệt mảng:**
    *   Sử dụng một vòng lặp `for` để di chuyển con trỏ `i` từ `1` đến hết mảng (`nums.length - 1`).

4.  **So sánh và di chuyển phần tử:**
    *   Bên trong vòng lặp, chúng ta so sánh giá trị của `nums[i]` (phần tử hiện tại mà con trỏ nhanh đang trỏ tới) với `nums[k]` (phần tử duy nhất cuối cùng đã được ghi nhận).
    *   **Nếu `nums[i] != nums[k]`:** Điều này có nghĩa là chúng ta đã tìm thấy một phần tử duy nhất mới (không trùng lặp với phần tử duy nhất cuối cùng).
        *   Tăng con trỏ `k` lên `1` (`k++`) để chuyển đến vị trí tiếp theo mà phần tử duy nhất mới sẽ được đặt vào.
        *   Gán giá trị `nums[i]` vào vị trí `nums[k]` (`nums[k] = nums[i]`).
    *   **Nếu `nums[i] == nums[k]`:** Điều này có nghĩa là `nums[i]` là một phần tử trùng lặp. Chúng ta chỉ cần bỏ qua nó và tiếp tục di chuyển con trỏ `i` đến phần tử tiếp theo trong vòng lặp.

5.  **Trả về kết quả:**
    *   Sau khi vòng lặp kết thúc, con trỏ `k` sẽ trỏ đến chỉ số của phần tử duy nhất cuối cùng trong mảng đã được sửa đổi. Vì mảng được đánh chỉ số từ 0, tổng số lượng phần tử duy nhất sẽ là `k + 1`. Trả về `k + 1`.

Ví dụ minh họa với `nums = [0,0,1,1,1,2,2,3,3,4]`:

| i | nums[i] | k | nums[k] | nums[i] != nums[k] ? | Hành động                                | nums (sau hành động)               |
|---|---------|---|---------|----------------------|------------------------------------------|-----------------------------------|
| - | -       | 0 | -       | -                    | Khởi tạo                                 | `[0,0,1,1,1,2,2,3,3,4]`           |
| 1 | 0       | 0 | 0       | Sai                  | Bỏ qua                                   | `[0,0,1,1,1,2,2,3,3,4]`           |
| 2 | 1       | 0 | 0       | Đúng                 | `k++` (k=1), `nums[1] = nums[2]` (1)     | `[0,1,1,1,1,2,2,3,3,4]`           |
| 3 | 1       | 1 | 1       | Sai                  | Bỏ qua                                   | `[0,1,1,1,1,2,2,3,3,4]`           |
| 4 | 1       | 1 | 1       | Sai                  | Bỏ qua                                   | `[0,1,1,1,1,2,2,3,3,4]`           |
| 5 | 2       | 1 | 1       | Đúng                 | `k++` (k=2), `nums[2] = nums[5]` (2)     | `[0,1,2,1,1,2,2,3,3,4]`           |
| 6 | 2       | 2 | 2       | Sai                  | Bỏ qua                                   | `[0,1,2,1,1,2,2,3,3,4]`           |
| 7 | 3       | 2 | 2       | Đúng                 | `k++` (k=3), `nums[3] = nums[7]` (3)     | `[0,1,2,3,1,2,2,3,3,4]`           |
| 8 | 3       | 3 | 3       | Sai                  | Bỏ qua                                   | `[0,1,2,3,1,2,2,3,3,4]`           |
| 9 | 4       | 3 | 3       | Đúng                 | `k++` (k=4), `nums[4] = nums[9]` (4)     | `[0,1,2,3,4,2,2,3,3,4]`           |
| Kết quả                                                                                                                              | Trả về `k + 1 = 4 + 1 = 5`         |

## 4. Độ phức tạp

*   **Độ phức tạp thời gian (Time Complexity):** O(n)
    *   Con trỏ `i` duyệt qua toàn bộ mảng từ đầu đến cuối một lần duy nhất. Mỗi thao tác bên trong vòng lặp (so sánh, tăng con trỏ, gán) đều là các thao tác hằng số (O(1)). Do đó, độ phức tạp thời gian là tuyến tính theo kích thước `n` của mảng `nums`.

*   **Độ phức tạp không gian (Space Complexity):** O(1)
    *   Thuật toán hoạt động hoàn toàn tại chỗ (in-place) bằng cách sửa đổi trực tiếp mảng đầu vào. Nó chỉ sử dụng một vài biến con trỏ (`i`, `k`) mà không phụ thuộc vào kích thước của đầu vào. Do đó, độ phức tạp không gian là hằng số.

## 5. Code

java
class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0)
            return 0;

        // k là con trỏ chỉ vào vị trí cuối cùng của phần tử duy nhất đã được ghi nhận.
        // Nó cũng đại diện cho số lượng phần tử duy nhất (trừ đi 1 do chỉ số bắt đầu từ 0).
        int k = 0; 
        
        // Con trỏ i duyệt qua toàn bộ mảng để tìm các phần tử duy nhất.
        for (int i = 1; i < nums.length; i++) {
            // Nếu phần tử hiện tại (nums[i]) khác với phần tử duy nhất cuối cùng được ghi nhận (nums[k]),
            // thì nums[i] là một phần tử duy nhất mới.
            if (nums[i] != nums[k]) {
                // Tăng k lên 1 để chuyển đến vị trí tiếp theo cho phần tử duy nhất mới.
                k++;
                // Ghi nhận phần tử duy nhất mới vào vị trí nums[k].
                nums[k] = nums[i];
            }
            // Nếu nums[i] == nums[k], đó là một phần tử trùng lặp, ta bỏ qua nó
            // và con trỏ i sẽ tự động tăng lên trong vòng lặp.
        }
        // Số lượng phần tử duy nhất là k + 1 vì k là chỉ số (bắt đầu từ 0).
        return k + 1;
    }
}