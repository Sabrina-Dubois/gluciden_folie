package co.simplon.glucidenfoliebusiness.dtos.unity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UnityCreateDto(@NotBlank @Size(min = 1, max = 10) String name) {
}
