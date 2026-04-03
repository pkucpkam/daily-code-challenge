## 1. Mô tả bài toán

Bài toán yêu cầu chúng ta tìm kiếm vị trí xuất hiện đầu tiên của một chuỗi con (`needle`) trong một chuỗi lớn hơn (`haystack`).
Nếu chuỗi `needle` được tìm thấy trong `haystack`, hãy trả về chỉ số (index) đầu tiên mà nó bắt đầu. Nếu `needle` không phải là một phần của `haystack`, hãy trả về `-1`.

**Ví dụ:**
*   `haystack = "sadbutsad"`, `needle = "sad"` => Output: `0` (vì "sad" xuất hiện lần đầu tiên ở chỉ số 0).
*   `haystack = "leetcode"`, `needle = "leeto"` => Output: `-1` (vì "leeto" không xuất hiện trong "leetcode").

**Ràng buộc:**
*   Độ dài của cả `haystack` và `needle` nằm trong khoảng từ 1 đến 10^4.
*   Cả hai chuỗi chỉ chứa các ký tự chữ cái tiếng Anh viết thường.

## 2. Ý tưởng cốt lõi

Ý tưởng cốt lõi của giải pháp này là tận dụng tối đa các hàm tiện ích có sẵn trong thư viện chuẩn của ngôn ngữ lập trình. Cụ thể, trong Java, lớp `String` cung cấp một phương thức `indexOf(String str)` được thiết kế chính xác để giải quyết bài toán này. Phương thức này sẽ tìm kiếm sự xuất hiện đầu tiên của chuỗi con `str` (tức `needle`) trong chuỗi gọi phương thức (`haystack`) và trả về chỉ số của nó, hoặc `-1` nếu không tìm thấy.

Việc sử dụng hàm có sẵn giúp chúng ta tránh phải tự triển khai một thuật toán tìm kiếm chuỗi phức tạp (như Naive, KMP, Boyer-Moore) mà vẫn đảm bảo hiệu suất tốt vì các hàm thư viện thường được tối ưu hóa cao.

## 3. Giải thích thuật toán

Thuật toán được triển khai vô cùng đơn giản và trực tiếp:

1.  Chúng ta có hai chuỗi đầu vào: `haystack` (chuỗi lớn) và `needle` (chuỗi con cần tìm).
2.  Trên đối tượng `haystack` (là một thể hiện của lớp `String` trong Java), chúng ta gọi phương thức `indexOf()`.
3.  Truyền chuỗi `needle` làm đối số cho phương thức `indexOf()`: `haystack.indexOf(needle)`.
4.  Phương thức `indexOf(needle)` sẽ thực hiện các bước sau (chi tiết bên trong phương thức được ẩn đi):
    *   Nó duyệt qua chuỗi `haystack`, bắt đầu từ chỉ số 0.
    *   Tại mỗi vị trí, nó cố gắng so khớp `needle` với phần chuỗi con của `haystack` bắt đầu từ vị trí đó.
    *   Ngay khi tìm thấy một vị trí mà `needle` hoàn toàn khớp, nó sẽ trả về chỉ số bắt đầu của vị trí đó.
    *   Nếu nó duyệt hết `haystack` mà không tìm thấy bất kỳ sự khớp nào của `needle`, nó sẽ trả về giá trị `-1`.
5.  Giá trị trả về từ `haystack.indexOf(needle)` chính là kết quả cuối cùng của bài toán, và chúng ta trả về giá trị đó.

Đây là một giải pháp sử dụng API có sẵn, nên không cần triển khai thuật toán tìm kiếm chuỗi từ đầu.

## 4. Độ phức tạp

*   **Độ phức tạp thời gian (Time Complexity)**:
    *   Phụ thuộc vào cách triển khai nội bộ của phương thức `String.indexOf()` trong Java. Mặc dù triển khai cụ thể có thể khác nhau giữa các phiên bản JVM, nhưng các triển khai hiện đại thường sử dụng các thuật toán tìm kiếm chuỗi tối ưu như Boyer-Moore hoặc các biến thể của nó.
    *   Trong trường hợp tệ nhất (ví dụ: `haystack = "aaaaab"`, `needle = "aaab"`), độ phức tạp của một thuật toán tìm kiếm chuỗi ngây thơ có thể lên tới O(N * M), trong đó N là độ dài của `haystack` và M là độ dài của `needle`.
    *   Tuy nhiên, với các thuật toán tối ưu hóa, độ phức tạp trung bình thường gần với O(N + M) hoặc thậm chí tốt hơn, O(N / M) trong các trường hợp thực tế. Ngay cả trong trường hợp tệ nhất, các thuật toán như Boyer-Moore vẫn thường hoạt động tốt hơn đáng kể so với phương pháp ngây thơ.
    *   Với ràng buộc `N, M <= 10^4`, giải pháp sử dụng `indexOf` là rất hiệu quả và đủ nhanh.

*   **Độ phức tạp không gian (Space Complexity)**:
    *   O(1) (Không gian phụ trợ). Phương thức `indexOf()` không yêu cầu sử dụng thêm bộ nhớ đáng kể nào mà phụ thuộc vào kích thước của chuỗi đầu vào. Nó chỉ cần một vài biến để quản lý các chỉ số và so sánh.

## 5. Code

java
class Solution {
    public int strStr(String haystack, String needle) {
        // Sử dụng phương thức indexOf có sẵn trong lớp String của Java
        // để tìm chỉ số của lần xuất hiện đầu tiên của needle trong haystack.
        // Phương thức này trả về chỉ số nếu tìm thấy, hoặc -1 nếu không tìm thấy.
        return haystack.indexOf(needle);
    }
}