package co.simplon.glucidenfoliebusiness.controllers.errors;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

	// 401 (Unauthorized)
	@ExceptionHandler(value = BadCredentialsException.class)
	protected ResponseEntity<Object> handleBadCredentialException(BadCredentialsException ex, WebRequest request) {
		return super.handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
	}

	// 403 (Forbidden)
	@ExceptionHandler(value = AccessDeniedException.class)
	protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
		return super.handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
	}

	// 404 Not Found pour différentes entités
	@ExceptionHandler(AccountNotFoundException.class)
	protected ResponseEntity<String> handleAccountNotFound(AccountNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(IngredientNotFoundException.class)
	protected ResponseEntity<String> handleIngredientNotFound(IngredientNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(RecipeNotFoundException.class)
	protected ResponseEntity<String> handleRecipeNotFound(RecipeNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(UnityNotFoundException.class)
	protected ResponseEntity<String> handleUnityNotFound(UnityNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(InvalidRecipeException.class)
	protected ResponseEntity<String> handleInvalidNotFound(InvalidRecipeException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	// Erreurs de validation des DTO
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, org.springframework.http.HttpStatusCode status, WebRequest request) {

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	// Handler générique pour RuntimeException non prévue
	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

}
