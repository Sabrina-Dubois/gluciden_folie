package co.simplon.glucidenfoliebusiness.exceptions;

public class AccountNotFoundException extends RuntimeException {

	public AccountNotFoundException() {
		super("Le compte spécifié est introuvable.");
	}

	public AccountNotFoundException(String message) {
		super(message);
	}

	public AccountNotFoundException(Long id) {
		super("Aucun compte trouvé avec l’ID : " + id);
	}
}
