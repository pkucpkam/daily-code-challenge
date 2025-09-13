class Solution {
    public double myPow(double x, int n) {
        if (n == 0) return 1.0;
        long N = n; 
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }
        double res = 1.0;
        while (N > 0) {
            if ((N & 1) == 1) res *= x;
            x *= x;
            N >>= 1;
        }
        return res;
    }
}