class Solution {
    public int lengthLongestPath(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }
        
        String[] lines = input.split("\n");
        int[] pathLen = new int[lines.length + 1];
        int maxLen = 0;
        
        for (String line : lines) {
            int depth = 0;
            while (depth < line.length() && line.charAt(depth) == '\t') {
                depth++;
            }
            
            int nameLen = line.length() - depth;
            
            if (line.indexOf('.') != -1) {
                maxLen = Math.max(maxLen, pathLen[depth] + nameLen);
            } else {
                pathLen[depth + 1] = pathLen[depth] + nameLen + 1;
            }
        }
        
        return maxLen;
    }
}