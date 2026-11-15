class Solution {
    public int repeatedStringMatch(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int count = 0;

        // Lặp chuỗi a cho đến khi độ dài của sb ít nhất bằng độ dài của b
        while (sb.length() < b.length()) {
            sb.append(a);
            count++;
        }

        // Kiểm tra xem b đã là chuỗi con của sb chưa (lặp k lần)
        if (sb.indexOf(b) != -1) {
            return count;
        }

        // Thêm 1 lần lặp nữa để bao phủ trường hợp b bắt đầu lệch ở cuối chuỗi a
        sb.append(a);
        count++;

        // Kiểm tra lại lần cuối (lặp k + 1 lần)
        if (sb.indexOf(b) != -1) {
            return count;
        }

        return -1;
    }
}
