# 355. Thiết Kế Twitter

**Độ khó:** Medium

## Mô Tả Bài Toán

Thiết kế một phiên bản đơn giản của Twitter nơi người dùng có thể đăng tweet, theo dõi/bỏ theo dõi người dùng khác, và có thể xem 10 tweet gần đây nhất trong bảng tin của họ.

## Các Phương Thức Lớp Twitter

- **Twitter()** - Khởi tạo đối tượng twitter.
- **void postTweet(int userId, int tweetId)** - Tạo một tweet mới với ID `tweetId` bởi người dùng `userId`. Mỗi lần gọi hàm này sẽ có `tweetId` duy nhất.
- **List<Integer> getNewsFeed(int userId)** - Lấy danh sách 10 ID tweet gần đây nhất trong bảng tin của người dùng `userId`. Mỗi tweet trong bảng tin phải được đăng bởi những người mà người dùng theo dõi hoặc bởi chính người dùng đó. Tweet phải được sắp xếp từ mới nhất đến cũ nhất.
- **void follow(int followerId, int followeeId)** - Người dùng có ID `followerId` bắt đầu theo dõi người dùng có ID `followeeId`.
- **void unfollow(int followerId, int followeeId)** - Người dùng có ID `followerId` bắt đầu bỏ theo dõi người dùng có ID `followeeId`.

## Ví Dụ 1

**Đầu vào:**
```
["Twitter", "postTweet", "getNewsFeed", "follow", "postTweet", "getNewsFeed", "unfollow", "getNewsFeed"]
[[], [1, 5], [1], [1, 2], [2, 6], [1], [1, 2], [1]]
```

**Đầu ra:**
```
[null, null, [5], null, null, [6, 5], null, [5]]
```

**Giải thích:**
```java
Twitter twitter = new Twitter();
twitter.postTweet(1, 5); // Người dùng 1 đăng tweet mới (id = 5).
twitter.getNewsFeed(1);  // Bảng tin của người dùng 1 trả về danh sách 1 tweet -> [5].
twitter.follow(1, 2);    // Người dùng 1 theo dõi người dùng 2.
twitter.postTweet(2, 6); // Người dùng 2 đăng tweet mới (id = 6).
twitter.getNewsFeed(1);  // Bảng tin của người dùng 1 trả về 2 tweet -> [6, 5]. 
                         // Tweet 6 đến trước 5 vì được đăng sau tweet 5.
twitter.unfollow(1, 2);  // Người dùng 1 bỏ theo dõi người dùng 2.
twitter.getNewsFeed(1);  // Bảng tin của người dùng 1 trả về 1 tweet -> [5], 
                         // vì người dùng 1 không còn theo dõi người dùng 2.
```

## Ràng Buộc

- 1 ≤ userId, followerId, followeeId ≤ 500
- 0 ≤ tweetId ≤ 10⁴
- Tất cả tweet có ID duy nhất.
- Tối đa 3 × 10⁴ lệnh gọi sẽ được thực hiện.
- Một người dùng không thể theo dõi chính mình.

## Phân Tích Giải Pháp

### Cấu Trúc Dữ Liệu

1. **Map<Integer, List<Tweet>> tweets**: Lưu trữ danh sách tweet của mỗi người dùng
   - Key: userId
   - Value: Danh sách tweet (gồm tweetId và timestamp)

2. **Map<Integer, Set<Integer>> followees**: Lưu trữ danh sách những người mà mỗi người dùng theo dõi
   - Key: userId
   - Value: Tập hợp các followeeId

3. **static int timeStamp**: Đếm tăng để theo dõi thứ tự của tweet

### Độ Phức Tạp

| Phương Thức | Thời Gian | Không Gian |
|-----------|---------|----------|
| postTweet | O(1) | O(1) |
| getNewsFeed | O(N log N) | O(N) |
| follow | O(1) | O(1) |
| unfollow | O(1) | O(1) |

Trong đó N = tổng số tweet từ người dùng và những người họ theo dõi

### Ưu Điểm của Giải Pháp

1. **postTweet nhanh**: Chỉ thêm tweet vào danh sách, O(1) thời gian
2. **follow/unfollow nhanh**: Sử dụng Set, O(1) thời gian
3. **getNewsFeed hiệu quả**: Dùng priority queue để lấy 10 tweet mới nhất
4. **Timestamp**: Đảm bảo thứ tự chính xác của tweet
5. **Không theo dõi chính mình**: Kiểm tra điều kiện này để tránh lỗi