# 627. Swap Salary

**Difficulty:** Easy 🟢  
**Topics:** Database / SQL  

---

## Problem Statement

### Table: `Salary`

```text
+-------------+----------+
| Column Name | Type     |
+-------------+----------+
| id          | int      |
| name        | varchar  |
| sex         | ENUM     |
| salary      | int      |
+-------------+----------+
```

- `id` is the primary key (column with unique values) for this table.
- The `sex` column is an ENUM (category) value of type (`'m'`, `'f'`).
- The table contains information about an employee.

---

Write a solution to swap all `'f'` and `'m'` values (i.e., change all `'f'` values to `'m'` and vice versa) with a single update statement and no intermediate temporary tables.

Note that you must write a single update statement, do not write any select statement for this problem.

The result format is in the following example.

---

## Examples

### Example 1

#### Input: `Salary` table

```text
+----+------+-----+--------+
| id | name | sex | salary |
+----+------+-----+--------+
| 1  | A    | m   | 2500   |
| 2  | B    | f   | 1500   |
| 3  | C    | m   | 5500   |
| 4  | D    | f   | 500    |
+----+------+-----+--------+
```

#### Output

```text
+----+------+-----+--------+
| id | name | sex | salary |
+----+------+-----+--------+
| 1  | A    | f   | 2500   |
| 2  | B    | m   | 1500   |
| 3  | C    | f   | 5500   |
| 4  | D    | m   | 500    |
+----+------+-----+--------+
```

#### Explanation

- `(1, A)` and `(3, C)` were changed from `'m'` to `'f'`.
- `(2, B)` and `(4, D)` were changed from `'f'` to `'m'`.