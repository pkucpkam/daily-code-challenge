# 610. Triangle Judgement

## Idea

A triangle is valid only when the sum of any two sides is greater than the third side.

For each row in the `Triangle` table, we check all three triangle inequalities:

- `x + y > z`
- `x + z > y`
- `y + z > x`

If all three are true, the row forms a triangle; otherwise it does not.

## SQL Solution

```sql
SELECT
    x,
    y,
    z,
    CASE
        WHEN x + y > z AND x + z > y AND y + z > x THEN 'Yes'
        ELSE 'No'
    END AS triangle
FROM Triangle;
```

## Why This Works

For any three side lengths to form a triangle, the longest side must be less than the sum of the other two sides. Checking each pair of sides against the remaining side guarantees the triangle condition holds in all directions.

If any one inequality fails, then the three segments cannot form a triangle.

## Time Complexity

- **Time:** `O(n)` for `n` rows in the table
- **Space:** `O(1)` extra space, aside from the result set

## Example

```text
Input:
+----+----+----+
| x  | y  | z  |
+----+----+----+
| 13 | 15 | 30 |
| 10 | 20 | 15 |
+----+----+----+

Output:
+----+----+----+----------+
| x  | y  | z  | triangle |
+----+----+----+----------+
| 13 | 15 | 30 | No       |
| 10 | 20 | 15 | Yes      |
+----+----+----+----------+
```
