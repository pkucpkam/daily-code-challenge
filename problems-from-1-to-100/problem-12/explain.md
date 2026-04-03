## 1. Mô tả bài toán
Bài toán yêu cầu chúng ta loại bỏ tất cả các lần xuất hiện của một giá trị `val` cụ thể khỏi một mảng số nguyên `nums` *tại chỗ* (in-place). Thứ tự của các phần tử còn lại có thể thay đổi. Mục tiêu là trả về số lượng các phần tử trong `nums` không bằng `val`, gọi là `k`.

Để được chấp nhận, chúng ta cần:
1.  Thay đổi mảng `nums` sao cho `k` phần tử đầu tiên của `nums` chứa các phần tử không bằng `val`.
2.  Các phần tử còn lại của `nums` (từ vị trí `k` trở đi) không quan trọng.
3.  Trả về giá trị `k`.

Ví dụ:
*   `nums = [3,2,2,3], val = 3` => Trả về `k = 2`, `nums` trở thành `[2,2,_,_]`
*   `nums = [0,1,2,2,3,0,4,2], val = 2` => Trả về `k = 5`, `nums` trở thành `[0,1,4,0,3,_,_,_]` (thứ tự các phần tử có thể khác)

## 2. Ý tưởng cốt lõi
Ý tưởng cốt lõi của giải pháp này là duyệt qua mảng từ trái sang phải. Mỗi khi gặp một phần tử có giá trị bằng `val` (phần tử cần loại bỏ), chúng ta sẽ dịch chuyển tất cả các phần tử phía sau nó lên một vị trí về phía trước để "ghi đè" lên phần tử đã loại bỏ. Đồng thời, chúng ta theo dõi kích thước hiệu quả của mảng (`k`) và vị trí hiện tại trong quá trình duyệt. Khi một phần tử bị loại bỏ, kích thước hiệu quả `k` sẽ giảm và chúng ta cần kiểm tra lại phần tử mới được dịch chuyển vào vị trí hiện tại.

## 3. Giải thích thuật toán
Thuật toán hoạt động theo các bước sau:

1.  **Khởi tạo `k`**: Một biến `k` được khởi tạo bằng độ dài ban đầu của mảng `nums` (`nums.length`). Biến này sẽ đóng vai trò như:
    *   Giới hạn trên của vòng lặp duyệt mảng.
    *   Số lượng phần tử hợp lệ cuối cùng trong mảng.
    *   Một con trỏ "ảo" tới cuối của phần mảng chứa các phần tử không bằng `val`.

2.  **Duyệt mảng**: Sử dụng một vòng lặp `for` với biến `i` từ `0` đến `k-1`. Vòng lặp này sẽ duyệt qua phần mảng hiện đang được coi là hợp lệ.

3.  **Kiểm tra phần tử**: Trong mỗi lần lặp, kiểm tra xem `nums[i]` có bằng `val` hay không.
    *   **Nếu `nums[i] == val` (Phần tử cần loại bỏ)**:
        *   Chúng ta cần loại bỏ phần tử này khỏi vị trí `i`. Để làm điều đó, chúng ta sẽ dịch chuyển tất cả các phần tử từ vị trí `i+1` đến `k-1` lên một vị trí về phía trước. Một vòng lặp `for` lồng nhau với biến `j` từ `i` đến `k-2` sẽ thực hiện `nums[j] = nums[j+1]`. Thao tác này ghi đè lên `nums[i]` bằng `nums[i+1]`, `nums[i+1]` bằng `nums[i+2]`, v.v.
        *   Sau khi dịch chuyển, phần tử ban đầu ở `nums[i+1]` đã di chuyển đến `nums[i]`. Vì vậy, chúng ta cần kiểm tra lại phần tử *mới* ở `nums[i]` trong lần lặp tiếp theo để xem nó có phải là `val` hay không. Để đảm bảo điều này, chúng ta giảm `i` đi `1` (`i--`). Khi vòng lặp chính tăng `i` ở cuối mỗi lần lặp, `i` sẽ trở lại giá trị hiện tại, cho phép kiểm tra lại phần tử đã dịch chuyển vào.
        *   Vì một phần tử đã bị "loại bỏ" khỏi phần hợp lệ của mảng, chúng ta giảm `k` đi `1` (`k--`). Điều này thu hẹp kích thước hiệu quả của mảng.
    *   **Nếu `nums[i] != val` (Phần tử hợp lệ)**:
        *   Phần tử này không cần loại bỏ. Chúng ta không làm gì cả và tiếp tục vòng lặp chính với `i` tăng lên như bình thường.

4.  **Kết thúc**: Sau khi vòng lặp chính kết thúc, biến `k` sẽ chứa số lượng các phần tử không bằng `val`, và các phần tử đó sẽ nằm ở `k` vị trí đầu tiên của mảng `nums`.

5.  **Trả về `k`**: Trả về giá trị của `k`.

## 4. Độ phức tạp

*   **Độ phức tạp thời gian (Time Complexity)**: `O(N^2)`
    *   Vòng lặp bên ngoài (`for (int i = 0; i < k; i++)`) chạy tối đa `N` lần (trong đó `N` là độ dài ban đầu của mảng `nums`).
    *   Trong trường hợp xấu nhất, nếu tất cả các phần tử trong mảng đều bằng `val` (ví dụ: `nums = [3,3,3,3]`, `val = 3`), thì mỗi lần lặp của vòng lặp ngoài sẽ kích hoạt vòng lặp dịch chuyển bên trong (`for (int j = i; j < k - 1; j++)`).
    *   Vòng lặp dịch chuyển bên trong có thể chạy tối đa `N` lần (khi `i` gần đầu mảng và `k` vẫn lớn). Ví dụ, nếu `nums = [2,2,2,2]` và `val = 2`, lần đầu tiên `i=0`, vòng lặp trong chạy `N-1` lần. Lần thứ hai `i=0` (do `i--`), vòng lặp trong chạy `N-2` lần, v.v.
    *   Do đó, độ phức tạp thời gian tổng thể là `O(N * N) = O(N^2)`.

*   **Độ phức tạp không gian (Space Complexity)**: `O(1)`
    *   Thuật toán chỉ sử dụng một vài biến số nguyên (`i`, `j`, `k`) để thực hiện các phép toán.
    *   Không có cấu trúc dữ liệu bổ sung nào có kích thước phụ thuộc vào `N` được sử dụng.
    *   Do đó, độ phức tạp không gian là hằng số.

## 5. Code

java
class Solution {
    public int removeElement(int[] nums, int val) {
        int k = nums.length; // k represents the effective length of the array (elements not equal to val)
        
        // Iterate through the array up to the current effective length k
        for (int i = 0; i < k; i++) {
            // If the current element is equal to val, it needs to be removed
            if (nums[i] == val) {
                // Shift all subsequent elements one position to the left
                // This effectively "removes" nums[i] by overwriting it
                for (int j = i; j < k - 1; j++) {
                    nums[j] = nums[j + 1];
                }
                // Decrement i to re-check the element that just moved into position i.
                // This is crucial because the new element at nums[i] might also be 'val'.
                i--;
                // Decrement k because one 'val' element has been effectively removed,
                // reducing the effective length of the array.
                k--;
            }
        }
        // Return k, which is the number of elements not equal to val
        return k;
    }
}