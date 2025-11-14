class Solution {
    public int maxProfit(int[] prices) {
        int firstBuy = Integer.MIN_VALUE;
        int firstSell = 0;
        int secondBuy = Integer.MIN_VALUE;
        int secondSell = 0;

        for (int p : prices) {
            firstBuy = Math.max(firstBuy, -p);
            firstSell = Math.max(firstSell, firstBuy + p);
            secondBuy = Math.max(secondBuy, firstSell - p);
            secondSell = Math.max(secondSell, secondBuy + p);
        }
        return secondSell;
    }
}