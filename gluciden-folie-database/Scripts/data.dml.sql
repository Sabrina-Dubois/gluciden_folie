-- Supprimer les données existantes avant d'insérer de nouvelles données
DELETE FROM t_recipes_categories;
DELETE FROM t_recipes;
DELETE FROM t_categories;
DELETE FROM t_accounts;
--DELETE FROM t_roles;

-- Insérer des rôles
--INSERT INTO t_roles (role_name)
--VALUES 
    --('Administratrice');

-- Insérer des comptes utilisateurs
INSERT INTO t_accounts (username, password)
VALUES 
    ('sss@sss.com', '$2a$12$dGnWdZ4rvO81e2mfbjitxejJstmxrzYQZUvf8gWvG.aCQt3OLN4bK');

-- Insérer des catégories
INSERT INTO t_categories (category_name, username)
VALUES 
    ('Sans lactose', 'sss@sss.com'),
    ('Gluten Free', 'sss@sss.com'),
    ('Léger en sucre', 'sss@sss.com'),
    ('Boost en protéines', 'sss@sss.com');

-- Insérer des recettes
INSERT INTO t_recipes (recipe_name, recipe_picture, username)
VALUES 
    ('Chocolat chaud', '9ba1206e-7632-49c0-97d5-fa48e2169d49.jpg', 'sss@sss.com'),
    ('Smoothie', 'ac207d91-318d-421e-9e31-42b2072e776c.jpg', 'sss@sss.com'),
    ('Mousse au chocolat', '4dfbb94d-e7eb-40db-9d93-88d926c0a3a2.png', 'sss@sss.com'),
    ('Tarte aux pommes', 'dae967fe-3689-412e-afa3-bd6bca90df5d.png', 'sss@sss.com');
