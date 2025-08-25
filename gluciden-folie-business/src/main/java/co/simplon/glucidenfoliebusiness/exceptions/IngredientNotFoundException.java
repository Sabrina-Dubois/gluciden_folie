package co.simplon.glucidenfoliebusiness.exceptions;

public class IngredientNotFoundException extends RuntimeException {

	public IngredientNotFoundException() {
		super("L'ingrédient spécifié est introuvable.");
	}

	public IngredientNotFoundException(String message) {
		super(message);
	}

	public IngredientNotFoundException(Long id) {
		super("Aucun ingrédient trouvé avec l’ID : " + id);
	}
}
