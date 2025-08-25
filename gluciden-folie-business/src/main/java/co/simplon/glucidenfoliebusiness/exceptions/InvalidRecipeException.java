package co.simplon.glucidenfoliebusiness.exceptions;

public class InvalidRecipeException extends RuntimeException {

	public InvalidRecipeException() {
		super("Invalide.");
	}

	public InvalidRecipeException(String message) {
		super(message);
	}

}
