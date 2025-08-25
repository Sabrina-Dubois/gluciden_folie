package co.simplon.glucidenfoliebusiness.services;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.simplon.glucidenfoliebusiness.dtos.category.CategoryCreateDto;
import co.simplon.glucidenfoliebusiness.dtos.category.CategoryUpdateDto;
import co.simplon.glucidenfoliebusiness.dtos.category.CategoryViewDto;
import co.simplon.glucidenfoliebusiness.entities.Account;
import co.simplon.glucidenfoliebusiness.entities.Category;
import co.simplon.glucidenfoliebusiness.repositories.AccountRepository;
import co.simplon.glucidenfoliebusiness.repositories.CategoryRepository;
import jakarta.validation.Valid;

@Service
public class CategoryService {

	// necessary field for communicated with DB
	private final CategoryRepository categories;
	private final AccountRepository accounts;

	// Constructeur de la classe CategoryService qui initialise les categories avec
	// les instances des repositories injectées par Spring.
	public CategoryService(CategoryRepository categories, AccountRepository accounts) {
		this.categories = categories;
		this.accounts = accounts;
	}

	@Transactional
	public void create(@Valid CategoryCreateDto inputs) {
		Category entity = new Category(); // nouveau objet de caté vide
		entity.setName(inputs.name());// donne un nom à la caté en utilisant le contenu de inputs
		// Récupérer le Jwt de l'utilisateur authentifié
		Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = jwt.getClaimAsString("sub"); // Assurez-vous que ce champ existe dans le JWT

		// Trouver l'account associé à cet utilisateur
		Account account = accounts.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new RuntimeException("Account not found for username: " + username));

		// Ajoute l'utilisateur à la recette
		entity.setAccount(account);
		categories.save(entity);
	}

	// Récupérer toutes les catégories et les convertir en CategoryViewDto
	public List<CategoryViewDto> getAll() {
		List<Category> categoryEntities = categories.findAll();

		// Convertir chaque entité Category en CategoryViewDto
		return categoryEntities.stream().map(category -> new CategoryViewDto(category.getId(), category.getName()))
				.toList();

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
