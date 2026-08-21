# 547. Number of Provinces

## Intuition

The problem asks us to find the total number of **provinces** (connected components) in an undirected graph of `n` cities. 
The connections between cities are represented by an $n \times n$ adjacency matrix `isConnected`, where `isConnected[i][j] = 1` means city `i` and city `j` are directly connected.

Since connection is symmetric and transitive (if city $A$ is connected to $B$ and $B$ to $C$, then $A$ is connected to $C$), each province is a connected component.

We need to count how many disjoint connected components exist in the graph.

---

## Key Insights & Optimal Solution (Depth-First Search)

### Depth-First Search (DFS)

We maintain a `visited` boolean array of size $n$ to track which cities have already been processed as part of an identified province.

1. Iterate through each city $i$ from $0$ to $n-1$:
   - If city $i$ has **not** been visited yet:
     - Increment `count` by $1$ (a new province is discovered).
     - Perform `dfs(i)` to visit city $i$ and recursively mark all cities connected to city $i$ as `visited`.
2. Return `count`.

### DFS Recursive Traversal
When visiting city $i$:
- Mark `visited[i] = true`.
- Iterate through all potential neighbors $j$ from $0$ to $n-1$:
  - If `isConnected[i][j] == 1` and `!visited[j]`, recursively call `dfs(j)`.

---

## Code Implementation (Java)

```java
class Solution {
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int count = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(isConnected, visited, i);
                count++;
            }
        }

        return count;
    }

    private void dfs(int[][] isConnected, boolean[] visited, int i) {
        visited[i] = true;
        for (int j = 0; j < isConnected.length; j++) {
            if (isConnected[i][j] == 1 && !visited[j]) {
                dfs(isConnected, visited, j);
            }
        }
    }
}
```

---

## Walkthrough Example

Consider Example 1: `isConnected = [[1,1,0],[1,1,0],[0,0,1]]`

- City 0 is connected to: City 0, City 1
- City 1 is connected to: City 0, City 1
- City 2 is connected to: City 2

```text
Initial state: visited = [false, false, false], count = 0

1. i = 0: visited[0] is false
   - Increment count to 1
   - Call dfs(0):
     - Mark visited[0] = true
     - j = 1: isConnected[0][1] == 1 and !visited[1] -> Call dfs(1)
       - Mark visited[1] = true
       - j = 0: visited[0] is true
       - j = 1: visited[1] is true
       - j = 2: isConnected[1][2] == 0
       - dfs(1) returns
     - j = 2: isConnected[0][2] == 0
     - dfs(0) returns

2. i = 1: visited[1] is true (skipped)

3. i = 2: visited[2] is false
   - Increment count to 2
   - Call dfs(2):
     - Mark visited[2] = true
     - dfs(2) returns

Final count = 2
```

---

## Alternative Approaches

### 1. Disjoint Set Union (Union-Find)

Initialize a Disjoint Set Union (DSU) structure where every city initially forms its own set (parent of $i = i$).

- Iterate over all unique pairs $(i, j)$ where $i < j$:
  - If `isConnected[i][j] == 1`, call `union(i, j)`.
  - Decrement component count whenever two previously disjoint components are merged.
- **Time Complexity:** $\mathcal{O}(N^2 \cdot \alpha(N))$ where $\alpha$ is the Inverse Ackermann function.
- **Space Complexity:** $\mathcal{O}(N)$ for parent & rank arrays.

### 2. Breadth-First Search (BFS)

Use a `Queue<Integer>` instead of recursion:
- Iterate through each city $i$. If `!visited[i]`, increment `count`, add $i$ to queue, and mark `visited[i] = true`.
- While queue is not empty, poll city $u$. For all $v$ where `isConnected[u][v] == 1` and `!visited[v]`, mark `visited[v] = true` and enqueue $v$.
- **Time Complexity:** $\mathcal{O}(N^2)$
- **Space Complexity:** $\mathcal{O}(N)$ for queue and visited array.

---

## Complexity Analysis

| Approach | Time Complexity | Space Complexity | Notes |
|:---|:---|:---|:---|
| **DFS (Recommended)** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | Minimal overhead, highly efficient |
| **BFS** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | Iterative queue traversal |
| **Union-Find (DSU)** | $\mathcal{O}(N^2 \cdot \alpha(N))$ | $\mathcal{O}(N)$ | Excellent for dynamic graph connections |

- **Time Complexity:** $\mathcal{O}(N^2)$ — We check all entries of the $N \times N$ matrix to find connected cities.
- **Space Complexity:** $\mathcal{O}(N)$ — Auxiliary space for the `visited` array and recursion stack up to depth $N$.
