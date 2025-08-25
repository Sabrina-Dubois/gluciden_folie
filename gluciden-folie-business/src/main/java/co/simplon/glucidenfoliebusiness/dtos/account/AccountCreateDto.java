package co.simplon.glucidenfoliebusiness.dtos.account;

import co.simplon.glucidenfoliebusiness.validations.account.AccountCreateUnique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@AccountCreateUnique
public record AccountCreateDto(

		@NotBlank @Size(min = 10, max = 50) String username, @NotBlank @Size(min = 8) String password) {

	@Override
	public String toString() {
		return String.format("{username=%s,password=[PROTECTED]}", username);
	}
}
