SELECT * FROM t_recipes r;

SELECT * FROM t_categories c ;

SELECT * FROM t_recipes_categories rc ;

SELECT * FROM t_accounts a ;

SELECT * FROM t_ingredients i;

SELECT * FROM t_unities u ;

-- Recette
SELECT * 
FROM t_recipes r
WHERE r.username NOT IN (SELECT a.username FROM t_accounts a);

--
SELECT * 
FROM t_recipes r
WHERE username IS NULL OR username = '';

--
SELECT * 
FROM information_schema.table_constraints
WHERE table_name = 't_recipes' AND constraint_type = 'FOREIGN KEY';

-- Sélectionner le compte par utilisateur
SELECT * 
FROM t_accounts a
WHERE username = 'sss@sss.com';

-- Avoir une recette avec la quantité & le nom de l'entité
SELECT i.ingredient_name, i.quantity, u.unity_name
FROM t_ingredients i
JOIN t_unities u ON i.id_unity = u.id;

