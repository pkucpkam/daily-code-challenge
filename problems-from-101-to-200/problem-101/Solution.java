class Solution {
    public String[] findWords(String[] words) {
        List<String> result = new ArrayList<>();
        String[] rows = {"qwertyuiop", "asdfghjkl", "zxcvbnm"};
        for (String word : words) {
            String lower = word.toLowerCase();
            for (String row : rows) {
                if (lower.chars().allMatch(c -> row.indexOf(c) != -1)) {
                    result.add(word);
                    break;
                }
            }
        }
        return result.toArray(new String[0]);
    }
}