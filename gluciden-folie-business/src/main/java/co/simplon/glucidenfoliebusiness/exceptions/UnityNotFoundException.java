package co.simplon.glucidenfoliebusiness.exceptions;

public class UnityNotFoundException extends RuntimeException {

	public UnityNotFoundException() {
		super("L’unité spécifiée est introuvable.");
	}

	public UnityNotFoundException(String message) {
		super(message);
	}

	public UnityNotFoundException(Long id) {
		super("Aucune unité trouvée avec l’ID : " + id);
	}
}
