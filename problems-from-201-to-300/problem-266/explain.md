# 229. Majority Element II - Best Solution

## Mô tả bài toán

Cho mảng số nguyên nums có độ dài n, hãy tìm tất cả phần tử xuất hiện nhiều hơn floor(n/3) lần.

## Ý tưởng cốt lõi

Dùng Boyer-Moore Voting Algorithm mở rộng.

Vì ngưỡng là n/3 nên tối đa chỉ có 2 phần tử thỏa mãn điều kiện.

Nếu có 3 phần tử khác nhau cùng xuất hiện hơn n/3 lần thì tổng số lần xuất hiện sẽ lớn hơn n, điều này là không thể.

Do đó ta chỉ cần theo dõi:
- 2 ứng viên: candidate1, candidate2
- 2 bộ đếm: count1, count2

## Cách hoạt động

### Bước 1: Tìm ứng viên

Duyệt toàn bộ mảng:

1. Nếu số hiện tại trùng candidate1 thì tăng count1.
2. Nếu trùng candidate2 thì tăng count2.
3. Nếu count1 bằng 0 thì thay candidate1 bằng số hiện tại và đặt count1 = 1.
4. Nếu count2 bằng 0 thì thay candidate2 bằng số hiện tại và đặt count2 = 1.
5. Nếu không rơi vào các trường hợp trên thì giảm cả count1 và count2.

Ý nghĩa của bước giảm đồng thời: ta đang triệt tiêu một bộ ba gồm 3 giá trị khác nhau, việc này không làm mất khả năng tồn tại của phần tử xuất hiện hơn n/3 lần.

### Bước 2: Xác thực ứng viên

Sau vòng 1, candidate1 và candidate2 chỉ là ứng viên tiềm năng.

Cần duyệt lại mảng để đếm thật số lần xuất hiện của từng ứng viên, rồi chỉ thêm vào kết quả nếu số lần xuất hiện > n/3.

## Vì sao đúng

- Tối đa có 2 đáp án hợp lệ với ngưỡng n/3.
- Cơ chế triệt tiêu giữ lại các phần tử có tần suất cao và loại dần các phần tử không đủ mạnh.
- Vòng xác thực đảm bảo kết quả cuối cùng luôn chính xác.

## Độ phức tạp

- Thời gian: O(n)
- Bộ nhớ phụ: O(1)

Đây là lời giải tối ưu đúng theo yêu cầu follow-up.

## Java code

```java
class Solution {
    public List<Integer> majorityElement(int[] nums) {
        int candidate1 = 0;
        int candidate2 = 1;
        int count1 = 0;
        int count2 = 0;

        for (int num : nums) {
            if (num == candidate1) {
                count1++;
            } else if (num == candidate2) {
                count2++;
            } else if (count1 == 0) {
                candidate1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                candidate2 = num;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }

        count1 = 0;
        count2 = 0;
        for (int num : nums) {
            if (num == candidate1) {
                count1++;
            } else if (num == candidate2) {
                count2++;
            }
        }

        List<Integer> answer = new ArrayList<>();
        int threshold = nums.length / 3;
        if (count1 > threshold) {
            answer.add(candidate1);
        }
        if (count2 > threshold) {
            answer.add(candidate2);
        }
        return answer;
    }
}
```
