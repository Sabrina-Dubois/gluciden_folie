package co.simplon.glucidenfoliebusiness.services;

import org.springframework.stereotype.Service;

import co.simplon.glucidenfoliebusiness.dtos.IngredientCreateDto;
import co.simplon.glucidenfoliebusiness.entities.Ingredient;
import co.simplon.glucidenfoliebusiness.entities.Unity;
import co.simplon.glucidenfoliebusiness.repositories.IngredientRepository;
import co.simplon.glucidenfoliebusiness.repositories.UnityRepository;

@Service
public class IngredientService {

	private final IngredientRepository ingredientsRepo;
	private final UnityRepository unitiesRepo;

	public IngredientService(IngredientRepository ingredientsRepo, UnityRepository unitiesRepo) {
		this.ingredientsRepo = ingredientsRepo;
		this.unitiesRepo = unitiesRepo;
	}

	// Créer ou récupérer un ingrédient
	public Ingredient createOrGet(IngredientCreateDto ingredientDto) {
		// Recherche si l'ingrédient existe déjà
		return ingredientsRepo.findByIngredientNameIgnoreCase(ingredientDto.ingredientName()).orElseGet(() -> {
			Ingredient ingredient = new Ingredient();
			ingredient.setIngredientName(ingredientDto.ingredientName());

			// Recherche de l'unité associée
			Unity unity = unitiesRepo.findById(ingredientDto.unity())
					.orElseThrow(() -> new RuntimeException("Unity not found"));

			ingredient.setUnity(unity);
			return ingredientsRepo.save(ingredient);
		});
	}
}
