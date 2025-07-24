package co.simplon.glucidenfoliebusiness.services;

import org.springframework.stereotype.Service;

import co.simplon.glucidenfoliebusiness.dtos.IngredientCreateDto;
import co.simplon.glucidenfoliebusiness.entities.Ingredient;
import co.simplon.glucidenfoliebusiness.repositories.IngredientRepository;

@Service
public class IngredientService {

	private final IngredientRepository ingredientsRepo;

	public IngredientService(IngredientRepository ingredientsRepo) {
		this.ingredientsRepo = ingredientsRepo;
	}

	// Créer ou récupérer un ingrédient
	public Ingredient createOrGet(IngredientCreateDto ingDto) {
		// Recherche si l'ingrédient existe déjà
		return ingredientsRepo.findByNameIgnoreCase(ingDto.name()).orElseGet(() -> {
			Ingredient ingredient = new Ingredient();
			ingredient.setName(ingDto.name());

			return ingredientsRepo.save(ingredient);
		});
	}
}
