
-- Supprimer les tables
DROP TABLE IF EXISTS t_recipes_categories;
DROP TABLE IF EXISTS t_recipes;
DROP TABLE IF EXISTS t_categories;
DROP TABLE IF EXISTS t_accounts;

-- Créer la table t_accounts
CREATE TABLE t_accounts (
    id INT GENERATED ALWAYS AS IDENTITY, -- SMALL/BIG
    username VARCHAR(255) NOT NULL,
    password VARCHAR(72) NOT NULL,
    CONSTRAINT t_id_accounts_pkey PRIMARY KEY(id),
    CONSTRAINT t_accounts_ukey UNIQUE(username)
);


-- Créer la table t_categories
CREATE TABLE t_categories (
    id INT GENERATED ALWAYS AS IDENTITY, -- SMALL/BIG
    category_name VARCHAR(50) NOT NULL,
    username VARCHAR(100) NOT NULL,
    CONSTRAINT t_id_categories_pkey PRIMARY KEY(id),
    CONSTRAINT t_categories_ukey UNIQUE(category_name),
    CONSTRAINT fkey_username FOREIGN KEY(username) REFERENCES t_accounts(username)
);

-- Créer la table t_recipes
CREATE TABLE t_recipes (
    id INT GENERATED ALWAYS AS IDENTITY, -- singulier -> réfère à un seul
    recipe_name VARCHAR(100) NOT NULL,
    recipe_picture VARCHAR(80) NOT NULL,
    username VARCHAR(100) NOT NULL,
    CONSTRAINT t_id_recipes_pkey PRIMARY KEY(id),  -- pluriel -> représente la collection de tous les id de recipe
    CONSTRAINT t_recipes_ukey UNIQUE(recipe_name),
    CONSTRAINT fkey_username FOREIGN KEY(username) REFERENCES t_accounts(username)
);


-- Créer la table t_recipes_categories
CREATE TABLE t_recipes_categories (
    id_recipe INT NOT NULL,
    id_category INT NOT NULL,
    CONSTRAINT t_recipes_categories_pkey PRIMARY KEY(id_recipe, id_category),  -- Clé primaire composite
    CONSTRAINT t_recipes_fkey FOREIGN KEY(id_recipe) REFERENCES t_recipes(id),  -- Clé étrangère vers les recettes
    CONSTRAINT t_categories_fkey FOREIGN KEY(id_category) REFERENCES t_categories(id)  -- Clé étrangère vers les catégories
);
