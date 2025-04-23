package co.simplon.glucidenfoliebusiness.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.glucidenfoliebusiness.entities.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

	Optional<Ingredient> findByIngredientNameIgnoreCase(String ingredientName);

}
