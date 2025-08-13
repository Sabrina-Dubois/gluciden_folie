package co.simplon.glucidenfoliebusiness.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

/**
 * Entité JPA représentant la table t_recipes_ingredients_unites. Cette entité
 * utilise une clé composite définie par RecipeIngredientUnityId. Elle relie une
 * recette, un ingrédient et une unité avec une quantité.
 */
@Entity
@Table(name = "t_recipes_ingredients_unities")
public class RecipeIngredientUnity {

	@EmbeddedId
	private RecipeIngredientUnityId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("ingredientId")
	@JoinColumn(name = "id_ingredient")
	private Ingredient ingredient;

	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("unityId")
	@JoinColumn(name = "id_unity")
	private Unity unity;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("recipeId")
	@JoinColumn(name = "id_recipe")
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

		this.id = new RecipeIngredientUnityId(recipe.getId(), ingredient.getId(), unity.getId());

	}

	// equals() basé sur l'ID composite
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

	// toString utile pour le debug
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
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public Unity getUnity() {
		return unity;
	}

	public void setUnity(Unity unity) {
		this.unity = unity;
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
