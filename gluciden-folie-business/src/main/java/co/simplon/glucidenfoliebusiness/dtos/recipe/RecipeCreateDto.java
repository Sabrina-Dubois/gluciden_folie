//Objets => Transférer les données entre les couches
//Créer et envoie des données du front vers la back
package co.simplon.glucidenfoliebusiness.dtos.recipe;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import co.simplon.glucidenfoliebusiness.dtos.IngredientCreateDto;
import co.simplon.glucidenfoliebusiness.validations.recipe.RecipeCreateUnique;
import co.simplon.glucidenfoliebusiness.validations.recipe.picture.FileSize;
import co.simplon.glucidenfoliebusiness.validations.recipe.picture.FileType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@RecipeCreateUnique
public record RecipeCreateDto(@NotBlank @Size(min = 4, max = 100) String name, @FileType(types = {
		MediaType.IMAGE_JPEG_VALUE,
		MediaType.IMAGE_PNG_VALUE }) @NotNull @FileSize(max = FileSize.TWO_MB) MultipartFile picture,
		List<IngredientCreateDto> ingredients){
}
