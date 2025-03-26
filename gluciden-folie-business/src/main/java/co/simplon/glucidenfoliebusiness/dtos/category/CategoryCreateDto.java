package co.simplon.glucidenfoliebusiness.dtos.category;

import co.simplon.glucidenfoliebusiness.validations.category.CategoryCreateUnique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@CategoryCreateUnique
public record CategoryCreateDto(@NotBlank @Size(max = 50) String name) {

}
