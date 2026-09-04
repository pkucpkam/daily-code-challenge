class Solution {
    /**
     * Checks if s2 contains a permutation of s1 using a fixed-size sliding window.
     * 
     * Time Complexity: O(n1 + n2) - single-pass initialization and O(1) sliding window updates.
     * Space Complexity: O(1) - fixed-size count arrays of length 26 for lowercase English letters.
     */
    public boolean checkInclusion(String s1, String s2) {
        int n1 = s1.length();
        int n2 = s2.length();

        // A permutation of s1 cannot exist in s2 if s1 is longer than s2
        if (n1 > n2) {
            return false;
        }

        int[] s1Count = new int[26];
        int[] s2Count = new int[26];

        // Populate character frequencies for s1 and the first window of s2
        for (int i = 0; i < n1; i++) {
            s1Count[s1.charAt(i) - 'a']++;
            s2Count[s2.charAt(i) - 'a']++;
        }

        // Count how many of the 26 character counts match initially
        int matches = 0;
        for (int i = 0; i < 26; i++) {
            if (s1Count[i] == s2Count[i]) {
                matches++;
            }
        }

        // Slide the window across s2
        for (int i = 0; i < n2 - n1; i++) {
            if (matches == 26) {
                return true;
            }

            int right = s2.charAt(i + n1) - 'a';
            int left = s2.charAt(i) - 'a';

            // Include new character on the right of the window
            s2Count[right]++;
            if (s1Count[right] == s2Count[right]) {
                matches++;
            } else if (s1Count[right] + 1 == s2Count[right]) {
                matches--;
            }

            // Exclude old character on the left of the window
            s2Count[left]--;
            if (s1Count[left] == s2Count[left]) {
                matches++;
            } else if (s1Count[left] - 1 == s2Count[left]) {
                matches--;
            }
        }

        return matches == 26;
    }
}