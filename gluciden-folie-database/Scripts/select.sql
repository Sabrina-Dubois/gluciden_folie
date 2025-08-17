SELECT * FROM t_recipes r;
SELECT * FROM t_categories c ;
SELECT * FROM t_recipes_categories rc ;
SELECT * FROM t_accounts a ;
SELECT * FROM t_ingredients i;
SELECT * FROM t_recipes_ingredients_unities riu ;
SELECT * FROM t_unities u ;
SELECT * FROM t_roles ro;
SELECT * FROM t_steps s;

SELECT column_name FROM information_schema.columns WHERE table_name = 't_categories';

SELECT * FROM information_schema.columns WHERE table_name = 't_categories';


-- Recette
SELECT * 
FROM t_recipes r
WHERE r.username NOT IN (SELECT a.username FROM t_accounts a);

--

ALTER TABLE t_ingredients DROP COLUMN id_unity;



-- Voir les colonnes de la table t_ingredients
SELECT column_name 
FROM information_schema.columns 
WHERE table_name = 't_ingredients';
SELECT * 
FROM information_schema.columns 
WHERE table_name = 't_categories';

SELECT 
    r.id AS recipe_id,
    r.recipe_name,
    r.difficulty,
    r.recipe_picture,
    i.ingredient_name,
    riu.quantity,
    u.unity_name
FROM t_recipes r
LEFT JOIN t_recipes_ingredients_unities riu ON r.id = riu.id_recipe
LEFT JOIN t_ingredients i ON riu.id_ingredient = i.id
LEFT JOIN t_unities u ON riu.id_unity = u.id
ORDER BY r.id, i.ingredient_name;

SELECT 
    r.id AS recipe_id,
    r.recipe_name,
    r.difficulty,
    r.recipe_picture,
    riu.id_ingredient,
    riu.quantity,
    riu.id_unity
FROM t_recipes r
LEFT JOIN t_recipes_ingredients_unities riu ON r.id = riu.id_recipe
ORDER BY r.id, riu.id_ingredient;

SELECT 
    r.id AS recipe_id,
    r.recipe_name,
    r.difficulty,
    r.recipe_picture,
    a.username AS author,
    JSON_AGG(JSON_BUILD_OBJECT(
        'ingredient', i.ingredient_name,
        'quantity', riu.quantity,
        'unity', u.unity_name
    ) ORDER BY i.ingredient_name) AS ingredients,
    JSON_AGG(JSON_BUILD_OBJECT(
        'number', s.step_number,
        'description', s.step_description
    ) ORDER BY s.step_number) AS steps
FROM t_recipes r
JOIN t_accounts a ON r.id_account = a.id
LEFT JOIN t_recipes_ingredients_unities riu ON r.id = riu.id_recipe
LEFT JOIN t_ingredients i ON riu.id_ingredient = i.id
LEFT JOIN t_unities u ON riu.id_unity = u.id
GROUP BY r.id, r.recipe_name, r.difficulty, r.recipe_picture, a.username
ORDER BY r.id;


-- 1 ingrédient farine --
SELECT r.id, r.recipe_name
FROM t_recipes r
JOIN t_recipes_ingredients_unities riu ON r.id = riu.id_recipe
JOIN t_ingredients i ON i.id = riu.id_ingredient
GROUP BY r.id, r.recipe_name
HAVING COUNT(*) = 1 AND LOWER(MAX(i.ingredient_name)) = 'Farine';

-- avec farine --
SELECT DISTINCT r.recipe_name
FROM t_recipes r
JOIN t_recipes_ingredients_unities riu ON r.id = riu.id_recipe
JOIN t_ingredients i ON i.id = riu.id_ingredient
WHERE LOWER(i.ingredient_name) = 'farine';

SELECT r.id, r.recipe_name
FROM t_recipes r
JOIN t_recipes_ingredients_unities riu ON r.id = riu.id_recipe
JOIN t_ingredients i ON i.id = riu.id_ingredient
GROUP BY r.id, r.recipe_name
HAVING COUNT(*) = 1 AND LOWER(MAX(i.ingredient_name)) = 'farine';


SELECT * 
FROM t_recipes r
WHERE username IS NULL OR username = '';

--
SELECT * 
FROM information_schema.table_constraints
WHERE table_name = 't_recipes' AND constraint_type = 'FOREIGN KEY';
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
