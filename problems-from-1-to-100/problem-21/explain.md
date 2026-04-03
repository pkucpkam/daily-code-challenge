# Merge Sorted Array

## 1. Mô tả bài toán
Cho hai mảng số nguyên `nums1` và `nums2` đã được sắp xếp tăng dần, cùng với hai số nguyên `m` và `n` lần lượt là số lượng phần tử thực tế của `nums1` và `nums2`.
Hãy hợp nhất `nums2` vào `nums1` sao cho mảng `nums1` sau khi hợp nhất vẫn giữ được thứ tự sắp xếp tăng dần.
Lưu ý: Mảng `nums1` có độ dài là `m + n`, trong đó `m` phần tử đầu là các số cần hợp nhất, và `n` phần tử cuối được đặt bằng 0 (để chứa kết quả).

## 2. Ý tưởng cốt lõi
- Nếu ta hợp nhất từ đầu mảng (như Merge Sort thông thường), ta sẽ phải dịch chuyển các phần tử của `nums1` ra sau, gây tốn thời gian.
- Ý tưởng tối ưu là **duyệt ngược từ cuối mảng** về đầu.
- So sánh các phần tử lớn nhất của `nums1` và `nums2`, phần tử nào lớn hơn sẽ được đặt vào vị trí cuối cùng còn trống của `nums1`. Điều này giúp ta tận dụng được khoảng trống (`0`) ở cuối `nums1` mà không cần mảng phụ hay dịch chuyển phần tử.

## 3. Giải thích thuật toán
1. Khởi tạo 3 con trỏ:
   - `i = m - 1`: Trỏ vào phần tử thực thụ cuối cùng của `nums1`.
   - `j = n - 1`: Trỏ vào phần tử cuối cùng của `nums2`.
   - `k = m + n - 1`: Trỏ vào vị trí cuối cùng của mảng `nums1` (nơi sẽ đặt kết quả).
2. Dùng vòng lặp chạy khi cả `i` và `j` đều chưa vượt quá chỉ số 0:
   - So sánh `nums1[i]` và `nums2[j]`.
   - Nếu `nums1[i] > nums2[j]`: Đưa `nums1[i]` vào `nums1[k]`, sau đó giảm `i` và `k`.
   - Ngược lại: Đưa `nums2[j]` vào `nums1[k]`, sau đó giảm `j` và `k`.
3. Sau vòng lặp, nếu `j >= 0` (nghĩa là vẫn còn phần tử trong `nums2` chưa được đưa vào), ta chép nốt các phần tử đó vào `nums1`.
4. (Nếu `i >= 0` thì ta không cần làm gì vì các phần tử đó đã nằm sẵn ở đúng vị trí trong `nums1` rồi).

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(m + n)\) - Ta chỉ duyệt qua mỗi phần tử của cả hai mảng tối đa một lần.
- **Không gian (Space Complexity)**: \(O(1)\) - Không sử dụng thêm mảng phụ, mọi thao tác đều thực hiện trực tiếp trên mảng `nums1`.

## 5. Code (Java)
```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1, j = n - 1, k = m + n - 1;
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
        // Nếu nums2 vẫn còn phần tử (nums1 đã hết hoặc nhỏ hơn toàn bộ nums2 còn lại)
        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }
    }
}
```
