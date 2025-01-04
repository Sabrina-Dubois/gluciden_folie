//Gère les requêtes HTTP entrantes
// traite data user
// call service
// renvoie les réponses appropriées.

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

import co.simplon.glucidenfoliebusiness.dtos.RecipeCreateDto;
import co.simplon.glucidenfoliebusiness.dtos.RecipeUpdateDto;
import co.simplon.glucidenfoliebusiness.dtos.RecipeViewDto;
import co.simplon.glucidenfoliebusiness.services.RecipeService;
import jakarta.validation.Valid;

@RequestMapping("/recipes") // Mapper les requêtes HTTP aux méthodes de contrôleur ->requêtes HTTP à l'URL
							// /recipes seront gérées par ce contrôleur
@RestController // classe = contrôleur REST -> méthodes de ce contrôleur renverront des réponses
				// JSON par défaut.
public class RecipeController {

	private final RecipeService service; // Champs ->instance du service injectée par Spring

	// Constructeur de la classe RecipeController -> initialise le champ service
	// avec l'instance de RecipeService injectée par Spring
	public RecipeController(RecipeService service) {
		this.service = service;
	}

	@PostMapping
	void create(@Valid @RequestParam("name") String name, @RequestParam("picture") MultipartFile picture) {
		RecipeCreateDto recipeCreateDto = new RecipeCreateDto(name, picture);
		service.create(recipeCreateDto);
	}

	@GetMapping
	Collection<RecipeViewDto> getAll() {
		return service.getAll();
	}

	// Si appel réussi on renvoie un code 204
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	void deleteOne(@PathVariable("id") Long id) {
		service.deleteOne(id);
	}

	@PutMapping("/{id}")
	void updateOne(@PathVariable("id") Long id, @Valid @RequestParam("name") String name,
			@RequestParam(value = "picture", required = false) MultipartFile picture) {
		// Créez un DTO avec les données reçues
		RecipeUpdateDto recipeUpdateDto = new RecipeUpdateDto(name, picture);
		service.updateOne(id, recipeUpdateDto);
	}

	@GetMapping("/{id}")
	RecipeViewDto getOne(@PathVariable("id") Long id) {
		return service.getOne(id);
	}

}
