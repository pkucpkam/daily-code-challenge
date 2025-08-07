class Solution {
    public boolean repeatedSubstringPattern(String s) {
        int n = s.length();
        String doubled = s + s;
        String sub = doubled.substring(1, 2 * n - 1);
        return sub.contains(s);
    }
}