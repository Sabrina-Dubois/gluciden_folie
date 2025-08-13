package co.simplon.glucidenfoliebusiness.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.glucidenfoliebusiness.dtos.unity.UnityCreateDto;
import co.simplon.glucidenfoliebusiness.entities.Unity;
import co.simplon.glucidenfoliebusiness.services.UnityService;
import jakarta.validation.Valid;

@RestController // Retourne http -> JSON
@RequestMapping("/unities") // chemin
public class UnityController {

	// injection service via controller
	private final UnityService unityService;

	public UnityController(UnityService unityService) {
		this.unityService = unityService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) // 201 creates si ok
	// @ valid active val si pas valide ; exceptio levé atuto
	// @requestbody donnée enoyé dans le POST seront mapé DTO unityCretadto
	public String create(@Valid @RequestBody UnityCreateDto unityCreateDto) {
		try {
			unityService.create(unityCreateDto);
			return "unity ok";
		} catch (Exception exception) {
			return "erreur";
		}
	}

	@GetMapping
	public ResponseEntity<List<Unity>> getAllUnities() {
		List<Unity> unities = unityService.getAllUnities(); // Appel du service pour récupérer les unités
		return ResponseEntity.ok(unities);
		// Retourne la liste des unités depuis le service
	}

}
