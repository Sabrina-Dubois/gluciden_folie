package co.simplon.glucidenfoliebusiness.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryUpdateDto(@NotBlank @Size(max = 200) String name) {
}
