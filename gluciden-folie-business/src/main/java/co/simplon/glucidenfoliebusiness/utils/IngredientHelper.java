package co.simplon.glucidenfoliebusiness.utils;

import org.springframework.stereotype.Component;

import co.simplon.glucidenfoliebusiness.dtos.IngredientCreateDto;
import co.simplon.glucidenfoliebusiness.entities.Ingredient;
import co.simplon.glucidenfoliebusiness.services.IngredientService;

@Component
public class IngredientHelper {

	private final IngredientService ingredientService;

	public IngredientHelper(IngredientService ingredientService) {
		this.ingredientService = ingredientService;
	}

	// Cette méthode va créer ou récupérer l'ingrédient et renvoyer une référence
	public Ingredient createOrGetIngredient(IngredientCreateDto ingredientDto) {
		return ingredientService.createOrGet(ingredientDto);
	}
}