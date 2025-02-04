-- Définir la structure ou le schéma de la BDD --
DROP TABLE IF EXISTS t_recipes_categories;
DROP TABLE IF EXISTS t_recipes;
DROP TABLE IF EXISTS t_categories;
DROP TABLE IF EXISTS t_accounts;
-- DROP TABLE IF EXISTS t_roles;

CREATE TABLE t_accounts(
       username VARCHAR(255) NOT NULL,
       password VARCHAR(72) NOT NULL, 
       --role_name VARCHAR(16) NOT NULL,
   CONSTRAINT t_accounts_pkey UNIQUE(username)
   --CONSTRAINT fkey_role_name FOREIGN KEY(role_name) REFERENCES t_roles(role_name)
);

CREATE TABLE t_categories(
   category_name VARCHAR(50) NOT NULL,
   username VARCHAR(100) NOT NULL,
   CONSTRAINT t_categories_pkey UNIQUE(category_name),
   CONSTRAINT fkey_username FOREIGN KEY(username) REFERENCES t_accounts(username)
);

CREATE TABLE t_recipes(
	recipe_name VARCHAR(100) NOT NULL,
	recipe_picture VARCHAR(80) NOT NULL,
	username VARCHAR(100) NOT NULL,
	CONSTRAINT t_recipes_pkey UNIQUE(recipe_name),
  	CONSTRAINT fkey_user_name FOREIGN KEY(username) REFERENCES t_accounts(username)
);


CREATE TABLE t_recipes_categories(
	recipe_name VARCHAR(100) NOT NULL,
    category_name VARCHAR(50) NOT NULL,
    CONSTRAINT t_recipes_categories_pkey PRIMARY KEY(recipe_name, category_name),  -- Clé primaire composite
    CONSTRAINT fkey_recipe_name FOREIGN KEY(recipe_name) REFERENCES t_recipes(recipe_name),  -- Clé étrangère vers les recettes
    CONSTRAINT fkey_category_name FOREIGN KEY(category_name) REFERENCES t_categories(category_name)  -- Clé étrangère vers les catégories
);


--CREATE TABLE t_roles(
	--role_name VARCHAR(16) NOT NULL,
	--CONSTRAINT t_role_pkey UNIQUE(role_name)
--);
