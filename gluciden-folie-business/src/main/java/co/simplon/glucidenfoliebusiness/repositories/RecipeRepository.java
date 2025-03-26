package co.simplon.glucidenfoliebusiness.repositories;

import java.util.Collection;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.glucidenfoliebusiness.dtos.recipe.RecipeViewDto;
import co.simplon.glucidenfoliebusiness.entities.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

	Collection<RecipeViewDto> findAllProjectedBy();

	RecipeViewDto findOneProjectedById(Long id);

	Recipe save(Recipe entity);

	boolean existsByNameIgnoreCase(String name);

	boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

}
