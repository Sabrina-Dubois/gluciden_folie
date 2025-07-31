package co.simplon.glucidenfoliebusiness.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
	PasswordEncoder passwordEncoder() {
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
		return http.cors(Customizer.withDefaults()) // Active CORS avec la config définie dans CorsConfig
				.csrf((csrf) -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/accounts", "/accounts/login")
						.permitAll().requestMatchers("/", "/home").permitAll()
						// Accès en lecture pour tous les utilisateurs (USER + ADMIN)
						.requestMatchers(HttpMethod.GET, "/recipes/**", "/categories/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/unities/**").permitAll()
						// 📝 Accès en écriture/modification/suppression réservé aux ADMIN
						.requestMatchers(HttpMethod.POST, "/recipes", "/categories/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/recipes/**", "/categories/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/recipes/**", "/categories/**").hasRole("ADMIN")
						.anyRequest().authenticated())
				// la méthode build configure le security chaine avec une config spécifique
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter())))
				.build();
	}

	// Nouvelle méthode séparée pour la conversion
	private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtConverter() {
		return jwt -> {
			String roleClaim = jwt.getClaim("role");
			List<GrantedAuthority> authorities = new ArrayList<>();
			if (roleClaim != null) {
				authorities.add(new SimpleGrantedAuthority(roleClaim));
				authorities.add(new SimpleGrantedAuthority(roleClaim.replace("ROLE_", "")));
			}
			return new JwtAuthenticationToken(jwt, authorities);
		};

	}

	// Gestion des exceptions liées à la base de données
	@ExceptionHandler(DataAccessException.class)
	protected ResponseEntity<Object> handleDataAccessException(DataAccessException ex, WebRequest request) {
		String errorMessage = "Erreur accès a la DB : " + ex.getMessage();
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.CONFLICT);
	}

	// Implémentation de la méthode interne de gestion des exceptions
	private ResponseEntity<Object> handleExceptionInternal(Exception ex, String message, HttpStatus status) {
		Map<String, Object> body = new HashMap<>();
		body.put("error", message);
		body.put("status", status.value());
		return new ResponseEntity<>(body, status);

	}

}
