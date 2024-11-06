//Logique métier => Implémenter la logique métier

package co.simplon.glucidenfoliebusiness.services;

import java.io.File;
import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import co.simplon.glucidenfoliebusiness.dtos.RecipeCreateDto;
import co.simplon.glucidenfoliebusiness.dtos.RecipeUpdateDto;
import co.simplon.glucidenfoliebusiness.dtos.RecipeViewDto;
import co.simplon.glucidenfoliebusiness.entities.Recipe;
import co.simplon.glucidenfoliebusiness.repositories.RecipeRepository;

@Service
public class RecipeService {

	@Value("${glucidenfoliebusiness.uploads.dest}")
	private String uploadsDest;

	// necessary field for communicated with DB
	private final RecipeRepository recipes;

	// Constructeur de la classe SpotService qui initialise les champs recipes avec
	// les instances des repositories injectées par Spring.
	public RecipeService(RecipeRepository recipes) {
		this.recipes = recipes;
	}

	// Créer la recette
	@Transactional // toutes opérations dedans gere les comme 1 et une seule transaction
	public void create(RecipeCreateDto inputs) {
		Recipe entity = new Recipe(); // nouveau objet de recette vide
		entity.setName(inputs.name());// donne un nom à la recette en utilisant le contenu de inputs
		MultipartFile pictureFromDto = inputs.picture();
		if (pictureFromDto != null) {
			String pictureToEntity = buildPicture(pictureFromDto);
			storePicture(pictureFromDto, pictureToEntity);
			entity.setPicture(pictureToEntity);
		}
		recipes.save(entity);
	}

	// Importer une photo de la recette
	// Générer un identifiant unique pour l'image
	private String buildPicture(MultipartFile pictureFromDto) {
		UUID uuid = UUID.randomUUID(); // Génère identifiant unique
		String name = pictureFromDto.getOriginalFilename(); // Récupère le nom d'origine du fichier
		int index = name.lastIndexOf('.');// dernier point
		String ext = name.substring(index, name.length());// extention du fichier
		return uuid + ext; // concat id et extension
	}

	private void storePicture(MultipartFile pictureFromDto, String pictureToEntity) {
		try {
			String dest = String.format("%s/%s", uploadsDest, pictureToEntity);
			File file = new File(dest);// objet File -> emplacement de destination
			pictureFromDto.transferTo(file);// Transfère l'image au nouvel emplacement
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	// Lire toutes les recettes
	public Collection<RecipeViewDto> getAll() {
		return recipes.findAllProjectedBy();
	}

	// Supprimer une recette
	public void deleteOne(Long id) {
		recipes.deleteById(id);
	}

	// Modifier uen recette
	public void updateOne(long id, RecipeUpdateDto inputs) {
		Recipe entity = recipes.findById(id).orElseThrow();
		entity.setName(inputs.name());

		MultipartFile newPicture = inputs.picture();
		if (newPicture != null) {
			String newPictureName = buildPicture(newPicture);
			storePicture(newPicture, newPictureName);
			entity.setPicture(newPictureName);
		}
		recipes.save(entity);
	}

	// Récupère une recette
	public RecipeViewDto getOne(Long id) {
		return recipes.findOneProjectedById(id);
	}

}
