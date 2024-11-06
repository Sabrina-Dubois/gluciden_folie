package co.simplon.glucidenfoliebusiness.dtos;

import co.simplon.glucidenfoliebusiness.validations.CategoryCreateUnique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@CategoryCreateUnique
public record CategoryCreateDto(@NotBlank @Size(max = 200) String name) {

}
