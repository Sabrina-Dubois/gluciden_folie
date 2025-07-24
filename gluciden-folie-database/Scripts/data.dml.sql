-- 1. Supprimer les liens dans les tables de liaison (pour éviter erreurs FK)
DELETE FROM t_recipes_ingredients_unities;

-- 2. Supprimer les données des tables principales dans l'ordre respectant les FK
DELETE FROM t_recipes;
DELETE FROM t_ingredients;
DELETE FROM t_categories;
DELETE FROM t_unities;
DELETE FROM t_accounts;
DELETE FROM t_roles;

-- Suppression spécifique des rôles USER et ADMIN si présents
DELETE FROM t_roles WHERE role_name IN ('ROLE_USER', 'ROLE_ADMIN');

-- Réinitialiser toutes les séquences liées aux colonnes IDENTITY :  IDs propres / recommencent à 1
ALTER SEQUENCE t_roles_id_seq RESTART WITH 1;
ALTER SEQUENCE t_accounts_id_seq RESTART WITH 1;
ALTER SEQUENCE t_unities_id_seq RESTART WITH 1;
ALTER SEQUENCE t_ingredients_id_seq RESTART WITH 1;
ALTER SEQUENCE t_recipes_id_seq RESTART WITH 1;
ALTER SEQUENCE t_categories_id_seq RESTART WITH 1;
ALTER SEQUENCE t_steps_id_seq RESTART WITH 1; 

-- Insertion des rôles par défaut
INSERT INTO t_roles (role_name, role_default) VALUES 
('USER', true),  -- Rôle par défaut
('ADMIN', false);

-- Suppression d'un compte utilisateur spécifique : éviter doublons
DELETE FROM t_accounts WHERE username = 'dubois.sabrina84@gmail.com';

-- Insertion d'un compte utilisateur avec un mot de passe hashé (bcrypt)
 --INSERT INTO t_accounts (username, password,id_role)
-- VALUES 
    -- ('dubois.sabrina84@gmail.com', '$2a$10$uUwwwknqP0kGhlg13/Is0u9h6ETQHSEjQ44ueedx7Ohz745g7UnTy',2);
   
-- Insertion du compte utilisateur avec le rôle ADMIN récupéré dynamiquement
-- Utilisation d'une sous-requête pour récupérer l'ID correspondant au rôle ADMIN
INSERT INTO t_accounts (username, password, id_role)
VALUES (
    'dubois.sabrina84@gmail.com', 
    '$2a$10$uUwwwknqP0kGhlg13/Is0u9h6ETQHSEjQ44ueedx7Ohz745g7UnTy',
    (SELECT id FROM t_roles WHERE role_name = 'ADMIN')  -- Récupère l'ID du rôle ADMIN
);

-- Insérer des catégories
INSERT INTO t_categories (category_name, id_account)
VALUES 
    ('Sans lactose', (SELECT id FROM t_accounts WHERE username ='dubois.sabrina84@gmail.com')),
    ('Gluten Free', (SELECT id FROM t_accounts WHERE username ='dubois.sabrina84@gmail.com')),
    ('Léger en sucre', (SELECT id FROM t_accounts WHERE username ='dubois.sabrina84@gmail.com')),
    ('Boost en protéines', (SELECT id FROM t_accounts WHERE username ='dubois.sabrina84@gmail.com'));

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
   