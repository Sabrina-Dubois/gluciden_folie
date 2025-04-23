package co.simplon.glucidenfoliebusiness.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.glucidenfoliebusiness.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	// Bonne pratique : ne jamais retourne une entité mais un objet de type
	// optionnel
	// Enveloppe qui encapsule null
	Optional<Account> findByUsernameIgnoreCase(String username);

	boolean existsByUsernameIgnoreCase(String username);

}
