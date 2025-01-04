package co.simplon.glucidenfoliebusiness.config;

import java.time.Instant;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtProvider {

	private final Algorithm algorithm;

	private final long expiration;

	JwtProvider(Algorithm algorithm, long expiration) {
		this.algorithm = algorithm;
		this.expiration = expiration;
	}

	public String create(String subject) {
		Instant issuedAt = Instant.now();
		Builder builder = JWT.create().withIssuedAt(issuedAt).withSubject(subject);

		if (expiration > -1) {
			Instant expiresAt = issuedAt.plusSeconds(expiration);
			builder.withExpiresAt(expiresAt);
		}
		return builder.sign(algorithm);
	}

}
