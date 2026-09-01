class Solution {
    public boolean checkRecord(String s) {
        int absents = 0;
        int lates = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == 'A') {
                absents++;
                if (absents >= 2) {
                    return false;
                }
                lates = 0; // Reset consecutive late count on non-'L' day
            } else if (c == 'L') {
                lates++;
                if (lates >= 3) {
                    return false;
                }
            } else {
                lates = 0; // Reset consecutive late count on 'P' day
            }
        }

        return true;
    }
}