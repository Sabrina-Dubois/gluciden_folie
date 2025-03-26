// Indique le package auquel appartient cette classe
package co.simplon.glucidenfoliebusiness.services;

// Importation de l'annotation @Service pour indiquer que cette classe est un service Spring
import org.springframework.stereotype.Service;

import co.simplon.glucidenfoliebusiness.dtos.unity.UnityCreateDto;
// Importation de l'entité Unity qui représente une unité en base de données
import co.simplon.glucidenfoliebusiness.entities.Unity;
// Importation du repository pour accéder aux données des unités
import co.simplon.glucidenfoliebusiness.repositories.UnityRepository;
// Importation de l'annotation @Transactional pour gérer la transaction
import jakarta.transaction.Transactional;
// Importation de l'annotation @Valid pour valider les données du DTO
import jakarta.validation.Valid;

// Annotation pour indiquer que cette classe est un service géré par Spring
@Service
public class UnityService {

	// Déclaration du repository en tant qu'attribut final (non modifiable) pour
	// accéder aux données
	private final UnityRepository unitiesRepo;

	// Constructeur pour injecter le repository dans le service
	public UnityService(UnityRepository unitiesRepo) {
		this.unitiesRepo = unitiesRepo;
	}

	// Annotation pour indiquer que cette méthode s'exécute dans une transaction
	@Transactional
	public void create(@Valid UnityCreateDto unityCreateDto) {
		// Création d'une nouvelle instance de l'entité Unity
		Unity entity = new Unity();
		// Affectation du nom à partir du DTO (Data Transfer Object)
		entity.setName(unityCreateDto.name());

		// Enregistrement de l'entité en base de données
		unitiesRepo.save(entity);
	}
}
