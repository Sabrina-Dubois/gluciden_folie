package co.simplon.glucidenfoliebusiness.entities;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

/**
 * Entité JPA représentant la table t_recipes_ingredients_unities. Cette entité
 * utilise une clé composite définie par RecipeIngredientUnityId. Elle relie une
 * recette, un ingrédient et une unité avec une quantité.
 */
@Entity
@Table(name = "t_recipes_ingredients_unities")
public class RecipeIngredientUnity {

	@EmbeddedId
	private RecipeIngredientUnityId id;

	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("ingredientId") // Lie l'ID de l'ingrédient avec la clé composite
	@JoinColumn(name = "id_ingredient")
	private Ingredient ingredient;

	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("unityId") // Lie l'ID de l'unité avec la clé composite
	@JoinColumn(name = "id_unity")
	private Unity unity;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("recipeId") // Lie l'ID de la recette avec la clé composite
	@JoinColumn(name = "id_recipe")
	@JsonBackReference
	private Recipe recipe;

	// Quantité de l'ingrédient dans la recette avec cette unité
	@Column(name = "quantity", nullable = false)
	private Double quantity;

	// Constructeur vide obligatoire pour JPA
	public RecipeIngredientUnity() {
	}

	// Constructeur complet pour faciliter la création d'objets
	public RecipeIngredientUnity(Recipe recipe, Ingredient ingredient, Unity unity, Double quantity) {
		this.recipe = recipe;
		this.ingredient = ingredient;
		this.unity = unity;
		this.quantity = quantity;

		// mise à jour de l'ID composite si les entités ont déjà un ID
		if (recipe != null)
			this.id.setRecipeId(recipe.getId());
		if (ingredient != null)
			this.id.setIngredientId(ingredient.getId());
		if (unity != null)
			this.id.setUnityId(unity.getId());

		// Correction importante : il faut vérifier que les IDs ne sont pas null
		// Si les entités n'ont pas encore d'ID, il faut d'abord les persister pour que
		// JPA génère les IDs
		// Long recipeId = recipe != null ? recipe.getId() : null;
		// Long ingredientId = ingredient != null ? ingredient.getId() : null;
		// Long unityId = unity != null ? unity.getId() : null;

		// this.id = new RecipeIngredientUnityId(recipeId, ingredientId, unityId);
		// Dans le constructeur complet
		this.id = new RecipeIngredientUnityId(recipe != null ? recipe.getId() : null,
				ingredient != null ? ingredient.getId() : null, unity != null ? unity.getId() : null);

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RecipeIngredientUnity that))
			return false;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "RecipeIngredientUnity{" + "recipeId=" + (id != null ? id.getRecipeId() : null) + ", ingredientId="
				+ (id != null ? id.getIngredientId() : null) + ", unityId=" + (id != null ? id.getUnityId() : null)
				+ ", quantity=" + quantity + '}';
	}

	// *** Getters et setters ***
	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
		// Mettre à jour l'ID composite si la recette change
		if (this.id == null)
			this.id = new RecipeIngredientUnityId();
		this.id.setRecipeId(recipe != null ? recipe.getId() : null);
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
		if (this.id == null)
			this.id = new RecipeIngredientUnityId();
		this.id.setIngredientId(ingredient != null ? ingredient.getId() : null);
	}

	public Unity getUnity() {
		return unity;
	}

	public void setUnity(Unity unity) {
		this.unity = unity;
		if (this.id == null)
			this.id = new RecipeIngredientUnityId();
		this.id.setUnityId(unity != null ? unity.getId() : null);
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public RecipeIngredientUnityId getId() {
		return id;
	}

	public void setId(RecipeIngredientUnityId id) {
		this.id = id;
	}

}
