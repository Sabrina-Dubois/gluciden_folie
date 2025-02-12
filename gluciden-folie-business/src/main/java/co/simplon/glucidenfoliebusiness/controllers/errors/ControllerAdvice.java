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

	// Gestion des erreurs 400 (Bad Request) pour des validations échouées
	// @ExceptionHandler(value = MethodArgumentNotValidException.class)
	// protected ResponseEntity<Object>
	// handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
	// WebRequest request) {
	// return new ResponseEntity<>(ex.getBindingResult().getAllErrors(),
	// HttpStatus.BAD_REQUEST);
	// }

	// Gestion des erreurs 404 (Not Found)
	// @ExceptionHandler(value = ResourceNotFoundException.class)
	// protected ResponseEntity<Object>
	// handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest
	// request) {
	// return new ResponseEntity<>("Ressource non trouvée", HttpStatus.NOT_FOUND);
	// }

	// Gestion des erreurs 409 (Conflict), par exemple en cas de doublon
	// @ExceptionHandler(value = DuplicateResourceException.class)
	// protected ResponseEntity<Object>
	// handleDuplicateResourceException(DuplicateResourceException ex,
	// WebRequest request) {
	// return new ResponseEntity<>("Ressource déjà existante", HttpStatus.CONFLICT);
	// }
}
