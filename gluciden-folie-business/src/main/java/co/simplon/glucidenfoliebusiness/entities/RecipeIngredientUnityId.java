package co.simplon.glucidenfoliebusiness.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Classe représentant la clé composite pour l'entité RecipeIngredientUnity.
 * Cette clé est composée des IDs des entités Recipe, Ingredient et Unity.
 */
@Embeddable
public class RecipeIngredientUnityId implements Serializable {

	@Column(name = "id_recipe")
	private Long recipeId;

	@Column(name = "id_ingredient")
	private Long ingredientId;

	@Column(name = "id_unity")
	private Long unityId;

	public RecipeIngredientUnityId() {
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(recipeId, ingredientId, unityId);
	}

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
