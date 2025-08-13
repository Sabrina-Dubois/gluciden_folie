package co.simplon.glucidenfoliebusiness.dtos.recipe;

import co.simplon.glucidenfoliebusiness.dtos.IngredientCreateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RecipeIngredientUnityDto(Long ingredientId, @Valid IngredientCreateDto ingredient, @NotNull Long unityId,
		@NotNull @Positive Double quantity) {

	public RecipeIngredientUnityDto {
		if (ingredientId == null && (ingredient == null || ingredient.name() == null || ingredient.name().isBlank())) {
			throw new IllegalArgumentException("Soit ingredientId soit ingredient.name doit être renseigné");
		}
	}
}
