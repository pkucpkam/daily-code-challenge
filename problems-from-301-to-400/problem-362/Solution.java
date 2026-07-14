/**
 * The rand7() API is already defined in the parent class SolBase.
 * public int rand7();
 * @return a random integer in the range 1 to 7
 */
class Solution extends SolBase {
    public int rand10() {
        while (true) {
            int row = rand7();
            int col = rand7();
            int idx = col + (row - 1) * 7;
            if (idx <= 40) {
                return 1 + (idx - 1) % 10;
            }
            
            // Optimization
            row = idx - 40; // 1 to 9
            col = rand7(); // 1 to 7
            idx = col + (row - 1) * 7; // 1 to 63
            if (idx <= 60) {
                return 1 + (idx - 1) % 10;
            }
            
            row = idx - 60; // 1 to 3
            col = rand7(); // 1 to 7
            idx = col + (row - 1) * 7; // 1 to 21
            if (idx <= 20) {
                return 1 + (idx - 1) % 10;
            }
        }
    }
}