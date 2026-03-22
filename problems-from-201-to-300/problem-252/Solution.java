class Solution {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        int islands = 0;

        java.util.ArrayDeque<Integer> stack = new java.util.ArrayDeque<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != '1') {
                    continue;
                }

                islands++;
                grid[r][c] = '0';
                stack.push(r * cols + c);

                while (!stack.isEmpty()) {
                    int cell = stack.pop();
                    int cr = cell / cols;
                    int cc = cell % cols;

                    if (cr > 0 && grid[cr - 1][cc] == '1') {
                        grid[cr - 1][cc] = '0';
                        stack.push((cr - 1) * cols + cc);
                    }
                    if (cr + 1 < rows && grid[cr + 1][cc] == '1') {
                        grid[cr + 1][cc] = '0';
                        stack.push((cr + 1) * cols + cc);
                    }
                    if (cc > 0 && grid[cr][cc - 1] == '1') {
                        grid[cr][cc - 1] = '0';
                        stack.push(cr * cols + (cc - 1));
                    }
                    if (cc + 1 < cols && grid[cr][cc + 1] == '1') {
                        grid[cr][cc + 1] = '0';
                        stack.push(cr * cols + (cc + 1));
                    }
                }
            }
        }

        return islands;
    }
}