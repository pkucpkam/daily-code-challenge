-- Solution: GROUP BY & ORDER BY COUNT DESC with LIMIT 1
-- Time Complexity: O(N log K) where N = number of orders, K = number of distinct customers
-- Space Complexity: O(K) for grouping

SELECT customer_number
FROM Orders
GROUP BY customer_number
ORDER BY COUNT(*) DESC
LIMIT 1;

-- Alternative Solution 1 (Follow-up: Handle ties using DENSE_RANK / RANK):
-- WITH CustomerOrderRank AS (
--     SELECT 
--         customer_number,
--         DENSE_RANK() OVER (ORDER BY COUNT(*) DESC) AS rnk
--     FROM Orders
--     GROUP BY customer_number
-- )
-- SELECT customer_number
-- FROM CustomerOrderRank
-- WHERE rnk = 1;

-- Alternative Solution 2 (Follow-up: Handle ties using Subquery & HAVING COUNT = MAX):
-- SELECT customer_number
-- FROM Orders
-- GROUP BY customer_number
-- HAVING COUNT(*) = (
--     SELECT MAX(order_count)
--     FROM (
--         SELECT COUNT(*) AS order_count
--         FROM Orders
--         GROUP BY customer_number
--     ) AS sub
-- );
