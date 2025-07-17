package co.simplon.glucidenfoliebusiness.services;

import org.springframework.stereotype.Service;

import co.simplon.glucidenfoliebusiness.entities.Ingredient;
import co.simplon.glucidenfoliebusiness.entities.Recipe;
import co.simplon.glucidenfoliebusiness.entities.RecipeIngredientUnity;
import co.simplon.glucidenfoliebusiness.entities.RecipeIngredientUnityId;
import co.simplon.glucidenfoliebusiness.entities.Unity;
import co.simplon.glucidenfoliebusiness.repositories.IngredientRepository;
import co.simplon.glucidenfoliebusiness.repositories.RecipeIngredientUnityRepository;
import co.simplon.glucidenfoliebusiness.repositories.RecipeRepository;
import co.simplon.glucidenfoliebusiness.repositories.UnityRepository;

/**
 * Service métier gérant la relation recette/ingrédient/unité. Chargé de la
 * création, modification et lecture des relations.
 */
@Service
public class RecipeIngredientUnityService {

	private final RecipeRepository recipeRepo;
	private final IngredientRepository ingredientRepo;
	private final UnityRepository unityRepo;
	private final RecipeIngredientUnityRepository recipeIngredientUnityRepo;

	public RecipeIngredientUnityService(RecipeRepository recipeRepo, IngredientRepository ingredientRepo,
			UnityRepository unityRepo, RecipeIngredientUnityRepository recipeIngredientUnityRepo) {
		this.recipeRepo = recipeRepo;
		this.ingredientRepo = ingredientRepo;
		this.unityRepo = unityRepo;
		this.recipeIngredientUnityRepo = recipeIngredientUnityRepo;
	}

	/**
	 * Crée ou met à jour une relation entre recette, ingrédient et unité avec une
	 * quantité. Si la relation existe déjà, met à jour la quantité.
	 * 
	 * @param recipeId     ID de la recette
	 * @param ingredientId ID de l'ingrédient
	 * @param unityId      ID de l'unité
	 * @param quantity     Quantité
	 * @return L'entité sauvegardée
	 * @throws IllegalArgumentException si recette, ingrédient ou unité introuvable
	 */

	public RecipeIngredientUnity createOrUpdate(Long recipeId, Long ingredientId, Long unityId, Double quantity) {

		Recipe recipe = recipeRepo.findById(recipeId).orElseThrow(() -> new RuntimeException("Recipe not found"));

		Ingredient ingredient = ingredientRepo.findById(ingredientId)
				.orElseThrow(() -> new RuntimeException("Ingredient not found"));

		Unity unity = unityRepo.findById(unityId).orElseThrow(() -> new RuntimeException("Unity not found"));

		// Création de la clé composite
		RecipeIngredientUnityId id = new RecipeIngredientUnityId(recipeId, ingredientId, unityId);

		// Recherche si relation existe déjà
		RecipeIngredientUnity entity = recipeIngredientUnityRepo.findById(id)
				.orElse(new RecipeIngredientUnity(recipe, ingredient, unity, quantity));

		// Mise à jour de la quantité
		entity.setQuantity(quantity);

		return recipeIngredientUnityRepo.save(entity);

	}
}
