SELECT * FROM t_recipes r;

SELECT * FROM t_categories c ;

SELECT * FROM t_recipes_categories rc ;

SELECT * FROM t_accounts a ;

SELECT * FROM t_ingredients i;

SELECT * FROM t_unities u ;

SELECT * FROM t_roles;

-- Recette
SELECT * 
FROM t_recipes r
WHERE r.username NOT IN (SELECT a.username FROM t_accounts a);

--

SELECT r.id AS recipe_id, r.recipe_name, r.recipe_picture, i.ingredient_name, ri.quantity
FROM t_recipes r
JOIN t_recipes_ingredients ri ON r.id = ri.id_recipe
JOIN t_ingredients i ON ri.id_ingredient = i.id;

SELECT r.id AS recipe_id, 
       r.recipe_name, 
       r.recipe_picture, 
       i.ingredient_name, 
       ri.quantity, 
       ri.unity AS unity
FROM t_recipes r
JOIN t_recipes_ingredients ri ON r.id = ri.id_recipe
JOIN t_ingredients i ON ri.id_ingredient = i.id;


SELECT * 
FROM t_recipes_ingredients ri
JOIN t_recipes r ON r.id = ri.id_recipe
JOIN t_ingredients i ON i.id = ri.id_ingredient;

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

-- 

SELECT * FROM t_accounts;

-- Avoir une recette avec la quantité & le nom de l'entité
SELECT i.ingredient_name, i.quantity, u.unity_name
FROM t_ingredients i
JOIN t_unities u ON i.id_unity = u.id;

SELECT * FROM t_accounts WHERE upper(username) = upper('sss');

SELECT username, role_name 
FROM t_accounts a
JOIN t_roles r ON a.id_role = r.id;

SELECT a.id, a.username, r.role_name 
FROM t_accounts a
JOIN t_roles r ON a.id_role = r.id;

SELECT id, username FROM t_accounts;

SELECT 
    a.username,
    a.password,
    r.role_name,
    a.id_role
FROM t_accounts a
JOIN t_roles r ON a.id_role = r.id
WHERE a.username = 'dubois.sabrina84@gmail.com';
