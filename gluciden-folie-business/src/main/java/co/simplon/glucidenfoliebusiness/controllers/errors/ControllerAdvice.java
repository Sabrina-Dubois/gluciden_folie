package co.simplon.glucidenfoliebusiness.controllers.errors;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import co.simplon.glucidenfoliebusiness.exceptions.AccountNotFoundException;
import co.simplon.glucidenfoliebusiness.exceptions.IngredientNotFoundException;
import co.simplon.glucidenfoliebusiness.exceptions.InvalidRecipeException;
import co.simplon.glucidenfoliebusiness.exceptions.RecipeNotFoundException;
import co.simplon.glucidenfoliebusiness.exceptions.UnityNotFoundException;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

	// Gestion des erreurs 401 (Unauthorized) sans message qui est gérer par le
	// front
	@ExceptionHandler(value = BadCredentialsException.class)
	protected ResponseEntity<Object> handleBadCredentialException(BadCredentialsException ex, WebRequest request) {
		return super.handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
	}

	// Gestion des erreurs 403 (Forbidden)
	@ExceptionHandler(value = AccessDeniedException.class)
	protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
		return super.handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
	}

	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<String> handleAccountNotFound(AccountNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(IngredientNotFoundException.class)
	public ResponseEntity<String> handleIngredientNotFound(IngredientNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(RecipeNotFoundException.class)
	public ResponseEntity<String> handleRecipeNotFound(RecipeNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(UnityNotFoundException.class)
	public ResponseEntity<String> handleUnityNotFound(UnityNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(InvalidRecipeException.class)
	public ResponseEntity<String> handleInvalidNotFound(InvalidRecipeException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	// Handler générique pour RuntimeException non prévue
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

}
