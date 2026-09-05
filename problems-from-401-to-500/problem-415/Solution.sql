-- Solution: Self JOIN with GROUP BY & HAVING
-- Time Complexity: O(N) with index, or O(N log N)
-- Space Complexity: O(N) for grouping

SELECT m.name
FROM Employee e
JOIN Employee m ON e.managerId = m.id
GROUP BY m.id, m.name
HAVING COUNT(e.id) >= 5;

-- Alternative Solution: Subquery with IN
-- SELECT name
-- FROM Employee
-- WHERE id IN (
--     SELECT managerId
--     FROM Employee
--     GROUP BY managerId
--     HAVING COUNT(*) >= 5
-- );
