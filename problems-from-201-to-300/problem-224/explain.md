# Reverse Words in a String

## Yêu cầu bài toán

- Cho một chuỗi ký tự `s`. Hãy đảo ngược thứ tự các **từ** trong chuỗi đó.
- Một từ được định nghĩa là một chuỗi các ký tự không chứa khoảng trắng. Các từ trong `s` được phân cách bởi ít nhất một khoảng trắng.
- Yêu cầu kết quả trả về:
  - Các từ chỉ được cách nhau bởi đúng một khoảng trắng.
  - Không có khoảng trắng thừa ở đầu và cuối chuỗi.

## Ý tưởng cốt lõi

Bài toán này có thể giải quyết nhanh chóng bằng các hàm xử lý chuỗi có sẵn. Các bước chính bao gồm:
1. Loại bỏ các khoảng trắng thừa ở hai đầu chuỗi.
2. Tách chuỗi thành mảng các từ dựa trên khoảng trắng (xử lý cả trường hợp có nhiều khoảng trắng ở giữa).
3. Đảo ngược thứ tự các từ trong mảng và nối chúng lại với nhau bằng một khoảng trắng duy nhất.

## Thuật toán

1. Sử dụng phương thức `trim()` để xóa khoảng trắng ở đầu và cuối chuỗi `s`.
2. Sử dụng `split("\\s+")` để tách chuỗi thành một mảng các chuỗi con. Biểu thức chính quy (Regex) `\\s+` giúp tách chuỗi ở bất kỳ vị trí nào có một hoặc nhiều khoảng trắng liên tiếp.
3. Sử dụng một `StringBuilder` để xây dựng chuỗi kết quả:
   - Duyệt mảng từ cuối lên đầu.
   - Nối từng từ vào `StringBuilder`.
   - Chèn thêm một khoảng trắng giữa các từ (trừ từ cuối cùng).
4. Trả về chuỗi kết quả.

## Độ phức tạp
- **Thời gian**: $O(N)$ - Duyệt qua chuỗi một vài lần để xử lý các bước tách và nối.
- **Không gian**: $O(N)$ - Để lưu trữ mảng các từ và chuỗi kết quả mới.

## Code (Java)

```java
class Solution {
    public String reverseWords(String s) {
        // Loại bỏ khoảng trắng thừa đầu đuôi và tách các từ bằng regex
        String[] words = s.trim().split("\\s+");
        
        StringBuilder result = new StringBuilder();
        
        // Duyệt ngược mảng từ cuối lên đầu
        for (int i = words.length - 1; i >= 0; i--) {
            result.append(words[i]);
            // Thêm khoảng trắng giữa các từ, trừ từ cuối cùng của chuỗi mới
            if (i > 0) {
                result.append(" ");
            }
        }
        return result.toString();
    }
}
```
