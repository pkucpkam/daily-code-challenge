# 529. Minesweeper

## Intuition

The Minesweeper game mechanics are all about revealing squares recursively until you hit the boundaries of empty spaces. 
When a user clicks on an unrevealed square (`'E'`), one of three things can happen:
1. **Mine Hit**: The game ends. We mark it as `'X'`.
2. **Adjacent to Mines**: We reveal how many mines surround the clicked square (a number from `'1'` to `'8'`). The reveal stops here.
3. **Completely Empty**: There are no adjacent mines. We mark the square as a blank `'B'`, and the game automatically clicks all 8 surrounding squares for us. This cascading effect continues until numerical boundaries are hit.

This "cascading" or "recursive revealing" behavior is a classic signature of **Depth-First Search (DFS)** or **Breadth-First Search (BFS)**. We will use DFS as it is straightforward to implement and nicely mimics the cascading nature of the game.

---

## Key Insights & Algorithm

### Depth-First Search (DFS)

1. **Initial Click Check**:
   - Before starting our DFS traversal, we must handle the edge case where the very first click lands directly on a mine (`'M'`).
   - If so, change it to `'X'` and return the board immediately.

2. **DFS Traversal on Empty Squares**:
   - For a given square `(r, c)`, if it is out of bounds or is not an unrevealed empty square (`'E'`), we stop (base case).
   - We count the number of mines in the 8 adjacent cells.
   - **Case A (Mines > 0)**: The square borders at least one mine. We update the cell with the number of adjacent mines (converted to a `char`) and **stop** the recursion for this path.
   - **Case B (Mines == 0)**: The square borders zero mines. We mark it as `'B'` and recursively call DFS on all 8 surrounding squares.

3. **Counting Adjacent Mines**:
   - A simple helper function iterates through the 8 possible direction offsets `(-1, -1)` to `(1, 1)`.
   - It checks boundaries and counts cells containing either an unrevealed mine (`'M'`).

---

## Complexity Analysis

- **Time Complexity**: $\mathcal{O}(M \times N)$
  - In the worst case, every single cell on the board is empty and has no adjacent mines. The DFS will visit each of the $M \times N$ cells exactly once to reveal them. The work done per cell (counting adjacent mines) is $\mathcal{O}(1)$ because it checks a fixed 8 neighbors.
  
- **Space Complexity**: $\mathcal{O}(M \times N)$
  - The space complexity is determined by the maximum depth of the recursion stack during the DFS. In the worst-case scenario (e.g., a completely empty board or a long winding path of empty squares), the recursion could go as deep as the total number of cells, requiring $\mathcal{O}(M \times N)$ auxiliary space.
