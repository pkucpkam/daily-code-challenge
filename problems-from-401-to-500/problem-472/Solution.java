class Solution {

    public int[][] imageSmoother(int[][] img) {
        int m = img.length;
        int n = img[0].length;

        // Bước 1: Duyệt qua từng ô, tính giá trị trung bình và đóng gói vào bit 8 -> 15
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int sum = 0;
                int count = 0;

                // Xác định biên của cửa sổ trượt 3x3 bao quanh ô (i, j)
                int rStart = Math.max(0, i - 1);
                int rEnd = Math.min(m - 1, i + 1);
                int cStart = Math.max(0, j - 1);
                int cEnd = Math.min(n - 1, j + 1);

                for (int r = rStart; r <= rEnd; r++) {
                    for (int c = cStart; c <= cEnd; c++) {
                        // Lấy giá trị nguyên bản bằng mặt nạ 0xFF (8 bit thấp)
                        sum += (img[r][c] & 0xFF);
                        count++;
                    }
                }

                int avg = sum / count;
                // Đóng gói giá trị trung bình vào các bit 8 -> 15 của ô hiện tại
                img[i][j] |= (avg << 8);
            }
        }

        // Bước 2: Dịch phải 8 bit để cập nhật giá trị làm mịn thành giá trị chính thức
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                img[i][j] >>= 8;
            }
        }

        return img;
    }
}