package co.simplon.glucidenfoliebusiness.dtos;

import jakarta.validation.constraints.Size;

public record RoleCreateDto(@Size(min = 4, max = 6) String roleName) {

}
