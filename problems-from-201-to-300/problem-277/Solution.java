class Solution {
    public int hIndex(int[] citations) {
        int left = 0;
        int right = citations.length - 1;
        int n = citations.length;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int papersWithAtLeastMid = n - mid;

            if (citations[mid] == papersWithAtLeastMid) {
                return papersWithAtLeastMid;
            }

            if (citations[mid] < papersWithAtLeastMid) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return n - left;
    }
}