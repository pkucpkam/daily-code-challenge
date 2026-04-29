class Solution {
    public int nthSuperUglyNumber(int n, int[] primes) {
        int k = primes.length;
        int[] ugly = new int[n];
        int[] indices = new int[k];
        long[] nextValues = new long[k];

        ugly[0] = 1;
        for (int i = 0; i < k; i++) {
            nextValues[i] = primes[i];
        }

        for (int i = 1; i < n; i++) {
            long nextUgly = nextValues[0];

            for (int j = 1; j < k; j++) {
                if (nextValues[j] < nextUgly) {
                    nextUgly = nextValues[j];
                }
            }

            ugly[i] = (int) nextUgly;

            for (int j = 0; j < k; j++) {
                if (nextValues[j] == nextUgly) {
                    indices[j]++;
                    nextValues[j] = (long) ugly[indices[j]] * primes[j];
                }
            }
        }

        return ugly[n - 1];
    }
}