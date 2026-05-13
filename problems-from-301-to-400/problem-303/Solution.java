class Solution {
    public int countNumbersWithUniqueDigits(int n) {
        if (n == 0) {
            return 1;
        }

        int result = 10;
        int uniqueDigits = 9;
        int availableDigits = 9;

        for (int length = 2; length <= n && availableDigits > 0; length++) {
            uniqueDigits *= availableDigits;
            result += uniqueDigits;
            availableDigits--;
        }

        return result;
    }
}