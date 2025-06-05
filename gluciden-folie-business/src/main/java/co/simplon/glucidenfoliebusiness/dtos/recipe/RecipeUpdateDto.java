package co.simplon.glucidenfoliebusiness.dtos.recipe;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import co.simplon.glucidenfoliebusiness.validations.recipe.RecipeUpdateUnique;
import co.simplon.glucidenfoliebusiness.validations.recipe.picture.FileSize;
import co.simplon.glucidenfoliebusiness.validations.recipe.picture.FileType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@RecipeUpdateUnique
public record RecipeUpdateDto(@NotBlank @Size(max = 100) String name,

		@FileType(types = {
				MediaType.IMAGE_JPEG_VALUE,
				MediaType.IMAGE_PNG_VALUE }) @FileSize(max = FileSize.TWO_MB) MultipartFile picture,
		@NotBlank String difficulty, List<RecipeIngredientUnityDto> ingredients){

}
