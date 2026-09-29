# 610. Triangle Judgement

**Difficulty:** Easy  
**Topics:** Database

---

## Problem Statement

Report for every three line segments whether they can form a triangle.

Return the result table in any order.

A triangle is valid if the sum of any two sides is greater than the third side.

---

## SQL Schema

```sql
Table: Triangle

+-------------+------+
| Column Name | Type |
+-------------+------+
| x           | int  |
| y           | int  |
| z           | int  |
+-------------+------+
```

In SQL, `(x, y, z)` is the primary key column for this table.
Each row of this table contains the lengths of three line segments.

---

## Example

### Example 1

**Input:**

```text
Triangle table:
+----+----+----+
| x  | y  | z  |
+----+----+----+
| 13 | 15 | 30 |
| 10 | 20 | 15 |
+----+----+----+
```

**Output:**

```text
+----+----+----+----------+
| x  | y  | z  | triangle |
+----+----+----+----------+
| 13 | 15 | 30 | No       |
| 10 | 20 | 15 | Yes      |
+----+----+----+----------+
```

---

