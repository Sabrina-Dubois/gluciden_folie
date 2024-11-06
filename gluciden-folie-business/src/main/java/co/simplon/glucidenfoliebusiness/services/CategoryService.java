package co.simplon.glucidenfoliebusiness.services;

import java.util.Collection;

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

	// Lire toutes les catégories
	public Collection<CategoryViewDto> getAll() {
		return categories.findAllProjectedBy();
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

	// Récupère une catégorie
	public CategoryViewDto getOne(Long id) {
		return categories.findOneProjectedById(id);
	}
}
