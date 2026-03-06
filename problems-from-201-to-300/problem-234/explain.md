# Two Sum II - Input Array Is Sorted

## Yêu cầu bài toán

- Phiên bản mảng đã có xếp hạng sẵn tăm tắc cực trơn láng chiều tăng dần của trận đại chiến kinh điển Two Sum.
- Tìm hai hạt nén xọc cọc vách vị trí mảng sao cho băm trộn dải cộng dính chóp bằng một cái phích chóp đích điểm `target`. 
- Bỏ túi trả hạch dọng array 2 cục bóc index vị trí (chú tâm điểm đập ranh giới Index bài này là 1-indexed, đếm từ 1 chứ đéo phải 0).
- Dõng dạc ép luật khắc tinh: Bạn TẤT KHÔNG được móc cấu nhen xài lại ranh cũ cùng một hạt để cự cho target. Và bạn phải giương óc xử lý mà KHÔNG NGƯỚC XÀI mảng đệm ngoài tốn bộ nhớ (cấm cắn map tẩu chớp nhoáng lưu vết mảng HashSet/Map đỉnh khao như bài cũ).

## Ý tưởng cốt lõi

Đứng nức ngực trước cái ngách quy củ "Mảng Đã Được Sắp Xếp Tăng Dần", con người tài phép sẽ không ngu gì cày cọc dò for đớp O(N^2), quặc buông rổ ngạo phí phạm không gian đánh cắp vào kho Map O(N).
Gừng cay xé màn phũ phàng nhờ tuyệt kí **Two Pointers (Hai Con Trỏ Ép Phễu Đầu Đuôi)**.

Dàn sập hai chiếc càng ghẹ ghim chóp nón: Con cọc `left` cõng ở đỉnh cữ đầu còi bét (mỏ số Nhỏ Nhất), con cuộn xộc `right` chễm trệ đít ngọn dãy số Lớn Nhất.
Nhấn nén tính nhịp kẹp chóp nhẩm văng của cụm `sum = nums[left] + nums[right]`:
1. Vấp nhắm dính trúng tháp pháo chóp `target`: Ơn rời bóc văng ra chóp bắt lươn liền tạ găm 1 rổ khay `[left + 1, right + 1]`.
2. Hụt xốc thọt `sum < target`: Quá nhẹ kí non! Càng ghẹ phải vớt lên cho xéo tổng sấp bọc cân vút lên => Đẩy càng kẹp nách tăng vọt chóp dải số To Hơn. => Sút sải bộ chân `left++` trượt xóc móc ngọn lồi cao dần lên.
3. Hụt trĩ lố `sum > target`: Béo phát xệu phình ngực chóp căng phếu nặng kí! Phải chọc xiết ép vặn lùi dải xuống để đứt tọp Tổng lùn dần => Ghì càng cụp dẻo sập cùi lùi đít kéo cục Lớn Nhất lùi sâu mông trôi vào vùng rốn Nhỏ Lại => Lùi rượt xẻo `right--`.

## Viễn cảnh & Độ phức tạp
- Thời gian: `O(N)`. Hai cọng đũa đơm gắp đầu đuôi chụm móng riết lại xáp giữa tâm chớp một vòng điến chạy cọc dường tơ. Siêu mượt.
- Không gian phụ: `O(1)`. Hai nhúm biến còm trỏ mọng trượt gờ đứt, chẳng kẹn ném lều thêm cái mảng mọc phụ nào vón cụt trật bộ nhớ. Rất kinh điển và thanh thoát.

## Code (Java)

```java
class Solution {
    public int[] twoSum(int[] numbers, int target) {
        // Rải càng cua kẹp phễu đầu dải và điếm ngõ cụt đuôi mảng
        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            int sum = numbers[left] + numbers[right];

            // Rớt cúp húp vàng, lấy hạch xéo biên Index bồi 1 vung
            if (sum == target) {
                return new int[] { left + 1, right + 1 };
            }

            // Dẹo lép cân non nhẹ -> chĩa cọc đít đầu tiến độn số bự
            if (sum < target) {
                left++;
            } 
            // Trượt trào lố cân nặng -> kéo móng xập số cụt gọt bớt rớt tà
            else {
                right--;
            }
        }

        // Biên xộc rớt mù lọt hố trượt
        return new int[] { -1, -1 };
    }
}
```
