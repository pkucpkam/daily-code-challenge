# Longest Substring Without Repeating Characters

## 1. Mô tả bài toán
Xác định độ dài của chuỗi con dài nhất mà không chứa bắt cứ một ký tự nào bị lặp lại trong một chuỗi `s` đầu vào.

## 2. Ý tưởng cốt lõi
- Dùng kỹ thuật "Sliding Window" (Cửa sổ trượt).
- Duy trì một Hash Map / Dictionary để ghi nhớ nhanh vị trí cập nhật mới nhất mà một ký tự xuất hiện.
- Trượt phần đuôi `end` qua từng ký tự của chuỗi; nếu bắt gặp ký tự trùng lại và vị trí từng trước nó rơi vào cửa sổ hiện tại, lập tức thu cửa sổ bằng cách dịch đỉnh `start` qua một bước khỏi ký tự trùng cũ.

## 3. Giải thích thuật toán
- Biến cấu hình: Khởi tạo bảng băm `char_map`, chỉ số bắt đầu đoạn không lặp lại `start = 0`, chuỗi tối đa `max_len = 0`.
- Vòng lặp quét trượt cuối đoạn từ `0` đến `len(s)-1`. Nếu một ký tự ở chỉ số `end` được phát hiện là đã ở trong `char_map` (**và** vị trí cũ từ Map đạt `>= start` - nghĩa là trùng lặp rơi vào đoạn cửa sổ ta đang ngắm đến):
  - Ta buộc phải cắt đứt đoạn liên tục này, cập nhật đầu cửa sổ trượt `start` qua ký tự trùng cũ của nó 1 ô: `start = char_map[s[end]] + 1`.
- Cập nhật vị trí xuất hiện mới nhất của nó `char_map[s[end]] = end`.
- Tính độ dài hiện tại mới bằng `end - start + 1` và gán lại độ dài lớn nhất `max_len`. 
- Giải thuật tìm ra đoạn liền kề thông minh mà không cần kiểm tra thô bạo.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \( O(N) \) Chỉ cần một pass duy nhất trên chuỗi (chỉ số trượt `end` chạy mảng từ trái sang phải), các xử lý map bằng \( O(1) \).
- **Không gian (Space Complexity)**: \( O(min(N, M)) \) với M là tập ký tự đặc trưng trong bảng chữ cái - do dùng `char_map`. 

## 5. Code
```python
class Solution(object):
    def lengthOfLongestSubstring(self, s):
        """
        :type s: str
        :rtype: int
        """
        char_map = {}
        start = 0
        max_len = 0

        for end in range(len(s)):
            if s[end] in char_map and char_map[s[end]] >= start:
                start = char_map[s[end]] + 1

            char_map[s[end]] = end
            max_len = max(max_len, end - start + 1)

        return max_len
```
