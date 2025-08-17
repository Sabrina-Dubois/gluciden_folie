package co.simplon.glucidenfoliebusiness.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("local")
//@ConditionalOnProperty(value = "glucidenfolie.cors.enable", havingValue = "true", matchIfMissing = true)
// a activer avec le enable app. propertie
public class CorsConfig {

	@Value("${gluciden-folie-business.cors.allowedOrigins}")
	private String origins;

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE")
						.allowedOrigins(origins).allowedHeaders("*").allowCredentials(true);// allowCRedenteial(cookie)
			}
		};
	}
}