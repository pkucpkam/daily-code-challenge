## 1. Mô tả bài toán

Bài toán yêu cầu chúng ta tìm độ dài của từ cuối cùng trong một chuỗi (`s`).
Một "từ" được định nghĩa là một chuỗi con liên tiếp các ký tự không phải là khoảng trắng. Chuỗi đầu vào `s` sẽ chỉ bao gồm các chữ cái tiếng Anh và các ký tự khoảng trắng (' '). Đề bài cũng đảm bảo rằng sẽ luôn có ít nhất một từ trong chuỗi.

**Ví dụ:**
*   `s = "Hello World"` -> Từ cuối cùng là "World", độ dài: 5.
*   `s = "   fly me   to   the moon  "` -> Từ cuối cùng là "moon", độ dài: 4.
*   `s = "luffy is still joyboy"` -> Từ cuối cùng là "joyboy", độ dài: 6.

## 2. Ý tưởng cốt lõi

Ý tưởng cốt lõi để giải quyết bài toán này là duyệt chuỗi từ cuối về đầu. Trước tiên, chúng ta loại bỏ các khoảng trắng thừa ở đầu và cuối chuỗi để đảm bảo rằng chúng ta chỉ tập trung vào các từ thực sự và không bị ảnh hưởng bởi các khoảng trắng không cần thiết ở cuối. Sau đó, chúng ta bắt đầu đếm số ký tự không phải khoảng trắng từ vị trí cuối cùng của chuỗi đã được xử lý cho đến khi gặp ký tự khoảng trắng đầu tiên hoặc đến đầu chuỗi.

## 3. Giải thích thuật toán

Thuật toán hoạt động theo các bước sau:

1.  **Tiền xử lý chuỗi:** Sử dụng phương thức `trim()` của chuỗi để loại bỏ tất cả các khoảng trắng ở đầu và cuối chuỗi `s`. Việc này rất quan trọng vì nó đảm bảo rằng nếu chuỗi kết thúc bằng một hoặc nhiều khoảng trắng (ví dụ: `"abc   "`), thì từ cuối cùng sẽ được xác định chính xác mà không cần xử lý đặc biệt cho các khoảng trắng thừa này. Kết quả của `trim()` sẽ được gán lại vào biến `s`.
    *Ví dụ: "   fly me   to   the moon  " sẽ trở thành "fly me   to   the moon"*

2.  **Khởi tạo bộ đếm:** Khởi tạo một biến `count` bằng `0`. Biến này sẽ lưu trữ độ dài của từ cuối cùng.

3.  **Duyệt ngược chuỗi:** Bắt đầu một vòng lặp từ chỉ số cuối cùng của chuỗi `s` (tức là `s.length() - 1`) và đi ngược về đầu chuỗi (đến chỉ số `0`).

4.  **Kiểm tra ký tự:** Trong mỗi lần lặp:
    *   **Nếu ký tự hiện tại là khoảng trắng (`' '`)**: Điều này có nghĩa là chúng ta đã tìm thấy điểm bắt đầu của từ cuối cùng (tức là ranh giới bên trái của từ). Vì chúng ta đang duyệt ngược, khi gặp khoảng trắng đầu tiên, `count` đã lưu trữ đúng độ dài của từ cuối cùng. Do đó, chúng ta có thể trả về giá trị của `count` ngay lập tức.
    *   **Nếu ký tự hiện tại không phải là khoảng trắng**: Điều này có nghĩa là ký tự này là một phần của từ cuối cùng. Tăng biến `count` lên `1`.

5.  **Trường hợp đặc biệt (một từ):** Nếu vòng lặp kết thúc mà không có ký tự khoảng trắng nào được tìm thấy (tức là toàn bộ chuỗi sau khi `trim()` chỉ chứa một từ duy nhất), thì `count` sẽ chứa độ dài của từ đó. Trong trường hợp này, vòng lặp sẽ hoàn thành và chúng ta sẽ trả về giá trị cuối cùng của `count`.

## 4. Độ phức tạp

*   **Độ phức tạp thời gian (Time Complexity):**
    *   `s.trim()`: Phương thức `trim()` trong Java có độ phức tạp thời gian là O(N), trong đó N là độ dài của chuỗi, vì nó có thể cần duyệt qua toàn bộ chuỗi để tìm các khoảng trắng ở đầu và cuối, và tạo một chuỗi mới.
    *   Vòng lặp: Duyệt chuỗi từ cuối về đầu. Trong trường hợp xấu nhất, vòng lặp có thể duyệt qua toàn bộ chuỗi (khi chuỗi chỉ có một từ hoặc từ cuối cùng rất dài). Do đó, độ phức tạp của vòng lặp là O(N).
    *   Tổng cộng, độ phức tạp thời gian của thuật toán là **O(N)**, với N là độ dài ban đầu của chuỗi `s`.

*   **Độ phức tạp không gian (Space Complexity):**
    *   `s.trim()`: Phương thức `trim()` trong Java tạo ra một chuỗi mới. Trong trường hợp xấu nhất, nếu không có khoảng trắng nào ở đầu hoặc cuối cần loại bỏ, chuỗi mới vẫn được tạo ra và có độ dài bằng N. Do đó, nó sử dụng **O(N)** không gian phụ trợ.
    *   Biến `count`: Chỉ sử dụng một lượng không gian cố định (một biến số nguyên), tức là O(1).
    *   Tổng cộng, độ phức tạp không gian của thuật toán là **O(N)** do việc tạo chuỗi mới bởi `s.trim()`. Nếu không tính đến việc `trim()` tạo chuỗi mới và chỉ xem xét không gian *thực sự phụ trợ* được sử dụng bởi logic của thuật toán (ngoài các thao tác chuỗi tiêu chuẩn), thì đó là O(1). Tuy nhiên, vì `trim()` *thực sự* cấp phát bộ nhớ cho một chuỗi mới, O(N) là đánh giá chính xác hơn cho không gian tổng thể.

## 5. Code

java
class Solution {
    public int lengthOfLastWord(String s) {
        int count = 0;
        // Loại bỏ khoảng trắng ở đầu và cuối chuỗi
        s = s.trim();
        
        // Duyệt chuỗi từ cuối về đầu
        for (int i = s.length() - 1; i >= 0; i--) {
            // Nếu gặp khoảng trắng, tức là đã tìm thấy từ cuối cùng
            if (s.charAt(i) == ' ') {
                return count; // Trả về độ dài đã đếm
            } else {
                // Nếu không phải khoảng trắng, tăng bộ đếm
                count++;
            }
        }
        // Trường hợp chuỗi chỉ có một từ (sau khi trim),
        // vòng lặp kết thúc mà không gặp khoảng trắng nào
        return count;
    }
}