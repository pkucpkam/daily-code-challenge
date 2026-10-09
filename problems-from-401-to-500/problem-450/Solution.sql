-- Solution 1 (Best & Standard ANSI SQL): Searched CASE Expression
-- Time Complexity: O(N) where N is the number of rows in the Salary table
-- Space Complexity: O(1) in-place modification without extra memory

UPDATE Salary
SET sex = CASE 
    WHEN sex = 'm' THEN 'f'
    ELSE 'm'
END;

-- =====================================================================
-- Alternative 1 (MySQL Built-in IF Function): Concise & direct
-- Time Complexity: O(N)
-- Space Complexity: O(1)
-- =====================================================================
-- UPDATE Salary
-- SET sex = IF(sex = 'm', 'f', 'm');

-- =====================================================================
-- Alternative 2 (String REPLACE Trick):
-- Swaps by stripping current sex out of the string 'mf'
-- If sex = 'm' -> 'mf' becomes 'f'; if sex = 'f' -> 'mf' becomes 'm'
-- =====================================================================
-- UPDATE Salary
-- SET sex = REPLACE('mf', sex, '');

-- =====================================================================
-- Alternative 3 (ASCII Arithmetic Trick):
-- ASCII('m') = 109, ASCII('f') = 102 -> Sum = 211
-- 211 - 109 = 102 ('f'), 211 - 102 = 109 ('m')
-- =====================================================================
-- UPDATE Salary
-- SET sex = CHAR(ASCII('m') + ASCII('f') - ASCII(sex));

-- =====================================================================
-- Alternative 4 (Bitwise XOR Trick):
-- ASCII('m') ^ ASCII('f') = 109 ^ 102 = 11
-- 109 ^ 11 = 102 ('f'), 102 ^ 11 = 109 ('m')
-- =====================================================================
-- UPDATE Salary
-- SET sex = CHAR(ASCII(sex) ^ 11);
