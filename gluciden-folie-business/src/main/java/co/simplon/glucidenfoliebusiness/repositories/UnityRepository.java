package co.simplon.glucidenfoliebusiness.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.glucidenfoliebusiness.entities.Unity;

@Repository
public interface UnityRepository extends JpaRepository<Unity, Long> {

	// Retourne toutes les unités disponibles dans la base
	List<Unity> findAll();
}
