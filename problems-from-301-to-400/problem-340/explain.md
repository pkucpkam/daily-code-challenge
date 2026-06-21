# 429. N-ary Tree Level Order Traversal - Solution Explanation

## Problem Overview

We are given the root of an N-ary tree, and our task is to return its level order traversal. A level order traversal visits all nodes at the present depth level before moving on to the nodes at the next depth level.

## Approach: Breadth-First Search (BFS)

The most natural way to perform a level order traversal is by using **Breadth-First Search (BFS)** with a queue.

### Step-by-Step Walkthrough

1. **Edge Case**: If the `root` is `null`, there are no nodes to traverse, so we immediately return an empty list `[]`.
2. **Initialization**: 
   - Create a `List<List<Integer>>` called `result` to store the final level-by-level output.
   - Create a `Queue<Node>` to help with our BFS traversal.
   - Add the `root` node to the queue to kick off the process.
3. **Traversal (while queue is not empty)**:
   - Determine the number of nodes at the current level by checking the queue's size: `size = queue.size()`.
   - Create a temporary list `currentLevel` to store the values of the nodes at this level.
   - Loop exactly `size` times:
     - `poll` (remove and get) the node at the front of the queue.
     - Add this node's value to the `currentLevel` list.
     - Add all of this node's children to the queue so they can be processed in the next iteration (the next level).
   - Once the loop finishes, the `currentLevel` is complete, so add it to the `result` list.
4. **Return**: Once the queue is completely empty, it means we have visited every node in the tree. Return the `result`.

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N)$ where $N$ is the number of nodes in the N-ary tree. Every node is added to and removed from the queue exactly once. We also iterate through each node's children exactly once.
- **Space Complexity:** $\mathcal{O}(N)$. In the worst-case scenario (e.g., a tree with depth 2 where the root has $N-1$ children), the queue will hold up to $N-1$ nodes at the same time. Also, the output array will store $N$ elements, which takes up $\mathcal{O}(N)$ space.
