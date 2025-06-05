package co.simplon.glucidenfoliebusiness.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Classe représentant la clé composite pour l'entité RecipeIngredientUnity.
 * Cette clé est composée des IDs des entités Recipe, Ingredient et Unity.
 * 
 * Cette classe doit : - Implémenter Serializable - Avoir un constructeur vide
 * (obligatoire pour JPA) - Redéfinir equals() et hashCode() pour permettre à
 * JPA de comparer les clés
 */
@Embeddable // Indique que cette classe peut être intégrée dans une autre entité comme clé
			// composite
public class RecipeIngredientUnityId implements Serializable {

	// Identifiant de la recette
	@Column(name = "id_recipe")
	private Long recipeId;

	// Identifiant de l'ingrédient
	@Column(name = "id_ingredient")
	private Long ingredientId;

	// Identifiant de l'unité
	@Column(name = "id_unity")
	private Long unityId;

	// Constructeur vide obligatoire pour JPA
	public RecipeIngredientUnityId() {
	}

	// Constructeur avec tous les paramètres
	public RecipeIngredientUnityId(Long recipeId, Long ingredientId, Long unityId) {
		this.recipeId = recipeId;
		this.ingredientId = ingredientId;
		this.unityId = unityId;
	}

	@Override
	public String toString() {
		return String.format("RecipeIngredientUnityId{recipeId=%d, ingredientId=%d, unityId=%d}", recipeId,
				ingredientId, unityId);
	}

	/**
	 * equals() sert à comparer deux instances de clé composite. JPA utilise cette
	 * méthode pour vérifier si deux clés sont identiques.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RecipeIngredientUnityId))
			return false;
		RecipeIngredientUnityId that = (RecipeIngredientUnityId) obj;
		return Objects.equals(recipeId, that.recipeId) && Objects.equals(ingredientId, that.ingredientId)
				&& Objects.equals(unityId, that.unityId);
	}

	/**
	 * hashCode() doit être cohérent avec equals() et permet une meilleure gestion
	 * dans les collections.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(recipeId, ingredientId, unityId);

	}

	// Getters et setters
	public Long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}

	public Long getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(Long ingredientId) {
		this.ingredientId = ingredientId;
	}

	public Long getUnityId() {
		return unityId;
	}

	public void setUnityId(Long unityId) {
		this.unityId = unityId;
	}

}
