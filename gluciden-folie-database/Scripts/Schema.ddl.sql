-- Définir la structure ou le schéma de la BDD
DROP TABLE IF EXISTS t_recipes;
DROP TABLE IF EXISTS t_categories;
DROP TABLE IF EXISTS t_recipes_categories;

CREATE TABLE t_categories(
   id_category INT GENERATED ALWAYS AS IDENTITY, -- SMALL/BIG
   category_name VARCHAR(100) NOT NULL,
   CONSTRAINT t_id_categories_pkey PRIMARY KEY(id_category),
   CONSTRAINT t_categories_ukey UNIQUE(category_name) 
);

CREATE TABLE t_recipes(
	id_recipe INT GENERATED ALWAYS AS IDENTITY, -- singulier -> réfère à un seul
	recipe_name VARCHAR(200) NOT NULL,
	recipe_picture VARCHAR(80) NOT NULL,
	CONSTRAINT t_id_recipes_pkey PRIMARY KEY(id_recipe), -- pluriel -> représente la collection de tous les id de recipe
	CONSTRAINT t_recipes_ukey UNIQUE(recipe_name)
);

CREATE TABLE t_recipes_categories(
	id_recipe INT,
    id_category INT,
    CONSTRAINT t_recipes_categories_pkey PRIMARY KEY(id_recipe, id_category),  -- Clé primaire composite
    CONSTRAINT t_recipes_fkey FOREIGN KEY(id_recipe) REFERENCES t_recipes(id_recipe),  -- Clé étrangère vers les recettes
    CONSTRAINT t_categories_fkey FOREIGN KEY(id_category) REFERENCES t_categories(id_category)  -- Clé étrangère vers les catégories
);
