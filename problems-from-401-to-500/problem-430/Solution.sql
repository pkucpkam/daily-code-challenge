-- Solution: Using WHERE with OR condition
-- Time Complexity: O(N) where N is the number of rows in the World table
-- Space Complexity: O(1) auxiliary space (excluding result set)

SELECT name, population, area
FROM World
WHERE area >= 3000000 OR population >= 25000000;

-- Alternative Solution: Using UNION
-- In databases where separate indexes exist on `area` and `population` without index merge,
-- UNION allows each branch to utilize its respective B-Tree index.
-- Note that UNION incurs deduplication overhead (temporary table / sort).
--
-- SELECT name, population, area
-- FROM World
-- WHERE area >= 3000000
-- UNION
-- SELECT name, population, area
-- FROM World
-- WHERE population >= 25000000;
