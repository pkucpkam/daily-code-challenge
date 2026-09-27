# 608. Tree Node

**Difficulty:** Medium 🟡  
**Topics:** Database / SQL  

---

## Problem Statement

### Table: `Tree`

```text
+-------------+------+
| Column Name | Type |
+-------------+------+
| id          | int  |
| p_id        | int  |
+-------------+------+
```

- `id` is the column with unique values for this table.
- Each row of this table contains information about the id of a node and the id of its parent node in a tree.
- The given structure is always a valid tree.

---

Each node in the tree can be one of three types:

- **"Leaf"**: if the node is a leaf node.
- **"Root"**: if the node is the root of the tree.
- **"Inner"**: if the node is neither a leaf node nor a root node.

Write a solution to report the type of each node in the tree.

Return the result table in **any order**.

The result format is in the following example.

---

## Examples

### Example 1

#### Input: `Tree` table

```text
+----+------+
| id | p_id |
+----+------+
| 1  | null |
| 2  | 1    |
| 3  | 1    |
| 4  | 2    |
| 5  | 2    |
+----+------+
```

#### Output

```text
+----+-------+
| id | type  |
+----+-------+
| 1  | Root  |
| 2  | Inner |
| 3  | Leaf  |
| 4  | Leaf  |
| 5  | Leaf  |
+----+-------+
```

#### Explanation

- Node 1 is the root node because its parent node is `null` and it has child nodes 2 and 3.
- Node 2 is an inner node because it has parent node 1 and child nodes 4 and 5.
- Nodes 3, 4, and 5 are leaf nodes because they have parent nodes and they do not have child nodes.

---

### Example 2

#### Input: `Tree` table

```text
+----+------+
| id | p_id |
+----+------+
| 1  | null |
+----+------+
```

#### Output

```text
+----+-------+
| id | type  |
+----+-------+
| 1  | Root  |
+----+-------+
```

#### Explanation

- If there is only one node on the tree, you only need to output its root attributes.

---

## Note

This question is the same as [3054: Binary Tree Nodes](https://leetcode.com/problems/binary-tree-nodes/).