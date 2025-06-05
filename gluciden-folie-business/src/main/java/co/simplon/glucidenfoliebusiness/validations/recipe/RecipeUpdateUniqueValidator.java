package co.simplon.glucidenfoliebusiness.validations.recipe;

import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeUpdateDto;
import co.simplon.glucidenfoliebusiness.repositories.RecipeRepository;
import co.simplon.glucidenfoliebusiness.validations.ValidationUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RecipeUpdateUniqueValidator implements ConstraintValidator<RecipeUpdateUnique, RecipeUpdateDto> {

	private final RecipeRepository recipes;

	public RecipeUpdateUniqueValidator(RecipeRepository recipes) {
		this.recipes = recipes;
	}

	@Override
	public boolean isValid(RecipeUpdateDto inputs, ConstraintValidatorContext context) {
		String name = inputs.name();
		if (name == null) {
			return true;
		}

		Long id = ValidationUtils.pathVariableAsLong("id");
		return !recipes.existsByNameIgnoreCaseAndIdNot(name, id);
	}

}
