package co.simplon.glucidenfoliebusiness.unit.dtos;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeCreateDto;
import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeIngredientUnityDto;
import co.simplon.glucidenfoliebusiness.dtos.recipe.StepCreateDto;
import co.simplon.glucidenfoliebusiness.enums.Difficulty;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class RecipeDtoValidationTest {

	private Validator validator;

	@BeforeEach
	void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	// =======================
	// Tests RecipeCreateDto
	// =======================

	@Test
	void shouldFail_WhenNameTooShort() {
		// KO : nom trop court (moins de 4 caractères)
		RecipeCreateDto dto = new RecipeCreateDto("abc",
				new MockMultipartFile("file", "test.png", "image/png", "dummy".getBytes()), Difficulty.FACILE,
				List.of(validIngredient()), List.of(validStep()));

		Set<ConstraintViolation<RecipeCreateDto>> violations = validator.validate(dto);
		assertThat(violations).anyMatch(v -> v.getMessage().contains("between 4 and 100"));
	}

	@Test
	void shouldBeValid_WhenNameCorrect() {
		// OK : nom correct
		RecipeCreateDto dto = new RecipeCreateDto("Recette Valide",
				new MockMultipartFile("file", "test.png", "image/png", "dummy".getBytes()), Difficulty.FACILE,
				List.of(validIngredient()), List.of(validStep()));

		Set<ConstraintViolation<RecipeCreateDto>> violations = validator.validate(dto);
		assertThat(violations).isEmpty();
	}

	@Test
	void shouldFail_WhenPictureIsNull() {
		// KO : image manquante
		RecipeCreateDto dto = new RecipeCreateDto("Recette valide", null, Difficulty.FACILE, List.of(validIngredient()),
				List.of(validStep()));

		Set<ConstraintViolation<RecipeCreateDto>> violations = validator.validate(dto);
		assertThat(violations).anyMatch(v -> v.getMessage().contains("must not be null"));
	}

	@Test
	void shouldBeValid_WhenPictureProvided() {
		// OK : image fournie
		RecipeCreateDto dto = new RecipeCreateDto("Recette valide",
				new MockMultipartFile("file", "test.png", "image/png", "dummy".getBytes()), Difficulty.FACILE,
				List.of(validIngredient()), List.of(validStep()));

		Set<ConstraintViolation<RecipeCreateDto>> violations = validator.validate(dto);
		assertThat(violations).isEmpty();
	}

	@Test
	void shouldFail_WhenStepsEmpty() {
		// KO : pas d'étapes fournies
		RecipeCreateDto dto = new RecipeCreateDto("Recette valide",
				new MockMultipartFile("file", "test.png", "image/png", "dummy".getBytes()), Difficulty.FACILE,
				List.of(validIngredient()), List.of());

		Set<ConstraintViolation<RecipeCreateDto>> violations = validator.validate(dto);
		assertThat(violations).anyMatch(v -> v.getMessage().contains("must not be empty"));
	}

	@Test
	void shouldBeValid_WhenStepsProvided() {
		// OK : étapes présentes
		RecipeCreateDto dto = new RecipeCreateDto("Recette valide",
				new MockMultipartFile("file", "test.png", "image/png", "dummy".getBytes()), Difficulty.FACILE,
				List.of(validIngredient()), List.of(validStep()));

		Set<ConstraintViolation<RecipeCreateDto>> violations = validator.validate(dto);
		assertThat(violations).isEmpty();
	}

	// =======================
	// Tests RecipeIngredientUnityDto
	// =======================

	@Test
	void shouldFail_WhenIngredientIdAndNameAreNull() {
		// Création du DTO avec ingredientId et ingredientName null
		RecipeIngredientUnityDto dto = new RecipeIngredientUnityDto(null, null, 1L, 100.0);

		// Validation via le Validator
		Set<ConstraintViolation<RecipeIngredientUnityDto>> violations = validator.validate(dto);

		// On vérifie qu'il y a au moins une violation
		assertThat(violations).anyMatch(v -> v.getMessage().contains("Ingredient id or name must be provided"));
	}

	@Test
	void shouldBeValid_WhenIngredientIdOrNameProvided() {
		RecipeIngredientUnityDto dto1 = new RecipeIngredientUnityDto(1L, null, 1L, 100.0);

		Set<ConstraintViolation<RecipeIngredientUnityDto>> violations1 = validator.validate(dto1);

		assertThat(violations1).isEmpty();

	}

	@Test
	void shouldFail_WhenQuantityNegative() {
		// KO : quantité négative
		RecipeIngredientUnityDto dto = new RecipeIngredientUnityDto(1L, null, 1L, -5.0);
		Set<ConstraintViolation<RecipeIngredientUnityDto>> violations = validator.validate(dto);
		assertThat(violations).anyMatch(v -> v.getMessage().contains("greater than 0"));
	}

	@Test
	void shouldBeValid_WhenQuantityPositive() {
		// OK : quantité positive
		RecipeIngredientUnityDto dto = new RecipeIngredientUnityDto(1L, null, 1L, 2.0);
		Set<ConstraintViolation<RecipeIngredientUnityDto>> violations = validator.validate(dto);
		assertThat(violations).isEmpty();
	}

	// =======================
	// Tests StepCreateDto
	// =======================

	@Test
	void shouldFail_WhenStepDescriptionBlank() {
		// KO : description vide
		StepCreateDto step = new StepCreateDto(1, "   ");
		Set<ConstraintViolation<StepCreateDto>> violations = validator.validate(step);
		assertThat(violations).anyMatch(v -> v.getMessage().contains("must not be blank"));
	}

	@Test
	void shouldBeValid_WhenStepCorrect() {
		// OK : description valide
		StepCreateDto step = validStep();
		Set<ConstraintViolation<StepCreateDto>> violations = validator.validate(step);
		assertThat(violations).isEmpty();
	}

	// =======================
	// Helpers
	// =======================

	private RecipeIngredientUnityDto validIngredient() {
		return new RecipeIngredientUnityDto(1L, null, 1L, 100.0);
	}

	private StepCreateDto validStep() {
		return new StepCreateDto(1, "Étape valide");
	}
}
