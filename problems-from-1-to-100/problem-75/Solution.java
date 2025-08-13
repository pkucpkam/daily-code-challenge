class Solution {
    public boolean isPerfectSquare(int num) {
        if (num < 0) return false;
        if (num <= 1) return true;
        int left = 1;
        int right = Math.min(num, 46340);
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (mid * mid == num) {
                return true;
            } else if (mid * mid < num) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }
}