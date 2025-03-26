// récupérer, créer et gérer les rôles 

package co.simplon.glucidenfoliebusiness.services;

import org.springframework.stereotype.Service;

import co.simplon.glucidenfoliebusiness.dtos.RoleCreateDto;
import co.simplon.glucidenfoliebusiness.entities.Role;
import co.simplon.glucidenfoliebusiness.repositories.RoleRepository;

@Service
public class RoleService {

	private final RoleRepository rolesRepo;

	public RoleService(RoleRepository roles) {
		this.rolesRepo = roles;
	}

	public void create(RoleCreateDto inputs) {
		// Creer un entité role -> dto
		Role entity = new Role(inputs.roleName(), false);

		// register db
		rolesRepo.save(entity);
	}
}
