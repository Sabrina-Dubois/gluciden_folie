package co.simplon.glucidenfoliebusiness.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.simplon.glucidenfoliebusiness.config.JwtProvider;
import co.simplon.glucidenfoliebusiness.dtos.LoginResponse;
import co.simplon.glucidenfoliebusiness.dtos.account.AccountCreateDto;
import co.simplon.glucidenfoliebusiness.dtos.account.AccountLogin;
import co.simplon.glucidenfoliebusiness.entities.Account;
import co.simplon.glucidenfoliebusiness.entities.Role;
import co.simplon.glucidenfoliebusiness.repositories.AccountRepository;
import co.simplon.glucidenfoliebusiness.repositories.RoleRepository;

@Service
@Transactional(readOnly = true)
public class AccountService {

	private final AccountRepository accountsRepo;
	private final RoleRepository roleRepo;
	private final PasswordEncoder encoder;
	private final JwtProvider jwtProvider;

	public AccountService(AccountRepository accountsRepo, RoleRepository roles, PasswordEncoder encoder,
			JwtProvider jwtProvider) {
		this.accountsRepo = accountsRepo;
		this.roleRepo = roles;
		this.encoder = encoder;
		this.jwtProvider = jwtProvider;
	}

	public Account getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {

			String username = jwt.getClaim("sub");
			return accountsRepo.findByUsernameIgnoreCase(username)
					.orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
		} else {
			throw new IllegalArgumentException("Utilisateur non authentifié");
		}
	}

	@Transactional
	public void create(AccountCreateDto inputs) {
		/** Création du compte */
		Account entity = new Account();
		entity.setUsername(inputs.username());

		/** Vérifications du username */
		if (inputs.username() == null || inputs.username().trim().isEmpty()) {
			throw new IllegalArgumentException("Le nom d'utilisateur ne peut pas être vide.");
		}
		if (accountsRepo.findByUsernameIgnoreCase(inputs.username()).isPresent()) {
			throw new IllegalArgumentException("L'utilisateur avec ce nom existe déjà.");
		}

		Role role;
		role = roleRepo.findByRoleName("USER")
				.orElseThrow(() -> new RuntimeException("Rôle USER par défaut non trouvé"));

		entity.setRole(role);

		String encodedPassword = encoder.encode(inputs.password());
		entity.setPassword(encodedPassword);

		accountsRepo.save(entity);
	}

	@Transactional
	public LoginResponse authenticate(AccountLogin inputs) {
		Account entity = accountsRepo.findByUsernameIgnoreCase(inputs.username())
				.orElseThrow(() -> new BadCredentialsException("Identifiants invalides"));

		if (!encoder.matches(inputs.password(), entity.getPassword())) {
			throw new BadCredentialsException("Identifiants invalides");
		}

		String role = "ROLE_" + entity.getRole().getRoleName();
		String token = jwtProvider.create(entity.getUsername(), role);

		return new LoginResponse(token, "Authentification réussie");
	}
}