package co.simplon.glucidenfoliebusiness.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

public record IngredientCreateDto(@Valid @Size(min = 3, max = 20) String name) {

	public IngredientCreateDto {
	}
}