class Solution {
    public boolean isAdditiveNumber(String num) {
        int n = num.length();

        for (int len1 = 1; len1 <= n / 2; len1++) {
            if (num.charAt(0) == '0' && len1 > 1) {
                break;
            }

            for (int len2 = 1; len1 + len2 < n; len2++) {
                if (num.charAt(len1) == '0' && len2 > 1) {
                    break;
                }

                if (isValidSequence(num, len1, len2)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isValidSequence(String num, int len1, int len2) {
        int start = 0;
        String first = num.substring(start, start + len1);
        start += len1;

        String second = num.substring(start, start + len2);
        start += len2;

        while (start < num.length()) {
            String sum = addStrings(first, second);
            if (!num.startsWith(sum, start)) {
                return false;
            }

            start += sum.length();
            first = second;
            second = sum;
        }

        return true;
    }

    private String addStrings(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;

        while (i >= 0 || j >= 0 || carry > 0) {
            int da = i >= 0 ? a.charAt(i) - '0' : 0;
            int db = j >= 0 ? b.charAt(j) - '0' : 0;
            int sum = da + db + carry;

            sb.append((char) ('0' + (sum % 10)));
            carry = sum / 10;
            i--;
            j--;
        }

        return sb.reverse().toString();
    }
}