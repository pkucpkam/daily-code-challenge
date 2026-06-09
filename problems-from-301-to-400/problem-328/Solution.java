class Solution {
    public int findNthDigit(int n) {
        int len = 1;
        long count = 9;
        long start = 1;

        // Step 1: Find the length of the number that contains the nth digit
        while (n > count) {
            n -= count;
            len++;
            start *= 10;
            count = (long) len * 9 * start;
        }

        // Step 2: Find the actual number containing the nth digit
        long num = start + (n - 1) / len;

        // Step 3: Find the specific digit in the number
        String s = Long.toString(num);
        return s.charAt((n - 1) % len) - '0';
    }
}