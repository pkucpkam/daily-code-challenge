# Gas Station

## Yêu cầu bài toán

- Có `n` cây xăng dọc theo một cung đường vòng tròn khép kín. Ở cây xăng thứ `i` cung cấp cho bạn lượng xăng là `gas[i]`.
- Bạn có một xe ô tô bình chứa không giới hạn xăng. Để đi từ cây xăng `i` sang cây `i+1` kế tiếp, xe bạn thụt giảm mất `cost[i]` lít xăng.
- Câu hỏi: Bạn muốn thực hiện trọn vẹn 1 chuyến đi vòng tròn theo chiều kim đồng hồ quanh các cây xăng và quay về mốc xuất phát. Hãy xác định vị trí cây xăng KHỞI HÀNH thích hợp, có đánh số `index`, mà việc đó là khả thi (nghĩa là trong cả cuộc hành trình xăng dư trong xe không bao giờ bị âm `<0`).
- Dữ liệu luôn đảm bảo: Nếu có đáp án thì đó là đáp án duy nhất. Nếu vô nghiệm thì về `-1`.

## Ý tưởng cốt lõi

Thay vì phải mô phỏng chạy chuyến đi đối với từng cây trạm rải rác từ đầu (thuật toán cày 2 vòng for tốn `O(N^2)`), ta có thể đúc kết ra hai chân lý tuyệt đối:

1. **Tổng lượng xăng**: Nếu như tổng sức chứa từ tất cả các trạm trong dải nhỏ hơn lượng tiêu thụ cho cả một vòng bo (`Sum(gas) < Sum(cost)`), chuyến đi này là không thể hoàn thiện (kể cả điểm bắt đầu là trạm nào chăng nữa). Ngược lại, nếu tổng xăng >= tổng hụt cost, chắc chắn có tồn tại 1 điểm xuất phát thành công.
2. **Kỹ thuật chạy mảng cục bộ**: Xách một chiếc xe chạy thử, dùng biến `tank`. Bình xăng ban đầu bằng `0`, đổ xăng sau khi qua cung `i` thì lượng dư sẽ là `diff = gas[i] - cost[i]`. Xe đi tới cây `i`, cộng dồn chênh lệch vào bình: `tank += diff`. Nếu tại bất cứ cái điểm nào làm bình `tank < 0`, nghĩa là từ nơi đang coi là mốc `start` tiến hành chạy tới `i` đã hoàn toàn đổ bể. Do đó, điểm mốc `start` này là ĐIỂM "XUI", và vì đi từ start hụt hơi thế nên **MỌI điểm** chen chúc giữa `start` trở đi về `i` đều là XUI XẺO cả. Bạn buộc phải đổi mốc xuất phát tiếp theo cho cuộc đời làm lại thành `start = i + 1` và reset bình chứa cạn sạch `tank = 0`.

## Thuật toán

1. Nắm trong tay biến: `total = 0` (để coi tổng xăng có đạt tiêu chuẩn không), `tank = 0` (đo độ liều mạng chiếc xe trong chặng), `start = 0` (điểm đặt ngôi sao hi vọng).
2. Xuyên một vòng `for` từ đầu tới cuối các cây trạm `i`.
   - Lọc độ chênh lệch của mỗi trạm: `diff = gas[i] - cost[i]`.
   - Vứt cái này lưu vào biến tổng cộng vòng đời `total += diff`.
   - Bơm vào biến dồn chặng `tank += diff`.
   - Nếu xảy ra vỡ trận trạm đổ xe (`tank < 0`): Ngay lập tức từ bỏ cái trạm `start` trong quá khứ. Cho phép chuyển dời ngày tái xuất sang đầu cầu trạm kế tiếp: `start = i + 1`, làm sạch bình dồn chặng `tank = 0`.
3. Kiểm toán sau cùng: Cuối mảng đi duyệt, nếu tổng tài sản dương cõi trần `total >= 0`, mốc `start` dạt dẹo kia chính là thánh địa hứa hẹn trả về `start % n`. (Phép chia lấy dư cho chắc là con số vòng quay lại của độ mảng `n`). Còn nếu tài sản bị âm, trả về `-1`.

## Vì sao đúng và chạy lật một mạch O(n)?

- Đây là một ứng dụng vô lường của Greedy (Khâu tham lam). Nếu một chiếc xe có bắt đầu tại điểm A mà đi tới điểm B kẹt số (âm xăng), thì chiếc xe dù có tái start từ bất cứ mốc nào nương thân giữa đoạn A hớn B đều sẽ thất bại (vì chặng đường dẫu có cố nén nhưng chênh lệch đã âm). Nên ta tự tin đôn ngày khởi hành nhảy tận sang mốc dính líu kế tiếp là `B + 1` để giảm thiểu vòng lặp chạy về đúng giới hạn O(n).
- Và vì giả thiết "Dư dả xăng tổng là có đường trơn hoàn não", nên bất kì cái điểm `start` lưu giữ trót lọt được khi `tank >= 0` bay cao mãi tới khi mảng quét kết thì chắc chắn đáp ứng được cho cả đoạn nối sau (tất cả củng cố bởi Toán Học).

## Độ phức tạp

- Thời gian: `O(N)`. Ta dạo qua các trạm trên đường cái đúng 1 lượt nhẹ duy nhất.
- Không gian (Space): `O(1)`. Chỉ nhúng ba biến gán cỏn con `total`, `tank`, `start`.

## Code (Java)

```java
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        long total = 0;
        long tank = 0;
        int start = 0;
        
        for (int i = 0; i < n; i++) {
            int diff = gas[i] - cost[i];
            total += diff;
            tank += diff;
            if (tank < 0) {
                start = i + 1; 
                tank = 0;      
            }
        }
        
        return total >= 0 ? start % n : -1;
    }
}
```
