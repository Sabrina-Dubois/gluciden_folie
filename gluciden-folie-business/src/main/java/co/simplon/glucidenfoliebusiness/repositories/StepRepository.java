package co.simplon.glucidenfoliebusiness.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.glucidenfoliebusiness.entities.Recipe;
import co.simplon.glucidenfoliebusiness.entities.Step;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {

	List<Step> findByRecipeIdOrderByNumberAsc(Long recipeId);

	// Dans StepRepository
	List<Step> findByRecipe(Recipe recipe);

}
