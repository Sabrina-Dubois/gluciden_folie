package co.simplon.glucidenfoliebusiness.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.simplon.glucidenfoliebusiness.dtos.CategoryCreateDto;
import co.simplon.glucidenfoliebusiness.dtos.CategoryUpdateDto;
import co.simplon.glucidenfoliebusiness.dtos.CategoryViewDto;
import co.simplon.glucidenfoliebusiness.entities.Category;
import co.simplon.glucidenfoliebusiness.repositories.CategoryRepository;
import jakarta.validation.Valid;

@Service
public class CategoryService {

	// necessary field for communicated with DB
	private final CategoryRepository categories;

	// Constructeur de la classe CategoryService qui initialise les categories avec
	// les instances des repositories injectées par Spring.
	public CategoryService(CategoryRepository categories) {
		this.categories = categories;
	}

	public CategoryRepository getCategories() {
		return categories;
	}

	@Transactional
	public void create(@Valid CategoryCreateDto inputs) {
		Category entity = new Category(); // nouveau objet de caté vide
		entity.setName(inputs.name());// donne un nom à la caté en utilisant le contenu de inputs

		categories.save(entity);
	}

	// Récupérer toutes les catégories et les convertir en CategoryViewDto
	public List<CategoryViewDto> getAll() {
		List<Category> categoryEntities = categories.findAll();

		// Convertir chaque entité Category en CategoryViewDto
		return categoryEntities.stream().map(category -> new CategoryViewDto(category.getId(), category.getName()))
				.collect(Collectors.toList());
	}

	// Suprimer une catégorie
	public void deleteOne(Long id) {
		categories.deleteById(id);
	}

	// Modifier une catégorie
	public void updateOne(long id, CategoryUpdateDto inputs) {
		Category entity = categories.findById(id).orElseThrow();
		entity.setName(inputs.name());

		categories.save(entity);
	}

	// Récupérer une catégorie par son ID
	public CategoryViewDto getOne(Long id) {
		Category category = categories.findById(id).orElseThrow();
		return new CategoryViewDto(category.getId(), category.getName());
	}
}
