# License Key Formatting

## 1. Mô tả bài toán
Cho một chuỗi `s` đại diện cho mã bản quyền (license key) gồm các ký tự chữ số, chữ cái và dấu gạch ngang `-`. Bạn được cho thêm một số nguyên `k`.
Chúng ta cần định dạng lại chuỗi sao cho:
- Mỗi nhóm chứa đúng `k` ký tự, ngoại trừ nhóm đầu tiên có thể ngắn hơn nhưng phải có ít nhất 1 ký tự.
- Giữa các nhóm phải có dấu gạch ngang `-`.
- Tất cả các chữ cái phải được chuyển thành chữ hoa.

## 2. Ý tưởng cốt lõi
- Vì nhóm đầu tiên là nhóm duy nhất có thể có độ dài linh hoạt, cách tiếp cận dễ nhất là **duyệt ngược chuỗi** từ cuối lên đầu.
- Khi duyệt ngược, ta cứ gom đủ `k` ký tự (không tính dấu `-`) thì thêm một dấu `-` mới.
- Cuối cùng, ta đảo ngược lại chuỗi để có kết quả đúng.

## 3. Giải thích thuật toán
1. Khởi tạo `StringBuilder` để xây dựng chuỗi.
2. Khởi tạo biến đếm `count = 0`.
3. Duyệt chuỗi `s` từ cuối về đầu:
   - Nếu ký tự hiện tại khác `-`:
     - Nếu đã đếm đủ `count == k`: Thêm dấu `-` vào `StringBuilder` và reset `count = 0`.
     - Thêm ký tự hiện tại (sau khi đã viết hoa) vào `StringBuilder`.
     - Tăng `count` lên 1.
4. Đảo ngược chuỗi thu được bằng `sb.reverse().toString()`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Với $n$ là độ dài chuỗi ban đầu. Ta duyệt qua mỗi ký tự một lần.
- **Không gian (Space Complexity)**: \(O(n)\) - Để lưu trữ kết quả trong StringBuilder.

## 5. Code (Java)
```java
class Solution {
    public String licenseKeyFormatting(String s, int k) {
        StringBuilder sb = new StringBuilder();
        int count = 0;

        // Duyệt ngược từ cuối chuỗi lên đầu
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c != '-') {
                // Nếu đã đủ một nhóm k ký tự, thêm dấu gạch ngang
                if (count == k) {
                    sb.append('-');
                    count = 0;
                }
                // Thêm ký tự đã được viết hoa
                sb.append(Character.toUpperCase(c));
                count++;
            }
        }

        // Đảo ngược lại vì ta duyệt từ cuối
        return sb.reverse().toString();
    }
}
```
