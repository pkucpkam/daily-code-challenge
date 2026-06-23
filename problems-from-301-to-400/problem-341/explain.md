# Breadth-First Search (BFS) Solution

## Intuition

The problem asks for the **minimum** number of mutations to convert a `startGene` into an `endGene`. This naturally maps to finding the shortest path in an unweighted graph, which is exactly what Breadth-First Search (BFS) is designed for. 

Here is how we model the problem:
- **Nodes**: Gene strings (like `"AACCGGTT"`).
- **Edges**: A connection exists between two nodes if they differ by exactly one character.
- **Shortest Path**: The minimum number of edges (mutations) to get from `startGene` to `endGene`.

## Algorithm

1. **Gene Bank as a Set**: We convert the `bank` array into a `HashSet` called `bankSet`. This gives us `O(1)` constant-time lookups to instantly check if a newly mutated gene is valid. If `endGene` isn't in `bankSet`, we can immediately return `-1` because the goal is unreachable.
2. **The Queue**: We use a queue to perform our level-by-level traversal. We start by putting `startGene` into the queue and setting a counter `mutations = 0`.
3. **Level-by-Level Traversal (BFS)**:
   - We check how many nodes are in the queue for the current level.
   - For each node, we check if it is the `endGene`. If so, we return `mutations`.
   - Otherwise, we generate all possible valid next steps. For each of the 8 characters in the string, we swap it with `'A'`, `'C'`, `'G'`, and `'T'` and check if this new gene is in the `bankSet`.
4. **Preventing Cycles (Visited nodes)**: Whenever we find a valid mutated gene in the `bankSet`, we add it to the queue **and immediately remove it from the `bankSet`**. Removing it ensures we never revisit the same gene and avoid infinite loops.
5. **Increment Counter**: After finishing all nodes at the current level, we increment `mutations` by `1` and move to the next level.
6. **No Path Exists**: If the queue becomes empty and we never found the `endGene`, we return `-1`.

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N)$, where $N$ is the number of genes in the `bank`. In the worst case, we check each gene in the bank exactly once. The inner operations to generate mutations take constant time (8 characters $\times$ 4 choices = 32 operations), so they do not increase the asymptotic complexity.
- **Space Complexity:** $\mathcal{O}(N)$, for storing the `bankSet` and the nodes in the `Queue`. In the worst case, all valid genes might be stored in the queue at the same time.
