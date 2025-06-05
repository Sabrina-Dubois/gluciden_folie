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


UPDATE t_accounts SET id_role = (SELECT id FROM t_roles WHERE role_name = 'ADMIN') 
WHERE username = 'dubois.sabrina84@gmail.com';

DELETE FROM t_accounts WHERE username = 'dubois.sabrina84@gmail.com'

-- Insérer des comptes utilisateurs
INSERT INTO t_accounts (username, password,id_role)
VALUES 
    ('dubois.sabrina84@gmail.com', '$2a$10$uUwwwknqP0kGhlg13/Is0u9h6ETQHSEjQ44ueedx7Ohz745g7UnTy',2);
   
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
   
-- Insérer les ingrédients (un seul par type d'ingrédient)
INSERT INTO t_ingredients (ingredient_name,id_unity)
VALUES 
    ('Oeuf',(SELECT id FROM t_unities WHERE unity_name ='unité')),
    ('Maïzena',(SELECT id FROM t_unities WHERE unity_name ='g')),
    ('Chocolat noir',(SELECT id FROM t_unities WHERE unity_name ='g')),
    ('Sucre',(SELECT id FROM t_unities WHERE unity_name ='g')),
    ('Sirop d''agave',(SELECT id FROM t_unities WHERE unity_name ='ml')),
    ('Lait d''amande',(SELECT id FROM t_unities WHERE unity_name ='L')),
    ('sel',(SELECT id FROM t_unities WHERE unity_name ='pincée'));

   
   
