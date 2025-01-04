package co.simplon.glucidenfoliebusiness.config;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.auth0.jwt.algorithms.Algorithm;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

	// Générer Mp
	@Value("${gluciden-folie-business.bcrypt.cost}")
	private int bcryptCost;// Déclare var pour stocker la valeur de bcrypt.cost

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(bcryptCost);
	}

	// clé secrète pour d-signer et décoder token
	@Value("${jwt.secret}")
	private String secret;

	// Expiartion token
	@Value("${jwt.expiration:3600}")
	private long expiration;

	// Générer un token signé
	@Bean
	JwtProvider jwtProvider() {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		return new JwtProvider(algorithm, expiration);
	}

	// Décoder token
	@Bean
	JwtDecoder jwtDecoder() {
		SecretKey secretKey = new SecretKeySpec(secret.getBytes(), "HMACSHA256");
		NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
		return decoder;
	}

	// Config du filtre de sécurité
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// FILTRE DE SÉCURITÉ -> à creuser sur le chef d'oeuvre on doit faire qqch avec
		// cet objet
		// Default spring behaviour : Polp -> il n'autorise rien pas défaut
		// Dire qu'on est dans un servuer qui s'appui sur JWT

		return http.cors(Customizer.withDefaults()) // Active CORS avec la config définie dans CorsConfig
				//
				.csrf((csrf) -> csrf.disable())
				// Multiples matchers qui map VERBES+ PATHS + AUTHORISATION
				.authorizeHttpRequests(
						(req) -> req.requestMatchers(HttpMethod.POST, "/accounts", "/accounts/login").permitAll())
				// Toujours mettre en dernière règle
				.authorizeHttpRequests((reqs) -> reqs.anyRequest().authenticated())
				// la méthode build configure le security chaine avec une config spécifique
				.oauth2ResourceServer((srv) -> srv.jwt(Customizer.withDefaults())) // Active la vérification JWT
				.build();
	}

	// Gestion des exceptions liées à la base de données
	@ExceptionHandler(DataAccessException.class)
	protected ResponseEntity<Object> handleDataAccessException(DataAccessException ex, WebRequest request) {
		return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	// Implémentation de la méthode interne de gestion des exceptions
	private ResponseEntity<Object> handleExceptionInternal(DataAccessException ex, Object object,
			HttpHeaders httpHeaders, HttpStatus conflict, WebRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
