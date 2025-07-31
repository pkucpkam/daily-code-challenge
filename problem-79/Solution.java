class Solution {
    public char findTheDifference(String s, String t) {
        char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();
        Arrays.sort(sArray);
        Arrays.sort(tArray);
        for (int i = 0; i < tArray.length; i++) {
            if (i == sArray.length || sArray[i] != tArray[i]) {
                return tArray[i];
            }
        }
        throw new IllegalArgumentException("No difference found");
    }
}