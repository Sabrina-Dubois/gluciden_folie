package co.simplon.glucidenfoliebusiness.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IngredientCreateDto(@NotBlank @Size(min = 3, max = 20) String name, @NotBlank @Size() BigDecimal quantity,
		Long unity) {

}