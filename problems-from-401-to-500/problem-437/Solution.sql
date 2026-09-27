# Write your MySQL query statement below

SELECT SP.name FROM SalesPerson SP
WHERE NOT EXISTS (
    SELECT 1 
    FROM Orders O 
    INNER JOIN Company C ON O.com_id = C.com_id
    WHERE SP.sales_id = O.sales_id AND C.name = 'RED'
)