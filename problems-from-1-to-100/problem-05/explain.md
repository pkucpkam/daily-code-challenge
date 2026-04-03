# Longest Palindromic Substring

## 1. Mô tả bài toán
Tìm và trả về chuỗi con đối xứng (Palindrome) dài nhất có bên trong chuỗi chuỗi `s` đưa vào. Điển hình là Palindrome có thể kéo xuôi hay ngược đều ra các ký tự y chang.

## 2. Ý tưởng cốt lõi
- Bài toán áp dụng phương pháp Expand Around Center (Mở rộng từ vị trí trung tâm). 
- Một chuỗi đối xứng có cơ sở xuất phát từ một tâm và mở tỏa ra hai phía bằng quy luật đối xứng cân bằng.
- Ở mỗi chỉ số ta duyệt qua, ta giả định nó là tâm và quét 2 bên xa dần nhất có thể.

## 3. Giải thích thuật toán
- Các edge cases (chuỗi rỗng) được chặn ở đầu đoạn. Quản lý chuỗi thông qua chỉ số gốc `start` và đuôi `end`.
- Quét qua chuỗi vòng `i`:
  - Có 2 trường hợp tạo trung tâm một dạng Palindrome:
    - Loại chữ số giữa ở chính là ký tự lẻ (Tâm = 1 ký tự): thiết lập 2 tay trái phải vào cùng `i`, đẩy `left -= 1`, `right += 1` khi nó còn là ranh giới trong chuỗi cũng như đối xứng 2 bên `s[left] == s[right]`. Chiều dài sẽ là `len1`.
    - Loại nén nằm giữa trục của số chẵn (Tâm là khoảng trống giữa 2 ký tự): thiết lập tại hai điểm giao thoa `i` và `i+1`. Chiều dài mở rộng sẽ là `len2`.
  - Chọn chiều dài tối ưu nhất và nếu đoạn này tỏ ra vượt trội đoạn từng cao nhất trước đó (`max_len > end - start`): Tính và tinh chỉnh lại biên `start` và biên `end` phụ thuộc trục.
- Kết thúc ta xẻ mảng trực tiếp qua `s[start:end + 1]`.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \( O(N^2) \). Do vòng lặp O(N) trung tâm trải dài n ký tự và đối với mỗi vị trí sẽ cắm rễ sang 2 phía thêm khả năng tối đa O(N).
- **Không gian (Space Complexity)**: \( O(1) \). Code chỉ giữ các biến số nguyên vị trí cho con trỏ chuỗi biên mà không cần gọi tốn thêm cấu trúc mảng mới phụ gì.

## 5. Code
```python
class Solution(object):
    def longestPalindrome(self, s):
        """
        :type s: str
        :rtype: str
        """
        if not s or len(s) < 1:
            return ""
        
        start, end = 0, 0
        
        for i in range(len(s)):
            left, right = i, i
            while left >= 0 and right < len(s) and s[left] == s[right]:
                left -= 1
                right += 1
            len1 = right - left - 1  
            
            left, right = i, i + 1
            while left >= 0 and right < len(s) and s[left] == s[right]:
                left -= 1
                right += 1
            len2 = right - left - 1  
            
            max_len = max(len1, len2)
            
            if max_len > end - start:
                start = i - (max_len - 1) // 2
                end = i + max_len // 2
        
        return s[start:end + 1]
```
