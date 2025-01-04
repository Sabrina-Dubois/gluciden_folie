package co.simplon.glucidenfoliebusiness.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.simplon.glucidenfoliebusiness.config.JwtProvider;
import co.simplon.glucidenfoliebusiness.dtos.AccountCreateDto;
import co.simplon.glucidenfoliebusiness.dtos.AccountLogin;
import co.simplon.glucidenfoliebusiness.dtos.LoginResponse;
import co.simplon.glucidenfoliebusiness.entities.Account;
import co.simplon.glucidenfoliebusiness.repositories.AccountRepository;

@Service
@Transactional(readOnly = true)
public class AccountService {

	private final AccountRepository accounts;
	// injecter mon bean et je déclare interface
	private final PasswordEncoder encoder;
	private final JwtProvider jwtProvider;

	public AccountService(AccountRepository accounts, PasswordEncoder encoder, JwtProvider jwtProvider) {
		this.accounts = accounts;
		this.encoder = encoder;
		this.jwtProvider = jwtProvider;
	}

	@Transactional
	public void create(AccountCreateDto inputs) {

		Account entity = new Account();
		entity.setUsername(inputs.username());
		entity.setPassword(inputs.password());

		// Vérif -> username n'est pas vide ou null
		if (inputs.username() == null || inputs.username().trim().isEmpty()) {
			throw new IllegalArgumentException("Le nom d'utilisateur ne peut pas être vide.");
		}
		// Vérif -> username existe avant de l'insérer
		if (accounts.findByUsernameIgnoreCase(inputs.username()).isPresent()) {
			throw new IllegalArgumentException("L'utilisateur avec ce nom existe déjà.");
		}

		// Encode le mot de passe
		String hashedPssword = encoder.encode(inputs.password()); // method chiffrage encode
		entity.setPassword(hashedPssword);

		accounts.save(entity);
	}

	public LoginResponse authenticate(AccountLogin inputs) {
		String username = inputs.username();
		// Cherche utilisateur par son username
		Account entity = accounts.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new BadCredentialsException(username));// si jamais c'est null prend ce qu'il y entre
		String password = inputs.password();
		String encoded = entity.getPassword(); // hash
		if (encoder.matches(password, encoded)) {

			String token = jwtProvider.create(username);
			// Retourner un token
			return new LoginResponse(token, "Authentification réussie");

		} else {
			throw new BadCredentialsException(username);
		}

	}
}
