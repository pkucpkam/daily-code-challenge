
class Solution {
    public int compareVersion(String version1, String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");

        int maxLen = Math.max(v1.length, v2.length);

        for (int i = 0; i < maxLen; i++) {
            int rev1 = i < v1.length ? Integer.parseInt(v1[i]) : 0;
            int rev2 = i < v2.length ? Integer.parseInt(v2[i]) : 0;

            if (rev1 < rev2) {
                return -1;
            } else if (rev1 > rev2) {
                return 1;
            }
        }

        return 0;
    }
}


class SolutionOptimized {
    public int compareVersion(String version1, String version2) {
        int i = 0, j = 0;

        while (i < version1.length() || j < version2.length()) {
            int rev1 = 0;
            while (i < version1.length() && version1.charAt(i) != '.') {
                rev1 = rev1 * 10 + (version1.charAt(i) - '0');
                i++;
            }

            int rev2 = 0;
            while (j < version2.length() && version2.charAt(j) != '.') {
                rev2 = rev2 * 10 + (version2.charAt(j) - '0');
                j++;
            }

            if (rev1 < rev2) {
                return -1;
            } else if (rev1 > rev2) {
                return 1;
            }

            i++;
            j++;
        }

        return 0;
    }
}
