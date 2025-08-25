package co.simplon.glucidenfoliebusiness.validations.step;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeCreateDto;
import co.simplon.glucidenfoliebusiness.dtos.recipe.StepCreateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RecipeStepsValidator implements ConstraintValidator<ValidSteps, RecipeCreateDto> {

	@Override
	public boolean isValid(RecipeCreateDto recipe, ConstraintValidatorContext context) {
		List<StepCreateDto> steps = recipe.steps();

		if (steps == null || steps.isEmpty()) {
			return false; // au moins une étape obligatoire
		}

		Set<Integer> numbers = steps.stream().map(StepCreateDto::number).collect(Collectors.toSet());

		// Vérifie unicité
		if (numbers.size() != steps.size()) {
			return false;
		}

		// Vérifie consécutivité
		int min = numbers.stream().min(Integer::compareTo).orElse(1);
		int max = numbers.stream().max(Integer::compareTo).orElse(1);

		return max - min + 1 == steps.size();
	}
}
