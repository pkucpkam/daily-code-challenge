# Single Number

## 1. Mô tả bài toán
Cho một mảng các số nguyên không rỗng `nums`. Trong mảng này, mọi phần tử đều xuất hiện 2 lần, ngoại trừ một phần tử duy nhất xuất hiện 1 lần. Hãy tìm phần tử đó.
Yêu cầu: Thuật toán phải có độ phức tạp thời gian tuyến tính $O(n)$ và chỉ sử dụng không gian bộ nhớ phụ hằng số $O(1)$.

## 2. Ý tưởng cốt lõi
- Chúng ta có thể sử dụng phép toán logic **XOR (Exclusive OR)**.
- Tính chất của phép toán XOR:
    1. \(a \oplus a = 0\) (Một số XOR với chính nó bằng 0).
    2. \(a \oplus 0 = a\) (Một số XOR với 0 bằng chính nó).
    3. Phép XOR có tính giao hoán và kết hợp: \(a \oplus b \oplus a = (a \oplus a) \oplus b = 0 \oplus b = b\).
- Vì vậy, nếu ta XOR toàn bộ các phần tử trong mảng, các cặp số giống nhau sẽ triệt tiêu nhau về 0, cuối cùng chỉ còn lại số xuất hiện một lần duy nhất.

## 3. Giải thích thuật toán
1. Khởi tạo một biến `result = 0`.
2. Duyệt qua từng số `num` trong mảng `nums`.
3. Thực hiện phép toán: `result = result ^ num` (XOR).
4. Sau khi kết thúc vòng lặp, giá trị của `result` chính là số cần tìm.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Ta chỉ duyệt qua mảng `nums` đúng một lần.
- **Không gian (Space Complexity)**: \(O(1)\) - Ta chỉ sử dụng một biến duy nhất để lưu trữ kết quả trung gian.

## 5. Code (Java)
```java
class Solution {
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            // Sử dụng phép toán XOR để triệt tiêu các cặp số giống nhau
            result ^= num;
        }
        return result; 
    }
}
```
