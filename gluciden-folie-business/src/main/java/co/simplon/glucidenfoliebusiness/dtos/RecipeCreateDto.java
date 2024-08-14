//Objets => Transférer les données entre les couches
//Créer et envoie des données du front vers la back
package co.simplon.glucidenfoliebusiness.dtos;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import co.simplon.glucidenfoliebusiness.validations.FileSize;
import co.simplon.glucidenfoliebusiness.validations.FileType;
import co.simplon.glucidenfoliebusiness.validations.RecipeCreateUnique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@RecipeCreateUnique
public record RecipeCreateDto(@NotBlank @Size(max = 200) String name,
		@FileType(types = {
			MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE })
		@FileSize(max = FileSize.TWO_MB) MultipartFile picture ){
	}
