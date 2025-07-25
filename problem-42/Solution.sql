SELECT * FROM Customer 
WHERE id NOT IN (
    SELECT DISTINCT customerId
    FROM Orders
);

-- Better solution
-- SELECT c.name AS Customers
-- FROM Customers c
-- LEFT OUTER JOIN Orders o ON c.id = o.customerId
-- WHERE o.id IS NULL
