package co.simplon.glucidenfoliebusiness.dtos;

public class RecipeIngredientCreateDto {

	private Long ingredientId;
	private Long recipeId;
	private Integer quantity;

	// Getters et setters
	public Long getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(Long ingredientId) {
		this.ingredientId = ingredientId;
	}

	public Long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
