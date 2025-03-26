package co.simplon.glucidenfoliebusiness.validations.category;

import co.simplon.glucidenfoliebusiness.dtos.category.CategoryCreateDto;
import co.simplon.glucidenfoliebusiness.repositories.CategoryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryCreateUniqueValidator implements ConstraintValidator<CategoryCreateUnique, CategoryCreateDto> {

	// variable categories qui est une insatnce de categoryRepo
	// repo pour verif si une caté avec le meme nom existe
	private final CategoryRepository categories;

	// constructeur pour injecter une instance de SpotRepository -> classe
	public CategoryCreateUniqueValidator(CategoryRepository categories) {
		this.categories = categories;
	}

	// logique de validation
	// extrait la valeur name de l'objet CategoryCreate
	@Override
	public boolean isValid(CategoryCreateDto inputs, ConstraintValidatorContext context) {
		String name = inputs.name();
		if (name == null) {
			return true;
		}
		return !categories.existsByNameIgnoreCase(name);
	}
}
