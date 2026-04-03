## 1. Mô tả bài toán

Bài toán yêu cầu chúng ta tìm vị trí của một giá trị `target` trong một mảng số nguyên `nums` đã được sắp xếp theo thứ tự tăng dần và chứa các giá trị phân biệt.

Có ba trường hợp xảy ra:
1.  Nếu `target` được tìm thấy trong mảng, hãy trả về chỉ số của nó.
2.  Nếu `target` không có trong mảng, hãy trả về chỉ số mà nó sẽ được chèn vào để giữ cho mảng vẫn được sắp xếp.

Yêu cầu đặc biệt là thuật toán phải có độ phức tạp thời gian O(log n).

**Ví dụ:**

*   **Ví dụ 1:**
    *   `nums = [1,3,5,6]`, `target = 5`
    *   `target` được tìm thấy ở chỉ số `2`.
    *   **Output: 2**

*   **Ví dụ 2:**
    *   `nums = [1,3,5,6]`, `target = 2`
    *   `target` không có trong mảng, nhưng nếu được chèn vào, nó sẽ đứng trước `3` (chỉ số `1`).
    *   **Output: 1**

*   **Ví dụ 3:**
    *   `nums = [1,3,5,6]`, `target = 7`
    *   `target` không có trong mảng và lớn hơn tất cả các phần tử. Nó sẽ được chèn vào cuối mảng.
    *   **Output: 4**

## 2. Ý tưởng cốt lõi

Mảng `nums` đã được sắp xếp, điều này là chìa khóa để giải quyết bài toán. Ý tưởng chính của giải pháp được cung cấp là duyệt qua mảng từ đầu đến cuối.

Chúng ta sẽ tìm kiếm điểm chèn (hoặc vị trí tìm thấy) bằng cách:
*   Duyệt qua từng phần tử trong mảng.
*   Nếu tại bất kỳ vị trí `i` nào, chúng ta thấy `target` nhỏ hơn hoặc bằng `nums[i]`, thì `i` chính là chỉ số mà chúng ta cần trả về. Điều này bao gồm cả trường hợp `target` được tìm thấy (`target == nums[i]`) và trường hợp `target` không tìm thấy nhưng nên được chèn vào trước `nums[i]` (`target < nums[i]`).
*   Nếu chúng ta duyệt hết toàn bộ mảng mà không tìm thấy phần tử nào lớn hơn hoặc bằng `target`, điều đó có nghĩa là `target` lớn hơn tất cả các phần tử hiện có. Trong trường hợp này, `target` nên được chèn vào cuối mảng.

## 3. Giải thích thuật toán

Thuật toán được triển khai trong đoạn mã cung cấp là một tìm kiếm tuyến tính (linear search):

1.  **Khởi tạo vòng lặp:** Bắt đầu duyệt mảng từ chỉ số `i = 0` cho đến hết mảng (`nums.length - 1`).
2.  **Kiểm tra điều kiện:** Trong mỗi lần lặp, so sánh `target` với phần tử hiện tại `nums[i]`.
    *   Nếu `target <= nums[i]`: Điều này có nghĩa là `target` đã được tìm thấy (nếu `target == nums[i]`) hoặc `target` nên được chèn vào vị trí `i` vì nó nhỏ hơn `nums[i]`. Trong cả hai trường hợp, `i` là chỉ số mong muốn, vì vậy chúng ta trả về `i` và kết thúc hàm.
3.  **Trường hợp không tìm thấy và lớn hơn tất cả:** Nếu vòng lặp hoàn thành mà không có lệnh `return` nào được thực thi, điều đó có nghĩa là `target` lớn hơn tất cả các phần tử trong mảng `nums`. Trong trường hợp này, `target` sẽ được chèn vào cuối mảng. Chỉ số cuối cùng này chính là độ dài của mảng, `nums.length`. Vì vậy, chúng ta trả về `nums.length`.

## 4. Độ phức tạp

**Độ phức tạp thời gian (Time Complexity):**
*   O(n), trong đó `n` là số lượng phần tử trong mảng `nums`.
*   Trong trường hợp xấu nhất (ví dụ: `target` lớn hơn tất cả các phần tử hoặc nằm gần cuối mảng), thuật toán phải duyệt qua toàn bộ mảng một lần.
*   **Lưu ý quan trọng:** Đề bài yêu cầu độ phức tạp thời gian O(log n). Giải pháp hiện tại sử dụng tìm kiếm tuyến tính có độ phức tạp O(n), không đáp ứng yêu cầu này. Để đạt được O(log n), cần sử dụng thuật toán tìm kiếm nhị phân (Binary Search).

**Độ phức tạp không gian (Space Complexity):**
*   O(1).
*   Thuật toán chỉ sử dụng một vài biến cục bộ (ví dụ: biến vòng lặp `i`) và không yêu cầu thêm không gian bộ nhớ đáng kể nào khác phụ thuộc vào kích thước của mảng.

## 5. Code

java
class Solution {
    public int searchInsert(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (target <= nums[i]) {
                return i;
            }
        }
        return nums.length;
    }
}