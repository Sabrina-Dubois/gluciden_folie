package co.simplon.glucidenfoliebusiness.dtos.recipe;

import java.util.List;

import co.simplon.glucidenfoliebusiness.enums.Difficulty;

public record RecipeReadDto(Long id, String name, String picture, Difficulty difficulty,
		List<IngredientInfo> ingredients, List<StepReadDto> steps) {
	public record IngredientInfo(IngredientDto ingredient, Long unityId, Double quantity) {
	}

	public record IngredientDto(String name) {
	}
}
