class Solution {
    public boolean isValidSerialization(String preorder) {
        String[] nodes = preorder.split(",");
        int slots = 1;

        for (String node : nodes) {
            if (slots <= 0) return false;
            slots--;
            if (!node.equals("#")) {
                slots += 2;
            }
        }

        return slots == 0;
    }
}