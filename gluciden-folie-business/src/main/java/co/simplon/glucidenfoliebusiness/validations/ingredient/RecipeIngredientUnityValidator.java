package co.simplon.glucidenfoliebusiness.validations.ingredient;

import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeIngredientUnityDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RecipeIngredientUnityValidator implements ConstraintValidator<ValidIngredients, RecipeIngredientUnityDto> {
	@Override
	public boolean isValid(RecipeIngredientUnityDto value, ConstraintValidatorContext context) {
		if (value == null)
			return true;
		return value.ingredientId() != null || (value.ingredient() != null && value.ingredient().name() != null
				&& !value.ingredient().name().isBlank());
	}
}
