# Add Digits

## 1. Mô tả bài toán
Cho một số nguyên `num`. Hãy thực hiện cộng tất cả các chữ số của nó lại với nhau. Nếu kết quả vẫn có từ hai chữ số trở lên, tiếp tục lặp lại quá trình đó cho đến khi kết quả chỉ còn một chữ số duy nhất. Trả về chữ số đó.
Ví dụ:
- `38 -> 3 + 8 = 11 -> 1 + 1 = 2`. Kết quả trả về là `2`.

## 2. Ý tưởng cốt lõi
- **Cách 1 (Vòng lặp)**: Sử dụng vòng lặp lồng nhau. Vòng lặp ngoài kiểm tra xem số đã là 1 chữ số chưa. Vòng lặp trong thực hiện việc tính tổng các chữ số.
- **Cách 2 (Toán học - Digital Root)**: Có một công thức toán học để tính kết quả này chỉ trong $O(1)$ mà không cần vòng lặp:
    - Nếu `num == 0`, kết quả là `0`.
    - Nếu `num % 9 == 0`, kết quả là `9`.
    - Ngược lại, kết quả là `num % 9`.

## 3. Giải thích thuật toán
*(Dựa theo giải pháp lặp trong file Solution.java)*:
1. Sử dụng vòng lặp `while (num >= 10)` để đảm bảo quá trình tiếp tục khi số vẫn còn lớn hơn 9.
2. Khởi tạo `sum = 0`.
3. Dùng vòng lặp con để tách từng chữ số (`num % 10`) và cộng dồn vào `sum`.
4. Gán `num = sum` để bắt đầu vòng lặp tiếp theo với tổng vừa tính.
5. Khi kết thúc, `num` sẽ là số có 1 chữ số.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(\log n)\) - Phụ thuộc vào số lượng chữ cái của các tổng trung gian. Thực tế số bước lặp rất nhỏ.
- **Không gian (Space Complexity)**: \(O(1)\) - Chỉ dùng một vài biến số nguyên đơn giản.

## 5. Code (Java)
```java
class Solution {
    public int addDigits(int num) {
        // Thực hiện lặp cho tới khi num chỉ còn 1 chữ số
        while (num >= 10) {
            int sum = 0;
            // Tính tổng các chữ số
            while (num > 0) {
                sum += num % 10;
                num /= 10;
            }
            num = sum;
        }
        return num;
    }
}
```
*(Mẹo tối ưu toán học: return num == 0 ? 0 : 1 + (num - 1) % 9;)*
