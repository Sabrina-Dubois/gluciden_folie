package co.simplon.glucidenfoliebusiness.dtos.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryUpdateDto(@NotBlank @Size(max = 50) String name) {
}
