class Solution {
    public int numDecodings(String s) {
        if (s == null || s.length() == 0)
            return 0;
        int n = s.length();
        if (s.charAt(0) == '0')
            return 0;

        int prev = 1, curr = 1;

        for (int i = 1; i < n; i++) {
            int ways = 0;
            char ch = s.charAt(i);
            if (ch != '0')
                ways += curr; 

            int twoVal = (s.charAt(i - 1) - '0') * 10 + (ch - '0');
            if (twoVal >= 10 && twoVal <= 26)
                ways += prev; 

            prev = curr;
            curr = ways;
            if (curr == 0 && i < n - 1) {
                
            }
        }

        return curr;
    }
}