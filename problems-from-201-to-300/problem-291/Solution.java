class Solution {
    public int maxProduct(String[] words) {
        int n = words.length;
        // Create a bitmask for each word
        int[] masks = new int[n];

        for (int i = 0; i < n; i++) {
            int mask = 0;
            for (char c : words[i].toCharArray()) {
                // Set the bit for character c (0-25 for a-z)
                mask |= (1 << (c - 'a'));
            }
            masks[i] = mask;
        }

        int maxProduct = 0;

        // Check all pairs of words
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // If masks don't overlap (no common letters)
                if ((masks[i] & masks[j]) == 0) {
                    maxProduct = Math.max(maxProduct, words[i].length() * words[j].length());
                }
            }
        }

        return maxProduct;
    }
}