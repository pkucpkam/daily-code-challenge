class Solution {
    public int uniquePaths(int m, int n) {
        long res = 1;
        int a = m + n - 2, b = Math.min(m - 1, n - 1);
        for (int i = 1; i <= b; i++) {
            res = res * (a - b + i) / i;
        }
        return (int) res;
    }
}