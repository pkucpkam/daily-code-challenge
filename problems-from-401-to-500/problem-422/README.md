# 584. Find Customer Referee

**Difficulty:** Easy 🟢  
**Topics:** Database / SQL  

---

## Problem Statement

### Table: `Customer`

```text
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| id          | int     |
| name        | varchar |
| referee_id  | int     |
+-------------+---------+
```

- In SQL, `id` is the primary key column for this table.
- Each row of this table indicates the id of a customer, their name, and the id of the customer who referred them.

---

Find the names of the customer that are either:

- Referred by any customer with `id != 2`.
- Not referred by any customer.

Return the result table in **any order**.

The result format is in the following example.

---

## Examples

### Example 1

#### Input: `Customer` table

```text
+----+------+------------+
| id | name | referee_id |
+----+------+------------+
| 1  | Will | null       |
| 2  | Jane | null       |
| 3  | Alex | 2          |
| 4  | Bill | null       |
| 5  | Zack | 1          |
| 6  | Mark | 2          |
+----+------+------------+
```

#### Output

```text
+------+
| name |
+------+
| Will |
| Jane |
| Bill |
| Zack |
+------+
```

#### Explanation

- Customers with `id` 1, 2, and 4 (Will, Jane, and Bill) are not referred by any customer (`referee_id` is `null`).
- Customer with `id` 5 (Zack) is referred by customer 1 (`referee_id = 1 != 2`).
- Customers with `id` 3 and 6 (Alex and Mark) are referred by customer 2 (`referee_id = 2`), so they are excluded.