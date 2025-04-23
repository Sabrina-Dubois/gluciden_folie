package co.simplon.glucidenfoliebusiness.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.glucidenfoliebusiness.entities.Recipe;
import co.simplon.glucidenfoliebusiness.entities.RecipeIngredient;
import co.simplon.glucidenfoliebusiness.entities.RecipeIngredientId;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, RecipeIngredientId> {
	List<RecipeIngredient> findByRecipeId(Recipe recipe);
}
