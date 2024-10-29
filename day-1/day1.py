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
