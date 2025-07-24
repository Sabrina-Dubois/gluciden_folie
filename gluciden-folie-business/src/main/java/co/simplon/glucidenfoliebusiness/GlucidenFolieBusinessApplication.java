package co.simplon.glucidenfoliebusiness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // (exclude = SecurityAutoConfiguration.class) // !! PAS EN PROD
public class GlucidenFolieBusinessApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlucidenFolieBusinessApplication.class, args);
	}

}
