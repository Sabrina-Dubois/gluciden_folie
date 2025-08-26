-- Définir la structure ou le schéma de la BDD --

DROP TABLE IF EXISTS t_recipes_categories;
DROP TABLE IF EXISTS t_recipes;
DROP TABLE IF EXISTS t_categories;
DROP TABLE IF EXISTS t_accounts;
DROP TABLE IF EXISTS t_roles;

CREATE TABLE t_roles(
	role_name VARCHAR(16) NOT NULL,
	CONSTRAINT t_role_pkey PRIMARY KEY(role_name)
);

CREATE TABLE t_accounts(
       username VARCHAR(255) NOT NULL,
       password VARCHAR(72) NOT NULL, 
   CONSTRAINT t_accounts_pkey PRIMARY KEY(username)
);

CREATE TABLE t_categories(
   category_name VARCHAR(50) NOT NULL,
   username VARCHAR(100) NOT NULL,
   role VARCHAR(20) NOT NULL,
   CONSTRAINT t_categories_pkey PRIMARY KEY(category_name),
   CONSTRAINT fkey_username FOREIGN KEY(username) REFERENCES t_accounts(username)
);

CREATE TABLE t_recipes(
	recipe_name VARCHAR(100) NOT NULL,
	recipe_picture VARCHAR(80) NOT NULL,
	username VARCHAR(100) NOT NULL,
	CONSTRAINT t_recipes_pkey UNIQUE(recipe_name),
  	CONSTRAINT fkey_username FOREIGN KEY(username) REFERENCES t_accounts(username)
);


CREATE TABLE t_recipes_categories(
	recipe_name VARCHAR(100) NOT NULL,
    category_name VARCHAR(50) NOT NULL,
    CONSTRAINT t_recipes_categories_pkey PRIMARY KEY(recipe_name, category_name),  -- Clé primaire composite
    CONSTRAINT fkey_recipe_name FOREIGN KEY(recipe_name) REFERENCES t_recipes(recipe_name),  -- Clé étrangère vers les recettes
    CONSTRAINT fkey_category_name FOREIGN KEY(category_name) REFERENCES t_categories(category_name)  -- Clé étrangère vers les catégories
);

CREATE TABLE t_unities(
   unity_name VARCHAR(50) NOT NULL,
   CONSTRAINT t_unities_pkey PRIMARY KEY(unity_name),
  
);

CREATE TABLE t_ingredients(
	ingredient_name VARCHAR(80) NOT NULL,
	CONSTRAINT t_ingredients_pkey PRIMARY KEY(ingredient_name)
);


CREATE TABLE t_recipes_ingredients_unities(
	quantity DECIMAL(10, 2) NOT NULL,
	recipe_name VARCHAR(100) NOT NULL,
    ingredient_name VARCHAR(50) NOT NULL,
    unity_name VARCHAR(50) NOT NULL,
    CONSTRAINT t_recipes_ingredients_pkey PRIMARY KEY(recipe_name, ingredient_name),  -- Clé primaire composite
    CONSTRAINT fkey_recipe_name FOREIGN KEY(recipe_name) REFERENCES t_recipes(recipe_name),  -- Clé étrangère vers les recettes
    CONSTRAINT fkey_ingredient_name FOREIGN KEY(ingredient_name) REFERENCES t_ingredients(ingredient_name),  -- Clé étrangère vers les ingrédients
    CONSTRAINT fkey_unity_name FOREIGN KEY(unity_name) REFERENCES t_unities(unity_name),  -- Clé étrangère vers les unités
);

CREATE TABLE t_steps(
   step_number INT NOT NULL,
   step_description VARCHAR(500) NOT NULL,
   recipe_name VARCHAR(100) NOT NULL,
   CONSTRAINT t_steps_pkey UNIQUE(step_number),
   CONSTRAINT fkey_recipe_name FOREIGN KEY(recipe_name) REFERENCES t_recipes(recipe_name)
);

