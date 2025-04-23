package co.simplon.glucidenfoliebusiness.dtos.account;

import co.simplon.glucidenfoliebusiness.validations.account.AccountCreateUnique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@AccountCreateUnique
public record AccountCreateDto(

		@NotBlank(message = "Le nom d'utilisateur est obligatoire.") @Size(min = 10, max = 50, message = "Le nom d'utilisateur doit contenir entre 10 et 50 caractères.") String username,
		@NotBlank(message = "Le mot de passe est obligatoire.") @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères.") String password) {

	@Override
	public String toString() {
		return String.format("{username=%s,password=[PROTECTED]}", username);
	}
}
