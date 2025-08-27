package co.simplon.glucidenfoliebusiness.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Mapping des images : URL -> dossier physique sur le VPS
		registry.addResourceHandler("/uploads/**")
				.addResourceLocations("file:/srv/readresolve.tech/ash/gluciden-folie-business/uploads/");
	}

}
