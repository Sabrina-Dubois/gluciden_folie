package co.simplon.glucidenfoliebusiness.validations.account;

import co.simplon.glucidenfoliebusiness.dtos.account.AccountCreateDto;
import co.simplon.glucidenfoliebusiness.repositories.AccountRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccountCreateUniqueValidator implements ConstraintValidator<AccountCreateUnique, AccountCreateDto> {

	// interrode BDD
	private final AccountRepository accountsRepo;

	public AccountCreateUniqueValidator(AccountRepository accountsRepo) {
		this.accountsRepo = accountsRepo;
	}

	// Logique de validation
	@Override
	public boolean isValid(AccountCreateDto inputs, ConstraintValidatorContext context) {
		if (inputs == null || inputs.username() == null || inputs.username().isBlank()) {
			return true; // Cette vérification sera couverte par @NotBlank
		}

		// Vérification si le nom d'utilisateur existe déjà dans la base de données
		boolean exists = accountsRepo.existsByUsernameIgnoreCase(inputs.username());
		if (exists) {
			// Ajouter un message d'erreur spécifique à ce cas
			context.buildConstraintViolationWithTemplate("L'utilisateur avec ce nom d'utilisateur existe déjà.")
					.addPropertyNode("username") // Attache l'erreur à l'attribut `username`
					.addConstraintViolation();
		}
		return !exists;
	}
}
