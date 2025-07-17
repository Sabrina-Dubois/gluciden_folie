-- Supprimer d'abord les liens dans t_recipes_ingredients_unities
DELETE FROM t_recipes_ingredients_unities
WHERE id_recipe IN (
    SELECT id FROM t_recipes WHERE recipe_name IN (
        'Cookies healthy aux flocons d''avoine',
        'Mousse au chocolat végane',
        'Chocolat chaud au lait de noisette',
        'Crêpes healthy sans gluten'
    )
);

-- Supprimer les étapes liées à ces recettes (important car t_steps a une FK sur id_recipe)
DELETE FROM t_steps
WHERE id_recipe IN (
    SELECT id FROM t_recipes WHERE recipe_name IN (
        'Cookies healthy aux flocons d''avoine',
        'Mousse au chocolat végane',
        'Chocolat chaud au lait de noisette',
        'Crêpes healthy sans gluten'
    )
);

-- Supprimer les recettes
DELETE FROM t_recipes 
WHERE recipe_name IN (
    'Cookies healthy aux flocons d''avoine',
    'Mousse au chocolat végane',
    'Chocolat chaud au lait de noisette',
    'Crêpes healthy sans gluten'
);

-- ************  Cookies healthy aux flocons d'avoine ************ 
DO $$
DECLARE 
  r_id INT;
BEGIN
  -- 1. Crée la recette et récupère son ID
  INSERT INTO t_recipes (recipe_name, difficulty, recipe_picture, id_account)
  VALUES (
    'Cookies healthy aux flocons d''avoine',
    'Facile',
    '9be1669d-dc4c-45be-98d6-10d9996c60d5.jpg',
    (SELECT id FROM t_accounts WHERE username = 'dubois.sabrina84@gmail.com')
  )
  RETURNING id INTO r_id;

  -- 2. Lie les ingrédients
  INSERT INTO t_recipes_ingredients_unities (id_recipe, id_ingredient, quantity, id_unity)
  SELECT 
    r_id,
    i.id,
    data.quantity,
    u.id
  FROM 
    (VALUES 
      ('Flocons d''avoine', 100, 'g'),
      ('Banane mûre', 2, 'unité'),
      ('Pépites de chocolat', 50, 'g'),
      ('Beurre de cacahuète', 2, 'c. à soupe')
    ) AS data(ingredient_name, quantity, unity_name)
  JOIN t_ingredients i ON i.ingredient_name = data.ingredient_name
  JOIN t_unities u ON u.unity_name = data.unity_name;

  -- 3. Insère les étapes
  INSERT INTO t_steps (step_number, step_description, id_recipe)
  VALUES 
    (1, 'Préchauffer le four à 180°C.', r_id),
    (2, 'Écraser les bananes dans un bol.', r_id),
    (3, 'Ajouter les flocons d''avoine, le beurre de cacahuète et bien mélanger.', r_id),
    (4, 'Incorporer les pépites de chocolat.', r_id),
    (5, 'Former des petits tas sur une plaque recouverte de papier cuisson.', r_id),
    (6, 'Cuire pendant 15 minutes.', r_id);
END $$;


-- ************ Mousse au chocolat végane ************ 
DO $$
DECLARE 
  r_id INT;
BEGIN
  -- 1. Crée la recette et récupère son ID
  INSERT INTO t_recipes (recipe_name, difficulty, recipe_picture, id_account)
  VALUES (
    'Mousse au chocolat végane',
    'Facile',
    'db7cf8b7-ca20-4445-8af4-79575adc01a5.png',
    (SELECT id FROM t_accounts WHERE username = 'dubois.sabrina84@gmail.com')
  )
  RETURNING id INTO r_id;

  -- 2. Lie les ingrédients
  INSERT INTO t_recipes_ingredients_unities (id_recipe, id_ingredient, quantity, id_unity)
  SELECT 
    r_id,
    i.id,
    data.quantity,
    u.id
  FROM 
    (VALUES 
      ('Chocolat noir', 200, 'g'),
      ('Jus de pois chiche', 150, 'ml'),	
      ('Édulcorant en poudre', 2, 'c. à soupe'),
      ('Vanille liquide', 1, 'c. à café')
    ) AS data(ingredient_name, quantity, unity_name)
  JOIN t_ingredients i ON i.ingredient_name = data.ingredient_name
  JOIN t_unities u ON u.unity_name = data.unity_name;

  -- 3. Insère les étapes
  INSERT INTO t_steps (step_number, step_description, id_recipe)
  VALUES 
    (1, 'Faire fondre le chocolat noir au bain-marie.', r_id),
    (2, 'Monter le jus de pois chiche en neige ferme à l''aide d''un batteur électrique.', r_id),
    (3, 'Ajouter l''édulcorant et la vanille liquide.', r_id),
    (4, 'Incorporer délicatement le chocolat fondu à la préparation.', r_id),
    (5, 'Répartir la mousse dans des verrines et réserver au frais pendant au moins 2 heures.', r_id);
END $$;

--  ************ Chocolat chaud au lait de noisette  ************ 
DO $$
DECLARE 
  r_id INT;
BEGIN
  -- 1. Crée la recette et récupère son ID
  INSERT INTO t_recipes (recipe_name, difficulty, recipe_picture, id_account)
  VALUES (
    'Chocolat chaud au lait de noisette',
    'Facile',
    '240bfccc-e4ae-4ea3-b309-3b84ce37ac20.jpg',
    (SELECT id FROM t_accounts WHERE username = 'dubois.sabrina84@gmail.com')
  )
  RETURNING id INTO r_id;

  -- 2. Lie les ingrédients
  INSERT INTO t_recipes_ingredients_unities (id_recipe, id_ingredient, quantity, id_unity)
  SELECT 
    r_id,
    i.id,
    data.quantity,
    u.id
  FROM 
    (VALUES
      ('Lait de noisette', 500, 'ml'),
      ('Chocolat noir', 100, 'g'),	
      ('Cannelle', 1, 'pincée'),
      ('Chamallows', 8, 'unité')
    ) AS data(ingredient_name, quantity, unity_name)
  JOIN t_ingredients i ON i.ingredient_name = data.ingredient_name
  JOIN t_unities u ON u.unity_name = data.unity_name;

  -- 3. Insère les étapes
  INSERT INTO t_steps (step_number, step_description, id_recipe)
  VALUES 
    (1, 'Verser le lait de noisette dans une casserole et le faire chauffer à feu moyen.', r_id),
    (2, 'Ajouter les morceaux de chocolat noir dans la casserole et remuer jusqu''à ce qu''ils soient complètement fondus.', r_id),
    (3, 'Ajouter une pincée de cannelle et mélanger.', r_id),
    (4, 'Verser le chocolat chaud dans une tasse.', r_id),
    (5, 'Déposer quelques chamallows sur le dessus avant de servir.', r_id);
END $$;

-- ************ Crêpes healthy sans gluten ************ 
DO $$
DECLARE 
  r_id INT;
BEGIN
  -- 1. Crée la recette et récupère son ID
  INSERT INTO t_recipes (recipe_name, difficulty, recipe_picture, id_account)
  VALUES (
    'Crêpes healthy sans gluten',
    'Facile',
    '32205151-137c-492d-81f5-d2776bffa91a.jpg',
    (SELECT id FROM t_accounts WHERE username = 'dubois.sabrina84@gmail.com')
  )
  RETURNING id INTO r_id;

  -- 1.1. Insère les ingrédients s'ils n'existent pas déjà
  INSERT INTO t_ingredients (ingredient_name)
  SELECT x FROM (VALUES 
    ('Farine de riz'),
    ('Fécule de maïs'),
    ('Lait végétal'),
    ('Oeuf'),
    ('Extrait de vanille'),
    ('Huile de coco')
  ) AS v(x)
  WHERE NOT EXISTS (
    SELECT 1 FROM t_ingredients i WHERE i.ingredient_name = v.x
  );


  -- 2. Lie les ingrédients
  INSERT INTO t_recipes_ingredients_unities (id_recipe, id_ingredient, quantity, id_unity)
  SELECT 
    r_id,
    i.id,
    data.quantity,
    u.id
  FROM 
    (VALUES 
      ('Farine de riz', 100, 'g'),
      ('Fécule de maïs', 50, 'g'),
      ('Lait végétal', 300, 'ml'),
      ('Oeuf', 2, 'unité'),
      ('Extrait de vanille', 1, 'c. à café'),
      ('Huile de coco', 1, 'c. à soupe')
    ) AS data(ingredient_name, quantity, unity_name)
  JOIN t_ingredients i ON i.ingredient_name = data.ingredient_name
  JOIN t_unities u ON u.unity_name = data.unity_name;

  -- 3. Insère les étapes
  INSERT INTO t_steps (step_number, step_description, id_recipe)
  VALUES 
    (1, 'Dans un saladier, mélanger la farine de riz et la fécule de maïs.', r_id),
    (2, 'Ajouter les œufs, l''extrait de vanille et le lait végétal progressivement en fouettant.', r_id),
    (3, 'Faire chauffer une poêle avec un peu d''huile de coco.', r_id),
    (4, 'Verser une louche de pâte dans la poêle et cuire chaque face pendant 1 à 2 minutes.', r_id),
    (5, 'Répéter jusqu''à épuisement de la pâte.', r_id);
END $$;
