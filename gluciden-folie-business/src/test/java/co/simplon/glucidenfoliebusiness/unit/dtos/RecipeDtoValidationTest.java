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
	private static final String VALID_RECIPE_NAME = "Recette valide";
	private static final String VALID_RECIPE_FILE = "file";
	private static final String VALID_IMAGE_NAME = "test.png";
	private static final String VALID_IMAGE_TYPE = "image/png";
	private static final byte[] VALID_IMAGE_BYTES = "dummy".getBytes();

	@BeforeEach
	void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	// =======================
	// Tests RecipeCreateDto
	// =======================

	@Test
	void shouldFailWhenNameTooShort() {
		// KO : nom trop court (moins de 4 caractères)
		RecipeCreateDto dto = new RecipeCreateDto("abc",
				new MockMultipartFile(VALID_RECIPE_FILE, VALID_IMAGE_NAME, VALID_IMAGE_TYPE, VALID_IMAGE_BYTES),
				Difficulty.FACILE, List.of(validIngredient()), List.of(validStep()));

		Set<ConstraintViolation<RecipeCreateDto>> violations = validator.validate(dto);
		assertThat(violations).anyMatch(v -> v.getMessage().contains("between 4 and 100"));
	}

	@Test
	void shouldBeValidWhenNameCorrect() {
		// OK : nom correct
		RecipeCreateDto dto = new RecipeCreateDto(VALID_RECIPE_NAME,
				new MockMultipartFile(VALID_RECIPE_FILE, VALID_IMAGE_NAME, VALID_IMAGE_TYPE, VALID_IMAGE_BYTES),
				Difficulty.FACILE, List.of(validIngredient()), List.of(validStep()));

		Set<ConstraintViolation<RecipeCreateDto>> violations = validator.validate(dto);
		assertThat(violations).isEmpty();
	}

	@Test
	void shouldFailWhenPictureIsNull() {
		// KO : image manquante
		RecipeCreateDto dto = new RecipeCreateDto(VALID_RECIPE_NAME, null, Difficulty.FACILE,
				List.of(validIngredient()), List.of(validStep()));

		Set<ConstraintViolation<RecipeCreateDto>> violations = validator.validate(dto);
		assertThat(violations).anyMatch(v -> v.getMessage().contains("must not be null"));
	}

	@Test
	void shouldBeValidWhenPictureProvided() {
		// OK : image fournie
		RecipeCreateDto dto = new RecipeCreateDto(VALID_RECIPE_NAME,
				new MockMultipartFile(VALID_RECIPE_FILE, VALID_IMAGE_NAME, VALID_IMAGE_TYPE, VALID_IMAGE_BYTES),
				Difficulty.FACILE, List.of(validIngredient()), List.of(validStep()));

		Set<ConstraintViolation<RecipeCreateDto>> violations = validator.validate(dto);
		assertThat(violations).isEmpty();
	}

	@Test
	void shouldFailWhenStepsEmpty() {
		// KO : pas d'étapes fournies
		RecipeCreateDto dto = new RecipeCreateDto(VALID_RECIPE_NAME,
				new MockMultipartFile(VALID_RECIPE_FILE, VALID_IMAGE_NAME, VALID_IMAGE_TYPE, VALID_IMAGE_BYTES),
				Difficulty.FACILE, List.of(validIngredient()), List.of());

		Set<ConstraintViolation<RecipeCreateDto>> violations = validator.validate(dto);
		assertThat(violations).anyMatch(v -> v.getMessage().contains("must not be empty"));
	}

	@Test
	void shouldBeValidWhenStepsProvided() {
		// OK : étapes présentes
		RecipeCreateDto dto = new RecipeCreateDto(VALID_RECIPE_NAME,
				new MockMultipartFile(VALID_RECIPE_FILE, VALID_IMAGE_NAME, VALID_IMAGE_TYPE, VALID_IMAGE_BYTES),
				Difficulty.FACILE, List.of(validIngredient()), List.of(validStep()));

		Set<ConstraintViolation<RecipeCreateDto>> violations = validator.validate(dto);
		assertThat(violations).isEmpty();
	}

	// =======================
	// Tests RecipeIngredientUnityDto
	// =======================

	@Test
	void shouldFailWhenIngredientIdAndNameAreNull() {
		// Création du DTO avec ingredientId et ingredientName null
		RecipeIngredientUnityDto dto = new RecipeIngredientUnityDto(null, null, 1L, 100.0);

		// Validation via le Validator
		Set<ConstraintViolation<RecipeIngredientUnityDto>> violations = validator.validate(dto);

		// On vérifie qu'il y a au moins une violation
		assertThat(violations).anyMatch(v -> v.getMessage().contains("Ingredient id or name must be provided"));
	}

	@Test
	void shouldBeValidWhenIngredientIdOrNameProvided() {
		RecipeIngredientUnityDto dto1 = new RecipeIngredientUnityDto(1L, null, 1L, 100.0);

		Set<ConstraintViolation<RecipeIngredientUnityDto>> violations1 = validator.validate(dto1);

		assertThat(violations1).isEmpty();

	}

	@Test
	void shouldFailWhenQuantityNegative() {
		// KO : quantité négative
		RecipeIngredientUnityDto dto = new RecipeIngredientUnityDto(1L, null, 1L, -5.0);
		Set<ConstraintViolation<RecipeIngredientUnityDto>> violations = validator.validate(dto);
		assertThat(violations).anyMatch(v -> v.getMessage().contains("greater than 0"));
	}

	@Test
	void shouldBeValidWhenQuantityPositive() {
		// OK : quantité positive
		RecipeIngredientUnityDto dto = new RecipeIngredientUnityDto(1L, null, 1L, 2.0);
		Set<ConstraintViolation<RecipeIngredientUnityDto>> violations = validator.validate(dto);
		assertThat(violations).isEmpty();
	}

	// =======================
	// Tests StepCreateDto
	// =======================

	@Test
	void shouldFailWhenStepDescriptionBlank() {
		// KO : description vide
		StepCreateDto step = new StepCreateDto(1, "   ");
		Set<ConstraintViolation<StepCreateDto>> violations = validator.validate(step);
		assertThat(violations).anyMatch(v -> v.getMessage().contains("must not be blank"));
	}

	@Test
	void shouldBeValidWhenStepCorrect() {
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
