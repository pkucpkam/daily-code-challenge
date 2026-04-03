# Khoảng cách lớn nhất của quãng đường

## 1. Mô tả bài toán
An và Bình rời rạp chiều phim để đi về chung. An cần ghé cửa hàng trước khi về nhà, còn Bình thì về thẳng nhà. Họ muốn đi cùng nhau trên một quãng đường chung dài nhất có thể để trò chuyện, sao cho sự chênh lệch quãng đường mỗi người cần đi so với dự kiến ban đầu không vượt quá giới hạn t1 (đối với người 1) và t2 (đối với người 2).

## 2. Ý tưởng cốt lõi
- Bài toán tìm khoảng cách chung lớn nhất mà An và Bình có thể đi cùng trước khi tách ra.
- Xác định khoảng cách lý tưởng từ rạp tới nhà, và rạp tới cửa hàng. 
- Nếu khoảng cách thẳng (nhà hoặc cửa hàng) nằm trong giới hạn cho phép, có thể đi thẳng luôn tới đó.
- Nếu không, dùng tìm kiếm nhị phân (Binary Search) trên khoảng cách từ `0` đến khoảng cách từ rạp tới cửa hàng để tìm độ dài lớn nhất `mid` sao cho cả hai ràng buộc về chênh lệch đường đi được thỏa mãn.

## 3. Giải thích thuật toán
- **Khởi tạo và tính toán cơ bản**: Tính khoảng cách giữa rạp phim, nhà và cửa hàng.
- **Kiểm tra giới hạn cơ sở**: Có thể họ sẽ đi thẳng tới nhà hoặc cửa hàng luôn từ đầu. Nếu khoảng cách đi thẳng nhỏ hơn hoặc bằng các giới hạn thiết lập `min(t1, t2)`, trả về kết quả đó luôn.
- **Tìm kiếm nhị phân**: 
  - Đặt khoảng tìm kiếm `[left, right]` là từ `0` đến độ dài quãng đường từ rạp tới cửa hàng.
  - Sử dụng điểm `mid` ở giữa để thử nghiệm. Nếu với độ dài `mid`, giới hạn sai số hành trình của cả hai (cho tới t1 và t2) vẫn được đảm bảo, ta tịnh tiến lên khoảng đi chung dài hơn (`left = mid`).
  - Nếu giới hạn thời gian/quãng đường bị vi phạm, thu hẹp khoảng cách lại (`right = mid`).
  - Vòng lặp dừng khi độ chênh lệch giữa `left` và `right` đạt mức sai số rất nhỏ `1e-6` nhằm mang lại độ phản hồi chính xác đến 4 chữ số sau số thập phân.

## 4. Độ phức tạp
- **Thời gian (Time Complexity)**: \(O(\log(\text{dist\_theater\_store}) / 1e-6)\) do ta sử dụng tìm kiếm nhị phân với sai số $10^{-6}$. Rất hiệu quả và gần như là \(O(1)\) nếu xem xét trên kích thước các khoảng cách thông thường.
- **Không gian (Space Complexity)**: \(O(1)\) vì chỉ dùng một số biến con trỏ chạy trên khoảng tìm kiếm giới hạn.

## 5. Code
```python
import math

# Hàm tính khoảng cách giữa hai điểm
def distance(p1, p2):
    return math.sqrt((p1[0] - p2[0]) ** 2 + (p1[1] - p2[1]) ** 2)

# Hàm tìm khoảng cách lớn nhất mà An và Bình có thể đi cùng nhau
def max_common_distance(t1, t2, theater, home, store):
    # Khoảng cách từ rạp đến nhà và từ rạp đến cửa hàng
    dist_theater_home = distance(theater, home)
    dist_theater_store = distance(theater, store)
    dist_store_home = distance(store, home)

    # Nếu khoảng cách đến nhà nhỏ hơn t1 và t2, họ có thể đi cùng đến nhà
    if dist_theater_home <= min(t1, t2):
        return dist_theater_home
    
    # Nếu khoảng cách đến cửa hàng nhỏ hơn t1 và t2, họ có thể đi cùng đến cửa hàng
    if dist_theater_store <= min(t1, t2):
        return dist_theater_store

    # Tìm điểm tối ưu bằng cách tối đa hóa khoảng cách đi cùng mà không vượt quá t1 và t2
    left, right = 0, dist_theater_store
    result = 0

    while right - left > 1e-6:  # Dùng sai số nhỏ để đạt độ chính xác
        mid = (left + right) / 2
        if mid <= t1 and (mid + dist_store_home) <= t2:
            result = mid
            left = mid
        else:
            right = mid

    return result

# Đầu vào: khoảng cách t1, t2 và tọa độ rạp phim, nhà và cửa hàng
t1, t2 = map(int, input("Nhập t1 và t2: ").split())
theater = tuple(map(int, input("Nhập tọa độ rạp chiếu phim (x y): ").split()))
home = tuple(map(int, input("Nhập tọa độ nhà (x y): ").split()))
store = tuple(map(int, input("Nhập tọa độ cửa hàng (x y): ").split()))

# Tính toán khoảng cách lớn nhất mà họ có thể đi cùng
max_distance = max_common_distance(t1, t2, theater, home, store)
print("Khoảng cách lớn nhất mà họ có thể đi cùng nhau là:", round(max_distance, 6))
```
