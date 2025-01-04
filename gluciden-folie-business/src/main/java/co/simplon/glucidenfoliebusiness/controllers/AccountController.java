package co.simplon.glucidenfoliebusiness.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.glucidenfoliebusiness.dtos.AccountCreateDto;
import co.simplon.glucidenfoliebusiness.dtos.AccountLogin;
import co.simplon.glucidenfoliebusiness.dtos.LoginResponse;
import co.simplon.glucidenfoliebusiness.services.AccountService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/accounts")
//@CrossOrigin("*")
public class AccountController {

	private final AccountService service;

	public AccountController(AccountService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String create(@Valid @RequestBody AccountCreateDto inputs) {
		try {
			// Appel à la méthode service pour créer le compte
			service.create(inputs);
			// Retourne un message de succès une fois que le compte est créé
			return "Compte créé avec succès !";
		} catch (Exception exception) {
			// Si une erreur se produit, retourne un message d'erreur
			return "Erreur lors de la création du compte : " + exception.getMessage();
		}
	}

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	LoginResponse authentificated(@RequestBody AccountLogin inputs) {
		return service.authenticate(inputs);

	}

}
