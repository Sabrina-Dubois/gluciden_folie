DROP TABLE IF EXISTS t_steps;
DROP TABLE IF EXISTS t_recipes_categories;
DROP TABLE IF EXISTS t_recipes_ingredients_unities;
DROP TABLE IF EXISTS t_categories;
DROP TABLE IF EXISTS t_recipes;
DROP TABLE IF EXISTS t_ingredients;
DROP TABLE IF EXISTS t_unities;
DROP TABLE IF EXISTS t_accounts;
DROP TABLE IF EXISTS t_roles;


CREATE TABLE t_roles (
    id INT GENERATED ALWAYS AS IDENTITY,
    role_name VARCHAR(20) NOT NULL,
    role_default BOOLEAN,
    CONSTRAINT t_id_roles_pkey PRIMARY KEY(id),
    CONSTRAINT t_roles_ukey UNIQUE(role_name)
);

CREATE TABLE t_accounts (
    id INT GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(72) NOT NULL,
    id_role INT NOT NULL,
    CONSTRAINT t_id_accounts_pkey PRIMARY KEY(id),
    CONSTRAINT t_accounts_ukey UNIQUE(username),
    CONSTRAINT fkey_role_name FOREIGN KEY(id_role) REFERENCES t_roles(id)
);


CREATE TABLE t_unities (
    id INT GENERATED ALWAYS AS IDENTITY, 
    unity_name VARCHAR(50) NOT NULL,
    CONSTRAINT t_id_unities_pkey PRIMARY KEY(id),
    CONSTRAINT t_unities_ukey UNIQUE(unity_name)
);

CREATE TABLE t_ingredients (
    id INT GENERATED ALWAYS AS IDENTITY, 
    ingredient_name VARCHAR(100) NOT NULL, 
    CONSTRAINT t_id_ingredients_pkey PRIMARY KEY(id),  
    CONSTRAINT t_ingredients_ukey UNIQUE(ingredient_name)
);

CREATE TABLE t_recipes (
    id INT GENERATED ALWAYS AS IDENTITY, 
    recipe_name VARCHAR(100) NOT NULL,
    recipe_picture VARCHAR(80) NOT NULL,
    difficulty VARCHAR(15),
    id_account INT NOT NULL, 
    CONSTRAINT t_id_recipes_pkey PRIMARY KEY(id),  
    CONSTRAINT t_recipes_ukey UNIQUE(recipe_name),
    CONSTRAINT fkey_username FOREIGN KEY(id_account) REFERENCES t_accounts(id)
);

CREATE TABLE t_categories (
    id INT GENERATED ALWAYS AS IDENTITY, 
    category_name VARCHAR(50) NOT NULL,
    id_account INT NOT NULL, 
    CONSTRAINT t_id_categories_pkey PRIMARY KEY(id),
    CONSTRAINT t_categories_ukey UNIQUE(category_name),
    CONSTRAINT fkey_username FOREIGN KEY(id_account) REFERENCES t_accounts(id)
);

CREATE TABLE t_recipes_ingredients_unities (
    id_recipe INT NOT NULL,
    id_ingredient INT NOT NULL,
    id_unity INT NOT NULL,
    quantity DECIMAL (10, 2) NOT NULL,
    CONSTRAINT t_recipes_ingredients_pkey PRIMARY KEY(id_recipe, id_ingredient,id_unity),
    CONSTRAINT t_recipes_fkey FOREIGN KEY(id_recipe) REFERENCES t_recipes(id),  
    CONSTRAINT t_ingredients_fkey FOREIGN KEY(id_ingredient) REFERENCES t_ingredients(id),
    CONSTRAINT t_unities_fkey FOREIGN KEY(id_unity) REFERENCES t_unities(id)
);

CREATE TABLE t_recipes_categories (
    id_recipe INT NOT NULL,
    id_category INT NOT NULL,
    CONSTRAINT t_recipes_categories_pkey PRIMARY KEY(id_recipe, id_category),
    CONSTRAINT t_recipes_fkey FOREIGN KEY(id_recipe) REFERENCES t_recipes(id),  
    CONSTRAINT t_categories_fkey FOREIGN KEY(id_category) REFERENCES t_categories(id)
);

CREATE TABLE t_steps (
    id INT GENERATED ALWAYS AS IDENTITY, 
    step_number INT NOT NULL,
    step_description VARCHAR(500) NOT NULL, 
    id_recipe INT NOT NULL,
    CONSTRAINT t_id_steps_pkey PRIMARY KEY(id),  
    CONSTRAINT t_steps_ukey UNIQUE(id_recipe,step_number),
    CONSTRAINT fkey_recipe_name FOREIGN KEY(id_recipe) REFERENCES t_recipes(id)
);