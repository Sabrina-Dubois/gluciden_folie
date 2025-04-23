package co.simplon.glucidenfoliebusiness.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import co.simplon.glucidenfoliebusiness.dtos.IngredientCreateDto;
import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeCreateDto;
import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeUpdateDto;
import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeViewDto;
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
	public void create(@RequestParam("name") String name, @RequestParam("picture") MultipartFile picture,
			@RequestParam("ingredients") String ingredientsJson) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();

		// ✅ On parse le JSON en liste d’objets
		List<IngredientCreateDto> ingredients = objectMapper.readValue(ingredientsJson,
				new TypeReference<List<IngredientCreateDto>>() {
				});

		// Construit la liste des objets IngredientCreateDto
		// List<IngredientCreateDto> ingredients = new ArrayList<>();
		// for (int i = 0; i < ingredients.size(); i++)
		/// ingredients.add(new IngredientCreateDto(ingredients));
		// ingredients.add(new IngredientCreateDto(ingredientsNames.get(i),
		// quantities.get(i), unities.get(i)));

		RecipeCreateDto recipeCreateDto = new RecipeCreateDto(name, picture, ingredients);
		recipeService.create(recipeCreateDto);
	}

	@GetMapping
	Collection<RecipeViewDto> getAll() {
		return recipeService.getAll();
	}

	// Si appel réussi on renvoie un code 204
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	void deleteOne(@PathVariable("id") Long id) {
		recipeService.deleteOne(id);
	}

	@PutMapping("/{id}")
	void updateOne(@PathVariable("id") Long id, @Valid @RequestParam("name") String name,
			@RequestParam(value = "picture", required = false) MultipartFile picture) {
		// Crée un DTO avec les données reçues
		RecipeUpdateDto recipeUpdateDto = new RecipeUpdateDto(name, picture);
		recipeService.updateOne(id, recipeUpdateDto);
	}

	@GetMapping("/{id}")
	RecipeViewDto getOne(@PathVariable("id") Long id) {
		return recipeService.getOne(id);
	}

	// Nouvelle méthode pour ajouter un ingrédient à une recette
	@PostMapping("/{recipeId}/addIngredient")
	public ResponseEntity<Void> addIngredientToRecipe(@PathVariable Long recipeId, @RequestParam Long ingredientId,
			@RequestParam Double quantity) {
		// Appel du service pour ajouter l'ingrédient à la recette
		recipeService.addIngredientToRecipe(recipeId, ingredientId, quantity);

		// Réponse de succès
		return ResponseEntity.ok().build();
	}
}
