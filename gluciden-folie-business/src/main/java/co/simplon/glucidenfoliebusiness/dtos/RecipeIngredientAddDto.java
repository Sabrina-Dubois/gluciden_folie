package co.simplon.glucidenfoliebusiness.dtos;

import java.math.BigDecimal;

public record RecipeIngredientAddDto(Long recipeId, Long ingredientId, BigDecimal quantity) {
}
