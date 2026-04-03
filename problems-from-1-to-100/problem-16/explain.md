## 1. Mô tả bài toán

Bài toán yêu cầu chúng ta cộng thêm 1 vào một số nguyên lớn được biểu diễn dưới dạng một mảng các chữ số. Mỗi phần tử `digits[i]` trong mảng là một chữ số của số đó. Các chữ số được sắp xếp từ trái sang phải, từ chữ số có ý nghĩa nhất đến chữ số có ý nghĩa ít nhất. Số nguyên lớn này không chứa các số 0 ở đầu (ngoại trừ số 0 đơn lẻ). Nhiệm vụ là trả về mảng chữ số mới sau khi đã cộng thêm 1.

Ví dụ:
*   `[1,2,3]` trở thành `[1,2,4]` (123 + 1 = 124)
*   `[4,3,2,1]` trở thành `[4,3,2,2]` (4321 + 1 = 4322)
*   `[9]` trở thành `[1,0]` (9 + 1 = 10)

## 2. Ý tưởng cốt lõi

Ý tưởng cốt lõi là mô phỏng lại quá trình cộng thêm 1 mà chúng ta thực hiện bằng tay. Chúng ta sẽ bắt đầu từ chữ số cuối cùng (ít quan trọng nhất) của số.

1.  Nếu chữ số cuối cùng nhỏ hơn 9, chúng ta chỉ cần tăng nó lên 1 và đó là kết quả. Không cần quan tâm đến các chữ số khác.
2.  Nếu chữ số cuối cùng là 9, chúng ta đặt nó thành 0 và có một "nhớ" (carry) sang chữ số liền trước nó. Quá trình này lặp lại cho đến khi chúng ta gặp một chữ số không phải là 9, hoặc cho đến khi chúng ta đã xử lý tất cả các chữ số.
3.  Trường hợp đặc biệt là khi tất cả các chữ số ban đầu đều là 9 (ví dụ: `[9,9,9]`). Khi đó, tất cả các chữ số sẽ trở thành 0, và chúng ta cần thêm một chữ số 1 vào đầu mảng kết quả (ví dụ: `[9,9,9]` trở thành `[1,0,0,0]`).

## 3. Giải thích thuật toán

Thuật toán thực hiện các bước sau:

1.  **Khởi tạo**: Lấy độ dài `n` của mảng `digits`.
2.  **Duyệt từ phải sang trái**: Lặp qua mảng `digits` từ chữ số cuối cùng (chỉ mục `n-1`) về phía chữ số đầu tiên (chỉ mục `0`).
3.  **Xử lý chữ số hiện tại**:
    *   **Nếu `digits[i] < 9`**: Đây là trường hợp phổ biến nhất. Tăng `digits[i]` lên 1. Vì không có số nhớ nào được tạo ra, chúng ta đã hoàn thành việc cộng 1 và có thể trả về mảng `digits` hiện tại.
    *   **Nếu `digits[i] == 9`**: Chữ số này sẽ trở thành 0 khi cộng 1 (vì 9 + 1 = 10, nhớ 1). Đặt `digits[i] = 0`. Tiếp tục vòng lặp để xử lý chữ số liền trước nó, vì có một số nhớ cần được cộng vào đó.
4.  **Trường hợp tất cả là 9**: Nếu vòng lặp kết thúc mà chưa trả về mảng `digits` (điều này chỉ xảy ra khi tất cả các chữ số ban đầu đều là 9, ví dụ: `[9,9,9]`), điều đó có nghĩa là tất cả các chữ số đã được đặt thành 0 và vẫn còn một số nhớ. Trong trường hợp này, chúng ta cần một mảng mới có kích thước lớn hơn 1 so với mảng ban đầu.
    *   Tạo một mảng `newDigits` mới có kích thước `n + 1`.
    *   Đặt `newDigits[0] = 1` (đây là số nhớ cuối cùng). Các phần tử còn lại của `newDigits` mặc định là 0 (đúng với kết quả của việc các chữ số 9 chuyển thành 0).
    *   Trả về mảng `newDigits`.

## 4. Độ phức tạp

*   **Độ phức tạp thời gian (Time Complexity)**:
    *   **Tốt nhất (Best Case)**: O(1). Xảy ra khi chữ số cuối cùng không phải là 9 (ví dụ: `[1,2,3]` -> `[1,2,4]`). Chỉ cần một thao tác tăng và trả về.
    *   **Trung bình và Tệ nhất (Average & Worst Case)**: O(N), trong đó `N` là số lượng chữ số. Xảy ra khi có các chữ số 9 ở cuối mảng, hoặc tất cả các chữ số đều là 9 (ví dụ: `[1,2,9]` hoặc `[9,9,9]`). Thuật toán có thể phải duyệt qua tất cả các chữ số của mảng.
    *   Tổng thể, ta thường nói độ phức tạp thời gian là O(N).

*   **Độ phức tạp không gian (Space Complexity)**:
    *   **Tốt nhất và Trung bình (Best & Average Case)**: O(1). Xảy ra khi không cần tạo mảng mới, ta chỉ sửa đổi mảng đầu vào và trả về nó.
    *   **Tệ nhất (Worst Case)**: O(N). Xảy ra khi tất cả các chữ số ban đầu đều là 9 (ví dụ: `[9,9,9]`). Trong trường hợp này, một mảng mới có kích thước `N+1` cần được tạo ra.
    *   Tổng thể, ta thường nói độ phức tạp không gian là O(N) do khả năng phải tạo mảng mới.

## 5. Code

java
class Solution {
    public int[] plusOne(int[] digits) {
        int n = digits.length;

        // Duyệt từ chữ số cuối cùng về đầu tiên
        for (int i = n - 1; i >= 0; i--) {
            // Nếu chữ số hiện tại nhỏ hơn 9, chỉ cần tăng nó lên 1
            // và trả về mảng. Không có số nhớ.
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            // Nếu chữ số hiện tại là 9, đặt nó thành 0 và tiếp tục vòng lặp
            // để xử lý số nhớ sang chữ số tiếp theo.
            digits[i] = 0;
        }

        // Nếu vòng lặp kết thúc, điều đó có nghĩa là tất cả các chữ số ban đầu
        // đều là 9 (ví dụ: [9,9,9]).
        // Chúng ta cần một mảng mới lớn hơn 1 phần tử.
        int[] newDigits = new int[n + 1];
        // Chữ số đầu tiên của mảng mới sẽ là 1 (số nhớ cuối cùng).
        newDigits[0] = 1;
        // Các phần tử còn lại của newDigits mặc định là 0, điều này đúng.
        return newDigits;
    }
}