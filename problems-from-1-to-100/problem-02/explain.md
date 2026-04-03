# Two Sum

## 1. Mô tả bài toán
Cho một mảng các số nguyên `nums` và một số nguyên `target`. Tìm và trả về chỉ số (indices) của hai số sao cho tổng của chúng cộng lại bằng `target`. Mỗi input được đảm bảo có chính xác một lời giải và không dùng một phần tử hai lần.

## 2. Ý tưởng cốt lõi
- Sử dụng Dictionary (Hash Map) để lưu trữ các giá trị của mảng cùng vị trí xuất hiện của chúng.
- Tính phần còn thiếu `complement = target - num`. Chỉ cần duyệt qua mảng một vòng và kiểm tra xem `complement` đã xuất hiện trong Dictionary trước đó hay chưa.

## 3. Giải thích thuật toán
- Dùng vòng lặp lấy cả giá trị giá trị và chỉ số tương ứng bằng `enumerate`.
- Tìm phần còn thiếu bằng cách lấy đích trừ phần tử đang xét: `complement = target - num`.
- Nếu phát hiện `complement` đã từng xuất hiện (nó nằm trong `num_dict`), nghĩa là mảng có chứa cặp số khớp với tổng yêu cầu. Trả về mảng 2 chỉ số.
- Nếu `complement` chưa tồn tại, lưu số đang xét vào Hash Map `num_dict[num] = i` để các bước lặp sau có thể tìm thấy mã này.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \( O(N) \) vì ta chỉ việc duyệt qua mảng số một lần, tìm key trong dictionary tốn \( O(1) \).
- **Không gian (Space Complexity)**: \( O(N) \) không gian phụ vì ta có thể lưu tất cả phần tử trong cấu trúc `num_dict` ở trường hợp tệ nhất.

## 5. Code
```python
class Solution(object):
    def twoSum(self, nums, target):
        """
        :type nums: List[int]
        :type target: int
        :rtype: List[int]
        """
        num_dict = {}
        
        for i, num in enumerate(nums):
            complement = target - num
            if complement in num_dict:
                return [num_dict[complement], i]
            num_dict[num] = i
```
