-- Supprimer les données existantes avant d'insérer de nouvelles données
DELETE FROM t_ingredients;
DELETE FROM t_recipes;
DELETE FROM t_categories;
DELETE FROM t_unities;
DELETE FROM t_accounts;
DELETE FROM t_roles;
DELETE FROM t_roles WHERE role_name IN ('ROLE_USER', 'ROLE_ADMIN');

 
 -- INSERTION DES RÔLES PAR DÉFAUT
INSERT INTO t_roles (role_name, role_default) VALUES 
('USER', true),  -- Rôle par défaut
('ADMIN', false);

-- EXEMPLE DE COMPTE ADMIN (mot de passe doit être encodé en BCrypt)
INSERT INTO t_accounts (username, password, id_role) VALUES
(
    'admin@example.com', 
    '$2a$10$N9qo8uLOickgx2ZMRZoMy.Mrq4L0LQtpjXbYgP9sV7.T6u1Q1YjKO', -- "admin123" encodé
    (SELECT id FROM t_roles WHERE role_name = 'ADMIN')
);

-- EXEMPLE DE COMPTE USER
INSERT INTO t_accounts (username, password, id_role) VALUES
(
    'user@example.com', 
    '$2a$10$N9qo8uLOickgx2ZMRZoMy.Mrq4L0LQtpjXbYgP9sV7.T6u1Q1YjKO', -- "user123" encodé
    (SELECT id FROM t_roles WHERE role_name = 'USER')
);

-- Insérer des comptes utilisateurs
INSERT INTO t_accounts (username, PASSWORD,id_role)
VALUES 
    ('sss@sss.com', 'ssSS66!!',1);

-- Insérer des catégories
INSERT INTO t_categories (category_name, id_account)
VALUES 
    ('Sans lactose', (SELECT id FROM t_accounts WHERE username ='sss@sss.com')),
    ('Gluten Free', (SELECT id FROM t_accounts WHERE username ='sss@sss.com')),
    ('Léger en sucre', (SELECT id FROM t_accounts WHERE username ='sss@sss.com')),
    ('Boost en protéines', (SELECT id FROM t_accounts WHERE username ='sss@sss.com'));

-- Insérer des recettes
INSERT INTO t_recipes (recipe_name, recipe_picture, id_account)
VALUES 
    ('Smoothie', 'ac207d91-318d-421e-9e31-42b2072e776c.jpg', (SELECT id FROM t_accounts WHERE username ='sss@sss.com')),
    ('Mousse au chocolat', '4dfbb94d-e7eb-40db-9d93-88d926c0a3a2.png', (SELECT id FROM t_accounts WHERE username ='sss@sss.com')),
    ('Tarte aux pommes', 'dae967fe-3689-412e-afa3-bd6bca90df5d.png', (SELECT id FROM t_accounts WHERE username ='sss@sss.com'));
   

 -- Insérer des unités
INSERT INTO t_unities (unity_name)
VALUES 
    ('g'),
    ('kg'),
    ('L'),
    ('ml'),
    ('unité'),
    ('c. à café'),
    ('c. à soupe'),
   	('pincée');
   
   -- Insérer des ingrédients
INSERT INTO t_ingredients (ingredient_name,quantity,id_unity)
VALUES 
    ('Oeuf',2,(SELECT id FROM t_unities WHERE unity_name ='unité')),
    ('Maïzena', 100,(SELECT id FROM t_unities WHERE unity_name ='g')),
    ('Chocolat noir', 100,(SELECT id FROM t_unities WHERE unity_name ='g')),
    ('Sucre', 10,(SELECT id FROM t_unities WHERE unity_name ='g')),
    ('Sirop d''agave', 20,(SELECT id FROM t_unities WHERE unity_name ='ml')),
    ('Lait d''amande', 0.50,(SELECT id FROM t_unities WHERE unity_name ='L')),
    ('sel',1,(SELECT id FROM t_unities WHERE unity_name ='pincée'));
   
   
