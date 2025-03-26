package co.simplon.glucidenfoliebusiness.controllers;

import java.util.Collection;

import org.springframework.http.HttpStatus;
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
	void create(@Valid @RequestParam("name") String name, @RequestParam("picture") MultipartFile picture) {
		RecipeCreateDto recipeCreateDto = new RecipeCreateDto(name, picture);
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
		// Créez un DTO avec les données reçues
		RecipeUpdateDto recipeUpdateDto = new RecipeUpdateDto(name, picture);
		recipeService.updateOne(id, recipeUpdateDto);
	}

	@GetMapping("/{id}")
	RecipeViewDto getOne(@PathVariable("id") Long id) {
		return recipeService.getOne(id);
	}

}
