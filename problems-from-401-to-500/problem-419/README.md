# 577. Employee Bonus

**Difficulty:** Easy 🟢  
**Topics:** Database / SQL  

---

## Problem Statement

### Table: `Employee`

```text
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| empId       | int     |
| name        | varchar |
| supervisor  | int     |
| salary      | int     |
+-------------+---------+
```

- `empId` is the column with unique values for this table.
- Each row of this table indicates the name and the ID of an employee in addition to their salary and the id of their manager.

### Table: `Bonus`

```text
+-------------+------+
| Column Name | Type |
+-------------+------+
| empId       | int  |
| bonus       | int  |
+-------------+------+
```

- `empId` is the column of unique values for this table.
- `empId` is a foreign key (reference column) to `empId` from the `Employee` table.
- Each row of this table contains the id of an employee and their respective bonus.

---

Write a solution to report the name and bonus amount of each employee who satisfies either of the following:

- The employee has a bonus less than `1000`.
- The employee did not get any bonus.

Return the result table in **any order**.

The result format is in the following example.

---

## Examples

### Example 1

#### Input:

`Employee` table:

```text
+-------+--------+------------+--------+
| empId | name   | supervisor | salary |
+-------+--------+------------+--------+
| 3     | Brad   | null       | 4000   |
| 1     | John   | 3          | 1000   |
| 2     | Dan    | 3          | 2000   |
| 4     | Thomas | 3          | 4000   |
+-------+--------+------------+--------+
```

`Bonus` table:

```text
+-------+-------+
| empId | bonus |
+-------+-------+
| 2     | 500   |
| 4     | 2000  |
+-------+-------+
```

#### Output

```text
+------+-------+
| name | bonus |
+------+-------+
| Brad | null  |
| John | null  |
| Dan  | 500   |
+------+-------+
```

#### Explanation

- Brad and John did not get any bonus.
- Dan has a bonus of 500, which is less than 1000.
- Thomas has a bonus of 2000, which is not less than 1000, so he is not included.