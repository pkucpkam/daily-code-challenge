class Solution {
    public int titleToNumber(String columnTitle) {
        int result = 0;
        int length = columnTitle.length();

        for (int i = 0; i < length; i++) {
            char currentChar = columnTitle.charAt(i);
            int value = currentChar - 'A' + 1;
            result = result * 26 + value;
        }

        return result;
    }
}

// Better Solution

// class Solution {
// public int titleToNumber(String columnTitle) {
// int res = 0;
// for(int i=0;i<columnTitle.length();i++){
// res = res*26 + (columnTitle.charAt(i) - 'A' +1);
// }
// return res;
// }
// }