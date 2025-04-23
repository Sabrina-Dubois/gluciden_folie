package co.simplon.glucidenfoliebusiness.entities;

import java.io.Serializable;
import java.util.Objects;

public class RecipeIngredientId implements Serializable {

	private Long recipeId;
	private Long ingredientId;

	public RecipeIngredientId() {
	}

	public RecipeIngredientId(Long recipeId, Long ingredientId) {
		this.recipeId = recipeId;
		this.ingredientId = ingredientId;
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof RecipeIngredientId that))
			return false;
		return Objects.equals(recipeId, that.recipeId) && Objects.equals(ingredientId, that.ingredientId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(recipeId, ingredientId);
	}
}
