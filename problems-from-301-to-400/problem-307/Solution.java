class Solution {
    private static final int MOD = 1337;

    public int superPow(int a, int[] b) {
        a %= MOD;
        int res = 1;
        for (int digit : b) {
            res = (pow(res, 10) * pow(a, digit)) % MOD;
        }
        return res;
    }

    private int pow(int base, int exp) {
        int res = 1;
        base %= MOD;
        while (exp > 0) {
            if ((exp & 1) != 0) {
                res = (res * base) % MOD;
            }
            base = (base * base) % MOD;
            exp >>= 1;
        }
        return res;
    }
}