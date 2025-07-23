package co.simplon.glucidenfoliebusiness.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.glucidenfoliebusiness.entities.Recipe;
import co.simplon.glucidenfoliebusiness.entities.Step;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {

	// affichage des étapes d'une recette, triées par numéro
	List<Step> findByRecipeIdOrderByNumberAsc(Long recipeId);

	// Récupérer toutes les étapes à partir de l'entité Recipe
	List<Step> findByRecipe(Recipe recipe);

	// Requis pour la mise à jour complète des étapes
	void deleteByRecipeId(Long recipeId);

}
