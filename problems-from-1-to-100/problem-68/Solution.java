class Solution {
    public boolean isPowerOfThree(int n) {
        if (n > 0 && 1162261467 % n == 0) {
            return true;
        }
        return false;
   
    }
}

// The number 1162261467 is the largest power of 3 that fits in a 32-bit signed integer (3^19).