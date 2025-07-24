package co.simplon.glucidenfoliebusiness.dtos.recipe;

import co.simplon.glucidenfoliebusiness.dtos.IngredientCreateDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RecipeIngredientUnityDto(Long ingredientId, IngredientCreateDto ingredient, @NotNull Long unityId,
		@NotNull @Positive Double quantity) {

}
