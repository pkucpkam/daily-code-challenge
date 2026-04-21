class Solution {
    public String getHint(String secret, String guess) {
        int bulls = 0;
        int cows = 0;
        int[] balance = new int[10];

        for (int i = 0; i < secret.length(); i++) {
            int s = secret.charAt(i) - '0';
            int g = guess.charAt(i) - '0';

            if (s == g) {
                bulls++;
            } else {
                if (balance[s] < 0) {
                    cows++;
                }
                if (balance[g] > 0) {
                    cows++;
                }
                balance[s]++;
                balance[g]--;
            }
        }

        return bulls + "A" + cows + "B";
    }
}