package co.simplon.glucidenfoliebusiness.services;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeCreateDto;
import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeIngredientUnityDto;
import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeReadDto;
import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeUpdateDto;
import co.simplon.glucidenfoliebusiness.entities.Account;
import co.simplon.glucidenfoliebusiness.entities.Ingredient;
import co.simplon.glucidenfoliebusiness.entities.Recipe;
import co.simplon.glucidenfoliebusiness.entities.RecipeIngredientUnity;
import co.simplon.glucidenfoliebusiness.entities.RecipeIngredientUnityId;
import co.simplon.glucidenfoliebusiness.entities.Unity;
import co.simplon.glucidenfoliebusiness.repositories.AccountRepository;
import co.simplon.glucidenfoliebusiness.repositories.IngredientRepository;
import co.simplon.glucidenfoliebusiness.repositories.RecipeIngredientUnityRepository;
import co.simplon.glucidenfoliebusiness.repositories.RecipeRepository;
import co.simplon.glucidenfoliebusiness.repositories.UnityRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RecipeService {

	@Value("${glucidenfoliebusiness.uploads.dest}")
	private String uploadsDest;

	private final RecipeRepository recipesRepo;
	private final AccountRepository accountsRepo;
	private final IngredientRepository ingredientsRepo;
	private final UnityRepository unityRepo;
	private final RecipeIngredientUnityRepository riuRepo;

	public RecipeService(RecipeRepository recipesRepo, AccountRepository accountsRepo,
			IngredientRepository ingredientsRepo, RecipeIngredientUnityRepository riuRepo, UnityRepository unityRepo) {
		this.recipesRepo = recipesRepo;
		this.accountsRepo = accountsRepo;
		this.ingredientsRepo = ingredientsRepo;
		this.riuRepo = riuRepo;
		this.unityRepo = unityRepo;
	}

	/* ********** CRÉATION DE RECETTE ********** */
	@Transactional
	public Recipe create(RecipeCreateDto recipeDto) {
		Recipe recipe = new Recipe();
		recipe.setName(recipeDto.name());

		// Valeur par défaut -> difficulté
		String difficulty = recipeDto.difficulty();
		if (difficulty == null || difficulty.trim().isEmpty()) {
			difficulty = "Facile";
		}
		recipe.setDifficulty(difficulty);

		// Récupérer l'utilisateur connecté
		Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = jwt.getClaimAsString("sub");

		Account account = accountsRepo.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new RuntimeException("Account not found for username: " + username));
		recipe.setAccount(account);

		// Gestion de la photo
		MultipartFile pictureFromDto = recipeDto.picture();
		if (pictureFromDto != null && !pictureFromDto.isEmpty()) {
			String pictureToRecipe = buildPicture(pictureFromDto);
			storePicture(pictureFromDto, pictureToRecipe);
			recipe.setPicture(pictureToRecipe);
		}

		// Sauvegarder la recette
		Recipe savedRecipe = recipesRepo.save(recipe);

		// Sauvegarder les ingrédients liés
		if (recipeDto.ingredients() != null) {
			for (RecipeIngredientUnityDto riuDto : recipeDto.ingredients()) {
				Ingredient ingredient;
				if (riuDto.ingredientId() != null) {
					ingredient = ingredientsRepo.findById(riuDto.ingredientId()).orElseThrow(
							() -> new RuntimeException("Ingrédient non trouvé avec l'ID : " + riuDto.ingredientId()));
				} else if (riuDto.ingredient() != null && riuDto.ingredient().name() != null) {
					String name = riuDto.ingredient().name().trim();
					ingredient = ingredientsRepo.findByNameIgnoreCase(name).orElseGet(() -> {
						Ingredient newIngredient = new Ingredient();
						newIngredient.setName(name);
						return ingredientsRepo.save(newIngredient);
					});
				} else {
					throw new RuntimeException("Aucun ID ni nom d’ingrédient fourni.");
				}

				Unity unity = unityRepo.findById(riuDto.unityId())
						.orElseThrow(() -> new RuntimeException("Unité non trouvée avec l'ID : " + riuDto.unityId()));

				RecipeIngredientUnity riu = new RecipeIngredientUnity();
				riu.setRecipe(savedRecipe);
				riu.setIngredient(ingredient);
				riu.setUnity(unity);
				riu.setQuantity(riuDto.quantity());

				// Clé composite
				RecipeIngredientUnityId riuId = new RecipeIngredientUnityId(savedRecipe.getId(), ingredient.getId(),
						unity.getId());
				riu.setId(riuId);

				riuRepo.save(riu);
			}
		}

		return savedRecipe;
	}

	/* ********** NOMMER LA PHOTO ********** */
	private String buildPicture(MultipartFile pictureFromDto) {
		UUID uuid = UUID.randomUUID();
		String name = pictureFromDto.getOriginalFilename();
		int index = name.lastIndexOf('.');
		String ext = name.substring(index);
		return uuid + ext;
	}

	/* ********** ENREGISTRER LA PHOTO SUR DISQUE ********** */
	private void storePicture(MultipartFile pictureFromDto, String pictureToRecipe) {
		try {
			String dest = String.format("%s/%s", uploadsDest, pictureToRecipe);
			File file = new File(dest);
			pictureFromDto.transferTo(file);
		} catch (Exception ex) {
			throw new RuntimeException("Erreur lors du stockage de l'image", ex);
		}
	}

	/* ********** GET : ingrédients d'une recette ********** */
	public List<RecipeIngredientUnity> getIngredientsForRecipe(Recipe recipe) {
		return riuRepo.findByRecipe(recipe);
	}

	/* ********** GET : toutes les recettes ********** */
	public Collection<Recipe> getAll() {
		return recipesRepo.findAll();
	}

	/* ********** GET : une recette via son id ********** */
	@Transactional(readOnly = true)
	public RecipeReadDto getOne(Long id) {
		Recipe recipe = recipesRepo.findById(id).orElseThrow(() -> new RuntimeException("Recette non trouvée"));

		List<RecipeIngredientUnity> rius = riuRepo.findByRecipe(recipe);

		List<RecipeReadDto.IngredientInfo> ingredients = rius.stream().map(riu -> {
			String ingredientName = riu.getIngredient().getName();
			Double quantity = riu.getQuantity();
			Long unityId = riu.getUnity().getId(); // ← nom lisible de l’unité

			return new RecipeReadDto.IngredientInfo(ingredientName, unityId, quantity);
		}).toList();

		return new RecipeReadDto(recipe.getId(), recipe.getName(), recipe.getPicture(), recipe.getDifficulty(),
				ingredients);
	}

	/* ********** DELETE : une recette ********** */
	public void deleteOne(Long id) {
		if (!recipesRepo.existsById(id)) {
			throw new EntityNotFoundException("Recipe not found");
		}
		// Supprimer les liens avec les ingrédients avant la recette
		riuRepo.deleteByRecipeId(id);
		recipesRepo.deleteById(id);
	}

	/* ********** UPDATE : une recette ********** */
	@Transactional
	public void updateOne(long id, RecipeUpdateDto inputs) {
		Recipe recipe = recipesRepo.findById(id).orElseThrow(() -> new RuntimeException("Recette non trouvée"));

		recipe.setName(inputs.name());

		MultipartFile newPicture = inputs.picture();
		if (newPicture != null && !newPicture.isEmpty()) {
			String newPictureName = buildPicture(newPicture);
			storePicture(newPicture, newPictureName);
			recipe.setPicture(newPictureName);
		}

		String difficulty = inputs.difficulty();
		if (difficulty == null || difficulty.trim().isEmpty()) {
			difficulty = "Facile";
		}
		recipe.setDifficulty(difficulty);

		List<RecipeIngredientUnityDto> newIngredients = inputs.ingredients();
		if (newIngredients != null && !newIngredients.isEmpty()) {
			riuRepo.deleteByRecipeId(recipe.getId());

			for (RecipeIngredientUnityDto riuDto : newIngredients) {
				Ingredient ingredient;
				if (riuDto.ingredientId() != null) {
					ingredient = ingredientsRepo.findById(riuDto.ingredientId()).orElseThrow(
							() -> new RuntimeException("Ingrédient non trouvé avec l'ID : " + riuDto.ingredientId()));
				} else if (riuDto.ingredient() != null && riuDto.ingredient().name() != null) {
					String name = riuDto.ingredient().name().trim();
					ingredient = ingredientsRepo.findByNameIgnoreCase(name).orElseGet(() -> {
						Ingredient newIngredient = new Ingredient();
						newIngredient.setName(name);
						return ingredientsRepo.save(newIngredient);
					});
				} else {
					throw new RuntimeException("Aucun ID ni nom d’ingrédient fourni.");
				}

				Unity unity = unityRepo.findById(riuDto.unityId())
						.orElseThrow(() -> new RuntimeException("Unité non trouvée avec l'ID : " + riuDto.unityId()));

				RecipeIngredientUnity riu = new RecipeIngredientUnity();
				riu.setRecipe(recipe);
				riu.setIngredient(ingredient);
				riu.setUnity(unity);
				riu.setQuantity(riuDto.quantity());

				RecipeIngredientUnityId riuId = new RecipeIngredientUnityId(recipe.getId(), ingredient.getId(),
						unity.getId());
				riu.setId(riuId);

				riuRepo.save(riu);
			}

		}
	}

	/* ********** Ajouter un ingrédient à une recette ********** */
	public boolean addIngredientToRecipe(Long recipeId, Long ingredientId, Double quantity) {
		return true;
	}
}
