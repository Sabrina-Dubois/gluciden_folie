package co.simplon.glucidenfoliebusiness.config;

import java.util.ArrayList;
import java.util.List;

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
		return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
	}

	// Config du filtre de sécurité
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		final String RECIPES_PATH = "/recipes/**";
		final String CATEGORIES_PATH = "/categories/**";
		final String UNITIES_PATH = "/unities/**";
		final String ADMIN_PATH = "ADMIN";

		return http.cors(Customizer.withDefaults()) // Active CORS avec la config définie dans CorsConfig
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						auth -> auth.requestMatchers(HttpMethod.POST, "/accounts", "/accounts/login").permitAll()
								// .requestMatchers("/swagger-ui/**", "/v3/api-docs/**",
								// "/swagger-ui.html").permitAll()
								.requestMatchers("/", "/home").permitAll().requestMatchers("/uploads/**").permitAll()
								.requestMatchers(HttpMethod.GET, RECIPES_PATH, CATEGORIES_PATH, UNITIES_PATH)
								.permitAll().requestMatchers(HttpMethod.POST, RECIPES_PATH, CATEGORIES_PATH)
								.hasRole(ADMIN_PATH).requestMatchers(HttpMethod.PUT, RECIPES_PATH, CATEGORIES_PATH)
								.hasRole(ADMIN_PATH).requestMatchers(HttpMethod.DELETE, RECIPES_PATH, CATEGORIES_PATH)
								.hasRole(ADMIN_PATH).anyRequest().authenticated())
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
}
