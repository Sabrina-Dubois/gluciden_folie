-- Modifier l'instance de bdd -> insérant, modifiant et supprimant ses données

INSERT INTO t_recipes (recipe_name, recipe_picture)
	VALUES 
		('Chocolat chaud','9ba1206e-7632-49c0-97d5-fa48e2169d49.jpg'),
		('Smoothie','ac207d91-318d-421e-9e31-42b2072e776c.jpg'),
		('Mousse au chocolat','4dfbb94d-e7eb-40db-9d93-88d926c0a3a2.png'),
		('Tarte aux pommes','dae967fe-3689-412e-afa3-bd6bca90df5d.png');

	
INSERT INTO t_categories (category_name)
	VALUES 
		('Sans lactose'),
		('Gluten Free'),
		('Léger en sucre'),
		('Boost en protéines');

	