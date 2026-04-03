# Longest Palindrome

## 1. Mô tả bài toán
Cho một chuỗi `s` gồm các chữ cái viết hoa và viết thường. Hãy tính độ dài lớn nhất của một **chuỗi đối xứng (palindrome)** có thể được tạo ra bằng cách sắp xếp lại các chữ cái từ chuỗi `s`.
Lưu ý: Phân biệt chữ hoa và chữ thường (ví dụ: "Aa" không phải là palindrome).

## 2. Ý tưởng cốt lõi
- Một chuỗi đối xứng là chuỗi mà các ký tự xuất hiện theo từng cặp đối xứng qua tâm.
- Để tạo ra chuỗi đối xứng dài nhất:
    - Các ký tự có số lần xuất hiện là số chẵn (`2, 4, 6...`) đều có thể dùng hết.
    - Các ký tự có số lần xuất hiện là số lẻ (`3, 5...`), ta có thể lấy phần chẵn của chúng (ví dụ: có 3 chữ 'a', ta lấy 2 chữ 'a').
    - Cuối cùng, nếu còn ít nhất một ký tự dư ra (số lần lẻ), ta có thể đặt **duy nhất một** ký tự lẻ đó vào chính giữa của chuỗi để làm tâm đối xứng.

## 3. Giải thích thuật toán
1. Sử dụng mảng tần suất `charCount[128]` để đếm số lần xuất hiện của từng ký tự ASCII.
2. Khởi tạo `length = 0` và biến cờ `hasOdd = false`.
3. Duyệt qua mảng tần suất:
   - Nếu số lượng `count` là chẵn: Cộng trực tiếp vào `length`.
   - Nếu số lượng `count` là lẻ: Cộng `count - 1` vào `length` và đánh dấu `hasOdd = true`.
4. Sau khi duyệt hết, nếu `hasOdd` là đúng, cộng thêm 1 vào `length` (cho ký tự nằm ở trung tâm).
5. Trả về `length`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Với `n` là độ dài chuỗi đầu vào. Ta duyệt mảng ký tự một lần để đếm và duyệt mảng tần suất cố định (128 phần tử).
- **Không gian (Space Complexity)**: \(O(1)\) - Sử dụng mảng tần suất có kích thước cố định là 128.

## 5. Code (Java)
```java
class Solution {
    public int longestPalindrome(String s) {
        // Đếm số lần xuất hiện của mỗi ký tự (gồm cả hoa và thường)
        int[] charCount = new int[128];
        for (char c : s.toCharArray()) {
            charCount[c]++;
        }

        int length = 0;
        boolean hasOdd = false;

        for (int count : charCount) {
            // Thêm số lượng chẵn tối đa có thể của mỗi ký tự
            length += (count / 2) * 2;
            // Kiểm tra xem có ký tự nào xuất hiện lẻ lần không để đặt vào chính giữa
            if (count % 2 != 0) {
                hasOdd = true;
            }
        }

        // Nếu có ít nhất một ký tự lẻ, ta có thể cho một ký tự vào giữa
        return hasOdd ? length + 1 : length;
    }
}
```
