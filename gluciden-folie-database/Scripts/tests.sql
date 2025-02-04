SELECT * FROM t_recipes tr;

SELECT * FROM t_categories tc ;

SELECT * FROM t_recipes_categories trc ;

SELECT * FROM t_accounts ta ;

SELECT * 
FROM t_recipes tr
WHERE tr.username NOT IN (SELECT ta.username FROM t_accounts ta);

SELECT * 
FROM t_recipes 
WHERE username IS NULL OR username = '';

SELECT * 
FROM information_schema.table_constraints
WHERE table_name = 't_recipes' AND constraint_type = 'FOREIGN KEY';

SELECT * 
FROM t_accounts 
WHERE username = 'sss@sss.com';
