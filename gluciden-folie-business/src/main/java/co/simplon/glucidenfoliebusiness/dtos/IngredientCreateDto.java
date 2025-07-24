package co.simplon.glucidenfoliebusiness.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IngredientCreateDto(@NotBlank @Size(min = 3, max = 20) String name) {

}