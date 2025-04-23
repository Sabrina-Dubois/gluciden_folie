//Logique métier => Implémenter la logique métier

package co.simplon.glucidenfoliebusiness.services;

import java.io.File;
import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import co.simplon.glucidenfoliebusiness.dtos.IngredientCreateDto;
import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeCreateDto;
import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeUpdateDto;
import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeViewDto;
import co.simplon.glucidenfoliebusiness.entities.Account;
import co.simplon.glucidenfoliebusiness.entities.Ingredient;
import co.simplon.glucidenfoliebusiness.entities.Recipe;
import co.simplon.glucidenfoliebusiness.entities.RecipeIngredient;
import co.simplon.glucidenfoliebusiness.repositories.AccountRepository;
import co.simplon.glucidenfoliebusiness.repositories.IngredientRepository;
import co.simplon.glucidenfoliebusiness.repositories.RecipeIngredientRepository;
import co.simplon.glucidenfoliebusiness.repositories.RecipeRepository;
import co.simplon.glucidenfoliebusiness.utils.IngredientHelper;

@Service
public class RecipeService {

	@Value("${glucidenfoliebusiness.uploads.dest}")
	private String uploadsDest;
	// necessary field for communicated with DB
	private final RecipeRepository recipesRepo;
	private final AccountRepository accountsRepo;
	private final IngredientRepository ingredientsRepo;
	private final IngredientHelper ingredientsHelper;
	private final RecipeIngredientRepository recipeIngredientRepo;

	// Constructeur de la classe SpotService qui initialise les champs recipes avec
	// les instances des repositories injectées par Spring.
	public RecipeService(RecipeRepository recipesRepo, AccountRepository accountsRepo,
			IngredientRepository ingredientsRepo, RecipeIngredientRepository recipeIngredientRepo,
			IngredientHelper ingredientsHelper) {
		this.recipesRepo = recipesRepo;
		this.accountsRepo = accountsRepo;
		this.ingredientsRepo = ingredientsRepo;
		this.recipeIngredientRepo = recipeIngredientRepo;
		this.ingredientsHelper = ingredientsHelper;
	}

	// Créer la recette
	@Transactional // toutes opérations dedans gere les comme 1 et une seule transaction
	public void create(RecipeCreateDto inputs) {
		// 1. Créer la recette
		Recipe entity = new Recipe(); // nouveau objet de recette vide
		entity.setName(inputs.name());// donne un nom à la recette en utilisant le contenu de inputs

		// Récupérer le Jwt de l'utilisateur authentifié
		Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = jwt.getClaimAsString("sub"); // Assurez-vous que ce champ existe dans le JWT

		// Trouver l'account associé à cet utilisateur
		Account account = accountsRepo.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new RuntimeException("Account not found for username: " + username));

		// Ajoute l'utilisateur à la recette
		entity.setAccount(account);

		// 2. Ajouter la photo si présente
		MultipartFile pictureFromDto = inputs.picture();
		if (pictureFromDto != null) {
			String pictureToEntity = buildPicture(pictureFromDto);
			storePicture(pictureFromDto, pictureToEntity);
			entity.setPicture(pictureToEntity);
		}

		// 3. Sauvegarder la recette et obtenir son ID généré
		// 3. Save the recipe and get the generated ID
		Recipe savedRecipe = recipesRepo.save(entity);
		Long recipeId = savedRecipe.getId(); // Get the ID of the saved recipe

		// Log the saved recipe ID
		System.out.println("Saved recipe with ID: " + recipeId);

		// 4. Associer les ingrédients à la recette
		for (IngredientCreateDto ingredientDto : inputs.ingredients()) {
			// Utilisation de IngredientHelper pour créer ou récupérer un ingrédient
			Ingredient ingredient = ingredientsHelper.createOrGetIngredient(ingredientDto);

			// Créer l'association recette-ingrédient avec la quantité
			RecipeIngredient recipeIngredient = new RecipeIngredient();
			recipeIngredient.setRecipeId(entity); // Associe la recette
			recipeIngredient.setIngredientId(ingredient); // Associe l'ingrédient
			recipeIngredient.setQuantity(ingredientDto.quantity().doubleValue()); // Quantité spécifique

			// Sauvegarder l'association dans la base de données
			recipeIngredientRepo.save(recipeIngredient);
		}

	}

	// Déléguer l'ajout des ingrédients à RecipeIngredientService
	// recipeIngredientService.addIngredientsToRecipe(entity, inputs.ingredients());

	// Importer une photo de la recette
	// Générer un identifiant unique pour l'image
	private String buildPicture(MultipartFile pictureFromDto) {
		UUID uuid = UUID.randomUUID(); // Génère identifiant unique
		String name = pictureFromDto.getOriginalFilename(); // Récupère le nom d'origine du fichier
		int index = name.lastIndexOf('.');// dernier point
		String ext = name.substring(index, name.length());// extention du fichier
		return uuid + ext; // concat id et extension
	}

	private void storePicture(MultipartFile pictureFromDto, String pictureToEntity) {
		try {
			String dest = String.format("%s/%s", uploadsDest, pictureToEntity);
			File file = new File(dest);// objet File -> emplacement de destination
			pictureFromDto.transferTo(file);// Transfère l'image au nouvel emplacement
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	// Lire toutes les recettes
	public Collection<RecipeViewDto> getAll() {
		return recipesRepo.findAllProjectedBy();
	}

	// Supprimer une recette
	public void deleteOne(Long id) {
		recipesRepo.deleteById(id);
	}

	// Modifier une recette
	public void updateOne(long id, RecipeUpdateDto inputs) {
		Recipe entity = recipesRepo.findById(id).orElseThrow();
		entity.setName(inputs.name());

		MultipartFile newPicture = inputs.picture();
		if (newPicture != null) {
			String newPictureName = buildPicture(newPicture);
			storePicture(newPicture, newPictureName);
			entity.setPicture(newPictureName);
		}
		recipesRepo.save(entity);
	}

	// Récupère une recette
	public RecipeViewDto getOne(Long id) {
		return recipesRepo.findOneProjectedById(id);
	}

	public void addIngredientToRecipe(Long recipeId, Long ingredientId, Double quantity) {
		// TODO Auto-generated method stub

	}

}
