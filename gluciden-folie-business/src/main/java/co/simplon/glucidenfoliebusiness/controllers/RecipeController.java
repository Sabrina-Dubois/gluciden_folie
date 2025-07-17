package co.simplon.glucidenfoliebusiness.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import co.simplon.glucidenfoliebusiness.services.RecipeService;
import jakarta.validation.Valid;

@RequestMapping("/recipes")
@RestController
public class RecipeController {

	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@PostMapping
	public Recipe create(@RequestParam("name") String name, @RequestParam("picture") MultipartFile picture,
			@RequestParam(value = "difficulty", required = false) String difficulty,
			@RequestParam("ingredients") String ingredientsJson,
			@RequestParam(value = "steps", required = false) String stepsJson) throws JsonProcessingException {

		if (difficulty == null || difficulty.trim().isEmpty()) {
			difficulty = "Facile";
		}

		ObjectMapper objectMapper = new ObjectMapper();

		// Désérialisation des ingrédients
		List<RecipeIngredientUnityDto> ingredients = objectMapper.readValue(ingredientsJson,
				new TypeReference<List<RecipeIngredientUnityDto>>() {
				});

		// Désérialisation des étapes
		List<StepCreateDto> steps = List.of(); // Liste vide par défaut
		if (stepsJson != null && !stepsJson.trim().isEmpty()) {
			steps = objectMapper.readValue(stepsJson, new TypeReference<List<StepCreateDto>>() {
			});
		}

		RecipeCreateDto recipeCreateDto = new RecipeCreateDto(name, picture, difficulty, ingredients, steps);
		return recipeService.create(recipeCreateDto);

	}

	@GetMapping
	Collection<Recipe> getAll() {
		System.out.println("GET /recipes reçu");
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
			@RequestParam(value = "difficulty", required = false) String difficulty,
			@RequestParam(value = "ingredients", required = false) String ingredientsJson,
			@RequestParam(value = "picture", required = false) MultipartFile picture) throws JsonProcessingException {
		try {
			List<RecipeIngredientUnityDto> ingredients = null;
			if (ingredientsJson != null && !ingredientsJson.isEmpty()) {
				ObjectMapper objectMapper = new ObjectMapper();
				ingredients = objectMapper.readValue(ingredientsJson, new TypeReference<>() {
				});
			}
			;

			// Crée un DTO avec les données reçues
			RecipeUpdateDto recipeUpdateDto = new RecipeUpdateDto(name, picture, difficulty, ingredients);
			recipeService.updateOne(id, recipeUpdateDto);

		} catch (

		JsonProcessingException e) {
			throw new RuntimeException("Erreur lors du parsing des ingrédients JSON", e);
		}
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
