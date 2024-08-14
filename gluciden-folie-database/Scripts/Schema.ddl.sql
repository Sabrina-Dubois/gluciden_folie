-- Définir la structure ou le schéma de la BDD
DROP TABLE IF EXISTS t_recipes;

CREATE TABLE t_recipes(
	id_recipe INT GENERATED ALWAYS AS IDENTITY, -- singulier -> réfère à un seul
	recipe_name VARCHAR(200) NOT NULL,
	recipe_picture VARCHAR(80) NOT NULL,
	CONSTRAINT t_id_recipes_pkey PRIMARY KEY(id_recipe), -- pluriel -> représente la collection de tous les id de recipe
	CONSTRAINT t_recipes_ukey UNIQUE(recipe_name)
);

