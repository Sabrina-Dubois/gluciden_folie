package co.simplon.glucidenfoliebusiness.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.glucidenfoliebusiness.dtos.IngredientCreateDto;
import co.simplon.glucidenfoliebusiness.services.IngredientService;
import jakarta.validation.Valid;

@RequestMapping("/ingredients")
@RestController // method renverront obj JSON
public class IngredientController {

	// le controlleur delegue au servie
	private final IngredientService ingredientService;

	// Constructor
	// spring inject une instance du service ingSrvice via l'injection
	public IngredientController(IngredientService ingredientService) {
		this.ingredientService = ingredientService;
	}

	@PostMapping // methode -> point acces pour grer rq http
	void create(@Valid @RequestBody IngredientCreateDto ingDto) {
		ingredientService.createOrGet(ingDto);
	}

}
