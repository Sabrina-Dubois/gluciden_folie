package co.simplon.glucidenfoliebusiness.services;

import org.springframework.stereotype.Service;

import co.simplon.glucidenfoliebusiness.dtos.IngredientCreateDto;
import co.simplon.glucidenfoliebusiness.entities.Ingredient;
import co.simplon.glucidenfoliebusiness.repositories.IngredientRepository;
import jakarta.validation.Valid;

@Service
public class IngredientService {
	private final IngredientRepository ingredientsRepo;
	// private final UnityRepository unitiesRepo;

	public IngredientService(IngredientRepository ingredientsRepo) {
		this.ingredientsRepo = ingredientsRepo;

	}

	public void create(@Valid IngredientCreateDto inputs) {
		Ingredient entityIngredient = new Ingredient();
		entityIngredient.setIngredientName(inputs.name());

		ingredientsRepo.save(entityIngredient);

	}

}
