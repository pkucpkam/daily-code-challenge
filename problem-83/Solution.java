class Solution {
    public String toHex(int num) {
        if (num == 0)
            return "0";
        StringBuilder hex = new StringBuilder();
        char[] hexChars = "0123456789abcdef".toCharArray();
        while (num != 0 && hex.length() < 8) {
            hex.insert(0, hexChars[num & 15]);
            num >>>= 4;
        }
        return hex.toString();
    }
}