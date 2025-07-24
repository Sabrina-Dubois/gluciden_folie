package co.simplon.glucidenfoliebusiness.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.glucidenfoliebusiness.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	boolean existsByNameIgnoreCase(String name);

	// Liste des catégories sans projection, récupération des entités Category
	List<Category> findAll();

	// Recherche d'une catégorie par son ID
	Optional<Category> findById(Long id);
}