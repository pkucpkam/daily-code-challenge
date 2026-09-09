-- Solution: LEFT JOIN with NULL check
-- Time Complexity: O(N + M) where N = number of employees, M = number of bonus records
-- Space Complexity: O(1) beyond output

SELECT 
    e.name, 
    b.bonus
FROM Employee e
LEFT JOIN Bonus b ON e.empId = b.empId
WHERE b.bonus < 1000 OR b.bonus IS NULL;

-- Alternative Solution: Using IFNULL / COALESCE
-- SELECT 
--     e.name, 
--     b.bonus
-- FROM Employee e
-- LEFT JOIN Bonus b ON e.empId = b.empId
-- WHERE IFNULL(b.bonus, 0) < 1000;
