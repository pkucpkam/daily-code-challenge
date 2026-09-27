SELECT id,
CASE
    WHEN p_id IS NULL THEN 'Root'
    WHEN NOT EXISTS (
        SELECT 1
        FROM Tree t2
        WHERE t2.p_id = Tree.id
    ) THEN 'Leaf'
    ELSE 'Inner'
END AS type
FROM Tree;