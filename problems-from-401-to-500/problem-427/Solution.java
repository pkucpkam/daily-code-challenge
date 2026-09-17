class Solution {
    public String fractionAddition(String expression) {
        int numerator = 0;
        int denominator = 1;

        char[] chars = expression.toCharArray();
        int n = chars.length;
        int i = 0;

        while (i < n) {
            // 1. Determine the sign of the current fraction
            int sign = 1;
            if (chars[i] == '+' || chars[i] == '-') {
                if (chars[i] == '-') {
                    sign = -1;
                }
                i++;
            }

            // 2. Parse the current numerator
            int curNumerator = 0;
            while (i < n && chars[i] >= '0' && chars[i] <= '9') {
                curNumerator = curNumerator * 10 + (chars[i] - '0');
                i++;
            }
            curNumerator *= sign;

            // 3. Skip the fraction separator '/'
            i++;

            // 4. Parse the current denominator
            int curDenominator = 0;
            while (i < n && chars[i] >= '0' && chars[i] <= '9') {
                curDenominator = curDenominator * 10 + (chars[i] - '0');
                i++;
            }

            // 5. Add current fraction to running sum:
            // a/b + c/d = (a * d + c * b) / (b * d)
            numerator = numerator * curDenominator + curNumerator * denominator;
            denominator = denominator * curDenominator;

            // 6. Reduce the fraction using GCD to keep values small and avoid overflow
            int commonDivisor = gcd(Math.abs(numerator), denominator);
            numerator /= commonDivisor;
            denominator /= commonDivisor;
        }

        return numerator + "/" + denominator;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}