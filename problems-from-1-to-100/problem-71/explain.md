# Reverse String

## 1. Mô tả bài toán
Cho một mảng các ký tự `s`. Hãy viết một hàm để đảo ngược thứ tự các ký tự trong mảng đó.
Yêu cầu:
- Phải thực hiện trực tiếp trên mảng đầu vào (**In-place**).
- Sử dụng thêm bộ nhớ với độ phức tạp $O(1)$.

## 2. Ý tưởng cốt lõi
- Để đảo ngược một mảng, ta hoán đổi phần tử đầu tiên với phần tử cuối cùng, phần tử thứ hai với phần tử áp chót, và cứ tiếp tục như vậy cho đến khi chạm tới giữa mảng.
- Sử dụng kỹ thuật **Hai con trỏ (Two Pointers)**:
    - Một con trỏ `left` bắt đầu từ chỉ số 0.
    - Một con trỏ `right` bắt đầu từ chỉ số cuối cùng `s.length - 1`.

## 3. Giải thích thuật toán
1. Khởi tạo `left = 0` và `right = s.length - 1`.
2. Trong khi `left < right`:
   - Sử dụng một biến tạm `temp` để lưu giá trị của `s[left]`.
   - Gán `s[left] = s[right]`.
   - Gán `s[right] = temp`.
   - Tăng `left` lên 1 và giảm `right` đi 1 để xét cặp ký tự tiếp theo.
3. Khi `left >= right`, toàn bộ mảng đã được đảo ngược.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Ta thực hiện \(n/2\) phép hoán đổi, trong đó `n` là độ dài của mảng.
- **Không gian (Space Complexity)**: \(O(1)\) - Chỉ sử dụng một biến tạm duy nhất để hoán đổi.

## 5. Code (Java)
```java
class Solution {
    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        
        // Hai con trỏ đi từ hai đầu vào giữa
        while (left < right) {
            // Hoán đổi giá trị
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            
            // Di chuyển con trỏ
            left++;
            right--;
        }
    }
}
```
*(Ghi chú: Đây là phương pháp đảo ngược mảng cơ bản và hiệu quả nhất).*
