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

import co.simplon.glucidenfoliebusiness.dtos.category.CategoryCreateDto;
import co.simplon.glucidenfoliebusiness.dtos.category.CategoryUpdateDto;
import co.simplon.glucidenfoliebusiness.dtos.category.CategoryViewDto;
import co.simplon.glucidenfoliebusiness.services.CategoryService;
import jakarta.validation.Valid;

@RequestMapping("/categories")
@RestController
public class CategoryController {

	private final CategoryService categoryService;

	// Constructeur de la classe CategoryController -> initialise le champ service
	// avec l'instance de CategoryService injectée par Spring
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping
	void create(@Valid @RequestBody CategoryCreateDto inputs) {
		categoryService.create(inputs);

	}

	@GetMapping
	Collection<CategoryViewDto> getAll() {
		return categoryService.getAll();
	}

	// Si appel réussi on renvoie un code 204
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	void deleteOne(@PathVariable("id") Long id) {
		categoryService.deleteOne(id);
	}

	@PutMapping("/{id}")
	void updateOne(@PathVariable("id") Long id, @Valid @ModelAttribute CategoryUpdateDto inputs) {
		categoryService.updateOne(id, inputs);
	}

	@GetMapping("/{id}")
	CategoryViewDto getOne(@PathVariable("id") Long id) {
		return categoryService.getOne(id);
	}
}
