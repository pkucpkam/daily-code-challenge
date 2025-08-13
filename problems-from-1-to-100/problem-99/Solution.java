class Solution {
    public int findPoisonedDuration(int[] timeSeries, int duration) {
        int totalPoisonedDuration = 0;

        for (int i = 0; i < timeSeries.length; i++) {
            int start = timeSeries[i];
            int end = start + duration;

            if (i > 0) {
                int previousEnd = timeSeries[i - 1] + duration;
                if (start < previousEnd) {
                    totalPoisonedDuration += end - previousEnd;
                } else {
                    totalPoisonedDuration += duration;
                }
            } else {
                totalPoisonedDuration += duration;
            }
        }

        return totalPoisonedDuration;
    }
}