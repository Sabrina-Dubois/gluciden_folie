package co.simplon.glucidenfoliebusiness.controllers;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.glucidenfoliebusiness.dtos.CategoryCreateDto;
import co.simplon.glucidenfoliebusiness.dtos.CategoryUpdateDto;
import co.simplon.glucidenfoliebusiness.dtos.CategoryViewDto;
import co.simplon.glucidenfoliebusiness.services.CategoryService;
import jakarta.validation.Valid;

@RequestMapping("/categories")
@RestController
public class CategoryController {

	private final CategoryService service;

	// Constructeur de la classe CategoryController -> initialise le champ service
	// avec l'instance de CategoryService injectée par Spring
	public CategoryController(CategoryService service) {
		this.service = service;
	}

	@PostMapping
	void create(@Valid @RequestBody CategoryCreateDto inputs) {
		service.create(inputs);

	}

	@GetMapping
	Collection<CategoryViewDto> getAll() {
		return service.getAll();
	}

	// Si appel réussi on renvoie un code 204
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	void deleteOne(@PathVariable("id") Long id) {
		service.deleteOne(id);
	}

	@PutMapping("/{id}")
	void updateOne(@PathVariable("id") Long id, @Valid @ModelAttribute CategoryUpdateDto inputs) {
		service.updateOne(id, inputs);
	}

	@GetMapping("/{id}")
	CategoryViewDto getOne(@PathVariable("id") Long id) {
		return service.getOne(id);
	}
}
