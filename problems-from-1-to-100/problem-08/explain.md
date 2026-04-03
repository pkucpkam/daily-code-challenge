# Giải thích bài toán

Dưới đây là nội dung file `explain.md`:


## 1. Mô tả bài toán

Bài toán yêu cầu viết một hàm tìm tiền tố chung dài nhất (Longest Common Prefix - LCP) trong một mảng các chuỗi. Nếu không có tiền tố chung nào, hàm sẽ trả về một chuỗi rỗng `""`.

Ví dụ:
*   Với `strs = ["flower","flow","flight"]`, tiền tố chung dài nhất là `"fl"`.
*   Với `strs = ["dog","racecar","car"]`, không có tiền tố chung nào, vì vậy trả về `""`.

*(Lưu ý: Các ràng buộc được cung cấp trong file README.md (`1 <= s.length <= 15`, `s contains only Roman numeral characters`) có vẻ không liên quan đến bài toán LCP mà thuộc về một bài toán khác. Chúng ta sẽ bỏ qua chúng và phân tích độ phức tạp dựa trên độ dài chuỗi tổng quát.)*

## 2. Ý tưởng cốt lõi

Ý tưởng cốt lõi để giải quyết bài toán này là phương pháp "quét ngang" (horizontal scanning). Chúng ta bắt đầu với chuỗi đầu tiên làm tiền tố chung tiềm năng. Sau đó, chúng ta duyệt qua từng chuỗi còn lại trong mảng. Đối với mỗi chuỗi, chúng ta kiểm tra xem tiền tố hiện tại có phải là tiền tố của chuỗi đó hay không. Nếu không, chúng ta sẽ cắt ngắn tiền tố từ phía cuối đi một ký tự cho đến khi nó trở thành tiền tố của chuỗi hiện tại hoặc trở thành chuỗi rỗng. Nếu tiền tố trở thành rỗng, điều đó có nghĩa là không có tiền tố chung giữa các chuỗi đã xét, và chúng ta có thể trả về chuỗi rỗng ngay lập tức. Sau khi duyệt qua tất cả các chuỗi, tiền tố còn lại chính là tiền tố chung dài nhất.

## 3. Giải thích thuật toán

Thuật toán hoạt động theo các bước sau:

1.  **Xử lý trường hợp đặc biệt:**
    *   Kiểm tra nếu mảng `strs` là `null` hoặc rỗng (`strs.length == 0`). Trong trường hợp này, không có chuỗi nào để tìm tiền tố chung, vì vậy trả về chuỗi rỗng `""`.

2.  **Khởi tạo tiền tố chung:**
    *   Gán chuỗi đầu tiên trong mảng (`strs[0]`) làm tiền tố chung tiềm năng ban đầu. Gọi biến này là `prefix`.

3.  **Duyệt qua các chuỗi còn lại:**
    *   Bắt đầu từ chuỗi thứ hai (chỉ số `i = 1`) và duyệt đến hết mảng `strs`.
    *   Đối với mỗi chuỗi `strs[i]`:
        *   Sử dụng một vòng lặp `while` để kiểm tra điều kiện `strs[i].indexOf(prefix) != 0`. Điều kiện này có nghĩa là `prefix` không phải là tiền tố của `strs[i]` (vì nếu là tiền tố, nó phải bắt đầu ở chỉ số 0).
        *   Chừng nào `prefix` chưa phải là tiền tố của `strs[i]`:
            *   Cắt ngắn `prefix` đi một ký tự từ cuối: `prefix = prefix.substring(0, prefix.length() - 1);`.
            *   Kiểm tra nếu `prefix` trở thành rỗng (`prefix.isEmpty()`). Nếu vậy, điều đó có nghĩa là không có tiền tố chung nào giữa các chuỗi đã xét, nên ta trả về `""` ngay lập tức.
        *   Vòng lặp `while` sẽ tiếp tục cho đến khi `prefix` thực sự là tiền tố của `strs[i]`.

4.  **Trả về kết quả:**
    *   Sau khi vòng lặp chính (duyệt qua tất cả các chuỗi) hoàn thành, biến `prefix` sẽ chứa tiền tố chung dài nhất của tất cả các chuỗi trong mảng. Trả về `prefix`.

## 4. Độ phức tạp

Để phân tích độ phức tạp, chúng ta gọi:
*   `N` là số lượng chuỗi trong mảng `strs` (`strs.length`).
*   `M` là độ dài tối đa của một chuỗi trong mảng.

*   **Độ phức tạp thời gian (Time Complexity):**
    *   Bước khởi tạo: `prefix = strs[0]` mất `O(M)` thời gian (để sao chép chuỗi).
    *   Vòng lặp chính chạy `N-1` lần (từ `i = 1` đến `N-1`).
    *   Bên trong vòng lặp chính, vòng lặp `while` kiểm tra và rút gọn `prefix`.
        *   Hàm `strs[i].indexOf(prefix)` trong Java có thể mất đến `O(độ dài chuỗi * độ dài tiền tố)` trong trường hợp xấu nhất (thuật toán tìm kiếm chuỗi đơn giản) hoặc `O(độ dài chuỗi + độ dài tiền tố)` với các thuật toán tối ưu hơn (như KMP). Tuy nhiên, vì chúng ta chỉ quan tâm đến việc `prefix` có nằm ở vị trí 0 hay không (`indexOf(...) != 0`), nó tương đương với `strs[i].startsWith(prefix)`. Hàm `startsWith()` thường có độ phức tạp là `O(độ dài tiền tố)`.
        *   Hàm `prefix.substring(...)` tạo ra một chuỗi mới và mất `O(độ dài tiền tố)` thời gian.
        *   Trong trường hợp xấu nhất, `prefix` có thể bị rút gọn từng ký tự một cho mỗi chuỗi `strs[i]`. Tức là, vòng lặp `while` có thể chạy tới `M` lần (nếu tiền tố bị rút gọn từ độ dài `M` xuống `0`).
        *   Mỗi lần lặp của `while` mất `O(M)` (cho `startsWith` và `substring`).
        *   Do đó, đối với mỗi chuỗi `strs[i]`, chi phí có thể lên tới `M * O(M) = O(M^2)`.
    *   Tổng độ phức tạp thời gian cho toàn bộ thuật toán sẽ là `O(N * M^2)`.

*   **Độ phức tạp không gian (Space Complexity):**
    *   Biến `prefix` lưu trữ một chuỗi. Trong trường hợp xấu nhất, nó có thể dài bằng chuỗi dài nhất trong mảng, tức là `M`.
    *   Hàm `substring` tạo ra các chuỗi mới, nhưng chúng sẽ được thu gom rác (garbage collected) khi không còn được tham chiếu. Tại bất kỳ thời điểm nào, chỉ có một đối tượng `prefix` chiếm không gian đáng kể.
    *   Do đó, độ phức tạp không gian là `O(M)`.

## 5. Code

java
class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";

        String prefix = strs[0];

        for (int i = 1; i < strs.length; i++) {
            // Vòng lặp này rút ngắn 'prefix' cho đến khi nó là tiền tố của strs[i]
            // strs[i].indexOf(prefix) != 0 nghĩa là 'prefix' không bắt đầu strs[i]
            while (strs[i].indexOf(prefix) != 0) {
                // Rút ngắn prefix đi một ký tự từ cuối
                prefix = prefix.substring(0, prefix.length() - 1);
                // Nếu prefix trở thành rỗng, không có tiền tố chung
                if (prefix.isEmpty())
                    return "";
            }
        }

        return prefix;
    }

    // public static void main(String[] args) {
    // Solution sol = new Solution();
    // String[] words = {"flower", "flow", "flight"};
    // System.out.println(sol.longestCommonPrefix(words));
    // }
}