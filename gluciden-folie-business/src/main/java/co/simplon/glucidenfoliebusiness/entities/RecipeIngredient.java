package co.simplon.glucidenfoliebusiness.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@IdClass(RecipeIngredientId.class)
@Table(name = "t_recipes_ingredients")
public class RecipeIngredient {

	@Id
	@ManyToOne
	@JoinColumn(name = "id_recipe", nullable = false)
	private Recipe recipeId;

	@Id
	@ManyToOne
	@JoinColumn(name = "id_ingredient", nullable = false, updatable = false)
	private Ingredient ingredientId;

	@Column(name = "quantity", nullable = false)
	private Double quantity;

	public RecipeIngredient() {
	}

	public Recipe getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Recipe recipeId) {
		this.recipeId = recipeId;
	}

	public Ingredient getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(Ingredient ingredientId) {
		this.ingredientId = ingredientId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

}
