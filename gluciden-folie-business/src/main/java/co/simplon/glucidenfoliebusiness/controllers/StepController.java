package co.simplon.glucidenfoliebusiness.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.glucidenfoliebusiness.entities.Step;
import co.simplon.glucidenfoliebusiness.services.StepService;

@RestController
@RequestMapping("/steps")
public class StepController {

	private final StepService service;

	public StepController(StepService service) {
		this.service = service;
	}

	@GetMapping("/recipe/{recipeId}")
	public ResponseEntity<List<Step>> getStepsByRecipe(@PathVariable Long recipeId) {
		List<Step> steps = service.getStepsByRecipeIdOrderByNumber(recipeId);
		if (steps.isEmpty()) {
			return ResponseEntity.noContent().build(); // 204 No Content si pas d'étapes
		}
		return ResponseEntity.ok(steps); // 200 OK avec la liste des étapes
	}

}
