package co.simplon.glucidenfoliebusiness.dtos.recipe;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import co.simplon.glucidenfoliebusiness.validations.recipe.RecipeCreateUnique;
import co.simplon.glucidenfoliebusiness.validations.recipe.picture.FileSize;
import co.simplon.glucidenfoliebusiness.validations.recipe.picture.FileType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@RecipeCreateUnique // validation métier spécifique
public record RecipeCreateDto(
		@NotBlank(message = "Le nom est obligatoire") @Size(min = 4, max = 100, message = "Le nom doit faire entre 4 et 100 caractères") String name,
		@NotNull(message = "La photo est obligatoire") @FileType(types = {
				MediaType.IMAGE_JPEG_VALUE,
				MediaType.IMAGE_PNG_VALUE }) @FileSize(max = FileSize.TWO_MB) MultipartFile picture,
		@NotBlank(message = "La difficulté est obligatoire") String difficulty,
		@NotNull @NotEmpty List<RecipeIngredientUnityDto> ingredients){
}
