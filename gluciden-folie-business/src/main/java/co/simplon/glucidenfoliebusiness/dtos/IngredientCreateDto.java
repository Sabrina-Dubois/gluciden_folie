package co.simplon.glucidenfoliebusiness.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IngredientCreateDto(@NotBlank @Size(min = 3, max = 20) String ingredientName,
		@NotNull @DecimalMin("0.1") Double quantity, Long unity) {

}