# Majority Element

## 1. Mô tả bài toán
Cho một mảng `nums` có kích thước `n`. Hãy tìm **phần tử đa số (majority element)**.
Phần tử đa số là phần tử xuất hiện nhiều hơn \(\lfloor n/2 \rfloor\) lần trong mảng. Giả định rằng phần tử đa số luôn luôn tồn tại trong mảng.

## 2. Ý tưởng cốt lõi
- Có nhiều cách để giải bài này (Sắp xếp, HashMap), nhưng cách tối ưu nhất về cả thời gian và không gian là **Thuật toán bầu chọn của Boyer-Moore (Boyer-Moore Voting Algorithm)**.
- Ý tưởng của thuật toán là: Ta duy trì một "ứng cử viên" (`candidate`) và một biến đếm (`count`).
- Nếu số tiếp theo giống ứng cử viên, ta tăng phiếu bầu. Nếu khác, ta hủy một phiếu bầu.
- Vì phần tử đa số chiếm hơn một nửa mảng, sau khi "hủy" lẫn nhau với các phần tử khác, nó vẫn sẽ là người cuối cùng còn trụ lại.

## 3. Giải thích thuật toán
1. Khởi tạo `count = 0` và `candidate = null`.
2. Duyệt qua từng số `num` trong mảng `nums`:
   - Nếu `count == 0`: Chọn `num` hiện tại làm `candidate`.
   - Cập nhật `count`: Nếu `num == candidate` thì `count++`, ngược lại `count--`.
3. Kết thúc vòng lặp, `candidate` chính là phần tử đa số cần tìm.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(n)\) - Ta chỉ duyệt qua mảng một lần duy nhất.
- **Không gian (Space Complexity)**: \(O(1)\) - Chỉ sử dụng hai biến đơn giản để lưu trữ trạng thái bầu chọn.

## 5. Code (Java)
```java
class Solution {
    public int majorityElement(int[] nums) {
        int count = 0;
        Integer candidate = null;

        for (int num : nums) {
            // Khi phiếu bầu về 0, chọn ứng cử viên mới
            if (count == 0) {
                candidate = num;
            }
            // Nếu trùng ứng cử viên thì tăng count, khác thì giảm count
            count += (num == candidate) ? 1 : -1;
        }

        return candidate;   
    }
}
```
