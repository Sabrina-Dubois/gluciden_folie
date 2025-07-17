package co.simplon.glucidenfoliebusiness.dtos.recipe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StepCreateDto(@NotNull Integer number, @NotBlank String description) {

}
