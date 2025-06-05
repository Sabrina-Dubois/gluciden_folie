-- ** Cookies healthy aux flocons d'avoine ** 

-- 1. Insérer les ingrédients --
INSERT INTO t_ingredients (ingredient_name) VALUES 
    ('Flocons d''avoine'),
    ('Banane mûre'),
    ('Pépites de chocolat'),
    ('Beurre de cacahuète')
ON CONFLICT (ingredient_name) DO NOTHING;

-- 2. Insérer la recette avec liaison à l'utilisateur --
WITH new_recipe AS (
  INSERT INTO t_recipes (recipe_name, difficulty, recipe_picture, id_account)
  VALUES ('Cookies healthy aux flocons d''avoine', 'Facile', '9be1669d-dc4c-45be-98d6-10d9996c60d5.jpg,
         (SELECT id FROM t_accounts WHERE username = 'dubois.sabrina84@gmail.com'))
  RETURNING id
)

-- 3. Lier les ingrédients avec quantité et unité --
INSERT INTO t_recipes_ingredients_unities (id_recipe, id_ingredient, quantity, id_unity)
SELECT
  new_recipe.id,
  i.id,
  data.quantity,
  u.id
FROM new_recipe,
  (VALUES 
    ('Flocons d''avoine', 100, 'g'),
    ('Banane mûre', 2, 'unité'),
    ('Pépites de chocolat', 50, 'g'),
    ('Beurre de cacahuète', 2, 'c. à soupe')
  ) AS data(ingredient_name, quantity, unity_name)
JOIN t_ingredients i ON i.ingredient_name = data.ingredient_name
JOIN t_unities u ON u.unity_name = data.unity_name;

-- ** Mousse au chocolat végane ** 
INSERT INTO t_ingredients(ingredient_name) VALUES
    ('Chocolat noir'),
    ('Jus de pois chiche'),
	('Édulcorant en poudre'),
	('Vanille liquide')
ON CONFLICT (ingredient_name) DO NOTHING;

WITH new_recipe AS (
	INSERT INTO t_recipes (recipe_name, difficulty, recipe_picture, id_account)
	VALUES ('Mousse au chocolat végane', 'Facile','db7cf8b7-ca20-4445-8af4-79575adc01a5.png',
			(SELECT id FROM t_accounts WHERE username = 'dubois.sabrina84@gmail.com'))
	RETURNING id
)

INSERT INTO t_recipes_ingredients_unities (id_recipe, id_ingredient, quantity, id_unity)
SELECT 
	new_recipe.id,
	i.id,
	data.quantity,
	u.id
FROM new_recipe,
	(VALUES 
		('Chocolat noir','200','g'),
    	('Jus de pois chiche','150','ml'),	
		('Édulcorant en poudre','2','c. à soupe'),
		('Vanille liquide','1','c. à café')
	) AS data(ingredient_name, quantity, unity_name)
JOIN t_ingredients i ON i.ingredient_name = data.ingredient_name
JOIN t_unities u ON u.unity_name = data.unity_name;
    