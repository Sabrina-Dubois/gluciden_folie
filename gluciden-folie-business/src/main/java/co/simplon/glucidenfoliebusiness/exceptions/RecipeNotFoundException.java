package co.simplon.glucidenfoliebusiness.exceptions;

public class RecipeNotFoundException extends RuntimeException {

	public RecipeNotFoundException() {
		super("La recette spécifiée est introuvable.");
	}

	public RecipeNotFoundException(String message) {
		super(message);
	}

	public RecipeNotFoundException(Long id) {
		super("Aucune recette trouvée avec l’ID : " + id);
	}
}
