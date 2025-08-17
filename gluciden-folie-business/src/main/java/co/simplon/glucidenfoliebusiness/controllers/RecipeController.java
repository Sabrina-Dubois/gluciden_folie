package co.simplon.glucidenfoliebusiness.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeCreateDto;
import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeIngredientUnityDto;
import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeReadDto;
import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeUpdateDto;
import co.simplon.glucidenfoliebusiness.dtos.recipe.StepCreateDto;
import co.simplon.glucidenfoliebusiness.entities.Recipe;
import co.simplon.glucidenfoliebusiness.enums.Difficulty;
import co.simplon.glucidenfoliebusiness.services.RecipeService;
import jakarta.validation.Valid;

@RequestMapping("/recipes")
@RestController
public class RecipeController {

	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<RecipeReadDto> create(@Valid @ModelAttribute RecipeCreateDto recipeCreateDto) {
		Recipe createdRecipe = recipeService.create(recipeCreateDto);

		// On recharge la recette avec tous ses ingrédients + steps via getOne
		RecipeReadDto recipeReadDto = recipeService.getOne(createdRecipe.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(recipeReadDto);
	}

	@GetMapping
	Collection<Recipe> getAll() {
		return recipeService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<RecipeReadDto> getOne(@PathVariable Long id) {
		RecipeReadDto dto = recipeService.getOne(id);
		return ResponseEntity.ok(dto);
	}

	// Si appel réussi on renvoie un code 204
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@Transactional
	@DeleteMapping("/{id}")
	void deleteOne(@PathVariable("id") Long id) {
		recipeService.deleteOne(id);
	}

	@PutMapping("/{id}")
	void updateOne(@PathVariable("id") Long id, @Valid @RequestParam("name") String name,
			@RequestParam(value = "difficulty", required = false) Difficulty difficulty,
			@RequestParam(value = "ingredients", required = false) String ingredientsJson,
			@RequestParam(value = "picture", required = false) MultipartFile picture,
			@RequestParam(value = "steps", required = false) String stepsJson) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper(); // Une seule instance ObjectMapper

		List<RecipeIngredientUnityDto> ingredients = null;
		if (ingredientsJson != null && !ingredientsJson.isEmpty()) {
			ingredients = objectMapper.readValue(ingredientsJson, new TypeReference<List<RecipeIngredientUnityDto>>() {
			});
		}

		List<StepCreateDto> steps = null;
		if (stepsJson != null && !stepsJson.isEmpty() && !"undefined".equals(stepsJson)) {
			steps = objectMapper.readValue(stepsJson, new TypeReference<List<StepCreateDto>>() {
			});
		}

		RecipeUpdateDto recipeUpdateDto = new RecipeUpdateDto(name, picture, difficulty, ingredients, steps);
		recipeService.updateOne(id, recipeUpdateDto);
	}

	// Nouvelle méthode pour ajouter un ingrédient à une recette
	@PostMapping("/{recipeId}/addIngredient")
	public ResponseEntity<String> addIngredientToRecipe(@PathVariable Long recipeId, @RequestParam Long ingredientId,
			@RequestParam Double quantity) {
		try {
			// Appel du service pour ajouter l'ingrédient à la recette
			boolean success = recipeService.addIngredientToRecipe(recipeId, ingredientId, quantity);

			// Vérifie si l'ingrédient a été correctement ajouté
			if (success) {
				return ResponseEntity.status(HttpStatus.OK).body("Ingrédient ajouté avec succès.");
			} else {
				// Si la recette ou l'ingrédient n'existe pas, on renvoie une erreur 404
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recette ou ingrédient introuvable.");
			}
		} catch (Exception e) {
			// En cas d'erreur interne, renvoie un code 500 avec un message d'erreur
			// générique
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erreur lors de l'ajout de l'ingrédient.");
		}
	}
}
