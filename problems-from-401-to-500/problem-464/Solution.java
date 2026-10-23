class Solution {
    public int minSteps(int n) {
        int ans = 0;
        int d = 2;

        // Phân tích n thành các thừa số nguyên tố
        while (d * d <= n) {
            while (n % d == 0) {
                ans += d;
                n /= d;
            }
            d++;
        }

        // Nếu sau vòng lặp n > 1, phần còn lại chính là một thừa số nguyên tố
        if (n > 1) {
            ans += n;
        }

        return ans;
    }
}