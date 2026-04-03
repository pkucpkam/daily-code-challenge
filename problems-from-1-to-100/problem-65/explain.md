# Word Pattern

## 1. Mô tả bài toán
Cho một chuỗi mẫu `pattern` và một chuỗi các từ `s`. Hãy kiểm tra xem `s` có tuân theo đúng quy luật của `pattern` hay không.
"Tuân theo đúng quy luật" nghĩa là tồn tại một ánh xạ song ánh (bijection) giữa các ký tự trong mẫu và các từ trong chuỗi:
- Mỗi ký tự trong mẫu ánh xạ tới đúng một từ duy nhất trong `s`.
- Mỗi từ duy nhất trong `s` ánh xạ ngược lại tới đúng một ký tự trong mẫu.
Ví dụ: `pattern = "abba"`, `s = "dog cat cat dog"` -> `true`.

## 2. Ý tưởng cốt lõi
- Tương tự bài toán kiểm tra chuỗi đẳng cấu (Isomorphic Strings), chúng ta cần đảm bảo mối quan hệ 1-1 giữa ký tự và từ.
- Ta sử dụng hai cấu trúc dữ liệu **HashMap** để lưu trữ ánh xạ hai chiều:
    - `charToWord`: Ánh xạ từ ký tự (Character) sang từ (String).
    - `wordToChar`: Ánh xạ từ từ (String) sang ký tự (Character).

## 3. Giải thích thuật toán
1. Tách chuỗi `s` thành một mảng các từ dựa trên dấu cách: `String[] words = s.split(" ")`.
2. Kiểm tra độ dài: Nếu số lượng ký tự trong `pattern` khác số lượng từ trong `words`, trả về `false`.
3. Duyệt qua từng cặp ký tự `c` và từ `word` tại cùng một chỉ số `i`:
   - Kiểm tra `charToWord`: 
     - Nếu `c` đã tồn tại nhưng không ánh xạ tới `word` hiện tại -> Trả về `false`.
     - Nếu chưa tồn tại, thêm cặp `(c, word)`.
   - Kiểm tra `wordToChar`:
     - Nếu `word` đã tồn tại nhưng không ánh xạ tới ký tự `c` hiện tại -> Trả về `false`.
     - Nếu chưa tồn tại, thêm cặp `(word, c)`.
4. Nếu đi hết vòng lặp, trả về `true`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(N)\) - Với `N` là tổng số ký tự trong chuỗi `s` (do thao tác `split` và duyệt mảng).
- **Không gian (Space Complexity)**: \(O(M)\) - Với `M` là số lượng từ/ký tự duy nhất được lưu trữ trong HashMap.

## 5. Code (Java)
```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");
        // Điều kiện cần: Số lượng ký tự mẫu phải bằng số lượng từ
        if (pattern.length() != words.length) {
            return false;
        }

        Map<Character, String> charToWord = new HashMap<>();
        Map<String, Character> wordToChar = new HashMap<>();

        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            String word = words[i];

            // Kiểm tra ánh xạ từ ký tự sang từ
            if (charToWord.containsKey(c)) {
                if (!charToWord.get(c).equals(word)) return false;
            } else {
                charToWord.put(c, word);
            }

            // Kiểm tra ánh xạ từ từ sang ký tự
            if (wordToChar.containsKey(word)) {
                if (wordToChar.get(word) != c) return false;
            } else {
                wordToChar.put(word, c);
            }
        }

        return true;
    }
}
```
