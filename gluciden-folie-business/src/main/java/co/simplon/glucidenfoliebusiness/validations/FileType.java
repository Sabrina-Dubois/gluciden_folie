package co.simplon.glucidenfoliebusiness.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.http.MediaType;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME) // compilation
@Target(ElementType.FIELD)//permettre utilisation que sur des champs pas sur class ni parametres
@Documented // Documenter ou exploiter
@Constraint(validatedBy = FileTypeValidator.class) // contrainte de validation qui fait reference a FileSizeValidator 
public @interface FileType { //method
	
String[] types() default { MediaType.ALL_VALUE };
	
	String message() default "Le type de fichier est invalide "; //pourquoi ça ne passe pas 
	
	Class <?>[] groups() default {}; //regrouper pls contraintes de validation
	
	Class<? extends Payload>[] payload() default {};

}
