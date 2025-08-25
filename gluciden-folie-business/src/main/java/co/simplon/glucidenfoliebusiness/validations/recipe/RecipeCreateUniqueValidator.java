package co.simplon.glucidenfoliebusiness.validations.recipe;

import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeCreateDto;
import co.simplon.glucidenfoliebusiness.repositories.RecipeRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RecipeCreateUniqueValidator implements ConstraintValidator<RecipeCreateUnique, RecipeCreateDto> {

	// variable recipes qui est une insatnce de recipeRepo
	// repo pour verif si une recette avec le meme nom existe
	private final RecipeRepository recipes;

	// constructeur pour injecter une instance de SpotRepository -> classe
	// SpotCreateUniqueValidator
	public RecipeCreateUniqueValidator(RecipeRepository recipes) {
		this.recipes = recipes;
	}

	// logique de validation
	// extrait la valeur name de l'objet RecipeCreate
	@Override
	public boolean isValid(RecipeCreateDto inputs, ConstraintValidatorContext context) {
		String name = inputs.name();
		if (name == null) {
			return true;
		}
		return !recipes.existsByNameIgnoreCase(name);
	}
}
