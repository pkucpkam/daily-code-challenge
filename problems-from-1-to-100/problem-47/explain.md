# Isomorphic Strings

## 1. Mô tả bài toán
Cho hai chuỗi `s` và `t`, hãy xác định xem chúng có **đẳng cấu (isomorphic)** hay không.
Hai chuỗi được gọi là đẳng cấu nếu các ký tự trong `s` có thể được thay thế để tạo thành `t`. 
Quy tắc:
- Mọi ký tự xuất hiện của một ký tự phải được thay thế bằng một ký tự khác mà vẫn giữ nguyên thứ tự.
- Không có hai ký tự nào được ánh xạ tới cùng một ký tự.
- Một ký tự có thể ánh xạ tới chính nó.

## 2. Ý tưởng cốt lõi
- Để đảm bảo tính ánh xạ 1-1 (bijective mapping), ta cần kiểm tra theo cả hai chiều:
    1. Một ký tự trong `s` chỉ được ánh xạ tới duy nhất một ký tự trong `t`.
    2. Một ký tự trong `t` cũng chỉ được ánh xạ từ duy nhất một ký tự trong `s`.
- Ta có thể sử dụng hai bảng băm (HashMap) để lưu trữ ánh xạ xuôi và ngược.

## 3. Giải thích thuật toán
1. Khởi tạo hai HashMap: `mapST` (ánh xạ từ s sang t) và `mapTS` (ánh xạ từ t sang s).
2. Duyệt qua từng chỉ số `i` từ 0 đến độ dài chuỗi:
   - Lấy ký tự `charS = s.charAt(i)` và `charT = t.charAt(i)`.
   - Kiểm tra ánh xạ từ `s` sang `t`: Nếu `charS` đã có trong `mapST` nhưng giá trị ánh xạ của nó không phải `charT` -> Trả về `false`.
   - Kiểm tra ánh xạ từ `t` sang `s`: Nếu `charT` đã có trong `mapTS` nhưng giá trị ánh xạ của nó không phải `charS` -> Trả về `false`.
   - Nếu chưa có, thêm cả hai ánh xạ vào hai map.
3. Nếu đi hết vòng lặp mà không vi phạm quy tắc, trả về `true`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Duyệt qua chuỗi một lần duy nhất.
- **Không gian (Space Complexity)**: \(O(K)\) - Với `K` là kích thước bộ ký tự (tối đa 256 đối với mã ASCII). Có thể coi là \(O(1)\).

## 5. Code (Java)
```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean isIsomorphic(String s, String t) {
        // Hai map để đảm bảo ánh xạ 1-1
        Map<Character, Character> mapST = new HashMap<>();
        Map<Character, Character> mapTS = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char charS = s.charAt(i);
            char charT = t.charAt(i);

            // Kiểm tra mâu thuẫn ánh xạ
            if (mapST.containsKey(charS) && mapST.get(charS) != charT) {
                return false;
            }
            if (mapTS.containsKey(charT) && mapTS.get(charT) != charS) {
                return false;
            }

            mapST.put(charS, charT);
            mapTS.put(charT, charS);
        }

        return true;
    }
}
```
