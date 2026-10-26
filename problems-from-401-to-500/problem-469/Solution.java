class Solution {
    public boolean judgeCircle(String moves) {
        // Cắt tỉa sớm O(1): Nếu số bước di chuyển là lẻ, robot không bao giờ có thể quay lại (0, 0)
        if ((moves.length() & 1) != 0) {
            return false;
        }

        int x = 0;
        int y = 0;
        int len = moves.length();

        for (int i = 0; i < len; i++) {
            char move = moves.charAt(i);
            switch (move) {
                case 'U':
                    y++;
                    break;
                case 'D':
                    y--;
                    break;
                case 'R':
                    x++;
                    break;
                case 'L':
                    x--;
                    break;
                default:
                    break;
            }
        }

        // Robot quay về gốc khi và chỉ khi cả 2 tọa độ x và y đều trở về 0
        return x == 0 && y == 0;
    }
}