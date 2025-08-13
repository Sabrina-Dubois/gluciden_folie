package co.simplon.glucidenfoliebusiness.validations.recipe.picture;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME) // compilation
@Target(ElementType.FIELD) // permettre utilisation que sur des champs pas sur class ni parametres
@Documented // Documenter ou exploiter
@Constraint(validatedBy = FileSizeValidator.class) // contrainte de validation qui fait référence a FileSizeValidator
public @interface FileSize { // method

	static final long ONE_MB = 1024L * 1024;

	static final long TWO_MB = ONE_MB * 2;

	long max() default FileSize.ONE_MB;

	String message() default ""; // pourquoi ça ne passe pas

	Class<?>[] groups() default {}; // regrouper pls contraintes de validation

	Class<? extends Payload>[] payload() default {};
}