SELECT * FROM t_recipes r;
SELECT * FROM t_categories c ;
SELECT * FROM t_recipes_categories rc ;
SELECT * FROM t_accounts a ;
SELECT * FROM t_ingredients i;
SELECT * FROM t_recipes_ingredients_unities riu ;
SELECT * FROM t_unities u ;
SELECT * FROM t_roles ro;
SELECT * FROM t_steps s;

-- ************ 🧩 Inspection des colonnes / métadonnées ************
-- 🔍 Affiche les noms de colonnes de t_categories
SELECT column_name FROM information_schema.columns WHERE table_name = 't_categories';
-- 🔍 Affiche toutes les infos des colonnes de t_categories
SELECT * FROM information_schema.columns WHERE table_name = 't_categories';
-- 🔍 Voir les colonnes de t_ingredients
SELECT column_name FROM information_schema.columns WHERE table_name = 't_ingredients';
SELECT * FROM information_schema.columns WHERE table_name = 't_ingredients';

SELECT ingredient_name FROM t_ingredients WHERE ingredient_name IN ('Flocons d''avoine', 'Banane mûre', 'Pépites de chocolat', 'Beurre de cacahuète');
SELECT unity_name FROM t_unities WHERE unity_name IN ('g', 'unité', 'c. à soupe');

-- 🔍 Voir les contraintes de t_recipes
SELECT * 
FROM information_schema.table_constraints
WHERE table_name = 't_recipes' AND constraint_type = 'FOREIGN KEY';

-- 🔍 Voir la dernière valeur de la séquence des rôles
SELECT last_value FROM t_roles_id_seq;

-- 🔍 Affiche la dernière valeur utilisée pour l’auto-incrément de la table t_roles
SELECT last_value FROM t_roles_id_seq;

-- 🔍 Voir les contraintes de type clé étrangère sur la table t_recipes
SELECT * 
FROM information_schema.table_constraints
WHERE table_name = 't_recipes' AND constraint_type = 'FOREIGN KEY';

-- Voir les colonnes de la table t_ingredients
SELECT column_name 
FROM information_schema.columns 
WHERE table_name = 't_ingredients';
SELECT * 
FROM information_schema.columns 
WHERE table_name = 't_categories';

-- ************ 🔐 Requêtes liées aux utilisateurs et rôles ************
-- 🔍 Affiche les utilisateurs avec leur rôle
SELECT 
    a.id, 
    a.username, 
    r.role_name 
FROM t_accounts a
JOIN t_roles r ON a.id_role = r.id;

-- 🔍 Liste les utilisateurs avec ID + nom + rôle
SELECT username, role_name 
FROM t_accounts a
JOIN t_roles r ON a.id_role = r.id;

-- 🔍 Affiche les infos d'un utilisateur (mot de passe inclus) selon son email
SELECT 
    a.username,
    a.password,
    r.role_name,
    a.id_role
FROM t_accounts a
JOIN t_roles r ON a.id_role = r.id
WHERE UPPER(a.username) = UPPER('dubois.sabrina84@gmail.com');

-- 🔍 Trouve l'ID d'un compte utilisateur en comparant l'email (insensible à la casse)
SELECT id FROM t_accounts WHERE UPPER(username) = UPPER('dubois.sabrina84@gmail.com');
SELECT id FROM t_accounts WHERE username = 'dubois.sabrina84@gmail.com';

-- 🔍 Liste les IDs et usernames de tous les comptes
SELECT id, username FROM t_accounts;

-- ************ 🔄 Intégrité des recettes ************
-- 🔍 Affiche les recettes avec un compte inexistant (problème d'intégrité)
SELECT * 
FROM t_recipes r
WHERE r.id_account NOT IN (SELECT a.id FROM t_accounts a);

-- ************ 📋 Étapes d’une recette ************
-- 🔍 Affiche les étapes d'une recette (id = 40) triées dans l'ordre
SELECT * FROM t_steps WHERE id_recipe = 7 ORDER BY step_number;

-- 🔍 Récupérer les étapes d’une recette précise (ex: id=5) dans l’ordre
SELECT *
FROM t_steps
WHERE id_recipe = 7
ORDER BY step_number;

-- 🔍 Affiche les recettes avec leurs étapes dans l’ordre (numérotées) 
SELECT 
    r.id AS recipe_id,
    r.recipe_name,
    s.step_number,
    s.step_description
FROM t_recipes r
LEFT JOIN t_steps s ON r.id = s.id_recipe
ORDER BY r.id, s.step_number;

-- 🔍 Affiche pour chaque recette un tableau JSON listant toutes ses étapes
SELECT 
    r.id AS recipe_id,
    r.recipe_name,
    JSON_AGG(
        JSON_BUILD_OBJECT(
            'step_number', s.step_number,
            'step_description', s.step_description
        )
        ORDER BY s.step_number
    ) AS steps
FROM t_recipes r
LEFT JOIN t_steps s ON r.id = s.id_recipe
GROUP BY r.id, r.recipe_name
ORDER BY r.id;


-- ************ 🍽️ Détails des recettes avec ingrédients + unités ************
--🔍 Affiche les recettes avec tous leurs ingrédients, quantités et unités (détail complet)
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

-- ************  🍱 Recettes + ingrédients en JSON ************ 
-- 🔍 Affiche chaque recette  + auteur + ingrédients au format JSON (groupés)
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
    ) ORDER BY i.ingredient_name) AS ingredients
FROM t_recipes r
JOIN t_accounts a ON r.id_account = a.id
LEFT JOIN t_recipes_ingredients_unities riu ON r.id = riu.id_recipe
LEFT JOIN t_ingredients i ON riu.id_ingredient = i.id
LEFT JOIN t_unities u ON riu.id_unity = u.id
GROUP BY r.id, r.recipe_name, r.difficulty, r.recipe_picture, a.username
ORDER BY r.id;

-- Requête : get_recipe_full_details
-- 🔍 Récupère toutes les informations d'une recette:titre + photo + difficulté + compte + ingrédients (quantités & unités) + les étapes
SELECT 
    r.id AS recipe_id,
    r.recipe_name,
    r.recipe_picture,
    r.difficulty,
    a.username AS author,
     -- ingrédients agrégés en JSON
    (
      SELECT JSON_AGG(
        JSONB_BUILD_OBJECT(
          'ingredient', i2.ingredient_name,
          'quantity', riu2.quantity,
          'unity', u2.unity_name
        ) ORDER BY i2.ingredient_name
      )::text
      FROM t_recipes_ingredients_unities riu2
      JOIN t_ingredients i2 ON riu2.id_ingredient = i2.id
      JOIN t_unities u2 ON riu2.id_unity = u2.id
      WHERE riu2.id_recipe = r.id
    ) AS ingredients,
    -- étapes en JSON
    (
      SELECT JSON_AGG(
        JSONB_BUILD_OBJECT(
          'step_number', s.step_number,
          'step_description', s.step_description
        ) ORDER BY s.step_number
      )::text
      FROM t_steps s
      WHERE s.id_recipe = r.id
    ) AS steps
FROM t_recipes r
JOIN t_accounts a ON r.id_account = a.id
WHERE r.id = 40 
GROUP BY r.id, r.recipe_name, r.recipe_picture, r.difficulty, a.username;

-- ************ 🍞 Recettes contenant l’ingrédient “farine” ************
-- 🔍 Affiche chaque recette  + auteur + ingrédients au format JSON (groupés) + filtre sur les recettes contenant l'ingrédient "farine"
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
    ) ORDER BY i.ingredient_name) AS ingredients
FROM t_recipes r
JOIN t_accounts a ON r.id_account = a.id
JOIN t_recipes_ingredients_unities riu ON r.id = riu.id_recipe
JOIN t_ingredients i ON riu.id_ingredient = i.id
JOIN t_unities u ON riu.id_unity = u.id
WHERE r.id IN (
    SELECT r_sub.id
    FROM t_recipes r_sub
    JOIN t_recipes_ingredients_unities riu_sub ON r_sub.id = riu_sub.id_recipe
    JOIN t_ingredients i_sub ON riu_sub.id_ingredient = i_sub.id
    WHERE LOWER(i_sub.ingredient_name) = 'farine'
)
GROUP BY r.id, r.recipe_name, r.difficulty, r.recipe_picture, a.username
ORDER BY r.id;

-- ************ 🥞 Recettes avec uniquement un ingrédient : ex --> farine ************
-- 🔍 Affiche toutes les recettes qui contiennent "farine" (au moins un des ingrédients)
SELECT r.id, r.recipe_name
FROM t_recipes r
JOIN t_recipes_ingredients_unities riu ON r.id = riu.id_recipe
JOIN t_ingredients i ON i.id = riu.id_ingredient
GROUP BY r.id, r.recipe_name
HAVING COUNT(*) = 1 AND LOWER(MAX(i.ingredient_name)) = 'farine';

-- 🔍 Liste des recettes contenant farine 
SELECT DISTINCT r.recipe_name
FROM t_recipes r
JOIN t_recipes_ingredients_unities riu ON r.id = riu.id_recipe
JOIN t_ingredients i ON i.id = riu.id_ingredient
WHERE LOWER(i.ingredient_name) = 'farine';

-- ************ 📊 Table de jointure brute ************
-- 🔍 Voir la table de jointure / relations recette-ingrédient-unité
SELECT * FROM t_recipes_ingredients_unities;

-- ************ 📈 Statistiques et synthèses générales ************
-- 🔍 Nombre de recettes par catégorie
SELECT c.category_name, COUNT(rc.id_recipe) AS nb_recettes
FROM t_categories c
LEFT JOIN t_recipes_categories rc ON c.id = rc.id_category
GROUP BY c.category_name
ORDER BY nb_recettes DESC;

-- 🔍 Nombre d’ingrédients utilisés par recette
SELECT r.id, r.recipe_name, COUNT(riu.id_ingredient) AS nb_ingredients
FROM t_recipes r
LEFT JOIN t_recipes_ingredients_unities riu ON r.id = riu.id_recipe
GROUP BY r.id, r.recipe_name
ORDER BY nb_ingredients DESC;

-- 🔍 Nombre de recettes par compte
SELECT a.username, COUNT(r.id) AS nb_recettes
FROM t_accounts a
LEFT JOIN t_recipes r ON a.id = r.id_account
GROUP BY a.username
ORDER BY nb_recettes DESC;

-- ************ 🔎 Recherche avancée ************
-- 🔍 Recettes contenant un ou plusieurs ingrédients donnés (ex: farine, sucre)
SELECT DISTINCT r.id, r.recipe_name
FROM t_recipes r
JOIN t_recipes_ingredients_unities riu ON r.id = riu.id_recipe
JOIN t_ingredients i ON i.id = riu.id_ingredient
WHERE LOWER(i.ingredient_name) IN ('farine', 'sucre')
ORDER BY r.recipe_name;

-- 🔍 Recettes sans aucune catégorie associée (pour vérifier intégrité)
SELECT r.id, r.recipe_name
FROM t_recipes r
LEFT JOIN t_recipes_categories rc ON r.id = rc.id_recipe
WHERE rc.id_category IS NULL;

-- 🔍 Recettes avec plus de 5 étapes
SELECT r.id, r.recipe_name, COUNT(s.id) AS nb_etapes
FROM t_recipes r
LEFT JOIN t_steps s ON r.id = s.id_recipe
GROUP BY r.id, r.recipe_name
HAVING COUNT(s.id) > 5
ORDER BY nb_etapes DESC;

-- ************ 🛠️ Vérifications et intégrité ************
-- 🔍 Ingrédients non utilisés dans aucune recette
SELECT i.id, i.ingredient_name
FROM t_ingredients i
LEFT JOIN t_recipes_ingredients_unities riu ON i.id = riu.id_ingredient
WHERE riu.id_ingredient IS NULL;

--  🔍 Unités non utilisées
SELECT u.id, u.unity_name
FROM t_unities u
LEFT JOIN t_recipes_ingredients_unities riu ON u.id = riu.id_unity
WHERE riu.id_unity IS NULL;

SHOW CREATE TABLE t_steps;
DELETE FROM t_steps WHERE id_recipe = 8;
INSERT INTO t_steps (id_recipe, step_number, step_description) VALUES (8, 1, 'Test1');
