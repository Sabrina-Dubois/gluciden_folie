-- Modifier l'instance de bdd -> insérant, modifiant et supprimant ses données

INSERT INTO t_recipes (recipe_name, recipe_picture)
	VALUES 
		('Carrot cake','/images/carrot_cake.jpg'),
		('Smoothie','/images/carrot_cake.jpg'),
		('Mousse au chocolat','https://images.pexels.com/photos/3026810/pexels-photo-3026810.jpeg'),
		('Mousse','https://images.pexels.com/photos/3026810/pexels-photo-3026810.jpeg');

	
INSERT INTO t_categories (category_name)
	VALUES 
		('Sans lactose'),
		('Sans Gluten'),
		('HyperProtéinées);
