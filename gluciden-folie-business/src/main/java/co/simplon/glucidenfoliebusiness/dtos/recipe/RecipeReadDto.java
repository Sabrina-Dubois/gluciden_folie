//Recup du back vers le front pour afficher données
package co.simplon.glucidenfoliebusiness.dtos.recipe;

import java.util.List;

public record RecipeReadDto(Long id, String name, String picture, String difficulty, List<IngredientInfo> ingredients) {
	public record IngredientInfo(String name, Long unityId, Double quantity) {
	}
}
