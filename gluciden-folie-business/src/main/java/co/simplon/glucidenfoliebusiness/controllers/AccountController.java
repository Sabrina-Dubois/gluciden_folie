package co.simplon.glucidenfoliebusiness.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.glucidenfoliebusiness.dtos.LoginResponse;
import co.simplon.glucidenfoliebusiness.dtos.account.AccountCreateDto;
import co.simplon.glucidenfoliebusiness.dtos.account.AccountLogin;
import co.simplon.glucidenfoliebusiness.services.AccountService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String create(@Valid @RequestBody AccountCreateDto accountCreateDto) {
		try {
			// Appel à la méthode service pour créer le compte
			accountService.create(accountCreateDto);
			return "Compte créé avec succès !";
		} catch (Exception exception) {
			return "Erreur lors de la création du compte : " + exception.getMessage();
		}
	}

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	LoginResponse authentificate(@RequestBody AccountLogin accountLogin) {
		return accountService.authenticate(accountLogin);

	}

}
