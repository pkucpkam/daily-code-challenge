class Solution {
    public int islandPerimeter(int[][] grid) {
        int perimeter = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    perimeter += 4;
                    if (i > 0 && grid[i - 1][j] == 1)
                        perimeter -= 2;
                    if (j > 0 && grid[i][j - 1] == 1)
                        perimeter -= 2;
                }
            }
        }
        return perimeter;
    }
}

// Best Sollution

// class Solution {
// public int islandPerimeter(int[][] grid) {
// int flips = 0;
// int length = grid[0].length;

// // starts as [0,0,0,...], xor with grid[i] to see how many tiles swapped type
// int[] stateHori = new int[length];
// for(int i = 0; i < grid.length; i++) {
// int state = 0; // same concept, xor with current state to get 1 if it is
// different

// for(int j = 0; j < length; j++) {
// int flipped = state ^ grid[i][j]; // add edge if changed from water to land
// or land to water (vertical)
// int flippedHori = stateHori[j] ^ grid[i][j]; // add edge if changed from
// water to land or land to water (horizontal)
// state = grid[i][j]; // update state
// flips += flipped + flippedHori; // each flipped is 1 if the state changed <=>
// there is a border
// }
// stateHori = grid[i]; // update state
// flips += state; // If state = 1, rightmost tile is land and will have 1 edge
// to the outside
// }

// for(int j = 0; j < length; j++) {
// flips += stateHori[j]; // same concept, one outside edge per tile in lowest
// row
// }

// return flips;
// }
// }