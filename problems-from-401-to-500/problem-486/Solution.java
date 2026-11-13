class Solution {
    public int calPoints(String[] operations) {
        int[] scores = new int[operations.length];
        int size = 0;
        int totalSum = 0;

        for (String op : operations) {
            switch (op) {
                case "+":
                    int sumTwo = scores[size - 1] + scores[size - 2];
                    scores[size++] = sumTwo;
                    totalSum += sumTwo;
                    break;
                case "D":
                    int doubled = scores[size - 1] * 2;
                    scores[size++] = doubled;
                    totalSum += doubled;
                    break;
                case "C":
                    totalSum -= scores[--size];
                    break;
                default:
                    int score = Integer.parseInt(op);
                    scores[size++] = score;
                    totalSum += score;
                    break;
            }
        }

        return totalSum;
    }
}
