package co.simplon.glucidenfoliebusiness.dtos.recipe;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import co.simplon.glucidenfoliebusiness.enums.Difficulty;
import co.simplon.glucidenfoliebusiness.validations.recipe.picture.FileSize;
import co.simplon.glucidenfoliebusiness.validations.recipe.picture.FileType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RecipeCreateDto(@NotBlank @Size(min = 4, max = 100) String name,

		@NotNull @FileType(types = {
				MediaType.IMAGE_JPEG_VALUE,
				MediaType.IMAGE_PNG_VALUE }) @FileSize(max = FileSize.TWO_MB) MultipartFile picture,

		@NotNull Difficulty difficulty,

		List<RecipeIngredientUnityDto> ingredients,

		@NotEmpty List<StepCreateDto> steps){

}
