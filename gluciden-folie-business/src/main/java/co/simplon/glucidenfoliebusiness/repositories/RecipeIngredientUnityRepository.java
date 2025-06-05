package co.simplon.glucidenfoliebusiness.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.glucidenfoliebusiness.entities.Recipe;
import co.simplon.glucidenfoliebusiness.entities.RecipeIngredientUnity;
import co.simplon.glucidenfoliebusiness.entities.RecipeIngredientUnityId;

@Repository
public interface RecipeIngredientUnityRepository extends JpaRepository<RecipeIngredientUnity, RecipeIngredientUnityId> {
	List<RecipeIngredientUnity> findByRecipe(Recipe recipe);

	void deleteByRecipeId(Long recipeId);

}
