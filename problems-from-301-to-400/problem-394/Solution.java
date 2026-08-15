import java.util.List;

class Solution {
    public int findMinDifference(List<String> timePoints) {
        // Optimization: Pigeonhole Principle
        // There are 24 * 60 = 1440 minutes in a day.
        // If there are more than 1440 time points, at least two must be identical.
        if (timePoints.size() > 1440) {
            return 0;
        }

        boolean[] minutes = new boolean[1440];

        // Convert each HH:MM string to total minutes from 00:00
        for (String time : timePoints) {
            int h = (time.charAt(0) - '0') * 10 + (time.charAt(1) - '0');
            int m = (time.charAt(3) - '0') * 10 + (time.charAt(4) - '0');
            int totalMinutes = h * 60 + m;

            // If duplicate time point exists, min difference is 0
            if (minutes[totalMinutes]) {
                return 0;
            }
            minutes[totalMinutes] = true;
        }

        int minDiff = Integer.MAX_VALUE;
        int prev = -1;
        int first = -1;
        int last = -1;

        // Find min difference between consecutive time points
        for (int i = 0; i < 1440; i++) {
            if (minutes[i]) {
                if (prev != -1) {
                    minDiff = Math.min(minDiff, i - prev);
                } else {
                    first = i;
                }
                prev = i;
                last = i;
            }
        }

        // Check circular wrap-around difference between the last and first time points
        minDiff = Math.min(minDiff, 1440 - last + first);

        return minDiff;
    }
}