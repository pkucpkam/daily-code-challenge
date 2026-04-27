class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int hold = -prices[0];
        int sold = 0;
        int rest = 0;

        for (int i = 1; i < prices.length; i++) {
            int price = prices[i];

            int prevHold = hold;
            int prevSold = sold;
            int prevRest = rest;

            hold = Math.max(prevHold, prevRest - price);
            sold = prevHold + price;
            rest = Math.max(prevRest, prevSold);
        }

        return Math.max(sold, rest);
    }
}