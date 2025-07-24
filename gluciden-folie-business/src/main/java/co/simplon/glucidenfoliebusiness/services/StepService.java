package co.simplon.glucidenfoliebusiness.services;

import java.util.List;

import org.springframework.stereotype.Service;

import co.simplon.glucidenfoliebusiness.entities.Step;
import co.simplon.glucidenfoliebusiness.repositories.StepRepository;

@Service
public class StepService {

	private final StepRepository stepRepo;

	public StepService(StepRepository stepRepo) {
		this.stepRepo = stepRepo;
	}

	public List<Step> getStepsByRecipeIdOrderByNumber(Long recipeId) {
		// Recipe recipe = new Recipe();
		// recipe.setId(recipeId);
		return stepRepo.findByRecipeIdOrderByNumberAsc(recipeId);
	}

	// + Méthode de sauvegarde explicite
	public List<Step> saveAll(List<Step> steps) {
		return stepRepo.saveAll(steps);
	}
}
