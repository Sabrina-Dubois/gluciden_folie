package co.simplon.glucidenfoliebusiness.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.glucidenfoliebusiness.dtos.CategoryViewDto;
import co.simplon.glucidenfoliebusiness.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	boolean existsByNameIgnoreCase(String name);

	// Liste des catégories
	Collection<CategoryViewDto> findAllProjectedBy();

	CategoryViewDto findOneProjectedById(Long id);

}
