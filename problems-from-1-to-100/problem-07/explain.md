## 1. Mô tả bài toán

Bài toán yêu cầu chuyển đổi một chuỗi số La Mã (Roman numeral) thành giá trị số nguyên tương ứng. Các ký hiệu La Mã cơ bản và giá trị của chúng như sau:

*   I: 1
*   V: 5
*   X: 10
*   L: 50
*   C: 100
*   D: 500
*   M: 1000

Quy tắc chung khi viết số La Mã là các ký hiệu có giá trị lớn hơn thường được đặt trước các ký hiệu có giá trị nhỏ hơn và được cộng lại. Ví dụ, `XII` là `X (10) + II (2) = 12`.

Tuy nhiên, có sáu trường hợp đặc biệt sử dụng quy tắc trừ:
*   `I` có thể đứng trước `V` (5) hoặc `X` (10) để tạo thành 4 (`IV`) và 9 (`IX`).
*   `X` có thể đứng trước `L` (50) hoặc `C` (100) để tạo thành 40 (`XL`) và 90 (`XC`).
*   `C` có thể đứng trước `D` (500) hoặc `M` (1000) để tạo thành 400 (`CD`) và 900 (`CM`).

Input là một chuỗi số La Mã hợp lệ, đảm bảo nằm trong phạm vi từ 1 đến 3999.
Output là giá trị số nguyên của chuỗi đó.

## 2. Ý tưởng cốt lõi

Ý tưởng cốt lõi để giải quyết bài toán này là duyệt chuỗi số La Mã từ phải sang trái (tức là từ ký tự cuối cùng đến ký tự đầu tiên). Cách tiếp cận này giúp đơn giản hóa việc xử lý các trường hợp trừ.

Khi duyệt từ phải sang trái, chúng ta so sánh giá trị của ký hiệu hiện tại với giá trị của ký hiệu ngay bên phải nó (mà chúng ta đã xử lý ở bước trước).
*   Nếu giá trị của ký hiệu hiện tại **nhỏ hơn** giá trị của ký hiệu bên phải, điều đó có nghĩa là chúng ta đang gặp một trường hợp trừ (ví dụ: `IV` - khi xử lý `I` thì `V` đã được xử lý, 1 < 5 nên ta trừ 1).
*   Ngược lại, nếu giá trị của ký hiệu hiện tại **lớn hơn hoặc bằng** giá trị của ký hiệu bên phải, thì giá trị của ký hiệu hiện tại được cộng vào tổng.

Bằng cách này, chúng ta không cần phải kiểm tra cụ thể các cặp ký tự như "IV" hay "CM", mà chỉ cần một logic so sánh đơn giản.

## 3. Giải thích thuật toán

Thuật toán được triển khai như sau:

1.  **Khởi tạo:**
    *   Tạo một `HashMap` để lưu trữ ánh xạ từ ký hiệu La Mã sang giá trị số nguyên của nó:
        *   `'I' -> 1`
        *   `'V' -> 5`
        *   `'X' -> 10`
        *   `'L' -> 50`
        *   `'C' -> 100`
        *   `'D' -> 500`
        *   `'M' -> 1000`
    *   Khởi tạo `result = 0`: Biến này sẽ tích lũy tổng giá trị số nguyên.
    *   Khởi tạo `preValue = 0`: Biến này sẽ lưu giá trị của ký hiệu La Mã đã xử lý ở vị trí bên phải liền kề. Khởi tạo bằng 0 đảm bảo rằng ký hiệu cuối cùng bên phải luôn được cộng vào tổng (vì `curValue` sẽ luôn lớn hơn 0).

2.  **Duyệt chuỗi:**
    *   Sử dụng một vòng lặp `for` để duyệt chuỗi `s` từ ký tự cuối cùng (chỉ số `s.length() - 1`) về ký tự đầu tiên (chỉ số `0`).

3.  **Xử lý từng ký tự:**
    *   Trong mỗi lần lặp, lấy ký tự hiện tại `s.charAt(i)`.
    *   Tra cứu giá trị số nguyên của ký tự đó từ `HashMap` và lưu vào biến `curValue`.
    *   **Áp dụng logic cộng/trừ:**
        *   So sánh `curValue` với `preValue`:
            *   Nếu `curValue < preValue`: Điều này cho thấy một trường hợp trừ (ví dụ: `I` trước `V` hoặc `X`). Ta trừ `curValue` khỏi `result`.
            *   Ngược lại (nếu `curValue >= preValue`): Ký hiệu hiện tại được cộng vào tổng. Ta cộng `curValue` vào `result`.
    *   **Cập nhật `preValue`:** Sau khi xử lý ký tự hiện tại, cập nhật `preValue = curValue`. Giá trị này sẽ được sử dụng để so sánh với ký tự tiếp theo ở bên trái.

4.  **Trả về kết quả:**
    *   Sau khi vòng lặp hoàn tất (đã duyệt qua tất cả các ký tự), biến `result` sẽ chứa giá trị số nguyên cuối cùng của số La Mã. Trả về `result`.

**Ví dụ minh họa: Chuyển đổi "MCMXCIV"**

| `i` | `s.charAt(i)` | `curValue` | `preValue` (trước) | So sánh (`curValue < preValue`?) | `result` (cập nhật) | `preValue` (sau) |
| :-- | :------------ | :--------- | :----------------- | :------------------------------- | :------------------ | :--------------- |
| 6   | 'V'           | 5          | 0                  | False (`5 >= 0`)                 | `0 + 5 = 5`         | 5                |
| 5   | 'I'           | 1          | 5                  | True (`1 < 5`)                   | `5 - 1 = 4`         | 1                |
| 4   | 'C'           | 100        | 1                  | False (`100 >= 1`)               | `4 + 100 = 104`     | 100              |
| 3   | 'X'           | 10         | 100                | True (`10 < 100`)                | `104 - 10 = 94`     | 10               |
| 2   | 'M'           | 1000       | 10                 | False (`1000 >= 10`)             | `94 + 1000 = 1094`  | 1000             |
| 1   | 'C'           | 100        | 1000               | True (`100 < 1000`)              | `1094 - 100 = 994`  | 100              |
| 0   | 'M'           | 1000       | 100                | False (`1000 >= 100`)            | `994 + 1000 = 1994` | 1000             |

Kết quả cuối cùng là 1994.

## 4. Độ phức tạp

*   **Độ phức tạp thời gian (Time Complexity):** O(N)
    *   Trong đó N là độ dài của chuỗi số La Mã đầu vào `s`.
    *   Việc khởi tạo `HashMap` với 7 cặp giá trị là hằng số (O(1)).
    *   Vòng lặp duyệt qua chuỗi chạy N lần. Trong mỗi lần lặp, các thao tác như truy cập ký tự (`charAt()`), tra cứu trong `HashMap` (`get()`), so sánh và phép toán số học đều mất thời gian không đổi (O(1)).
    *   Do đó, tổng thời gian thực hiện tỉ lệ thuận với độ dài của chuỗi đầu vào.

*   **Độ phức tạp không gian (Space Complexity):** O(1)
    *   `HashMap` được sử dụng để lưu trữ ánh xạ các ký hiệu La Mã. Số lượng các ký hiệu này là cố định (7 ký hiệu), không phụ thuộc vào độ dài của chuỗi đầu vào. Do đó, không gian mà `HashMap` chiếm là hằng số.
    *   Các biến khác như `result`, `preValue`, `curValue` cũng chỉ chiếm không gian bộ nhớ cố định.
    *   Vì vậy, độ phức tạp không gian là hằng số.

## 5. Code

java
import java.util.HashMap;

public class Solution {
    public int romanToInt(String s) {
        int result = 0;
        int preValue = 0; // Giá trị của ký tự đã xử lý ngay bên phải
        
        // Khởi tạo HashMap để ánh xạ ký hiệu La Mã với giá trị số nguyên
        HashMap<Character, Integer> roman = new HashMap<Character, Integer>();
        roman.put('I', 1);
        roman.put('V', 5);
        roman.put('X', 10);
        roman.put('L', 50);
        roman.put('C', 100);
        roman.put('D', 500);
        roman.put('M', 1000);
        
        // Duyệt chuỗi từ phải sang trái
        for (int i = s.length() - 1; i >= 0; i--) {
            // Lấy giá trị số nguyên của ký tự hiện tại
            int curValue = roman.get(s.charAt(i));
            
            // So sánh giá trị hiện tại với giá trị của ký tự bên phải (preValue)
            if (curValue < preValue) {
                // Nếu curValue nhỏ hơn preValue, đây là trường hợp trừ (ví dụ: IV, IX)
                result -= curValue;
            } else {
                // Ngược lại, cộng giá trị hiện tại vào tổng
                result += curValue;
            }
            // Cập nhật preValue cho lần lặp tiếp theo
            preValue = curValue;
        }
        
        return result;
    }
}