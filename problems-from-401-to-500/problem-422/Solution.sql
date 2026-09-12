-- Solution: Filter with NULL check (ANSI SQL)
-- Time Complexity: O(N) where N = number of customers
-- Space Complexity: O(1) beyond output

SELECT name
FROM Customer
WHERE referee_id != 2 OR referee_id IS NULL;

-- Alternative Solution 1: Using IFNULL / COALESCE
-- SELECT name
-- FROM Customer
-- WHERE IFNULL(referee_id, 0) != 2;

-- Alternative Solution 2: MySQL NULL-Safe Operator (<=>)
-- SELECT name
-- FROM Customer
-- WHERE NOT (referee_id <=> 2);
