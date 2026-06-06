class Solution {
    public int integerReplacement(int n) {
        long N = n;
        int count = 0;
        while (N > 1) {
            if (N % 2 == 0) {
                N /= 2;
            } else {
                if (N == 3 || N % 4 == 1) {
                    N--;
                } else {
                    N++;
                }
            }
            count++;
        }
        return count;
    }
}