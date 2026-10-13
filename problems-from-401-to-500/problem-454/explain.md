# 637. Average of Levels in Binary Tree

## Idea

To compute the average value for each level, we process the tree level by level using breadth-first search (BFS).

A queue is perfect for this because it naturally keeps nodes in order by depth. For each level:

- count how many nodes are in the current level
- sum their values
- divide the sum by the level size
- store the average in the result list

This ensures every level is handled independently and we never mix values from different levels.

## Key Observation

The average of a level is:

`sum of all node values on that level / number of nodes on that level`

If we traverse the tree level by level, we can compute this in one pass without extra complicated logic.

## Correctness

At the start of each BFS iteration, the queue contains exactly the nodes of the current level (in order). We determine `levelSize` as the number of nodes currently in the queue, then process exactly those nodes.

For each node in this level:

- we add its value to `levelSum`
- we enqueue its children for the next level

After processing all nodes in the level, `levelSum / levelSize` is exactly the average value of that depth. Since each level is processed once and each node contributes exactly once, the result list contains the average for every level in the tree.

## Complexity

- **Time:** `O(n)`
- **Space:** `O(w)` where `w` is the maximum width of the tree, plus the output list

## Java Solution

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class Solution {
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            long levelSum = 0L;

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                levelSum += node.val;

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            result.add(levelSum / (double) levelSize);
        }

        return result;
    }
}
```
