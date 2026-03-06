# Candy

## Yêu cầu bài toán

- Có `n` học sinh đứng thành một hàng. Mức điểm xếp hạng (rating) của mỗi em được cho trong mảng `ratings`.
- Bạn cần phân phát kẹo cho học sinh theo hai quy tắc sau:
  - Mỗi học sinh phải có ít nhất **1 viên kẹo**.
  - Học sinh có mức xếp hạng cao hơn học sinh lân cận (trái hoặc phải) phải nhận được **nhiều kẹo hơn** học sinh đó.
- Mục tiêu: Tính số lượng kẹo tối thiểu cần thiết để thỏa mãn các điều kiện trên.

## Ý tưởng cốt lõi

Bài toán có thể giải quyết hiệu quả bằng thuật toán **Tham lam (Greedy)** với độ phức tạp $O(N)$.
- Ý tưởng chính là quan sát sự thay đổi của mức xếp hạng: các đoạn dốc lên (rating tăng dần) và các đoạn dốc xuống (rating giảm dần).
- Khi xếp hạng tăng: Số kẹo của học sinh sau sẽ bằng số kẹo của học sinh trước cộng thêm 1.
- Khi xếp hạng giảm: Chúng ta cần đảm bảo học sinh trước có nhiều kẹo hơn học sinh sau. Nếu dốc xuống kéo dài, ta có thể cần quay lại tăng thêm kẹo cho các học sinh ở đỉnh dốc để thỏa mãn điều kiện lân cận.

## Thuật toán (Duyệt một lần)

Chúng ta có thể sử dụng các biến để theo dõi trạng thái của dốc mà không cần mảng phụ:
1. **Khởi tạo**: Học sinh đầu tiên luôn nhận ít nhất 1 viên kẹo. Biến `total = 1`.
2. **Theo dõi các trạng thái**:
   - `up`: Độ dài của đoạn dốc lên hiện tại.
   - `down`: Độ dài của đoạn dốc xuống hiện tại.
   - `peak`: Giá trị kẹo tại đỉnh dốc cao nhất vừa đạt được.
3. **Duyệt qua mảng ratings từ phần tử thứ hai**:
   - **Trường hợp Rating tăng (`ratings[i] > ratings[i-1]`)**:
     - Tăng `up`, đặt `down = 0`.
     - Cập nhật `peak = up`.
     - Cộng vào tổng: `1 + up` viên kẹo.
   - **Trường hợp Rating bằng (`ratings[i] == ratings[i-1]`)**:
     - Reset các trạng thái `up = down = peak = 0`.
     - Học sinh này chỉ cần 1 viên kẹo: `total += 1`.
   - **Trường hợp Rating giảm (`ratings[i] < ratings[i-1]`)**:
     - Reset `up = 0`, tăng `down`.
     - Cộng vào tổng: `down` viên kẹo (đại diện cho việc tăng 1 viên cho tất cả các em trong đoạn dốc xuống để đảm bảo tính liên tục).
     - **Đặc biệt**: Nếu đoạn dốc xuống `down` vượt quá chiều cao của đỉnh `peak`, ta phải cộng thêm 1 viên vào `total` để bù cho đỉnh dốc cũ.

## Độ phức tạp
- **Thời gian**: $O(N)$ - Duyệt mảng một lần duy nhất.
- **Không gian**: $O(1)$ - Chỉ sử dụng một vài biến hỗ trợ.

## Code (Java)

```java
class Solution {
    public int candy(int[] ratings) {
        if (ratings == null || ratings.length == 0)
            return 0;
        int n = ratings.length;
        long total = 1;
        int up = 0;
        int down = 0;
        int peak = 0;

        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                up++;
                peak = up;
                down = 0;
                total += 1 + up;
            } else if (ratings[i] == ratings[i - 1]) {
                up = down = peak = 0;
                total += 1;
            } else {
                up = 0;
                down++;
                total += down;
                if (down > peak) {
                    total += 1;
                }
            }
        }
        return (int) total;
    }
}
```
