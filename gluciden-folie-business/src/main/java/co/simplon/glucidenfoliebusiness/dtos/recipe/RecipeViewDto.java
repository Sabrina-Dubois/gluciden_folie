//Recup du back vers le front pour afficher données
package co.simplon.glucidenfoliebusiness.dtos.recipe;

public record RecipeViewDto(
			Long id,
			String name, 
			String picture
			) {
}
