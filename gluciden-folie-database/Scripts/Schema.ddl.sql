DROP TABLE IF EXISTS t_recipes_categories;
DROP TABLE IF EXISTS t_recipes;
DROP TABLE IF EXISTS t_categories;
DROP TABLE IF EXISTS t_accounts;

CREATE TABLE t_accounts (
    id INT GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(72) NOT NULL,
    CONSTRAINT t_id_accounts_pkey PRIMARY KEY(id),
    CONSTRAINT t_accounts_ukey UNIQUE(username)
);

CREATE TABLE t_categories (
    id INT GENERATED ALWAYS AS IDENTITY, 
    category_name VARCHAR(50) NOT NULL,
    username VARCHAR(100) NOT NULL,
    CONSTRAINT t_id_categories_pkey PRIMARY KEY(id),
    CONSTRAINT t_categories_ukey UNIQUE(category_name),
    CONSTRAINT fkey_username FOREIGN KEY(username) REFERENCES t_accounts(username)
);


CREATE TABLE t_recipes (
    id INT GENERATED ALWAYS AS IDENTITY, 
    recipe_name VARCHAR(100) NOT NULL,
    recipe_picture VARCHAR(80) NOT NULL,
    username VARCHAR(100) NOT NULL,
    CONSTRAINT t_id_recipes_pkey PRIMARY KEY(id),  
    CONSTRAINT t_recipes_ukey UNIQUE(recipe_name),
    CONSTRAINT fkey_username FOREIGN KEY(username) REFERENCES t_accounts(username)
);


CREATE TABLE t_recipes_categories (
    id_recipe INT NOT NULL,
    id_category INT NOT NULL,-- Clé primaire composite
    CONSTRAINT t_recipes_fkey FOREIGN KEY(id_recipe) REFERENCES t_recipes(id),  
    CONSTRAINT t_categories_fkey FOREIGN KEY(id_category) REFERENCES t_categories(id)  
);
