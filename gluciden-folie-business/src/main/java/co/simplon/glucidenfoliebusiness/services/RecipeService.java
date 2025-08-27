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
import co.simplon.glucidenfoliebusiness.dtos.recipe.StepCreateDto;
import co.simplon.glucidenfoliebusiness.dtos.recipe.StepReadDto;
import co.simplon.glucidenfoliebusiness.entities.Account;
import co.simplon.glucidenfoliebusiness.entities.Ingredient;
import co.simplon.glucidenfoliebusiness.entities.Recipe;
import co.simplon.glucidenfoliebusiness.entities.RecipeIngredientUnity;
import co.simplon.glucidenfoliebusiness.entities.RecipeIngredientUnityId;
import co.simplon.glucidenfoliebusiness.entities.Step;
import co.simplon.glucidenfoliebusiness.entities.Unity;
import co.simplon.glucidenfoliebusiness.enums.Difficulty;
import co.simplon.glucidenfoliebusiness.exceptions.AccountNotFoundException;
import co.simplon.glucidenfoliebusiness.exceptions.IngredientNotFoundException;
import co.simplon.glucidenfoliebusiness.exceptions.RecipeNotFoundException;
import co.simplon.glucidenfoliebusiness.exceptions.UnityNotFoundException;
import co.simplon.glucidenfoliebusiness.repositories.AccountRepository;
import co.simplon.glucidenfoliebusiness.repositories.IngredientRepository;
import co.simplon.glucidenfoliebusiness.repositories.RecipeIngredientUnityRepository;
import co.simplon.glucidenfoliebusiness.repositories.RecipeRepository;
import co.simplon.glucidenfoliebusiness.repositories.StepRepository;
import co.simplon.glucidenfoliebusiness.repositories.UnityRepository;

@Service
public class RecipeService {

	@Value("${glucidenfoliebusiness.uploads.dest}")
	private String uploadsDest;

	private final RecipeRepository recipesRepo;
	private final AccountRepository accountsRepo;
	private final IngredientRepository ingredientsRepo;
	private final UnityRepository unityRepo;
	private final RecipeIngredientUnityRepository riuRepo;
	private final StepRepository stepRepo;

	public RecipeService(RecipeRepository recipesRepo, AccountRepository accountsRepo,
			IngredientRepository ingredientsRepo, RecipeIngredientUnityRepository riuRepo, UnityRepository unityRepo,
			StepRepository stepRepo) {
		this.recipesRepo = recipesRepo;
		this.accountsRepo = accountsRepo;
		this.ingredientsRepo = ingredientsRepo;
		this.riuRepo = riuRepo;
		this.unityRepo = unityRepo;
		this.stepRepo = stepRepo;
	}

	/* ********** CRÉATION DE RECETTE ********** */
	@Transactional
	public Recipe create(RecipeCreateDto recipeDto) {
		Recipe recipe = new Recipe();
		recipe.setName(recipeDto.name());
		recipe.setDifficulty(defaultDifficulty(recipeDto.difficulty()));

		recipe.setAccount(getConnectedAccount());

		handleRecipePicture(recipeDto.picture(), recipe);

		Recipe savedRecipe = recipesRepo.save(recipe);

		saveRecipeIngredients(recipeDto.ingredients(), savedRecipe);

		saveRecipeSteps(recipeDto.steps(), savedRecipe);

		return savedRecipe;
	}

	private Difficulty defaultDifficulty(Difficulty difficulty) {
		return difficulty != null ? difficulty : Difficulty.FACILE;
	}

	private Account getConnectedAccount() {
		Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = jwt.getClaimAsString("sub");
		return accountsRepo.findByUsernameIgnoreCase(username).orElseThrow(AccountNotFoundException::new);
	}

	private void handleRecipePicture(MultipartFile picture, Recipe recipe) {
		if (picture != null && !picture.isEmpty()) {
			String pictureToRecipe = buildPicture(picture);
			storePicture(picture, pictureToRecipe);
			recipe.setPicture(pictureToRecipe);
		}
	}

	private void saveRecipeIngredients(List<RecipeIngredientUnityDto> ingredientsDto, Recipe recipe) {
		if (ingredientsDto == null)
			return;

		for (RecipeIngredientUnityDto riuDto : ingredientsDto) {
			Ingredient ingredient = resolveIngredient(riuDto);
			Unity unity = unityRepo.findById(riuDto.unityId())
					.orElseThrow(() -> new UnityNotFoundException(riuDto.unityId()));

			RecipeIngredientUnity riu = new RecipeIngredientUnity();
			riu.setRecipe(recipe);
			riu.setIngredient(ingredient);
			riu.setUnity(unity);
			riu.setQuantity(riuDto.quantity());
			riu.setId(new RecipeIngredientUnityId(recipe.getId(), ingredient.getId(), unity.getId()));

			riuRepo.save(riu);
		}
	}

	private Ingredient resolveIngredient(RecipeIngredientUnityDto riuDto) {
		if (riuDto.ingredientId() != null) {
			return ingredientsRepo.findById(riuDto.ingredientId())
					.orElseThrow(() -> new IngredientNotFoundException(riuDto.ingredientId()));
		} else if (riuDto.ingredient() != null && riuDto.ingredient().name() != null) {
			String name = riuDto.ingredient().name().trim();
			return ingredientsRepo.findByNameIgnoreCase(name).orElseGet(() -> {
				Ingredient newIngredient = new Ingredient();
				newIngredient.setName(name);
				return ingredientsRepo.save(newIngredient);
			});
		} else {
			throw new IllegalArgumentException("Aucun ID ni nom d’ingrédient fourni.");
		}
	}

	private void saveRecipeSteps(List<StepCreateDto> stepsDto, Recipe recipe) {
		if (stepsDto == null)
			return;

		for (StepCreateDto stepDto : stepsDto) {
			Step step = new Step();
			step.setRecipe(recipe);
			step.setNumber(stepDto.number());
			step.setDescription(stepDto.description());
			stepRepo.save(step);
		}
	}

	/* ********** NOMMER LA PHOTO ********** */
	private String buildPicture(MultipartFile pictureFromDto) {
		if (pictureFromDto == null || pictureFromDto.isEmpty()) {
			throw new IllegalArgumentException("Le fichier est vide ou nul.");
		}

		UUID uuid = UUID.randomUUID();
		String originalName = pictureFromDto.getOriginalFilename();

		if (originalName == null || originalName.trim().isEmpty()) {
			return uuid + ".jpg";
		}

		int index = originalName.lastIndexOf('.');
		String extension = (index != -1) ? originalName.substring(index) : ".jpg";

		return uuid + extension;
	}

	/* ********** ENREGISTRER LA PHOTO ********** */
	private void storePicture(MultipartFile pictureFromDto, String pictureToRecipe) {
		try {
			String dest = String.format("%s/%s", uploadsDest, pictureToRecipe);
			File file = new File(dest);
			pictureFromDto.transferTo(file);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Erreur lors du stockage de l'image", ex);
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
		Recipe recipe = recipesRepo.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));

		// Ingrédients
		List<RecipeIngredientUnity> rius = riuRepo.findByRecipe(recipe);

		List<RecipeReadDto.IngredientInfo> ingredients = rius.stream().map(riu -> {
			RecipeReadDto.IngredientDto ingredientDto = new RecipeReadDto.IngredientDto(riu.getIngredient().getName());
			Double quantity = riu.getQuantity();
			Long unityId = riu.getUnity() != null ? riu.getUnity().getId() : null;

			return new RecipeReadDto.IngredientInfo(ingredientDto, unityId, quantity);
		}).toList();

		// Convertir les étapes en DTO
		List<StepReadDto> steps = stepRepo.findByRecipeIdOrderByNumberAsc(id).stream()
				.map(step -> new StepReadDto(step.getNumber(), step.getDescription())).toList();

		return new RecipeReadDto(recipe.getId(), recipe.getName(), recipe.getPicture(), recipe.getDifficulty(),
				ingredients, steps);
	}

	/* ********** DELETE : une recette ********** */
	public void deleteOne(Long id) {
		if (!recipesRepo.existsById(id)) {
			throw new RecipeNotFoundException(id);
		}
		// Supprimer les liens avec les ingrédients avant la recette
		riuRepo.deleteByRecipeId(id);

		// Supprimer les étapes associées à la recette
		List<Step> steps = stepRepo.findByRecipeIdOrderByNumberAsc(id);
		stepRepo.deleteAll(steps);

		// Supprimer la recette elle-même
		recipesRepo.deleteById(id);
	}

	/* ********** UPDATE : une recette ********** */
	@Transactional
	public Recipe updateOne(long id, RecipeUpdateDto inputs) {
		Recipe recipe = recipesRepo.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));

		recipe.setName(inputs.name());
		recipe.setDifficulty(defaultDifficulty(inputs.difficulty()));

		handleRecipePicture(inputs.picture(), recipe);

		// Mettre à jour les ingrédients
		updateRecipeIngredients(recipe, inputs.ingredients());

		// Mettre à jour les étapes
		updateRecipeSteps(recipe, inputs.steps());

		return recipesRepo.save(recipe);
	}

	/* ********** Mise à jour des ingrédients ********** */
	private void updateRecipeIngredients(Recipe recipe, List<RecipeIngredientUnityDto> ingredientsDto) {
		if (ingredientsDto == null || ingredientsDto.isEmpty()) {
			riuRepo.deleteByRecipeId(recipe.getId());
			return;
		}

		// Supprimer les anciens liens
		riuRepo.deleteByRecipeId(recipe.getId());

		// Sauvegarder les nouveaux
		saveRecipeIngredients(ingredientsDto, recipe);
	}

	/* ********** Mise à jour des étapes ********** */
	private void updateRecipeSteps(Recipe recipe, List<StepCreateDto> stepsDto) {
		List<Step> existingSteps = stepRepo.findByRecipeIdOrderByNumberAsc(recipe.getId());

		if (stepsDto == null || stepsDto.isEmpty()) {
			stepRepo.deleteAll(existingSteps);
			return;
		}

		for (StepCreateDto stepDto : stepsDto) {
			Step step = existingSteps.stream().filter(s -> s.getNumber() == stepDto.number()).findFirst().orElse(null);

			if (step != null) {
				step.setDescription(stepDto.description());
				stepRepo.save(step);
			} else {
				Step newStep = new Step();
				newStep.setRecipe(recipe);
				newStep.setNumber(stepDto.number());
				newStep.setDescription(stepDto.description());
				stepRepo.save(newStep);
			}
		}

		// Supprimer les étapes existantes non présentes dans la nouvelle liste
		List<Integer> newStepNumbers = stepsDto.stream().map(StepCreateDto::number).toList();
		for (Step step : existingSteps) {
			if (!newStepNumbers.contains(step.getNumber())) {
				stepRepo.delete(step);
			}
		}
	}

	/* ********** Ajouter un ingrédient à une recette ********** */
	public boolean addIngredientToRecipe(Long recipeId, Long ingredientId, Double quantity) {
		return true;
	}
}
