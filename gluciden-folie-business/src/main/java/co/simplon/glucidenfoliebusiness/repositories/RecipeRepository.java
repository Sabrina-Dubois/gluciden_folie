//Interface -> interagir avec la base de données

package co.simplon.glucidenfoliebusiness.repositories;

import java.util.Collection;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.glucidenfoliebusiness.dtos.RecipeViewDto;
import co.simplon.glucidenfoliebusiness.entities.Recipe;

// Interfaces -> définir des comportements communs à différentes classes
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> { //acceuil tous les types

	//Liste des recettes
	Collection<RecipeViewDto> findAllProjectedBy();
	
	RecipeViewDto findOneProjectedById(Long id);
	
	

	Recipe save(Recipe entity);

	boolean existsByNameIgnoreCase(String name);


	boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

}
