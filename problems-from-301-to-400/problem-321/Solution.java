class Solution {
    public boolean validUtf8(int[] data) {
        int remainingBytes = 0;
        
        for (int val : data) {
            if (remainingBytes == 0) {
                // Determine the length of the UTF-8 character based on the leading bits
                if ((val >> 7) == 0b0) {
                    remainingBytes = 0;
                } else if ((val >> 5) == 0b110) {
                    remainingBytes = 1;
                } else if ((val >> 4) == 0b1110) {
                    remainingBytes = 2;
                } else if ((val >> 3) == 0b11110) {
                    remainingBytes = 3;
                } else {
                    return false; // Invalid leading byte
                }
            } else {
                // Check if the continuation byte starts with "10"
                if ((val >> 6) == 0b10) {
                    remainingBytes--;
                } else {
                    return false; // Invalid continuation byte
                }
            }
        }
        
        // All characters must be fully resolved
        return remainingBytes == 0;
    }
}